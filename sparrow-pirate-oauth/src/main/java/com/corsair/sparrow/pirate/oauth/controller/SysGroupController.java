package com.corsair.sparrow.pirate.oauth.controller;


import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.corsair.sparrow.pirate.core.base.BaseController;

import com.corsair.sparrow.pirate.oauth.service.ISysGroupService;
import com.corsair.sparrow.pirate.oauth.domain.bean.SysGroup;
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
 * 用户组表 前端控制器
 * </p>
 *
 * @author jack
 * @since 2019-03-22
 */
@Slf4j
@Api(value = "/sys-group",description = "SysGroup表 接口,负责人: jack")
@RestController
@RequestMapping(value = "/sys-group")
public class SysGroupController extends BaseController {


    @Autowired
    private ISysGroupService sysGroupService;

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "根据id获取SysGroup",notes = "根据id获取SysGroup")
    public RespEntity<SysGroup> getById(@PathVariable Long id){
        return RespEntity.ok().setRespData(sysGroupService.getById(id));
    }

    @GetMapping
    @ApiOperation(value = "获取SysGroup列表",notes = "获取SysGroup列表")
    public RespEntity<List<SysGroup>> getList(){
        return RespEntity.ok().setRespData(sysGroupService.list());
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "pageNum", value = "起始页", required = true, paramType = "query"),
        @ApiImplicitParam(name = "pageSize", value = "分页Size", required = true, paramType = "query")
    })
    @ApiOperation(value = "条件分页查询",notes = "条件分页查询")
    @GetMapping(value = "/getPageInfo")
    public RespEntity<PageInfo<SysGroup>> getPageInfo(PagingRequest pagingRequest){
        return RespEntity.ok().setRespData(sysGroupService.getPageInfo(pagingRequest,null));
    }

    @ApiOperation(value = "保存SysGroup",notes = "保存SysGroup")
    @PostMapping
    public RespEntity save(@Valid @ModelAttribute SysGroup sysGroup, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return RespEntity.paramsError(bindingResult.getFieldError().getDefaultMessage());
        }
        return sysGroupService.save(sysGroup)?RespEntity.ok():RespEntity.error();
    }

    @ApiOperation(value = "修改SysGroup",notes = "修改SysGroup")
    @PutMapping
    public RespEntity update(@Valid @RequestBody SysGroup sysGroup, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return RespEntity.paramsError(bindingResult.getFieldError().getDefaultMessage());
        }
        return sysGroupService.saveOrUpdate(sysGroup)?RespEntity.ok():RespEntity.error();
    }

    @ApiOperation(value = "根据ID删除记录行",notes = "根据ID删除记录行")
    @DeleteMapping(value = "/{id}")
    public RespEntity delete(@PathVariable Long id){
        return sysGroupService.removeById(id)?RespEntity.ok():RespEntity.error();
    }
}
