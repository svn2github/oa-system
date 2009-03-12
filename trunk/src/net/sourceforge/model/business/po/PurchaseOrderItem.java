/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

/*
 * Created Fri Sep 23 14:47:57 CST 2005 by MyEclipse Hibernate Tool.
 */
package net.sourceforge.model.business.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

import net.sourceforge.model.admin.User;
import net.sourceforge.model.business.BaseRecharge;
import net.sourceforge.model.business.BuyForTarget;
import net.sourceforge.model.business.ta.AirTicket;

/**
 * A class that represents a row in the 't_po_item' table. This class may be
 * customized as it is never re-generated after being created.
 */
public class PurchaseOrderItem extends AbstractPurchaseOrderItem implements Serializable, BuyForTarget {
    /**
     * Simple constructor of PurchaseOrderItem instances.
     */
    public PurchaseOrderItem() {
    }

    /**
     * Constructor of PurchaseOrderItem instances given a simple primary key.
     * 
     * @param id
     */
    public PurchaseOrderItem(Integer id) {
        super(id);
    }

    /* Add customized code below */
    private Collection recharges;

    private Collection attachments;

    private AirTicket airTicket;

    public Collection getAttachments() {
        return attachments;
    }

    public void setAttachments(Collection attachments) {
        this.attachments = attachments;
    }

    public Collection getRecharges() {
        return recharges;
    }

    public void setRecharges(Collection recharges) {
        this.recharges = recharges;
    }

    /**
     * @return Returns the airTicket.
     */
    public AirTicket getAirTicket() {
        return airTicket;
    }

    /**
     * @param airTicket
     *            The airTicket to set.
     */
    public void setAirTicket(AirTicket airTicket) {
        this.airTicket = airTicket;
    }

    public BaseRecharge createNewRechargeObj() {
        PurchaseOrderItemRecharge poir = new PurchaseOrderItemRecharge();
        poir.setPurchaseOrderItem(this);
        return poir;
    }

    public BigDecimal getAmount() {
        BigDecimal price = getUnitPrice();
        if (price == null)
            return null;
        int quantity = getQuantity();
        return price.multiply(new BigDecimal(quantity)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    public BigDecimal getExchangeRateValue() {
        if (this.getPurchaseOrder() == null) {
            if (this.getExchangeRate() != null)
                return this.getExchangeRate().getExchangeRate();
            else
                return null;
        } else {
            return this.getPurchaseOrder().getExchangeRateValue();
        }
    }

    public BigDecimal getBaseCurrencyAmount() {
        BigDecimal exchangeRateValue = this.getExchangeRateValue();
        if (exchangeRateValue == null)
            return null;
        BigDecimal amount = getAmount();
        if (amount == null)
            return null;
        return amount.multiply(exchangeRateValue).setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    public void clearId() {
        this.setId(null);

    }

    public User getInspector() {
        if (this.getPurchaseOrder() == null)
            return null;
        return this.getPurchaseOrder().getInspector();
    }

    public User getRequestor() {
        if (this.getPurchaseRequestItem() == null)
            return null;
        return this.getPurchaseRequestItem().getPurchaseRequest().getRequestor();
    }

    public boolean isReceived() {
        if (this.getReceivedQuantity() == null && this.getCancelledQuantity() == null && this.getReturnedQuantity() == null)
            return false;
        return (this.getQuantity() == this.getProcessedQty());
    }

    private int intValue(Integer v) {
        if (v == null)
            return 0;
        return v.intValue();
    }

    public int getProcessedQty() {
        int receivedQuantity = this.intValue(this.getReceivedQuantity());
        int returnedQuantity = this.intValue(this.getReturnedQuantity());
        int canceldQuantity = this.intValue(this.getCancelledQuantity());
        return receivedQuantity + returnedQuantity + canceldQuantity;
    }

    public void receive(int qty) {
        if (qty > 0)
            addReceivedQty(qty);
        else
            addReturnedQty(-qty);
    }

    public void addReceivedQty(int qty) {
        if (qty <= 0)
            throw new RuntimeException("qty must >0");
        int oldReceivedQty = intValue(this.getReceivedQuantity());
        this.setReceivedQuantity(new Integer(oldReceivedQty + qty));
    }

    public void addReturnedQty(int qty) {
        if (qty <= 0)
            throw new RuntimeException("qty must >0");
        int oldQty = intValue(this.getReturnedQuantity());
        this.setReturnedQuantity(new Integer(oldQty + qty));
    }

    private BigDecimal outputedAmount = new BigDecimal(0);
    private BigDecimal outputedPrice = new BigDecimal(0);

    public BigDecimal getOutputedAmount() {
        return outputedAmount;
    }

    public void setOutputedAmount(BigDecimal outputedAmount) {
        this.outputedAmount = outputedAmount;
    }

    public BigDecimal getOutputedPrice() {
        return outputedPrice;
    }

    public void setOutputedPrice(BigDecimal outputedPrice) {
        this.outputedPrice = outputedPrice;
    }

}
