/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2009-3-11 10:35:55                           */
/*==============================================================*/


/*==============================================================*/
/* Table: air_ticket                                            */
/*==============================================================*/
create table air_ticket (
   air_ticket_id        int                  identity,
   ta_no                varchar(16)          null,
   po_item_id           int                  null,
   rtn_po_item_id       int                  null,
   supp_id              int                  null,
   lve_flt_no           varchar(20)          null,
   lve_flt_class        int                  null,
   lve_flt_fr           smallint             null,
   lve_flt_to           smallint             null,
   lve_flt_depart       datetime             null,
   lve_flt_arrive       datetime             null,
   exch_rate_id         int                  not null default 0,
   exchange_rate        decimal(11,4)        not null default 0.0000,
   price                decimal(8,2)         null,
   is_recharge          int                  not null default 0,
   status               int                  null,
   pur_type_cd          varchar(8)           null,
   boarding_pass_received int                not null default 1,
   constraint PK_air_ticket primary key nonclustered (air_ticket_id)
)
go

/*==============================================================*/
/* Index: fk_air_ticket_to_currency                             */
/*==============================================================*/
create index fk_air_ticket_to_currency on air_ticket (
exch_rate_id ASC
)
go

/*==============================================================*/
/* Table: air_ticket_recharge                                   */
/*==============================================================*/
create table air_ticket_recharge (
   tkt_rechrg_id        int                  identity,
   air_ticket_id        int                  not null default 0,
   cust_cd              varchar(17)          null,
   person_dep_id        smallint             null,
   person_id            int                  null,
   amount               decimal(9,2)         not null default 0.00,
   constraint PK_air_ticket_recharge primary key nonclustered (tkt_rechrg_id)
)
go

/*==============================================================*/
/* Table: approver_delegate                                     */
/*==============================================================*/
create table approver_delegate (
   dlgt_id              int                  identity,
   dlgt_type            int                  not null default 0,
   org_approver         int                  not null default 0,
   dlgt_approver        int                  not null default 0,
   dlgt_fr_date         datetime             null,
   dlgt_to_date         datetime             null,
   constraint PK_approver_delegate primary key nonclustered (dlgt_id)
)
go

/*==============================================================*/
/* Table: capex                                                 */
/*==============================================================*/
create table capex (
   capex_no             varchar(16)          not null,
   capex_site           smallint             not null default 0,
   pur_ctgy_id          int                  null,
   pur_subctgy_id       int                  null,
   base_curr_cd         varchar(8)           not null,
   budget_id            int                  null,
   capex_req_id         int                  null,
   capex_actualamt      decimal(14,2)        not null default 0.00,
   row_version          int                  not null default 0,
   capex_year           int                  not null default 0,
   constraint PK_capex primary key nonclustered (capex_no)
)
go

/*==============================================================*/
/* Table: capex_department                                      */
/*==============================================================*/
create table capex_department (
   capex_depart_id      int                  identity,
   capex_no             varchar(16)          not null,
   capex_dep_id         smallint             not null default 0,
   constraint PK_capex_department primary key nonclustered (capex_depart_id)
)
go

/*==============================================================*/
/* Table: capex_req_item_history                                */
/*==============================================================*/
create table capex_req_item_history (
   cr_item_hist_id      int                  identity,
   cr_hist_id           int                  not null default 0,
   pur_subctgy_id       int                  not null default 0,
   supp_id              int                  null,
   supp_nm              varchar(50)          null,
   item_id              int                  null,
   item_sepc            varchar(255)         not null,
   exch_rate_id         int                  not null default 0,
   exchange_rate        decimal(11,4)        not null default 0.0000,
   unit_price           decimal(12,2)        not null default 0.00,
   quantity             int                  not null default 0,
   constraint PK_capex_req_item_history primary key nonclustered (cr_item_hist_id)
)
go

/*==============================================================*/
/* Index: fk_capex_req_item_history_to_currency                 */
/*==============================================================*/
create index fk_capex_req_item_history_to_currency on capex_req_item_history (
exch_rate_id ASC
)
go

/*==============================================================*/
/* Table: capex_request                                         */
/*==============================================================*/
create table capex_request (
   capex_req_id         int                  identity,
   capex_no             varchar(16)          not null,
   cr_type              int                  not null default 0,
   cr_title             varchar(50)          not null,
   cr_desc              varchar(500)         null,
   cr_status            int                  not null default 0,
   base_amt             decimal(14,2)        not null default 0.00,
   creator              int                  not null default 0,
   create_date          datetime             not null default 0000-00-00,
   requestor            int                  not null default 0,
   req_date             datetime             null,
   list_id              varchar(32)          null,
   activity             varchar(50)          null,
   post_audit_date      datetime             null,
   first_exp_date       datetime             null,
   last_exp_date        datetime             null,
   compleation_date     datetime             null,
   proj_leader_id       int                  null,
   accounting_cd        varchar(32)          null,
   is_capex_type        int                  not null default 0,
   is_asset_type        int                  not null default 0,
   other_type           varchar(100)         null,
   is_new_imp_reason    int                  not null default 0,
   is_cpt_inc_reason    int                  not null default 0,
   is_cst_imp_reason    int                  not null default 0,
   is_new_pdt_reason    int                  not null default 0,
   is_downsiz_reason    int                  not null default 0,
   is_hse_reason        int                  not null default 0,
   other_reason         varchar(100)         null,
   capex_capital_amt    decimal(14,2)        not null default 0.00,
   other_expense_amt    decimal(14,2)        not null default 0.00,
   asset_disposal_amt   decimal(14,2)        not null default 0.00,
   gross_book_amt       decimal(14,2)        not null default 0.00,
   proj_impct_non1      varchar(100)         null,
   proj_impct_non2      varchar(100)         null,
   proj_impct_other1    varchar(100)         null,
   proj_impct_other2    varchar(100)         null,
   proj_impct_other3    varchar(100)         null,
   ref_curr_cd          varchar(8)           not null,
   ref_exchange_rate    decimal(11,4)        not null default 0.0000,
   lst_forecast_amt     decimal(14,2)        null,
   discount_cash_flow   decimal(9,2)         null,
   npv_lc               decimal(14,2)        null,
   internal_rtn_rate    decimal(5,2)         null,
   discount_rate        decimal(5,2)         null,
   proj_leader_title    varchar(100)         null,
   approve_date         datetime             null,
   constraint PK_capex_request primary key nonclustered (capex_req_id)
)
go

/*==============================================================*/
/* Table: capex_request_approver                                */
/*==============================================================*/
create table capex_request_approver (
   list_id              varchar(32)          not null,
   approver_id          int                  not null default 0,
   approve_seq          smallint             not null default 0,
   approve_status       int                  not null default 0,
   approve_date         datetime             null,
   approve_comment      varchar(255)         null,
   can_modify           int                  not null default 0,
   actual_approver      int                  null,
   your_turn_date       datetime             null,
   email_date           datetime             null,
   email_times          int                  not null default 0,
   constraint PK_capex_request_approver primary key nonclustered (approver_id, list_id)
)
go

/*==============================================================*/
/* Table: capex_request_attach                                  */
/*==============================================================*/
create table capex_request_attach (
   attach_id            int                  identity,
   capex_req_id         int                  not null default 0,
   file_title           varchar(50)          not null,
   file_name            varchar(50)          not null,
   file_content         image                null,
   upload_date          datetime             not null default 0000-00-00,
   cont_file_size       int                  not null default 0,
   constraint PK_capex_request_attach primary key nonclustered (attach_id)
)
go

/*==============================================================*/
/* Table: capex_request_history                                 */
/*==============================================================*/
create table capex_request_history (
   cr_hist_id           int                  identity,
   capex_req_id         int                  not null default 0,
   capex_status         int                  not null default 0,
   base_amt             decimal(14,2)        not null default 0.00,
   list_id              CHAR(32)             not null,
   constraint PK_capex_request_history primary key nonclustered (cr_hist_id)
)
go

/*==============================================================*/
/* Table: capex_request_item                                    */
/*==============================================================*/
create table capex_request_item (
   cr_item_id           int                  identity,
   capex_req_id         int                  not null default 0,
   pur_subctgy_id       int                  not null default 0,
   supp_id              int                  null,
   supp_nm              varchar(50)          null,
   item_id              int                  null,
   item_sepc            varchar(255)         not null,
   exch_rate_id         int                  not null default 0,
   exchange_rate        decimal(11,4)        not null default 0.0000,
   unit_price           decimal(12,2)        not null default 0.00,
   quantity             int                  not null default 0,
   constraint PK_capex_request_item primary key nonclustered (cr_item_id)
)
go

/*==============================================================*/
/* Index: fk_capex_request_item_to_currency                     */
/*==============================================================*/
create index fk_capex_request_item_to_currency on capex_request_item (
exch_rate_id ASC
)
go

/*==============================================================*/
/* Table: city                                                  */
/*==============================================================*/
create table city (
   city_id              smallint             identity,
   province_id          smallint             not null default 0,
   eng_name             varchar(50)          not null,
   sec_name             varchar(50)          not null,
   is_enabled           int                  not null default 1,
   maint_site           smallint             null,
   constraint PK_city primary key nonclustered (city_id)
)
go

/*==============================================================*/
/* Table: country                                               */
/*==============================================================*/
create table country (
   country_id           smallint             identity,
   short_name           varchar(3)           not null,
   eng_name             varchar(50)          not null,
   sec_name             varchar(50)          not null,
   is_enabled           int                  not null default 1,
   maint_site           smallint             null,
   constraint PK_country primary key nonclustered (country_id)
)
go

/*==============================================================*/
/* Table: currency                                              */
/*==============================================================*/
create table currency (
   curr_cd              varchar(8)           not null,
   curr_nm              varchar(50)          not null,
   is_enabled           int                  not null default 1,
   constraint PK_currency primary key nonclustered (curr_cd)
)
go

/*==============================================================*/
/* Table: customer                                              */
/*==============================================================*/
create table customer (
   cust_cd              varchar(17)          not null,
   cust_site            smallint             not null default 0,
   cust_desc            varchar(50)          not null,
   cust_type            int                  not null default 0,
   is_enabled           int                  not null default 0,
   constraint PK_customer primary key nonclustered (cust_cd)
)
go

/*==============================================================*/
/* Table: department                                            */
/*==============================================================*/
create table department (
   dep_id               smallint             identity,
   dep_site             smallint             not null default 0,
   parent_id            smallint             null,
   dep_mgr              int                  null,
   is_enabled           int                  not null default 1,
   dep_name             varchar(100)         null,
   constraint PK_department primary key nonclustered (dep_id)
)
go

/*==============================================================*/
/* Table: email                                                 */
/*==============================================================*/
create table email (
   email_id             int                  identity,
   mail_fr              varchar(100)         not null,
   mail_to              varchar(100)         not null,
   subject              varchar(100)         not null,
   body                 text                 null,
   create_time          datetime             not null default 0000-00-00,
   send_time            datetime             null,
   fail_count           int                  not null default 0,
   wait_to_send         int                  not null default 1,
   site                 smallint             not null default 0,
   constraint PK_email primary key nonclustered (email_id)
)
go

