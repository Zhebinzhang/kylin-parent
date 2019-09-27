package com.kyllin.upms.search.dto;



import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Mht
 * @since 2019-09-15
 */
@Data
@Accessors(chain = true)
public class UserDto extends Page implements  Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * hrID
     */

    private Integer id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 住宅电话
     */
    private String telephone;

    /**
     * 联系地址
     */
    private String address;

    private Integer enabled;

    /**
     * 用户名
     */

    private String username;

    /**
     * 密码
     */
    private String password;

    private String userface;

    private String remark;




}
