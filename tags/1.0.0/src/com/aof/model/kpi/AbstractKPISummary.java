package com.aof.model.kpi;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public abstract class AbstractKPISummary implements Serializable {

    private int hashValue = 0;
    
    /** identifier field */
    private Integer id;
    
    /** nullable persistent field */
    private Date summaryDate;
    
    /** nullable persistent field */
    private int loginUserCount;
    
    /** nullable persistent field */
    private int closedTANumToday;
    
    /** nullable persistent field */
    private BigDecimal avgTAClosedDays;
    
    /** nullable persistent field */
    private int createdTANumToday;
    
    /** nullable persistent field */
    private int closedCapexNumToday;
    
    /** nullable persistent field */
    private BigDecimal avgCapexClosedDays;
    
    /** nullable persistent field */
    private int createdCapexNumToday;
    
    /** nullable persistent field */
    private int closedPRNumToday;
    
    /** nullable persistent field */
    private BigDecimal avgPRClosedDays;
    
    /** nullable persistent field */
    private int createdPRNumToday;
    
    /** nullable persistent field */
    private int closedExpenseNumToday;
    
    /** nullable persistent field */
    private BigDecimal avgExpenseClosedDays;
    
    /** nullable persistent field */
    private int createdExpenseNumToday;

    
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
        this.id = id;
    }


    /**
     * @return Returns the loginUserCount.
     */
    public int getLoginUserCount() {
        return loginUserCount;
    }


    /**
     * @param loginUserCount The loginUserCount to set.
     */
    public void setLoginUserCount(int loginUserCount) {
        this.loginUserCount = loginUserCount;
    }
    

    /**
     * @return Returns the avgCapexClosedDays.
     */
    public BigDecimal getAvgCapexClosedDays() {
        return avgCapexClosedDays;
    }


    /**
     * @param avgCapexClosedDays The avgCapexClosedDays to set.
     */
    public void setAvgCapexClosedDays(BigDecimal avgCapexClosedDays) {
        this.avgCapexClosedDays = avgCapexClosedDays;
    }


    /**
     * @return Returns the avgExpenseClosedDays.
     */
    public BigDecimal getAvgExpenseClosedDays() {
        return avgExpenseClosedDays;
    }


    /**
     * @param avgExpenseClosedDays The avgExpenseClosedDays to set.
     */
    public void setAvgExpenseClosedDays(BigDecimal avgExpenseClosedDays) {
        this.avgExpenseClosedDays = avgExpenseClosedDays;
    }


    /**
     * @return Returns the avgPRClosedDays.
     */
    public BigDecimal getAvgPRClosedDays() {
        return avgPRClosedDays;
    }


    /**
     * @param avgPRClosedDays The avgPRClosedDays to set.
     */
    public void setAvgPRClosedDays(BigDecimal avgPRClosedDays) {
        this.avgPRClosedDays = avgPRClosedDays;
    }


    /**
     * @return Returns the avgTAClosedDays.
     */
    public BigDecimal getAvgTAClosedDays() {
        return avgTAClosedDays;
    }


    /**
     * @param avgTAClosedDays The avgTAClosedDays to set.
     */
    public void setAvgTAClosedDays(BigDecimal avgTAClosedDays) {
        this.avgTAClosedDays = avgTAClosedDays;
    }


    /**
     * @return Returns the closedCapexNumToday.
     */
    public int getClosedCapexNumToday() {
        return closedCapexNumToday;
    }


    /**
     * @param closedCapexNumToday The closedCapexNumToday to set.
     */
    public void setClosedCapexNumToday(int closedCapexNumToday) {
        this.closedCapexNumToday = closedCapexNumToday;
    }


    /**
     * @return Returns the closedExpenseNumToday.
     */
    public int getClosedExpenseNumToday() {
        return closedExpenseNumToday;
    }


    /**
     * @param closedExpenseNumToday The closedExpenseNumToday to set.
     */
    public void setClosedExpenseNumToday(int closedExpenseNumToday) {
        this.closedExpenseNumToday = closedExpenseNumToday;
    }


    /**
     * @return Returns the closedPRNumToday.
     */
    public int getClosedPRNumToday() {
        return closedPRNumToday;
    }


    /**
     * @param closedPRNumToday The closedPRNumToday to set.
     */
    public void setClosedPRNumToday(int closedPRNumToday) {
        this.closedPRNumToday = closedPRNumToday;
    }


    /**
     * @return Returns the closedTANumToday.
     */
    public int getClosedTANumToday() {
        return closedTANumToday;
    }


    /**
     * @param closedTANumToday The closedTANumToday to set.
     */
    public void setClosedTANumToday(int closedTANumToday) {
        this.closedTANumToday = closedTANumToday;
    }


    /**
     * @return Returns the createdCapexNumToday.
     */
    public int getCreatedCapexNumToday() {
        return createdCapexNumToday;
    }


    /**
     * @param createdCapexNumToday The createdCapexNumToday to set.
     */
    public void setCreatedCapexNumToday(int createdCapexNumToday) {
        this.createdCapexNumToday = createdCapexNumToday;
    }


    /**
     * @return Returns the createdExpenseNumToday.
     */
    public int getCreatedExpenseNumToday() {
        return createdExpenseNumToday;
    }


    /**
     * @param createdExpenseNumToday The createdExpenseNumToday to set.
     */
    public void setCreatedExpenseNumToday(int createdExpenseNumToday) {
        this.createdExpenseNumToday = createdExpenseNumToday;
    }


    /**
     * @return Returns the createdPRNumToday.
     */
    public int getCreatedPRNumToday() {
        return createdPRNumToday;
    }


    /**
     * @param createdPRNumToday The createdPRNumToday to set.
     */
    public void setCreatedPRNumToday(int createdPRNumToday) {
        this.createdPRNumToday = createdPRNumToday;
    }


    /**
     * @return Returns the createdTANumToday.
     */
    public int getCreatedTANumToday() {
        return createdTANumToday;
    }


    /**
     * @param createdTANumToday The createdTANumToday to set.
     */
    public void setCreatedTANumToday(int createdTANumToday) {
        this.createdTANumToday = createdTANumToday;
    }


    /**
     * @return Returns the summaryDate.
     */
    public Date getSummaryDate() {
        return summaryDate;
    }


    /**
     * @param summaryDate The summaryDate to set.
     */
    public void setSummaryDate(Date summaryDate) {
        this.summaryDate = summaryDate;
    }

    /**
     * 缺省构造函数
     */
    public AbstractKPISummary() {
    }

    /**
     * 给定主键的构造函数
     * 
     * @param id
     */
    public AbstractKPISummary(Integer id) {
        this.setId(id);
    }

    public boolean equals(Object ks) {
        if (ks == null)
            return false;
        if (this == ks)
            return true;
        if (!(ks instanceof KPISummary))
            return false;
        KPISummary that = (KPISummary) ks;
        if (this.getId() != null)
            return this.getId().equals(that.getId());
        return that.getId() == null;
    }
    
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
