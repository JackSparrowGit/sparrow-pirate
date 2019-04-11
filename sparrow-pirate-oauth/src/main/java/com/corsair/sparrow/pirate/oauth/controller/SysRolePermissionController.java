package com.corsair.sparrow.pirate.oauth.controller;


import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.corsair.sparrow.pirate.core.base.BaseController;

import com.corsair.sparrow.pirate.oauth.service.ISysRolePermissionService;
import com.corsair.sparrow.pirate.oauth.domain.bean.SysRolePermission;
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
 * 角色权限关联表 前端控制器
 * </p>
 *
 * @author jack
 * @since 2019-03-22
 */
@Slf4j
@Api(value = "/sys-role-permission",description = "SysRolePermission表 接口,负责人: jack")
@RestController
@RequestMapping(value = "/sys-role-permission")
public class SysRolePermissionController extends BaseController {


    @Autowired
    private ISysRolePermissionService sysRolePermissionService;

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "根据id获取SysRolePermission",notes = "根据id获取SysRolePermission")
    public RespEntity<SysRolePermission> getById(@PathVariable Long id){
        return RespEntity.ok().setRespData(sysRolePermissionService.getById(id));
    }

    @GetMapping
    @ApiOperation(value = "获取SysRolePermission列表",notes = "获取SysRolePermission列表")
    public RespEntity<List<SysRolePermission>> getList(){
        return RespEntity.ok().setRespData(sysRolePermissionService.list());
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "pageNum", value = "起始页", required = true, paramType = "query"),
        @ApiImplicitParam(name = "pageSize", value = "分页Size", required = true, paramType = "query")
    })
    @ApiOperation(value = "条件分页查询",notes = "条件分页查询")
    @GetMapping(value = "/getPageInfo")
    public RespEntity<PageInfo<SysRolePermission>> getPageInfo(PagingRequest pagingRequest){
        return RespEntity.ok().setRespData(sysRolePermissionService.getPageInfo(pagingRequest,null));
    }

    @ApiOperation(value = "保存SysRolePermission",notes = "保存SysRolePermission")
    @PostMapping
    public RespEntity save(@Valid @ModelAttribute SysRolePermission sysRolePermission, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return RespEntity.paramsError(bindingResult.getFieldError().getDefaultMessage());
        }
        return sysRolePermissionService.save(sysRolePermission)?RespEntity.ok():RespEntity.error();
    }

    @ApiOperation(value = "修改SysRolePermission",notes = "修改SysRolePermission")
    @PutMapping
    public RespEntity update(@Valid @RequestBody SysRolePermission sysRolePermission, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return RespEntity.paramsError(bindingResult.getFieldError().getDefaultMessage());
        }
        return sysRolePermissionService.saveOrUpdate(sysRolePermission)?RespEntity.ok():RespEntity.error();
    }

    @ApiOperation(value = "根据ID删除记录行",notes = "根据ID删除记录行")
    @DeleteMapping(value = "/{id}")
    public RespEntity delete(@PathVariable Long id){
        return sysRolePermissionService.removeById(id)?RespEntity.ok():RespEntity.error();
    }
}
