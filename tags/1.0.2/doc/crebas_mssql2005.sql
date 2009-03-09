/*
 *ER/Studio 7.0 SQL Code Generation
 * Company :      ÑÓ·æ¹¤Ã³
 * Project :      DATA MODEL
 * Author :       ¶¡öÎ
 *
 * Date Created : Tuesday, February 24, 2009 14:21:42
 * Target DBMS : Microsoft SQL Server 2005
 */

USE master
go
CREATE DATABASE oa
go
USE oa
go
/* 
 * TABLE: air_ticket 
 */

CREATE TABLE air_ticket(
    air_ticket_id             int               IDENTITY(1,1),
    ta_no                     varchar(16)       NULL,
    po_item_id                int               NULL,
    rtn_po_item_id            int               NULL,
    supp_id                   int               NULL,
    lve_flt_no                varchar(20)       NULL,
    lve_flt_class             int               NULL,
    lve_flt_fr                smallint          NULL,
    lve_flt_to                smallint          NULL,
    lve_flt_depart            datetime          NULL,
    lve_flt_arrive            datetime          NULL,
    exch_rate_id              int               DEFAULT 0 NOT NULL,
    exchange_rate             decimal(11, 4)    DEFAULT 0.0000 NOT NULL,
    price                     decimal(8, 2)     NULL,
    is_recharge               int               DEFAULT 0 NOT NULL,
    status                    int               NULL,
    pur_type_cd               varchar(8)        NULL,
    boarding_pass_received    int               DEFAULT 1 NOT NULL,
    CONSTRAINT PK1 PRIMARY KEY CLUSTERED (air_ticket_id)
)
go



IF OBJECT_ID('air_ticket') IS NOT NULL
    PRINT '<<< CREATED TABLE air_ticket >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE air_ticket >>>'
go

/* 
 * TABLE: air_ticket_recharge 
 */

CREATE TABLE air_ticket_recharge(
    tkt_rechrg_id    int              IDENTITY(1,1),
    air_ticket_id    int              DEFAULT 0 NOT NULL,
    cust_cd          varchar(17)      NULL,
    person_dep_id    smallint         NULL,
    person_id        int              NULL,
    amount           decimal(9, 2)    DEFAULT 0.00 NOT NULL,
    CONSTRAINT PK2 PRIMARY KEY CLUSTERED (tkt_rechrg_id)
)
go



IF OBJECT_ID('air_ticket_recharge') IS NOT NULL
    PRINT '<<< CREATED TABLE air_ticket_recharge >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE air_ticket_recharge >>>'
go

/* 
 * TABLE: approver_delegate 
 */

CREATE TABLE approver_delegate(
    dlgt_id          int         IDENTITY(1,1),
    dlgt_type        int         DEFAULT 0 NOT NULL,
    org_approver     int         DEFAULT 0 NOT NULL,
    dlgt_approver    int         DEFAULT 0 NOT NULL,
    dlgt_fr_date     datetime    NULL,
    dlgt_to_date     datetime    NULL,
    CONSTRAINT PK3 PRIMARY KEY CLUSTERED (dlgt_id)
)
go



IF OBJECT_ID('approver_delegate') IS NOT NULL
    PRINT '<<< CREATED TABLE approver_delegate >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE approver_delegate >>>'
go

/* 
 * TABLE: capex 
 */

CREATE TABLE capex(
    capex_no           varchar(16)       NOT NULL,
    capex_site         smallint          DEFAULT 0 NOT NULL,
    pur_ctgy_id        int               NULL,
    pur_subctgy_id     int               NULL,
    base_curr_cd       varchar(8)        NOT NULL,
    budget_id          int               NULL,
    capex_req_id       int               NULL,
    capex_actualamt    decimal(14, 2)    DEFAULT 0.00 NOT NULL,
    row_version        int               DEFAULT 0 NOT NULL,
    capex_year         int               DEFAULT 0 NOT NULL,
    CONSTRAINT PK4 PRIMARY KEY CLUSTERED (capex_no)
)
go



IF OBJECT_ID('capex') IS NOT NULL
    PRINT '<<< CREATED TABLE capex >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE capex >>>'
go

/* 
 * TABLE: capex_department 
 */

CREATE TABLE capex_department(
    capex_depart_id    int            IDENTITY(1,1),
    capex_no           varchar(16)    NOT NULL,
    capex_dep_id       smallint       DEFAULT 0 NOT NULL,
    CONSTRAINT PK5 PRIMARY KEY CLUSTERED (capex_depart_id)
)
go



IF OBJECT_ID('capex_department') IS NOT NULL
    PRINT '<<< CREATED TABLE capex_department >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE capex_department >>>'
go

/* 
 * TABLE: capex_req_item_history 
 */

CREATE TABLE capex_req_item_history(
    cr_item_hist_id    int               IDENTITY(1,1),
    cr_hist_id         int               DEFAULT 0 NOT NULL,
    pur_subctgy_id     int               DEFAULT 0 NOT NULL,
    supp_id            int               NULL,
    supp_nm            varchar(50)       NULL,
    item_id            int               NULL,
    item_sepc          varchar(255)      NOT NULL,
    exch_rate_id       int               DEFAULT 0 NOT NULL,
    exchange_rate      decimal(11, 4)    DEFAULT 0.0000 NOT NULL,
    unit_price         decimal(12, 2)    DEFAULT 0.00 NOT NULL,
    quantity           int               DEFAULT 0 NOT NULL,
    CONSTRAINT PK6 PRIMARY KEY CLUSTERED (cr_item_hist_id)
)
go



IF OBJECT_ID('capex_req_item_history') IS NOT NULL
    PRINT '<<< CREATED TABLE capex_req_item_history >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE capex_req_item_history >>>'
go

/* 
 * TABLE: capex_request 
 */

CREATE TABLE capex_request(
    capex_req_id          int               IDENTITY(1,1),
    capex_no              varchar(16)       NOT NULL,
    cr_type               int               DEFAULT 0 NOT NULL,
    cr_title              varchar(50)       NOT NULL,
    cr_desc               text              NULL,
    cr_status             int               DEFAULT 0 NOT NULL,
    base_amt              decimal(14, 2)    DEFAULT 0.00 NOT NULL,
    creator               int               DEFAULT 0 NOT NULL,
    create_date           datetime          NOT NULL,
    requestor             int               DEFAULT 0 NOT NULL,
    req_date              datetime          NULL,
    list_id               varchar(32)       NULL,
    activity              varchar(50)       NULL,
    post_audit_date       datetime          NULL,
    first_exp_date        datetime          NULL,
    last_exp_date         datetime          NULL,
    compleation_date      datetime          NULL,
    proj_leader_id        int               NULL,
    accounting_cd         varchar(32)       NULL,
    is_capex_type         int               DEFAULT 0 NOT NULL,
    is_asset_type         int               DEFAULT 0 NOT NULL,
    other_type            varchar(100)      NULL,
    is_new_imp_reason     int               DEFAULT 0 NOT NULL,
    is_cpt_inc_reason     int               DEFAULT 0 NOT NULL,
    is_cst_imp_reason     int               DEFAULT 0 NOT NULL,
    is_new_pdt_reason     int               DEFAULT 0 NOT NULL,
    is_downsiz_reason     int               DEFAULT 0 NOT NULL,
    is_hse_reason         int               DEFAULT 0 NOT NULL,
    other_reason          varchar(100)      NULL,
    capex_capital_amt     decimal(14, 2)    DEFAULT 0.00 NOT NULL,
    other_expense_amt     decimal(14, 2)    DEFAULT 0.00 NOT NULL,
    asset_disposal_amt    decimal(14, 2)    DEFAULT 0.00 NOT NULL,
    gross_book_amt        decimal(14, 2)    DEFAULT 0.00 NOT NULL,
    proj_impct_non1       varchar(100)      NULL,
    proj_impct_non2       varchar(100)      NULL,
    proj_impct_other1     varchar(100)      NULL,
    proj_impct_other2     varchar(100)      NULL,
    proj_impct_other3     varchar(100)      NULL,
    ref_curr_cd           varchar(8)        DEFAULT 'EUR' NOT NULL,
    ref_exchange_rate     decimal(11, 4)    DEFAULT 0.0000 NOT NULL,
    lst_forecast_amt      decimal(14, 2)    NULL,
    discount_cash_flow    decimal(9, 2)     NULL,
    npv_lc                decimal(14, 2)    NULL,
    internal_rtn_rate     decimal(5, 2)     NULL,
    discount_rate         decimal(5, 2)     NULL,
    proj_leader_title     varchar(100)      NULL,
    approve_date          datetime          NULL,
    CONSTRAINT PK7 PRIMARY KEY CLUSTERED (capex_req_id)
)
go



IF OBJECT_ID('capex_request') IS NOT NULL
    PRINT '<<< CREATED TABLE capex_request >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE capex_request >>>'
go

/* 
 * TABLE: capex_request_approver 
 */

CREATE TABLE capex_request_approver(
    list_id            varchar(32)     NOT NULL,
    approver_id        int             DEFAULT 0 NOT NULL,
    approve_seq        smallint        DEFAULT 0 NOT NULL,
    approve_status     int             DEFAULT 0 NOT NULL,
    approve_date       datetime        NULL,
    approve_comment    varchar(255)    NULL,
    can_modify         int             DEFAULT 0 NOT NULL,
    actual_approver    int             NULL,
    your_turn_date     datetime        NULL,
    email_date         datetime        NULL,
    email_times        int             DEFAULT 0 NOT NULL,
    CONSTRAINT PK8 PRIMARY KEY CLUSTERED (list_id, approver_id)
)
go



IF OBJECT_ID('capex_request_approver') IS NOT NULL
    PRINT '<<< CREATED TABLE capex_request_approver >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE capex_request_approver >>>'
go

/* 
 * TABLE: capex_request_attach 
 */

CREATE TABLE capex_request_attach(
    attach_id         int             IDENTITY(1,1),
    capex_req_id      int             DEFAULT 0 NOT NULL,
    file_title        varchar(50)     NOT NULL,
    file_name         varchar(50)     NOT NULL,
    file_content      image    				NOT NULL,
    upload_date       datetime        NOT NULL,
    cont_file_size    int             DEFAULT 0 NOT NULL,
    CONSTRAINT PK9 PRIMARY KEY CLUSTERED (attach_id)
)
go



IF OBJECT_ID('capex_request_attach') IS NOT NULL
    PRINT '<<< CREATED TABLE capex_request_attach >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE capex_request_attach >>>'
go

/* 
 * TABLE: capex_request_history 
 */

CREATE TABLE capex_request_history(
    cr_hist_id      int               IDENTITY(1,1),
    capex_req_id    int               DEFAULT 0 NOT NULL,
    capex_status    int               DEFAULT 0 NOT NULL,
    base_amt        decimal(14, 2)    DEFAULT 0.00 NOT NULL,
    list_id         char(32)          NOT NULL,
    CONSTRAINT PK10 PRIMARY KEY CLUSTERED (cr_hist_id)
)
go



IF OBJECT_ID('capex_request_history') IS NOT NULL
    PRINT '<<< CREATED TABLE capex_request_history >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE capex_request_history >>>'
go

/* 
 * TABLE: capex_request_item 
 */

CREATE TABLE capex_request_item(
    cr_item_id        int               IDENTITY(1,1),
    capex_req_id      int               DEFAULT 0 NOT NULL,
    pur_subctgy_id    int               DEFAULT 0 NOT NULL,
    supp_id           int               NULL,
    supp_nm           varchar(50)       NULL,
    item_id           int               NULL,
    item_sepc         varchar(255)      NOT NULL,
    exch_rate_id      int               DEFAULT 0 NOT NULL,
    exchange_rate     decimal(11, 4)    DEFAULT 0.0000 NOT NULL,
    unit_price        decimal(12, 2)    DEFAULT 0.00 NOT NULL,
    quantity          int               DEFAULT 0 NOT NULL,
    CONSTRAINT PK11 PRIMARY KEY CLUSTERED (cr_item_id)
)
go



IF OBJECT_ID('capex_request_item') IS NOT NULL
    PRINT '<<< CREATED TABLE capex_request_item >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE capex_request_item >>>'
go

/* 
 * TABLE: city 
 */

CREATE TABLE city(
    city_id        smallint       IDENTITY(1,1),
    province_id    smallint       DEFAULT 0 NOT NULL,
    eng_name       varchar(50)    NOT NULL,
    sec_name       varchar(50)    NOT NULL,
    is_enabled     int            DEFAULT 1 NOT NULL,
    maint_site     smallint       NULL,
    CONSTRAINT PK12 PRIMARY KEY CLUSTERED (city_id)
)
go



IF OBJECT_ID('city') IS NOT NULL
    PRINT '<<< CREATED TABLE city >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE city >>>'
go

/* 
 * TABLE: country 
 */

CREATE TABLE country(
    country_id    smallint       IDENTITY(1,1),
    short_name    varchar(3)     NOT NULL,
    eng_name      varchar(50)    NOT NULL,
    sec_name      varchar(50)    NOT NULL,
    is_enabled    int            DEFAULT 1 NOT NULL,
    maint_site    smallint       NULL,
    CONSTRAINT PK13 PRIMARY KEY CLUSTERED (country_id)
)
go



IF OBJECT_ID('country') IS NOT NULL
    PRINT '<<< CREATED TABLE country >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE country >>>'
go

/* 
 * TABLE: currency 
 */

CREATE TABLE currency(
    curr_cd       varchar(8)     NOT NULL,
    curr_nm       varchar(50)    NOT NULL,
    is_enabled    int            DEFAULT 1 NOT NULL,
    CONSTRAINT PK14 PRIMARY KEY CLUSTERED (curr_cd)
)
go



IF OBJECT_ID('currency') IS NOT NULL
    PRINT '<<< CREATED TABLE currency >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE currency >>>'
go

/* 
 * TABLE: customer 
 */

CREATE TABLE customer(
    cust_cd       varchar(17)    NOT NULL,
    cust_site     smallint       DEFAULT 0 NOT NULL,
    cust_desc     varchar(50)    NOT NULL,
    cust_type     int            DEFAULT 0 NOT NULL,
    is_enabled    int            DEFAULT 0 NOT NULL,
    CONSTRAINT PK15 PRIMARY KEY CLUSTERED (cust_cd)
)
go



IF OBJECT_ID('customer') IS NOT NULL
    PRINT '<<< CREATED TABLE customer >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE customer >>>'
go

/* 
 * TABLE: department 
 */

CREATE TABLE department(
    dep_id        smallint        IDENTITY(1,1),
    dep_site      smallint        DEFAULT 0 NOT NULL,
    parent_id     smallint        NULL,
    dep_mgr       int             NULL,
    is_enabled    int             DEFAULT 1 NOT NULL,
    dep_name      varchar(100)    NULL,
    CONSTRAINT PK16 PRIMARY KEY CLUSTERED (dep_id)
)
go



IF OBJECT_ID('department') IS NOT NULL
    PRINT '<<< CREATED TABLE department >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE department >>>'
go

/* 
 * TABLE: email 
 */

CREATE TABLE email(
    email_id        int             IDENTITY(1,1),
    mail_fr         varchar(100)    NOT NULL,
    mail_to         varchar(100)    NOT NULL,
    subject         varchar(100)    NOT NULL,
    body            text            NULL,
    create_time     datetime        NOT NULL,
    send_time       datetime        NULL,
    fail_count      int             DEFAULT 0 NOT NULL,
    wait_to_send    int             DEFAULT 1 NOT NULL,
    site            smallint        DEFAULT 0 NOT NULL,
    CONSTRAINT PK17 PRIMARY KEY CLUSTERED (email_id)
)
go



IF OBJECT_ID('email') IS NOT NULL
    PRINT '<<< CREATED TABLE email >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE email >>>'
go

/* 
 * TABLE: email_batch 
 */

CREATE TABLE email_batch(
    email_batch_id    int             IDENTITY(1,1),
    mail_to_user      int             DEFAULT 0 NOT NULL,
    body              text            NULL,
    template_name     varchar(100)    NOT NULL,
    site              smallint        DEFAULT 0 NOT NULL,
    is_send           int             DEFAULT 0 NOT NULL,
    ref_no            varchar(16)     NULL,
    CONSTRAINT PK18 PRIMARY KEY CLUSTERED (email_batch_id)
)
go



IF OBJECT_ID('email_batch') IS NOT NULL
    PRINT '<<< CREATED TABLE email_batch >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE email_batch >>>'
go

/* 
 * TABLE: erp_tran 
 */

CREATE TABLE erp_tran(
    site_id             smallint        DEFAULT 0 NOT NULL,
    start_time          datetime        NULL,
    time_per_day        smallint        NULL,
    interval_min        smallint        NULL,
    succ_mail_to        varchar(150)    NULL,
    fail_mail_to        varchar(150)    NULL,
    export_file_type    int             DEFAULT 1 NOT NULL,
    serv_addr           varchar(50)     NULL,
    serv_port           int             NULL,
    serv_user           varchar(50)     NULL,
    serv_pwd            varchar(50)     NULL,
    serv_dir            varchar(255)    NULL,
    CONSTRAINT PK19 PRIMARY KEY CLUSTERED (site_id)
)
go



IF OBJECT_ID('erp_tran') IS NOT NULL
    PRINT '<<< CREATED TABLE erp_tran >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE erp_tran >>>'
go

/* 
 * TABLE: exchange_rate 
 */

CREATE TABLE exchange_rate(
    exch_rate_id     int               IDENTITY(1,1),
    curr_code        varchar(8)        NOT NULL,
    site_id          smallint          DEFAULT 0 NOT NULL,
    exchange_rate    decimal(11, 4)    NULL,
    CONSTRAINT PK20 PRIMARY KEY CLUSTERED (exch_rate_id)
)
go



IF OBJECT_ID('exchange_rate') IS NOT NULL
    PRINT '<<< CREATED TABLE exchange_rate >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE exchange_rate >>>'
go

/* 
 * TABLE: exp_cell_det_hist 
 */

CREATE TABLE exp_cell_det_hist(
    exp_hist_cel_id    int              IDENTITY(1,1),
    exp_hist_row_id    int              DEFAULT 0 NOT NULL,
    exp_subctgy_id     int              DEFAULT 0 NOT NULL,
    exp_amt            decimal(8, 2)    DEFAULT 0.00 NOT NULL,
    exp_desc           varchar(255)     NULL,
    CONSTRAINT PK21 PRIMARY KEY CLUSTERED (exp_hist_cel_id)
)
go



IF OBJECT_ID('exp_cell_det_hist') IS NOT NULL
    PRINT '<<< CREATED TABLE exp_cell_det_hist >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE exp_cell_det_hist >>>'
go

/* 
 * TABLE: exp_row_det_hist 
 */

CREATE TABLE exp_row_det_hist(
    exp_hist_row_id    int         IDENTITY(1,1),
    exp_hist_id        int         DEFAULT 0 NOT NULL,
    exp_date           datetime    NOT NULL,
    proj_id            smallint    NULL,
    CONSTRAINT PK22 PRIMARY KEY CLUSTERED (exp_hist_row_id)
)
go



IF OBJECT_ID('exp_row_det_hist') IS NOT NULL
    PRINT '<<< CREATED TABLE exp_row_det_hist >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE exp_row_det_hist >>>'
go

/* 
 * TABLE: expense 
 */

CREATE TABLE expense(
    exp_no             varchar(16)       NOT NULL,
    ta_no              varchar(16)       NULL,
    exp_dep            smallint          DEFAULT 0 NOT NULL,
    exp_title          varchar(50)       NOT NULL,
    exp_desc           text              NULL,
    exp_status         int               DEFAULT 0 NOT NULL,
    exp_ctgy_id        int               DEFAULT 0 NOT NULL,
    exp_curr_cd        varchar(8)        NOT NULL,
    exp_amt            decimal(9, 2)     DEFAULT 0.00 NOT NULL,
    base_curr_cd       varchar(8)        NOT NULL,
    exchange_rate      decimal(11, 4)    DEFAULT 0.0000 NOT NULL,
    requestor          int               DEFAULT 0 NOT NULL,
    req_date           datetime          NULL,
    creator            int               DEFAULT 0 NOT NULL,
    create_date        datetime          NOT NULL,
    is_recharge        int               DEFAULT 0 NOT NULL,
    list_id            varchar(32)       NULL,
    export_status      int               DEFAULT 0 NOT NULL,
    approve_date       datetime          NULL,
    exp_confirm_amt    decimal(9, 2)     DEFAULT 0.00 NOT NULL,
    email_date         datetime          NULL,
    email_times        int               DEFAULT 0 NOT NULL,
    confirm_date       datetime          NULL,
    CONSTRAINT PK23 PRIMARY KEY CLUSTERED (exp_no)
)
go



IF OBJECT_ID('expense') IS NOT NULL
    PRINT '<<< CREATED TABLE expense >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE expense >>>'
go

/* 
 * TABLE: expense_approver 
 */

