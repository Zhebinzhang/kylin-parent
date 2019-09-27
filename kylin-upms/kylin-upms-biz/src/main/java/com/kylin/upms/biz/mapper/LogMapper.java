package com.kylin.upms.biz.mapper;

import com.kylin.upms.biz.entity.OperationLog;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Author: zhebin
 * @Date: 2019/9/26 20:52
 */
public interface LogMapper extends MongoRepository<OperationLog,String> {
}
