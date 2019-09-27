package com.kylin.upms.biz.service;

import com.baomidou.mybatisplus.service.IService;
import com.kylin.upms.biz.entity.User;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Mht
 * @since 2019-09-15
 */
public interface IUserService extends IService<User> {

    AggregatedPage<User> selectuser(Integer pageNum ,String key);
}