/*==============================================================*/
/* Table: email_batch                                           */
/*==============================================================*/
create table email_batch (
   email_batch_id       int                  identity,
   mail_to_user         int                  not null default 0,
   body                 text                 null,
   template_name        varchar(100)         not null,
   site                 smallint             not null default 0,
   is_send              int                  not null default 0,
   ref_no               varchar(16)          null,
   constraint PK_email_batch primary key nonclustered (email_batch_id)
)
go

/*==============================================================*/
/* Table: erp_tran                                              */
/*==============================================================*/
create table erp_tran (
   site_id              smallint             not null default 0,
   start_time           datetime             null,
   time_per_day         smallint             null,
   interval_min         smallint             null,
   succ_mail_to         varchar(150)         null,
   fail_mail_to         varchar(150)         null,
   export_file_type     int                  not null default 1,
   serv_addr            varchar(50)          null,
   serv_port            int                  null,
   serv_user            varchar(50)          null,
   serv_pwd             varchar(50)          null,
   serv_dir             varchar(255)         null,
   constraint PK_erp_tran primary key nonclustered (site_id)
)
go

/*==============================================================*/
/* Table: exchange_rate                                         */
/*==============================================================*/
create table exchange_rate (
   exch_rate_id         int                  identity,
   curr_code            varchar(8)           not null,
   site_id              smallint             not null default 0,
   exchange_rate        decimal(11,4)        null,
   constraint PK_exchange_rate primary key nonclustered (exch_rate_id)
)
go

/*==============================================================*/
/* Table: exp_cell_det_hist                                     */
/*==============================================================*/
create table exp_cell_det_hist (
   exp_hist_cel_id      int                  identity,
   exp_hist_row_id      int                  not null default 0,
   exp_subctgy_id       int                  not null default 0,
   exp_amt              decimal(8,2)         not null default 0.00,
   exp_desc             varchar(255)         null,
   constraint PK_exp_cell_det_hist primary key nonclustered (exp_hist_cel_id),
   constraint uk_exp_cell_det_hist unique (exp_subctgy_id, exp_hist_row_id)
)
go

/*==============================================================*/
/* Table: exp_row_det_hist                                      */
/*==============================================================*/
create table exp_row_det_hist (
   exp_hist_row_id      int                  identity,
   exp_hist_id          int                  not null default 0,
   exp_date             datetime             not null default 0000-00-00,
   proj_id              smallint             null,
   constraint PK_exp_row_det_hist primary key nonclustered (exp_hist_row_id)
)
go

/*==============================================================*/
/* Table: expense                                               */
/*==============================================================*/
create table expense (
   exp_no               varchar(16)          not null,
   ta_no                varchar(16)          null,
   exp_dep              smallint             not null default 0,
   exp_title            varchar(50)          not null,
   exp_desc             varchar(255)         null,
   exp_status           int                  not null default 0,
   exp_ctgy_id          int                  not null default 0,
   exp_curr_cd          varchar(8)           not null,
   exp_amt              decimal(9,2)         not null default 0.00,
   base_curr_cd         varchar(8)           not null,
   exchange_rate        decimal(11,4)        not null default 0.0000,
   requestor            int                  not null default 0,
   req_date             datetime             null,
   creator              int                  not null default 0,
   create_date          datetime             not null default 0000-00-00,
   is_recharge          int                  not null default 0,
   list_id              varchar(32)          null,
   export_status        int                  not null default 0,
   approve_date         datetime             null,
   exp_confirm_amt      decimal(9,2)         not null default 0.00,
   email_date           datetime             null,
   email_times          int                  not null default 0,
   confirm_date         datetime             null,
   exp_budget_id        int                  null,
   constraint PK_expense primary key nonclustered (exp_no)
)
go

/*==============================================================*/
/* Table: expense_approver                                      */
/*==============================================================*/
create table expense_approver (
   list_id              varchar(32)          not null,
   approver_id          int                  not null default 0,
   approve_seq          smallint             not null default 0,
   approve_status       int                  not null default 0,
   approve_date         datetime             null,
   approve_comment      varchar(255)         null,
   can_modify           int                  not null default 0,
   actual_approver      int                  null,
   your_turn_date       datetime             null,
   email_date           datetime             null,
   email_times          int                  not null default 0,
   constraint PK_expense_approver primary key nonclustered (list_id, approver_id)
)
go

/*==============================================================*/
/* Table: expense_attach                                        */
/*==============================================================*/
create table expense_attach (
   exp_attach_id        int                  identity,
   exp_no               varchar(16)          not null,
   file_title           varchar(50)          not null,
   file_name            varchar(50)          not null,
   file_content         image                null,
   upload_date          datetime             not null default 0000-00-00,
   cont_file_size       int                  not null default 0,
   constraint PK_expense_attach primary key nonclustered (exp_attach_id)
)
go

/*==============================================================*/
/* Table: expense_cell_det                                      */
/*==============================================================*/
create table expense_cell_det (
   exp_cell_id          int                  identity,
   exp_row_id           int                  not null default 0,
   exp_subctgy_id       int                  not null default 0,
   exp_amt              decimal(8,2)         not null default 0.00,
   exp_desc             varchar(255)         null,
   constraint PK_expense_cell_det primary key nonclustered (exp_cell_id),
   constraint uk_expense_cell_det unique (exp_subctgy_id, exp_row_id)
)
go

/*==============================================================*/
/* Table: expense_claim                                         */
/*==============================================================*/
create table expense_claim (
   exp_claim_id         int                  identity,
   exp_no               varchar(16)          not null,
   exp_subctgy_id       int                  not null default 0,
   exp_amt              decimal(9,2)         not null default 0.00,
   exp_desc             varchar(255)         not null,
   exp_proj_id          smallint             null,
   exp_proj_cd          varchar(8)           null,
   constraint PK_expense_claim primary key nonclustered (exp_claim_id),
   constraint uk_expense_claim unique (exp_subctgy_id, exp_no)
)
go

/*==============================================================*/
/* Table: expense_ctgy                                          */
/*==============================================================*/
create table expense_ctgy (
   exp_ctgy_id          int                  identity,
   exp_ctgy_desc        varchar(50)          not null,
   exp_ctgy_site        smallint             not null default 0,
   exp_ctgy_type        int                  not null default 0,
   ref_erp_no           varchar(8)           null,
   is_enabled           int                  not null default 1,
   constraint PK_expense_ctgy primary key nonclustered (exp_ctgy_id)
)
go

/*==============================================================*/
/* Table: expense_history                                       */
/*==============================================================*/
create table expense_history (
   exp_hist_id          int                  identity,
   exp_no               varchar(16)          not null,
   exp_status           int                  not null default 0,
   exchange_rate        decimal(11,4)        not null default 0.0000,
   list_id              varchar(32)          not null,
   constraint PK_expense_history primary key nonclustered (exp_hist_id)
)
go

/*==============================================================*/
/* Table: expense_recharge                                      */
/*==============================================================*/
create table expense_recharge (
   exp_recharge_id      int                  identity,
   exp_no               varchar(16)          not null,
   cust_cd              varchar(17)          null,
   person_dep_id        smallint             null,
   person_id            int                  null,
   amount               decimal(9,2)         not null default 0.00,
   constraint PK_expense_recharge primary key nonclustered (exp_recharge_id)
)
go

/*==============================================================*/
/* Table: expense_row_det                                       */
/*==============================================================*/
create table expense_row_det (
   exp_row_id           int                  identity,
   exp_no               varchar(16)          not null,
   exp_date             datetime             not null default 0000-00-00,
   proj_id              smallint             null,
   constraint PK_expense_row_det primary key nonclustered (exp_row_id)
)
go

/*==============================================================*/
/* Table: expense_subctgy                                       */
/*==============================================================*/
create table expense_subctgy (
   exp_subctgy_id       int                  identity,
   exp_ctgy_id          int                  not null default 0,
   exp_subctgy_des      varchar(50)          not null,
   ref_erp_no           varchar(8)           null,
   is_enabled           int                  not null default 1,
   is_hotel             int                  not null default 0,
   constraint PK_expense_subctgy primary key nonclustered (exp_subctgy_id)
)
go

/*==============================================================*/
/* Table: global_mail_rmd                                       */
/*==============================================================*/
create table global_mail_rmd (
   md_det_id            int                  not null default 0,
   due_day              smallint             not null default 3,
   interval_day         smallint             not null default 2,
   max_time             smallint             not null default 10,
   constraint PK_global_mail_rmd primary key nonclustered (md_det_id)
)
go

/*==============================================================*/
/* Table: global_para                                           */
/*==============================================================*/
create table global_para (
   global_para_id       int                  identity,
   smtp_addr            varchar(50)          null,
   smtp_user            varchar(50)          null,
   smtp_pwd             varchar(50)          null,
   min_pwd_len          smallint             null,
   pwd_expire_day       smallint             null,
   max_wrg_pwd_lmt      smallint             null,
   is_ldap_auth         int                  not null default 0,
   ldap_ser_nm          varchar(50)          null,
   ldap_ser_ip          varchar(50)          null,
   ldap_ser_port        smallint             null,
   ldap_root_dn         varchar(50)          null,
   ldap_username        varchar(50)          null,
   ldap_password        varchar(50)          null,
   ldap_query           varchar(50)          null,
   ldap_filter          varchar(50)          null,
   constraint PK_global_para primary key nonclustered (global_para_id)
)
go

/*==============================================================*/
/* Table: hotel                                                 */
/*==============================================================*/
create table hotel (
   hotel_id             int                  identity,
   hotel_nm             varchar(500)         null,
   hotel_site           smallint             null,
   hotel_city           smallint             null,
   hotel_addr           varchar(500)         null,
   hotel_curr           varchar(8)           null,
   hotel_desc           varchar(500)         null,
   hotel_tele           varchar(50)          null,
   hotel_fax            varchar(50)          null,
   hotel_level          int                  not null default 0,
   cont_fr_date         datetime             null,
   cont_to_date         datetime             null,
   promote_status       int                  not null default 0,
   is_enabled           int                  not null default 1,
   promote_msg          varchar(500)         null,
   promote_date         datetime             null,
   email_date           datetime             null,
   email_times          int                  not null default 0,
   hotel_contact        varchar(150)         null,
   hotel_email          varchar(100)         null,
   hotel_spec           varchar(500)         null,
   constraint PK_hotel primary key nonclustered (hotel_id)
)
go

/*==============================================================*/
/* Table: hotel_contract                                        */
/*==============================================================*/
create table hotel_contract (
   cont_id              int                  identity,
   hotel_id             int                  not null default 0,
   cont_file_nm         varchar(50)          not null,
   cont_file_desc       varchar(20)          not null,
   cont_filecontnt      image                null,
   cont_uploaddate      datetime             not null default 0000-00-00,
   cont_file_size       int                  not null default 0,
   constraint PK_hotel_contract primary key nonclustered (cont_id)
)
go

/*==============================================================*/
/* Table: hotel_room                                            */
/*==============================================================*/
create table hotel_room (
   room_id              int                  identity,
   hotel_id             int                  not null default 0,
   room_nm              varchar(50)          not null,
   room_price           decimal(9,2)         not null default 0.00,
   room_discount        smallint             null,
   room_desc            varchar(255)         null,
   is_enabled           int                  not null default 1,
   room_breakfast       varchar(255)         null,
   room_network         varchar(255)         null,
   constraint PK_hotel_room primary key nonclustered (room_id)
)
go

