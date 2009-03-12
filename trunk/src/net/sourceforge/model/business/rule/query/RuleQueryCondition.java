/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.business.rule.query;

import org.apache.commons.lang.enums.Enum;

public class RuleQueryCondition extends Enum{
    public static final RuleQueryCondition ID_EQ = new RuleQueryCondition("id_eq");
    public static final RuleQueryCondition DESCRIPTION_LIKE = new RuleQueryCondition("description_like");
    public static final RuleQueryCondition ENABLED_EQ = new RuleQueryCondition("enabled_eq");
    public static final RuleQueryCondition SITE_ID_EQ = new RuleQueryCondition("site_id_eq");
    public static final RuleQueryCondition TYPE_EQ = new RuleQueryCondition("type_eq");
    public static final RuleQueryCondition USERNAME_LIKE = new RuleQueryCondition("username_like");
    
	protected RuleQueryCondition(String value) {
		super(value);
	}
	
	public static RuleQueryCondition getEnum(String value) {
        return (RuleQueryCondition) getEnum(RuleQueryCondition.class, value);
    }

}
