package net.sourceforge.model.business.query;

import org.apache.commons.lang.enums.Enum;

public class BaseApproveQueryCondition extends Enum {

    //public static final BaseApproveQueryCondition ID_EQ = new BaseApproveQueryCondition("id_eq");

    public static final BaseApproveQueryCondition APPROVER_ID_EQ = new BaseApproveQueryCondition("approver_id_eq");
    
    public static final BaseApproveQueryCondition SITE_ID_EQ = new BaseApproveQueryCondition("site_id_eq"); 

    public static final BaseApproveQueryCondition STATUS_EQ = new BaseApproveQueryCondition("status_eq"); 
    
    public static final BaseApproveQueryCondition YOUR_TURN_DATE_LE = new BaseApproveQueryCondition("your_turn_date_le");
    
    public static final BaseApproveQueryCondition LAST_EMAIL_DATE_LE = new BaseApproveQueryCondition("last_email_date_le");

    public static final BaseApproveQueryCondition SENT_EMAIL_TIMES_LT = new BaseApproveQueryCondition("sent_email_times_lt");
    
    public static final BaseApproveQueryCondition LAST_APPROVED_DATE_GE = new BaseApproveQueryCondition("LAST_APPROVED_DATE_GE");
    
    public static final BaseApproveQueryCondition LAST_APPROVED_DATE_LE = new BaseApproveQueryCondition("LAST_APPROVED_DATE_LE");

    //public static final BaseApproveQueryCondition SEQ_EQ = new BaseApproveQueryCondition("seq_eq");

    //public static final BaseApproveQueryCondition APPROVEDATE_EQ = new BaseApproveQueryCondition("approveDate_eq");

    //public static final BaseApproveQueryCondition COMMENT_LIKE = new BaseApproveQueryCondition("comment_like");

    //public static final BaseApproveQueryCondition CANMODIFY_EQ = new BaseApproveQueryCondition("canModify_eq");

    protected BaseApproveQueryCondition(String value) {
        super(value);
    }

}
