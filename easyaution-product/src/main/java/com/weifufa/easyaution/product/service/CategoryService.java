package com.weifufa.easyaution.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weifufa.common.utils.PageUtils;
import com.weifufa.easyaution.product.entity.CategoryEntity;

import java.util.Map;

/**
 * 商品三级分类
 *
 * @author weifufa
 * @email wff66@foxmail.com
 * @date 2023-01-31 21:28:27
 */
public interface CategoryService extends IService<CategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

