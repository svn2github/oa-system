package com.aof.model.business.rule.query;

import com.aof.model.metadata.RuleType;



public class ApproverQueryCondition{
	private Integer siteId;
	private Integer departmentId;
	private RuleType ruleType;
    private String name;
	
	public Integer getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
	public RuleType getRuleType() {
		return ruleType;
	}
	public void setRuleType(RuleType ruleType) {
		this.ruleType = ruleType;
	}
	public Integer getSiteId() {
		return siteId;
	}
	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
	
	

}