/*==============================================================*/
/* Table: kpi_sum_expctgy                                       */
/*==============================================================*/
create table kpi_sum_expctgy (
   kpi_sum_expctgy_id   int                  identity,
   kpi_summary_id       int                  not null default 0,
   site_id              smallint             not null default 0,
   summary_date         datetime             not null default 0000-00-00,
   pr_categry_id        int                  not null default 0,
   pr_created           int                  not null default 0,
   constraint PK_kpi_sum_expctgy primary key nonclustered (kpi_sum_expctgy_id)
)
go

/*==============================================================*/
/* Table: kpi_sum_purctgy                                       */
/*==============================================================*/
create table kpi_sum_purctgy (
   kpi_sum_purctgy_id   int                  identity,
   kpi_summary_id       int                  not null default 0,
   site_id              smallint             not null default 0,
   summary_date         datetime             not null default 0000-00-00,
   pr_categry_id        int                  not null default 0,
   pr_created           int                  not null default 0,
   constraint PK_kpi_sum_purctgy primary key nonclustered (kpi_sum_purctgy_id)
)
go

/*==============================================================*/
/* Table: kpi_summary                                           */
/*==============================================================*/
create table kpi_summary (
   kpi_summary_id       int                  identity,
   summary_date         datetime             not null default 0000-00-00,
   login_user_count     int                  not null default 0,
   ta_closed            int                  not null default 0,
   avg_ta_closed_days   decimal(9,2)         not null default 0.00,
   ta_created           int                  not null default 0,
   capex_closed         int                  not null default 0,
   avg_ce_closed_days   decimal(9,2)         not null default 0.00,
   capex_created        int                  not null default 0,
   pr_closed            int                  not null default 0,
   avg_pr_closed_days   decimal(9,2)         not null default 0.00,
   pr_created           int                  not null default 0,
   expense_closed       int                  not null default 0,
   avg_ex_closed_days   decimal(9,2)         not null default 0.00,
   expense_created      int                  not null default 0,
   constraint PK_kpi_summary primary key nonclustered (kpi_summary_id)
)
go

/*==============================================================*/
/* Table: menu                                                  */
/*==============================================================*/
create table menu (
   menu_id              int                  identity,
   menu_nm              varchar(100)         not null,
   menu_desc            varchar(255)         null,
   func_id              int                  null,
   url                  varchar(255)         null,
   parent_id            int                  null,
   onclick              varchar(100)         null,
   onmouse_over         varchar(100)         null,
   onmouse_down         varchar(100)         null,
   sec_menu_nm          varchar(100)         not null,
   sec_menu_desc        varchar(255)         null,
   constraint PK_menu primary key nonclustered (menu_id)
)
go

/*==============================================================*/
/* Table: metadata_det                                          */
/*==============================================================*/
create table metadata_det (
   md_det_id            int                  not null default 0,
   md_mst_id            int                  not null default 0,
   eng_full_desc        varchar(255)         not null,
   sec_full_desc        varchar(255)         not null,
   eng_short_desc       varchar(255)         not null,
   sec_short_desc       varchar(255)         not null,
   color                varchar(20)          null,
   constraint PK_metadata_det primary key nonclustered (md_det_id, md_mst_id)
)
go

/*==============================================================*/
/* Table: metadata_mst                                          */
/*==============================================================*/
create table metadata_mst (
   md_mst_id            int                  not null default 0,
   md_mst_desc          varchar(80)          null,
   constraint PK_metadata_mst primary key nonclustered (md_mst_id)
)
go

/*==============================================================*/
/* Table: oa_function                                           */
/*==============================================================*/
create table oa_function (
   func_id              int                  not null default 0,
   func_nm              varchar(50)          not null,
   func_desc            varchar(255)         null,
   constraint PK_oa_function primary key nonclustered (func_id)
)
go

/*==============================================================*/
/* Table: oa_user                                               */
/*==============================================================*/
create table oa_user (
   user_id              int                  identity,
   user_cd              varchar(20)          not null,
   user_pwd             varchar(32)          not null,
   user_nm              varchar(50)          not null,
   gender               int                  not null default 0,
   email                varchar(150)         not null,
   tele_no              varchar(50)          null,
   primary_site         smallint             not null default 0,
   pwd_hint_quest       varchar(255)         null,
   pwd_hint_answer      varchar(255)         null,
   last_login_time      datetime             null,
   login_fail_time      smallint             not null default 0,
   is_enabled           int                  not null default 1,
   locale               varchar(20)          null,
   constraint PK_oa_user primary key nonclustered (user_id),
   constraint uk_user_cd unique (user_cd)
)
go

/*==============================================================*/
/* Table: payment_schdl_det                                     */
/*==============================================================*/
create table payment_schdl_det (
   psd_id               int                  identity,
   po_no                varchar(16)          not null,
   psd_desc             varchar(255)         not null,
   psd_date             datetime             not null default 0000-00-00,
   psd_amt              decimal(14,2)        not null default 0.00,
   constraint PK_payment_schdl_det primary key nonclustered (psd_id)
)
go

/*==============================================================*/
/* Table: po_approver                                           */
/*==============================================================*/
create table po_approver (
   list_id              varchar(32)          not null,
   approver_id          int                  not null default 0,
   actual_approver      int                  null,
   your_turn_date       datetime             null,
   email_date           datetime             null,
   email_times          int                  not null default 0,
   approve_seq          smallint             not null default 0,
   approve_status       int                  not null default 0,
   approve_date         datetime             null,
   approve_comment      varchar(255)         null,
   can_modify           int                  not null default 0,
   constraint PK_po_approver primary key nonclustered (list_id, approver_id)
)
go

/*==============================================================*/
/* Table: po_attach                                             */
/*==============================================================*/
create table po_attach (
   po_attach_id         int                  identity,
   po_no                varchar(16)          null,
   file_title           varchar(50)          not null,
   file_name            varchar(50)          not null,
   file_content         image                null,
   upload_date          datetime             not null default 0000-00-00,
   cont_file_size       int                  not null default 0,
   constraint PK_po_attach primary key nonclustered (po_attach_id)
)
go

/*==============================================================*/
/* Table: po_history                                            */
/*==============================================================*/
create table po_history (
   po_his_id            int                  identity,
   po_no                varchar(16)          null,
   po_status            int                  not null default 0,
   base_amount          decimal(14,2)        not null default 0.00,
   list_id              varchar(32)          not null,
   exchange_rate        decimal(11,4)        not null default 0.0000,
   constraint PK_po_history primary key nonclustered (po_his_id)
)
go

/*==============================================================*/
/* Table: po_item                                               */
/*==============================================================*/
create table po_item (
   po_item_id           int                  identity,
   po_no                varchar(16)          null,
   supp_id              int                  null,
   supp_nm              varchar(50)          null,
   item_id              int                  null,
   item_sepc            varchar(255)         not null,
   exch_rate_id         int                  not null default 0,
   unit_price           decimal(12,2)        not null default 0.00,
   quantity             int                  not null default 0,
   due_date             datetime             null,
   buy_for_user         int                  null,
   buy_for_dep          smallint             null,
   is_recharge          int                  not null default 0,
   pur_type_cd          varchar(8)           null,
   receive_qty          int                  not null default 0,
   return_qty           int                  not null default 0,
   cancel_qty           int                  not null default 0,
   pr_item_id           int                  null,
   description          varchar(24)          null,
   proj_id              smallint             null,
   constraint PK_po_item primary key nonclustered (po_item_id)
)
go

/*==============================================================*/
/* Index: fk_po_item_to_currency                                */
/*==============================================================*/
create index fk_po_item_to_currency on po_item (
exch_rate_id ASC
)
go

/*==============================================================*/
/* Table: po_item_attach                                        */
/*==============================================================*/
create table po_item_attach (
   po_item_att_id       int                  identity,
   po_item_id           int                  null default 0,
   file_title           varchar(50)          not null,
   file_name            varchar(50)          not null,
   file_content         image                null,
   upload_date          datetime             not null default 0000-00-00,
   cont_file_size       int                  not null default 0,
   constraint PK_po_item_attach primary key nonclustered (po_item_att_id)
)
go

/*==============================================================*/
/* Table: po_item_cancel                                        */
/*==============================================================*/
create table po_item_cancel (
   cancel_id            int                  identity,
   po_item_id           int                  not null default 0,
   cancel_date          datetime             not null default 0000-00-00,
   cancel_qty           int                  not null default 0,
   cancel_user          int                  not null default 0,
   export_status        int                  not null default 0,
   constraint PK_po_item_cancel primary key nonclustered (cancel_id)
)
go

/*==============================================================*/
/* Table: po_item_history                                       */
/*==============================================================*/
create table po_item_history (
   po_item_his_id       int                  identity,
   po_his_id            int                  not null default 0,
   supp_id              int                  null,
   item_id              int                  null,
   exch_rate_id         int                  not null default 0,
   unit_price           decimal(12,2)        not null default 0.00,
   quantity             int                  not null default 0,
   due_date             datetime             null,
   buy_for_user         int                  null,
   is_recharge          int                  not null default 0,
   pur_type_cd          varchar(8)           null,
   item_sepc            varchar(255)         not null,
   buy_for_dep          smallint             null,
   proj_id              smallint             null,
   constraint PK_po_item_history primary key nonclustered (po_item_his_id)
)
go

/*==============================================================*/
/* Index: fk_po_item_history_to_currency                        */
/*==============================================================*/
create index fk_po_item_history_to_currency on po_item_history (
exch_rate_id ASC
)
go

/*==============================================================*/
/* Table: po_item_receipt                                       */
/*==============================================================*/
create table po_item_receipt (
   rcpt_id              int                  identity,
   po_item_id           int                  not null default 0,
   receiver1            int                  not null default 0,
   rcpt_date1           datetime             null,
   rcpt_qty1            int                  null,
   receiver2            int                  not null default 0,
   rcpt_date2           datetime             null,
   rcpt_qty2            int                  null,
   export_status        int                  not null default 0,
   constraint PK_po_item_receipt primary key nonclustered (rcpt_id)
)
go

/*==============================================================*/
/* Table: po_item_recharge                                      */
/*==============================================================*/
create table po_item_recharge (
   po_itm_rechrgid      int                  identity,
   po_item_id           int                  not null default 0,
   cust_cd              varchar(17)          null,
   person_dep_id        smallint             null,
   person_id            int                  null,
   amount               decimal(9,2)         not null default 0.00,
   constraint PK_po_item_recharge primary key nonclustered (po_itm_rechrgid)
)
go

/*==============================================================*/
/* Table: pr_approver                                           */
/*==============================================================*/
create table pr_approver (
   list_id              varchar(32)          not null,
   approver_id          int                  not null default 0,
   approve_seq          smallint             not null default 0,
   approve_status       int                  not null default 0,
   approve_date         datetime             null,
   approve_comment      varchar(255)         null,
   can_modify           int                  not null default 0,
   actual_approver      int                  null,
   your_turn_date       datetime             null,
   email_date           datetime             null,
   email_times          int                  not null default 0,
   constraint PK_pr_approver primary key nonclustered (list_id, approver_id)
)
go

/*==============================================================*/
/* Table: pr_attach                                             */
/*==============================================================*/
create table pr_attach (
   pr_attach_id         int                  identity,
   pr_no                varchar(16)          null,
   file_title           varchar(50)          not null,
   file_name            varchar(50)          not null,
   file_content         image                null,
   upload_date          datetime             not null default 0000-00-00,
   cont_file_size       int                  not null default 0,
   constraint PK_pr_attach primary key nonclustered (pr_attach_id)
)
go

