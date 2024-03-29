package com.weifufa.easyaution.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.weifufa.common.valid.AddGroup;
import com.weifufa.common.valid.UpdateGroup;
import com.weifufa.common.valid.UpdateStatusGroup;
import com.weifufa.easyaution.product.entity.AuctionEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.weifufa.easyaution.product.entity.CategoryEntity;
import com.weifufa.easyaution.product.service.CategoryService;
import com.weifufa.common.utils.PageUtils;
import com.weifufa.common.utils.R;


/**
 * 商品三级分类
 *
 * @author weifufa
 * @email wff66@foxmail.com
 * @date 2023-01-31 21:28:27
 */

@RestController
@RequestMapping("product/category")
@Api(tags = "类别管理", value = "拍品的分类管理") //类说明
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 列表
     */
    @ApiOperation(value = "获取类别列表")
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = categoryService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 查出所有分类列表
     */
    @ApiOperation(value = "获取分类列表")
    @RequestMapping("/list/all")
    public R list(){
        List<CategoryEntity> list = categoryService.list();
        return R.ok().put("data", list);
    }
    /**
     * 信息
     */
    @ApiOperation(value = "获取信息列表")
    @RequestMapping("/info/{catId}")
    public R info(@PathVariable("catId") Long catId) {
        CategoryEntity category = categoryService.getById(catId);

        return R.ok().put("category", category);
    }

    /**
     * 保存
     */
    @ApiOperation(value = "保存类别列表")
    @RequestMapping("/save")
    public R save(@RequestBody CategoryEntity category) {
        categoryService.save(category);

        return R.ok();
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改类别列表")
    @RequestMapping("/update")
    public R update(@Validated({AddGroup.class, UpdateGroup.class}) @RequestBody CategoryEntity category) {
        categoryService.updateById(category);

        return R.ok();
    }

    /**
     * 修改状态
     */

    @ApiOperation(value = "分类修改状态")
    @RequestMapping("/update/status")
    public R updateStatus(@Validated(UpdateStatusGroup.class) @RequestBody CategoryEntity category) {
        categoryService.updateById(category);
        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除类别列表")
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] catIds) {
        categoryService.removeByIds(Arrays.asList(catIds));
        return R.ok();
    }

}
