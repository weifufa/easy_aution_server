package com.weifufa.easyaution.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.models.auth.In;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("pms_compete")
public class CompeteEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Integer Id;
    /**
     * 拍品id
     */
    private Integer auctionId;
    /**
     * 出价者id
     */
    private Integer memberId;
    /**
     * 用户手机号
     */
    private String userPhone;
    /**
     * 出价后的价格
     */
    private Integer bid;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;

}