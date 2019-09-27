package com.kylin.aop.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: zhebin
 * @Date: 2019/9/26 9:06
 */
@Data
public class OperationLog implements Serializable {

    private static final long serialVersionUID = 1L;
    private  String requesturl;
    private String methodname;
    private String method;
    private  String user;
    private  String context;
    private String date;
}
