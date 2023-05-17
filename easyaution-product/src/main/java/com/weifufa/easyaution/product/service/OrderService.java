package com.weifufa.easyaution.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weifufa.common.utils.PageUtils;
import com.weifufa.easyaution.product.entity.OrderEntity;

import java.util.Map;

/**
 * 
 *
 * @author weifufa
 * @email wff66@foxmail.com
 * @date 2023-03-26 17:03:16
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

