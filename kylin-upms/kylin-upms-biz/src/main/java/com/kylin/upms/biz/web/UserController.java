package com.kylin.upms.biz.web;




import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.kylin.aop.annotation.OperationLogDetail;
import com.kylin.aop.enums.OperationType;
import com.kylin.aop.enums.OperationUnit;
import com.kylin.upms.biz.dto.UserDto;
import com.kylin.upms.biz.entity.Role;
import com.kylin.upms.biz.entity.User;
import com.kylin.upms.biz.entity.UserRole;
import com.kylin.upms.biz.service.IRoleService;
import com.kylin.upms.biz.service.IUserRoleService;
import com.kylin.upms.biz.service.IUserService;
import com.kylin.upms.biz.vo.ResEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Mht
 * @since 2019-09-15
 */
@Api("操作用户")
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    IUserService iUserService;
    @Autowired
    IRoleService iRoleService;
    @Autowired
    IUserRoleService iUserRoleService;


    Logger logger= LoggerFactory.getLogger(this.getClass());

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @ApiOperation("根据查询条件分页查询用户列表")
    @RequestMapping(method = RequestMethod.GET,value = "/page")
    @OperationLogDetail(operationType = OperationType.UPDATE,operationUnit = OperationUnit.USER)
    public ResEntity get(UserDto userDto){
        Page<User> page = new Page<User>(userDto.getPageNum(),userDto.getPageSize());
        User user  = new User();
        BeanUtils.copyProperties(userDto,user);
        EntityWrapper entityWrapper = new EntityWrapper(user);
        entityWrapper.like("username",user.getUsername());
        Page page1 = iUserService.selectPage(page, entityWrapper);
        return ResEntity.ok(page1);
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ResEntity add(UserDto userDto){
        User user  = new User();
        BeanUtils.copyProperties(userDto,user);
        //加密密码
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        boolean b = iUserService.insertOrUpdate(user);
        if (b){ return ResEntity.ok("添加成功"); }
        return ResEntity.error("添加失败");
    }

    @OperationLogDetail(operationType = OperationType.UPDATE,operationUnit = OperationUnit.USER)
    @RequestMapping(method = RequestMethod.DELETE)
    public ResEntity del(Integer id){
        boolean b = iUserService.deleteById(id);
        if (b){ return ResEntity.ok("删除成功"); }
        return ResEntity.error("删除失败");
    }

    //获取所有角色的列表
    @OperationLogDetail(operationType = OperationType.UPDATE,operationUnit = OperationUnit.USER)
    @RequestMapping(method = RequestMethod.GET,value = "/getRolesListByUsername")
    public ResEntity get(String username){
        List<Role> roleslist = iRoleService.getRoleByUserName(username);
        System.out.println(roleslist);
        return ResEntity.ok(roleslist);
    }

    @OperationLogDetail(operationType = OperationType.UPDATE,operationUnit = OperationUnit.USER)
    @RequestMapping(method = RequestMethod.GET,value = "/getByUsername")
    public boolean getUser(UserDto userDto){
        User user  = new User();
        BeanUtils.copyProperties(userDto,user);
        EntityWrapper entityWrapper = new EntityWrapper(user);
        entityWrapper.like("username", user.getUsername());
        User selectOne = iUserService.selectOne(entityWrapper);
        System.out.println("user:=====>>"+selectOne);
        if (selectOne==null){return true;}
        return false;
    }
    //获取单独用户的角色
    @RequestMapping(method = RequestMethod.GET,value = "/getUserRoleById")
    public ResEntity roleById(Integer id){
        EntityWrapper<UserRole> wrapper = new EntityWrapper<>();
        wrapper.eq("uid",id);
        List<UserRole> userRoles = iUserRoleService.selectList(wrapper);
        List<Integer> list=new ArrayList<>();
        userRoles.forEach((userRole)->list.add(userRole.getRid()));
        logger.info("单个用户所有的角色"+list);
        return  ResEntity.ok(list);
    }

    //用户赋予角色和角色的修改
    @RequestMapping(method = RequestMethod.GET,value = "/changeUserRoles")
    public ResEntity add(Integer id,Integer rids[]){

        logger.info("更改权限的用户id--》"+id);
        logger.info("需要改为的目标id--》"+rids);

        EntityWrapper<UserRole> wrapper = new EntityWrapper<>();
        wrapper.eq("uid",id);
        iUserRoleService.delete(wrapper);


        List<UserRole> userRoleList=new ArrayList<>();
       Arrays.asList(rids).forEach((rid)->userRoleList.add(new UserRole(id,rid)));
        boolean b = iUserRoleService.insertBatch(userRoleList);
        if (b){return ResEntity.ok("修改成功");}
        return  ResEntity.error("修改失败");
    }


    //高亮
    @CrossOrigin(origins = {"http://localhost:8080"})
    @RequestMapping(method = RequestMethod.GET,value = "/page1")
    public ResEntity getTwo(UserDto userDto){
        AggregatedPage<User> selectuser = iUserService.selectuser(userDto.getPageNum(), userDto.getUsername());
        System.out.println("77777777"+selectuser.getNumber()+"======"+selectuser.getTotalElements());
        System.out.println(selectuser);
        return ResEntity.ok("查询成功",selectuser);
    }
}
