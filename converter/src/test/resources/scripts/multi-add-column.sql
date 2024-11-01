CREATE TABLE `champ_app_mgr_d`  (
    `RID` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `LABEL_TYPE`  varchar(8) NULL,
    `LABEL_KEY`  varchar(32) NULL,
    `LABEL_VALUE`  varchar(32) NULL,
    `LABEL_SORT`  varchar(32) NULL,
    PRIMARY KEY (`RID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;


-- ------------------------------------------
-- 增加MD5，STATUS字段
-- ------------------------------------------
ALTER TABLE `champ_app_mgr_d`
    ADD COLUMN `MD5` varchar(32) NULL COMMENT '启动项内容MD5' AFTER `LABEL_SORT`,
    ADD COLUMN `STATUS` varchar(1) NULL COMMENT '是否为当前应用引用:0:否 1：是' AFTER `MD5`;
