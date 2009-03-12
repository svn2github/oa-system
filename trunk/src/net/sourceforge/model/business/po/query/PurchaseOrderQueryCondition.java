package net.sourceforge.model.business.po.query;

import org.apache.commons.lang.enums.Enum;

public class PurchaseOrderQueryCondition extends Enum {

    /* id */
    public static final PurchaseOrderQueryCondition ID_EQ = new PurchaseOrderQueryCondition("id_eq");

    /* keyPropertyList */

    /* kmtoIdList */

    /* mtoIdList */
    public static final PurchaseOrderQueryCondition SITE_ID_EQ = new PurchaseOrderQueryCondition("site_id_eq");

    public static final PurchaseOrderQueryCondition SUPPLIER_ID_EQ = new PurchaseOrderQueryCondition("supplier_id_eq");

    public static final PurchaseOrderQueryCondition SUBCATEGORY_ID_EQ = new PurchaseOrderQueryCondition("subCategory_id_eq");

    public static final PurchaseOrderQueryCondition CURRENCY_CODE_EQ = new PurchaseOrderQueryCondition("currency_code_eq");

    public static final PurchaseOrderQueryCondition BASECURRENCY_CODE_EQ = new PurchaseOrderQueryCondition("baseCurrency_code_eq");

    public static final PurchaseOrderQueryCondition CREATEUSER_ID_EQ = new PurchaseOrderQueryCondition("createUser_id_eq");

    public static final PurchaseOrderQueryCondition PURCHASER_ID_EQ = new PurchaseOrderQueryCondition("purchaser_id_eq");

    /* property */
    public static final PurchaseOrderQueryCondition ERPNO_LIKE = new PurchaseOrderQueryCondition("erpNo_like");

    public static final PurchaseOrderQueryCondition TITLE_LIKE = new PurchaseOrderQueryCondition("title_like");

    public static final PurchaseOrderQueryCondition DESCRIPTION_LIKE = new PurchaseOrderQueryCondition("description_like");

    public static final PurchaseOrderQueryCondition AMOUNT_EQ = new PurchaseOrderQueryCondition("amount_eq");

    public static final PurchaseOrderQueryCondition EXCHANGERATE_EQ = new PurchaseOrderQueryCondition("exchangeRate_eq");

    public static final PurchaseOrderQueryCondition STATUS_EQ = new PurchaseOrderQueryCondition("status_eq");

    public static final PurchaseOrderQueryCondition CREATEDATE_EQ = new PurchaseOrderQueryCondition("createDate_eq");

    public static final PurchaseOrderQueryCondition EXPORTSTATUS_EQ = new PurchaseOrderQueryCondition("exportStatus_eq");

    public static final PurchaseOrderQueryCondition APPROVEREQUESTID_EQ = new PurchaseOrderQueryCondition("approveRequestId_eq");

    public static final PurchaseOrderQueryCondition SUPPLIER_NAME_LIKE = new PurchaseOrderQueryCondition("approveRequestName_like");

    public static final PurchaseOrderQueryCondition ID_LIKE = new PurchaseOrderQueryCondition("id_like");

    public static final PurchaseOrderQueryCondition STATUS_IN2 = new PurchaseOrderQueryCondition("STATUS_IN2");

    public static final PurchaseOrderQueryCondition STATUS_IN3 = new PurchaseOrderQueryCondition("STATUS_IN3");

    public static final PurchaseOrderQueryCondition CATEGORY_ID_EQ = new PurchaseOrderQueryCondition("category_id_eq");

    public static final PurchaseOrderQueryCondition AMOUNT_GE = new PurchaseOrderQueryCondition("amount_ge");

    public static final PurchaseOrderQueryCondition AMOUNT_LE = new PurchaseOrderQueryCondition("amount_le");

    public static final PurchaseOrderQueryCondition CREATEDATE_GE = new PurchaseOrderQueryCondition("createDate_ge");

    public static final PurchaseOrderQueryCondition CREATEDATE_LT = new PurchaseOrderQueryCondition("createDate_lt");

    public static final PurchaseOrderQueryCondition CONFIRMDATE_GE = new PurchaseOrderQueryCondition("confirmDate_ge");

    public static final PurchaseOrderQueryCondition CONFIRMDATE_LT = new PurchaseOrderQueryCondition("confirmDate_lt");

    protected PurchaseOrderQueryCondition(String value) {
        super(value);
    }

    public static PurchaseOrderQueryCondition getEnum(String value) {
        return (PurchaseOrderQueryCondition) getEnum(PurchaseOrderQueryCondition.class, value);
    }

}
