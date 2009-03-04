/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

/*
 * Created Fri Sep 23 14:47:57 CST 2005 by MyEclipse Hibernate Tool.
 */
package com.aof.model.business.po;

import java.io.Serializable;

/**
 * A class that represents a row in the 'po_item_history' table. This class may be
 * customized as it is never re-generated after being created.
 */
public class PurchaseOrderHistoryItem extends AbstractPurchaseOrderHistoryItem implements Serializable {
    /**
     * Simple constructor of PurchaseOrderHistoryItem instances.
     */
    public PurchaseOrderHistoryItem() {
    }

    /**
     * Constructor of PurchaseOrderHistoryItem instances given a simple primary key.
     * 
     * @param id
     */
    public PurchaseOrderHistoryItem(Integer id) {
        super(id);
    }

    /* Add customized code below */

}