CREATE TABLE expense_approver(
    list_id            varchar(32)     NOT NULL,
    approver_id        int             DEFAULT 0 NOT NULL,
    approve_seq        smallint        DEFAULT 0 NOT NULL,
    approve_status     int             DEFAULT 0 NOT NULL,
    approve_date       datetime        NULL,
    approve_comment    varchar(255)    NULL,
    can_modify         int             DEFAULT 0 NOT NULL,
    actual_approver    int             NULL,
    your_turn_date     datetime        NULL,
    email_date         datetime        NULL,
    email_times        int             DEFAULT 0 NOT NULL,
    CONSTRAINT PK24 PRIMARY KEY CLUSTERED (list_id, approver_id)
)
go



IF OBJECT_ID('expense_approver') IS NOT NULL
    PRINT '<<< CREATED TABLE expense_approver >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE expense_approver >>>'
go

/* 
 * TABLE: expense_attach 
 */

CREATE TABLE expense_attach(
    exp_attach_id     int             IDENTITY(1,1),
    exp_no            varchar(16)     NOT NULL,
    file_title        varchar(50)     NOT NULL,
    file_name         varchar(50)     NOT NULL,
    file_content      image    				NOT NULL,
    upload_date       datetime        NOT NULL,
    cont_file_size    int             DEFAULT 0 NOT NULL,
    CONSTRAINT PK25 PRIMARY KEY CLUSTERED (exp_attach_id)
)
go



IF OBJECT_ID('expense_attach') IS NOT NULL
    PRINT '<<< CREATED TABLE expense_attach >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE expense_attach >>>'
go

/* 
 * TABLE: expense_cell_det 
 */

CREATE TABLE expense_cell_det(
    exp_cell_id       int              IDENTITY(1,1),
    exp_row_id        int              DEFAULT 0 NOT NULL,
    exp_subctgy_id    int              DEFAULT 0 NOT NULL,
    exp_amt           decimal(8, 2)    DEFAULT 0.00 NOT NULL,
    exp_desc          varchar(255)     NULL,
    CONSTRAINT PK26 PRIMARY KEY CLUSTERED (exp_cell_id)
)
go



IF OBJECT_ID('expense_cell_det') IS NOT NULL
    PRINT '<<< CREATED TABLE expense_cell_det >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE expense_cell_det >>>'
go

/* 
 * TABLE: expense_claim 
 */

CREATE TABLE expense_claim(
    exp_claim_id      int              IDENTITY(1,1),
    exp_no            varchar(16)      NOT NULL,
    exp_subctgy_id    int              DEFAULT 0 NOT NULL,
    exp_amt           decimal(9, 2)    DEFAULT 0.00 NOT NULL,
    exp_desc          text             NOT NULL,
    exp_proj_id       smallint         NULL,
    exp_proj_cd       varchar(8)       NULL,
    CONSTRAINT PK27 PRIMARY KEY CLUSTERED (exp_claim_id)
)
go



IF OBJECT_ID('expense_claim') IS NOT NULL
    PRINT '<<< CREATED TABLE expense_claim >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE expense_claim >>>'
go

/* 
 * TABLE: expense_ctgy 
 */

CREATE TABLE expense_ctgy(
    exp_ctgy_id      int            IDENTITY(1,1),
    exp_ctgy_desc    varchar(50)    NOT NULL,
    exp_ctgy_site    smallint       DEFAULT 0 NOT NULL,
    exp_ctgy_type    int            DEFAULT 0 NOT NULL,
    ref_erp_no       varchar(8)     NULL,
    is_enabled       int            DEFAULT 1 NOT NULL,
    CONSTRAINT PK28 PRIMARY KEY CLUSTERED (exp_ctgy_id)
)
go



IF OBJECT_ID('expense_ctgy') IS NOT NULL
    PRINT '<<< CREATED TABLE expense_ctgy >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE expense_ctgy >>>'
go

/* 
 * TABLE: expense_history 
 */

CREATE TABLE expense_history(
    exp_hist_id      int               IDENTITY(1,1),
    exp_no           varchar(16)       NOT NULL,
    exp_status       int               DEFAULT 0 NOT NULL,
    exchange_rate    decimal(11, 4)    DEFAULT 0.0000 NOT NULL,
    list_id          varchar(32)       NOT NULL,
    CONSTRAINT PK29 PRIMARY KEY CLUSTERED (exp_hist_id)
)
go



IF OBJECT_ID('expense_history') IS NOT NULL
    PRINT '<<< CREATED TABLE expense_history >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE expense_history >>>'
go

/* 
 * TABLE: expense_recharge 
 */

CREATE TABLE expense_recharge(
    exp_recharge_id    int              IDENTITY(1,1),
    exp_no             varchar(16)      NOT NULL,
    cust_cd            varchar(17)      NULL,
    person_dep_id      smallint         NULL,
    person_id          int              NULL,
    amount             decimal(9, 2)    DEFAULT 0.00 NOT NULL,
    CONSTRAINT PK30 PRIMARY KEY CLUSTERED (exp_recharge_id)
)
go



IF OBJECT_ID('expense_recharge') IS NOT NULL
    PRINT '<<< CREATED TABLE expense_recharge >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE expense_recharge >>>'
go

/* 
 * TABLE: expense_row_det 
 */

CREATE TABLE expense_row_det(
    exp_row_id    int            IDENTITY(1,1),
    exp_no        varchar(16)    NOT NULL,
    exp_date      datetime       NOT NULL,
    proj_id       smallint       NULL,
    CONSTRAINT PK31 PRIMARY KEY CLUSTERED (exp_row_id)
)
go



IF OBJECT_ID('expense_row_det') IS NOT NULL
    PRINT '<<< CREATED TABLE expense_row_det >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE expense_row_det >>>'
go

/* 
 * TABLE: expense_subctgy 
 */

CREATE TABLE expense_subctgy(
    exp_subctgy_id     int            IDENTITY(1,1),
    exp_ctgy_id        int            DEFAULT 0 NOT NULL,
    exp_subctgy_des    varchar(50)    NOT NULL,
    ref_erp_no         varchar(8)     NULL,
    is_enabled         int            DEFAULT 1 NOT NULL,
    is_hotel           int            DEFAULT 0 NOT NULL,
    CONSTRAINT PK32 PRIMARY KEY CLUSTERED (exp_subctgy_id)
)
go



IF OBJECT_ID('expense_subctgy') IS NOT NULL
    PRINT '<<< CREATED TABLE expense_subctgy >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE expense_subctgy >>>'
go

/* 
 * TABLE: global_mail_rmd 
 */

CREATE TABLE global_mail_rmd(
    md_det_id       int         DEFAULT 0 NOT NULL,
    due_day         smallint    DEFAULT 3 NOT NULL,
    interval_day    smallint    DEFAULT 2 NOT NULL,
    max_time        smallint    DEFAULT 10 NOT NULL,
    CONSTRAINT PK33 PRIMARY KEY CLUSTERED (md_det_id)
)
go



IF OBJECT_ID('global_mail_rmd') IS NOT NULL
    PRINT '<<< CREATED TABLE global_mail_rmd >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE global_mail_rmd >>>'
go

/* 
 * TABLE: global_para 
 */

CREATE TABLE global_para(
    global_para_id     int            IDENTITY(1,1),
    smtp_addr          varchar(50)    NULL,
    smtp_user          varchar(50)    NULL,
    smtp_pwd           varchar(50)    NULL,
    min_pwd_len        smallint       NULL,
    pwd_expire_day     smallint       NULL,
    max_wrg_pwd_lmt    smallint       NULL,
    is_ldap_auth       int            DEFAULT 0 NOT NULL,
    ldap_ser_nm        varchar(50)    NULL,
    ldap_ser_ip        varchar(50)    NULL,
    ldap_ser_port      smallint       NULL,
    ldap_root_dn       varchar(50)    NULL,
    ldap_username      varchar(50)    NULL,
    ldap_password      varchar(50)    NULL,
    ldap_query         varchar(50)    NULL,
    ldap_filter        varchar(50)    NULL,
    CONSTRAINT PK34 PRIMARY KEY CLUSTERED (global_para_id)
)
go



IF OBJECT_ID('global_para') IS NOT NULL
    PRINT '<<< CREATED TABLE global_para >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE global_para >>>'
go

/* 
 * TABLE: hotel 
 */

CREATE TABLE hotel(
    hotel_id          int             IDENTITY(1,1),
    hotel_nm          text            NULL,
    hotel_site        smallint        NULL,
    hotel_city        smallint        NULL,
    hotel_addr        text            NULL,
    hotel_curr        varchar(8)      NULL,
    hotel_desc        text            NULL,
    hotel_tele        varchar(50)     NULL,
    hotel_fax         varchar(50)     NULL,
    hotel_level       int             DEFAULT 0 NOT NULL,
    cont_fr_date      datetime        NULL,
    cont_to_date      datetime        NULL,
    promote_status    int             DEFAULT 0 NOT NULL,
    is_enabled        int             DEFAULT 1 NOT NULL,
    promote_msg       text            NULL,
    promote_date      datetime        NULL,
    email_date        datetime        NULL,
    email_times       int             DEFAULT 0 NOT NULL,
    hotel_contact     varchar(150)    NULL,
    hotel_email       varchar(100)    NULL,
    hotel_spec        text            NULL,
    CONSTRAINT PK35 PRIMARY KEY CLUSTERED (hotel_id)
)
go



IF OBJECT_ID('hotel') IS NOT NULL
    PRINT '<<< CREATED TABLE hotel >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE hotel >>>'
go

/* 
 * TABLE: hotel_contract 
 */

CREATE TABLE hotel_contract(
    cont_id            int             IDENTITY(1,1),
    hotel_id           int             DEFAULT 0 NOT NULL,
    cont_file_nm       varchar(50)     NOT NULL,
    cont_file_desc     varchar(20)     NOT NULL,
    cont_filecontnt    image					 NOT NULL,
    cont_uploaddate    datetime        NOT NULL,
    cont_file_size     int             DEFAULT 0 NOT NULL,
    CONSTRAINT PK36 PRIMARY KEY CLUSTERED (cont_id)
)
go



IF OBJECT_ID('hotel_contract') IS NOT NULL
    PRINT '<<< CREATED TABLE hotel_contract >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE hotel_contract >>>'
go

/* 
 * TABLE: hotel_room 
 */

CREATE TABLE hotel_room(
    room_id           int              IDENTITY(1,1),
    hotel_id          int              DEFAULT 0 NOT NULL,
    room_nm           varchar(50)      NOT NULL,
    room_price        decimal(9, 2)    DEFAULT 0.00 NOT NULL,
    room_discount     smallint         NULL,
    room_desc         varchar(255)     NULL,
    is_enabled        int              DEFAULT 1 NOT NULL,
    room_breakfast    varchar(255)     NULL,
    room_network      varchar(255)     NULL,
    CONSTRAINT PK37 PRIMARY KEY CLUSTERED (room_id)
)
go



IF OBJECT_ID('hotel_room') IS NOT NULL
    PRINT '<<< CREATED TABLE hotel_room >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE hotel_room >>>'
go

/* 
 * TABLE: kpi_sum_expctgy 
 */

CREATE TABLE kpi_sum_expctgy(
    kpi_sum_expctgy_id    int         IDENTITY(1,1),
    kpi_summary_id        int         DEFAULT 0 NOT NULL,
    site_id               smallint    DEFAULT 0 NOT NULL,
    summary_date          datetime    NOT NULL,
    pr_categry_id         int         DEFAULT 0 NOT NULL,
    pr_created            int         DEFAULT 0 NOT NULL,
    CONSTRAINT PK38 PRIMARY KEY CLUSTERED (kpi_sum_expctgy_id)
)
go



IF OBJECT_ID('kpi_sum_expctgy') IS NOT NULL
    PRINT '<<< CREATED TABLE kpi_sum_expctgy >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE kpi_sum_expctgy >>>'
go

/* 
 * TABLE: kpi_sum_purctgy 
 */

CREATE TABLE kpi_sum_purctgy(
    kpi_sum_purctgy_id    int         IDENTITY(1,1),
    kpi_summary_id        int         DEFAULT 0 NOT NULL,
    site_id               smallint    DEFAULT 0 NOT NULL,
    summary_date          datetime    NOT NULL,
    pr_categry_id         int         DEFAULT 0 NOT NULL,
    pr_created            int         DEFAULT 0 NOT NULL,
    CONSTRAINT PK39 PRIMARY KEY CLUSTERED (kpi_sum_purctgy_id)
)
go



IF OBJECT_ID('kpi_sum_purctgy') IS NOT NULL
    PRINT '<<< CREATED TABLE kpi_sum_purctgy >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE kpi_sum_purctgy >>>'
go

/* 
 * TABLE: kpi_summary 
 */

CREATE TABLE kpi_summary(
    kpi_summary_id        int              IDENTITY(1,1),
    summary_date          datetime         NOT NULL,
    login_user_count      int              DEFAULT 0 NOT NULL,
    ta_closed             int              DEFAULT 0 NOT NULL,
    avg_ta_closed_days    decimal(9, 2)    DEFAULT 0.00 NOT NULL,
    ta_created            int              DEFAULT 0 NOT NULL,
    capex_closed          int              DEFAULT 0 NOT NULL,
    avg_ce_closed_days    decimal(9, 2)    DEFAULT 0.00 NOT NULL,
    capex_created         int              DEFAULT 0 NOT NULL,
    pr_closed             int              DEFAULT 0 NOT NULL,
    avg_pr_closed_days    decimal(9, 2)    DEFAULT 0.00 NOT NULL,
    pr_created            int              DEFAULT 0 NOT NULL,
    expense_closed        int              DEFAULT 0 NOT NULL,
    avg_ex_closed_days    decimal(9, 2)    DEFAULT 0.00 NOT NULL,
    expense_created       int              DEFAULT 0 NOT NULL,
    CONSTRAINT PK40 PRIMARY KEY CLUSTERED (kpi_summary_id)
)
go



IF OBJECT_ID('kpi_summary') IS NOT NULL
    PRINT '<<< CREATED TABLE kpi_summary >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE kpi_summary >>>'
go

/* 
 * TABLE: menu 
 */

CREATE TABLE menu(
    menu_id          int             IDENTITY(1,1),
    menu_nm          varchar(100)    NOT NULL,
    menu_desc        varchar(255)    NULL,
    func_id          int             NULL,
    url              varchar(255)    NULL,
    parent_id        int             NULL,
    onclick          varchar(100)    NULL,
    onmouse_over     varchar(100)    NULL,
    onmouse_down     varchar(100)    NULL,
    sec_menu_nm      varchar(100)    NOT NULL,
    sec_menu_desc    varchar(255)    NULL,
    CONSTRAINT PK41 PRIMARY KEY CLUSTERED (menu_id)
)
go



IF OBJECT_ID('menu') IS NOT NULL
    PRINT '<<< CREATED TABLE menu >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE menu >>>'
go

/* 
 * TABLE: metadata_det 
 */

CREATE TABLE metadata_det(
    md_det_id         int             DEFAULT 0 NOT NULL,
    md_mst_id         int             DEFAULT 0 NOT NULL,
    eng_full_desc     varchar(255)    NOT NULL,
    sec_full_desc     varchar(255)    NOT NULL,
    eng_short_desc    varchar(255)    NOT NULL,
    sec_short_desc    varchar(255)    NOT NULL,
    color             varchar(20)     NULL,
    CONSTRAINT PK42 PRIMARY KEY CLUSTERED (md_det_id, md_mst_id)
)
go



IF OBJECT_ID('metadata_det') IS NOT NULL
    PRINT '<<< CREATED TABLE metadata_det >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE metadata_det >>>'
go

/* 
 * TABLE: metadata_mst 
 */

CREATE TABLE metadata_mst(
    md_mst_id      int            DEFAULT 0 NOT NULL,
    md_mst_desc    varchar(80)    NULL,
    CONSTRAINT PK43 PRIMARY KEY CLUSTERED (md_mst_id)
)
go



IF OBJECT_ID('metadata_mst') IS NOT NULL
    PRINT '<<< CREATED TABLE metadata_mst >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE metadata_mst >>>'
go

/* 
 * TABLE: oa_function 
 */

CREATE TABLE oa_function(
    func_id      int             DEFAULT 0 NOT NULL,
    func_nm      varchar(50)     NOT NULL,
    func_desc    varchar(255)    NULL,
    CONSTRAINT PK44 PRIMARY KEY CLUSTERED (func_id)
)
go



IF OBJECT_ID('oa_function') IS NOT NULL
    PRINT '<<< CREATED TABLE oa_function >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE oa_function >>>'
go

/* 
 * TABLE: oa_user 
 */

CREATE TABLE oa_user(
    user_id            int             IDENTITY(1,1),
    user_cd            varchar(20)     NOT NULL,
    user_pwd           varchar(32)     NOT NULL,
    user_nm            varchar(50)     NOT NULL,
    gender             int             DEFAULT 0 NOT NULL,
    email              varchar(150)    NOT NULL,
    tele_no            varchar(50)     NULL,
    primary_site       smallint        DEFAULT 0 NOT NULL,
    pwd_hint_quest     varchar(255)    NULL,
    pwd_hint_answer    varchar(255)    NULL,
    last_login_time    datetime        NULL,
    login_fail_time    smallint        DEFAULT 0 NOT NULL,
    is_enabled         int             DEFAULT 1 NOT NULL,
    locale             varchar(20)     NULL,
    CONSTRAINT PK45 PRIMARY KEY CLUSTERED (user_id)
)
go



IF OBJECT_ID('oa_user') IS NOT NULL
    PRINT '<<< CREATED TABLE oa_user >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE oa_user >>>'
go

/* 
 * TABLE: payment_schdl_det 
 */

CREATE TABLE payment_schdl_det(
    psd_id      int               IDENTITY(1,1),
    po_no       varchar(16)       NOT NULL,
    psd_desc    varchar(255)      NOT NULL,
    psd_date    datetime          DEFAULT 0000-00-00 NOT NULL,
    psd_amt     decimal(14, 2)    DEFAULT 0.00 NOT NULL,
    CONSTRAINT PK46 PRIMARY KEY CLUSTERED (psd_id)
)
go



IF OBJECT_ID('payment_schdl_det') IS NOT NULL
    PRINT '<<< CREATED TABLE payment_schdl_det >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE payment_schdl_det >>>'
go

/* 
 * TABLE: po_approver 
 */

CREATE TABLE po_approver(
    list_id            varchar(32)     NOT NULL,
    approver_id        int             DEFAULT 0 NOT NULL,
    actual_approver    int             NULL,
    your_turn_date     datetime        NULL,
    email_date         datetime        NULL,
    email_times        int             DEFAULT 0 NOT NULL,
    approve_seq        smallint        DEFAULT 0 NOT NULL,
    approve_status     int             DEFAULT 0 NOT NULL,
    approve_date       datetime        NULL,
    approve_comment    varchar(255)    NULL,
    can_modify         int             DEFAULT 0 NOT NULL,
    CONSTRAINT PK47 PRIMARY KEY CLUSTERED (list_id, approver_id)
)
go



IF OBJECT_ID('po_approver') IS NOT NULL
    PRINT '<<< CREATED TABLE po_approver >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE po_approver >>>'
go

/* 
 * TABLE: po_attach 
 */

CREATE TABLE po_attach(
    po_attach_id      int            IDENTITY(1,1),
    po_no             varchar(16)    NULL,
    file_title        varchar(50)    NOT NULL,
    file_name         varchar(50)    NOT NULL,
    file_content      image          NOT NULL,
    upload_date       datetime       DEFAULT 0000-00-00 NOT NULL,
    cont_file_size    int            DEFAULT 0 NOT NULL,
    CONSTRAINT PK48 PRIMARY KEY CLUSTERED (po_attach_id)
)
go



IF OBJECT_ID('po_attach') IS NOT NULL
    PRINT '<<< CREATED TABLE po_attach >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE po_attach >>>'
go

/* 
 * TABLE: po_history 
 */

CREATE TABLE po_history(
    po_his_id        int               IDENTITY(1,1),
    po_no            varchar(16)       NULL,
    po_status        int               DEFAULT 0 NOT NULL,
    base_amount      decimal(14, 2)    DEFAULT 0.00 NOT NULL,
    list_id          varchar(32)       NOT NULL,
    exchange_rate    decimal(11, 4)    DEFAULT 0.0000 NOT NULL,
    CONSTRAINT PK49 PRIMARY KEY CLUSTERED (po_his_id)
)
go



IF OBJECT_ID('po_history') IS NOT NULL
    PRINT '<<< CREATED TABLE po_history >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE po_history >>>'
go

/* 
 * TABLE: po_item 
 */

CREATE TABLE po_item(
    po_item_id      int               IDENTITY(1,1),
    po_no           varchar(16)       NULL,
    supp_id         int               NULL,
    supp_nm         varchar(50)       NULL,
    item_id         int               NULL,
    item_sepc       varchar(255)      NOT NULL,
    exch_rate_id    int               DEFAULT 0 NOT NULL,
    unit_price      decimal(12, 2)    DEFAULT 0.00 NOT NULL,
    quantity        int               DEFAULT 0 NOT NULL,
    due_date        datetime          NULL,
    buy_for_user    int               NULL,
    buy_for_dep     smallint          NULL,
    is_recharge     int               DEFAULT 0 NOT NULL,
    pur_type_cd     varchar(8)        NULL,
    receive_qty     int               DEFAULT 0 NOT NULL,
    return_qty      int               DEFAULT 0 NOT NULL,
    cancel_qty      int               DEFAULT 0 NOT NULL,
    pr_item_id      int               NULL,
    description     varchar(24)       NULL,
    proj_id         smallint          NULL,
    CONSTRAINT PK50 PRIMARY KEY CLUSTERED (po_item_id)
)
go



