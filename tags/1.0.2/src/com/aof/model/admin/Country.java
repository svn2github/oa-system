/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

/*
 * Created Fri Sep 23 10:14:38 CST 2005 by MyEclipse Hibernate Tool.
 */ 
package com.aof.model.admin;

import java.io.Serializable;
import java.util.List;

/**
 * A class that represents a row in the 't_country' table. 
 * This class may be customized as it is never re-generated 
 * after being created.
 */
public class Country
    extends AbstractCountry
    implements Serializable
{
	
	private List enabledProvinceList;
	
	private List enabledCityList;
	
	
    /**
     * Simple constructor of TCountry instances.
     */
    public Country()
    {
    }

    /**
     * Constructor of TCountry instances given a simple primary key.
     * @param countryId
     */
    public Country(java.lang.Integer id)
    {
        super(id);
    }


	public List getEnabledProvinceList() {
		return enabledProvinceList;
	}

	public void setEnabledProvinceList(List enabledProvinceList) {
		this.enabledProvinceList = enabledProvinceList;
	}
    
	

	/**
	 * @return Returns the enabledCityList.
	 */
	public List getEnabledCityList() {
		return enabledCityList;
	}

	/**
	 * @param enabledCityList The enabledCityList to set.
	 */
	public void setEnabledCityList(List enabledCityList) {
		this.enabledCityList = enabledCityList;
	}
	
	

}
