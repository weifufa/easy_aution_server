package com.weifufa.easyaution.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.weifufa.common.valid.AddGroup;
import com.weifufa.common.valid.UpdateGroup;
import com.weifufa.common.valid.UpdateStatusGroup;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * 商品三级分类
 * 
 * @author weifufa
 * @email wff66@foxmail.com
 * @date 2023-01-31 21:28:27
 */
@Data
@TableName("pms_category")
public class CategoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 分类id
	 */
	@NotNull(message = "修改必须指定分类Id",groups = {UpdateGroup.class,UpdateStatusGroup.class})
	@Null(message = "新增不能指定Id")
	@TableId
	private Long catId;
	/**
	 * 分类名称
	 */
	/**
	 * 品牌名
	 */
	@NotBlank(message = "分类名必须提交",groups = {AddGroup.class,UpdateGroup.class})
	private String name;
	/**
	 * 父分类id
	 */
	private Long parentCid;
	/**
	 * 层级
	 */
	private Integer catLevel;
	/**
	 * 是否显示[0-不显示，1显示]
	 */
	@NotNull(groups = {AddGroup.class, UpdateStatusGroup.class})
	@TableLogic//(value = "1",delval = "0")
	private Integer showStatus;
	/**
	 * 排序
	 */
    @NotNull
	@Min(value=0,message = "排序必须大于等于0",groups = {AddGroup.class, UpdateGroup.class})
	private Integer sort;
	/**
	 * 图标地址
	 */
	private String icon;
	/**
	 * 计量单位
	 */
	private String productUnit;
	/**
	 * 商品数量
	 */
	private Integer productCount;

}
