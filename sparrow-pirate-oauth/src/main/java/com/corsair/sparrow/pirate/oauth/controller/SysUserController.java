package com.corsair.sparrow.pirate.oauth.controller;


import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.corsair.sparrow.pirate.core.base.BaseController;

import com.corsair.sparrow.pirate.oauth.service.ISysUserService;
import com.corsair.sparrow.pirate.oauth.domain.bean.SysUser;
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
 * 用户表 前端控制器
 * </p>
 *
 * @author jack
 * @since 2019-03-22
 */
@Slf4j
@Api(value = "/sys-user",description = "SysUser表 接口,负责人: jack")
@RestController
@RequestMapping(value = "/sys-user")
public class SysUserController extends BaseController {


    @Autowired
    private ISysUserService sysUserService;

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "根据id获取SysUser",notes = "根据id获取SysUser")
    public RespEntity<SysUser> getById(@PathVariable Long id){
        return RespEntity.ok().setRespData(sysUserService.getById(id));
    }

    @GetMapping
    @ApiOperation(value = "获取SysUser列表",notes = "获取SysUser列表")
    public RespEntity<List<SysUser>> getList(){
        return RespEntity.ok().setRespData(sysUserService.list());
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "pageNum", value = "起始页", required = true, paramType = "query"),
        @ApiImplicitParam(name = "pageSize", value = "分页Size", required = true, paramType = "query")
    })
    @ApiOperation(value = "条件分页查询",notes = "条件分页查询")
    @GetMapping(value = "/getPageInfo")
    public RespEntity<PageInfo<SysUser>> getPageInfo(PagingRequest pagingRequest){
        return RespEntity.ok().setRespData(sysUserService.getPageInfo(pagingRequest,null));
    }

    @ApiOperation(value = "保存SysUser",notes = "保存SysUser")
    @PostMapping
    public RespEntity save(@Valid @ModelAttribute SysUser sysUser, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return RespEntity.paramsError(bindingResult.getFieldError().getDefaultMessage());
        }
        return sysUserService.save(sysUser)?RespEntity.ok():RespEntity.error();
    }

    @ApiOperation(value = "修改SysUser",notes = "修改SysUser")
    @PutMapping
    public RespEntity update(@Valid @RequestBody SysUser sysUser, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return RespEntity.paramsError(bindingResult.getFieldError().getDefaultMessage());
        }
        return sysUserService.saveOrUpdate(sysUser)?RespEntity.ok():RespEntity.error();
    }

    @ApiOperation(value = "根据ID删除记录行",notes = "根据ID删除记录行")
    @DeleteMapping(value = "/{id}")
    public RespEntity delete(@PathVariable Long id){
        return sysUserService.removeById(id)?RespEntity.ok():RespEntity.error();
    }
}
