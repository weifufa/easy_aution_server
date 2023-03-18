package com.weifufa.easyaution.product.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
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
	 * 所属分类
	 */
	private Integer categoryId;
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
	 * 拍卖结束时间
	 */
	private Date auctionEndTime;
	/**
	 * 拍卖开始时间
	 */
	private Date auctionStartTime;
	/**
	 * 发布时间
	 */
	@TableField(fill= FieldFill.INSERT)
	private Date createTime;
	/**
	 * 修改时间
	 */
	@TableField(fill= FieldFill.INSERT_UPDATE)
	private Date updateTime;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 图片
	 */
	private String images;

}
