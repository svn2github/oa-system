/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.model.business.pr.query;

import org.apache.commons.lang.enums.Enum;

/**
 * CapexRequestApproveRequest查询顺序的枚举类
 * 
 * @author nicebean
 * @version 1.0 (2005-12-8)
 */
public class CapexRequestApproveRequestQueryOrder extends Enum {

    public static final CapexRequestApproveRequestQueryOrder SEQ = new CapexRequestApproveRequestQueryOrder("seq");

    public static final CapexRequestApproveRequestQueryOrder STATUS = new CapexRequestApproveRequestQueryOrder("status");

    public static final CapexRequestApproveRequestQueryOrder APPROVEDATE = new CapexRequestApproveRequestQueryOrder("approveDate");

    public static final CapexRequestApproveRequestQueryOrder COMMENT = new CapexRequestApproveRequestQueryOrder("comment");

    public static final CapexRequestApproveRequestQueryOrder CANMODIFY = new CapexRequestApproveRequestQueryOrder("canModify");

    public static final CapexRequestApproveRequestQueryOrder CAPEXREQUEST_CAPEX_ID = new CapexRequestApproveRequestQueryOrder("capexRequest_capex_id");

    protected CapexRequestApproveRequestQueryOrder(String value) {
        super(value);
    }

    public static CapexRequestApproveRequestQueryOrder getEnum(String value) {
        return (CapexRequestApproveRequestQueryOrder) getEnum(CapexRequestApproveRequestQueryOrder.class, value);
    }

}