/*==============================================================*/
/* Table: pr_authorized_purchaser                               */
/*==============================================================*/
create table pr_authorized_purchaser (
   pr_auth_pur_id       int                  identity,
   pr_no                varchar(16)          not null,
   purchaser            int                  not null default 0,
   constraint PK_pr_authorized_purchaser primary key nonclustered (pr_auth_pur_id),
   constraint uk_pr_authorized_purchaser unique (pr_no, purchaser)
)
go

/*==============================================================*/
/* Table: pr_history                                            */
/*==============================================================*/
create table pr_history (
   pr_hist_id           int                  identity,
   pr_no                varchar(16)          not null,
   pr_status            int                  not null default 0,
   list_id              varchar(32)          not null,
   constraint PK_pr_history primary key nonclustered (pr_hist_id)
)
go

/*==============================================================*/
/* Table: pr_item                                               */
/*==============================================================*/
create table pr_item (
   pr_item_id           int                  identity,
   pr_no                varchar(16)          not null,
   supp_nm              varchar(50)          null,
   item_id              int                  null,
   item_sepc            varchar(255)         not null,
   exch_rate_id         int                  not null default 0,
   exchange_rate        decimal(11,4)        not null default 0.0000,
   unit_price           decimal(12,2)        not null default 0.00,
   quantity             int                  not null default 0,
   due_date             datetime             null,
   buy_for_dep          smallint             null,
   buy_for_user         int                  null,
   supp_id              int                  null,
   is_recharge          int                  not null default 0,
   po_item_id           int                  null,
   proj_id              smallint             null,
   constraint PK_pr_item primary key nonclustered (pr_item_id)
)
go

/*==============================================================*/
/* Table: pr_item_attach                                        */
/*==============================================================*/
create table pr_item_attach (
   pr_item_att_id       int                  identity,
   pr_item_id           int                  null default 0,
   file_title           varchar(50)          not null,
   file_name            varchar(50)          not null,
   file_content         image                null,
   upload_date          datetime             not null default 0000-00-00,
   cont_file_size       int                  not null default 0,
   constraint PK_pr_item_attach primary key nonclustered (pr_item_att_id)
)
go

/*==============================================================*/
/* Table: pr_item_history                                       */
/*==============================================================*/
create table pr_item_history (
   pr_item_hist_id      int                  identity,
   pr_hist_id           int                  not null default 0,
   supp_nm              varchar(50)          null,
   item_id              int                  null,
   item_sepc            varchar(255)         not null,
   exch_rate_id         int                  not null default 0,
   exchange_rate        decimal(11,4)        not null default 0.0000,
   unit_price           decimal(12,2)        not null default 0.00,
   quantity             int                  not null default 0,
   due_date             datetime             null,
   buy_for_dep          smallint             null,
   buy_for_user         int                  null,
   supp_id              int                  null,
   is_recharge          int                  not null default 0,
   proj_id              smallint             null,
   constraint PK_pr_item_history primary key nonclustered (pr_item_hist_id)
)
go

/*==============================================================*/
/* Index: fk_pr_item_history_to_currency                        */
/*==============================================================*/
create index fk_pr_item_history_to_currency on pr_item_history (
exch_rate_id ASC
)
go

/*==============================================================*/
/* Table: pr_item_recharge                                      */
/*==============================================================*/
create table pr_item_recharge (
   pr_itm_rechrgid      int                  identity,
   pr_item_id           int                  not null default 0,
   cust_cd              varchar(17)          null,
   person_dep_id        smallint             null,
   person_id            int                  null,
   amount               decimal(9,2)         not null default 0.00,
   constraint PK_pr_item_recharge primary key nonclustered (pr_itm_rechrgid)
)
go

/*==============================================================*/
/* Table: proj_cd                                               */
/*==============================================================*/
create table proj_cd (
   proj_id              smallint             identity,
   proj_cd              varchar(8)           not null,
   is_enabled           int                  not null default 1,
   proj_site            smallint             not null,
   description          varchar(18)          null,
   constraint PK_proj_cd primary key nonclustered (proj_id)
)
go

/*==============================================================*/
/* Table: province                                              */
/*==============================================================*/
create table province (
   province_id          smallint             identity,
   country_id           smallint             not null default 0,
   eng_name             varchar(50)          not null,
   sec_name             varchar(50)          not null,
   is_enabled           int                  not null default 1,
   maint_site           smallint             null,
   constraint PK_province primary key nonclustered (province_id)
)
go

/*==============================================================*/
/* Table: purchase_ctgy                                         */
/*==============================================================*/
create table purchase_ctgy (
   pur_ctgy_id          int                  identity,
   pur_ctgy_desc        varchar(50)          not null,
   pur_ctgy_site        smallint             null default 0,
   is_enabled           int                  not null default 1,
   constraint PK_purchase_ctgy primary key nonclustered (pur_ctgy_id)
)
go

/*==============================================================*/
/* Table: purchase_order                                        */
/*==============================================================*/
create table purchase_order (
   po_no                varchar(16)          not null,
   po_site              smallint             not null default 0,
   ref_erp_no           varchar(16)          null,
   po_title             varchar(50)          not null,
   po_desc              varchar(500)         null,
   supp_id              int                  not null default 0,
   pur_subctgy_id       int                  null,
   pur_amount           decimal(14,2)        not null default 0.00,
   base_curr_cd         varchar(8)           not null,
   exchange_rate        decimal(11,4)        not null default 0.0000,
   po_status            int                  not null default 0,
   create_user          int                  not null default 0,
   create_date          datetime             not null default 0000-00-00,
   request_date         datetime             null,
   export_status        int                  not null default 0,
   purchaser            int                  null,
   list_id              varchar(32)          null,
   receiver             varchar(50)          null,
   receive_addr         varchar(255)         null,
   exch_rate_id         int                  not null default 0,
   inspector            int                  null,
   row_version          int                  not null default 0,
   confirmer            int                  null,
   email_date           datetime             null,
   email_times          int                  not null default 0,
   approve_date         datetime             null,
   confirm_date         datetime             null,
   constraint PK_purchase_order primary key nonclustered (po_no)
)
go

/*==============================================================*/
/* Index: fk_purchase_order_to_currency2                        */
/*==============================================================*/
create index fk_purchase_order_to_currency2 on purchase_order (
base_curr_cd ASC
)
go

/*==============================================================*/
/* Table: purchase_request                                      */
/*==============================================================*/
create table purchase_request (
   pr_no                varchar(16)          not null,
   pr_title             varchar(50)          not null,
   pr_desc              varchar(500)         null,
   pur_subctgy_id       int                  not null default 0,
   pr_dep_id            smallint             not null default 0,
   pr_status            int                  not null default 0,
   pr_capex_no          varchar(16)          null,
   pr_budget_id         int                  null,
   base_curr_cd         varchar(8)           not null,
   requestor            int                  not null default 0,
   req_date             datetime             null,
   creator              int                  not null default 0,
   create_date          datetime             not null default 0000-00-00,
   pr_purchaser         int                  null,
   list_id              varchar(32)          null,
   base_amt             decimal(11,2)        null default 0.00,
   approve_date         datetime             null,
   row_version          int                  not null default 0,
   is_delegate          int                  not null default 0,
   email_date           datetime             null,
   email_times          int                  not null default 0,
   constraint PK_purchase_request primary key nonclustered (pr_no)
)
go

/*==============================================================*/
/* Table: purchase_subctgy                                      */
/*==============================================================*/
create table purchase_subctgy (
   pur_subctgy_id       int                  identity,
   pur_ctgy_id          int                  not null default 0,
   def_supplier         int                  null,
   pur_subctgy_des      varchar(50)          not null,
   is_enabled           int                  not null default 1,
   inspector            int                  null,
   constraint PK_purchase_subctgy primary key nonclustered (pur_subctgy_id)
)
go

/*==============================================================*/
/* Table: purchase_type                                         */
/*==============================================================*/
create table purchase_type (
   pur_type_cd          varchar(8)           not null,
   pur_type_desc        varchar(50)          null,
   pur_type_site        smallint             not null default 0,
   is_enabled           int                  not null default 1,
   constraint PK_purchase_type primary key nonclustered (pur_type_cd)
)
go

/*==============================================================*/
/* Table: role                                                  */
/*==============================================================*/
create table role (
   role_id              int                  identity,
   role_nm              varchar(50)          not null,
   role_desc            varchar(255)         null,
   role_type            int                  null,
   constraint PK_role primary key nonclustered (role_id)
)
go

/*==============================================================*/
/* Table: role_function                                         */
/*==============================================================*/
create table role_function (
   role_id              int                  not null default 0,
   func_id              int                  not null default 0,
   constraint PK_role_function primary key nonclustered (func_id, role_id)
)
go

/*==============================================================*/
/* Table: rules                                                */
/*==============================================================*/
create table rules (
   rule_id              int                  identity,
   rule_type            int                  not null default 0,
   rule_desc            varchar(255)         not null,
   rule_site            smallint             not null default 0,
   is_enabled           int                  not null default 0,
   rule_top             int                  null default 0,
   rule_left            int                  null default 0,
   constraint PK_rule primary key nonclustered (rule_id)
)
go

/*==============================================================*/
/* Table: rule_condition                                        */
/*==============================================================*/
create table rule_condition (
   rule_id              int                  not null default 0,
   condition_type       int                  not null default 0,
   compare_symbol       int                  not null default 0,
   compare_value        varchar(50)          not null,
   constraint PK_rule_condition primary key nonclustered (rule_id, condition_type)
)
go

/*==============================================================*/
/* Table: rule_consequence                                      */
/*==============================================================*/
create table rule_consequence (
   rule_id              int                  not null default 0,
   user_id              int                  not null default 0,
   seq_no               tinyint              not null default 0,
   can_modify           int                  not null default 0,
   superior             int                  null,
   constraint PK_rule_consequence primary key nonclustered (user_id, rule_id)
)
go

/*==============================================================*/
/* Table: rule_flow                                             */
/*==============================================================*/
create table rule_flow (
   flow_id              int                  identity,
   flow_type            int                  not null default 0,
   flow_site            smallint             not null default 0,
   flow_desc            varchar(255)         not null,
   is_enabled           int                  not null default 0,
   constraint PK_rule_flow primary key nonclustered (flow_id)
)
go

/*==============================================================*/
/* Table: rule_flow_rule                                        */
/*==============================================================*/
create table rule_flow_rule (
   flow_id              int                  not null default 0,
   seq_no               tinyint              not null default 0,
   rule_id              int                  not null default 0,
   pass_next            tinyint              not null default 0,
   fail_next            tinyint              not null default 0,
   constraint PK_rule_flow_rule primary key nonclustered (flow_id, seq_no)
)
go

/*==============================================================*/
/* Table: site                                                  */
/*==============================================================*/
create table site (
   site_id              smallint             identity,
   site_nm              varchar(50)          not null,
   site_mgr             int                  null,
   site_curr            varchar(8)           not null,
   site_logo            image                null,
   can_recharge         int                  not null default 0,
   is_enabled           int                  not null default 1,
   site_city            smallint             not null default 0,
   site_activity        varchar(50)          null,
   constraint PK_site primary key nonclustered (site_id)
)
go

