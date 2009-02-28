package com.aof.model.business.rule.query;

import org.apache.commons.lang.enums.Enum;

public class RuleQueryOrder extends Enum {

	/*id*/
	public static final RuleQueryOrder ID = new RuleQueryOrder("id");

	/*property*/
	public static final RuleQueryOrder DESCRIPTION = new RuleQueryOrder("description");
    public static final RuleQueryOrder ENABLED = new RuleQueryOrder("enabled");
    
	protected RuleQueryOrder(String value) {
		super(value);
	}
	
	public static RuleQueryOrder getEnum(String value) {
        return (RuleQueryOrder) getEnum(RuleQueryOrder.class, value);
    }

}
