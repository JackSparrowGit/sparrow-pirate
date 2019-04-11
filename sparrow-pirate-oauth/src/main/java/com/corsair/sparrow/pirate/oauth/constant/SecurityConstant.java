package com.corsair.sparrow.pirate.oauth.constant;

/**
 * @author jack
 */
public interface SecurityConstant {

    String SYS_KEY = "sparrow";
    /**
     * 系统前缀
     */
    String SYS_PREFIX = "corsair_";
    /**
     * license信息
     */
    String SYS_LICENSE = "made by jack 9874118@qq.com ~";
    /**
     * 自定义表名
     */
    String CLIENT_TABLE = "t_oauth_client_details";
    /**
     * 官方sys_oauth_client_details 表字段
     */
    String CLIENT_FIELDS = "client_id, client_secret, resource_ids, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove ";
    /**
     * 标准find statement语句
     */
    String BASE_FIND_STATEMENT = "select "+ CLIENT_FIELDS+ " from "+CLIENT_TABLE;
    /**
     * oauth2默认查询语句
     */
    String DEFAULT_FIND_STATEMENT = BASE_FIND_STATEMENT + " order by client_id";
    /**
     * 条件查询client_id
     */
    String DEFAULT_SELECT_STATEMENT = BASE_FIND_STATEMENT + " where client_id = ?";
    /**
     * 系统基础角色
     */
    String BASE_ROLE = "ROLE_BASE";
    /**
     * ROLE_前缀
     */
    String ROLE_PREFIX = "ROLE_";
}