/*==============================================================*/
/* Table: site_mail_rmd                                         */
/*==============================================================*/
create table site_mail_rmd (
   site_id              smallint             not null default 0,
   md_det_id            int                  not null default 0,
   due_day              smallint             not null default 3,
   interval_day         smallint             not null default 2,
   max_time             smallint             not null default 10,
   constraint PK_site_mail_rmd primary key nonclustered (site_id, md_det_id)
)
go

/*==============================================================*/
/* Table: supplier                                              */
/*==============================================================*/
create table supplier (
   supp_id              int                  identity,
   supp_cd              varchar(8)           not null,
   supp_site            smallint             null,
   supp_nm              varchar(50)          null,
   supp_address1        varchar(50)          null,
   supp_address2        varchar(50)          null,
   supp_address3        varchar(50)          null,
   supp_city            smallint             null,
   supp_post            varchar(9)           null,
   supp_attention1      varchar(24)          null,
   supp_attention2      varchar(24)          null,
   supp_tele1           varchar(16)          null,
   supp_ext1            varchar(4)           null,
   supp_tele2           varchar(16)          null,
   supp_ext2            varchar(4)           null,
   supp_fax1            varchar(16)          null,
   supp_fax2            varchar(16)          null,
   supp_pur_accnt       varchar(8)           null,
   supp_pur_subant      varchar(8)           null,
   supp_pur_cstcen      varchar(8)           null,
   supp_ap_account      varchar(8)           null,
   supp_ap_subacnt      varchar(8)           null,
   supp_ap_cstcen       varchar(8)           null,
   supp_bank            varchar(8)           null,
   supp_curr_code       varchar(8)           null,
   supp_cr_terms        varchar(20)          null,
   supp_contact         varchar(20)          null,
   cont_fr_date         datetime             null,
   cont_to_date         datetime             null,
   is_enabled           int                  not null default 1,
   export_status        int                  not null default 0,
   last_exp_file        varchar(30)          null,
   is_air_tkt_supp      int                  not null default 0,
   is_confirmed         int                  not null default 1,
   promote_status       int                  not null default 1,
   promote_msg          varchar(500)         null,
   promote_date         datetime             null,
   confirm_date         datetime             null,
   supp_cnfm_sta        int                  not null default 1,
   email_date           datetime             null,
   email_times          int                  not null default 0,
   supp_country         smallint             not null default 1,
   last_modify_date     datetime             null,
   constraint PK_supplier primary key nonclustered (supp_id)
)
go

/*==============================================================*/
/* Table: supplier_contract                                     */
/*==============================================================*/
create table supplier_contract (
   cont_id              int                  identity,
   supp_id              int                  not null default 0,
   cont_file_nm         varchar(50)          not null,
   cont_file_desc       varchar(20)          not null,
   cont_filecontnt      image                null,
   cont_uploaddate      datetime             not null default 0000-00-00,
   cont_file_size       int                  not null default 0,
   constraint PK_supplier_contract primary key nonclustered (cont_id)
)
go

/*==============================================================*/
/* Table: supplier_history                                      */
/*==============================================================*/
create table supplier_history (
   supp_id              int                  not null default 0,
   supp_cd              varchar(8)           not null,
   supp_site            smallint             null,
   supp_name            varchar(50)          null,
   supp_address1        varchar(50)          null,
   supp_address2        varchar(50)          null,
   supp_address3        varchar(50)          null,
   supp_city            smallint             null,
   supp_post            varchar(9)           null,
   supp_attention1      varchar(24)          null,
   supp_attention2      varchar(24)          null,
   supp_tele1           varchar(16)          null,
   supp_ext1            varchar(4)           null,
   supp_tele2           varchar(16)          null,
   supp_ext2            varchar(4)           null,
   supp_fax1            varchar(16)          null,
   supp_fax2            varchar(16)          null,
   supp_pur_accnt       varchar(8)           null,
   supp_pur_subant      varchar(8)           null,
   supp_pur_cstcen      varchar(8)           null,
   supp_ap_account      varchar(8)           null,
   supp_ap_subacnt      varchar(8)           null,
   supp_ap_cstcen       varchar(8)           null,
   supp_bank            varchar(8)           null,
   supp_curr_code       varchar(8)           null,
   supp_cr_terms        varchar(20)          null,
   supp_contact         varchar(20)          null,
   cont_fr_date         datetime             null,
   cont_to_date         datetime             null,
   is_enabled           int                  not null default 1,
   is_air_tkt_supp      int                  not null default 0,
   promote_status       int                  not null default 1,
   confirm_date         datetime             not null default 0000-00-00,
   supp_country         smallint             not null default 1,
   constraint PK_supplier_history primary key nonclustered (supp_id)
)
go

/*==============================================================*/
/* Table: supplier_item                                         */
/*==============================================================*/
create table supplier_item (
   item_id              int                  identity,
   supp_id              int                  not null default 0,
   pur_subctgy_id       int                  not null default 0,
   curr_cd              varchar(8)           not null,
   item_sepc            varchar(255)         not null,
   item_unit_price      decimal(12,2)        not null default 0.00,
   is_enabled           int                  not null default 1,
   ref_erp_no           varchar(8)           null,
   constraint PK_supplier_item primary key nonclustered (item_id)
)
go

/*==============================================================*/
/* Table: system_log                                            */
/*==============================================================*/
create table system_log (
   log_id               int                  identity,
   site_id              smallint             not null default 0,
   object               varchar(50)          not null,
   obj_id               varchar(255)         not null,
   action               varchar(50)          not null,
   key_field            varchar(255)         null,
   action_time          datetime             not null default 0000-00-00,
   user_id              int                  null,
   constraint PK_system_log primary key nonclustered (log_id)
)
go

/*==============================================================*/
/* Table: ta_approver                                           */
/*==============================================================*/
create table ta_approver (
   list_id              varchar(32)          not null,
   approver_id          int                  not null default 0,
   actual_approver      int                  null,
   your_turn_date       datetime             null,
   email_date           datetime             null,
   email_times          int                  not null default 0000000000,
   approve_seq          smallint             not null default 0,
   approve_status       int                  not null default 0,
   approve_date         datetime             null,
   approve_comment      varchar(255)         null,
   can_modify           int                  not null default 0,
   constraint PK_ta_approver primary key nonclustered (list_id, approver_id)
)
go

/*==============================================================*/
/* Table: ta_history                                            */
/*==============================================================*/
create table ta_history (
   ta_his_id            int                  identity,
   ta_no                varchar(16)          not null,
   ta_status            int                  not null default 0,
   fr_city              smallint             not null default 0,
   to_city              smallint             not null default 0,
   hotel_id             int                  null,
   hotel_nm             varchar(50)          null,
   room_id              int                  null,
   room_desc            varchar(50)          null,
   chk_in_date          datetime             null,
   chk_out_date         datetime             null,
   travel_mode          int                  not null default 0,
   sgl_or_rtn           int                  not null default 0,
   travel_date_fr       datetime             null,
   travel_date_to       datetime             null,
   list_id              varchar(32)          null,
   leave_time           datetime             null,
   back_time            datetime             null,
   constraint PK_ta_history primary key nonclustered (ta_his_id)
)
go

/*==============================================================*/
/* Table: travel_application                                    */
/*==============================================================*/
create table travel_application (
   ta_no                varchar(16)          not null,
   ta_dep               smallint             not null default 0,
   ta_title             varchar(50)          not null,
   ta_desc              varchar(500)         null,
   ta_status            int                  not null default 0,
   fr_city              smallint             not null default 0,
   to_city              smallint             not null default 0,
   hotel_id             int                  null,
   hotel_nm             varchar(50)          null,
   room_id              int                  null,
   room_desc            varchar(50)          null,
   chk_in_date          datetime             null,
   chk_out_date         datetime             null,
   travel_mode          int                  not null default 0,
   sgl_or_rtn           int                  not null default 0,
   travel_date_fr       datetime             null,
   travel_date_to       datetime             null,
   leave_time           datetime             null,
   back_time            datetime             null,
   requestor            int                  not null default 0,
   creator              int                  not null default 0,
   req_date             datetime             null,
   booker               int                  null,
   list_id              varchar(32)          null,
   request_type         int                  not null default 0,
   book_type            int                  not null default 0,
   create_date          datetime             not null default 0000-00-00,
   approve_date         datetime             null,
   email_date           datetime             null,
   email_times          int                  not null default 0,
   ta_fee               decimal(14,2)        not null default 0.00,
   is_on_travel         int                  null,
   ta_curr_cd           varchar(8)           null,
   constraint PK_travel_application primary key nonclustered (ta_no)
)
go

/*==============================================================*/
/* Table: travel_group_det                                      */
/*==============================================================*/
create table travel_group_det (
   tvl_grp_id           int                  not null default 0,
   exp_subctgy_id       int                  not null default 0,
   domes_amt_limit      decimal(9,2)         null,
   overs_amt_limit      decimal(9,2)         null,
   constraint PK_travel_group_det primary key nonclustered (tvl_grp_id, exp_subctgy_id)
)
go

/*==============================================================*/
/* Table: travel_group_mst                                      */
/*==============================================================*/
create table travel_group_mst (
   tvl_grp_id           int                  identity,
   tvl_grp_nm           varchar(50)          null,
   tvl_grp_site         smallint             null,
   is_enabled           int                  not null default 1,
   constraint PK_travel_group_mst primary key nonclustered (tvl_grp_id)
)
go

/*==============================================================*/
/* Table: user_department                                       */
/*==============================================================*/
create table user_department (
   user_id              int                  not null default 0,
   dep_id               smallint             not null default 0,
   title                varchar(50)          null,
   constraint PK_user_department primary key nonclustered (user_id, dep_id)
)
go

/*==============================================================*/
/* Table: user_role                                             */
/*==============================================================*/
create table user_role (
   user_role_id         int                  identity,
   user_id              int                  not null default 0,
   role_id              int                  not null default 0,
   granted_dep          smallint             null,
   granted_site         smallint             null,
   constraint PK_user_role primary key nonclustered (user_role_id),
   constraint ux_user_role unique (role_id, granted_dep, user_id, granted_site)
)
go

/*==============================================================*/
/* Table: user_site                                             */
/*==============================================================*/
create table user_site (
   user_id              int                  not null default 0,
   site_id              smallint             not null default 0,
   tvl_grp_id           int                  not null default 0,
   constraint PK_user_site primary key nonclustered (user_id, site_id)
)
go

/*==============================================================*/
/* Table: yearly_bgt_dep_history                                */
/*==============================================================*/
create table yearly_bgt_dep_history (
   bg_hist_dep_id       int                  identity,
   budget_hist_id       int                  not null default 0,
   budget_dep_id        smallint             not null default 0,
   constraint PK_yearly_bgt_dep_history primary key nonclustered (bg_hist_dep_id),
   constraint uk_yearly_bgt_dep_history unique (budget_hist_id, budget_dep_id)
)
go

