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
import java.util.HashMap;
import java.util.Map;

import net.sourceforge.model.Loggable;
import net.sourceforge.model.admin.Site;

/**
 * A class that represents a row in the 'po_item_receipt' table. This class may be
 * customized as it is never re-generated after being created.
 */
public class PurchaseOrderItemReceipt extends AbstractPurchaseOrderItemReceipt implements Serializable ,Loggable{
    /**
     * Simple constructor of PurchaseOrderItemReceipt instances.
     */
    public PurchaseOrderItemReceipt() {
    }

    /**
     * Constructor of PurchaseOrderItemReceipt instances given a simple primary key.
     * 
     * @param id
     */
    public PurchaseOrderItemReceipt(Integer id) {
        super(id);
    }

    /* Add customized code below */
    public boolean isFinished()
    {
        if(this.getReceiveQty1()==null) return false;
        if(this.getReceiveQty2()==null) return false;
        return (this.getReceiveQty1().equals(this.getReceiveQty2()));
    }

    public Site getLogSite() {
        return this.getPurchaseOrderItem().getPurchaseOrder().getSite();
    }

    public String getLogTargetName() {
        return LOG_TARGET_NAME;
    }

    public String getLogTargetId() {
        return this.getPurchaseOrderItem().getPurchaseOrder().getId();
    }

    public String[][] getLogFieldInfo(String action) {
        return (String[][]) actionFieldInfo.get(action);
    }
    
    private static final String LOG_TARGET_NAME="PO";
    

    public static final String LOG_ACTION_RECEIPT = "PO Receipt";
    public static final String LOG_ACTION_DELETERECEIPT = "DELETE PO Receipt";
    
    
    
    private static final Map actionFieldInfo = new HashMap();
    static {
        actionFieldInfo.put(LOG_ACTION_RECEIPT, 
                new String[][] { 
                    { "PO Item id", "purchaseOrderItem", "id" },
                    { "RECEIVER1", "receiver1", "name" },
                    { "RECEIVER2", "receiver2", "name" },
                    { "RCPT_DATE1", "receiveDate1", null },
                    { "RCPT_DATE2", "receiveDate2", null },
                    { "RCPT_QTY1", "receiveQty1", null },
                    { "RCPT_QTY2", "receiveQty2", null },
        });
        
        actionFieldInfo.put(LOG_ACTION_DELETERECEIPT, 
                new String[][] { 
                    { "PO Item id", "purchaseOrderItem", "id" },
                    { "RECEIVER1", "receiver1", "name" },
                    { "RECEIVER2", "receiver2", "name" },
                    { "RCPT_DATE1", "receiveDate1", null },
                    { "RCPT_DATE2", "receiveDate2", null },
                    { "RCPT_QTY1", "receiveQty1", null },
                    { "RCPT_QTY2", "receiveQty2", null },
        });
        
      
    }
}