IF OBJECT_ID('po_item') IS NOT NULL
    PRINT '<<< CREATED TABLE po_item >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE po_item >>>'
go

/* 
 * TABLE: po_item_attach 
 */

CREATE TABLE po_item_attach(
    po_item_att_id    int            IDENTITY(1,1),
    po_item_id        int            DEFAULT 0 NULL,
    file_title        varchar(50)    NOT NULL,
    file_name         varchar(50)    NOT NULL,
    file_content      image          NOT NULL,
    upload_date       datetime       DEFAULT 0000-00-00 NOT NULL,
    cont_file_size    int            DEFAULT 0 NOT NULL,
    CONSTRAINT PK51 PRIMARY KEY CLUSTERED (po_item_att_id)
)
go



IF OBJECT_ID('po_item_attach') IS NOT NULL
    PRINT '<<< CREATED TABLE po_item_attach >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE po_item_attach >>>'
go

/* 
 * TABLE: po_item_cancel 
 */

CREATE TABLE po_item_cancel(
    cancel_id        int         IDENTITY(1,1),
    po_item_id       int         DEFAULT 0 NOT NULL,
    cancel_date      datetime    DEFAULT 0000-00-00 NOT NULL,
    cancel_qty       int         DEFAULT 0 NOT NULL,
    cancel_user      int         DEFAULT 0 NOT NULL,
    export_status    int         DEFAULT 0 NOT NULL,
    CONSTRAINT PK52 PRIMARY KEY CLUSTERED (cancel_id)
)
go



IF OBJECT_ID('po_item_cancel') IS NOT NULL
    PRINT '<<< CREATED TABLE po_item_cancel >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE po_item_cancel >>>'
go

/* 
 * TABLE: po_item_history 
 */

CREATE TABLE po_item_history(
    po_item_his_id    int               IDENTITY(1,1),
    po_his_id         int               DEFAULT 0 NOT NULL,
    supp_id           int               NULL,
    item_id           int               NULL,
    exch_rate_id      int               DEFAULT 0 NOT NULL,
    unit_price        decimal(12, 2)    DEFAULT 0.00 NOT NULL,
    quantity          int               DEFAULT 0 NOT NULL,
    due_date          datetime          NULL,
    buy_for_user      int               NULL,
    is_recharge       int               DEFAULT 0 NOT NULL,
    pur_type_cd       varchar(8)        NULL,
    item_sepc         varchar(255)      NOT NULL,
    buy_for_dep       smallint          NULL,
    proj_id           smallint          NULL,
    CONSTRAINT PK53 PRIMARY KEY CLUSTERED (po_item_his_id)
)
go



IF OBJECT_ID('po_item_history') IS NOT NULL
    PRINT '<<< CREATED TABLE po_item_history >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE po_item_history >>>'
go

/* 
 * TABLE: po_item_receipt 
 */

CREATE TABLE po_item_receipt(
    rcpt_id          int         IDENTITY(1,1),
    po_item_id       int         DEFAULT 0 NOT NULL,
    receiver1        int         DEFAULT 0 NOT NULL,
    rcpt_date1       datetime    NULL,
    rcpt_qty1        int         NULL,
    receiver2        int         DEFAULT 0 NOT NULL,
    rcpt_date2       datetime    NULL,
    rcpt_qty2        int         NULL,
    export_status    int         DEFAULT 0 NOT NULL,
    CONSTRAINT PK54 PRIMARY KEY CLUSTERED (rcpt_id)
)
go



IF OBJECT_ID('po_item_receipt') IS NOT NULL
    PRINT '<<< CREATED TABLE po_item_receipt >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE po_item_receipt >>>'
go

/* 
 * TABLE: po_item_recharge 
 */

CREATE TABLE po_item_recharge(
    po_itm_rechrgid    int              IDENTITY(1,1),
    po_item_id         int              DEFAULT 0 NOT NULL,
    cust_cd            varchar(17)      NULL,
    person_dep_id      smallint         NULL,
    person_id          int              NULL,
    amount             decimal(9, 2)    DEFAULT 0.00 NOT NULL,
    CONSTRAINT PK55 PRIMARY KEY CLUSTERED (po_itm_rechrgid)
)
go



IF OBJECT_ID('po_item_recharge') IS NOT NULL
    PRINT '<<< CREATED TABLE po_item_recharge >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE po_item_recharge >>>'
go

/* 
 * TABLE: pr_approver 
 */

CREATE TABLE pr_approver(
    list_id            varchar(32)     NOT NULL,
    approver_id        int             DEFAULT 0 NOT NULL,
    approve_seq        smallint        DEFAULT 0 NOT NULL,
    approve_status     int             DEFAULT 0 NOT NULL,
    approve_date       datetime        NULL,
    approve_comment    varchar(255)    NULL,
    can_modify         int             DEFAULT 0 NOT NULL,
    actual_approver    int             NULL,
    your_turn_date     datetime        NULL,
    email_date         datetime        NULL,
    email_times        int             DEFAULT 0 NOT NULL,
    CONSTRAINT PK56 PRIMARY KEY CLUSTERED (list_id, approver_id)
)
go



IF OBJECT_ID('pr_approver') IS NOT NULL
    PRINT '<<< CREATED TABLE pr_approver >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE pr_approver >>>'
go

/* 
 * TABLE: pr_attach 
 */

CREATE TABLE pr_attach(
    pr_attach_id      int            IDENTITY(1,1),
    pr_no             varchar(16)    NULL,
    file_title        varchar(50)    NOT NULL,
    file_name         varchar(50)    NOT NULL,
    file_content      image          NOT NULL,
    upload_date       datetime       NOT NULL,
    cont_file_size    int            DEFAULT 0 NOT NULL,
    CONSTRAINT PK57 PRIMARY KEY CLUSTERED (pr_attach_id)
)
go



IF OBJECT_ID('pr_attach') IS NOT NULL
    PRINT '<<< CREATED TABLE pr_attach >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE pr_attach >>>'
go

/* 
 * TABLE: pr_authorized_purchaser 
 */

CREATE TABLE pr_authorized_purchaser(
    pr_auth_pur_id    int            IDENTITY(1,1),
    pr_no             varchar(16)    NOT NULL,
    purchaser         int            DEFAULT 0 NOT NULL,
    CONSTRAINT PK58 PRIMARY KEY CLUSTERED (pr_auth_pur_id)
)
go



IF OBJECT_ID('pr_authorized_purchaser') IS NOT NULL
    PRINT '<<< CREATED TABLE pr_authorized_purchaser >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE pr_authorized_purchaser >>>'
go

/* 
 * TABLE: pr_history 
 */

CREATE TABLE pr_history(
    pr_hist_id    int            IDENTITY(1,1),
    pr_no         varchar(16)    NOT NULL,
    pr_status     int            DEFAULT 0 NOT NULL,
    list_id       varchar(32)    NOT NULL,
    CONSTRAINT PK59 PRIMARY KEY CLUSTERED (pr_hist_id)
)
go



IF OBJECT_ID('pr_history') IS NOT NULL
    PRINT '<<< CREATED TABLE pr_history >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE pr_history >>>'
go

/* 
 * TABLE: pr_item 
 */

CREATE TABLE pr_item(
    pr_item_id       int               IDENTITY(1,1),
    pr_no            varchar(16)       NOT NULL,
    supp_nm          varchar(50)       NULL,
    item_id          int               NULL,
    item_sepc        varchar(255)      NOT NULL,
    exch_rate_id     int               DEFAULT 0 NOT NULL,
    exchange_rate    decimal(11, 4)    DEFAULT 0.0000 NOT NULL,
    unit_price       decimal(12, 2)    DEFAULT 0.00 NOT NULL,
    quantity         int               DEFAULT 0 NOT NULL,
    due_date         datetime          NULL,
    buy_for_dep      smallint          NULL,
    buy_for_user     int               NULL,
    supp_id          int               NULL,
    is_recharge      int               DEFAULT 0 NOT NULL,
    po_item_id       int               NULL,
    proj_id          smallint          NULL,
    CONSTRAINT PK60 PRIMARY KEY CLUSTERED (pr_item_id)
)
go



IF OBJECT_ID('pr_item') IS NOT NULL
    PRINT '<<< CREATED TABLE pr_item >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE pr_item >>>'
go

/* 
 * TABLE: pr_item_attach 
 */

CREATE TABLE pr_item_attach(
    pr_item_att_id    int             IDENTITY(1,1),
    pr_item_id        int             DEFAULT 0 NULL,
    file_title        varchar(50)     NOT NULL,
    file_name         varchar(50)     NOT NULL,
    file_content      image    				NOT NULL,
    upload_date       datetime        NOT NULL,
    cont_file_size    int             DEFAULT 0 NOT NULL,
    CONSTRAINT PK61 PRIMARY KEY CLUSTERED (pr_item_att_id)
)
go



IF OBJECT_ID('pr_item_attach') IS NOT NULL
    PRINT '<<< CREATED TABLE pr_item_attach >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE pr_item_attach >>>'
go

/* 
 * TABLE: pr_item_history 
 */

CREATE TABLE pr_item_history(
    pr_item_hist_id    int               IDENTITY(1,1),
    pr_hist_id         int               DEFAULT 0 NOT NULL,
    supp_nm            varchar(50)       NULL,
    item_id            int               NULL,
    item_sepc          varchar(255)      NOT NULL,
    exch_rate_id       int               DEFAULT 0 NOT NULL,
    exchange_rate      decimal(11, 4)    DEFAULT 0.0000 NOT NULL,
    unit_price         decimal(12, 2)    DEFAULT 0.00 NOT NULL,
    quantity           int               DEFAULT 0 NOT NULL,
    due_date           datetime          NULL,
    buy_for_dep        smallint          NULL,
    buy_for_user       int               NULL,
    supp_id            int               NULL,
    is_recharge        int               DEFAULT 0 NOT NULL,
    proj_id            smallint          NULL,
    CONSTRAINT PK62 PRIMARY KEY CLUSTERED (pr_item_hist_id)
)
go



IF OBJECT_ID('pr_item_history') IS NOT NULL
    PRINT '<<< CREATED TABLE pr_item_history >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE pr_item_history >>>'
go

/* 
 * TABLE: pr_item_recharge 
 */

CREATE TABLE pr_item_recharge(
    pr_itm_rechrgid    int              IDENTITY(1,1),
    pr_item_id         int              DEFAULT 0 NOT NULL,
    cust_cd            varchar(17)      NULL,
    person_dep_id      smallint         NULL,
    person_id          int              NULL,
    amount             decimal(9, 2)    DEFAULT 0.00 NOT NULL,
    CONSTRAINT PK63 PRIMARY KEY CLUSTERED (pr_itm_rechrgid)
)
go



IF OBJECT_ID('pr_item_recharge') IS NOT NULL
    PRINT '<<< CREATED TABLE pr_item_recharge >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE pr_item_recharge >>>'
go

/* 
 * TABLE: proj_cd 
 */

CREATE TABLE proj_cd(
    proj_id        smallint       IDENTITY(1,1),
    proj_cd        varchar(8)     NOT NULL,
    is_enabled     int            DEFAULT 1 NOT NULL,
    proj_site      smallint       NOT NULL,
    description    varchar(18)    NULL,
    CONSTRAINT PK64 PRIMARY KEY CLUSTERED (proj_id)
)
go



IF OBJECT_ID('proj_cd') IS NOT NULL
    PRINT '<<< CREATED TABLE proj_cd >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE proj_cd >>>'
go

/* 
 * TABLE: province 
 */

CREATE TABLE province(
    province_id    smallint       IDENTITY(1,1),
    country_id     smallint       DEFAULT 0 NOT NULL,
    eng_name       varchar(50)    NOT NULL,
    sec_name       varchar(50)    NOT NULL,
    is_enabled     int            DEFAULT 1 NOT NULL,
    maint_site     smallint       NULL,
    CONSTRAINT PK65 PRIMARY KEY CLUSTERED (province_id)
)
go



IF OBJECT_ID('province') IS NOT NULL
    PRINT '<<< CREATED TABLE province >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE province >>>'
go

/* 
 * TABLE: purchase_ctgy 
 */

CREATE TABLE purchase_ctgy(
    pur_ctgy_id      int            IDENTITY(1,1),
    pur_ctgy_desc    varchar(50)    NOT NULL,
    pur_ctgy_site    smallint       DEFAULT 0 NULL,
    is_enabled       int            DEFAULT 1 NOT NULL,
    CONSTRAINT PK66 PRIMARY KEY CLUSTERED (pur_ctgy_id)
)
go



IF OBJECT_ID('purchase_ctgy') IS NOT NULL
    PRINT '<<< CREATED TABLE purchase_ctgy >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE purchase_ctgy >>>'
go

/* 
 * TABLE: purchase_order 
 */

CREATE TABLE purchase_order(
    po_no             varchar(16)       NOT NULL,
    po_site           smallint          DEFAULT 0 NOT NULL,
    ref_erp_no        varchar(16)       NULL,
    po_title          varchar(50)       NOT NULL,
    po_desc           text              NULL,
    supp_id           int               DEFAULT 0 NOT NULL,
    pur_subctgy_id    int               NULL,
    pur_amount        decimal(14, 2)    DEFAULT 0.00 NOT NULL,
    base_curr_cd      varchar(8)        NOT NULL,
    exchange_rate     decimal(11, 4)    DEFAULT 0.0000 NOT NULL,
    po_status         int               DEFAULT 0 NOT NULL,
    create_user       int               DEFAULT 0 NOT NULL,
    create_date       datetime          DEFAULT 0000-00-00 NOT NULL,
    request_date      datetime          NULL,
    export_status     int               DEFAULT 0 NOT NULL,
    purchaser         int               NULL,
    list_id           varchar(32)       NULL,
    receiver          varchar(50)       NULL,
    receive_addr      varchar(255)      NULL,
    exch_rate_id      int               DEFAULT 0 NOT NULL,
    inspector         int               NULL,
    row_version       int               DEFAULT 0 NOT NULL,
    confirmer         int               NULL,
    email_date        datetime          NULL,
    email_times       int               DEFAULT 0 NOT NULL,
    approve_date      datetime          NULL,
    confirm_date      datetime          NULL,
    CONSTRAINT PK67 PRIMARY KEY CLUSTERED (po_no)
)
go



IF OBJECT_ID('purchase_order') IS NOT NULL
    PRINT '<<< CREATED TABLE purchase_order >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE purchase_order >>>'
go

/* 
 * TABLE: purchase_request 
 */

CREATE TABLE purchase_request(
    pr_no             varchar(16)       NOT NULL,
    pr_title          varchar(50)       NOT NULL,
    pr_desc           text              NULL,
    pur_subctgy_id    int               DEFAULT 0 NOT NULL,
    pr_dep_id         smallint          DEFAULT 0 NOT NULL,
    pr_status         int               DEFAULT 0 NOT NULL,
    pr_capex_no       varchar(16)       NULL,
    pr_budget_id      int               NULL,
    base_curr_cd      varchar(8)        NOT NULL,
    requestor         int               DEFAULT 0 NOT NULL,
    req_date          datetime          NULL,
    creator           int               DEFAULT 0 NOT NULL,
    create_date       datetime          NOT NULL,
    pr_purchaser      int               NULL,
    list_id           varchar(32)       NULL,
    base_amt          decimal(11, 2)    DEFAULT 0.00 NULL,
    approve_date      datetime          NULL,
    row_version       int               DEFAULT 0 NOT NULL,
    is_delegate       int               DEFAULT 0 NOT NULL,
    email_date        datetime          NULL,
    email_times       int               DEFAULT 0 NOT NULL,
    CONSTRAINT PK68 PRIMARY KEY CLUSTERED (pr_no)
)
go



IF OBJECT_ID('purchase_request') IS NOT NULL
    PRINT '<<< CREATED TABLE purchase_request >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE purchase_request >>>'
go

/* 
 * TABLE: purchase_subctgy 
 */

CREATE TABLE purchase_subctgy(
    pur_subctgy_id     int            IDENTITY(1,1),
    pur_ctgy_id        int            DEFAULT 0 NOT NULL,
    def_supplier       int            NULL,
    pur_subctgy_des    varchar(50)    NOT NULL,
    is_enabled         int            DEFAULT 1 NOT NULL,
    inspector          int            NULL,
    CONSTRAINT PK69 PRIMARY KEY CLUSTERED (pur_subctgy_id)
)
go



IF OBJECT_ID('purchase_subctgy') IS NOT NULL
    PRINT '<<< CREATED TABLE purchase_subctgy >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE purchase_subctgy >>>'
go

/* 
 * TABLE: purchase_type 
 */

CREATE TABLE purchase_type(
    pur_type_cd      varchar(8)     NOT NULL,
    pur_type_desc    varchar(50)    NULL,
    pur_type_site    smallint       DEFAULT 0 NOT NULL,
    is_enabled       int            DEFAULT 1 NOT NULL,
    CONSTRAINT PK70 PRIMARY KEY CLUSTERED (pur_type_cd)
)
go



IF OBJECT_ID('purchase_type') IS NOT NULL
    PRINT '<<< CREATED TABLE purchase_type >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE purchase_type >>>'
go

/* 
 * TABLE: role 
 */

CREATE TABLE role(
    role_id      int             IDENTITY(1,1),
    role_nm      varchar(50)     NOT NULL,
    role_desc    varchar(255)    NULL,
    role_type    int             NULL,
    CONSTRAINT PK71 PRIMARY KEY CLUSTERED (role_id)
)
go



IF OBJECT_ID('role') IS NOT NULL
    PRINT '<<< CREATED TABLE role >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE role >>>'
go

/* 
 * TABLE: role_function 
 */

CREATE TABLE role_function(
    role_id    int    DEFAULT 0 NOT NULL,
    func_id    int    DEFAULT 0 NOT NULL,
    CONSTRAINT PK72 PRIMARY KEY CLUSTERED (role_id, func_id)
)
go



IF OBJECT_ID('role_function') IS NOT NULL
    PRINT '<<< CREATED TABLE role_function >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE role_function >>>'
go

/* 
 * TABLE: rule 
 */

CREATE TABLE [rule](
    rule_id       int             IDENTITY(1,1),
    rule_type     int             DEFAULT 0 NOT NULL,
    rule_desc     varchar(255)    NOT NULL,
    rule_site     smallint        DEFAULT 0 NOT NULL,
    is_enabled    int             DEFAULT 0 NOT NULL,
    rule_top      int             DEFAULT 0 NULL,
    rule_left     int             DEFAULT 0 NULL,
    CONSTRAINT PK73 PRIMARY KEY CLUSTERED (rule_id)
)
go



IF OBJECT_ID('rule') IS NOT NULL
    PRINT '<<< CREATED TABLE rule >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE rule >>>'
go

/* 
 * TABLE: rule_condition 
 */

CREATE TABLE rule_condition(
    rule_id           int            DEFAULT 0 NOT NULL,
    condition_type    int            DEFAULT 0 NOT NULL,
    compare_symbol    int            DEFAULT 0 NOT NULL,
    compare_value     varchar(50)    NOT NULL,
    CONSTRAINT PK74 PRIMARY KEY CLUSTERED (rule_id, condition_type)
)
go



IF OBJECT_ID('rule_condition') IS NOT NULL
    PRINT '<<< CREATED TABLE rule_condition >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE rule_condition >>>'
go

/* 
 * TABLE: rule_consequence 
 */

CREATE TABLE rule_consequence(
    rule_id       int        DEFAULT 0 NOT NULL,
    user_id       int        DEFAULT 0 NOT NULL,
    seq_no        tinyint    DEFAULT 0 NOT NULL,
    can_modify    int        DEFAULT 0 NOT NULL,
    superior      int        NULL,
    CONSTRAINT PK75 PRIMARY KEY CLUSTERED (rule_id, user_id)
)
go



IF OBJECT_ID('rule_consequence') IS NOT NULL
    PRINT '<<< CREATED TABLE rule_consequence >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE rule_consequence >>>'
go

/* 
 * TABLE: rule_flow 
 */

CREATE TABLE rule_flow(
    flow_id       int             IDENTITY(1,1),
    flow_type     int             DEFAULT 0 NOT NULL,
    flow_site     smallint        DEFAULT 0 NOT NULL,
    flow_desc     varchar(255)    NOT NULL,
    is_enabled    int             DEFAULT 0 NOT NULL,
    CONSTRAINT PK76 PRIMARY KEY CLUSTERED (flow_id)
)
go



IF OBJECT_ID('rule_flow') IS NOT NULL
    PRINT '<<< CREATED TABLE rule_flow >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE rule_flow >>>'
go

/* 
 * TABLE: rule_flow_rule 
 */

CREATE TABLE rule_flow_rule(
    flow_id      int        DEFAULT 0 NOT NULL,
    seq_no       tinyint    DEFAULT 0 NOT NULL,
    rule_id      int        DEFAULT 0 NOT NULL,
    pass_next    tinyint    DEFAULT 0 NOT NULL,
    fail_next    tinyint    DEFAULT 0 NOT NULL,
    CONSTRAINT PK77 PRIMARY KEY CLUSTERED (flow_id, seq_no)
)
go