/*==============================================================*/
/* Table: yearly_budget                                         */
/*==============================================================*/
create table yearly_budget (
   budget_id            int                  identity,
   budget_site          smallint             not null default 0,
   budget_cd            varchar(20)          not null,
   budget_nm            varchar(50)          not null,
   budget_type          int                  not null default 0,
   budget_year          smallint             not null default 0,
   budget_amt           decimal(14,2)        not null default 0.00,
   budget_actulamt      decimal(14,2)        not null default 0.00,
   pur_ctgy_id          int                  null,
   pur_subctgy_id       int                  null,
   budget_version       smallint             not null default 0,
   budget_status        int                  not null default 0,
   is_freeze            int                  not null default 0,
   create_user          int                  not null default 0,
   create_date          datetime             not null default 0000-00-00,
   modify_user          int                  null,
   modify_date          datetime             null,
   [row_version]        int		               not null default 0,
   exp_ctgy_id          int                  null,
   exp_subctgy_id       int                  null,
   budget_duration_from datetime             null,
   budget_duration_to   datetime             null,
   constraint PK_yearly_budget primary key nonclustered (budget_id),
   constraint uk_yearly_budget unique (budget_cd, budget_site)
)
go

/*==============================================================*/
/* Table: yearly_budget_department                              */
/*==============================================================*/
create table yearly_budget_department (
   bg_dep_id            int                  identity,
   budget_id            int                  not null default 0,
   budget_dep_id        smallint             not null default 0,
   constraint PK_yearly_budget_department primary key nonclustered (bg_dep_id),
   constraint uk_yearly_budget_department unique (budget_id, budget_dep_id)
)
go

/*==============================================================*/
/* Table: yearly_budget_history                                 */
/*==============================================================*/
create table yearly_budget_history (
   budget_hist_id       int                  identity,
   budget_id            int                  not null default 0,
   budget_version       smallint             not null default 0,
   budget_nm            varchar(50)          not null,
   budget_amt           decimal(14,2)        not null default 0.00,
   create_user          int                  not null default 0,
   create_date          datetime             not null default 0000-00-00,
   constraint PK_yearly_budget_history primary key nonclustered (budget_hist_id),
   constraint uk_yearly_budget_history unique (budget_id, budget_version)
)
go

alter table air_ticket
   add constraint fk_air_ticket_to_exchange_rate foreign key (exch_rate_id)
      references exchange_rate (exch_rate_id)
go

alter table air_ticket
   add constraint fk_air_ticket_to_po_item foreign key (po_item_id)
      references po_item (po_item_id)
go

alter table air_ticket
   add constraint fk_air_ticket_to_po_item2 foreign key (rtn_po_item_id)
      references po_item (po_item_id)
go

alter table air_ticket
   add constraint fk_air_ticket_to_purchase_type foreign key (pur_type_cd)
      references purchase_type (pur_type_cd)
go

alter table air_ticket
   add constraint fk_air_ticket_to_supplier foreign key (supp_id)
      references supplier (supp_id)
go

alter table air_ticket
   add constraint fk_air_ticket_to_travel_application foreign key (ta_no)
      references travel_application (ta_no)
go

alter table air_ticket_recharge
   add constraint fk_air_ticket_recharge_to_air_ticket foreign key (air_ticket_id)
      references air_ticket (air_ticket_id)
go

alter table air_ticket_recharge
   add constraint fk_air_ticket_recharge_to_customer foreign key (cust_cd)
      references customer (cust_cd)
go

alter table air_ticket_recharge
   add constraint fk_air_ticket_recharge_to_department foreign key (person_dep_id)
      references department (dep_id)
go

alter table air_ticket_recharge
   add constraint fk_air_ticket_recharge_to_user foreign key (person_id)
      references oa_user (user_id)
go

alter table approver_delegate
   add constraint fk_approver_delegate_to_user foreign key (org_approver)
      references oa_user (user_id)
go

alter table approver_delegate
   add constraint fk_approver_delegate_to_user2 foreign key (dlgt_approver)
      references oa_user (user_id)
go

alter table capex
   add constraint fk_capex_to_capex_request foreign key (capex_req_id)
      references capex_request (capex_req_id)
go

alter table capex
   add constraint fk_capex_to_currency foreign key (base_curr_cd)
      references currency (curr_cd)
go

alter table capex
   add constraint fk_capex_to_purchase_ctgy foreign key (pur_ctgy_id)
      references purchase_ctgy (pur_ctgy_id)
go

alter table capex
   add constraint fk_capex_to_purchase_subctgy foreign key (pur_subctgy_id)
      references purchase_subctgy (pur_subctgy_id)
go

alter table capex
   add constraint fk_capex_to_site foreign key (capex_site)
      references site (site_id)
go

alter table capex
   add constraint fk_capex_to_yearly_budget foreign key (budget_id)
      references yearly_budget (budget_id)
go

alter table capex_department
   add constraint fk_capex_department_to_capex foreign key (capex_no)
      references capex (capex_no)
go

alter table capex_department
   add constraint fk_capex_department_to_department foreign key (capex_dep_id)
      references department (dep_id)
go

alter table capex_req_item_history
   add constraint fk_capex_req_item_history_to_capex_request_history foreign key (cr_hist_id)
      references capex_request_history (cr_hist_id)
go

alter table capex_req_item_history
   add constraint fk_capex_req_item_history_to_exchange_rate foreign key (exch_rate_id)
      references exchange_rate (exch_rate_id)
go

alter table capex_req_item_history
   add constraint fk_capex_req_item_history_to_purchase_subctgy foreign key (pur_subctgy_id)
      references purchase_subctgy (pur_subctgy_id)
go

alter table capex_req_item_history
   add constraint fk_capex_req_item_history_to_supplier foreign key (supp_id)
      references supplier (supp_id)
go

alter table capex_req_item_history
   add constraint fk_capex_req_item_history_to_supplier_item foreign key (item_id)
      references supplier_item (item_id)
go

alter table capex_request
   add constraint fk_capex_request_to_capex foreign key (capex_no)
      references capex (capex_no)
go

alter table capex_request
   add constraint fk_capex_request_to_currency foreign key (ref_curr_cd)
      references currency (curr_cd)
go

alter table capex_request
   add constraint fk_capex_request_to_user foreign key (requestor)
      references oa_user (user_id)
go

alter table capex_request
   add constraint fk_capex_request_to_user2 foreign key (proj_leader_id)
      references oa_user (user_id)
go

alter table capex_request_approver
   add constraint fk_capex_request_approver_to_user foreign key (approver_id)
      references oa_user (user_id)
go

alter table capex_request_approver
   add constraint fk_capex_request_approver_to_user2 foreign key (actual_approver)
      references oa_user (user_id)
go

alter table capex_request_attach
   add constraint fk_capex_request_attach_to_capex_request foreign key (capex_req_id)
      references capex_request (capex_req_id)
go

alter table capex_request_history
   add constraint fk_capex_request_history_to_capex_request foreign key (capex_req_id)
      references capex_request (capex_req_id)
go

alter table capex_request_item
   add constraint fk_capex_request_item_to_capex_request foreign key (capex_req_id)
      references capex_request (capex_req_id)
go

alter table capex_request_item
   add constraint fk_capex_request_item_to_exchange_rate foreign key (exch_rate_id)
      references exchange_rate (exch_rate_id)
go

alter table capex_request_item
   add constraint fk_capex_request_item_to_purchase_subctgy foreign key (pur_subctgy_id)
      references purchase_subctgy (pur_subctgy_id)
go

alter table capex_request_item
   add constraint fk_capex_request_item_to_supplier foreign key (supp_id)
      references supplier (supp_id)
go

alter table capex_request_item
   add constraint fk_capex_request_item_to_supplier_item foreign key (item_id)
      references supplier_item (item_id)
go

alter table city
   add constraint fk_city_to_province foreign key (province_id)
      references province (province_id)
go

alter table city
   add constraint fk_city_to_site foreign key (maint_site)
      references site (site_id)
go

alter table country
   add constraint fk_country_to_site foreign key (maint_site)
      references site (site_id)
go

alter table customer
   add constraint fk_customer_to_site foreign key (cust_site)
      references site (site_id)
go

alter table department
   add constraint fk_department_to_department foreign key (parent_id)
      references department (dep_id)
go

alter table department
   add constraint fk_department_to_site foreign key (dep_site)
      references site (site_id)
go

alter table department
   add constraint fk_department_to_user foreign key (dep_mgr)
      references oa_user (user_id)
go

alter table email
   add constraint email_to_site foreign key (site)
      references site (site_id)
go

alter table erp_tran
   add constraint fk_erp_tran_to_site foreign key (site_id)
      references site (site_id)
go

alter table exchange_rate
   add constraint fk_exchange_rate_to_currency foreign key (curr_code)
      references currency (curr_cd)
go

alter table exchange_rate
   add constraint fk_exchange_rate_to_site foreign key (site_id)
      references site (site_id)
go

alter table exp_cell_det_hist
   add constraint fk_exp_cell_det_hist_exp_row_det_hist foreign key (exp_hist_row_id)
      references exp_row_det_hist (exp_hist_row_id)
go

alter table exp_cell_det_hist
   add constraint fk_exp_cell_det_hist_to_expense_subctgy foreign key (exp_subctgy_id)
      references expense_subctgy (exp_subctgy_id)
go

alter table exp_row_det_hist
   add constraint FK_exp_row_det_hist_to_proj_cd foreign key (proj_id)
      references proj_cd (proj_id)
go

alter table exp_row_det_hist
   add constraint fk_exp_row_det_hist_to_expense_history foreign key (exp_hist_id)
      references expense_history (exp_hist_id)
go

alter table expense
   add constraint FK_expense_to_yearly_budget foreign key (exp_budget_id)
      references yearly_budget (budget_id)
go

alter table expense
   add constraint fk_expense_to_currency foreign key (exp_curr_cd)
      references currency (curr_cd)
go

alter table expense
   add constraint fk_expense_to_currency2 foreign key (base_curr_cd)
      references currency (curr_cd)
go

alter table expense
   add constraint fk_expense_to_department foreign key (exp_dep)
      references department (dep_id)
go

alter table expense
   add constraint fk_expense_to_expense_ctgy foreign key (exp_ctgy_id)
      references expense_ctgy (exp_ctgy_id)
go

alter table expense
   add constraint fk_expense_to_travel_application foreign key (ta_no)
      references travel_application (ta_no)
go

alter table expense
   add constraint fk_expense_to_user foreign key (requestor)
      references oa_user (user_id)
go

alter table expense
   add constraint fk_expense_to_user2 foreign key (creator)
      references oa_user (user_id)
go

alter table expense_approver
   add constraint fk_expense_approver_to_user foreign key (approver_id)
      references oa_user (user_id)
go

alter table expense_approver
   add constraint fk_expense_approver_to_user2 foreign key (actual_approver)
      references oa_user (user_id)
go

alter table expense_attach
   add constraint fk_expense_attach_to_expense foreign key (exp_no)
      references expense (exp_no)
go

alter table expense_cell_det
   add constraint fk_expense_cell_det_to_expense_row_det foreign key (exp_row_id)
      references expense_row_det (exp_row_id)
go

alter table expense_cell_det
   add constraint fk_expense_cell_det_to_expense_subctgy foreign key (exp_subctgy_id)
      references expense_subctgy (exp_subctgy_id)
go

alter table expense_claim
   add constraint FK_expense_claim_to_proj_cd foreign key (exp_proj_id)
      references proj_cd (proj_id)
go

alter table expense_claim
   add constraint fk_expense_claim_to_expense foreign key (exp_no)
      references expense (exp_no)
go

alter table expense_claim
   add constraint fk_expense_claim_to_expense_subctgy foreign key (exp_subctgy_id)
      references expense_subctgy (exp_subctgy_id)
