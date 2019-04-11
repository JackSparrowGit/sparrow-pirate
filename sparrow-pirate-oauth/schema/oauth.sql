--
--  t_oauth_client_details表
--  如采用jdbc存储token,请参考: https://github.com/spring-projects/spring-security-oauth/blob/master/spring-security-oauth2/src/test/resources/schema.sql
--
CREATE TABLE `t_oauth_client_details`  (
     `client_id` varchar(48) NOT NULL COMMENT '设备id',
     `resource_ids` varchar(255) NULL COMMENT '资源ids',
     `client_secret` varchar(255) NULL COMMENT '设备密钥',
     `scope` varchar(255) NULL COMMENT '范围',
     `authorized_grant_types` varchar(255) NULL COMMENT '授权类型',
     `web_server_redirect_uri` varchar(255) NULL COMMENT '网页重定向uri',
     `authorities` varchar(255) NULL COMMENT '权限',
     `access_token_validity` int(11) NULL COMMENT 'access_token时效',
     `refresh_token_validity` int(11) NULL COMMENT 'refresh_token时效',
     `additional_information` varchar(4096) NULL COMMENT '附加信息',
     `autoapprove` varchar(255) NULL COMMENT '自动授权',
     PRIMARY KEY (`client_id`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'oauth鉴权表';
--
--  t_sys_user表
--
CREATE TABLE `t_sys_user`  (
     `id` bigint(20) NOT NULL COMMENT 'id',
     `username` varchar(255) NOT NULL COMMENT '用户名',
     `password` varchar(512) NOT NULL COMMENT '密码',
     `salt` varchar(255) NULL COMMENT '密码盐',
     `first_name` varchar(255) NULL COMMENT '姓',
     `last_name` varchar(255) NULL COMMENT '名',
     `nick_name` varchar(255) NULL COMMENT '昵称',
     `phone` varchar(20) NULL COMMENT '手机号',
     `email` varchar(255) NULL COMMENT '邮箱',
     `gender` enum('male','female') NULL DEFAULT 'male' COMMENT '性别',
     `image` varchar(512) NULL COMMENT '头像',
     `birthday` date DEFAULT NULL COMMENT '生日',
     `is_enabled` bit(1) NOT NULL DEFAULT 1 COMMENT '是否可用',
     `is_account_non_expired` bit(1) NOT NULL DEFAULT 1 COMMENT '账号是否过期',
     `is_account_non_locked` bit(1) NOT NULL DEFAULT 1 COMMENT '账号是否被锁定',
     `is_credentials_non_expired` bit(1) NOT NULL DEFAULT 1 COMMENT '账号认证信息是否过期',
     `language` varchar(6) NULL COMMENT '语种(en zh_CN ..)',
     `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
     `region` varchar(255) NULL COMMENT '籍贯',
     `last_login_time` timestamp NULL COMMENT '最后登陆时间',
     `primary_key` varchar(255) NULL COMMENT '业务私钥',
     PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表';
--
--  t_sys_role表
--
CREATE TABLE `t_sys_role`  (
     `id` bigint(20) NOT NULL COMMENT 'id',
     `role_code` varchar(255) NOT NULL COMMENT '角色编码',
     `role_name` varchar(255) NOT NULL COMMENT '角色名称',
     `role_type` varchar(255) NULL COMMENT '角色类型',
     `is_enable` bit(1) NOT NULL DEFAULT 1 COMMENT '角色是否可用',
     `description` varchar(255) NULL COMMENT '描述',
     `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
     `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
     PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色表';
--
--  t_sys_permission表
--
CREATE TABLE `t_sys_permission`  (
     `id` bigint(20) NOT NULL COMMENT 'id',
     `parent_id` bigint(20) NOT NULL COMMENT '父节点ID',
     `parent_ids` varchar(255) NOT NULL COMMENT '父节点ID集合，逗号分隔',
     `name` varchar(255) NOT NULL COMMENT '权限名称',
     `depth` int(10) NOT NULL DEFAULT 0 COMMENT '权限深度',
     `sort` int(10) NOT NULL DEFAULT 0 COMMENT '排列顺序',
     `icon` varchar(255) NULL COMMENT '前端icon',
     `clazz` varchar(255) NULL COMMENT '前端class',
     `style` varchar(255) NULL COMMENT '前端style',
     `url` varchar(255) NULL COMMENT '资源url',
     `type` varchar(255) NULL COMMENT '资源类型',
     `methods` varchar(255) NULL COMMENT '支持的请求方式 *代表所有',
     `options_fields` varchar(255) NULL COMMENT '参数域',
     `protect_type` varchar(255) NULL COMMENT '保护类型',
     `create_by` varchar(255) NULL COMMENT '创建者',
     `update_by` varchar(255) NULL COMMENT '修改者',
     `is_enable` bit(1) NOT NULL DEFAULT 1 COMMENT '是否可用',
     `description` varchar(255) NULL COMMENT '描述',
     `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
     `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
     PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '权限表';
--
--  t_sys_group表
--
CREATE TABLE `t_sys_group`  (
    `id` bigint(20) NOT NULL COMMENT 'id',
    `parent_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '父节点ID',
    `parent_ids` varchar(255) NOT NULL COMMENT '父节点IDS',
    `group_code` varchar(255) NOT NULL COMMENT '组编号',
    `group_name` varchar(255) NOT NULL COMMENT '组名称',
    `type` varchar(255) NULL COMMENT '组类型',
    `is_enabled` bit(1) NOT NULL DEFAULT 1 COMMENT '是否可用',
    `description` varchar(255) NULL COMMENT '描述',
    `create_by` varchar(255) NULL COMMENT '创建者',
    `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户组表';
--
--  t_sys_user_role表
--
CREATE TABLE `t_sys_user_role`  (
    `id` bigint(20) NOT NULL COMMENT 'id',
    `user_id` bigint(20) NOT NULL COMMENT '用户ID',
    `role_id` bigint(20) NOT NULL COMMENT '角色ID',
    `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户-角色关联表';
--
--  t_sys_role_permission表
--
CREATE TABLE `t_sys_role_permission`  (
    `id` bigint(20) NOT NULL COMMENT 'id',
    `role_id` bigint(20) NOT NULL COMMENT '角色ID',
    `permission_id` bigint(20) NOT NULL COMMENT '权限ID',
    `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色-权限关联表';
--
--  t_sys_user_group表
--
CREATE TABLE `t_sys_user_group`  (
     `id` bigint(20) NOT NULL COMMENT 'id',
     `user_id` bigint(20) NOT NULL COMMENT '用户ID',
     `group_id` bigint(20) NOT NULL COMMENT '用户组ID',
     `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
     `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
     PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户-用户组关联表';


--
-- 插入初始值
--

-- 插入client
INSERT INTO t_oauth_client_details('client_id','client_secret','scope','authorized_grant_types','authorities','autoapprove') VALUES('sparrow','$2a$10$Ig95hdvksbunP2WnaRF.NuU2cu/HkquLZcg2lKugMpsrGgmp6LzQi','test','authorization_code,client_credentials,password,implicit,refresh_token','WRIGTH_WRITE,WRIGHT_READ','true');

-- 插入user
INSERT INTO t_sys_user(id,username,password,phone,email,gender) VALUES(1,'jack','$2a$10$JginFktqZQ0dxnNLAzxx6OudGPFwdASQ4HnJxldgscU.tHFeOoDyy','18320941632','9874118@qq.com','male');

-- 插入role
INSERT INTO t_sys_role(id,role_code,role_name) VALUES(1,'ROLE_ROOT','ROOT');

-- 绑定user_role
INSERT INTO t_sys_user_role(id,user_id,role_id) VALUES(1,1,1);
