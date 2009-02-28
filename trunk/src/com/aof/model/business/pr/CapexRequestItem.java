/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.business.pr;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A class that represents a row in the 'capex_request_item' table.
 * 
 * @author nicebean
 * @version 1.0 (2005-11-19)
 */
public class CapexRequestItem extends AbstractCapexRequestItem implements Serializable {
    /**
     * Simple constructor of CapexRequestItem instances.
     */
    public CapexRequestItem() {
    }

    /**
     * Constructor of CapexRequestItem instances given a simple primary key.
     * 
     * @param id
     */
    public CapexRequestItem(Integer id) {
        super(id);
    }

    /* Add customized code below */

    public BigDecimal getAmount() {
        BigDecimal price = getPrice();
        if (price == null) return null;
        int quantity = getQuantity();
        return price.multiply(new BigDecimal(quantity)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }
    
    public BigDecimal getBaseCurrencyAmount() {
        BigDecimal exchangeRate = getExchangeRateValue();
        if (exchangeRate == null) return null;
        BigDecimal amount = getAmount();
        if (amount == null) return null;
        return amount.multiply(exchangeRate).setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }
}