go

alter table expense_ctgy
   add constraint fk_expense_ctgy_to_site foreign key (exp_ctgy_site)
      references site (site_id)
go

alter table expense_history
   add constraint fk_expense_history_to_expense foreign key (exp_no)
      references expense (exp_no)
go

alter table expense_recharge
   add constraint fk_expense_recharge_department foreign key (person_dep_id)
      references department (dep_id)
go

alter table expense_recharge
   add constraint fk_expense_recharge_to_customer foreign key (cust_cd)
      references customer (cust_cd)
go

alter table expense_recharge
   add constraint fk_expense_recharge_to_expense foreign key (exp_no)
      references expense (exp_no)
go

alter table expense_recharge
   add constraint fk_expense_recharge_to_user foreign key (person_id)
      references oa_user (user_id)
go

alter table expense_row_det
   add constraint FK_expense_row_det_to_proj_cd foreign key (proj_id)
      references proj_cd (proj_id)
go

alter table expense_row_det
   add constraint fk_expense_row_det_to_expense foreign key (exp_no)
      references expense (exp_no)
go

alter table expense_subctgy
   add constraint fk_expense_subctgy_to_expense_ctgy foreign key (exp_ctgy_id)
      references expense_ctgy (exp_ctgy_id)
go

alter table hotel
   add constraint fk_hotel_to_city foreign key (hotel_city)
      references city (city_id)
go

alter table hotel
   add constraint fk_hotel_to_currency foreign key (hotel_curr)
      references currency (curr_cd)
go

alter table hotel
   add constraint fk_hotel_to_site foreign key (hotel_site)
      references site (site_id)
go

alter table hotel_contract
   add constraint fk_hotel_contract_to_hotel foreign key (hotel_id)
      references hotel (hotel_id)
go

alter table hotel_room
   add constraint fk_hotel_room_to_hotel foreign key (hotel_id)
      references hotel (hotel_id)
go

alter table menu
   add constraint fk_menu_to_function foreign key (func_id)
      references oa_function (func_id)
go

alter table menu
   add constraint fk_menu_to_menu foreign key (parent_id)
      references menu (menu_id)
go

alter table metadata_det
   add constraint fk_metadata_det_to_metadata_mst foreign key (md_mst_id)
      references metadata_mst (md_mst_id)
go

alter table oa_user
   add constraint fk_user_to_site foreign key (primary_site)
      references site (site_id)
go

alter table payment_schdl_det
   add constraint fk_payment_schdl_det_to_purchase_order foreign key (po_no)
      references purchase_order (po_no)
go

alter table po_approver
   add constraint fk_po_approver_to_user foreign key (approver_id)
      references oa_user (user_id)
go

alter table po_approver
   add constraint fk_po_approver_to_user2 foreign key (actual_approver)
      references oa_user (user_id)
go

alter table po_attach
   add constraint fk_po_attach_to_purchase_order foreign key (po_no)
      references purchase_order (po_no)
go

alter table po_history
   add constraint fk_po_history_to_purchase_order foreign key (po_no)
      references purchase_order (po_no)
go

alter table po_item
   add constraint FK_po_item_to_proj_cd foreign key (proj_id)
      references proj_cd (proj_id)
go

alter table po_item
   add constraint fk_po_item_to_department foreign key (buy_for_dep)
      references department (dep_id)
go

alter table po_item
   add constraint fk_po_item_to_exchange_rate foreign key (exch_rate_id)
      references exchange_rate (exch_rate_id)
go

alter table po_item
   add constraint fk_po_item_to_pr_item foreign key (pr_item_id)
      references pr_item (pr_item_id)
go

alter table po_item
   add constraint fk_po_item_to_purchase_order foreign key (po_no)
      references purchase_order (po_no)
go

alter table po_item
   add constraint fk_po_item_to_purchase_type foreign key (pur_type_cd)
      references purchase_type (pur_type_cd)
go

alter table po_item
   add constraint fk_po_item_to_supplier foreign key (supp_id)
      references supplier (supp_id)
go

alter table po_item
   add constraint fk_po_item_to_supplier_item foreign key (item_id)
      references supplier_item (item_id)
go

alter table po_item
   add constraint fk_po_item_to_user foreign key (buy_for_user)
      references oa_user (user_id)
go

alter table po_item_attach
   add constraint fk_po_item_attach_to_po_item foreign key (po_item_id)
      references po_item (po_item_id)
go

alter table po_item_cancel
   add constraint fk_po_item_cancel_po_item foreign key (po_item_id)
      references po_item (po_item_id)
go

alter table po_item_cancel
   add constraint fk_po_item_cancel_to_user foreign key (cancel_user)
      references oa_user (user_id)
go

alter table po_item_history
   add constraint FK_po_item_history_to_proj_cd foreign key (proj_id)
      references proj_cd (proj_id)
go

alter table po_item_history
   add constraint fk_po_item_history_po_history foreign key (po_his_id)
      references po_history (po_his_id)
go

alter table po_item_history
   add constraint fk_po_item_history_purchase_type foreign key (pur_type_cd)
      references purchase_type (pur_type_cd)
go

alter table po_item_history
   add constraint fk_po_item_history_to_department foreign key (buy_for_dep)
      references department (dep_id)
go

alter table po_item_history
   add constraint fk_po_item_history_to_exchange_rate foreign key (exch_rate_id)
      references exchange_rate (exch_rate_id)
go

alter table po_item_history
   add constraint fk_po_item_history_to_supplier foreign key (supp_id)
      references supplier (supp_id)
go

alter table po_item_history
   add constraint fk_po_item_history_to_supplier_item foreign key (item_id)
      references supplier_item (item_id)
go

alter table po_item_history
   add constraint fk_po_item_history_to_user foreign key (buy_for_user)
      references oa_user (user_id)
go

alter table po_item_receipt
   add constraint fk_po_item_receipt_to_po_item foreign key (po_item_id)
      references po_item (po_item_id)
go

alter table po_item_receipt
   add constraint fk_po_item_receipt_to_user foreign key (receiver1)
      references oa_user (user_id)
go

alter table po_item_receipt
   add constraint fk_po_item_receipt_to_user2 foreign key (receiver2)
      references oa_user (user_id)
go

alter table po_item_recharge
   add constraint fk_po_item_recharge_to_customer foreign key (cust_cd)
      references customer (cust_cd)
go

alter table po_item_recharge
   add constraint fk_po_item_recharge_to_department foreign key (person_dep_id)
      references department (dep_id)
go

alter table po_item_recharge
   add constraint fk_po_item_recharge_to_po_item foreign key (po_item_id)
      references po_item (po_item_id)
go

alter table po_item_recharge
   add constraint fk_po_item_recharge_to_user foreign key (person_id)
      references oa_user (user_id)
go

alter table pr_approver
   add constraint fk_pr_approver_to_user foreign key (approver_id)
      references oa_user (user_id)
go

alter table pr_approver
   add constraint fk_pr_approver_to_user2 foreign key (actual_approver)
      references oa_user (user_id)
go

alter table pr_attach
   add constraint fk_pr_attach_to_purchase_request foreign key (pr_no)
      references purchase_request (pr_no)
go

alter table pr_authorized_purchaser
   add constraint fk_pr_authorized_purchaser_to_purchase_request foreign key (pr_no)
      references purchase_request (pr_no)
go

alter table pr_authorized_purchaser
   add constraint fk_pr_authorized_purchaser_to_user foreign key (purchaser)
      references oa_user (user_id)
go

alter table pr_history
   add constraint fk_pr_history_to_purchase_request foreign key (pr_no)
      references purchase_request (pr_no)
go

alter table pr_item
   add constraint FK_pr_item_to_proj_cd foreign key (proj_id)
      references proj_cd (proj_id)
go

alter table pr_item
   add constraint fk_pr_item_to_department foreign key (buy_for_dep)
      references department (dep_id)
go

alter table pr_item
   add constraint fk_pr_item_to_exchange_rate foreign key (exch_rate_id)
      references exchange_rate (exch_rate_id)
go

alter table pr_item
   add constraint fk_pr_item_to_po_item foreign key (po_item_id)
      references po_item (po_item_id)
go

alter table pr_item
   add constraint fk_pr_item_to_purchase_request foreign key (pr_no)
      references purchase_request (pr_no)
go

alter table pr_item
   add constraint fk_pr_item_to_supplier foreign key (supp_id)
      references supplier (supp_id)
go

alter table pr_item
   add constraint fk_pr_item_to_supplier_item foreign key (item_id)
      references supplier_item (item_id)
go

alter table pr_item
   add constraint fk_pr_item_to_user foreign key (buy_for_user)
      references oa_user (user_id)
go

alter table pr_item_attach
   add constraint fk_pr_item_attach_to_pr_item foreign key (pr_item_id)
      references pr_item (pr_item_id)
go

alter table pr_item_history
   add constraint FK_pr_item_history_to_proj_cd foreign key (proj_id)
      references proj_cd (proj_id)
go

alter table pr_item_history
   add constraint fk_pr_item_history_to_department foreign key (buy_for_dep)
      references department (dep_id)
go

alter table pr_item_history
   add constraint fk_pr_item_history_to_exchange_rate foreign key (exch_rate_id)
      references exchange_rate (exch_rate_id)
go

alter table pr_item_history
   add constraint fk_pr_item_history_to_pr_history foreign key (pr_hist_id)
      references pr_history (pr_hist_id)
go

alter table pr_item_history
   add constraint fk_pr_item_history_to_supplier foreign key (supp_id)
      references supplier (supp_id)
go

alter table pr_item_history
   add constraint fk_pr_item_history_to_supplier_item foreign key (item_id)
      references supplier_item (item_id)
go

alter table pr_item_history
   add constraint fk_pr_item_history_to_user foreign key (buy_for_user)
      references oa_user (user_id)
go

alter table pr_item_recharge
   add constraint fk_pr_item_recharge_to_customer foreign key (cust_cd)
      references customer (cust_cd)
go

alter table pr_item_recharge
   add constraint fk_pr_item_recharge_to_department foreign key (person_dep_id)
      references department (dep_id)
go

alter table pr_item_recharge
   add constraint fk_pr_item_recharge_to_pr_item foreign key (pr_item_id)
      references pr_item (pr_item_id)
go

alter table pr_item_recharge
   add constraint fk_pr_item_recharge_to_user foreign key (person_id)
      references oa_user (user_id)
go

alter table proj_cd
   add constraint FK_proj_cd_to_site foreign key (proj_site)
      references site (site_id)
go

alter table province
   add constraint fk_province_to_country foreign key (country_id)
      references country (country_id)
go

alter table province
   add constraint fk_province_to_site foreign key (maint_site)
      references site (site_id)
go

alter table purchase_ctgy
   add constraint fk_purchase_ctgy_to_site foreign key (pur_ctgy_site)
      references site (site_id)
go

alter table purchase_order
   add constraint fk_purchase_order_exchage_rate foreign key (exch_rate_id)
      references exchange_rate (exch_rate_id)
go

alter table purchase_order
   add constraint fk_purchase_order_to_currency foreign key (base_curr_cd)
      references currency (curr_cd)
go

alter table purchase_order
   add constraint fk_purchase_order_to_purchase_subctgy foreign key (pur_subctgy_id)
      references purchase_subctgy (pur_subctgy_id)
