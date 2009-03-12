/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.business.pr;

import java.io.Serializable;

import net.sourceforge.model.metadata.PurchaseRequestStatus;

/**
 * 该类代表pr_history表中的一行
 * 
 * @author nicebean
 * @version 1.0 (2005-12-01)
 */
public abstract class AbstractPurchaseRequestHistory implements Serializable {
    /**
     * The cached hash code value for this instance. Settting to 0 triggers
     * re-calculation.
     */
    private int hashValue = 0;

    /** identifier field */
    private Integer id;

    /** persistent field */
    private PurchaseRequestStatus status;

    /** persistent field */
    private String approveRequestId;

    /** persistent field */
    private PurchaseRequest purchaseRequest;

    /** default constructor */
    public AbstractPurchaseRequestHistory() {
    }

    /** minimal constructor */
    public AbstractPurchaseRequestHistory(Integer id) {
        setId(id);
    }

    /**
     * @return Returns the approveRequestId.
     */
    public String getApproveRequestId() {
        return approveRequestId;
    }

    /**
     * @param approveRequestId The approveRequestId to set.
     */
    public void setApproveRequestId(String approveRequestId) {
        this.approveRequestId = approveRequestId;
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
    protected void setId(Integer id) {
        this.hashValue = 0;
        this.id = id;
    }

    /**
     * @return Returns the purchaseRequest.
     */
    public PurchaseRequest getPurchaseRequest() {
        return purchaseRequest;
    }

    /**
     * @param purchaseRequest The purchaseRequest to set.
     */
    public void setPurchaseRequest(PurchaseRequest purchaseRequest) {
        this.purchaseRequest = purchaseRequest;
    }

    /**
     * @return Returns the status.
     */
    public PurchaseRequestStatus getStatus() {
        return status;
    }

    /**
     * @param status The status to set.
     */
    public void setStatus(PurchaseRequestStatus status) {
        this.status = status;
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
        if (!(rhs instanceof PurchaseRequestHistory))
            return false;
        PurchaseRequestHistory that = (PurchaseRequestHistory) rhs;
        if (this.getId() != null)
            return this.getId().equals(that.getId());
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
            int idValue = this.getId() == null ? 0 : this.getId().hashCode();
            result = result * 37 + idValue;
            this.hashValue = result;
        }
        return this.hashValue;
    }

}