IF OBJECT_ID('rule_flow_rule') IS NOT NULL
    PRINT '<<< CREATED TABLE rule_flow_rule >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE rule_flow_rule >>>'
go

/* 
 * TABLE: site 
 */

CREATE TABLE site(
    site_id          smallint        IDENTITY(1,1),
    site_nm          varchar(50)     NOT NULL,
    site_mgr         int             NULL,
    site_curr        varchar(8)      NOT NULL,
    site_logo        image    			 NULL,
    can_recharge     int             DEFAULT 0 NOT NULL,
    is_enabled       int             DEFAULT 1 NOT NULL,
    site_city        smallint        DEFAULT 0 NOT NULL,
    site_activity    varchar(50)     NULL,
    CONSTRAINT PK78 PRIMARY KEY CLUSTERED (site_id)
)
go



IF OBJECT_ID('site') IS NOT NULL
    PRINT '<<< CREATED TABLE site >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE site >>>'
go

/* 
 * TABLE: site_mail_rmd 
 */

CREATE TABLE site_mail_rmd(
    site_id         smallint    DEFAULT 0 NOT NULL,
    md_det_id       int         DEFAULT 0 NOT NULL,
    due_day         smallint    DEFAULT 3 NOT NULL,
    interval_day    smallint    DEFAULT 2 NOT NULL,
    max_time        smallint    DEFAULT 10 NOT NULL,
    CONSTRAINT PK79 PRIMARY KEY CLUSTERED (site_id, md_det_id)
)
go



IF OBJECT_ID('site_mail_rmd') IS NOT NULL
    PRINT '<<< CREATED TABLE site_mail_rmd >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE site_mail_rmd >>>'
go

/* 
 * TABLE: supplier 
 */

CREATE TABLE supplier(
    supp_id             int            IDENTITY(1,1),
    supp_cd             varchar(8)     NOT NULL,
    supp_site           smallint       NULL,
    supp_nm             varchar(50)    NULL,
    supp_address1       varchar(50)    NULL,
    supp_address2       varchar(50)    NULL,
    supp_address3       varchar(50)    NULL,
    supp_city           smallint       NULL,
    supp_post           varchar(9)     NULL,
    supp_attention1     varchar(24)    NULL,
    supp_attention2     varchar(24)    NULL,
    supp_tele1          varchar(16)    NULL,
    supp_ext1           varchar(4)     NULL,
    supp_tele2          varchar(16)    NULL,
    supp_ext2           varchar(4)     NULL,
    supp_fax1           varchar(16)    NULL,
    supp_fax2           varchar(16)    NULL,
    supp_pur_accnt      varchar(8)     NULL,
    supp_pur_subant     varchar(8)     NULL,
    supp_pur_cstcen     varchar(8)     NULL,
    supp_ap_account     varchar(8)     NULL,
    supp_ap_subacnt     varchar(8)     NULL,
    supp_ap_cstcen      varchar(8)     NULL,
    supp_bank           varchar(8)     NULL,
    supp_curr_code      varchar(8)     NULL,
    supp_cr_terms       varchar(20)    NULL,
    supp_contact        varchar(20)    NULL,
    cont_fr_date        datetime       NULL,
    cont_to_date        datetime       NULL,
    is_enabled          int            DEFAULT 1 NOT NULL,
    export_status       int            DEFAULT 0 NOT NULL,
    last_exp_file       varchar(30)    NULL,
    is_air_tkt_supp     int            DEFAULT 0 NOT NULL,
    is_confirmed        int            DEFAULT 1 NOT NULL,
    promote_status      int            DEFAULT 1 NOT NULL,
    promote_msg         text           NULL,
    promote_date        datetime       NULL,
    confirm_date        datetime       NULL,
    supp_cnfm_sta       int            DEFAULT 1 NOT NULL,
    email_date          datetime       NULL,
    email_times         int            DEFAULT 0 NOT NULL,
    supp_country        smallint       DEFAULT 1 NOT NULL,
    last_modify_date    datetime       NULL,
    CONSTRAINT PK80 PRIMARY KEY CLUSTERED (supp_id)
)
go



IF OBJECT_ID('supplier') IS NOT NULL
    PRINT '<<< CREATED TABLE supplier >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE supplier >>>'
go

/* 
 * TABLE: supplier_contract 
 */

CREATE TABLE supplier_contract(
    cont_id            int             IDENTITY(1,1),
    supp_id            int             DEFAULT 0 NOT NULL,
    cont_file_nm       varchar(50)     NOT NULL,
    cont_file_desc     varchar(20)     NOT NULL,
    cont_filecontnt    image    			 NOT NULL,
    cont_uploaddate    datetime        NOT NULL,
    cont_file_size     int             DEFAULT 0 NOT NULL,
    CONSTRAINT PK81 PRIMARY KEY CLUSTERED (cont_id)
)
go



IF OBJECT_ID('supplier_contract') IS NOT NULL
    PRINT '<<< CREATED TABLE supplier_contract >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE supplier_contract >>>'
go

/* 
 * TABLE: supplier_history 
 */

CREATE TABLE supplier_history(
    supp_id            int            DEFAULT 0 NOT NULL,
    supp_cd            varchar(8)     NOT NULL,
    supp_site          smallint       NULL,
    supp_name          varchar(50)    NULL,
    supp_address1      varchar(50)    NULL,
    supp_address2      varchar(50)    NULL,
    supp_address3      varchar(50)    NULL,
    supp_city          smallint       NULL,
    supp_post          varchar(9)     NULL,
    supp_attention1    varchar(24)    NULL,
    supp_attention2    varchar(24)    NULL,
    supp_tele1         varchar(16)    NULL,
    supp_ext1          varchar(4)     NULL,
    supp_tele2         varchar(16)    NULL,
    supp_ext2          varchar(4)     NULL,
    supp_fax1          varchar(16)    NULL,
    supp_fax2          varchar(16)    NULL,
    supp_pur_accnt     varchar(8)     NULL,
    supp_pur_subant    varchar(8)     NULL,
    supp_pur_cstcen    varchar(8)     NULL,
    supp_ap_account    varchar(8)     NULL,
    supp_ap_subacnt    varchar(8)     NULL,
    supp_ap_cstcen     varchar(8)     NULL,
    supp_bank          varchar(8)     NULL,
    supp_curr_code     varchar(8)     NULL,
    supp_cr_terms      varchar(20)    NULL,
    supp_contact       varchar(20)    NULL,
    cont_fr_date       datetime       NULL,
    cont_to_date       datetime       NULL,
    is_enabled         int            DEFAULT 1 NOT NULL,
    is_air_tkt_supp    int            DEFAULT 0 NOT NULL,
    promote_status     int            DEFAULT 1 NOT NULL,
    confirm_date       datetime       NOT NULL,
    supp_country       smallint       DEFAULT 1 NOT NULL,
    CONSTRAINT PK82 PRIMARY KEY CLUSTERED (supp_id)
)
go



IF OBJECT_ID('supplier_history') IS NOT NULL
    PRINT '<<< CREATED TABLE supplier_history >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE supplier_history >>>'
go

/* 
 * TABLE: supplier_item 
 */

CREATE TABLE supplier_item(
    item_id            int               IDENTITY(1,1),
    supp_id            int               DEFAULT 0 NOT NULL,
    pur_subctgy_id     int               DEFAULT 0 NOT NULL,
    curr_cd            varchar(8)        NOT NULL,
    item_sepc          varchar(255)      NOT NULL,
    item_unit_price    decimal(12, 2)    DEFAULT 0.00 NOT NULL,
    is_enabled         int               DEFAULT 1 NOT NULL,
    ref_erp_no         varchar(8)        NULL,
    CONSTRAINT PK83 PRIMARY KEY CLUSTERED (item_id)
)
go



IF OBJECT_ID('supplier_item') IS NOT NULL
    PRINT '<<< CREATED TABLE supplier_item >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE supplier_item >>>'
go

/* 
 * TABLE: system_log 
 */

CREATE TABLE system_log(
    log_id         int             IDENTITY(1,1),
    site_id        smallint        DEFAULT 0 NOT NULL,
    object         varchar(50)     NOT NULL,
    obj_id         varchar(255)    NOT NULL,
    [action]    	 varchar(50)     NOT NULL,
    key_field      varchar(255)    NULL,
    action_time    datetime        NOT NULL,
    user_id        int             NULL,
    CONSTRAINT PK84 PRIMARY KEY CLUSTERED (log_id)
)
go



IF OBJECT_ID('system_log') IS NOT NULL
    PRINT '<<< CREATED TABLE system_log >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE system_log >>>'
go

/* 
 * TABLE: ta_approver 
 */

CREATE TABLE ta_approver(
    list_id            varchar(32)     NOT NULL,
    approver_id        int             DEFAULT 0 NOT NULL,
    actual_approver    int             NULL,
    your_turn_date     datetime        NULL,
    email_date         datetime        NULL,
    email_times        int             DEFAULT 0000000000 NOT NULL,
    approve_seq        smallint        DEFAULT 0 NOT NULL,
    approve_status     int             DEFAULT 0 NOT NULL,
    approve_date       datetime        NULL,
    approve_comment    varchar(255)    NULL,
    can_modify         int             DEFAULT 0 NOT NULL,
    CONSTRAINT PK85 PRIMARY KEY CLUSTERED (list_id, approver_id)
)
go



IF OBJECT_ID('ta_approver') IS NOT NULL
    PRINT '<<< CREATED TABLE ta_approver >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE ta_approver >>>'
go

/* 
 * TABLE: ta_history 
 */

CREATE TABLE ta_history(
    ta_his_id         int            IDENTITY(1,1),
    ta_no             varchar(16)    NOT NULL,
    ta_status         int            DEFAULT 0 NOT NULL,
    fr_city           smallint       DEFAULT 0 NOT NULL,
    to_city           smallint       DEFAULT 0 NOT NULL,
    hotel_id          int            NULL,
    hotel_nm          varchar(50)    NULL,
    room_id           int            NULL,
    room_desc         varchar(50)    NULL,
    chk_in_date       datetime       NULL,
    chk_out_date      datetime       NULL,
    travel_mode       int            DEFAULT 0 NOT NULL,
    sgl_or_rtn        int            DEFAULT 0 NOT NULL,
    travel_date_fr    datetime       NULL,
    travel_date_to    datetime       NULL,
    list_id           varchar(32)    NULL,
    leave_time        datetime       NULL,
    back_time         datetime       NULL,
    CONSTRAINT PK86 PRIMARY KEY CLUSTERED (ta_his_id)
)
go



IF OBJECT_ID('ta_history') IS NOT NULL
    PRINT '<<< CREATED TABLE ta_history >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE ta_history >>>'
go

/* 
 * TABLE: travel_application 
 */

CREATE TABLE travel_application(
    ta_no             varchar(16)       NOT NULL,
    ta_dep            smallint          DEFAULT 0 NOT NULL,
    ta_title          varchar(50)       NOT NULL,
    ta_desc           text              NULL,
    ta_status         int               DEFAULT 0 NOT NULL,
    fr_city           smallint          DEFAULT 0 NOT NULL,
    to_city           smallint          DEFAULT 0 NOT NULL,
    hotel_id          int               NULL,
    hotel_nm          varchar(50)       NULL,
    room_id           int               NULL,
    room_desc         varchar(50)       NULL,
    chk_in_date       datetime          NULL,
    chk_out_date      datetime          NULL,
    travel_mode       int               DEFAULT 0 NOT NULL,
    sgl_or_rtn        int               DEFAULT 0 NOT NULL,
    travel_date_fr    datetime          NULL,
    travel_date_to    datetime          NULL,
    leave_time        datetime          NULL,
    back_time         datetime          NULL,
    requestor         int               DEFAULT 0 NOT NULL,
    creator           int               DEFAULT 0 NOT NULL,
    req_date          datetime          NULL,
    booker            int               NULL,
    list_id           varchar(32)       NULL,
    request_type      int               DEFAULT 0 NOT NULL,
    book_type         int               DEFAULT 0 NOT NULL,
    create_date       datetime          DEFAULT 0000-00-00 NOT NULL,
    approve_date      datetime          NULL,
    email_date        datetime          NULL,
    email_times       int               DEFAULT 0 NOT NULL,
    ta_fee            decimal(14, 2)    DEFAULT 0.00 NOT NULL,
    is_on_travel      int               NULL,
    CONSTRAINT PK87 PRIMARY KEY CLUSTERED (ta_no)
)
go



IF OBJECT_ID('travel_application') IS NOT NULL
    PRINT '<<< CREATED TABLE travel_application >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE travel_application >>>'
go

/* 
 * TABLE: travel_group_det 
 */

CREATE TABLE travel_group_det(
    tvl_grp_id         int              DEFAULT 0 NOT NULL,
    exp_subctgy_id     int              DEFAULT 0 NOT NULL,
    domes_amt_limit    decimal(9, 2)    NULL,
    overs_amt_limit    decimal(9, 2)    NULL,
    CONSTRAINT PK88 PRIMARY KEY CLUSTERED (tvl_grp_id, exp_subctgy_id)
)
go



IF OBJECT_ID('travel_group_det') IS NOT NULL
    PRINT '<<< CREATED TABLE travel_group_det >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE travel_group_det >>>'
go

/* 
 * TABLE: travel_group_mst 
 */

CREATE TABLE travel_group_mst(
    tvl_grp_id      int            IDENTITY(1,1),
    tvl_grp_nm      varchar(50)    NULL,
    tvl_grp_site    smallint       NULL,
    is_enabled      int            DEFAULT 1 NOT NULL,
    CONSTRAINT PK89 PRIMARY KEY CLUSTERED (tvl_grp_id)
)
go



IF OBJECT_ID('travel_group_mst') IS NOT NULL
    PRINT '<<< CREATED TABLE travel_group_mst >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE travel_group_mst >>>'
go

/* 
 * TABLE: user_department 
 */

CREATE TABLE user_department(
    user_id    int            DEFAULT 0 NOT NULL,
    dep_id     smallint       DEFAULT 0 NOT NULL,
    title      varchar(50)    NULL,
    CONSTRAINT PK90 PRIMARY KEY CLUSTERED (user_id, dep_id)
)
go



IF OBJECT_ID('user_department') IS NOT NULL
    PRINT '<<< CREATED TABLE user_department >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE user_department >>>'
go

/* 
 * TABLE: user_role 
 */

CREATE TABLE user_role(
    user_id         int         DEFAULT 0 NOT NULL,
    role_id         int         DEFAULT 0 NOT NULL,
    granted_dep     smallint    NULL,
    granted_site    smallint    NULL,
    user_role_id    int         IDENTITY(1,1),
    CONSTRAINT PK91 PRIMARY KEY CLUSTERED (user_role_id)
)
go



IF OBJECT_ID('user_role') IS NOT NULL
    PRINT '<<< CREATED TABLE user_role >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE user_role >>>'
go

/* 
 * TABLE: user_site 
 */

CREATE TABLE user_site(
    user_id       int         DEFAULT 0 NOT NULL,
    site_id       smallint    DEFAULT 0 NOT NULL,
    tvl_grp_id    int         DEFAULT 0 NOT NULL,
    CONSTRAINT PK92 PRIMARY KEY CLUSTERED (user_id, site_id)
)
go



IF OBJECT_ID('user_site') IS NOT NULL
    PRINT '<<< CREATED TABLE user_site >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE user_site >>>'
go

/* 
 * TABLE: yearly_bgt_dep_history 
 */

CREATE TABLE yearly_bgt_dep_history(
    bg_hist_dep_id    int         IDENTITY(1,1),
    budget_hist_id    int         DEFAULT 0 NOT NULL,
    budget_dep_id     smallint    DEFAULT 0 NOT NULL,
    CONSTRAINT PK93 PRIMARY KEY CLUSTERED (bg_hist_dep_id)
)
go



IF OBJECT_ID('yearly_bgt_dep_history') IS NOT NULL
    PRINT '<<< CREATED TABLE yearly_bgt_dep_history >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE yearly_bgt_dep_history >>>'
go

/* 
 * TABLE: yearly_budget 
 */

CREATE TABLE yearly_budget(
    budget_id          int               IDENTITY(1,1),
    budget_site        smallint          DEFAULT 0 NOT NULL,
    budget_cd          varchar(20)       NOT NULL,
    budget_nm          varchar(50)       NOT NULL,
    budget_type        int               DEFAULT 0 NOT NULL,
    budget_year        smallint          DEFAULT 0 NOT NULL,
    budget_amt         decimal(14, 2)    DEFAULT 0.00 NOT NULL,
    budget_actulamt    decimal(14, 2)    DEFAULT 0.00 NOT NULL,
    pur_ctgy_id        int               NULL,
    pur_subctgy_id     int               NULL,
    budget_version     smallint          DEFAULT 0 NOT NULL,
    budget_status      int               DEFAULT 0 NOT NULL,
    is_freeze          int               DEFAULT 0 NOT NULL,
    create_user        int               DEFAULT 0 NOT NULL,
    create_date        datetime          NOT NULL,
    modify_user        int               NULL,
    modify_date        datetime          NULL,
    row_version        int               DEFAULT 0 NOT NULL,
    CONSTRAINT PK94 PRIMARY KEY CLUSTERED (budget_id)
)
go



IF OBJECT_ID('yearly_budget') IS NOT NULL
    PRINT '<<< CREATED TABLE yearly_budget >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE yearly_budget >>>'
go

/* 
 * TABLE: yearly_budget_department 
 */

CREATE TABLE yearly_budget_department(
    bg_dep_id        int         IDENTITY(1,1),
    budget_id        int         DEFAULT 0 NOT NULL,
    budget_dep_id    smallint    DEFAULT 0 NOT NULL,
    CONSTRAINT PK95 PRIMARY KEY CLUSTERED (bg_dep_id)
)
go



IF OBJECT_ID('yearly_budget_department') IS NOT NULL
    PRINT '<<< CREATED TABLE yearly_budget_department >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE yearly_budget_department >>>'
go

/* 
 * TABLE: yearly_budget_history 
 */

CREATE TABLE yearly_budget_history(
    budget_hist_id    int               IDENTITY(1,1),
    budget_id         int               DEFAULT 0 NOT NULL,
    budget_version    smallint          DEFAULT 0 NOT NULL,
    budget_nm         varchar(50)       NOT NULL,
    budget_amt        decimal(14, 2)    DEFAULT 0.00 NOT NULL,
    create_user       int               DEFAULT 0 NOT NULL,
    create_date       datetime          NOT NULL,
    CONSTRAINT PK96 PRIMARY KEY CLUSTERED (budget_hist_id)
)
go



IF OBJECT_ID('yearly_budget_history') IS NOT NULL
    PRINT '<<< CREATED TABLE yearly_budget_history >>>'
ELSE
    PRINT '<<< FAILED CREATING TABLE yearly_budget_history >>>'
go

/* 
 * INDEX: fk_air_ticket_to_travel_application 
 */

CREATE INDEX fk_air_ticket_to_travel_application ON air_ticket(ta_no)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('air_ticket') AND name='fk_air_ticket_to_travel_application')
    PRINT '<<< CREATED INDEX air_ticket.fk_air_ticket_to_travel_application >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX air_ticket.fk_air_ticket_to_travel_application >>>'
go

/* 
 * INDEX: fk_air_ticket_to_po_item 
 */

CREATE INDEX fk_air_ticket_to_po_item ON air_ticket(po_item_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('air_ticket') AND name='fk_air_ticket_to_po_item')
    PRINT '<<< CREATED INDEX air_ticket.fk_air_ticket_to_po_item >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX air_ticket.fk_air_ticket_to_po_item >>>'
go

/* 
 * INDEX: fk_air_ticket_to_po_item2 
 */

CREATE INDEX fk_air_ticket_to_po_item2 ON air_ticket(rtn_po_item_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('air_ticket') AND name='fk_air_ticket_to_po_item2')
    PRINT '<<< CREATED INDEX air_ticket.fk_air_ticket_to_po_item2 >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX air_ticket.fk_air_ticket_to_po_item2 >>>'
go

/* 
 * INDEX: fk_air_ticket_to_purchase_type 
 */

CREATE INDEX fk_air_ticket_to_purchase_type ON air_ticket(pur_type_cd)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('air_ticket') AND name='fk_air_ticket_to_purchase_type')
    PRINT '<<< CREATED INDEX air_ticket.fk_air_ticket_to_purchase_type >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX air_ticket.fk_air_ticket_to_purchase_type >>>'
go

/* 
 * INDEX: fk_air_ticket_to_supplier 
 */

CREATE INDEX fk_air_ticket_to_supplier ON air_ticket(supp_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('air_ticket') AND name='fk_air_ticket_to_supplier')
    PRINT '<<< CREATED INDEX air_ticket.fk_air_ticket_to_supplier >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX air_ticket.fk_air_ticket_to_supplier >>>'
go

/* 
 * INDEX: fk_air_ticket_to_currency 
 */

CREATE INDEX fk_air_ticket_to_currency ON air_ticket(exch_rate_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('air_ticket') AND name='fk_air_ticket_to_currency')
    PRINT '<<< CREATED INDEX air_ticket.fk_air_ticket_to_currency >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX air_ticket.fk_air_ticket_to_currency >>>'
