package com.weifufa.easyaution.product.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.weifufa.easyaution.product.entity.OrderEntity;
import com.weifufa.easyaution.product.service.OrderService;
import com.weifufa.common.utils.PageUtils;
import com.weifufa.common.utils.R;



/**
 * 
 *
 * @author weifufa
 * @email wff66@foxmail.com
 * @date 2023-03-26 17:03:16
 */
@RestController
@RequestMapping("product/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = orderService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{orderId}")
    public R info(@PathVariable("orderId") Integer orderId){
		OrderEntity order = orderService.getById(orderId);
        return R.ok().put("order", order);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody OrderEntity order){
		orderService.save(order);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody OrderEntity order){
		orderService.updateById(order);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] orderIds){
		orderService.removeByIds(Arrays.asList(orderIds));

        return R.ok();
    }

}
