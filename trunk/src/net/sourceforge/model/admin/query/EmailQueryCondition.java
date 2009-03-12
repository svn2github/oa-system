package net.sourceforge.model.admin.query;

import org.apache.commons.lang.enums.Enum;

/**
 * Email查询条件的枚举类
 * @author ych
 * @version 1.0 (Nov 13, 2005)
 */
public class EmailQueryCondition extends Enum{

	public static final EmailQueryCondition ID_EQ =
		 new EmailQueryCondition("id_eq");

	public static final EmailQueryCondition MAILFROM_LIKE =
		 new EmailQueryCondition("mailFrom_like");
	public static final EmailQueryCondition MAILTO_LIKE =
		 new EmailQueryCondition("mailTo_like");
	public static final EmailQueryCondition SUBJECT_LIKE =
		 new EmailQueryCondition("subject_like");
	public static final EmailQueryCondition BODY_LIKE =
		 new EmailQueryCondition("body_like");
	public static final EmailQueryCondition CREATETIME_BT =
		 new EmailQueryCondition("createTime_bt");
	public static final EmailQueryCondition SENTTIME_BT =
		 new EmailQueryCondition("sentTime_bt");
	public static final EmailQueryCondition FAILCOUNT_GE =
		 new EmailQueryCondition("failCount_gt");
	public static final EmailQueryCondition WAITTOSEND_EQ =
		 new EmailQueryCondition("waitToSend_eq");

	protected EmailQueryCondition(String value) {
		super(value);
	}
	
	public static EmailQueryCondition getEnum(String value) {
        return (EmailQueryCondition) getEnum(EmailQueryCondition.class, value);
    }

}
