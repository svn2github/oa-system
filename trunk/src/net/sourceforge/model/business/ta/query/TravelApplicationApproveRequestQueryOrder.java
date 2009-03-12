/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.model.business.ta.query;

import org.apache.commons.lang.enums.Enum;

/**
 * TravelApplicationApproveRequest查询顺序的枚举类
 * @author ych
 * @version 1.0 (Nov 18, 2005)
 */
public class TravelApplicationApproveRequestQueryOrder extends Enum{
    
	public static final TravelApplicationApproveRequestQueryOrder SEQ = new TravelApplicationApproveRequestQueryOrder("seq");
	public static final TravelApplicationApproveRequestQueryOrder STATUS = new TravelApplicationApproveRequestQueryOrder("status");
	public static final TravelApplicationApproveRequestQueryOrder APPROVEDATE = new TravelApplicationApproveRequestQueryOrder("approveDate");
	public static final TravelApplicationApproveRequestQueryOrder COMMENT = new TravelApplicationApproveRequestQueryOrder("comment");
	public static final TravelApplicationApproveRequestQueryOrder CANMODIFY = new TravelApplicationApproveRequestQueryOrder("canModify");
    public static final TravelApplicationApproveRequestQueryOrder TA_CODE = new TravelApplicationApproveRequestQueryOrder("ta_code");
    
	protected TravelApplicationApproveRequestQueryOrder(String value) {
		super(value);
	}
	
	public static TravelApplicationApproveRequestQueryOrder getEnum(String value) {
        return (TravelApplicationApproveRequestQueryOrder) getEnum(TravelApplicationApproveRequestQueryOrder.class, value);
    }

}
