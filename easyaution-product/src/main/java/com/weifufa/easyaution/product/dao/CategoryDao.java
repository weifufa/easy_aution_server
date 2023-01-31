package com.weifufa.easyaution.product.dao;

import com.weifufa.easyaution.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author weifufa
 * @email wff66@foxmail.com
 * @date 2023-01-31 21:28:27
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
