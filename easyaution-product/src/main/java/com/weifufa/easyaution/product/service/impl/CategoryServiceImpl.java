package com.weifufa.easyaution.product.service.impl;

import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weifufa.common.utils.PageUtils;
import com.weifufa.common.utils.Query;

import com.weifufa.easyaution.product.dao.CategoryDao;
import com.weifufa.easyaution.product.entity.CategoryEntity;
import com.weifufa.easyaution.product.service.CategoryService;
import org.springframework.util.StringUtils;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        //1.获取key
        String key=(String) params.get("key");
        QueryWrapper<CategoryEntity> queryWrapper=new QueryWrapper<>();
        if(!StringUtils.isEmpty(key))
        {
            queryWrapper.eq("name",key);
        }
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                queryWrapper.orderByAsc("sort")
        );
        return new PageUtils(page);
    }


}