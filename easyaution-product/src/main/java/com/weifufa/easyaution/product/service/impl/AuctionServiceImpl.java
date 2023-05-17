package com.weifufa.easyaution.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.weifufa.easyaution.product.entity.CategoryEntity;
import com.weifufa.easyaution.product.vo.QueryParam;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weifufa.common.utils.PageUtils;
import com.weifufa.common.utils.Query;

import com.weifufa.easyaution.product.dao.AuctionDao;
import com.weifufa.easyaution.product.entity.AuctionEntity;
import com.weifufa.easyaution.product.service.AuctionService;
import org.springframework.util.StringUtils;


@Service("auctionService")
public class AuctionServiceImpl extends ServiceImpl<AuctionDao, AuctionEntity> implements AuctionService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        //1.获取key
        String key=(String) params.get("key");
        QueryWrapper<AuctionEntity> queryWrapper=new QueryWrapper<>();
        if(!StringUtils.isEmpty(key))
        {
            queryWrapper.like("auction_name",key);
        }
        String state=(String)params.get("state");
        if(!StringUtils.isEmpty(state))
        {
            queryWrapper.eq("auction_state",Integer.parseInt(state));
        }
        IPage<AuctionEntity> page = this.page(
                new Query<AuctionEntity>().getPage(params),
                queryWrapper.orderByDesc("create_time")
        );
        return new PageUtils(page);
    }

    @Override
    public List<AuctionEntity> getNotStartAuction(QueryParam param) {
        QueryWrapper<AuctionEntity> queryWrapper=new QueryWrapper<>();
       queryWrapper.eq("auction_state", 0).orderByDesc("create_time");
        List<AuctionEntity> list = this.list(queryWrapper);
        for (AuctionEntity auctionEntity : list) {
            try {
                JSONArray jsonArray = JSON.parseArray(auctionEntity.getImages()); //将图片字符串转换成json数组
                String[] array = new String[jsonArray.size()];
                for (int i = 0; i < jsonArray.size(); i++) {
                    array[i] = jsonArray.getString(i);
                }
                auctionEntity.setImageArray(array);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

}