package com.lq.pwd.controller;


import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.api.R;
import com.lq.pwd.common.Check;
import com.lq.pwd.common.Constant;
import com.lq.pwd.dto.CompanyDTO;
import com.lq.pwd.model.Company;
import com.lq.pwd.security.LoginUserInfo;
import com.lq.pwd.service.ICompanyService;
import com.lq.pwd.service.impl.CompanyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 厂商，公司信息 前端控制器
 * </p>
 *
 * @author lq.com
 * @since 2022-06-19
 */
@RestController
@RequestMapping("/pwd/company")
public class CompanyController {
    @Autowired
    RedisTemplate<String,String> redisTemplate;
    @Autowired
    TimedCache<String, String> timedCache;
    @Autowired
    ICompanyService companyService;
    @RequestMapping("add")
    public R add(@RequestBody @Valid CompanyDTO companyDTO, BindingResult result, @AuthenticationPrincipal LoginUserInfo userInfo){
        Check.checkDTO(result);

        String key = "usr:"+userInfo.getUserId()+":addCompany";
        String value = timedCache.get(key);
        if (StrUtil.isEmpty(value)) {
            timedCache.put(key, "1");
        }else{
            timedCache.put(key,(Integer.parseInt(value)+1)+"");
            throw new RuntimeException(Constant.ERR_SYS_P);
        }

        companyDTO.setUsrId(userInfo.getUserId());
        companyService.add(companyDTO);
        timedCache.remove(key);
        return R.ok("添加企业成功");
    }

    @RequestMapping("get")
    public R get(@AuthenticationPrincipal LoginUserInfo userInfo){
        List<Company> companyList = companyService.get(userInfo.getUserId());
        return R.ok(companyList);
    }

}
