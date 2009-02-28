package com.aof.model.admin.query;

import org.apache.commons.lang.enums.Enum;

public class TravelGroupDetailQueryCondition extends Enum{

	/*id*/

	/*keyPropertyList*/

	/*kmtoIdList*/
	public static final TravelGroupDetailQueryCondition EXPENSESUBCATEGORY_ID_EQ =
		 new TravelGroupDetailQueryCondition("expenseSubCategory_id_eq");
	public static final TravelGroupDetailQueryCondition TRAVELGROUP_ID_EQ =
		 new TravelGroupDetailQueryCondition("travelGroup_id_eq");

	/*mtoIdList*/

	/*property*/
	public static final TravelGroupDetailQueryCondition AMOUNTLIMIT_EQ =
		 new TravelGroupDetailQueryCondition("amountLimit_eq");
    public static final TravelGroupDetailQueryCondition EXPENSESUBCATEGORY_ISHOTEL_EQ = 
     new TravelGroupDetailQueryCondition("expenseSubCategory_ishote_eq");

	protected TravelGroupDetailQueryCondition(String value) {
		super(value);
	}
	
	public static TravelGroupDetailQueryCondition getEnum(String value) {
        return (TravelGroupDetailQueryCondition) getEnum(TravelGroupDetailQueryCondition.class, value);
    }

}
