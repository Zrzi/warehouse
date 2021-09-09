SET CHARACTER_SET_CLIENT = gbk;
SET CHARACTER_SET_RESULTS = gbk;

CREATE TABLE `cost`(
    `date` VARCHAR(10),
    `cost` DECIMAL(11, 2),
    `checked` INTEGER NOT NULL DEFAULT 0,
    PRIMARY KEY (`date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `department`(
    `department_id` BIGINT AUTO_INCREMENT,
    `department_name` VARCHAR(16) UNIQUE ,
    `department_admin_id` BIGINT,
    PRIMARY KEY (`department_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `employee`(
    `employee_id` BIGINT AUTO_INCREMENT,
    `employee_username` VARCHAR(32) UNIQUE NOT NULL,
    `employee_password` VARCHAR(32) NOT NULL,
    `employee_name` VARCHAR(16) UNIQUE NOT NULL,
    `employee_gender` VARCHAR(2),
    `employee_age` INT,
    `employee_address` VARCHAR(128),
    `employee_department_id` BIGINT,
    PRIMARY KEY (`employee_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `employee_role`(
    `employee_role_employee_id` BIGINT NOT NULL,
    `employee_role_role_id` BIGINT NOT NULL,
    PRIMARY KEY (`employee_role_employee_id`, `employee_role_role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `history_record`(
    `history_record_type` VARCHAR(20) NOT NULL,
    `history_record` VARCHAR(128) NOT NULL,
    `time` VARCHAR(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `material`(
    `material_id` BIGINT AUTO_INCREMENT,
    `material_name` VARCHAR(32) NOT NULL UNIQUE,
    `valid` INTEGER NOT NULL DEFAULT 1,
    PRIMARY KEY (`material_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `material_storation`(
    `material_storation_id` BIGINT AUTO_INCREMENT,
    `material_storation_warehouse_id` BIGINT NOT NULL,
    `material_storation_material_id` BIGINT NOT NULL,
    `material_storation_time` VARCHAR(32),
    `material_storation_number` INTEGER NOT NULL,
    `material_storation_rest_number` INTEGER NOT NULL,
    `material_storation_price` DECIMAL(11, 2) NOT NULL,
    PRIMARY KEY (`material_storation_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `product`(
    `product_id` BIGINT AUTO_INCREMENT,
    `product_name` VARCHAR(32) NOT NULL UNIQUE,
    `valid` INTEGER NOT NULL DEFAULT 1,
    PRIMARY KEY (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `product_application`(
    `product_application_id` BIGINT AUTO_INCREMENT,
    `product_application_product_id` BIGINT NOT NULL,
    `product_application_number` INTEGER NOT NULL,
    `product_application_stored` INTEGER NOT NULL,
    PRIMARY KEY (`product_application_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `product_material`(
    `product_material_product_id` BIGINT,
    `product_material_material_id` BIGINT,
    `product_material_number` INTEGER NOT NULL,
    PRIMARY KEY (`product_material_product_id`, `product_material_material_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `product_storation`(
    `product_storation_id` BIGINT AUTO_INCREMENT,
    `product_storation_warehouse_id` BIGINT NOT NULL,
    `product_storation_product_id` BIGINT NOT NULL,
    `product_storation_time` VARCHAR(32) NOT NULL,
    `product_storation_number` INTEGER NOT NULL,
    `product_storation_rest_number` INTEGER NOT NULL,
    PRIMARY KEY (`product_storation_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `purchase_order`(
    `purchase_order_id` BIGINT AUTO_INCREMENT,
    `purchase_order_employee_id` BIGINT NOT NULL,
    `purchase_order_material_id` BIGINT NOT NULL,
    `purchase_order_time` VARCHAR(32) NOT NULL,
    `purchase_order_number` INTEGER NOT NULL,
    `purchase_order_rest_number` INTEGER NOT NULL,
    `purchase_order_price` DECIMAL(11, 2) NOT NULL,
    `purchase_order_approved` INTEGER NOT NULL,
    `purchase_order_stored` INTEGER NOT NULL,
    PRIMARY KEY (`purchase_order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `retrieval_record`(
    `retrieval_record_id` BIGINT AUTO_INCREMENT,
    `retrieval_record_material_id` BIGINT NOT NULL,
    `retrieval_record_number` INTEGER NOT NULL,
    `retrieval_record_price` DECIMAL(11, 2) NOT NULL,
    `retrieval_record_time` VARCHAR(10),
    PRIMARY KEY (`retrieval_record_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `role`(
    `role_id` BIGINT AUTO_INCREMENT,
    `role_name` VARCHAR(32) NOT NULL UNIQUE,
    PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sail_order`(
    `sail_order_id` BIGINT AUTO_INCREMENT,
    `sail_order_employee_id` BIGINT NOT NULL,
    `sail_order_product_id` BIGINT NOT NULL,
    `sail_order_time` VARCHAR(32) NOT NULL,
    `sail_order_number` INTEGER NOT NULL,
    `sail_order_price` DECIMAL(11, 2) NOT NULL,
    `valid` INTEGER NOT NULL DEFAULT 1,
    PRIMARY KEY (`sail_order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `warehouse`(
    `warehouse_id` BIGINT AUTO_INCREMENT,
    `warehouse_type` VARCHAR(10),
    `warehouse_address` VARCHAR(128) NOT NULL UNIQUE,
    `warehouse_max` INTEGER NOT NULL,
    `warehouse_min` INTEGER NOT NULL,
    PRIMARY KEY (`warehouse_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* 索引 */
CREATE INDEX `department_name_index` ON `department`(`department_name`);

CREATE INDEX `employee_name_index` ON `employee`(`employee_username`);

CREATE INDEX `material_name_index` ON `material`(`material_name`);

CREATE INDEX `material_storation_index` ON `material_storation`(`material_storation_warehouse_id`, `material_storation_material_id`, `material_storation_time`);

CREATE INDEX `purchase_order_index` ON `purchase_order`(`purchase_order_employee_id`, `purchase_order_material_id`);

CREATE INDEX `retrieval_record_time_index` ON `retrieval_record`(`retrieval_record_time`);

CREATE INDEX `product_name_index` ON `product`(`product_name`);

CREATE INDEX `product_storation_index` ON `product_storation`(`product_storation_warehouse_id`, `product_storation_material_id`, `product_storation_time`);

CREATE INDEX `role_name_index` ON `role`(`role_name`);

CREATE INDEX `warehouse_type_index` ON `warehouse`(`warehouse_type`);

/* role */
INSERT INTO `role` (`role_name`)
VALUES ('ROLE_WAREHOUSE_ADMIN');

INSERT INTO `role` (`role_name`)
VALUES ('ROLE_PURCHASE_ORDER_DEPT');

INSERT INTO `role` (`role_name`)
VALUES ('ROLE_PURCHASE_ORDER_ADMIN');

INSERT INTO `role` (`role_name`)
VALUES ('ROLE_PRODUCTION_DEPT');

INSERT INTO `role` (`role_name`)
VALUES ('ROLE_SAILS_DEPT');

INSERT INTO `role` (`role_name`)
VALUES ('ROLE_FINANCE_DEPT');

/* department */
INSERT INTO `department` (`department_name`)
VALUE ('采购部门');

INSERT INTO `department` (`department_name`)
VALUE ('生产部门');

INSERT INTO `department` (`department_name`)
VALUE ('销售部门');

INSERT INTO `department` (`department_name`)
VALUE ('财务部门');

INSERT INTO `department` (`department_name`)
VALUE ('仓库');

/* employee */
INSERT INTO `employee` (`employee_username`, `employee_password`, `employee_name`, `employee_gender`, `employee_age`, `employee_address`, `employee_department_id`)
VALUES ('warehouse_admin', '123456', '张三', NULL, NULL, NULL, 5);

INSERT INTO `employee` (`employee_username`, `employee_password`, `employee_name`, `employee_gender`, `employee_age`, `employee_address`, `employee_department_id`)
VALUES ('purchase_order', '123456', '李四', NULL, NULL, NULL, 1);

INSERT INTO `employee` (`employee_username`, `employee_password`, `employee_name`, `employee_gender`, `employee_age`, `employee_address`, `employee_department_id`)
VALUES ('purchase_order_admin', '123456', '王五', NULL, NULL, NULL, 1);

INSERT INTO `employee` (`employee_username`, `employee_password`, `employee_name`, `employee_gender`, `employee_age`, `employee_address`, `employee_department_id`)
VALUES ('product', '123456', '赵六', NULL, NULL, NULL, 2);

INSERT INTO `employee` (`employee_username`, `employee_password`, `employee_name`, `employee_gender`, `employee_age`, `employee_address`, `employee_department_id`)
VALUES ('sail', '123456', '孙七', NULL, NULL, NULL, 3);

INSERT INTO `employee` (`employee_username`, `employee_password`, `employee_name`, `employee_gender`, `employee_age`, `employee_address`, `employee_department_id`)
VALUES ('finance', '123456', '周八', NULL, NULL, NULL, 4);

/* employee_role */
INSERT INTO `employee_role` (`employee_role_employee_id`, `employee_role_role_id`)
VALUES (1, 1);

INSERT INTO `employee_role` (`employee_role_employee_id`, `employee_role_role_id`)
VALUES (2, 2);

INSERT INTO `employee_role` (`employee_role_employee_id`, `employee_role_role_id`)
VALUES (3, 3);

INSERT INTO `employee_role` (`employee_role_employee_id`, `employee_role_role_id`)
VALUES (4, 4);

INSERT INTO `employee_role` (`employee_role_employee_id`, `employee_role_role_id`)
VALUES (5, 5);

INSERT INTO `employee_role` (`employee_role_employee_id`, `employee_role_role_id`)
VALUES (6, 6);

/* 材料 */
INSERT INTO `material` (`material_name`, `valid`)
VALUES ('材料1', 1);

INSERT INTO `material` (`material_name`, `valid`)
VALUES ('材料2', 1);

INSERT INTO `material` (`material_name`, `valid`)
VALUES ('材料3', 1);

INSERT INTO `material` (`material_name`, `valid`)
VALUES ('材料4', 1);

INSERT INTO `material` (`material_name`, `valid`)
VALUES ('材料5', 1);

INSERT INTO `material` (`material_name`, `valid`)
VALUES ('材料6', 1);

/* 材料 */
INSERT INTO `product` (`product_name`, `valid`)
VALUES ('产品1', 1);

INSERT INTO `product` (`product_name`, `valid`)
VALUES ('产品2', 1);

INSERT INTO `product` (`product_name`, `valid`)
VALUES ('产品3', 1);

INSERT INTO `product` (`product_name`, `valid`)
VALUES ('产品4', 1);

INSERT INTO `product` (`product_name`, `valid`)
VALUES ('产品5', 1);

INSERT INTO `product` (`product_name`, `valid`)
VALUES ('产品6', 1);

INSERT INTO `product_material` (`product_material_product_id`, `product_material_material_id`, `product_material_number`)
VALUES (1, 1, 10);

INSERT INTO `product_material` (`product_material_product_id`, `product_material_material_id`, `product_material_number`)
VALUES (2, 2, 10);

INSERT INTO `product_material` (`product_material_product_id`, `product_material_material_id`, `product_material_number`)
VALUES (3, 3, 10);

INSERT INTO `product_material` (`product_material_product_id`, `product_material_material_id`, `product_material_number`)
VALUES (4, 4, 10);

INSERT INTO `product_material` (`product_material_product_id`, `product_material_material_id`, `product_material_number`)
VALUES (5, 5, 10);

INSERT INTO `product_material` (`product_material_product_id`, `product_material_material_id`, `product_material_number`)
VALUES (6, 6, 10);