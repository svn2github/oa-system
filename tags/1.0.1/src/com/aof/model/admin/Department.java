/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

/*
 * Created Thu Sep 22 16:52:37 CST 2005 by MyEclipse Hibernate Tool.
 */
package com.aof.model.admin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that represents a row in the 't_department' table. This class may be
 * customized as it is never re-generated after being created.
 */
public class Department extends AbstractDepartment implements Serializable {
    /**
     * Simple constructor of TDepartment instances.
     */
    public Department() {
    }

    /**
     * Constructor of TDepartment instances given a simple primary key.
     * 
     * @param depId
     */
    public Department(java.lang.Integer id) {
        super(id);
    }

    /* Add customized code below */
    private List children;
    private String indentName;

    public List getChildren() {
        return children;
    }

    public void addChild(Department d) {
        if (children == null)
            children = new ArrayList();
        children.add(d);
    }

    public String getIndentName() {
        return indentName;
    }

    public void setIndentName(String indentName) {
        this.indentName = indentName;
    }
    
    private boolean granted=false;

	public boolean isGranted() {
		return granted;
	}

	public void setGranted(boolean granted) {
		this.granted = granted;
	}

    
}