go

alter table purchase_order
   add constraint fk_purchase_order_to_site foreign key (po_site)
      references site (site_id)
go

alter table purchase_order
   add constraint fk_purchase_order_to_supplier foreign key (supp_id)
      references supplier (supp_id)
go

alter table purchase_order
   add constraint fk_purchase_order_to_user foreign key (create_user)
      references oa_user (user_id)
go

alter table purchase_order
   add constraint fk_purchase_order_to_user2 foreign key (purchaser)
      references oa_user (user_id)
go

alter table purchase_order
   add constraint fk_purchase_order_to_user3 foreign key (inspector)
      references oa_user (user_id)
go

alter table purchase_order
   add constraint fk_purchase_order_to_user4 foreign key (confirmer)
      references oa_user (user_id)
go

alter table purchase_request
   add constraint fk_purchase_request_to_capex foreign key (pr_capex_no)
      references capex (capex_no)
go

alter table purchase_request
   add constraint fk_purchase_request_to_currency foreign key (base_curr_cd)
      references currency (curr_cd)
go

alter table purchase_request
   add constraint fk_purchase_request_to_department foreign key (pr_dep_id)
      references department (dep_id)
go

alter table purchase_request
   add constraint fk_purchase_request_to_purchase_subctgy foreign key (pur_subctgy_id)
      references purchase_subctgy (pur_subctgy_id)
go

alter table purchase_request
   add constraint fk_purchase_request_to_user foreign key (requestor)
      references oa_user (user_id)
go

alter table purchase_request
   add constraint fk_purchase_request_to_user2 foreign key (creator)
      references oa_user (user_id)
go

alter table purchase_request
   add constraint fk_purchase_request_to_user3 foreign key (pr_purchaser)
      references oa_user (user_id)
go

alter table purchase_request
   add constraint fk_purchase_request_to_yearly_budget foreign key (pr_budget_id)
      references yearly_budget (budget_id)
go

alter table purchase_subctgy
   add constraint fk_purchase_subctgy_to_purchase_ctgy foreign key (pur_ctgy_id)
      references purchase_ctgy (pur_ctgy_id)
go

alter table purchase_subctgy
   add constraint fk_purchase_subctgy_to_supplier foreign key (def_supplier)
      references supplier (supp_id)
go

alter table purchase_subctgy
   add constraint fk_purchase_subctgy_to_user foreign key (inspector)
      references oa_user (user_id)
go

alter table purchase_type
   add constraint fk_purchase_type_to_site foreign key (pur_type_site)
      references site (site_id)
go

alter table role_function
   add constraint fk_role_function_to_function foreign key (func_id)
      references oa_function (func_id)
go

alter table role_function
   add constraint fk_role_function_to_role foreign key (role_id)
      references role (role_id)
go

alter table rules
   add constraint fk_rule_to_site foreign key (rule_site)
      references site (site_id)
go

alter table rule_condition
   add constraint fk_rule_condition_to_rule foreign key (rule_id)
      references rules (rule_id)
go

alter table rule_consequence
   add constraint fk_rule_consequence_to_rule foreign key (rule_id)
      references rules (rule_id)
go

alter table rule_consequence
   add constraint fk_rule_consequence_to_user foreign key (user_id)
      references oa_user (user_id)
go

alter table rule_consequence
   add constraint fk_rule_consequence_to_user2 foreign key (superior)
      references oa_user (user_id)
go

alter table rule_flow
   add constraint fk_rule_flow_to_site foreign key (flow_site)
      references site (site_id)
go

alter table rule_flow_rule
   add constraint fk_rule_flow_rule_to_flow foreign key (flow_id)
      references rule_flow (flow_id)
go

alter table rule_flow_rule
   add constraint fk_rule_flow_rule_to_rule foreign key (rule_id)
      references rules (rule_id)
go

alter table site
   add constraint fk_site_to_city foreign key (site_city)
      references city (city_id)
go

alter table site
   add constraint fk_site_to_currency foreign key (site_curr)
      references currency (curr_cd)
go

alter table site
   add constraint fk_site_to_user foreign key (site_mgr)
      references oa_user (user_id)
go

alter table site_mail_rmd
   add constraint fk_site_mail_rmd_to_site foreign key (site_id)
      references site (site_id)
go

alter table supplier
   add constraint fk_supplier_to_city foreign key (supp_city)
      references city (city_id)
go

alter table supplier
   add constraint fk_supplier_to_currency foreign key (supp_curr_code)
      references currency (curr_cd)
go

alter table supplier
   add constraint fk_supplier_to_site foreign key (supp_site)
      references site (site_id)
go

alter table supplier_contract
   add constraint fk_supplier_contract_to_supplier foreign key (supp_id)
      references supplier (supp_id)
go

alter table supplier_history
   add constraint fk_supplier_history_to_city foreign key (supp_city)
      references city (city_id)
go

alter table supplier_history
   add constraint fk_supplier_history_to_currency foreign key (supp_curr_code)
      references currency (curr_cd)
go

alter table supplier_history
   add constraint fk_supplier_history_to_site foreign key (supp_site)
      references site (site_id)
go

alter table supplier_history
   add constraint fk_supplier_history_to_supplier foreign key (supp_id)
      references supplier (supp_id)
go

alter table supplier_item
   add constraint fk_supplier_item_purchase_subctgy foreign key (pur_subctgy_id)
      references purchase_subctgy (pur_subctgy_id)
go

alter table supplier_item
   add constraint fk_supplier_item_to_currency foreign key (curr_cd)
      references currency (curr_cd)
go

alter table supplier_item
   add constraint fk_supplier_item_to_supplier foreign key (supp_id)
      references supplier (supp_id)
go

alter table system_log
   add constraint fk_system_log_to_site foreign key (site_id)
      references site (site_id)
go

alter table system_log
   add constraint fk_system_log_to_user foreign key (user_id)
      references oa_user (user_id)
go

alter table ta_approver
   add constraint fk_ta_approver_to_user foreign key (approver_id)
      references oa_user (user_id)
go

alter table ta_approver
   add constraint fk_ta_approver_to_user2 foreign key (actual_approver)
      references oa_user (user_id)
go

alter table ta_history
   add constraint fk_ta_history_to_city foreign key (fr_city)
      references city (city_id)
go

alter table ta_history
   add constraint fk_ta_history_to_city2 foreign key (to_city)
      references city (city_id)
go

alter table ta_history
   add constraint fk_ta_history_to_hotel foreign key (hotel_id)
      references hotel (hotel_id)
go

alter table ta_history
   add constraint fk_ta_history_to_hotel_room foreign key (room_id)
      references hotel_room (room_id)
go

alter table ta_history
   add constraint fk_ta_history_to_travel_application foreign key (ta_no)
      references travel_application (ta_no)
go

alter table travel_application
   add constraint FK_travel_application_to_currency foreign key (ta_curr_cd)
      references currency (curr_cd)
go

alter table travel_application
   add constraint fk_travel_application_to_city foreign key (fr_city)
      references city (city_id)
go

alter table travel_application
   add constraint fk_travel_application_to_city2 foreign key (to_city)
      references city (city_id)
go

alter table travel_application
   add constraint fk_travel_application_to_hotel foreign key (hotel_id)
      references hotel (hotel_id)
go

alter table travel_application
   add constraint fk_travel_application_to_hotel_room foreign key (room_id)
      references hotel_room (room_id)
go

alter table travel_application
   add constraint fk_travel_application_to_user foreign key (requestor)
      references oa_user (user_id)
go

alter table travel_application
   add constraint fk_travel_application_to_user2 foreign key (booker)
      references oa_user (user_id)
go

alter table travel_application
   add constraint fk_travel_application_to_user3 foreign key (creator)
      references oa_user (user_id)
go

alter table travel_group_det
   add constraint fk_travel_group_det_to_expense_subctgy foreign key (exp_subctgy_id)
      references expense_subctgy (exp_subctgy_id)
go

alter table travel_group_det
   add constraint fk_travel_group_det_to_travel_group_mst foreign key (tvl_grp_id)
      references travel_group_mst (tvl_grp_id)
go

alter table travel_group_mst
   add constraint fk_travel_group_mst_to_site foreign key (tvl_grp_site)
      references site (site_id)
go

alter table user_department
   add constraint fk_user_department_to_department foreign key (dep_id)
      references department (dep_id)
go

alter table user_department
   add constraint fk_user_department_to_user foreign key (user_id)
      references oa_user (user_id)
go

alter table user_role
   add constraint fk_user_role_to_department foreign key (granted_dep)
      references department (dep_id)
go

alter table user_role
   add constraint fk_user_role_to_role foreign key (role_id)
      references role (role_id)
go

alter table user_role
   add constraint fk_user_role_to_site foreign key (granted_site)
      references site (site_id)
go

alter table user_role
   add constraint fk_user_role_to_user foreign key (user_id)
      references oa_user (user_id)
go

alter table user_site
   add constraint fk_user_site_to_site foreign key (site_id)
      references site (site_id)
go

alter table user_site
   add constraint fk_user_site_to_travel_group_mst foreign key (tvl_grp_id)
      references travel_group_mst (tvl_grp_id)
go

alter table user_site
   add constraint fk_user_site_to_user foreign key (user_id)
      references oa_user (user_id)
go

alter table yearly_bgt_dep_history
   add constraint fk_yearly_bgt_dep_history_to_department foreign key (budget_dep_id)
      references department (dep_id)
go

alter table yearly_bgt_dep_history
   add constraint fk_yearly_bgt_dep_history_to_yearly_budget_history foreign key (budget_hist_id)
      references yearly_budget_history (budget_hist_id)
go

alter table yearly_budget
   add constraint FK_yearly_budget_to_expense_ctgy foreign key (exp_ctgy_id)
      references expense_ctgy (exp_ctgy_id)
go

alter table yearly_budget
   add constraint FK_yearly_budget_to_expense_subctgy foreign key (exp_subctgy_id)
      references expense_subctgy (exp_subctgy_id)
go

alter table yearly_budget
   add constraint fk_yearly_budget_to_purchase_ctgy foreign key (pur_ctgy_id)
      references purchase_ctgy (pur_ctgy_id)
go

alter table yearly_budget
   add constraint fk_yearly_budget_to_purchase_subctgy foreign key (pur_subctgy_id)
      references purchase_subctgy (pur_subctgy_id)
go

alter table yearly_budget
   add constraint fk_yearly_budget_to_site foreign key (budget_site)
      references site (site_id)
go

alter table yearly_budget
   add constraint fk_yearly_budget_to_user foreign key (create_user)
      references oa_user (user_id)
go

alter table yearly_budget
   add constraint fk_yearly_budget_to_user2 foreign key (modify_user)
      references oa_user (user_id)
go

alter table yearly_budget_department
   add constraint fk_yearly_budget_department_to_department foreign key (budget_dep_id)
      references department (dep_id)
go

alter table yearly_budget_department
   add constraint fk_yearly_budget_department_to_yearly_budget foreign key (budget_id)
      references yearly_budget (budget_id)
go

alter table yearly_budget_history
   add constraint fk_yearly_budget_history_to_user foreign key (create_user)
      references oa_user (user_id)
go

alter table yearly_budget_history
   add constraint fk_yearly_budget_history_to_yearly_budget foreign key (budget_id)
      references yearly_budget (budget_id)
go