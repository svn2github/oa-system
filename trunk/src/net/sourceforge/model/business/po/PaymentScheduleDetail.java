/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.business.po;

import java.io.Serializable;


/**
 * A class that represents a row in the 'payment_schdl_det' table. This class may be
 * customized as it is never re-generated after being created.
 */
public class PaymentScheduleDetail extends AbstractPaymentScheduleDetail implements Serializable {
    /**
     * Simple constructor of PaymentScheduleDetail instances.
     */
    public PaymentScheduleDetail() {
    }

    /**
     * Constructor of PaymentScheduleDetail instances given a simple primary key.
     * 
     * @param id
     */
    public PaymentScheduleDetail(Integer id) {
        super(id);
    }

    public void clearId() {
        this.setId(null);
    }

    /* Add customized code below */
    

}
