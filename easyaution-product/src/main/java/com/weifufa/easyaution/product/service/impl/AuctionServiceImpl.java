package com.weifufa.easyaution.product.service.impl;

import com.weifufa.easyaution.product.entity.CategoryEntity;
import org.springframework.stereotype.Service;
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

}