INSERT INTO `metadata_det` (`md_det_id`,`md_mst_id`,`eng_full_desc`,`sec_full_desc`,`eng_short_desc`,`sec_short_desc`,`color`) VALUES  (3,100104,'Expense','Expense','Expense','Expense',NULL);

ALTER TABLE `oa`.`yearly_budget` ADD COLUMN `exp_ctgy_id` INTEGER AFTER `row_version`,
 ADD COLUMN `exp_subctgy_id` INTEGER AFTER `exp_ctgy_id`,
 ADD CONSTRAINT `FK_yearly_budget_to_expense_ctgy` FOREIGN KEY `FK_yearly_budget_to_expense_ctgy` (`exp_ctgy_id`)
    REFERENCES `expense_ctgy` (`exp_ctgy_id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
 ADD CONSTRAINT `FK_yearly_budget_to_expense_subctgy` FOREIGN KEY `FK_yearly_budget_to_expense_subctgy` (`exp_subctgy_id`)
    REFERENCES `expense_subctgy` (`exp_subctgy_id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT;

ALTER TABLE `oa`.`expense` ADD COLUMN `exp_budget_id` INTEGER AFTER `confirm_date`,
 ADD CONSTRAINT `FK_expense_to_yearly_budget` FOREIGN KEY `FK_expense_to_yearly_budget` (`exp_budget_id`)
    REFERENCES `yearly_budget` (`budget_id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT;
    
ALTER TABLE `oa`.`yearly_budget` ADD COLUMN `budget_duration_from` DATETIME AFTER `exp_subctgy_id`,
 ADD COLUMN `budget_duration_to` DATETIME AFTER `budget_duration_from`;
