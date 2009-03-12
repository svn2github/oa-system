/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.business.po;

import java.io.Serializable;

import net.sourceforge.model.admin.User;

/**
 * 该类代表po_approve表的一行记录
 */
public class PurchaseOrderApproveRequest extends AbstractPurchaseOrderApproveRequest implements Serializable {

    /**
     * 缺省构造函数
     */
    public PurchaseOrderApproveRequest() {
    }

    /**
     * 给定主键的构造函数
     * 
     * @param id
     * @param approver
     */
    public PurchaseOrderApproveRequest(String id, User approver) {
        super(id, approver);
    }

    /* Add customized code below */

}
