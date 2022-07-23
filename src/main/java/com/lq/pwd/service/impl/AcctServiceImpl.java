package com.lq.pwd.service.impl;

import cn.hutool.core.util.StrUtil;
import com.lq.pwd.common.Check;
import com.lq.pwd.common.Constant;
import com.lq.pwd.common.Rsa;
import com.lq.pwd.dto.AcctPwdDTO;
import com.lq.pwd.dto.AddPwdDTO;
import com.lq.pwd.dto.EditAcctDTO;
import com.lq.pwd.dto.EditPwdDTO;
import com.lq.pwd.model.Acct;
import com.lq.pwd.mapper.AcctMapper;
import com.lq.pwd.model.Company;
import com.lq.pwd.model.Pwd;
import com.lq.pwd.service.IAcctService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lq.pwd.service.ICompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 账号信息 服务实现类
 * </p>
 *
 * @author lq.com
 * @since 2022-06-13
 */
@Slf4j
@Service
public class AcctServiceImpl extends ServiceImpl<AcctMapper, Acct> implements IAcctService {

    @Value("${pubKeyQ}")
    String pubKeyQ;

    @Value("${priKeyQ}")
    String priKeyQ;

    @Autowired
    PwdServiceImpl pwdService;

    @Autowired
    AcctMapper acctMapper;

    @Autowired
    ICompanyService companyService;

    @Autowired
    ProductServiceImpl productService;

    /**
     * 添加账号密码
     * @param acctPwdDTO
     */
    @Transactional
    public void addAcct(AcctPwdDTO acctPwdDTO){
        // 把companyId和Type拆出来
        Integer companyId = null;
        String companyType=null;
        try {
            String companyIdType = acctPwdDTO.getCompanyIdType();
            String[] idType = companyIdType.split("-");
            acctPwdDTO.setCompanyId(Integer.parseInt(idType[0]));
            companyId = Integer.parseInt(idType[0]);
            acctPwdDTO.setCompanyType(idType[1]);
            companyType = idType[1];
//            log.info("companyId:{},companyType:{}",companyId,companyType);
            // 校验用户上送的id-type是否有误
            List<Company> companyList = companyService.get(acctPwdDTO.getUsrId());
            boolean containFlag = false;
            for (Company company : companyList) {
                if (company.getId().intValue()==companyId.intValue()){
                    containFlag = true;
                    if (!company.getType().equals(companyType))throw new RuntimeException(Constant.ERR_COMPANY_ID_TYPE);
                    break;
                }
            }
            if (!containFlag)
                throw new RuntimeException(Constant.ERR_COMPANY_ID_TYPE);

        }catch (Exception e){
            throw new RuntimeException(Constant.ERR_COMPANY_ID_TYPE);
        }
        // 校验产品类型是否匹配
        Check.checkProductByCompanyType(acctPwdDTO.getProductId(),companyType);

        // 校验账号密码
        Check.checkAcctName(acctPwdDTO.getAcctName());

        // 检查密码类型
        String pwdType = acctPwdDTO.getPwdType();
        // 检查密码
        Check.checkPwdTypeByCompanyType(pwdType,companyType);

        if (Constant.PWD_TYPE_MSG.equals(pwdType))acctPwdDTO.setPhone(null);
        if (Constant.PWD_TYPE_EMAIL.equals(pwdType))acctPwdDTO.setEmail(null);

        Pwd pwd = Check.checkAndNewPwd(pwdType, acctPwdDTO.getPwd(), acctPwdDTO.getAuthProductId());

        if (Constant.COMPANY_TYPE_BANK.equals(acctPwdDTO.getCompanyType())){
            int judgeRes = judgeBank(acctPwdDTO);
            if (judgeRes == 1) return;
        }else {

        /*
         针对非银行的多密码（支付密码）保存
         支付密码保存前，必须有登录密码或短信验证登录，若无则做提示

         非银行账户不支持保存ukey密码
         */

        }




        // 检查账号是否保存过
        Check.checkAcctNameExit(acctPwdDTO.getAcctName(),acctPwdDTO.getUsrId(),acctPwdDTO.getCompanyId());

        // 判断账号密码保存个数
        Check.checkAcctPwdLimit(acctPwdDTO.getUsrId());
         Acct acct = Acct.builder().companyId(acctPwdDTO.getCompanyId())
                .acctName(acctPwdDTO.getAcctName())
                .phone(acctPwdDTO.getPhone())
                .productId(acctPwdDTO.getProductId())
                .usrId(acctPwdDTO.getUsrId())
                .email(acctPwdDTO.getEmail())
                .build();
        save(acct);

        pwd.setAcctId(acct.getId());
        setDbPwd(pwd);
        pwdService.save(pwd);
    }

