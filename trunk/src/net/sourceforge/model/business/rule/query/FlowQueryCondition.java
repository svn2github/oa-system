/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.business.rule.query;

import org.apache.commons.lang.enums.Enum;

public class FlowQueryCondition extends Enum{
    public static final FlowQueryCondition ID_EQ = new FlowQueryCondition("id_eq");
    public static final FlowQueryCondition DESCRIPTION_LIKE = new FlowQueryCondition("description_like");
    public static final FlowQueryCondition ENABLED_EQ = new FlowQueryCondition("enabled_eq");
    public static final FlowQueryCondition SITE_ID_EQ = new FlowQueryCondition("site_id_eq");
    public static final FlowQueryCondition TYPE_EQ = new FlowQueryCondition("type_eq");
	
	protected FlowQueryCondition(String value) {
		super(value);
	}
	
	public static FlowQueryCondition getEnum(String value) {
        return (FlowQueryCondition) getEnum(FlowQueryCondition.class, value);
    }

}
