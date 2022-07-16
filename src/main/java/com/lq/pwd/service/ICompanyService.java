package com.lq.pwd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lq.pwd.dto.CompanyDTO;
import com.lq.pwd.model.Company;

import java.util.List;

/**
 * <p>
 * 厂商，公司信息 服务类
 * </p>
 *
 * @author lq.com
 * @since 2022-06-19
 */
public interface ICompanyService extends IService<Company> {

    void add(CompanyDTO companyDTO);

    List<Company> get(Integer usrId);
}
