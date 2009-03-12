package net.sourceforge.model.business.pr.query;

import org.apache.commons.lang.enums.Enum;

public class CapexRequestQueryOrder extends Enum{

	public static final CapexRequestQueryOrder CAPEX_ID = new CapexRequestQueryOrder("capex_id");
    public static final CapexRequestQueryOrder CAPEX_YEARLYBUDGET_ID = new CapexRequestQueryOrder("capex_yearlyBudget_id");
	public static final CapexRequestQueryOrder TYPE = new CapexRequestQueryOrder("type");
	public static final CapexRequestQueryOrder TITLE = new CapexRequestQueryOrder("title");
	public static final CapexRequestQueryOrder DESCRIPTION = new CapexRequestQueryOrder("description");
	public static final CapexRequestQueryOrder STATUS = new CapexRequestQueryOrder("status");
	public static final CapexRequestQueryOrder AMOUNT = new CapexRequestQueryOrder("amount");
	public static final CapexRequestQueryOrder REQUESTDATE = new CapexRequestQueryOrder("requestDate");
    
	protected CapexRequestQueryOrder(String value) {
		super(value);
	}
	
	public static CapexRequestQueryOrder getEnum(String value) {
        return (CapexRequestQueryOrder) getEnum(CapexRequestQueryOrder.class, value);
    }

}