    /**
     * 判断银行账户是否符合添加密码
     *
    *  银行账户判断
    *  每家银行账户只允许一个登录密码，支付密码，和ukey密码，绑定一个手机号，若用户在添加以上多个密码时提示已保存的密码是否修改
    *  但可以支持多个银行卡交易密码
     *
     * @param acctPwdDTO
     * @return 返回1表示已处理完任务，其他未处理完
     */
    private Integer judgeBank(AcctPwdDTO acctPwdDTO) {
        // 手机号问题
        String pwdType = acctPwdDTO.getPwdType();
        if (Constant.PWD_TYPE_AUTH.equals(pwdType)) throw new RuntimeException("银行账户不支持授权登录");
        if (Constant.PWD_TYPE_TRADE.equals(pwdType)){
            if (!acctPwdDTO.getAcctName().matches("\\d{10,20}"))
                throw new RuntimeException("交易密码只支持银行卡，检查银行卡号输入是否有误");
        } else {
            Integer count = acctMapper.selectBankAppPwdCount(acctPwdDTO.getCompanyId(), acctPwdDTO.getUsrId(), acctPwdDTO.getPwdType());
            if (count!=0) throw new RuntimeException("该类型密码只允许保存一个，检查是否需要修改密码");

            // 查找 登录密码支付密码的等的账户，若已保存，则新增密码即可,若无全部新增；
            List<Integer> acctIdList = acctMapper.selectBankAcctIdByPwdType(acctPwdDTO.getCompanyId(), acctPwdDTO.getUsrId());
            if (acctIdList.size()>0){

                Check.checkAcctPwdLimit(acctPwdDTO.getUsrId());

                Pwd pwd = Pwd.builder()
                        .pwd(acctPwdDTO.getPwd())
                        .pwdType(acctPwdDTO.getPwdType())
                        .authProductId(acctPwdDTO.getAuthProductId())
                        .acctId(acctIdList.get(0))
                        .build();
                setDbPwd(pwd);
                pwdService.save(pwd);
                return 1;
            }
        }
        return 0;

    }

    /**
     * 根据账户id删除账户
     * @param acctId
     */
    public void deleteAcctById(Integer acctId, Integer usrId){
        acctExit(acctId,usrId);
        updateById(Acct.builder().id(acctId).valid("0").build());
    }

    /**
     * 根据companyId删除所有的acct
     * @param companyId
     */
    public void deleteAllAcctByCompanyId(Integer companyId, Integer usrId){
        lambdaUpdate().eq(Acct::getCompanyId,companyId)
                .eq(Acct::getValid,"1")
                .eq(Acct::getUsrId,usrId)
                .set(Acct::getValid,"0")
                .update();
    }
    /**
     * acctId不为空，密码pwd不为空，密码类型pwdType不为空
     * @param acctPwdDTO
     * @return
     */
    public boolean updatePwd(AcctPwdDTO acctPwdDTO){
        boolean updateFlag = pwdService.lambdaUpdate().set(Pwd::getValid,"0")
//                .eq(Pwd::getAcctId,acctPwdDTO.getAcctId())
                .eq(Pwd::getValid,"1")
                .update();
        if (!updateFlag) return false;
        boolean saveFlag = pwdService.save(Pwd.builder().pwd(acctPwdDTO.getPwd())
//                .acctId(acctPwdDTO.getAcctId())
                .pwdType(acctPwdDTO.getPwdType())
                .build());
        return saveFlag;
    }

    /**
     * 查找账号的历史密码
     * @param acctId
     * @return
     */
    public List<Pwd> hisPwd(Integer acctId){
        List<Pwd> pwdList = pwdService.lambdaQuery().eq(Pwd::getAcctId, acctId).list();
        return pwdList;
    }

    /**
     * 查找用户所有的密码
     * @param usrId
     * @return
     */
    public List getAllPwd(Integer usrId){
        return acctMapper.getAllPwd(usrId,null);
    }

    /**
     * 搜索用户保存的密码
     * @param searchText
     * @return
     */
    public List searchPwd(Integer usrId,String searchText){
        return acctMapper.getAllPwd(usrId,"%"+searchText+"%");
    }

    public void deletePwdById(Integer pwdId, Integer usrId) {
        pwdExit(pwdId,usrId);
        pwdService.lambdaUpdate()
                .set(Pwd::getValid,"0")
                .eq(Pwd::getId,pwdId)
                .update();
    }

    /**
     * 判断用户是否有此账户，防止恶意操作
     * @param acctId
     * @param usrId
     */
    public Acct acctExit(Integer acctId,Integer usrId){
        if (acctId == null)throw new RuntimeException(Constant.ERR_ACCT_ID_NULL);
        List<Acct> acctList = lambdaQuery().eq(Acct::getUsrId,usrId)
                .eq(Acct::getValid,"1")
                .eq(Acct::getId,acctId).list();
        if (acctList.size() == 0) throw new RuntimeException(Constant.ERR_NO_ACCT);
        return acctList.get(0);
    }

    /**
     * 判断用户是否有此密码，防止恶意操作
     * @param pwdId
     * @param usrId
     */
    public Pwd pwdExit(Integer pwdId, Integer usrId){
        if (pwdId == null) throw new RuntimeException(Constant.ERR_PWD_ID_NULL);
        Pwd pwd = pwdService.getById(pwdId);
        if (pwd == null) throw new RuntimeException(Constant.ERR_NO_PWD);
        try{
            acctExit(pwd.getAcctId(),usrId);
        }catch (Exception e){
            throw new RuntimeException(Constant.ERR_NO_PWD);
        }
        return pwd;
    }

