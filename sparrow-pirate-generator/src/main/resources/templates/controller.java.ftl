package ${package.Controller};


import org.springframework.web.bind.annotation.RequestMapping;

<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

import ${package.Service}.${table.serviceName};
import ${package.Entity}.${entity};
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
 * ${table.comment!} 前端控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Slf4j
@Api(value = "<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>",description = "${entity}表 接口,负责人: ${author}")
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping(value = "<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>

    <#assign servName="${table.serviceName ? substring(1)}">
    <#assign servicrVariableName="${servName ? uncap_first}">
    <#assign entityVarialeName="${entity ? uncap_first}">

    @Autowired
    private ${table.serviceName} ${servicrVariableName};

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "根据id获取${entity}",notes = "根据id获取${entity}")
    public RespEntity<${entity}> getById(@PathVariable Long id){
        return RespEntity.ok().setRespData(${servicrVariableName}.getById(id));
    }

    @GetMapping
    @ApiOperation(value = "获取${entity}列表",notes = "获取${entity}列表")
    public RespEntity<List<${entity}>> getList(){
        return RespEntity.ok().setRespData(${servicrVariableName}.list());
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "pageNum", value = "起始页", required = true, paramType = "query"),
        @ApiImplicitParam(name = "pageSize", value = "分页Size", required = true, paramType = "query")
    })
    @ApiOperation(value = "条件分页查询",notes = "条件分页查询")
    @GetMapping(value = "/getPageInfo")
    public RespEntity<PageInfo<${entity}>> getPageInfo(PagingRequest pagingRequest){
        return RespEntity.ok().setRespData(${servicrVariableName}.getPageInfo(pagingRequest,null));
    }

    @ApiOperation(value = "保存${entity}",notes = "保存${entity}")
    @PostMapping
    public RespEntity save(@Valid @ModelAttribute ${entity} ${entityVarialeName}, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return RespEntity.paramsError(bindingResult.getFieldError().getDefaultMessage());
        }
        return ${servicrVariableName}.save(${entityVarialeName})?RespEntity.ok():RespEntity.error();
    }

    @ApiOperation(value = "修改${entity}",notes = "修改${entity}")
    @PutMapping
    public RespEntity update(@Valid @RequestBody ${entity} ${entityVarialeName}, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return RespEntity.paramsError(bindingResult.getFieldError().getDefaultMessage());
        }
        return ${servicrVariableName}.saveOrUpdate(${entityVarialeName})?RespEntity.ok():RespEntity.error();
    }

    @ApiOperation(value = "根据ID删除记录行",notes = "根据ID删除记录行")
    @DeleteMapping(value = "/{id}")
    public RespEntity delete(@PathVariable Long id){
        return ${servicrVariableName}.removeById(id)?RespEntity.ok():RespEntity.error();
    }
}
</#if>
