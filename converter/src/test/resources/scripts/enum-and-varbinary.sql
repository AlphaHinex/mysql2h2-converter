SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE IF NOT EXISTS `thread_info` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `object` enum('thread') DEFAULT NULL COMMENT 'object',
  `created_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `title` varchar(512) DEFAULT NULL COMMENT '标题',
  `assistant_type` varchar(512) DEFAULT NULL COMMENT '智能体类型',
  `created_by` varchar(64) DEFAULT NULL COMMENT '会话创建人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='对话历史表';

CREATE TABLE IF NOT EXISTS `thread_message` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `object` enum('thread.message') DEFAULT NULL COMMENT 'thread.messageobject',
  `created_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `thread_id` varchar(64) DEFAULT NULL COMMENT '会话id',
  `role` varchar(64) DEFAULT NULL COMMENT '消息主题角色user或者assistant',
  `status` enum('progress','incomplete','completed') DEFAULT NULL COMMENT '消息状态',
  `completed_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '消息完成时间',
  `incomplete_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '标记为不完整消息的时间',
  `assistant_id` varchar(64) DEFAULT NULL COMMENT '智能体id',
  `run_id` varchar(64) DEFAULT NULL COMMENT '智能体执行id',
  `user_id` varchar(64) DEFAULT NULL COMMENT '用户id',
  `metadata` longtext COMMENT 'json对象字符串每条消息的metadata',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会话消息表';

CREATE TABLE IF NOT EXISTS `thread_message_content` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `created_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `message_id` varchar(64) DEFAULT NULL COMMENT '会话消息的id',
  `content_type` varchar(128) DEFAULT NULL COMMENT '内容类型',
  `content` longtext COMMENT '内容',
  `thread_id` varchar(64) DEFAULT NULL COMMENT '会话id',
  `role` varbinary(64) DEFAULT NULL COMMENT '消息主题角色user或者assistant',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会话消息内容表';

SET FOREIGN_KEY_CHECKS = 1;
