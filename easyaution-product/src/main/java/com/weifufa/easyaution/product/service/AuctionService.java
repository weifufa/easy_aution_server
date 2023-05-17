package com.weifufa.easyaution.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weifufa.common.utils.PageUtils;
import com.weifufa.easyaution.product.entity.AuctionEntity;
import com.weifufa.easyaution.product.vo.QueryParam;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author weifufa
 * @email wff66@foxmail.com
 * @date 2023-02-02 23:44:51
 */
public interface AuctionService extends IService<AuctionEntity> {

    PageUtils queryPage(Map<String, Object> params);

    //查询未开拍的拍品
    List<AuctionEntity> getNotStartAuction(QueryParam param);
}

