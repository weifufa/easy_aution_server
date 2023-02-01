package com.weifufa.easyaution.product;

import com.weifufa.easyaution.product.dao.CategoryDao;
import com.weifufa.easyaution.product.entity.CategoryEntity;
import com.weifufa.easyaution.product.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class EasyautionProductApplicationTests {
    @Autowired
    CategoryService categoryService;
@Autowired
    CategoryDao categoryDao;
    @Test
    void contextLoads() {

//        List<CategoryEntity> list = categoryService.getBaseMapper().selectList(null);
//        System.out.println(list);
        //categoryService.removeById(1463)
        categoryDao.deleteById(1436);
        List<CategoryEntity> categoryEntities = categoryDao.selectList(null);
        System.out.println(categoryEntities);
    }

}
