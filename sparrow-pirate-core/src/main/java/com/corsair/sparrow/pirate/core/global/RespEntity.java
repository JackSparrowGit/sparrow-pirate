package com.corsair.sparrow.pirate.core.global;

import com.corsair.sparrow.pirate.core.utils.JsonUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author jack
 */
@Setter
@Getter
@ToString
@Accessors(chain = true)
public class RespEntity<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 响应业务状态码
     */
    @ApiModelProperty(value = "响应业务状态码",position = 1)
    private volatile String respCode = RespCode.OK.getRespCode();
    /**
     * 响应提示信息
     */
    @ApiModelProperty(value = "响应业务状态码",position = 2)
    private volatile String respMsg = RespCode.OK.getRespMsg();
    /**
     * 响应内容
     */
    @ApiModelProperty(value = "响应业务内容",position = 3)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private volatile T respData;
    /**
     *
     */
    @ApiModelProperty(value = "服务器时间戳",position = 4)
    private volatile Long timestamp = System.currentTimeMillis();


    /**
     * 通配符
     */
    public static final String WILDCARD_ALL = "*";

    /**
     * json序列化时需要过滤字段
     */
    @JsonIgnore
    private volatile String filterFeilds = WILDCARD_ALL;

    /**
     * 自定义导出标题
     */
    @JsonIgnore
    private volatile LinkedHashMap<String,String> exportTitleMap;

    public RespEntity() {
    }

    public RespEntity(String respCode) {
        this.respCode = respCode;
    }

    public RespEntity(String respCode, String respMsg) {
        this.respCode = respCode;
        this.respMsg = respMsg;
    }

    public RespEntity(String respCode, String respMsg, T respData) {
        this.respCode = respCode;
        this.respMsg = respMsg;
        this.respData = respData;
    }

    public static RespEntity empty(){
        return new RespEntity();
    }

    public static RespEntity ok(){
        return new RespEntity(RespCode.OK.getRespCode(),RespCode.OK.getRespMsg());
    }

    public static<T> RespEntity<T> ok(final T t){
        return new RespEntity(RespCode.OK.getRespCode(),RespCode.OK.getRespMsg(),t);
    }

    /**
     * 标准成功
     * @param respCode
     * @param message
     * @return
     */
    public static RespEntity ok(final RespCode respCode,final String message){
        return new RespEntity(respCode.getRespCode(),message);
    }

    public static<T> RespEntity<T> ok(final RespCode respCode,final String message,T t){
        return new RespEntity(respCode.getRespCode(),message,t);
    }

    /**
     * 标准失败
     * @return
     */
    public static RespEntity error(){
        return new RespEntity(RespCode.ERROR.getRespCode(),RespCode.ERROR.getRespMsg());
    }


    public static<T> RespEntity<T> error(final RespCode respCode){
        return new RespEntity(RespCode.ERROR.getRespCode(),RespCode.ERROR.getRespMsg());
    }

    public static RespEntity error(final RespCode respCode,final String message){
        return new RespEntity(respCode.getRespCode(),message);
    }

    /**
     * badRequest
     * @return
     */
    public static RespEntity badRequest(){
        return new RespEntity(RespCode.BAD_REQUEST.getRespCode(),RespCode.BAD_REQUEST.getRespMsg());
    }

    public static<T> RespEntity<T> badRequest(final String message){
        return new RespEntity(RespCode.BAD_REQUEST.getRespCode(),message);
    }

    public static RespEntity badRequest(final RespCode respCode,final String message){
        return new RespEntity(respCode.getRespCode(),message);
    }

    /**
     * paramsError
     * @return
     */
    public static RespEntity paramsError(){
        return new RespEntity(RespCode.PARAMS_ERROR.getRespCode(),RespCode.PARAMS_ERROR.getRespMsg());
    }

    public static<T> RespEntity<T> paramsError(final String message){
        return new RespEntity(RespCode.PARAMS_ERROR.getRespCode(),message);
    }

    public static RespEntity paramsError(final RespCode respCode,final String message){
        return new RespEntity(respCode.getRespCode(),message);
    }

    /**
     * 标准服务器内部错误
     * @return
     */
    public static RespEntity internalServerError(){
        return new RespEntity(RespCode.INTERNAL_SERVER_ERROR.getRespCode(),RespCode.INTERNAL_SERVER_ERROR.getRespMsg());
    }

    public static<T> RespEntity<T> internalServerError(final String message){
        return new RespEntity(RespCode.INTERNAL_SERVER_ERROR.getRespCode(),message);
    }

    public static RespEntity internalServerError(final RespCode respCode,final String message){
        return new RespEntity(respCode.getRespCode(),message);
    }

    /**
     * 标准请求方式不支持错误
     * @return
     */
    public static RespEntity methodNotAllowd(){
        return new RespEntity(RespCode.METHOD_NOT_ALLOWED.getRespCode(),RespCode.METHOD_NOT_ALLOWED.getRespMsg());
    }

    public static<T> RespEntity<T> methodNotAllowd(final String message) {
        return new RespEntity(RespCode.METHOD_NOT_ALLOWED.getRespCode(),message);
    }

    public static RespEntity methodNotAllowd(final RespCode respCode,final String message){
        return new RespEntity(respCode.getRespCode(),message);
    }

    /**
     * forbidden的操作方式
     * @return
     */
    public static RespEntity forbidden(){
        return new RespEntity(RespCode.FORBIDDEN.getRespCode(),RespCode.FORBIDDEN.getRespMsg());
    }

    public static<T> RespEntity<T> forbidden(final String message) {
        return new RespEntity(RespCode.FORBIDDEN.getRespCode(),message);
    }

    public static RespEntity forbidden(final RespCode respCode,final String message){
        return new RespEntity(respCode.getRespCode(),message);
    }

    /**
     * 向RespData中添加 (key,value)
     * @param key
     * @param value
     * @return
     */
    public RespEntity add(final String key,final Object value){
        if(Objects.nonNull(this.respData)){
            this.respData = (T) new HashMap<String,Object>(0);
            Map<String,Object> content = (Map<String,Object>) this.respData;
            content.put(key, value);
            return this;
        }
        if(!(this.respData instanceof Map)){
            return this;
        }
        ((Map) this.respData).put(key,value);
        return this;
    }

    /**
     * 非分页-设置过滤字段
     * @param filterFeilds
     * @return
     */
    public RespEntity setFilterFields(final String filterFeilds){
        if( Objects.isNull(filterFeilds) || WILDCARD_ALL.equals(filterFeilds)){
            return this;
        }
        this.filterFeilds = WILDCARD_ALL + ",respData["+filterFeilds.trim()+"]";
        return this;
    }

    /**
     * 分页结果-设置过滤字段
     * @param filterFeilds
     * @return
     */
    public RespEntity setFilterFieldsForPaging(final String filterFeilds){
        if(Objects.isNull(filterFeilds) || WILDCARD_ALL.equals(filterFeilds)){
            return this;
        }
        this.filterFeilds = WILDCARD_ALL + ",respData[*,list["+filterFeilds.trim()+"]]";
        return this;
    }

    /**
     * 设置过滤字段并刷新
     * @param filterFeilds
     * @return
     */
    public RespEntity setFilterFieldsAndFlush(final String filterFeilds){
        return this.setFilterFieldsAndFlush(filterFeilds,false);
    }

    /**
     * 设置过滤字段并刷新
     * @param filterFeilds
     * @param isFilterPaging
     * @return
     */
    public RespEntity setFilterFieldsAndFlush(final String filterFeilds,boolean isFilterPaging){
        if(isFilterPaging){
            return this.setFilterFieldsForPaging(filterFeilds).filterFieldsFlush();
        }
        return this.setFilterFields(filterFeilds).filterFieldsFlush();
    }

    /**
     * 刷新过滤字段
     * @return
     */
    public RespEntity filterFieldsFlush(){
        return JsonUtils.jsonToType(this.toJson(),this.getClass());
    }

    /**
     * 转化成json
     * @return
     */
    public String toJson(){
        if(this.isNotFieldsFilter()){
            return JsonUtils.toJson(this);
        }
        return JsonUtils.toFilterJson(this,this.getFilterFeilds());
    }

    @JsonIgnore
    public Boolean isOK(){
        return RespCode.OK.respCode.equals(this.getRespCode());
    }

    @JsonIgnore
    public Boolean isNotOK(){
        return !this.isOK();
    }

    @JsonIgnore
    public boolean isNotFieldsFilter(){
        return Objects.isNull(this.getFilterFeilds()) || WILDCARD_ALL.equals(this.getFilterFeilds());
    }

    /**
     * 业务状态码,如需国际化可采用多属性构造
     */
    public enum RespCode {
        OK("10000","处理成功"),
        ERROR("99999","处理失败"),
        BAD_REQUEST("10400","请求失败"),
        METHOD_NOT_ALLOWED("10405","请求方式不允许"),
        PARAMS_ERROR("11000","参数有误"),
        UNAUTHORIZED("10401","请求失败"),
        FORBIDDEN("10403","权限不足"),
        INTERNAL_SERVER_ERROR("10500","服务器内部错误"),
        RESOURCES_NOT_FOUND("10505","资源不存在"),
        FREQUENTL("66666","处理过频"),
        TIMEOUT("20200","请求失败"),
        ;

        private final String respCode;
        private final String respMsg;

        RespCode(String respCode, String respMsg) {
            this.respCode = respCode;
            this.respMsg = respMsg;
        }

        public String getRespCode() {
            return respCode;
        }

        public String getRespMsg() {
            return respMsg;
        }}
}
