package com.weifufa.easyaution.product.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.weifufa.common.constant.ProductConstant;
import com.weifufa.common.utils.JWTUtil;
import com.weifufa.easyaution.product.dao.CompeteDao;
import com.weifufa.easyaution.product.entity.CompeteEntity;
import com.weifufa.easyaution.product.vo.QueryParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.weifufa.easyaution.product.entity.AuctionEntity;
import com.weifufa.easyaution.product.service.AuctionService;
import com.weifufa.common.utils.PageUtils;
import com.weifufa.common.utils.R;

import javax.servlet.http.HttpServletRequest;


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
    @Autowired
    private CompeteDao competeDao;
    @Autowired
    StringRedisTemplate redisTemplate;
    /**
     * 列表
     */
    @ApiOperation(value = "获取拍品列表")
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = auctionService.queryPage(params);

        return R.ok().put("page", page);
    }

    //获取未开拍物品信息
    @ApiOperation(value = "获取未开拍物品信息")
    @RequestMapping("/getAuctionNotStart")
    public R listNotStart(@RequestBody QueryParam param){
       List<AuctionEntity> list= auctionService.getNotStartAuction(param);

        return R.ok().put("data", list);
    }
    /**
     * 信息
     */
    @ApiOperation(value = "获取拍品信息")
    @RequestMapping("/info/{auctionId}")
    public R info(@PathVariable ("auctionId") Integer auctionId){
        AuctionEntity auction = auctionService.getById(auctionId);
        return R.ok().put("auction", auction);
    }
    /**
     * 信息
     */
    @ApiOperation(value = "获取拍品信息")
    @RequestMapping("/info")
    public R getinfoById(@RequestParam ("auctionId") Integer auctionId){
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
    /**
     * 参与竞拍
     */
    @ApiOperation(value = "参与竞拍")
    @RequestMapping("/submitAuction")
    public R submitAuction(@RequestBody CompeteEntity competeEntity, HttpServletRequest request){
        String token =request.getHeader("Authorization");
        if(token==null)
        {
            return R.error("token失效，请重新登录");
        }
        String userPhone = JWTUtil.getUserPhone(token);
        competeEntity.setUserPhone(userPhone);
        CompeteEntity compete=competeDao.selectByAuctionIdAndUserPhone(competeEntity);

        int count=0;
        Integer Bid=competeEntity.getBid();//出价
        if(compete==null)
        {
             count = competeDao.insert(competeEntity);//插入数据
            redisTemplate.opsForValue().set(ProductConstant.AUCTION_MAX_PRICE+competeEntity.getAuctionId(),Bid.toString()); //缓存到redis
        }else {
            compete.setBid(Bid);//更新竞价
            compete.setUpdateTime(new Date());
            count = competeDao.updateById(compete);//不等于null直接更新最高价格
            redisTemplate.opsForValue().set(ProductConstant.AUCTION_MAX_PRICE+competeEntity.getAuctionId(),Bid.toString());
        }
        if(count==1)
        return R.ok().put("AlreadyBid",Bid);
        else return R.error();
    }
    /**
     * 获取竞拍最高价
     */
    @ApiOperation(value = "获取竞拍最高价")
    @RequestMapping("/getMaxPrice")
    public R getMaxPrice(@RequestParam ("auctionId") Integer auctionId) {
        String MaxPrice = redisTemplate.opsForValue().get(ProductConstant.AUCTION_MAX_PRICE + auctionId);
        return R.ok().put("maxPrice",MaxPrice);
    }
    /**
     * 获取已出价
     */
    @ApiOperation(value = "获取已出价")
    @RequestMapping("/getAlreadyBid")
    public R getAlreadyBid(@RequestParam ("auctionId") Integer auctionId,HttpServletRequest request) {
        String token =request.getHeader("Authorization");
        String userPhone = JWTUtil.getUserPhone(token);

        if(StringUtils.isEmpty(userPhone))
        {
            return R.error("token失效，请重新登录");
        }
        CompeteEntity competeEntity=new CompeteEntity();
        competeEntity.setAuctionId(auctionId);
        competeEntity.setUserPhone(userPhone);
        CompeteEntity compete=competeDao.selectByAuctionIdAndUserPhone(competeEntity);
        if(compete!=null)
        return R.ok().put("AlreadyBid",compete.getBid());
        else return R.error("当前用户未出价!");
    }
}
