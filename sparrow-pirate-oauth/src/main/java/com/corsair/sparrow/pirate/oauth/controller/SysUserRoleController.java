package com.corsair.sparrow.pirate.oauth.controller;


import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.corsair.sparrow.pirate.core.base.BaseController;

import com.corsair.sparrow.pirate.oauth.service.ISysUserRoleService;
import com.corsair.sparrow.pirate.oauth.domain.bean.SysUserRole;
import com.corsair.sparrow.pirate.core.base.PagingRequest;
import com.corsair.sparrow.pirate.core.global.RespEntity;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 用户角色关联表 前端控制器
 * </p>
 *
 * @author jack
 * @since 2019-03-22
 */
@Slf4j
@Api(value = "/sys-user-role",description = "SysUserRole表 接口,负责人: jack")
@RestController
@RequestMapping(value = "/sys-user-role")
public class SysUserRoleController extends BaseController {


    @Autowired
    private ISysUserRoleService sysUserRoleService;

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "根据id获取SysUserRole",notes = "根据id获取SysUserRole")
    public RespEntity<SysUserRole> getById(@PathVariable Long id){
        return RespEntity.ok().setRespData(sysUserRoleService.getById(id));
    }

    @GetMapping
    @ApiOperation(value = "获取SysUserRole列表",notes = "获取SysUserRole列表")
    public RespEntity<List<SysUserRole>> getList(){
        return RespEntity.ok().setRespData(sysUserRoleService.list());
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "pageNum", value = "起始页", required = true, paramType = "query"),
        @ApiImplicitParam(name = "pageSize", value = "分页Size", required = true, paramType = "query")
    })
    @ApiOperation(value = "条件分页查询",notes = "条件分页查询")
    @GetMapping(value = "/getPageInfo")
    public RespEntity<PageInfo<SysUserRole>> getPageInfo(PagingRequest pagingRequest){
        return RespEntity.ok().setRespData(sysUserRoleService.getPageInfo(pagingRequest,null));
    }

    @ApiOperation(value = "保存SysUserRole",notes = "保存SysUserRole")
    @PostMapping
    public RespEntity save(@Valid @ModelAttribute SysUserRole sysUserRole, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return RespEntity.paramsError(bindingResult.getFieldError().getDefaultMessage());
        }
        return sysUserRoleService.save(sysUserRole)?RespEntity.ok():RespEntity.error();
    }

    @ApiOperation(value = "修改SysUserRole",notes = "修改SysUserRole")
    @PutMapping
    public RespEntity update(@Valid @RequestBody SysUserRole sysUserRole, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return RespEntity.paramsError(bindingResult.getFieldError().getDefaultMessage());
        }
        return sysUserRoleService.saveOrUpdate(sysUserRole)?RespEntity.ok():RespEntity.error();
    }

    @ApiOperation(value = "根据ID删除记录行",notes = "根据ID删除记录行")
    @DeleteMapping(value = "/{id}")
    public RespEntity delete(@PathVariable Long id){
        return sysUserRoleService.removeById(id)?RespEntity.ok():RespEntity.error();
    }
}
