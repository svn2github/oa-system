/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.admin;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A class that represents a row in the exchange_rate table.
 * 
 * @author nicebean
 * @version 1.0 (2005-11-15)
 */
public abstract class AbstractExchangeRate implements Serializable {
    /**
     * The cached hash code value for this instance. Settting to 0 triggers
     * re-calculation.
     */
    private int hashValue = 0;
    
    private Integer id;

    private Currency currency;

    private Site site;

    /** The value of the simple exchangeRate property. */
    private BigDecimal exchangeRate;

    /**
     * Simple constructor of AbstractExchangeRate instances.
     */
    public AbstractExchangeRate() {
    }

    /**
     * Constructor of AbstractExchangeRate instances given a simple primary
     * key.
     * 
     * @param id
     */
    public AbstractExchangeRate(Integer id) {
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
     * @return Returns the currency.
     */
    public Currency getCurrency() {
        return currency;
    }

    /**
     * @param currency
     *            The currency to set.
     */
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    /**
     * @return Returns the site.
     */
    public Site getSite() {
        return site;
    }

    /**
     * @param site
     *            The site to set.
     */
    public void setSite(Site site) {
        this.site = site;
    }

    /**
     * Return the value of the exchange_rate column.
     * 
     * @return BigDecimal
     */
    public BigDecimal getExchangeRate() {
        return this.exchangeRate;
    }

    /**
     * Set the value of the exchange_rate column.
     * 
     * @param exchangeRate
     */
    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    /**
     * Implementation of the equals comparison on the basis of equality of the
     * primary key values.
     * 
     * @param rhs
     * @return boolean
     */
    public boolean equals(Object rhs) {
        if (rhs == null)
            return false;
        if (this == rhs)
            return true;
        if (!(rhs instanceof ExchangeRate))
            return false;
        ExchangeRate that = (ExchangeRate) rhs;
        if (this.getId() != null) {
            return this.getId().equals(that.getId());
        }
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
            int currencyValue = this.getCurrency() == null ? 0 : this.getCurrency().hashCode();
            result = result * 37 + currencyValue;
            int tSiteValue = this.getSite() == null ? 0 : this.getSite().hashCode();
            result = result * 37 + tSiteValue;
            this.hashValue = result;
        }
        return this.hashValue;
    }
}
