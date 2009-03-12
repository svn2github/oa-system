package net.sourceforge.model.business.pr.query;

import org.apache.commons.lang.enums.Enum;

public class CapexRequestQueryCondition extends Enum {

    /* id */
    public static final CapexRequestQueryCondition ID_EQ = new CapexRequestQueryCondition("id_eq");

    /* keyPropertyList */

    /* kmtoIdList */

    /* mtoIdList */
    public static final CapexRequestQueryCondition CAPEX_ID_LIKE = new CapexRequestQueryCondition("capex_id_like");

    public static final CapexRequestQueryCondition CAPEX_ID_EQ = new CapexRequestQueryCondition("capex_id_eq");

    public static final CapexRequestQueryCondition REQUESTOR_ID_EQ = new CapexRequestQueryCondition("requestor_id_eq");

    /* property */
    public static final CapexRequestQueryCondition TYPE_EQ = new CapexRequestQueryCondition("type_eq");

    public static final CapexRequestQueryCondition TITLE_LIKE = new CapexRequestQueryCondition("title_like");

    public static final CapexRequestQueryCondition DESCRIPTION_LIKE = new CapexRequestQueryCondition("description_like");

    public static final CapexRequestQueryCondition STATUS_EQ = new CapexRequestQueryCondition("status_eq");

    public static final CapexRequestQueryCondition STATUS_NE = new CapexRequestQueryCondition("status_ne");

    public static final CapexRequestQueryCondition AMOUNT_EQ = new CapexRequestQueryCondition("amount_eq");

    public static final CapexRequestQueryCondition REQUESTDATE_EQ = new CapexRequestQueryCondition("requestDate_eq");

    public static final CapexRequestQueryCondition REQUESTDATE_GE = new CapexRequestQueryCondition("requestDate_ge");
    
    public static final CapexRequestQueryCondition REQUESTDATE_LE = new CapexRequestQueryCondition("requestDate_le");    
    
    public static final CapexRequestQueryCondition REQUESTDATE_LT = new CapexRequestQueryCondition("requestDate_lt");
    
    public static final CapexRequestQueryCondition CREATEDATE_EQ = new CapexRequestQueryCondition("createDate_eq");

    public static final CapexRequestQueryCondition CREATEDATE_GE = new CapexRequestQueryCondition("createDate_ge");
    
    public static final CapexRequestQueryCondition CREATEDATE_LE = new CapexRequestQueryCondition("createDate_le");
    
    public static final CapexRequestQueryCondition CREATEDATE_LT = new CapexRequestQueryCondition("createDate_lt");
    
    public static final CapexRequestQueryCondition APPROVEDATE_EQ = new CapexRequestQueryCondition("approveDate_eq");

    public static final CapexRequestQueryCondition APPROVEDATE_GE = new CapexRequestQueryCondition("approveDate_ge");
    
    public static final CapexRequestQueryCondition APPROVEDATE_LE = new CapexRequestQueryCondition("approveDate_le");
    
    public static final CapexRequestQueryCondition APPROVEDATE_LT = new CapexRequestQueryCondition("approveDate_lt");
    
    public static final CapexRequestQueryCondition APPROVEREQUESTID_EQ = new CapexRequestQueryCondition("approveRequestId_eq");

    public static final CapexRequestQueryCondition CAPEX_SITE_ID_EQ = new CapexRequestQueryCondition("capex_site_id_eq");

    public static final CapexRequestQueryCondition IS_LAST_APPROVED = new CapexRequestQueryCondition("is_last_approved");
    
    public static final CapexRequestQueryCondition CAPEX_DEPARTMENT_ID_EQ = new CapexRequestQueryCondition("capex_department_id_eq");
    
    public static final CapexRequestQueryCondition CAPEX_DEPARTMENT_ID_IN = new CapexRequestQueryCondition("capex_department_id_in");

    public static final CapexRequestQueryCondition CAPEX_YEAR_EQ = new CapexRequestQueryCondition("capex_yearlyBudget_year_eq");
    
    public static final CapexRequestQueryCondition AMOUNT_GE = new CapexRequestQueryCondition("amount_ge");

    public static final CapexRequestQueryCondition AMOUNT_LE = new CapexRequestQueryCondition("amount_le");

    public static final CapexRequestQueryCondition CAPEX_REMAINAMOUNT_GE = new CapexRequestQueryCondition("capex_remainamount_ge");

    public static final CapexRequestQueryCondition CAPEX_REMAINAMOUNT_LE = new CapexRequestQueryCondition("capex_remainamount_le");

    protected CapexRequestQueryCondition(String value) {
        super(value);
    }

    public static CapexRequestQueryCondition getEnum(String value) {
        return (CapexRequestQueryCondition) getEnum(CapexRequestQueryCondition.class, value);
    }

}
