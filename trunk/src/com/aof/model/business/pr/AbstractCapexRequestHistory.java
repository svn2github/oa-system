/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.business.pr;

import java.io.Serializable;
import java.math.BigDecimal;

import com.aof.model.metadata.CapexRequestStatus;

/**
 * 该类代表capex_request_history表中的一行
 * 
 * @author nicebean
 * @version 1.0 (2005-11-19)
 */
public abstract class AbstractCapexRequestHistory implements Serializable {
    /**
     * The cached hash code value for this instance. Settting to 0 triggers
     * re-calculation.
     */
    private int hashValue = 0;

    /** identifier field */
    private Integer id;

    /** persistent field */
    private CapexRequestStatus status;

    /** persistent field */
    private BigDecimal amount;

    /** persistent field */
    private String approveRequestId;

    /** persistent field */
    private CapexRequest capexRequest;

    /** default constructor */
    public AbstractCapexRequestHistory() {
    }

    /** minimal constructor */
    public AbstractCapexRequestHistory(Integer id) {
        setId(id);
    }

    /**
     * @return Returns the amount.
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @param amount
     *            The amount to set.
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * @return Returns the approveRequestId.
     */
    public String getApproveRequestId() {
        return approveRequestId;
    }

    /**
     * @param approveRequestId
     *            The approveRequestId to set.
     */
    public void setApproveRequestId(String approveRequestId) {
        this.approveRequestId = approveRequestId;
    }

    /**
     * @return Returns the capexRequest.
     */
    public CapexRequest getCapexRequest() {
        return capexRequest;
    }

    /**
     * @param capexRequest
     *            The capexRequest to set.
     */
    public void setCapexRequest(CapexRequest capexRequest) {
        this.capexRequest = capexRequest;
    }

    /**
     * @return Returns the id.
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     *            The id to set.
     */
    protected void setId(Integer id) {
        this.hashValue = 0;
        this.id = id;
    }

    /**
     * @return Returns the status.
     */
    public CapexRequestStatus getStatus() {
        return status;
    }

    /**
     * @param status
     *            The status to set.
     */
    public void setStatus(CapexRequestStatus status) {
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
        if (!(rhs instanceof CapexRequestHistory))
            return false;
        CapexRequestHistory that = (CapexRequestHistory) rhs;
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
