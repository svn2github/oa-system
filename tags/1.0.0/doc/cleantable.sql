delete from expense_cell_det;
delete from expense_row_det;
delete from expense_attach;
delete from expense_claim;
delete from exp_cell_det_hist
delete from exp_row_det_hist
delete from expense_approver
delete from expense_recharge
delete from expense_history
delete from expense

delete from po_attach
delete from po_approver
delete from po_item_cancel
delete from po_item_history
delete from po_item_receipt
delete from po_item_recharge
delete from po_history
update pr_item set po_item_id = null
delete from po_item
delete from purchase_order

delete from pr_item_history
delete from pr_item_attach
delete from pr_item_recharge
delete from pr_item
delete from pr_approver
delete from pr_attach
delete from pr_authorized_purchaser
delete from pr_history
delete from purchase_request

delete from ta_approver
delete from ta_history
delete from air_ticket_recharge
delete from air_ticket
delete from travel_application

delete from capex_req_item_history
delete from  capex_request_history
delete from  capex_request_item
delete from  capex_request_approver
delete from  capex_request_attach
delete from  capex_department
update capex set capex_req_id = null
delete from capex_request
delete from capex

delete from email_batch
delete from email

delete from approver_delegate

delete from yearly_bgt_dep_history
delete FROM yearly_budget_history
delete FROM yearly_budget_department
delete FROM yearly_budget

delete from travel_group_det

delete from system_log

delete from exchange_rate

delete from hotel_room
delete from hotel_contract
delete from hotel

delete from supplier_item

delete from supplier_history
delete from supplier_contract

delete from purchase_subctgy
delete from purchase_ctgy
delete from supplier

delete from city where city_id <> 12
delete from province where province_id <> 9
delete from country where country_id <> 1
delete from currency where curr_cd <> 'RMB'
delete from currency where curr_cd = 'RMB'

delete from rule_consequence
delete from rule_condition
delete from rule_flow_rule
delete from rule
delete from rule_flow

delete from user_department where user_id <> 1
delete from user_site where user_id <> 1
delete FROM user_role where user_id <> 1
delete from oa_user where user_id <> 1

