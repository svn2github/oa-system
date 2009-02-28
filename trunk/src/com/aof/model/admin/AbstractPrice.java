/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

/*
 * WARNING: DO NOT EDIT THIS FILE. This is a generated file that is synchronized
 * by MyEclipse Hibernate tool integration.
 *
 * Created Fri Sep 23 14:35:36 CST 2005 by MyEclipse Hibernate Tool.
 */
package com.aof.model.admin;

import java.io.Serializable;
import java.math.BigDecimal;

import com.aof.model.metadata.EnabledDisabled;

/**
 * A class that represents a row in the t_price table. 
 * You can customize the behavior of this class by editing the class, {@link TPrice()}.
 * WARNING: DO NOT EDIT THIS FILE. This is a generated file that is synchronized
 * by MyEclipse Hibernate tool integration.
 */
public abstract class AbstractPrice 
    implements Serializable
{
    /** The cached hash code value for this instance.  Settting to 0 triggers re-calculation. */
    private int hashValue = 0;

    /** The composite primary key value. */
    private Integer id;

    /** The value of the hotel association. */
    private Hotel hotel;

    /** The value of the simple room property. */
    private String room;

    /** The value of the simple price property. */
    private BigDecimal price;

    /** The value of the simple discount property. */
    private Integer discount;

    /** The value of the simple description property. */
    private String description;
    
    private String breakfast;

    private String network;

    /** The value of the simple enabled property. */
    private EnabledDisabled enabled;

    /**
     * Simple constructor of AbstractPrice instances.
     */
    public AbstractPrice()
    {
    }

    /**
     * Constructor of AbstractPrice instances given a simple primary key.
     * @param id
     */
    public AbstractPrice(Integer id)
    {
        this.setId(id);
    }

    /**
     * Return the simple primary key value that identifies this object.
     * @return Integer
     */
    public Integer getId()
    {
        return id;
    }

    /**
     * Set the simple primary key value that identifies this object.
     * @param id
     */
    public void setId(Integer id)
    {
        this.hashValue = 0;
        this.id = id;
    }

    
    
    
	/**
	 * @return Returns the enabled.
	 */
	public EnabledDisabled getEnabled() {
		return enabled;
	}

	/**
	 * @param enabled The enabled to set.
	 */
	public void setEnabled(EnabledDisabled enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return Returns the hotel.
	 */
	public Hotel getHotel() {
		return hotel;
	}

	/**
	 * @param hotel The hotel to set.
	 */
	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	/**
     * Return the value of the room column.
     * @return String
     */
    public String getRoom()
    {
        return this.room;
    }

    /**
     * Set the value of the room column.
     * @param room
     */
    public void setRoom(String room)
    {
        this.room = room;
    }

    /**
     * Return the value of the price column.
     * @return BigDecimal
     */
    public BigDecimal getPrice()
    {
        return this.price;
    }

    /**
     * Set the value of the price column.
     * @param price
     */
    public void setPrice(BigDecimal price)
    {
        this.price = price;
    }

    /**
     * Return the value of the discount column.
     * @return Integer
     */
    public Integer getDiscount()
    {
        return this.discount;
    }

    /**
     * Set the value of the discount column.
     * @param discount
     */
    public void setDiscount(Integer discount)
    {
        this.discount = discount;
    }

    /**
     * Return the value of the description column.
     * @return String
     */
    public String getDescription()
    {
        return this.description;
    }

    /**
     * Set the value of the description column.
     * @param description
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    

    /**
     * Implementation of the equals comparison on the basis of equality of the primary key values.
     * @param rhs
     * @return boolean
     */
    public boolean equals(Object rhs)
    {
    	if (rhs == null) return false;
        if (this == rhs) return true;
        if (!(rhs instanceof Price)) return false;
        Price that = (Price) rhs;
        if (this.getId() != null) return this.getId().equals(that.getId());
        return that.getId() == null;
    }

    /**
     * Implementation of the hashCode method conforming to the Bloch pattern with
     * the exception of array properties (these are very unlikely primary key types).
     * @return int
     */
    public int hashCode()
    {
        if (this.hashValue == 0)
        {
            int result = 17;
            int priceIdValue = this.getId() == null ? 0 : this.getId().hashCode();
            result = result * 37 + priceIdValue;
            this.hashValue = result;
        }
        return this.hashValue;
    }

    public String getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(String breakfast) {
        this.breakfast = breakfast;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }
    
}
