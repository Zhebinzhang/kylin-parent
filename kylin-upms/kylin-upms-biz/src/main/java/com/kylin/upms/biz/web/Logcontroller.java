package com.kylin.upms.biz.web;

import com.kylin.upms.biz.entity.OperationLog;
import com.kylin.upms.biz.mapper.LogMapper;
import com.kylin.upms.biz.vo.ResEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: zhebin
 * @Date: 2019/9/26 15:39
 */
@RestController
@RequestMapping("log")
@CrossOrigin
public class Logcontroller {

    @Autowired
    LogMapper logMapper;
    @RequestMapping(method = RequestMethod.GET,value = "/selectAlllog")
    public ResEntity get(Integer pageNum){
        PageRequest pageRequest=PageRequest.of(pageNum,5);
        Page<OperationLog> all = logMapper.findAll(pageRequest);
        System.out.println("ssssssssssssssssssssssssssssss"+all);
        return  ResEntity.ok(all);
    }
    @RequestMapping(value = "delById",method = RequestMethod.DELETE)
    public ResEntity delById(String id)
    {
        try {
            logMapper.deleteById(id);
            return ResEntity.ok("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return  ResEntity.error("删除失败");
        }
    }

}