go

/* 
 * INDEX: fk_air_ticket_recharge_to_air_ticket 
 */

CREATE INDEX fk_air_ticket_recharge_to_air_ticket ON air_ticket_recharge(air_ticket_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('air_ticket_recharge') AND name='fk_air_ticket_recharge_to_air_ticket')
    PRINT '<<< CREATED INDEX air_ticket_recharge.fk_air_ticket_recharge_to_air_ticket >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX air_ticket_recharge.fk_air_ticket_recharge_to_air_ticket >>>'
go

/* 
 * INDEX: fk_air_ticket_recharge_to_customer 
 */

CREATE INDEX fk_air_ticket_recharge_to_customer ON air_ticket_recharge(cust_cd)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('air_ticket_recharge') AND name='fk_air_ticket_recharge_to_customer')
    PRINT '<<< CREATED INDEX air_ticket_recharge.fk_air_ticket_recharge_to_customer >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX air_ticket_recharge.fk_air_ticket_recharge_to_customer >>>'
go

/* 
 * INDEX: fk_air_ticket_recharge_to_user 
 */

CREATE INDEX fk_air_ticket_recharge_to_user ON air_ticket_recharge(person_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('air_ticket_recharge') AND name='fk_air_ticket_recharge_to_user')
    PRINT '<<< CREATED INDEX air_ticket_recharge.fk_air_ticket_recharge_to_user >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX air_ticket_recharge.fk_air_ticket_recharge_to_user >>>'
go

/* 
 * INDEX: fk_air_ticket_recharge_to_department 
 */

CREATE INDEX fk_air_ticket_recharge_to_department ON air_ticket_recharge(person_dep_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('air_ticket_recharge') AND name='fk_air_ticket_recharge_to_department')
    PRINT '<<< CREATED INDEX air_ticket_recharge.fk_air_ticket_recharge_to_department >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX air_ticket_recharge.fk_air_ticket_recharge_to_department >>>'
go

/* 
 * INDEX: fk_approver_delegate_to_user 
 */

CREATE INDEX fk_approver_delegate_to_user ON approver_delegate(org_approver)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('approver_delegate') AND name='fk_approver_delegate_to_user')
    PRINT '<<< CREATED INDEX approver_delegate.fk_approver_delegate_to_user >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX approver_delegate.fk_approver_delegate_to_user >>>'
go

/* 
 * INDEX: fk_approver_delegate_to_user2 
 */

CREATE INDEX fk_approver_delegate_to_user2 ON approver_delegate(dlgt_approver)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('approver_delegate') AND name='fk_approver_delegate_to_user2')
    PRINT '<<< CREATED INDEX approver_delegate.fk_approver_delegate_to_user2 >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX approver_delegate.fk_approver_delegate_to_user2 >>>'
go

/* 
 * INDEX: fk_capex_to_site 
 */

CREATE INDEX fk_capex_to_site ON capex(capex_site)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('capex') AND name='fk_capex_to_site')
    PRINT '<<< CREATED INDEX capex.fk_capex_to_site >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX capex.fk_capex_to_site >>>'
go

/* 
 * INDEX: fk_capex_to_purchase_ctgy 
 */

CREATE INDEX fk_capex_to_purchase_ctgy ON capex(pur_ctgy_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('capex') AND name='fk_capex_to_purchase_ctgy')
    PRINT '<<< CREATED INDEX capex.fk_capex_to_purchase_ctgy >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX capex.fk_capex_to_purchase_ctgy >>>'
go

/* 
 * INDEX: fk_capex_to_purchase_subctgy 
 */

CREATE INDEX fk_capex_to_purchase_subctgy ON capex(pur_subctgy_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('capex') AND name='fk_capex_to_purchase_subctgy')
    PRINT '<<< CREATED INDEX capex.fk_capex_to_purchase_subctgy >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX capex.fk_capex_to_purchase_subctgy >>>'
go

/* 
 * INDEX: fk_capex_to_currency 
 */

CREATE INDEX fk_capex_to_currency ON capex(base_curr_cd)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('capex') AND name='fk_capex_to_currency')
    PRINT '<<< CREATED INDEX capex.fk_capex_to_currency >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX capex.fk_capex_to_currency >>>'
go

/* 
 * INDEX: fk_capex_to_yearly_budget 
 */

CREATE INDEX fk_capex_to_yearly_budget ON capex(budget_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('capex') AND name='fk_capex_to_yearly_budget')
    PRINT '<<< CREATED INDEX capex.fk_capex_to_yearly_budget >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX capex.fk_capex_to_yearly_budget >>>'
go

/* 
 * INDEX: fk_capex_to_capex_request 
 */

CREATE INDEX fk_capex_to_capex_request ON capex(capex_req_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('capex') AND name='fk_capex_to_capex_request')
    PRINT '<<< CREATED INDEX capex.fk_capex_to_capex_request >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX capex.fk_capex_to_capex_request >>>'
go

/* 
 * INDEX: fk_capex_department_to_capex 
 */

CREATE INDEX fk_capex_department_to_capex ON capex_department(capex_no)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('capex_department') AND name='fk_capex_department_to_capex')
    PRINT '<<< CREATED INDEX capex_department.fk_capex_department_to_capex >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX capex_department.fk_capex_department_to_capex >>>'
go

/* 
 * INDEX: fk_capex_department_to_department 
 */

CREATE INDEX fk_capex_department_to_department ON capex_department(capex_dep_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('capex_department') AND name='fk_capex_department_to_department')
    PRINT '<<< CREATED INDEX capex_department.fk_capex_department_to_department >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX capex_department.fk_capex_department_to_department >>>'
go

/* 
 * INDEX: fk_capex_req_item_history_to_supplier_item 
 */

CREATE INDEX fk_capex_req_item_history_to_supplier_item ON capex_req_item_history(item_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('capex_req_item_history') AND name='fk_capex_req_item_history_to_supplier_item')
    PRINT '<<< CREATED INDEX capex_req_item_history.fk_capex_req_item_history_to_supplier_item >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX capex_req_item_history.fk_capex_req_item_history_to_supplier_item >>>'
go

/* 
 * INDEX: fk_capex_req_item_history_to_supplier 
 */

CREATE INDEX fk_capex_req_item_history_to_supplier ON capex_req_item_history(supp_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('capex_req_item_history') AND name='fk_capex_req_item_history_to_supplier')
    PRINT '<<< CREATED INDEX capex_req_item_history.fk_capex_req_item_history_to_supplier >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX capex_req_item_history.fk_capex_req_item_history_to_supplier >>>'
go

/* 
 * INDEX: fk_capex_req_item_history_to_purchase_subctgy 
 */

CREATE INDEX fk_capex_req_item_history_to_purchase_subctgy ON capex_req_item_history(pur_subctgy_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('capex_req_item_history') AND name='fk_capex_req_item_history_to_purchase_subctgy')
    PRINT '<<< CREATED INDEX capex_req_item_history.fk_capex_req_item_history_to_purchase_subctgy >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX capex_req_item_history.fk_capex_req_item_history_to_purchase_subctgy >>>'
go

/* 
 * INDEX: fk_capex_req_item_history_to_capex_request_history 
 */

CREATE INDEX fk_capex_req_item_history_to_capex_request_history ON capex_req_item_history(cr_hist_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('capex_req_item_history') AND name='fk_capex_req_item_history_to_capex_request_history')
    PRINT '<<< CREATED INDEX capex_req_item_history.fk_capex_req_item_history_to_capex_request_history >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX capex_req_item_history.fk_capex_req_item_history_to_capex_request_history >>>'
go

/* 
 * INDEX: fk_capex_req_item_history_to_currency 
 */

CREATE INDEX fk_capex_req_item_history_to_currency ON capex_req_item_history(exch_rate_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('capex_req_item_history') AND name='fk_capex_req_item_history_to_currency')
    PRINT '<<< CREATED INDEX capex_req_item_history.fk_capex_req_item_history_to_currency >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX capex_req_item_history.fk_capex_req_item_history_to_currency >>>'
go

/* 
 * INDEX: fk_capex_request_to_capex 
 */

CREATE INDEX fk_capex_request_to_capex ON capex_request(capex_no)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('capex_request') AND name='fk_capex_request_to_capex')
    PRINT '<<< CREATED INDEX capex_request.fk_capex_request_to_capex >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX capex_request.fk_capex_request_to_capex >>>'
go

/* 
 * INDEX: fk_capex_request_to_user 
 */

CREATE INDEX fk_capex_request_to_user ON capex_request(requestor)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('capex_request') AND name='fk_capex_request_to_user')
    PRINT '<<< CREATED INDEX capex_request.fk_capex_request_to_user >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX capex_request.fk_capex_request_to_user >>>'
go

/* 
 * INDEX: fk_capex_request_to_user2 
 */

CREATE INDEX fk_capex_request_to_user2 ON capex_request(proj_leader_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('capex_request') AND name='fk_capex_request_to_user2')
    PRINT '<<< CREATED INDEX capex_request.fk_capex_request_to_user2 >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX capex_request.fk_capex_request_to_user2 >>>'
go

/* 
 * INDEX: fk_capex_request_to_currency 
 */

CREATE INDEX fk_capex_request_to_currency ON capex_request(ref_curr_cd)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('capex_request') AND name='fk_capex_request_to_currency')
    PRINT '<<< CREATED INDEX capex_request.fk_capex_request_to_currency >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX capex_request.fk_capex_request_to_currency >>>'
go

/* 
 * INDEX: fk_capex_request_approver_to_user 
 */

CREATE INDEX fk_capex_request_approver_to_user ON capex_request_approver(approver_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('capex_request_approver') AND name='fk_capex_request_approver_to_user')
    PRINT '<<< CREATED INDEX capex_request_approver.fk_capex_request_approver_to_user >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX capex_request_approver.fk_capex_request_approver_to_user >>>'
go

/* 
 * INDEX: fk_capex_request_approver_to_user2 
 */

CREATE INDEX fk_capex_request_approver_to_user2 ON capex_request_approver(actual_approver)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('capex_request_approver') AND name='fk_capex_request_approver_to_user2')
    PRINT '<<< CREATED INDEX capex_request_approver.fk_capex_request_approver_to_user2 >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX capex_request_approver.fk_capex_request_approver_to_user2 >>>'
go

/* 
 * INDEX: fk_capex_request_attach_to_capex_request 
 */

CREATE INDEX fk_capex_request_attach_to_capex_request ON capex_request_attach(capex_req_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('capex_request_attach') AND name='fk_capex_request_attach_to_capex_request')
    PRINT '<<< CREATED INDEX capex_request_attach.fk_capex_request_attach_to_capex_request >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX capex_request_attach.fk_capex_request_attach_to_capex_request >>>'
go

/* 
 * INDEX: fk_capex_request_history_to_capex_request 
 */

CREATE INDEX fk_capex_request_history_to_capex_request ON capex_request_history(capex_req_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('capex_request_history') AND name='fk_capex_request_history_to_capex_request')
    PRINT '<<< CREATED INDEX capex_request_history.fk_capex_request_history_to_capex_request >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX capex_request_history.fk_capex_request_history_to_capex_request >>>'
go

/* 
 * INDEX: fk_capex_request_item_to_supplier_item 
 */

CREATE INDEX fk_capex_request_item_to_supplier_item ON capex_request_item(item_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('capex_request_item') AND name='fk_capex_request_item_to_supplier_item')
    PRINT '<<< CREATED INDEX capex_request_item.fk_capex_request_item_to_supplier_item >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX capex_request_item.fk_capex_request_item_to_supplier_item >>>'
go

/* 
 * INDEX: fk_capex_request_item_to_supplier 
 */

CREATE INDEX fk_capex_request_item_to_supplier ON capex_request_item(supp_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('capex_request_item') AND name='fk_capex_request_item_to_supplier')
    PRINT '<<< CREATED INDEX capex_request_item.fk_capex_request_item_to_supplier >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX capex_request_item.fk_capex_request_item_to_supplier >>>'
go

/* 
 * INDEX: fk_capex_request_item_to_purchase_subctgy 
 */

CREATE INDEX fk_capex_request_item_to_purchase_subctgy ON capex_request_item(pur_subctgy_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('capex_request_item') AND name='fk_capex_request_item_to_purchase_subctgy')
    PRINT '<<< CREATED INDEX capex_request_item.fk_capex_request_item_to_purchase_subctgy >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX capex_request_item.fk_capex_request_item_to_purchase_subctgy >>>'
go

/* 
 * INDEX: fk_capex_request_item_to_capex_request 
 */

CREATE INDEX fk_capex_request_item_to_capex_request ON capex_request_item(capex_req_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('capex_request_item') AND name='fk_capex_request_item_to_capex_request')
    PRINT '<<< CREATED INDEX capex_request_item.fk_capex_request_item_to_capex_request >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX capex_request_item.fk_capex_request_item_to_capex_request >>>'
go

/* 
 * INDEX: fk_capex_request_item_to_currency 
 */

CREATE INDEX fk_capex_request_item_to_currency ON capex_request_item(exch_rate_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('capex_request_item') AND name='fk_capex_request_item_to_currency')
    PRINT '<<< CREATED INDEX capex_request_item.fk_capex_request_item_to_currency >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX capex_request_item.fk_capex_request_item_to_currency >>>'
go

/* 
 * INDEX: fk_city_to_province 
 */

CREATE INDEX fk_city_to_province ON city(province_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('city') AND name='fk_city_to_province')
    PRINT '<<< CREATED INDEX city.fk_city_to_province >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX city.fk_city_to_province >>>'
go

/* 
 * INDEX: fk_city_to_site 
 */

CREATE INDEX fk_city_to_site ON city(maint_site)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('city') AND name='fk_city_to_site')
    PRINT '<<< CREATED INDEX city.fk_city_to_site >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX city.fk_city_to_site >>>'
go

/* 
 * INDEX: fk_country_to_site 
 */

CREATE INDEX fk_country_to_site ON country(maint_site)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('country') AND name='fk_country_to_site')
    PRINT '<<< CREATED INDEX country.fk_country_to_site >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX country.fk_country_to_site >>>'
go

/* 
 * INDEX: fk_customer_to_site 
 */

CREATE INDEX fk_customer_to_site ON customer(cust_site)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('customer') AND name='fk_customer_to_site')
    PRINT '<<< CREATED INDEX customer.fk_customer_to_site >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX customer.fk_customer_to_site >>>'
go

/* 
 * INDEX: fk_department_to_site 
 */

CREATE INDEX fk_department_to_site ON department(dep_site)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('department') AND name='fk_department_to_site')
    PRINT '<<< CREATED INDEX department.fk_department_to_site >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX department.fk_department_to_site >>>'
go

/* 
 * INDEX: fk_department_to_department 
 */

CREATE INDEX fk_department_to_department ON department(parent_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('department') AND name='fk_department_to_department')
    PRINT '<<< CREATED INDEX department.fk_department_to_department >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX department.fk_department_to_department >>>'
go

/* 
 * INDEX: fk_department_to_user 
 */

CREATE INDEX fk_department_to_user ON department(dep_mgr)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('department') AND name='fk_department_to_user')
    PRINT '<<< CREATED INDEX department.fk_department_to_user >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX department.fk_department_to_user >>>'
go

/* 
 * INDEX: email_to_site 
 */

CREATE INDEX email_to_site ON email(site)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('email') AND name='email_to_site')
    PRINT '<<< CREATED INDEX email.email_to_site >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX email.email_to_site >>>'
go

/* 
 * INDEX: fk_exchange_rate_to_site 
 */

CREATE INDEX fk_exchange_rate_to_site ON exchange_rate(site_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('exchange_rate') AND name='fk_exchange_rate_to_site')
    PRINT '<<< CREATED INDEX exchange_rate.fk_exchange_rate_to_site >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX exchange_rate.fk_exchange_rate_to_site >>>'
go

/* 
 * INDEX: uk_exchange_rate 
 */

CREATE INDEX uk_exchange_rate ON exchange_rate(curr_code, site_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('exchange_rate') AND name='uk_exchange_rate')
    PRINT '<<< CREATED INDEX exchange_rate.uk_exchange_rate >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX exchange_rate.uk_exchange_rate >>>'
go

/* 
 * INDEX: uk_exp_cell_det_hist 
 */

CREATE UNIQUE INDEX uk_exp_cell_det_hist ON exp_cell_det_hist(exp_hist_row_id, exp_subctgy_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('exp_cell_det_hist') AND name='uk_exp_cell_det_hist')
    PRINT '<<< CREATED INDEX exp_cell_det_hist.uk_exp_cell_det_hist >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX exp_cell_det_hist.uk_exp_cell_det_hist >>>'
go

/* 
 * INDEX: fk_exp_cell_det_hist_to_expense_subctgy 
 */

CREATE INDEX fk_exp_cell_det_hist_to_expense_subctgy ON exp_cell_det_hist(exp_subctgy_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('exp_cell_det_hist') AND name='fk_exp_cell_det_hist_to_expense_subctgy')
    PRINT '<<< CREATED INDEX exp_cell_det_hist.fk_exp_cell_det_hist_to_expense_subctgy >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX exp_cell_det_hist.fk_exp_cell_det_hist_to_expense_subctgy >>>'
go

/* 
 * INDEX: fk_exp_row_det_hist_to_expense_history 
 */

CREATE INDEX fk_exp_row_det_hist_to_expense_history ON exp_row_det_hist(exp_hist_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('exp_row_det_hist') AND name='fk_exp_row_det_hist_to_expense_history')
    PRINT '<<< CREATED INDEX exp_row_det_hist.fk_exp_row_det_hist_to_expense_history >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX exp_row_det_hist.fk_exp_row_det_hist_to_expense_history >>>'
go

/* 
 * INDEX: FK_exp_row_det_hist_to_proj_cd 
 */

CREATE INDEX FK_exp_row_det_hist_to_proj_cd ON exp_row_det_hist(proj_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('exp_row_det_hist') AND name='FK_exp_row_det_hist_to_proj_cd')
    PRINT '<<< CREATED INDEX exp_row_det_hist.FK_exp_row_det_hist_to_proj_cd >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX exp_row_det_hist.FK_exp_row_det_hist_to_proj_cd >>>'
go

/* 
 * INDEX: fk_expense_to_expense_ctgy 
 */

CREATE INDEX fk_expense_to_expense_ctgy ON expense(exp_ctgy_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('expense') AND name='fk_expense_to_expense_ctgy')
    PRINT '<<< CREATED INDEX expense.fk_expense_to_expense_ctgy >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX expense.fk_expense_to_expense_ctgy >>>'
go

/* 
 * INDEX: fk_expense_to_currency 
 */

CREATE INDEX fk_expense_to_currency ON expense(exp_curr_cd)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('expense') AND name='fk_expense_to_currency')
    PRINT '<<< CREATED INDEX expense.fk_expense_to_currency >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX expense.fk_expense_to_currency >>>'
go

/* 
 * INDEX: fk_expense_to_currency2 
 */

CREATE INDEX fk_expense_to_currency2 ON expense(base_curr_cd)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('expense') AND name='fk_expense_to_currency2')
    PRINT '<<< CREATED INDEX expense.fk_expense_to_currency2 >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX expense.fk_expense_to_currency2 >>>'
go

/* 
 * INDEX: fk_expense_to_user 
 */

CREATE INDEX fk_expense_to_user ON expense(requestor)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('expense') AND name='fk_expense_to_user')
    PRINT '<<< CREATED INDEX expense.fk_expense_to_user >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX expense.fk_expense_to_user >>>'
go

/* 
 * INDEX: fk_expense_to_user2 
 */

CREATE INDEX fk_expense_to_user2 ON expense(creator)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('expense') AND name='fk_expense_to_user2')
    PRINT '<<< CREATED INDEX expense.fk_expense_to_user2 >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX expense.fk_expense_to_user2 >>>'
go

/* 
 * INDEX: fk_expense_to_travel_application 
 */

CREATE INDEX fk_expense_to_travel_application ON expense(ta_no)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('expense') AND name='fk_expense_to_travel_application')
    PRINT '<<< CREATED INDEX expense.fk_expense_to_travel_application >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX expense.fk_expense_to_travel_application >>>'
go

/* 
 * INDEX: fk_expense_to_department 
 */

CREATE INDEX fk_expense_to_department ON expense(exp_dep)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('expense') AND name='fk_expense_to_department')
    PRINT '<<< CREATED INDEX expense.fk_expense_to_department >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX expense.fk_expense_to_department >>>'
go

/* 
 * INDEX: fk_expense_approver_to_user 
 */

CREATE INDEX fk_expense_approver_to_user ON expense_approver(approver_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('expense_approver') AND name='fk_expense_approver_to_user')
    PRINT '<<< CREATED INDEX expense_approver.fk_expense_approver_to_user >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX expense_approver.fk_expense_approver_to_user >>>'
go

/* 
 * INDEX: fk_expense_approver_to_user2 
 */

CREATE INDEX fk_expense_approver_to_user2 ON expense_approver(actual_approver)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('expense_approver') AND name='fk_expense_approver_to_user2')
    PRINT '<<< CREATED INDEX expense_approver.fk_expense_approver_to_user2 >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX expense_approver.fk_expense_approver_to_user2 >>>'
