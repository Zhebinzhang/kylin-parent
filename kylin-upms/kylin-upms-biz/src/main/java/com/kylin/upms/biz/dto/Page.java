package com.kylin.upms.biz.dto;

import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class Page {

    @ApiParam(value = "当前页",required = true)
    private int pageNum;

    @ApiParam(value = "每页显示多少条",required = true)
    private int pageSize;
}
