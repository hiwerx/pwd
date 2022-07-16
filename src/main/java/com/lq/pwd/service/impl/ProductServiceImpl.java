package com.lq.pwd.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lq.pwd.mapper.ProductMapper;
import com.lq.pwd.model.Product;
import com.lq.pwd.service.IProductService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 产品信息 服务实现类
 * </p>
 *
 * @author lq.com
 * @since 2022-06-19
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

    public List<Product> getAllProductId(){
        return lambdaQuery().select(Product::getId).list();
    }
}