    public void addPwd(AddPwdDTO acctPwdDTO) {

        // 查下该账户的该密码是否已保存

        // 如果是银行账号，登录密码，支付密码，ukey,短信验证只能一个，且每张卡号只能一个交易密码


        // 没有账户不允许添加支付密码
        acctExit(acctPwdDTO.getAcctId(),acctPwdDTO.getUsrId());
        // 判断密码类型
        String pwdType= acctPwdDTO.getPwdType();
        Check.checkPwdTypeByCompanyType(pwdType,acctPwdDTO.getCompanyType());
        Pwd pwd = Check.checkAndNewPwd(pwdType,acctPwdDTO.getPwd(),acctPwdDTO.getAuthProductId());
        pwd.setAcctId(acctPwdDTO.getAcctId());

        List<Acct> acctList = this.lambdaQuery().eq(Acct::getUsrId,acctPwdDTO.getUsrId()).list();
        List<Pwd> pwdList = pwdService.lambdaQuery().in(Pwd::getAcctId,acctList.stream().map(Acct::getId).collect(Collectors.toList())).list();
        if (pwdList.size()>Check.pwdMax.intValue()) throw new RuntimeException(Constant.ERR_PED_TOO_MUCH);

        setDbPwd(pwd);
        pwdService.save(pwd);

    }

    public void editAcct(EditAcctDTO acctPwdDTO) {
        Integer acctId = acctPwdDTO.getAcctId();
        String acctName = acctPwdDTO.getAcctName();
        String phone = acctPwdDTO.getPhone();
        String email = acctPwdDTO.getEmail();

        // 合法校验
        Acct acct = acctExit(acctId,acctPwdDTO.getUsrId());
        boolean acctNameNoChange = acctName.equals(acct.getAcctName());
        boolean phoneNoChange = (StrUtil.isBlank(phone)&&StrUtil.isBlank(acct.getPhone()))|| Objects.equals(phone,acct.getPhone());
        boolean emailNoChange = (StrUtil.isBlank(email)&&StrUtil.isBlank(acct.getEmail()))||Objects.equals(email,acct.getEmail());
        if (acctNameNoChange&&phoneNoChange&&emailNoChange)
            throw new RuntimeException(Constant.ERR_NO_CHANGE);
        // 账户名有变动检查一下是否存在，没变动不检查
        if (!acctNameNoChange) Check.checkAcctNameExit(acctName,acctPwdDTO.getUsrId(),acct.getCompanyId());

        lambdaUpdate().set(!acctNameNoChange,Acct::getAcctName,acctName)
                .set(!phoneNoChange, Acct::getPhone,StrUtil.isBlank(phone)?null:phone)
                .set(!emailNoChange,Acct::getEmail,StrUtil.isBlank(email)?null:email)
                .eq(Acct::getId,acctId)
        .update()
        ;

    }

    public void editPwd(EditPwdDTO acctPwdDTO) {
        Integer pwdId = acctPwdDTO.getPwdId();
        String pwdType = acctPwdDTO.getPwdType();
        String pwdName = acctPwdDTO.getPwd();
        Integer authProductId = acctPwdDTO.getAuthProductId();
        if (pwdId==null) throw new RuntimeException(Constant.ERR_PWD_ID_NULL);
        if (StrUtil.isBlank(pwdName)) throw new RuntimeException(Constant.ERR_PWD_NULL);
        Pwd pwd = pwdExit(pwdId, acctPwdDTO.getUsrId());
        String dbPwdName = pwd.getPwd();
        dbPwdName = Rsa.decodeRsa(priKeyQ,dbPwdName);
        boolean pwdNoChange = pwdName.equals(dbPwdName);
        setDbPwd(pwd);
        if (Constant.PWD_TYPE_AUTH.equals(pwdType)){
            boolean authProductIdNoChange = pwd.getAuthProductId().intValue()==authProductId.intValue();
            if (authProductIdNoChange&&pwdNoChange)
                throw new RuntimeException(Constant.ERR_NO_CHANGE);
            pwdService.lambdaUpdate().set(!pwdNoChange,Pwd::getPwd,getDbPwd(pwdName))
                    .set(!authProductIdNoChange,Pwd::getAuthProductId,authProductId)
                    .eq(Pwd::getId,pwdId).update();
        } else {
            if (pwdNoChange) throw new RuntimeException(Constant.ERR_NO_CHANGE);
            pwdService.lambdaUpdate().set(Pwd::getPwd,getDbPwd(pwdName))
                    .eq(Pwd::getId,pwdId).update();
        }
    }

    // 入库加密
    public void setDbPwd(Pwd pwd){
        pwd.setPwd(Rsa.encodeRsa(pubKeyQ,pwd.getPwd()));
    }
    // 入库加密
    public String getDbPwd(String pwd){
        return Rsa.encodeRsa(pubKeyQ,pwd);
    }
}
