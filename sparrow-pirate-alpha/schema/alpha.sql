--
--  t_alpha表
--
DROP TABLE `t_alpha` CASCADE;
CREATE TABLE `t_alpha` (
   `id` bigint(20) NOT NULL COMMENT 'id',
   `name` varchar(255) NOT NULL COMMENT '名称',
   `rate` double(8,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '概率',
   `money` decimal(18,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '金钱',
   `del_flag` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
   `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Alpha表'