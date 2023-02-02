package com.weifufa.easyaution.product.service.impl;

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


@Service("auctionService")
public class AuctionServiceImpl extends ServiceImpl<AuctionDao, AuctionEntity> implements AuctionService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AuctionEntity> page = this.page(
                new Query<AuctionEntity>().getPage(params),
                new QueryWrapper<AuctionEntity>()
        );

        return new PageUtils(page);
    }

}