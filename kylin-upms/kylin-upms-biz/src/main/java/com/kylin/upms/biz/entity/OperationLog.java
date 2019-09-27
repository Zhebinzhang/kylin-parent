package com.kylin.upms.biz.entity;

import lombok.*;

/**
 * @Author: zhebin
 * @Date: 2019/9/26 9:06
 */

@Data
public class OperationLog {

    private String _id;
    private String requesturl;
    private String methodname;
    private String method;
    private String user;
    private String context;
    private String date;
    private String _class;
}
