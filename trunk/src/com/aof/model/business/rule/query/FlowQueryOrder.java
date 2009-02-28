package com.aof.model.business.rule.query;

import org.apache.commons.lang.enums.Enum;

public class FlowQueryOrder extends Enum {

	/*id*/
	public static final FlowQueryOrder ID = new FlowQueryOrder("id");

	/*property*/
	public static final FlowQueryOrder DESCRIPTION = new FlowQueryOrder("description");
    public static final FlowQueryOrder ENABLED = new FlowQueryOrder("enabled");
    
	protected FlowQueryOrder(String value) {
		super(value);
	}
	
	public static FlowQueryOrder getEnum(String value) {
        return (FlowQueryOrder) getEnum(FlowQueryOrder.class, value);
    }

}
