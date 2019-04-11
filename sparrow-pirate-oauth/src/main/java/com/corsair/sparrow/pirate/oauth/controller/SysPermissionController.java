package com.corsair.sparrow.pirate.oauth.controller;


import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.corsair.sparrow.pirate.core.base.BaseController;

import com.corsair.sparrow.pirate.oauth.service.ISysPermissionService;
import com.corsair.sparrow.pirate.oauth.domain.bean.SysPermission;
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
 * 权限表 前端控制器
 * </p>
 *
 * @author jack
 * @since 2019-03-22
 */
@Slf4j
@Api(value = "/sys-permission",description = "SysPermission表 接口,负责人: jack")
@RestController
@RequestMapping(value = "/sys-permission")
public class SysPermissionController extends BaseController {


    @Autowired
    private ISysPermissionService sysPermissionService;

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "根据id获取SysPermission",notes = "根据id获取SysPermission")
    public RespEntity<SysPermission> getById(@PathVariable Long id){
        return RespEntity.ok().setRespData(sysPermissionService.getById(id));
    }

    @GetMapping
    @ApiOperation(value = "获取SysPermission列表",notes = "获取SysPermission列表")
    public RespEntity<List<SysPermission>> getList(){
        return RespEntity.ok().setRespData(sysPermissionService.list());
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "pageNum", value = "起始页", required = true, paramType = "query"),
        @ApiImplicitParam(name = "pageSize", value = "分页Size", required = true, paramType = "query")
    })
    @ApiOperation(value = "条件分页查询",notes = "条件分页查询")
    @GetMapping(value = "/getPageInfo")
    public RespEntity<PageInfo<SysPermission>> getPageInfo(PagingRequest pagingRequest){
        return RespEntity.ok().setRespData(sysPermissionService.getPageInfo(pagingRequest,null));
    }

    @ApiOperation(value = "保存SysPermission",notes = "保存SysPermission")
    @PostMapping
    public RespEntity save(@Valid @ModelAttribute SysPermission sysPermission, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return RespEntity.paramsError(bindingResult.getFieldError().getDefaultMessage());
        }
        return sysPermissionService.save(sysPermission)?RespEntity.ok():RespEntity.error();
    }

    @ApiOperation(value = "修改SysPermission",notes = "修改SysPermission")
    @PutMapping
    public RespEntity update(@Valid @RequestBody SysPermission sysPermission, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return RespEntity.paramsError(bindingResult.getFieldError().getDefaultMessage());
        }
        return sysPermissionService.saveOrUpdate(sysPermission)?RespEntity.ok():RespEntity.error();
    }

    @ApiOperation(value = "根据ID删除记录行",notes = "根据ID删除记录行")
    @DeleteMapping(value = "/{id}")
    public RespEntity delete(@PathVariable Long id){
        return sysPermissionService.removeById(id)?RespEntity.ok():RespEntity.error();
    }
}