go

/* 
 * INDEX: fk_expense_attach_to_expense 
 */

CREATE INDEX fk_expense_attach_to_expense ON expense_attach(exp_no)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('expense_attach') AND name='fk_expense_attach_to_expense')
    PRINT '<<< CREATED INDEX expense_attach.fk_expense_attach_to_expense >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX expense_attach.fk_expense_attach_to_expense >>>'
go

/* 
 * INDEX: uk_expense_cell_det 
 */

CREATE UNIQUE INDEX uk_expense_cell_det ON expense_cell_det(exp_row_id, exp_subctgy_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('expense_cell_det') AND name='uk_expense_cell_det')
    PRINT '<<< CREATED INDEX expense_cell_det.uk_expense_cell_det >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX expense_cell_det.uk_expense_cell_det >>>'
go

/* 
 * INDEX: fk_expense_cell_det_to_expense_subctgy 
 */

CREATE INDEX fk_expense_cell_det_to_expense_subctgy ON expense_cell_det(exp_subctgy_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('expense_cell_det') AND name='fk_expense_cell_det_to_expense_subctgy')
    PRINT '<<< CREATED INDEX expense_cell_det.fk_expense_cell_det_to_expense_subctgy >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX expense_cell_det.fk_expense_cell_det_to_expense_subctgy >>>'
go

/* 
 * INDEX: uk_expense_claim 
 */

CREATE UNIQUE INDEX uk_expense_claim ON expense_claim(exp_no, exp_subctgy_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('expense_claim') AND name='uk_expense_claim')
    PRINT '<<< CREATED INDEX expense_claim.uk_expense_claim >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX expense_claim.uk_expense_claim >>>'
go

/* 
 * INDEX: fk_expense_claim_to_expense_subctgy 
 */

CREATE INDEX fk_expense_claim_to_expense_subctgy ON expense_claim(exp_subctgy_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('expense_claim') AND name='fk_expense_claim_to_expense_subctgy')
    PRINT '<<< CREATED INDEX expense_claim.fk_expense_claim_to_expense_subctgy >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX expense_claim.fk_expense_claim_to_expense_subctgy >>>'
go

/* 
 * INDEX: FK_expense_claim_to_proj_cd 
 */

CREATE INDEX FK_expense_claim_to_proj_cd ON expense_claim(exp_proj_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('expense_claim') AND name='FK_expense_claim_to_proj_cd')
    PRINT '<<< CREATED INDEX expense_claim.FK_expense_claim_to_proj_cd >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX expense_claim.FK_expense_claim_to_proj_cd >>>'
go

/* 
 * INDEX: fk_expense_ctgy_to_site 
 */

CREATE INDEX fk_expense_ctgy_to_site ON expense_ctgy(exp_ctgy_site)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('expense_ctgy') AND name='fk_expense_ctgy_to_site')
    PRINT '<<< CREATED INDEX expense_ctgy.fk_expense_ctgy_to_site >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX expense_ctgy.fk_expense_ctgy_to_site >>>'
go

/* 
 * INDEX: fk_expense_history_to_expense 
 */

CREATE INDEX fk_expense_history_to_expense ON expense_history(exp_no)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('expense_history') AND name='fk_expense_history_to_expense')
    PRINT '<<< CREATED INDEX expense_history.fk_expense_history_to_expense >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX expense_history.fk_expense_history_to_expense >>>'
go

/* 
 * INDEX: fk_expense_recharge_to_expense 
 */

CREATE INDEX fk_expense_recharge_to_expense ON expense_recharge(exp_no)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('expense_recharge') AND name='fk_expense_recharge_to_expense')
    PRINT '<<< CREATED INDEX expense_recharge.fk_expense_recharge_to_expense >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX expense_recharge.fk_expense_recharge_to_expense >>>'
go

/* 
 * INDEX: fk_expense_recharge_to_customer 
 */

CREATE INDEX fk_expense_recharge_to_customer ON expense_recharge(cust_cd)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('expense_recharge') AND name='fk_expense_recharge_to_customer')
    PRINT '<<< CREATED INDEX expense_recharge.fk_expense_recharge_to_customer >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX expense_recharge.fk_expense_recharge_to_customer >>>'
go

/* 
 * INDEX: fk_expense_recharge_to_user 
 */

CREATE INDEX fk_expense_recharge_to_user ON expense_recharge(person_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('expense_recharge') AND name='fk_expense_recharge_to_user')
    PRINT '<<< CREATED INDEX expense_recharge.fk_expense_recharge_to_user >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX expense_recharge.fk_expense_recharge_to_user >>>'
go

/* 
 * INDEX: fk_expense_recharge_department 
 */

CREATE INDEX fk_expense_recharge_department ON expense_recharge(person_dep_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('expense_recharge') AND name='fk_expense_recharge_department')
    PRINT '<<< CREATED INDEX expense_recharge.fk_expense_recharge_department >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX expense_recharge.fk_expense_recharge_department >>>'
go

/* 
 * INDEX: fk_expense_row_det_to_expense 
 */

CREATE INDEX fk_expense_row_det_to_expense ON expense_row_det(exp_no)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('expense_row_det') AND name='fk_expense_row_det_to_expense')
    PRINT '<<< CREATED INDEX expense_row_det.fk_expense_row_det_to_expense >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX expense_row_det.fk_expense_row_det_to_expense >>>'
go

/* 
 * INDEX: FK_expense_row_det_to_proj_cd 
 */

CREATE INDEX FK_expense_row_det_to_proj_cd ON expense_row_det(proj_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('expense_row_det') AND name='FK_expense_row_det_to_proj_cd')
    PRINT '<<< CREATED INDEX expense_row_det.FK_expense_row_det_to_proj_cd >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX expense_row_det.FK_expense_row_det_to_proj_cd >>>'
go

/* 
 * INDEX: fk_expense_subctgy_to_expense_ctgy 
 */

CREATE INDEX fk_expense_subctgy_to_expense_ctgy ON expense_subctgy(exp_ctgy_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('expense_subctgy') AND name='fk_expense_subctgy_to_expense_ctgy')
    PRINT '<<< CREATED INDEX expense_subctgy.fk_expense_subctgy_to_expense_ctgy >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX expense_subctgy.fk_expense_subctgy_to_expense_ctgy >>>'
go

/* 
 * INDEX: fk_hotel_to_site 
 */

CREATE INDEX fk_hotel_to_site ON hotel(hotel_site)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('hotel') AND name='fk_hotel_to_site')
    PRINT '<<< CREATED INDEX hotel.fk_hotel_to_site >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX hotel.fk_hotel_to_site >>>'
go

/* 
 * INDEX: fk_hotel_to_city 
 */

CREATE INDEX fk_hotel_to_city ON hotel(hotel_city)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('hotel') AND name='fk_hotel_to_city')
    PRINT '<<< CREATED INDEX hotel.fk_hotel_to_city >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX hotel.fk_hotel_to_city >>>'
go

/* 
 * INDEX: fk_hotel_to_currency 
 */

CREATE INDEX fk_hotel_to_currency ON hotel(hotel_curr)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('hotel') AND name='fk_hotel_to_currency')
    PRINT '<<< CREATED INDEX hotel.fk_hotel_to_currency >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX hotel.fk_hotel_to_currency >>>'
go

/* 
 * INDEX: fk_hotel_contract_to_hotel 
 */

CREATE INDEX fk_hotel_contract_to_hotel ON hotel_contract(hotel_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('hotel_contract') AND name='fk_hotel_contract_to_hotel')
    PRINT '<<< CREATED INDEX hotel_contract.fk_hotel_contract_to_hotel >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX hotel_contract.fk_hotel_contract_to_hotel >>>'
go

/* 
 * INDEX: fk_hotel_room_to_hotel 
 */

CREATE INDEX fk_hotel_room_to_hotel ON hotel_room(hotel_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('hotel_room') AND name='fk_hotel_room_to_hotel')
    PRINT '<<< CREATED INDEX hotel_room.fk_hotel_room_to_hotel >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX hotel_room.fk_hotel_room_to_hotel >>>'
go

/* 
 * INDEX: fk_menu_to_menu 
 */

CREATE INDEX fk_menu_to_menu ON menu(parent_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('menu') AND name='fk_menu_to_menu')
    PRINT '<<< CREATED INDEX menu.fk_menu_to_menu >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX menu.fk_menu_to_menu >>>'
go

/* 
 * INDEX: fk_menu_to_function 
 */

CREATE INDEX fk_menu_to_function ON menu(func_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('menu') AND name='fk_menu_to_function')
    PRINT '<<< CREATED INDEX menu.fk_menu_to_function >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX menu.fk_menu_to_function >>>'
go

/* 
 * INDEX: fk_metadata_det_to_metadata_mst 
 */

CREATE INDEX fk_metadata_det_to_metadata_mst ON metadata_det(md_mst_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('metadata_det') AND name='fk_metadata_det_to_metadata_mst')
    PRINT '<<< CREATED INDEX metadata_det.fk_metadata_det_to_metadata_mst >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX metadata_det.fk_metadata_det_to_metadata_mst >>>'
go

/* 
 * INDEX: uk_user_cd 
 */

CREATE UNIQUE INDEX uk_user_cd ON oa_user(user_cd)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('oa_user') AND name='uk_user_cd')
    PRINT '<<< CREATED INDEX oa_user.uk_user_cd >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX oa_user.uk_user_cd >>>'
go

/* 
 * INDEX: fk_user_to_site 
 */

CREATE INDEX fk_user_to_site ON oa_user(primary_site)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('oa_user') AND name='fk_user_to_site')
    PRINT '<<< CREATED INDEX oa_user.fk_user_to_site >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX oa_user.fk_user_to_site >>>'
go

/* 
 * INDEX: fk_payment_schdl_det_to_purchase_order 
 */

CREATE INDEX fk_payment_schdl_det_to_purchase_order ON payment_schdl_det(po_no)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('payment_schdl_det') AND name='fk_payment_schdl_det_to_purchase_order')
    PRINT '<<< CREATED INDEX payment_schdl_det.fk_payment_schdl_det_to_purchase_order >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX payment_schdl_det.fk_payment_schdl_det_to_purchase_order >>>'
go

/* 
 * INDEX: fk_po_approver_to_user 
 */

CREATE INDEX fk_po_approver_to_user ON po_approver(approver_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('po_approver') AND name='fk_po_approver_to_user')
    PRINT '<<< CREATED INDEX po_approver.fk_po_approver_to_user >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX po_approver.fk_po_approver_to_user >>>'
go

/* 
 * INDEX: fk_po_approver_to_user2 
 */

CREATE INDEX fk_po_approver_to_user2 ON po_approver(actual_approver)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('po_approver') AND name='fk_po_approver_to_user2')
    PRINT '<<< CREATED INDEX po_approver.fk_po_approver_to_user2 >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX po_approver.fk_po_approver_to_user2 >>>'
go

/* 
 * INDEX: fk_po_attach_to_purchase_order 
 */

CREATE INDEX fk_po_attach_to_purchase_order ON po_attach(po_no)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('po_attach') AND name='fk_po_attach_to_purchase_order')
    PRINT '<<< CREATED INDEX po_attach.fk_po_attach_to_purchase_order >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX po_attach.fk_po_attach_to_purchase_order >>>'
go

/* 
 * INDEX: fk_po_history_to_purchase_order 
 */

CREATE INDEX fk_po_history_to_purchase_order ON po_history(po_no)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('po_history') AND name='fk_po_history_to_purchase_order')
    PRINT '<<< CREATED INDEX po_history.fk_po_history_to_purchase_order >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX po_history.fk_po_history_to_purchase_order >>>'
go

/* 
 * INDEX: fk_po_item_to_purchase_order 
 */

CREATE INDEX fk_po_item_to_purchase_order ON po_item(po_no)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('po_item') AND name='fk_po_item_to_purchase_order')
    PRINT '<<< CREATED INDEX po_item.fk_po_item_to_purchase_order >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX po_item.fk_po_item_to_purchase_order >>>'
go

/* 
 * INDEX: fk_po_item_to_supplier_item 
 */

CREATE INDEX fk_po_item_to_supplier_item ON po_item(item_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('po_item') AND name='fk_po_item_to_supplier_item')
    PRINT '<<< CREATED INDEX po_item.fk_po_item_to_supplier_item >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX po_item.fk_po_item_to_supplier_item >>>'
go

/* 
 * INDEX: fk_po_item_to_purchase_type 
 */

CREATE INDEX fk_po_item_to_purchase_type ON po_item(pur_type_cd)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('po_item') AND name='fk_po_item_to_purchase_type')
    PRINT '<<< CREATED INDEX po_item.fk_po_item_to_purchase_type >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX po_item.fk_po_item_to_purchase_type >>>'
go

/* 
 * INDEX: fk_po_item_to_user 
 */

CREATE INDEX fk_po_item_to_user ON po_item(buy_for_user)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('po_item') AND name='fk_po_item_to_user')
    PRINT '<<< CREATED INDEX po_item.fk_po_item_to_user >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX po_item.fk_po_item_to_user >>>'
go

/* 
 * INDEX: fk_po_item_to_supplier 
 */

CREATE INDEX fk_po_item_to_supplier ON po_item(supp_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('po_item') AND name='fk_po_item_to_supplier')
    PRINT '<<< CREATED INDEX po_item.fk_po_item_to_supplier >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX po_item.fk_po_item_to_supplier >>>'
go

/* 
 * INDEX: fk_po_item_to_pr_item 
 */

CREATE INDEX fk_po_item_to_pr_item ON po_item(pr_item_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('po_item') AND name='fk_po_item_to_pr_item')
    PRINT '<<< CREATED INDEX po_item.fk_po_item_to_pr_item >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX po_item.fk_po_item_to_pr_item >>>'
go

/* 
 * INDEX: fk_po_item_to_currency 
 */

CREATE INDEX fk_po_item_to_currency ON po_item(exch_rate_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('po_item') AND name='fk_po_item_to_currency')
    PRINT '<<< CREATED INDEX po_item.fk_po_item_to_currency >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX po_item.fk_po_item_to_currency >>>'
go

/* 
 * INDEX: fk_po_item_to_department 
 */

CREATE INDEX fk_po_item_to_department ON po_item(buy_for_dep)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('po_item') AND name='fk_po_item_to_department')
    PRINT '<<< CREATED INDEX po_item.fk_po_item_to_department >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX po_item.fk_po_item_to_department >>>'
go

/* 
 * INDEX: FK_po_item_to_proj_cd 
 */

CREATE INDEX FK_po_item_to_proj_cd ON po_item(proj_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('po_item') AND name='FK_po_item_to_proj_cd')
    PRINT '<<< CREATED INDEX po_item.FK_po_item_to_proj_cd >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX po_item.FK_po_item_to_proj_cd >>>'
go

/* 
 * INDEX: fk_po_item_attach_to_po_item 
 */

CREATE INDEX fk_po_item_attach_to_po_item ON po_item_attach(po_item_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('po_item_attach') AND name='fk_po_item_attach_to_po_item')
    PRINT '<<< CREATED INDEX po_item_attach.fk_po_item_attach_to_po_item >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX po_item_attach.fk_po_item_attach_to_po_item >>>'
go

/* 
 * INDEX: fk_po_item_cancel_po_item 
 */

CREATE INDEX fk_po_item_cancel_po_item ON po_item_cancel(po_item_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('po_item_cancel') AND name='fk_po_item_cancel_po_item')
    PRINT '<<< CREATED INDEX po_item_cancel.fk_po_item_cancel_po_item >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX po_item_cancel.fk_po_item_cancel_po_item >>>'
go

/* 
 * INDEX: fk_po_item_cancel_to_user 
 */

CREATE INDEX fk_po_item_cancel_to_user ON po_item_cancel(cancel_user)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('po_item_cancel') AND name='fk_po_item_cancel_to_user')
    PRINT '<<< CREATED INDEX po_item_cancel.fk_po_item_cancel_to_user >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX po_item_cancel.fk_po_item_cancel_to_user >>>'
go

/* 
 * INDEX: fk_po_item_history_po_history 
 */

CREATE INDEX fk_po_item_history_po_history ON po_item_history(po_his_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('po_item_history') AND name='fk_po_item_history_po_history')
    PRINT '<<< CREATED INDEX po_item_history.fk_po_item_history_po_history >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX po_item_history.fk_po_item_history_po_history >>>'
go

/* 
 * INDEX: fk_po_item_history_to_supplier_item 
 */

CREATE INDEX fk_po_item_history_to_supplier_item ON po_item_history(item_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('po_item_history') AND name='fk_po_item_history_to_supplier_item')
    PRINT '<<< CREATED INDEX po_item_history.fk_po_item_history_to_supplier_item >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX po_item_history.fk_po_item_history_to_supplier_item >>>'
go

/* 
 * INDEX: fk_po_item_history_purchase_type 
 */

CREATE INDEX fk_po_item_history_purchase_type ON po_item_history(pur_type_cd)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('po_item_history') AND name='fk_po_item_history_purchase_type')
    PRINT '<<< CREATED INDEX po_item_history.fk_po_item_history_purchase_type >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX po_item_history.fk_po_item_history_purchase_type >>>'
go

/* 
 * INDEX: fk_po_item_history_to_supplier 
 */

CREATE INDEX fk_po_item_history_to_supplier ON po_item_history(supp_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('po_item_history') AND name='fk_po_item_history_to_supplier')
    PRINT '<<< CREATED INDEX po_item_history.fk_po_item_history_to_supplier >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX po_item_history.fk_po_item_history_to_supplier >>>'
go

/* 
 * INDEX: fk_po_item_history_to_currency 
 */

CREATE INDEX fk_po_item_history_to_currency ON po_item_history(exch_rate_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('po_item_history') AND name='fk_po_item_history_to_currency')
    PRINT '<<< CREATED INDEX po_item_history.fk_po_item_history_to_currency >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX po_item_history.fk_po_item_history_to_currency >>>'
go

/* 
 * INDEX: fk_po_item_history_to_user 
 */

CREATE INDEX fk_po_item_history_to_user ON po_item_history(buy_for_user)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('po_item_history') AND name='fk_po_item_history_to_user')
    PRINT '<<< CREATED INDEX po_item_history.fk_po_item_history_to_user >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX po_item_history.fk_po_item_history_to_user >>>'
go

/* 
 * INDEX: fk_po_item_history_to_department 
 */

CREATE INDEX fk_po_item_history_to_department ON po_item_history(buy_for_dep)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('po_item_history') AND name='fk_po_item_history_to_department')
    PRINT '<<< CREATED INDEX po_item_history.fk_po_item_history_to_department >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX po_item_history.fk_po_item_history_to_department >>>'
go

/* 
 * INDEX: FK_po_item_history_to_proj_cd 
 */

CREATE INDEX FK_po_item_history_to_proj_cd ON po_item_history(proj_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('po_item_history') AND name='FK_po_item_history_to_proj_cd')
    PRINT '<<< CREATED INDEX po_item_history.FK_po_item_history_to_proj_cd >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX po_item_history.FK_po_item_history_to_proj_cd >>>'
go

/* 
 * INDEX: fk_po_item_receipt_to_po_item 
 */

CREATE INDEX fk_po_item_receipt_to_po_item ON po_item_receipt(po_item_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('po_item_receipt') AND name='fk_po_item_receipt_to_po_item')
    PRINT '<<< CREATED INDEX po_item_receipt.fk_po_item_receipt_to_po_item >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX po_item_receipt.fk_po_item_receipt_to_po_item >>>'
go

/* 
 * INDEX: fk_po_item_receipt_to_user 
 */

CREATE INDEX fk_po_item_receipt_to_user ON po_item_receipt(receiver1)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('po_item_receipt') AND name='fk_po_item_receipt_to_user')
    PRINT '<<< CREATED INDEX po_item_receipt.fk_po_item_receipt_to_user >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX po_item_receipt.fk_po_item_receipt_to_user >>>'
go

/* 
 * INDEX: fk_po_item_receipt_to_user2 
 */

CREATE INDEX fk_po_item_receipt_to_user2 ON po_item_receipt(receiver2)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('po_item_receipt') AND name='fk_po_item_receipt_to_user2')
    PRINT '<<< CREATED INDEX po_item_receipt.fk_po_item_receipt_to_user2 >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX po_item_receipt.fk_po_item_receipt_to_user2 >>>'
go

/* 
 * INDEX: fk_po_item_recharge_to_po_item 
 */

CREATE INDEX fk_po_item_recharge_to_po_item ON po_item_recharge(po_item_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('po_item_recharge') AND name='fk_po_item_recharge_to_po_item')
    PRINT '<<< CREATED INDEX po_item_recharge.fk_po_item_recharge_to_po_item >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX po_item_recharge.fk_po_item_recharge_to_po_item >>>'
go

/* 
 * INDEX: fk_po_item_recharge_to_user 
 */

CREATE INDEX fk_po_item_recharge_to_user ON po_item_recharge(person_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('po_item_recharge') AND name='fk_po_item_recharge_to_user')
    PRINT '<<< CREATED INDEX po_item_recharge.fk_po_item_recharge_to_user >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX po_item_recharge.fk_po_item_recharge_to_user >>>'
go

