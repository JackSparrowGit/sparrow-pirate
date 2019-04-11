package com.corsair.sparrow.pirate.oauth.controller;


import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.corsair.sparrow.pirate.core.base.BaseController;

import com.corsair.sparrow.pirate.oauth.service.ISysUserGroupService;
import com.corsair.sparrow.pirate.oauth.domain.bean.SysUserGroup;
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
 * 用户-用户组关联表 前端控制器
 * </p>
 *
 * @author jack
 * @since 2019-03-22
 */
@Slf4j
@Api(value = "/sys-user-group",description = "SysUserGroup表 接口,负责人: jack")
@RestController
@RequestMapping(value = "/sys-user-group")
public class SysUserGroupController extends BaseController {


    @Autowired
    private ISysUserGroupService sysUserGroupService;

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "根据id获取SysUserGroup",notes = "根据id获取SysUserGroup")
    public RespEntity<SysUserGroup> getById(@PathVariable Long id){
        return RespEntity.ok().setRespData(sysUserGroupService.getById(id));
    }

    @GetMapping
    @ApiOperation(value = "获取SysUserGroup列表",notes = "获取SysUserGroup列表")
    public RespEntity<List<SysUserGroup>> getList(){
        return RespEntity.ok().setRespData(sysUserGroupService.list());
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "pageNum", value = "起始页", required = true, paramType = "query"),
        @ApiImplicitParam(name = "pageSize", value = "分页Size", required = true, paramType = "query")
    })
    @ApiOperation(value = "条件分页查询",notes = "条件分页查询")
    @GetMapping(value = "/getPageInfo")
    public RespEntity<PageInfo<SysUserGroup>> getPageInfo(PagingRequest pagingRequest){
        return RespEntity.ok().setRespData(sysUserGroupService.getPageInfo(pagingRequest,null));
    }

    @ApiOperation(value = "保存SysUserGroup",notes = "保存SysUserGroup")
    @PostMapping
    public RespEntity save(@Valid @ModelAttribute SysUserGroup sysUserGroup, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return RespEntity.paramsError(bindingResult.getFieldError().getDefaultMessage());
        }
        return sysUserGroupService.save(sysUserGroup)?RespEntity.ok():RespEntity.error();
    }

    @ApiOperation(value = "修改SysUserGroup",notes = "修改SysUserGroup")
    @PutMapping
    public RespEntity update(@Valid @RequestBody SysUserGroup sysUserGroup, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return RespEntity.paramsError(bindingResult.getFieldError().getDefaultMessage());
        }
        return sysUserGroupService.saveOrUpdate(sysUserGroup)?RespEntity.ok():RespEntity.error();
    }

    @ApiOperation(value = "根据ID删除记录行",notes = "根据ID删除记录行")
    @DeleteMapping(value = "/{id}")
    public RespEntity delete(@PathVariable Long id){
        return sysUserGroupService.removeById(id)?RespEntity.ok():RespEntity.error();
    }
}
