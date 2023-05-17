package com.weifufa.easyaution.product.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.weifufa.easyaution.product.entity.CompeteEntity;
import com.weifufa.easyaution.product.entity.OrderEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * 
 * @author weifufa
 * @email wff66@foxmail.com
 * @date 2023-03-26 17:03:16
 */
@Mapper
public interface CompeteDao extends BaseMapper<CompeteEntity> {

    /**
     * 根据拍品id和用户手机号
     * @return
     */
    CompeteEntity selectByAuctionIdAndUserPhone(CompeteEntity competeEntity);
}
