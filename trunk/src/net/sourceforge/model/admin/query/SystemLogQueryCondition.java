package net.sourceforge.model.admin.query;

import org.apache.commons.lang.enums.Enum;

/**
 * SystemLog查询条件的枚举类
 * 
 * @author nicebean
 * @version 1.0 (2006-02-15)
 */
public class SystemLogQueryCondition extends Enum {

    public static final SystemLogQueryCondition ID_EQ = new SystemLogQueryCondition("id_eq");

    public static final SystemLogQueryCondition SITE_ID_EQ = new SystemLogQueryCondition("site_id_eq");

    public static final SystemLogQueryCondition USER_ID_EQ = new SystemLogQueryCondition("user_id_eq");
    
    public static final SystemLogQueryCondition USER_NAME_LIKE = new SystemLogQueryCondition("user_name_like");

    public static final SystemLogQueryCondition TARGET_EQ = new SystemLogQueryCondition("target_eq");
    
    public static final SystemLogQueryCondition TARGET_LIKE = new SystemLogQueryCondition("target_like");

    public static final SystemLogQueryCondition CONTENT_LIKE = new SystemLogQueryCondition("content_like");
    
    public static final SystemLogQueryCondition ACTION_TIME_GT = new SystemLogQueryCondition("action_time_gt");
    
    public static final SystemLogQueryCondition ACTION_TIME_LT = new SystemLogQueryCondition("action_time_lt");

    protected SystemLogQueryCondition(String value) {
        super(value);
    }

    public static SystemLogQueryCondition getEnum(String value) {
        return (SystemLogQueryCondition) getEnum(SystemLogQueryCondition.class, value);
    }

}
