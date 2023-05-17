package com.weifufa.easyaution.product.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class QueryParam {
    private static final long serialVersionUID = 1L;

    /** 搜索值 */
    private String searchValue;

    /** 创建者 */
    private String createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新者 */
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /** 备注 */
    private String remark;

    /** 数据权限 */
    private String dataScope;

    /** 开始时间 */
    @JsonIgnore
    private String beginTime;

    /** 结束时间 */
    @JsonIgnore
    private String endTime;

    /** 请求参数 */
    private Map<String, Object> params;

    /** 当前页 */
    private int pageNum;

    /** 每页数目 */
    private int pageSize;

    /** 是否分页 */
    private boolean pageSizeZero;
}