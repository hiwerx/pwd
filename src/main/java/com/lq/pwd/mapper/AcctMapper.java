package com.lq.pwd.mapper;

import com.lq.pwd.model.Acct;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lq.pwd.vo.AcctPwdVO;
import com.lq.pwd.vo.CompanyAcctPwdVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 账号信息 Mapper 接口
 * </p>
 *
 * @author lq.com
 * @since 2022-06-13
 */
@Repository
public interface AcctMapper extends BaseMapper<Acct> {

    List<CompanyAcctPwdVO> getAllPwd(Integer usrId, String searchText);

    Integer selectBankAppPwdCount(Integer companyId, Integer usrId, String pwdType);

    List<Integer> selectBankAcctIdByPwdType(Integer companyId, Integer usrId);
}
