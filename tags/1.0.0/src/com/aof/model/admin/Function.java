/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

/*
 * Created Thu Sep 22 16:44:25 CST 2005 by MyEclipse Hibernate Tool.
 */
package com.aof.model.admin;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import org.apache.struts.action.ActionMapping;

/**
 * A class that represents a row in the 't_function' table. This class may be
 * customized as it is never re-generated after being created.
 */
public class Function extends AbstractFunction implements Serializable {
	public Function() {
	}

	public Function(java.lang.Integer id) {
		super(id);
	}

	/* Add customized code below */

    /**
     * 实现ActionMapping对象间的排序规则，目前以根据path升序排
     */
    public final static Comparator ACTIONMAPPING_COMPARATOR = new Comparator() {

        public int compare(Object o1, Object o2) {
            if (!(o1 instanceof ActionMapping && o2 instanceof ActionMapping)) return 0;
            ActionMapping a1 = (ActionMapping) o1;
            ActionMapping a2 = (ActionMapping) o2;
            if (a1 == null) return -1;
            if (a2 == null) return 1;
            return a1.getPath().compareTo(a2.getPath());
        }
        
    };
    private Set actionMappings;
    private String level;

	public Set getActionMappings() {
		return actionMappings;
	}

	public void setActionMappings(Set actionMappings) {
		this.actionMappings = actionMappings;
	}
    
    public void addActionMapping(ActionMapping mapping) {
        if (actionMappings == null) actionMappings = new TreeSet(ACTIONMAPPING_COMPARATOR);
        actionMappings.add(mapping);
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

	public boolean isGlobal() {
		return hasOnlyLevel(GLOBAL);
	}

	public boolean isSite() {
		return hasOnlyLevel(SITE);
	}

	public boolean isDepartment() {
		return hasOnlyLevel(DEPARTMENT);
	}

	private boolean hasOnlyLevel(String s) {
		return this.getLevel().equals(s);
	}

	private static final String GLOBAL = "g";

	private static final String SITE = "s";

	private static final String DEPARTMENT = "d";

}
