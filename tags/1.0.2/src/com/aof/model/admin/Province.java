/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

/*
 * Created Fri Sep 23 13:47:16 CST 2005 by MyEclipse Hibernate Tool.
 */ 
package com.aof.model.admin;

import java.io.Serializable;
import java.util.List;

/**
 * A class that represents a row in the 't_province' table. 
 * This class may be customized as it is never re-generated 
 * after being created.
 */
public class Province
    extends AbstractProvince
    implements Serializable
{
    /**
     * Simple constructor of TProvince instances.
     */
    public Province()
    {
    }

    /**
     * Constructor of TProvince instances given a simple primary key.
     * @param id
     */
    public Province(java.lang.Integer id)
    {
        super(id);
    }

    /* Add customized code below */
    private List enabledCityList;

	public List getEnabledCityList() {
		return enabledCityList;
	}

	public void setEnabledCityList(List enabledCityList) {
		this.enabledCityList = enabledCityList;
	}
    
}