/* 
 * INDEX: fk_po_item_recharge_to_customer 
 */

CREATE INDEX fk_po_item_recharge_to_customer ON po_item_recharge(cust_cd)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('po_item_recharge') AND name='fk_po_item_recharge_to_customer')
    PRINT '<<< CREATED INDEX po_item_recharge.fk_po_item_recharge_to_customer >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX po_item_recharge.fk_po_item_recharge_to_customer >>>'
go

/* 
 * INDEX: fk_po_item_recharge_to_department 
 */

CREATE INDEX fk_po_item_recharge_to_department ON po_item_recharge(person_dep_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('po_item_recharge') AND name='fk_po_item_recharge_to_department')
    PRINT '<<< CREATED INDEX po_item_recharge.fk_po_item_recharge_to_department >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX po_item_recharge.fk_po_item_recharge_to_department >>>'
go

/* 
 * INDEX: fk_pr_approver_to_user 
 */

CREATE INDEX fk_pr_approver_to_user ON pr_approver(approver_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('pr_approver') AND name='fk_pr_approver_to_user')
    PRINT '<<< CREATED INDEX pr_approver.fk_pr_approver_to_user >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX pr_approver.fk_pr_approver_to_user >>>'
go

/* 
 * INDEX: fk_pr_approver_to_user2 
 */

CREATE INDEX fk_pr_approver_to_user2 ON pr_approver(actual_approver)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('pr_approver') AND name='fk_pr_approver_to_user2')
    PRINT '<<< CREATED INDEX pr_approver.fk_pr_approver_to_user2 >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX pr_approver.fk_pr_approver_to_user2 >>>'
go

/* 
 * INDEX: fk_pr_attach_to_purchase_request 
 */

CREATE INDEX fk_pr_attach_to_purchase_request ON pr_attach(pr_no)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('pr_attach') AND name='fk_pr_attach_to_purchase_request')
    PRINT '<<< CREATED INDEX pr_attach.fk_pr_attach_to_purchase_request >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX pr_attach.fk_pr_attach_to_purchase_request >>>'
go

/* 
 * INDEX: uk_pr_authorized_purchaser 
 */

CREATE UNIQUE INDEX uk_pr_authorized_purchaser ON pr_authorized_purchaser(pr_no, purchaser)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('pr_authorized_purchaser') AND name='uk_pr_authorized_purchaser')
    PRINT '<<< CREATED INDEX pr_authorized_purchaser.uk_pr_authorized_purchaser >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX pr_authorized_purchaser.uk_pr_authorized_purchaser >>>'
go

/* 
 * INDEX: fk_pr_authorized_purchaser_to_user 
 */

CREATE INDEX fk_pr_authorized_purchaser_to_user ON pr_authorized_purchaser(purchaser)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('pr_authorized_purchaser') AND name='fk_pr_authorized_purchaser_to_user')
    PRINT '<<< CREATED INDEX pr_authorized_purchaser.fk_pr_authorized_purchaser_to_user >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX pr_authorized_purchaser.fk_pr_authorized_purchaser_to_user >>>'
go

/* 
 * INDEX: fk_pr_history_to_purchase_request 
 */

CREATE INDEX fk_pr_history_to_purchase_request ON pr_history(pr_no)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('pr_history') AND name='fk_pr_history_to_purchase_request')
    PRINT '<<< CREATED INDEX pr_history.fk_pr_history_to_purchase_request >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX pr_history.fk_pr_history_to_purchase_request >>>'
go

/* 
 * INDEX: fk_pr_item_to_purchase_request 
 */

CREATE INDEX fk_pr_item_to_purchase_request ON pr_item(pr_no)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('pr_item') AND name='fk_pr_item_to_purchase_request')
    PRINT '<<< CREATED INDEX pr_item.fk_pr_item_to_purchase_request >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX pr_item.fk_pr_item_to_purchase_request >>>'
go

/* 
 * INDEX: fk_pr_item_to_supplier_item 
 */

CREATE INDEX fk_pr_item_to_supplier_item ON pr_item(item_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('pr_item') AND name='fk_pr_item_to_supplier_item')
    PRINT '<<< CREATED INDEX pr_item.fk_pr_item_to_supplier_item >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX pr_item.fk_pr_item_to_supplier_item >>>'
go

/* 
 * INDEX: fk_pr_item_to_department 
 */

CREATE INDEX fk_pr_item_to_department ON pr_item(buy_for_dep)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('pr_item') AND name='fk_pr_item_to_department')
    PRINT '<<< CREATED INDEX pr_item.fk_pr_item_to_department >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX pr_item.fk_pr_item_to_department >>>'
go

/* 
 * INDEX: fk_pr_item_to_user 
 */

CREATE INDEX fk_pr_item_to_user ON pr_item(buy_for_user)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('pr_item') AND name='fk_pr_item_to_user')
    PRINT '<<< CREATED INDEX pr_item.fk_pr_item_to_user >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX pr_item.fk_pr_item_to_user >>>'
go

/* 
 * INDEX: fk_pr_item_to_supplier 
 */

CREATE INDEX fk_pr_item_to_supplier ON pr_item(supp_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('pr_item') AND name='fk_pr_item_to_supplier')
    PRINT '<<< CREATED INDEX pr_item.fk_pr_item_to_supplier >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX pr_item.fk_pr_item_to_supplier >>>'
go

/* 
 * INDEX: fk_pr_item_to_exchange_rate 
 */

CREATE INDEX fk_pr_item_to_exchange_rate ON pr_item(exch_rate_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('pr_item') AND name='fk_pr_item_to_exchange_rate')
    PRINT '<<< CREATED INDEX pr_item.fk_pr_item_to_exchange_rate >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX pr_item.fk_pr_item_to_exchange_rate >>>'
go

/* 
 * INDEX: fk_pr_item_to_po_item 
 */

CREATE INDEX fk_pr_item_to_po_item ON pr_item(po_item_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('pr_item') AND name='fk_pr_item_to_po_item')
    PRINT '<<< CREATED INDEX pr_item.fk_pr_item_to_po_item >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX pr_item.fk_pr_item_to_po_item >>>'
go

/* 
 * INDEX: FK_pr_item_to_proj_cd 
 */

CREATE INDEX FK_pr_item_to_proj_cd ON pr_item(proj_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('pr_item') AND name='FK_pr_item_to_proj_cd')
    PRINT '<<< CREATED INDEX pr_item.FK_pr_item_to_proj_cd >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX pr_item.FK_pr_item_to_proj_cd >>>'
go

/* 
 * INDEX: fk_pr_item_attach_to_pr_item 
 */

CREATE INDEX fk_pr_item_attach_to_pr_item ON pr_item_attach(pr_item_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('pr_item_attach') AND name='fk_pr_item_attach_to_pr_item')
    PRINT '<<< CREATED INDEX pr_item_attach.fk_pr_item_attach_to_pr_item >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX pr_item_attach.fk_pr_item_attach_to_pr_item >>>'
go

/* 
 * INDEX: fk_pr_item_history_to_pr_history 
 */

CREATE INDEX fk_pr_item_history_to_pr_history ON pr_item_history(pr_hist_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('pr_item_history') AND name='fk_pr_item_history_to_pr_history')
    PRINT '<<< CREATED INDEX pr_item_history.fk_pr_item_history_to_pr_history >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX pr_item_history.fk_pr_item_history_to_pr_history >>>'
go

/* 
 * INDEX: fk_pr_item_history_to_supplier_item 
 */

CREATE INDEX fk_pr_item_history_to_supplier_item ON pr_item_history(item_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('pr_item_history') AND name='fk_pr_item_history_to_supplier_item')
    PRINT '<<< CREATED INDEX pr_item_history.fk_pr_item_history_to_supplier_item >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX pr_item_history.fk_pr_item_history_to_supplier_item >>>'
go

/* 
 * INDEX: fk_pr_item_history_to_supplier 
 */

CREATE INDEX fk_pr_item_history_to_supplier ON pr_item_history(supp_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('pr_item_history') AND name='fk_pr_item_history_to_supplier')
    PRINT '<<< CREATED INDEX pr_item_history.fk_pr_item_history_to_supplier >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX pr_item_history.fk_pr_item_history_to_supplier >>>'
go

/* 
 * INDEX: fk_pr_item_history_to_user 
 */

CREATE INDEX fk_pr_item_history_to_user ON pr_item_history(buy_for_user)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('pr_item_history') AND name='fk_pr_item_history_to_user')
    PRINT '<<< CREATED INDEX pr_item_history.fk_pr_item_history_to_user >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX pr_item_history.fk_pr_item_history_to_user >>>'
go

/* 
 * INDEX: fk_pr_item_history_to_department 
 */

CREATE INDEX fk_pr_item_history_to_department ON pr_item_history(buy_for_dep)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('pr_item_history') AND name='fk_pr_item_history_to_department')
    PRINT '<<< CREATED INDEX pr_item_history.fk_pr_item_history_to_department >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX pr_item_history.fk_pr_item_history_to_department >>>'
go

/* 
 * INDEX: fk_pr_item_history_to_currency 
 */

CREATE INDEX fk_pr_item_history_to_currency ON pr_item_history(exch_rate_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('pr_item_history') AND name='fk_pr_item_history_to_currency')
    PRINT '<<< CREATED INDEX pr_item_history.fk_pr_item_history_to_currency >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX pr_item_history.fk_pr_item_history_to_currency >>>'
go

/* 
 * INDEX: FK_pr_item_history_to_proj_cd 
 */

CREATE INDEX FK_pr_item_history_to_proj_cd ON pr_item_history(proj_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('pr_item_history') AND name='FK_pr_item_history_to_proj_cd')
    PRINT '<<< CREATED INDEX pr_item_history.FK_pr_item_history_to_proj_cd >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX pr_item_history.FK_pr_item_history_to_proj_cd >>>'
go

/* 
 * INDEX: fk_pr_item_recharge_to_pr_item 
 */

CREATE INDEX fk_pr_item_recharge_to_pr_item ON pr_item_recharge(pr_item_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('pr_item_recharge') AND name='fk_pr_item_recharge_to_pr_item')
    PRINT '<<< CREATED INDEX pr_item_recharge.fk_pr_item_recharge_to_pr_item >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX pr_item_recharge.fk_pr_item_recharge_to_pr_item >>>'
go

/* 
 * INDEX: fk_pr_item_recharge_to_customer 
 */

CREATE INDEX fk_pr_item_recharge_to_customer ON pr_item_recharge(cust_cd)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('pr_item_recharge') AND name='fk_pr_item_recharge_to_customer')
    PRINT '<<< CREATED INDEX pr_item_recharge.fk_pr_item_recharge_to_customer >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX pr_item_recharge.fk_pr_item_recharge_to_customer >>>'
go

/* 
 * INDEX: fk_pr_item_recharge_to_user 
 */

CREATE INDEX fk_pr_item_recharge_to_user ON pr_item_recharge(person_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('pr_item_recharge') AND name='fk_pr_item_recharge_to_user')
    PRINT '<<< CREATED INDEX pr_item_recharge.fk_pr_item_recharge_to_user >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX pr_item_recharge.fk_pr_item_recharge_to_user >>>'
go

/* 
 * INDEX: fk_pr_item_recharge_to_department 
 */

CREATE INDEX fk_pr_item_recharge_to_department ON pr_item_recharge(person_dep_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('pr_item_recharge') AND name='fk_pr_item_recharge_to_department')
    PRINT '<<< CREATED INDEX pr_item_recharge.fk_pr_item_recharge_to_department >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX pr_item_recharge.fk_pr_item_recharge_to_department >>>'
go

/* 
 * INDEX: FK_proj_cd_to_site 
 */

CREATE INDEX FK_proj_cd_to_site ON proj_cd(proj_site)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('proj_cd') AND name='FK_proj_cd_to_site')
    PRINT '<<< CREATED INDEX proj_cd.FK_proj_cd_to_site >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX proj_cd.FK_proj_cd_to_site >>>'
go

/* 
 * INDEX: fk_province_to_country 
 */

CREATE INDEX fk_province_to_country ON province(country_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('province') AND name='fk_province_to_country')
    PRINT '<<< CREATED INDEX province.fk_province_to_country >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX province.fk_province_to_country >>>'
go

/* 
 * INDEX: fk_province_to_site 
 */

CREATE INDEX fk_province_to_site ON province(maint_site)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('province') AND name='fk_province_to_site')
    PRINT '<<< CREATED INDEX province.fk_province_to_site >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX province.fk_province_to_site >>>'
go

/* 
 * INDEX: fk_purchase_ctgy_to_site 
 */

CREATE INDEX fk_purchase_ctgy_to_site ON purchase_ctgy(pur_ctgy_site)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('purchase_ctgy') AND name='fk_purchase_ctgy_to_site')
    PRINT '<<< CREATED INDEX purchase_ctgy.fk_purchase_ctgy_to_site >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX purchase_ctgy.fk_purchase_ctgy_to_site >>>'
go

/* 
 * INDEX: fk_purchase_order_to_site 
 */

CREATE INDEX fk_purchase_order_to_site ON purchase_order(po_site)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('purchase_order') AND name='fk_purchase_order_to_site')
    PRINT '<<< CREATED INDEX purchase_order.fk_purchase_order_to_site >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX purchase_order.fk_purchase_order_to_site >>>'
go

/* 
 * INDEX: fk_purchase_order_to_purchase_subctgy 
 */

CREATE INDEX fk_purchase_order_to_purchase_subctgy ON purchase_order(pur_subctgy_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('purchase_order') AND name='fk_purchase_order_to_purchase_subctgy')
    PRINT '<<< CREATED INDEX purchase_order.fk_purchase_order_to_purchase_subctgy >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX purchase_order.fk_purchase_order_to_purchase_subctgy >>>'
go

/* 
 * INDEX: fk_purchase_order_to_currency2 
 */

CREATE INDEX fk_purchase_order_to_currency2 ON purchase_order(base_curr_cd)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('purchase_order') AND name='fk_purchase_order_to_currency2')
    PRINT '<<< CREATED INDEX purchase_order.fk_purchase_order_to_currency2 >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX purchase_order.fk_purchase_order_to_currency2 >>>'
go

/* 
 * INDEX: fk_purchase_order_to_user 
 */

CREATE INDEX fk_purchase_order_to_user ON purchase_order(create_user)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('purchase_order') AND name='fk_purchase_order_to_user')
    PRINT '<<< CREATED INDEX purchase_order.fk_purchase_order_to_user >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX purchase_order.fk_purchase_order_to_user >>>'
go

/* 
 * INDEX: fk_purchase_order_to_user2 
 */

CREATE INDEX fk_purchase_order_to_user2 ON purchase_order(purchaser)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('purchase_order') AND name='fk_purchase_order_to_user2')
    PRINT '<<< CREATED INDEX purchase_order.fk_purchase_order_to_user2 >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX purchase_order.fk_purchase_order_to_user2 >>>'
go

/* 
 * INDEX: fk_purchase_order_to_supplier 
 */

CREATE INDEX fk_purchase_order_to_supplier ON purchase_order(supp_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('purchase_order') AND name='fk_purchase_order_to_supplier')
    PRINT '<<< CREATED INDEX purchase_order.fk_purchase_order_to_supplier >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX purchase_order.fk_purchase_order_to_supplier >>>'
go

/* 
 * INDEX: fk_purchase_order_exchage_rate 
 */

CREATE INDEX fk_purchase_order_exchage_rate ON purchase_order(exch_rate_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('purchase_order') AND name='fk_purchase_order_exchage_rate')
    PRINT '<<< CREATED INDEX purchase_order.fk_purchase_order_exchage_rate >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX purchase_order.fk_purchase_order_exchage_rate >>>'
go

/* 
 * INDEX: fk_purchase_order_to_user3 
 */

CREATE INDEX fk_purchase_order_to_user3 ON purchase_order(inspector)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('purchase_order') AND name='fk_purchase_order_to_user3')
    PRINT '<<< CREATED INDEX purchase_order.fk_purchase_order_to_user3 >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX purchase_order.fk_purchase_order_to_user3 >>>'
go

/* 
 * INDEX: fk_purchase_order_to_user4 
 */

CREATE INDEX fk_purchase_order_to_user4 ON purchase_order(confirmer)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('purchase_order') AND name='fk_purchase_order_to_user4')
    PRINT '<<< CREATED INDEX purchase_order.fk_purchase_order_to_user4 >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX purchase_order.fk_purchase_order_to_user4 >>>'
go

/* 
 * INDEX: fk_purchase_request_to_purchase_subctgy 
 */

CREATE INDEX fk_purchase_request_to_purchase_subctgy ON purchase_request(pur_subctgy_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('purchase_request') AND name='fk_purchase_request_to_purchase_subctgy')
    PRINT '<<< CREATED INDEX purchase_request.fk_purchase_request_to_purchase_subctgy >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX purchase_request.fk_purchase_request_to_purchase_subctgy >>>'
go

/* 
 * INDEX: fk_purchase_request_to_department 
 */

CREATE INDEX fk_purchase_request_to_department ON purchase_request(pr_dep_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('purchase_request') AND name='fk_purchase_request_to_department')
    PRINT '<<< CREATED INDEX purchase_request.fk_purchase_request_to_department >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX purchase_request.fk_purchase_request_to_department >>>'
go

/* 
 * INDEX: fk_purchase_request_to_capex 
 */

CREATE INDEX fk_purchase_request_to_capex ON purchase_request(pr_capex_no)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('purchase_request') AND name='fk_purchase_request_to_capex')
    PRINT '<<< CREATED INDEX purchase_request.fk_purchase_request_to_capex >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX purchase_request.fk_purchase_request_to_capex >>>'
go

/* 
 * INDEX: fk_purchase_request_to_yearly_budget 
 */

CREATE INDEX fk_purchase_request_to_yearly_budget ON purchase_request(pr_budget_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('purchase_request') AND name='fk_purchase_request_to_yearly_budget')
    PRINT '<<< CREATED INDEX purchase_request.fk_purchase_request_to_yearly_budget >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX purchase_request.fk_purchase_request_to_yearly_budget >>>'
go

/* 
 * INDEX: fk_purchase_request_to_currency 
 */

CREATE INDEX fk_purchase_request_to_currency ON purchase_request(base_curr_cd)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('purchase_request') AND name='fk_purchase_request_to_currency')
    PRINT '<<< CREATED INDEX purchase_request.fk_purchase_request_to_currency >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX purchase_request.fk_purchase_request_to_currency >>>'
go

/* 
 * INDEX: fk_purchase_request_to_user 
 */

CREATE INDEX fk_purchase_request_to_user ON purchase_request(requestor)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('purchase_request') AND name='fk_purchase_request_to_user')
    PRINT '<<< CREATED INDEX purchase_request.fk_purchase_request_to_user >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX purchase_request.fk_purchase_request_to_user >>>'
go

/* 
 * INDEX: fk_purchase_request_to_user2 
 */

CREATE INDEX fk_purchase_request_to_user2 ON purchase_request(creator)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('purchase_request') AND name='fk_purchase_request_to_user2')
    PRINT '<<< CREATED INDEX purchase_request.fk_purchase_request_to_user2 >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX purchase_request.fk_purchase_request_to_user2 >>>'
go

/* 
 * INDEX: fk_purchase_request_to_user3 
 */

CREATE INDEX fk_purchase_request_to_user3 ON purchase_request(pr_purchaser)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('purchase_request') AND name='fk_purchase_request_to_user3')
    PRINT '<<< CREATED INDEX purchase_request.fk_purchase_request_to_user3 >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX purchase_request.fk_purchase_request_to_user3 >>>'
go

/* 
 * INDEX: fk_purchase_subctgy_to_purchase_ctgy 
 */

CREATE INDEX fk_purchase_subctgy_to_purchase_ctgy ON purchase_subctgy(pur_ctgy_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('purchase_subctgy') AND name='fk_purchase_subctgy_to_purchase_ctgy')
    PRINT '<<< CREATED INDEX purchase_subctgy.fk_purchase_subctgy_to_purchase_ctgy >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX purchase_subctgy.fk_purchase_subctgy_to_purchase_ctgy >>>'
go

/* 
 * INDEX: fk_purchase_subctgy_to_supplier 
 */

CREATE INDEX fk_purchase_subctgy_to_supplier ON purchase_subctgy(def_supplier)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('purchase_subctgy') AND name='fk_purchase_subctgy_to_supplier')
    PRINT '<<< CREATED INDEX purchase_subctgy.fk_purchase_subctgy_to_supplier >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX purchase_subctgy.fk_purchase_subctgy_to_supplier >>>'
go

/* 
 * INDEX: fk_purchase_subctgy_to_user 
 */

CREATE INDEX fk_purchase_subctgy_to_user ON purchase_subctgy(inspector)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('purchase_subctgy') AND name='fk_purchase_subctgy_to_user')
    PRINT '<<< CREATED INDEX purchase_subctgy.fk_purchase_subctgy_to_user >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX purchase_subctgy.fk_purchase_subctgy_to_user >>>'
go

/* 
 * INDEX: fk_purchase_type_to_site 
 */

CREATE INDEX fk_purchase_type_to_site ON purchase_type(pur_type_site)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('purchase_type') AND name='fk_purchase_type_to_site')
    PRINT '<<< CREATED INDEX purchase_type.fk_purchase_type_to_site >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX purchase_type.fk_purchase_type_to_site >>>'
