package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.corsair.sparrow.pirate.core.base.PagingRequest;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 * ${table.comment!} 服务实现类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Service
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

}
<#else>
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {

   <#if kotlin>
    <#else >
    @Override
    public PageInfo<${entity}> getPageInfo(PagingRequest pagingRequest, Wrapper<${entity}> queryWrapper) {
      PageHelper.startPage(pagingRequest.getPageNum(),pagingRequest.getPageSize());
      return new PageInfo<>(super.list(queryWrapper));
    }
   </#if>
}
</#if>
