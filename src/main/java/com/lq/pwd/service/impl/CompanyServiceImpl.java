package com.lq.pwd.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lq.pwd.common.Constant;
import com.lq.pwd.dto.CompanyDTO;
import com.lq.pwd.mapper.CompanyMapper;
import com.lq.pwd.model.Company;
import com.lq.pwd.service.ICompanyService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 厂商，公司信息 服务实现类
 * </p>
 *
 * @author lq.com
 * @since 2022-06-19
 */
@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements ICompanyService {

    @Value("${personal.company.sum}")
    Integer comNum;
    @Override
    public void add(CompanyDTO companyDTO) {
        if (lambdaQuery().eq(Company::getUsrId,companyDTO.getUsrId()).list().size()>=comNum){
            throw new RuntimeException(Constant.ERR_COMPANY_NUM_FULL+comNum+"个,若需更多厂商请联系管理员或选择其他");
        }
        String type = companyDTO.getType();
        if (type == null) throw new RuntimeException(Constant.ERR_COMPANY_TYPE_NULL);
        String companyName = companyDTO.getProducerName();
        if (companyName == null) throw new RuntimeException(Constant.ERR_COMPANY_NAME_NULL);
        List<Company> companyList = this.lambdaQuery().eq(Company::getName,companyName).in(Company::getUsrId,0,companyDTO.getUsrId()).list();
        if (companyList.size()>0) throw new RuntimeException(Constant.ERR_COMPANY_EXIT);
        Company company = Company.builder().name(companyName)
                .type(type)
                .phone(companyDTO.getPhone())
                .url(companyDTO.getUrl())
                .icon(companyDTO.getIcon())
                .usrId(companyDTO.getUsrId())
                .build();
        this.save(company);
    }

    @Override
    public List<Company> get(Integer usrId) {
        return this.lambdaQuery().select(Company::getId,Company::getName,Company::getType)
                .in(Company::getUsrId,0,usrId)
                .list();
    }
}
