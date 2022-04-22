CREATE TABLE `champ_app_mgr_d`  (
    `RID` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键',
    PRIMARY KEY (`RID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

ALTER TABLE `champ_app_mgr_d`   ADD COLUMN `LABEL_TYPE`  varchar(8) NULL COMMENT 'label分类' AFTER `RID`;
ALTER TABLE `champ_app_mgr_d`   ADD COLUMN `LABEL_KEY`  varchar(32) NULL COMMENT 'label' AFTER `LABEL_TYPE`;
ALTER TABLE `champ_app_mgr_d`   ADD COLUMN `LABEL_VALUE`  varchar(32) NULL COMMENT 'label值' AFTER `LABEL_KEY`;
ALTER TABLE `champ_app_mgr_d`   ADD COLUMN `LABEL_SORT`  varchar(32) NULL COMMENT 'label排序值' AFTER `LABEL_VALUE`;

-- 修改LABEL_KEY、LABEL_VALUE字段长度为256
ALTER TABLE `champ_app_mgr_d`
    MODIFY COLUMN `LABEL_KEY`  varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'label' AFTER `LABEL_TYPE`,
    MODIFY COLUMN `LABEL_VALUE`  varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'label值' AFTER `LABEL_KEY`;