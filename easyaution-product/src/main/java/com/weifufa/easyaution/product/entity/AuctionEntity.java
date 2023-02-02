package com.weifufa.easyaution.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author weifufa
 * @email wff66@foxmail.com
 * @date 2023-02-02 23:44:51
 */
@Data
@TableName("pms_auction")
public class AuctionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 拍品id
	 */
	@TableId
	private Integer auctionId;
	/**
	 * 拍卖品名称
	 */
	private String auctionName;
	/**
	 * 拍卖数量
	 */
	private String auctionAmount;
	/**
	 * 起拍价
	 */
	private BigDecimal startPrice;
	/**
	 * 竞拍次数
	 */
	private Integer auctionNumber;
	/**
	 * 最高价
	 */
	private BigDecimal maxPrice;
	/**
	 * 竞拍状态
	 */
	private Integer auctionState;
	/**
	 * 结束时间
	 */
	private Date endTime;
	/**
	 * 发布时间
	 */
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date updateTime;

}
