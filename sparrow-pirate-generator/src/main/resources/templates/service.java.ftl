package ${package.Service};

import ${package.Entity}.${entity};
import ${superServiceClassPackage};

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.corsair.sparrow.pirate.core.base.PagingRequest;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 * ${table.comment!} 服务类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {

  <#if kotlin>
   <#else >
    /**
    * 默认带条件参数分页
    * @param pagingRequest
    * @param queryWrapper
    * @return
    */
    PageInfo<${entity}> getPageInfo(PagingRequest pagingRequest, Wrapper<${entity}> queryWrapper);
  </#if>

}
</#if>
