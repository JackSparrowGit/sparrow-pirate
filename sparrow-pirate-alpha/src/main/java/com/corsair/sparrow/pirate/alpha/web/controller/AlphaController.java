package com.corsair.sparrow.pirate.alpha.web.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.corsair.sparrow.pirate.alpha.domain.bean.Alpha;
import com.corsair.sparrow.pirate.alpha.service.IAlphaService;
import com.corsair.sparrow.pirate.core.base.BaseController;
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
 * Alpha表 前端控制器
 * </p>
 *
 * @author jack
 * @since 2019-03-19
 */
@Slf4j
@Api(value = "/alpha",description = "XX表 接口")
@RestController
@RequestMapping(value = "/alpha")
public class AlphaController extends BaseController {

    @Autowired
    private IAlphaService alphaService;

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "根据id获取alpha",notes = "根据id获取alpha")
    public RespEntity<Alpha> getById(@PathVariable Long id){
        return RespEntity.ok().setRespData(alphaService.getById(id));
    }

    @GetMapping
    @ApiOperation(value = "获取alpha列表",notes = "获取alpha列表")
    public RespEntity<List<Alpha>> getList(){
        return RespEntity.ok().setRespData(alphaService.list());
    }

    /**
     * mybatisPlus默认分页功能较弱
     * @param pagingRequest
     * @return
     */
    @GetMapping(value = "/page")
    public RespEntity<IPage<Alpha>> getPage(PagingRequest pagingRequest){
        return RespEntity.ok().setRespData(alphaService.page(new Page<>(pagingRequest.getPageNum(), pagingRequest.getPageSize())));
    }

    /**
     * 建议使用pagehelp分页，新版本无需再配置与mybatisPlus适配器
     * @param pagingRequest
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "起始页", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "分页Size", required = true, paramType = "query")
    })
    @ApiOperation(value = "条件分页查询",notes = "条件分页查询")
    @GetMapping(value = "/getPageInfo")
    public RespEntity<PageInfo<Alpha>> getPageInfo(PagingRequest pagingRequest){
        return RespEntity.ok().setRespData(alphaService.getPageInfo(pagingRequest,null));
    }

    /**
     * 以下为实例，需要定制生成后修改
     * @param alpha
     * @param bindingResult
     * @return
     */
    @ApiOperation(value = "保存",notes = "保存")
    @PostMapping
    public RespEntity save(@Valid @ModelAttribute Alpha alpha, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return RespEntity.paramsError(bindingResult.getFieldError().getDefaultMessage());
        }
        return alphaService.save(alpha)?RespEntity.ok():RespEntity.error();
    }

    @ApiOperation(value = "修改",notes = "修改")
    @PutMapping
    public RespEntity update(@Valid @RequestBody Alpha alpha, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return RespEntity.paramsError(bindingResult.getFieldError().getDefaultMessage());
        }
        return alphaService.saveOrUpdate(alpha)?RespEntity.ok():RespEntity.error();
    }

    @ApiOperation(value = "根据ID删除记录行",notes = "根据ID删除记录行")
    @DeleteMapping(value = "/{id}")
    public RespEntity delete(@PathVariable Long id){
        return alphaService.removeById(id)?RespEntity.ok():RespEntity.error();
    }
}
