/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.business.pr;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

import net.sourceforge.model.business.BaseRecharge;
import net.sourceforge.model.business.BuyForTarget;
import net.sourceforge.model.business.po.PurchaseOrder;
import net.sourceforge.model.business.po.PurchaseOrderItem;

/**
 * A class that represents a row in the 'pr_item' table.
 * 
 * @author nicebean
 * @version 1.0 (2005-12-01)
 */
public class PurchaseRequestItem extends AbstractPurchaseRequestItem implements Serializable, BuyForTarget {
    /**
     * Simple constructor of PurchaseRequestItem instances.
     */
    public PurchaseRequestItem() {
    }

    /**
     * Constructor of PurchaseRequestItem instances given a simple primary key.
     * 
     * @param id
     */
    public PurchaseRequestItem(Integer id) {
        super(id);
    }

    /* Add customized code below */
    public BaseRecharge createNewRechargeObj() {
        PurchaseRequestItemRecharge prir = new PurchaseRequestItemRecharge();
        prir.setPurchaseRequestItem(this);
        return prir;
    }
    
    public BigDecimal getAmount() {
        BigDecimal price = getUnitPrice();
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
    
    public BigDecimal getBaseCurrencyPrice() {
        BigDecimal price = getUnitPrice();
        if (price == null) return null;
        BigDecimal exchangeRate = getExchangeRateValue();
        if (exchangeRate == null) return null;
        return price.multiply(exchangeRate).setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }
    
    private Collection recharges;

    public Collection getRecharges() {
        return recharges;
    }

    public void setRecharges(Collection recharges) {
        this.recharges = recharges;
    }
    
    private Collection attachments;

    public Collection getAttachments() {
        return attachments;
    }

    public void setAttachments(Collection attachments) {
        this.attachments = attachments;
    }
    
    public void clearId()
    {
        this.setId(null);
    }
    
    public Object getStatus() {
        PurchaseOrderItem poItem = this.getPurchaseOrderItem();
        if (poItem == null) {
            return this.getPurchaseRequest().getStatus();
        }
        PurchaseOrder po = poItem.getPurchaseOrder();
        if (po == null) {
            return this.getPurchaseRequest().getStatus();
        }
        return po.getStatus();
    }
    
}