go

/* 
 * INDEX: fk_role_function_to_role 
 */

CREATE INDEX fk_role_function_to_role ON role_function(role_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('role_function') AND name='fk_role_function_to_role')
    PRINT '<<< CREATED INDEX role_function.fk_role_function_to_role >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX role_function.fk_role_function_to_role >>>'
go

/* 
 * INDEX: fk_rule_to_site 
 */

CREATE INDEX fk_rule_to_site ON [rule](rule_site)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('rule') AND name='fk_rule_to_site')
    PRINT '<<< CREATED INDEX rule.fk_rule_to_site >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX rule.fk_rule_to_site >>>'
go

/* 
 * INDEX: fk_rule_consequence_to_user 
 */

CREATE INDEX fk_rule_consequence_to_user ON rule_consequence(user_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('rule_consequence') AND name='fk_rule_consequence_to_user')
    PRINT '<<< CREATED INDEX rule_consequence.fk_rule_consequence_to_user >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX rule_consequence.fk_rule_consequence_to_user >>>'
go

/* 
 * INDEX: fk_rule_consequence_to_user2 
 */

CREATE INDEX fk_rule_consequence_to_user2 ON rule_consequence(superior)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('rule_consequence') AND name='fk_rule_consequence_to_user2')
    PRINT '<<< CREATED INDEX rule_consequence.fk_rule_consequence_to_user2 >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX rule_consequence.fk_rule_consequence_to_user2 >>>'
go

/* 
 * INDEX: fk_rule_flow_to_site 
 */

CREATE INDEX fk_rule_flow_to_site ON rule_flow(flow_site)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('rule_flow') AND name='fk_rule_flow_to_site')
    PRINT '<<< CREATED INDEX rule_flow.fk_rule_flow_to_site >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX rule_flow.fk_rule_flow_to_site >>>'
go

/* 
 * INDEX: fk_rule_flow_rule_to_rule 
 */

CREATE INDEX fk_rule_flow_rule_to_rule ON rule_flow_rule(rule_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('rule_flow_rule') AND name='fk_rule_flow_rule_to_rule')
    PRINT '<<< CREATED INDEX rule_flow_rule.fk_rule_flow_rule_to_rule >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX rule_flow_rule.fk_rule_flow_rule_to_rule >>>'
go

/* 
 * INDEX: fk_site_to_user 
 */

CREATE INDEX fk_site_to_user ON site(site_mgr)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('site') AND name='fk_site_to_user')
    PRINT '<<< CREATED INDEX site.fk_site_to_user >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX site.fk_site_to_user >>>'
go

/* 
 * INDEX: fk_site_to_currency 
 */

CREATE INDEX fk_site_to_currency ON site(site_curr)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('site') AND name='fk_site_to_currency')
    PRINT '<<< CREATED INDEX site.fk_site_to_currency >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX site.fk_site_to_currency >>>'
go

/* 
 * INDEX: fk_site_to_city 
 */

CREATE INDEX fk_site_to_city ON site(site_city)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('site') AND name='fk_site_to_city')
    PRINT '<<< CREATED INDEX site.fk_site_to_city >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX site.fk_site_to_city >>>'
go

/* 
 * INDEX: fk_supplier_to_currency 
 */

CREATE INDEX fk_supplier_to_currency ON supplier(supp_curr_code)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('supplier') AND name='fk_supplier_to_currency')
    PRINT '<<< CREATED INDEX supplier.fk_supplier_to_currency >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX supplier.fk_supplier_to_currency >>>'
go

/* 
 * INDEX: fk_supplier_to_site 
 */

CREATE INDEX fk_supplier_to_site ON supplier(supp_site)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('supplier') AND name='fk_supplier_to_site')
    PRINT '<<< CREATED INDEX supplier.fk_supplier_to_site >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX supplier.fk_supplier_to_site >>>'
go

/* 
 * INDEX: fk_supplier_to_city 
 */

CREATE INDEX fk_supplier_to_city ON supplier(supp_city)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('supplier') AND name='fk_supplier_to_city')
    PRINT '<<< CREATED INDEX supplier.fk_supplier_to_city >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX supplier.fk_supplier_to_city >>>'
go

/* 
 * INDEX: fk_supplier_contract_to_supplier 
 */

CREATE INDEX fk_supplier_contract_to_supplier ON supplier_contract(supp_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('supplier_contract') AND name='fk_supplier_contract_to_supplier')
    PRINT '<<< CREATED INDEX supplier_contract.fk_supplier_contract_to_supplier >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX supplier_contract.fk_supplier_contract_to_supplier >>>'
go

/* 
 * INDEX: fk_supplier_history_to_site 
 */

CREATE INDEX fk_supplier_history_to_site ON supplier_history(supp_site)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('supplier_history') AND name='fk_supplier_history_to_site')
    PRINT '<<< CREATED INDEX supplier_history.fk_supplier_history_to_site >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX supplier_history.fk_supplier_history_to_site >>>'
go

/* 
 * INDEX: fk_supplier_history_to_currency 
 */

CREATE INDEX fk_supplier_history_to_currency ON supplier_history(supp_curr_code)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('supplier_history') AND name='fk_supplier_history_to_currency')
    PRINT '<<< CREATED INDEX supplier_history.fk_supplier_history_to_currency >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX supplier_history.fk_supplier_history_to_currency >>>'
go

/* 
 * INDEX: fk_supplier_history_to_city 
 */

CREATE INDEX fk_supplier_history_to_city ON supplier_history(supp_city)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('supplier_history') AND name='fk_supplier_history_to_city')
    PRINT '<<< CREATED INDEX supplier_history.fk_supplier_history_to_city >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX supplier_history.fk_supplier_history_to_city >>>'
go

/* 
 * INDEX: fk_supplier_item_purchase_subctgy 
 */

CREATE INDEX fk_supplier_item_purchase_subctgy ON supplier_item(pur_subctgy_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('supplier_item') AND name='fk_supplier_item_purchase_subctgy')
    PRINT '<<< CREATED INDEX supplier_item.fk_supplier_item_purchase_subctgy >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX supplier_item.fk_supplier_item_purchase_subctgy >>>'
go

/* 
 * INDEX: fk_supplier_item_to_currency 
 */

CREATE INDEX fk_supplier_item_to_currency ON supplier_item(curr_cd)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('supplier_item') AND name='fk_supplier_item_to_currency')
    PRINT '<<< CREATED INDEX supplier_item.fk_supplier_item_to_currency >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX supplier_item.fk_supplier_item_to_currency >>>'
go

/* 
 * INDEX: fk_supplier_item_to_supplier 
 */

CREATE INDEX fk_supplier_item_to_supplier ON supplier_item(supp_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('supplier_item') AND name='fk_supplier_item_to_supplier')
    PRINT '<<< CREATED INDEX supplier_item.fk_supplier_item_to_supplier >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX supplier_item.fk_supplier_item_to_supplier >>>'
go

/* 
 * INDEX: fk_system_log_to_site 
 */

CREATE INDEX fk_system_log_to_site ON system_log(site_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('system_log') AND name='fk_system_log_to_site')
    PRINT '<<< CREATED INDEX system_log.fk_system_log_to_site >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX system_log.fk_system_log_to_site >>>'
go

/* 
 * INDEX: fk_system_log_to_user 
 */

CREATE INDEX fk_system_log_to_user ON system_log(user_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('system_log') AND name='fk_system_log_to_user')
    PRINT '<<< CREATED INDEX system_log.fk_system_log_to_user >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX system_log.fk_system_log_to_user >>>'
go

/* 
 * INDEX: fk_ta_approver_to_user 
 */

CREATE INDEX fk_ta_approver_to_user ON ta_approver(approver_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('ta_approver') AND name='fk_ta_approver_to_user')
    PRINT '<<< CREATED INDEX ta_approver.fk_ta_approver_to_user >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX ta_approver.fk_ta_approver_to_user >>>'
go

/* 
 * INDEX: fk_ta_approver_to_user2 
 */

CREATE INDEX fk_ta_approver_to_user2 ON ta_approver(actual_approver)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('ta_approver') AND name='fk_ta_approver_to_user2')
    PRINT '<<< CREATED INDEX ta_approver.fk_ta_approver_to_user2 >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX ta_approver.fk_ta_approver_to_user2 >>>'
go

/* 
 * INDEX: fk_ta_history_to_travel_application 
 */

CREATE INDEX fk_ta_history_to_travel_application ON ta_history(ta_no)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('ta_history') AND name='fk_ta_history_to_travel_application')
    PRINT '<<< CREATED INDEX ta_history.fk_ta_history_to_travel_application >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX ta_history.fk_ta_history_to_travel_application >>>'
go

/* 
 * INDEX: fk_ta_history_to_city 
 */

CREATE INDEX fk_ta_history_to_city ON ta_history(fr_city)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('ta_history') AND name='fk_ta_history_to_city')
    PRINT '<<< CREATED INDEX ta_history.fk_ta_history_to_city >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX ta_history.fk_ta_history_to_city >>>'
go

/* 
 * INDEX: fk_ta_history_to_city2 
 */

CREATE INDEX fk_ta_history_to_city2 ON ta_history(to_city)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('ta_history') AND name='fk_ta_history_to_city2')
    PRINT '<<< CREATED INDEX ta_history.fk_ta_history_to_city2 >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX ta_history.fk_ta_history_to_city2 >>>'
go

/* 
 * INDEX: fk_ta_history_to_hotel 
 */

CREATE INDEX fk_ta_history_to_hotel ON ta_history(hotel_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('ta_history') AND name='fk_ta_history_to_hotel')
    PRINT '<<< CREATED INDEX ta_history.fk_ta_history_to_hotel >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX ta_history.fk_ta_history_to_hotel >>>'
go

/* 
 * INDEX: fk_ta_history_to_hotel_room 
 */

CREATE INDEX fk_ta_history_to_hotel_room ON ta_history(room_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('ta_history') AND name='fk_ta_history_to_hotel_room')
    PRINT '<<< CREATED INDEX ta_history.fk_ta_history_to_hotel_room >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX ta_history.fk_ta_history_to_hotel_room >>>'
go

/* 
 * INDEX: fk_travel_application_to_city 
 */

CREATE INDEX fk_travel_application_to_city ON travel_application(fr_city)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('travel_application') AND name='fk_travel_application_to_city')
    PRINT '<<< CREATED INDEX travel_application.fk_travel_application_to_city >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX travel_application.fk_travel_application_to_city >>>'
go

/* 
 * INDEX: fk_travel_application_to_city2 
 */

CREATE INDEX fk_travel_application_to_city2 ON travel_application(to_city)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('travel_application') AND name='fk_travel_application_to_city2')
    PRINT '<<< CREATED INDEX travel_application.fk_travel_application_to_city2 >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX travel_application.fk_travel_application_to_city2 >>>'
go

/* 
 * INDEX: fk_travel_application_to_hotel 
 */

CREATE INDEX fk_travel_application_to_hotel ON travel_application(hotel_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('travel_application') AND name='fk_travel_application_to_hotel')
    PRINT '<<< CREATED INDEX travel_application.fk_travel_application_to_hotel >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX travel_application.fk_travel_application_to_hotel >>>'
go

/* 
 * INDEX: fk_travel_application_to_hotel_room 
 */

CREATE INDEX fk_travel_application_to_hotel_room ON travel_application(room_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('travel_application') AND name='fk_travel_application_to_hotel_room')
    PRINT '<<< CREATED INDEX travel_application.fk_travel_application_to_hotel_room >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX travel_application.fk_travel_application_to_hotel_room >>>'
go

/* 
 * INDEX: fk_travel_application_to_user 
 */

CREATE INDEX fk_travel_application_to_user ON travel_application(requestor)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('travel_application') AND name='fk_travel_application_to_user')
    PRINT '<<< CREATED INDEX travel_application.fk_travel_application_to_user >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX travel_application.fk_travel_application_to_user >>>'
go

/* 
 * INDEX: fk_travel_application_to_user2 
 */

CREATE INDEX fk_travel_application_to_user2 ON travel_application(booker)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('travel_application') AND name='fk_travel_application_to_user2')
    PRINT '<<< CREATED INDEX travel_application.fk_travel_application_to_user2 >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX travel_application.fk_travel_application_to_user2 >>>'
go

/* 
 * INDEX: fk_travel_application_to_user3 
 */

CREATE INDEX fk_travel_application_to_user3 ON travel_application(creator)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('travel_application') AND name='fk_travel_application_to_user3')
    PRINT '<<< CREATED INDEX travel_application.fk_travel_application_to_user3 >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX travel_application.fk_travel_application_to_user3 >>>'
go

/* 
 * INDEX: fk_travel_group_det_to_expense_subctgy 
 */

CREATE INDEX fk_travel_group_det_to_expense_subctgy ON travel_group_det(exp_subctgy_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('travel_group_det') AND name='fk_travel_group_det_to_expense_subctgy')
    PRINT '<<< CREATED INDEX travel_group_det.fk_travel_group_det_to_expense_subctgy >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX travel_group_det.fk_travel_group_det_to_expense_subctgy >>>'
go

/* 
 * INDEX: fk_travel_group_mst_to_site 
 */

CREATE INDEX fk_travel_group_mst_to_site ON travel_group_mst(tvl_grp_site)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('travel_group_mst') AND name='fk_travel_group_mst_to_site')
    PRINT '<<< CREATED INDEX travel_group_mst.fk_travel_group_mst_to_site >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX travel_group_mst.fk_travel_group_mst_to_site >>>'
go

/* 
 * INDEX: fk_user_department_to_department 
 */

CREATE INDEX fk_user_department_to_department ON user_department(dep_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('user_department') AND name='fk_user_department_to_department')
    PRINT '<<< CREATED INDEX user_department.fk_user_department_to_department >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX user_department.fk_user_department_to_department >>>'
go

/* 
 * INDEX: ux_user_role 
 */

CREATE UNIQUE INDEX ux_user_role ON user_role(user_id, role_id, granted_dep, granted_site)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('user_role') AND name='ux_user_role')
    PRINT '<<< CREATED INDEX user_role.ux_user_role >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX user_role.ux_user_role >>>'
go

/* 
 * INDEX: fk_user_role_to_role 
 */

CREATE INDEX fk_user_role_to_role ON user_role(role_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('user_role') AND name='fk_user_role_to_role')
    PRINT '<<< CREATED INDEX user_role.fk_user_role_to_role >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX user_role.fk_user_role_to_role >>>'
go

/* 
 * INDEX: fk_user_role_to_department 
 */

CREATE INDEX fk_user_role_to_department ON user_role(granted_dep)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('user_role') AND name='fk_user_role_to_department')
    PRINT '<<< CREATED INDEX user_role.fk_user_role_to_department >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX user_role.fk_user_role_to_department >>>'
go

/* 
 * INDEX: fk_user_role_to_site 
 */

CREATE INDEX fk_user_role_to_site ON user_role(granted_site)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('user_role') AND name='fk_user_role_to_site')
    PRINT '<<< CREATED INDEX user_role.fk_user_role_to_site >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX user_role.fk_user_role_to_site >>>'
go

/* 
 * INDEX: fk_user_site_to_site 
 */

CREATE INDEX fk_user_site_to_site ON user_site(site_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('user_site') AND name='fk_user_site_to_site')
    PRINT '<<< CREATED INDEX user_site.fk_user_site_to_site >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX user_site.fk_user_site_to_site >>>'
go

/* 
 * INDEX: fk_user_site_to_travel_group_mst 
 */

CREATE INDEX fk_user_site_to_travel_group_mst ON user_site(tvl_grp_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('user_site') AND name='fk_user_site_to_travel_group_mst')
    PRINT '<<< CREATED INDEX user_site.fk_user_site_to_travel_group_mst >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX user_site.fk_user_site_to_travel_group_mst >>>'
go

/* 
 * INDEX: uk_yearly_bgt_dep_history 
 */

CREATE UNIQUE INDEX uk_yearly_bgt_dep_history ON yearly_bgt_dep_history(budget_hist_id, budget_dep_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('yearly_bgt_dep_history') AND name='uk_yearly_bgt_dep_history')
    PRINT '<<< CREATED INDEX yearly_bgt_dep_history.uk_yearly_bgt_dep_history >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX yearly_bgt_dep_history.uk_yearly_bgt_dep_history >>>'
go

/* 
 * INDEX: fk_yearly_bgt_dep_history_to_department 
 */

CREATE INDEX fk_yearly_bgt_dep_history_to_department ON yearly_bgt_dep_history(budget_dep_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('yearly_bgt_dep_history') AND name='fk_yearly_bgt_dep_history_to_department')
    PRINT '<<< CREATED INDEX yearly_bgt_dep_history.fk_yearly_bgt_dep_history_to_department >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX yearly_bgt_dep_history.fk_yearly_bgt_dep_history_to_department >>>'
go

/* 
 * INDEX: uk_yearly_budget 
 */

CREATE UNIQUE INDEX uk_yearly_budget ON yearly_budget(budget_site, budget_cd)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('yearly_budget') AND name='uk_yearly_budget')
    PRINT '<<< CREATED INDEX yearly_budget.uk_yearly_budget >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX yearly_budget.uk_yearly_budget >>>'
go

/* 
 * INDEX: fk_yearly_budget_to_site 
 */

CREATE INDEX fk_yearly_budget_to_site ON yearly_budget(budget_site)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('yearly_budget') AND name='fk_yearly_budget_to_site')
    PRINT '<<< CREATED INDEX yearly_budget.fk_yearly_budget_to_site >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX yearly_budget.fk_yearly_budget_to_site >>>'
go

/* 
 * INDEX: fk_yearly_budget_to_purchase_ctgy 
 */

CREATE INDEX fk_yearly_budget_to_purchase_ctgy ON yearly_budget(pur_ctgy_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('yearly_budget') AND name='fk_yearly_budget_to_purchase_ctgy')
    PRINT '<<< CREATED INDEX yearly_budget.fk_yearly_budget_to_purchase_ctgy >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX yearly_budget.fk_yearly_budget_to_purchase_ctgy >>>'
go

/* 
 * INDEX: fk_yearly_budget_to_purchase_subctgy 
 */

CREATE INDEX fk_yearly_budget_to_purchase_subctgy ON yearly_budget(pur_subctgy_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('yearly_budget') AND name='fk_yearly_budget_to_purchase_subctgy')
    PRINT '<<< CREATED INDEX yearly_budget.fk_yearly_budget_to_purchase_subctgy >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX yearly_budget.fk_yearly_budget_to_purchase_subctgy >>>'
go

/* 
 * INDEX: fk_yearly_budget_to_user 
 */

CREATE INDEX fk_yearly_budget_to_user ON yearly_budget(create_user)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('yearly_budget') AND name='fk_yearly_budget_to_user')
    PRINT '<<< CREATED INDEX yearly_budget.fk_yearly_budget_to_user >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX yearly_budget.fk_yearly_budget_to_user >>>'
go

/* 
 * INDEX: fk_yearly_budget_to_user2 
 */

CREATE INDEX fk_yearly_budget_to_user2 ON yearly_budget(modify_user)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('yearly_budget') AND name='fk_yearly_budget_to_user2')
    PRINT '<<< CREATED INDEX yearly_budget.fk_yearly_budget_to_user2 >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX yearly_budget.fk_yearly_budget_to_user2 >>>'
go

/* 
 * INDEX: uk_yearly_budget_department 
 */

CREATE UNIQUE INDEX uk_yearly_budget_department ON yearly_budget_department(budget_id, budget_dep_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('yearly_budget_department') AND name='uk_yearly_budget_department')
    PRINT '<<< CREATED INDEX yearly_budget_department.uk_yearly_budget_department >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX yearly_budget_department.uk_yearly_budget_department >>>'
go

/* 
 * INDEX: fk_yearly_budget_department_to_department 
 */

CREATE INDEX fk_yearly_budget_department_to_department ON yearly_budget_department(budget_dep_id)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('yearly_budget_department') AND name='fk_yearly_budget_department_to_department')
    PRINT '<<< CREATED INDEX yearly_budget_department.fk_yearly_budget_department_to_department >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX yearly_budget_department.fk_yearly_budget_department_to_department >>>'
go

/* 
 * INDEX: uk_yearly_budget_history 
 */

CREATE UNIQUE INDEX uk_yearly_budget_history ON yearly_budget_history(budget_id, budget_version)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('yearly_budget_history') AND name='uk_yearly_budget_history')
    PRINT '<<< CREATED INDEX yearly_budget_history.uk_yearly_budget_history >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX yearly_budget_history.uk_yearly_budget_history >>>'
go

/* 
 * INDEX: fk_yearly_budget_history_to_user 
 */

CREATE INDEX fk_yearly_budget_history_to_user ON yearly_budget_history(create_user)
go
IF EXISTS (SELECT * FROM sysindexes WHERE id=OBJECT_ID('yearly_budget_history') AND name='fk_yearly_budget_history_to_user')
    PRINT '<<< CREATED INDEX yearly_budget_history.fk_yearly_budget_history_to_user >>>'
ELSE
    PRINT '<<< FAILED CREATING INDEX yearly_budget_history.fk_yearly_budget_history_to_user >>>'
go

