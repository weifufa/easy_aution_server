package com.weifufa.easyaution.product.controller;

import java.util.Arrays;
import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.weifufa.easyaution.product.entity.AuctionEntity;
import com.weifufa.easyaution.product.service.AuctionService;
import com.weifufa.common.utils.PageUtils;
import com.weifufa.common.utils.R;



/**
 * 
 *
 * @author weifufa
 * @email wff66@foxmail.com
 * @date 2023-02-02 23:44:51
 */
@RestController
@RequestMapping("product/auction")
@Api(tags = "类别管理", value = "拍品管理") //类说明
public class AuctionController {
    @Autowired
    private AuctionService auctionService;

    /**
     * 列表
     */
    @ApiOperation(value = "获取拍品列表")
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = auctionService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @ApiOperation(value = "获取拍品信息")
    @RequestMapping("/info/{auctionId}")
    public R info(@PathVariable("auctionId") Integer auctionId){
		AuctionEntity auction = auctionService.getById(auctionId);

        return R.ok().put("auction", auction);
    }

    /**
     * 保存
     */
    @ApiOperation(value = "保存拍品信息")
    @RequestMapping("/save")
    public R save(@RequestBody AuctionEntity auction){
		auctionService.save(auction);

        return R.ok();
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改拍品信息")
    @RequestMapping("/update")
    public R update(@RequestBody AuctionEntity auction){
		auctionService.updateById(auction);

        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除拍品信息")
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] auctionIds){
		auctionService.removeByIds(Arrays.asList(auctionIds));

        return R.ok();
    }

}
