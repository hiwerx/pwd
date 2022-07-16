package com.lq.pwd.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lq.pwd.mapper.AuthProductMapper;
import com.lq.pwd.model.AuthProduct;
import com.lq.pwd.service.IAuthProductService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 登录授权方 服务实现类
 * </p>
 *
 * @author lq.com
 * @since 2022-06-19
 */
@Service
public class AuthProductServiceImpl extends ServiceImpl<AuthProductMapper, AuthProduct> implements IAuthProductService {

}
