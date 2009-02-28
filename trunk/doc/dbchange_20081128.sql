CREATE TABLE `oa`.`proj_cd` (
  `proj_id` SMALLINT NOT NULL DEFAULT NULL AUTO_INCREMENT,
  `proj_cd` VARCHAR(8) NOT NULL,
  `is_enabled` INTEGER NOT NULL DEFAULT 1,
  `proj_site` SMALLINT NOT NULL,
  `description` VARCHAR(18),
  PRIMARY KEY (`proj_id`)
)
ENGINE = InnoDB
CHARACTER SET utf8 COLLATE utf8_general_ci;

ALTER TABLE `oa`.`proj_cd` ADD CONSTRAINT `FK_proj_cd_to_site` FOREIGN KEY `FK_proj_cd_to_site` (`proj_site`)
    REFERENCES `site` (`site_id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT;


ALTER TABLE `oa`.`expense_row_det` ADD COLUMN `proj_id` SMALLINT AFTER `exp_date`;
ALTER TABLE `oa`.`expense_row_det` ADD CONSTRAINT `FK_expense_row_det_to_proj_cd` FOREIGN KEY `FK_expense_row_det_to_proj_cd` (`proj_id`)
    REFERENCES `proj_cd` (`proj_id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT;

ALTER TABLE `oa`.`exp_row_det_hist` ADD COLUMN `proj_id` SMALLINT AFTER `exp_date`;
ALTER TABLE `oa`.`exp_row_det_hist` ADD CONSTRAINT `FK_exp_row_det_hist_to_proj_cd` FOREIGN KEY `FK_exp_row_det_hist_to_proj_cd` (`proj_id`)
    REFERENCES `proj_cd` (`proj_id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT;

ALTER TABLE `oa`.`pr_item` ADD COLUMN `proj_id` SMALLINT AFTER `po_item_id`;
ALTER TABLE `oa`.`pr_item` ADD CONSTRAINT `FK_pr_item_to_proj_cd` FOREIGN KEY `FK_pr_item_to_proj_cd` (`proj_id`)
    REFERENCES `proj_cd` (`proj_id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT;

ALTER TABLE `oa`.`po_item` ADD COLUMN `proj_id` SMALLINT AFTER `description`;
ALTER TABLE `oa`.`po_item` ADD CONSTRAINT `FK_po_item_to_proj_cd` FOREIGN KEY `FK_po_item_to_proj_cd` (`proj_id`)
    REFERENCES `proj_cd` (`proj_id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT;


ALTER TABLE `oa`.`pr_item_history` ADD COLUMN `proj_id` SMALLINT AFTER `is_recharge`;
ALTER TABLE `oa`.`pr_item_history` ADD CONSTRAINT `FK_pr_item_history_to_proj_cd` FOREIGN KEY `FK_pr_item_history_to_proj_cd` (`proj_id`)
    REFERENCES `proj_cd` (`proj_id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT;


ALTER TABLE `oa`.`po_item_history` ADD COLUMN `proj_id` SMALLINT AFTER `buy_for_dep`,
ALTER TABLE `oa`.`po_item_history` ADD CONSTRAINT `FK_po_item_history_to_proj_cd` FOREIGN KEY `FK_po_item_history_to_proj_cd` (`proj_id`)
    REFERENCES `proj_cd` (`proj_id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT;

ALTER TABLE `oa`.`expense_claim`  ADD COLUMN `exp_proj_id` SMALLINT AFTER `exp_desc`;
ALTER TABLE `oa`.`expense_claim` ADD CONSTRAINT `FK_expense_claim_to_proj_cd` FOREIGN KEY `FK_expense_claim_to_proj_cd` (`exp_proj_id`)
    REFERENCES `proj_cd` (`proj_id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT;


