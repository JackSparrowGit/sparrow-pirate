package com.corsair.sparrow.pirate.oauth.controller;


import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.corsair.sparrow.pirate.core.base.BaseController;

import com.corsair.sparrow.pirate.oauth.service.ISysRoleService;
import com.corsair.sparrow.pirate.oauth.domain.bean.SysRole;
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
 * 角色表 前端控制器
 * </p>
 *
 * @author jack
 * @since 2019-03-22
 */
@Slf4j
@Api(value = "/sys-role",description = "SysRole表 接口,负责人: jack")
@RestController
@RequestMapping(value = "/sys-role")
public class SysRoleController extends BaseController {


    @Autowired
    private ISysRoleService sysRoleService;

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "根据id获取SysRole",notes = "根据id获取SysRole")
    public RespEntity<SysRole> getById(@PathVariable Long id){
        return RespEntity.ok().setRespData(sysRoleService.getById(id));
    }

    @GetMapping
    @ApiOperation(value = "获取SysRole列表",notes = "获取SysRole列表")
    public RespEntity<List<SysRole>> getList(){
        return RespEntity.ok().setRespData(sysRoleService.list());
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "pageNum", value = "起始页", required = true, paramType = "query"),
        @ApiImplicitParam(name = "pageSize", value = "分页Size", required = true, paramType = "query")
    })
    @ApiOperation(value = "条件分页查询",notes = "条件分页查询")
    @GetMapping(value = "/getPageInfo")
    public RespEntity<PageInfo<SysRole>> getPageInfo(PagingRequest pagingRequest){
        return RespEntity.ok().setRespData(sysRoleService.getPageInfo(pagingRequest,null));
    }

    @ApiOperation(value = "保存SysRole",notes = "保存SysRole")
    @PostMapping
    public RespEntity save(@Valid @ModelAttribute SysRole sysRole, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return RespEntity.paramsError(bindingResult.getFieldError().getDefaultMessage());
        }
        return sysRoleService.save(sysRole)?RespEntity.ok():RespEntity.error();
    }

    @ApiOperation(value = "修改SysRole",notes = "修改SysRole")
    @PutMapping
    public RespEntity update(@Valid @RequestBody SysRole sysRole, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return RespEntity.paramsError(bindingResult.getFieldError().getDefaultMessage());
        }
        return sysRoleService.saveOrUpdate(sysRole)?RespEntity.ok():RespEntity.error();
    }

    @ApiOperation(value = "根据ID删除记录行",notes = "根据ID删除记录行")
    @DeleteMapping(value = "/{id}")
    public RespEntity delete(@PathVariable Long id){
        return sysRoleService.removeById(id)?RespEntity.ok():RespEntity.error();
    }
}
