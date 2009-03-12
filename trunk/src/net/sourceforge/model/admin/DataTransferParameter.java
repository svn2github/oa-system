/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.admin;

import java.io.Serializable;

/**
 * A class that represents a row in the 'erp_tran' table. This class may be
 * customized as it is never re-generated after being created.
 * 
 * @author nicebean
 * @version 1.0 (2005-12-14)
 */
public class DataTransferParameter extends AbstractDataTransferParameter implements Serializable {
    /**
     * Simple constructor of DataTransferParameter instances.
     */
    public DataTransferParameter() {
    }

    /**
     * Constructor of DataTransferParameter instances given a simple primary key.
     * 
     * @param supplier
     */
    public DataTransferParameter(Site site) {
        super(site);
    }
    
    private Integer interval;
    
    private Integer intervalHour;
    
    private Integer intervalType;
    
    /**
     * @return Returns the interval.
     */
    public Integer getInterval() {
        if (getIntervalMin()==null) {
            return null;
        }
        interval = getIntervalHour().intValue()==0?this.getIntervalMin():this.getIntervalHour();
        return interval;
    }

    /**
     * @param interval The interval to set.
     */
    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    
    /**
     * @return Returns the intervalType.
     */
    public Integer getIntervalType() {
        if (getIntervalMin()==null) {
            return new Integer(1);
        }
        intervalType=this.getIntervalHour().intValue()==0?new Integer(2):new Integer(1);
        return intervalType;
    }

    /**
     * @param intervalType The intervalType to set.
     */
    public void setIntervalType(Integer intervalType) {
        this.intervalType = intervalType;
    }

    
    /**
     * @param intervalHour The intervalHour to set.
     */
    public void setIntervalHour(Integer intervalHour) {
        this.intervalHour = intervalHour;
    }

    public Integer getIntervalHour() {
        if (getIntervalMin()==null) {
            return new Integer(0);
        }
        int interval= this.getIntervalMin().intValue();
        intervalHour=(interval % 60==0) ? new Integer(interval / 60) : new Integer(0);
        return intervalHour;
    }

    public boolean schemeEqual(DataTransferParameter that)
    {
        if (!this.getId().equals(that.getId())) return false;
        if (this.getIntervalMin()!=null) {
            if (!this.getIntervalMin().equals(that.getIntervalMin())) return false;
        } else {
            if (that.getIntervalMin()!=null) return false;
        }
        if (this.getStartTime()!=null) {
            if (!this.getStartTime().equals(that.getStartTime())) return false;
        } else {
            if (that.getStartTime()!=null) return false;
        }
        if (this.getTimePerDay()!=null) {
            if (!this.getTimePerDay().equals(that.getTimePerDay())) return false;
        } else {
            if (that.getTimePerDay()!=null) return false;
        }
        return true;
    }
}
