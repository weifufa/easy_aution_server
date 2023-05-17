package com.weifufa.easyaution.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author weifufa
 * @email wff66@foxmail.com
 * @date 2023-03-26 17:03:16
 */
@Data
@TableName("pms_order")
public class OrderEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 订单id
	 */
	@TableId
	private Integer orderId;
	/**
	 * 拍品id
	 */
	private Integer auctionId;
	/**
	 * 拍品名称
	 */
	private String auctionName;
	/**
	 * 卖家
	 */
	private String seller;
	/**
	 * 卖家
	 */
	private String buyer;
	/**
	 * 订单状态
	 */
	private Integer orderState;
	/**
	 * 成交价
	 * */
	private Float currentPrice;
	/**
	 * 创建时间
	 */
	private Date createTime;

}
