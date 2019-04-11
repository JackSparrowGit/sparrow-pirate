package com.corsair.sparrow.pirate.oauth.controller;


import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.corsair.sparrow.pirate.core.base.BaseController;

import com.corsair.sparrow.pirate.oauth.service.IOauthClientDetailsService;
import com.corsair.sparrow.pirate.oauth.domain.bean.OauthClientDetails;
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
 * oauth鉴权表 前端控制器
 * </p>
 *
 * @author jack
 * @since 2019-03-22
 */
@Slf4j
@Api(value = "/oauth-client-details",description = "OauthClientDetails表 接口,负责人: jack")
@RestController
@RequestMapping(value = "/oauth-client-details")
public class OauthClientDetailsController extends BaseController {


    @Autowired
    private IOauthClientDetailsService oauthClientDetailsService;

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "根据id获取OauthClientDetails",notes = "根据id获取OauthClientDetails")
    public RespEntity<OauthClientDetails> getById(@PathVariable Long id){
        return RespEntity.ok().setRespData(oauthClientDetailsService.getById(id));
    }

    @GetMapping
    @ApiOperation(value = "获取OauthClientDetails列表",notes = "获取OauthClientDetails列表")
    public RespEntity<List<OauthClientDetails>> getList(){
        return RespEntity.ok().setRespData(oauthClientDetailsService.list());
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "pageNum", value = "起始页", required = true, paramType = "query"),
        @ApiImplicitParam(name = "pageSize", value = "分页Size", required = true, paramType = "query")
    })
    @ApiOperation(value = "条件分页查询",notes = "条件分页查询")
    @GetMapping(value = "/getPageInfo")
    public RespEntity<PageInfo<OauthClientDetails>> getPageInfo(PagingRequest pagingRequest){
        return RespEntity.ok().setRespData(oauthClientDetailsService.getPageInfo(pagingRequest,null));
    }

    @ApiOperation(value = "保存OauthClientDetails",notes = "保存OauthClientDetails")
    @PostMapping
    public RespEntity save(@Valid @ModelAttribute OauthClientDetails oauthClientDetails, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return RespEntity.paramsError(bindingResult.getFieldError().getDefaultMessage());
        }
        return oauthClientDetailsService.save(oauthClientDetails)?RespEntity.ok():RespEntity.error();
    }

    @ApiOperation(value = "修改OauthClientDetails",notes = "修改OauthClientDetails")
    @PutMapping
    public RespEntity update(@Valid @RequestBody OauthClientDetails oauthClientDetails, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return RespEntity.paramsError(bindingResult.getFieldError().getDefaultMessage());
        }
        return oauthClientDetailsService.saveOrUpdate(oauthClientDetails)?RespEntity.ok():RespEntity.error();
    }

    @ApiOperation(value = "根据ID删除记录行",notes = "根据ID删除记录行")
    @DeleteMapping(value = "/{id}")
    public RespEntity delete(@PathVariable Long id){
        return oauthClientDetailsService.removeById(id)?RespEntity.ok():RespEntity.error();
    }
}
