/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

/*
 * Created Fri Sep 23 13:36:35 CST 2005 by MyEclipse Hibernate Tool.
 */ 
package com.aof.model.admin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A class that represents a row in the 't_hotel' table. 
 * This class may be customized as it is never re-generated 
 * after being created.
 */
public class Hotel
    extends AbstractHotel
    implements Serializable
{
    /**
     * Simple constructor of Hotel instances.
     */
    public Hotel()
    {
    }

    /**
     * Constructor of Hotel instances given a simple primary key.
     * @param id
     */
    public Hotel(java.lang.Integer id)
    {
        super(id);
    }

	public List getRooms() {
		return rooms;
	}

	public void setRooms(ArrayList list) {
		this.rooms=list;
		
	}
	
	private List rooms;

    /* Add customized code below */

    public void emailed(Date d) {
        this.setEmailDate(d);
        this.setEmailTimes(this.getEmailTimes()+1);
    }
}
