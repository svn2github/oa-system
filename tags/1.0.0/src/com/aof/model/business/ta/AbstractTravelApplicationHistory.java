/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.business.ta;

import java.io.Serializable;

/**
 * 该类代表t_ta_history表的一行记录
 */
public abstract class AbstractTravelApplicationHistory extends BaseTravelApplication implements Serializable {
    /**
     * The cached hash code value for this instance. Settting to 0 triggers re-calculation.
     */
    private int hashValue = 0;

    private Integer id;
    private TravelApplication travelApplication;

    /**
     * 缺省构造函数
     */
    public AbstractTravelApplicationHistory() {
    }

    /**
     * 给定主键的构造函数
     * 
     * @param id
     */
    public AbstractTravelApplicationHistory(Integer id) {
        this.setId(id);
    }

    /**
     * @return Returns the id.
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id to set.
     */
    public void setId(Integer id) {
        this.hashValue = 0;
        this.id = id;
    }

    /**
     * @return Returns the travelApplication.
     */
    public TravelApplication getTravelApplication() {
        return travelApplication;
    }

    /**
     * @param travelApplication The travelApplication to set.
     */
    public void setTravelApplication(TravelApplication travelApplication) {
        this.travelApplication = travelApplication;
    }

    /**
     * Implementation of the equals comparison on the basis of equality of the
     * primary key values.
     * 
     * @param rhs
     * @return boolean
     */
    public boolean equals(Object rhs) {
        if (rhs == null) return false;
        if (this == rhs) return true;
        if (!(rhs instanceof TravelApplicationHistory)) return false;
        TravelApplicationHistory that = (TravelApplicationHistory) rhs;
        if (this.getId() != null) return this.getId().equals(that.getId());
        return that.getId() == null;
    }

    /**
     * Implementation of the hashCode method conforming to the Bloch pattern
     * with the exception of array properties (these are very unlikely primary
     * key types).
     * 
     * @return int
     */
    public int hashCode() {
        if (this.hashValue == 0) {
            int result = 17;
            int itemIdValue = this.getId() == null ? 0 : this.getId().hashCode();
            result = result * 37 + itemIdValue;
            this.hashValue = result;
        }
        return this.hashValue;
    }
}
