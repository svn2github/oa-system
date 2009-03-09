/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

/*
 * Created Fri Sep 23 14:49:10 CST 2005 by MyEclipse Hibernate Tool.
 */
package com.aof.model.admin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that represents a row in the 't_pur_cate' table. This class may be
 * customized as it is never re-generated after being created.
 */
public class PurchaseCategory extends AbstractPurchaseCategory implements Serializable {
    /**
     * Simple constructor of PurchaseCategory instances.
     */
    public PurchaseCategory() {
    }

    /**
     * Constructor of PurchaseCategory instances given a simple primary key.
     * 
     * @param id
     */
    public PurchaseCategory(java.lang.Integer id) {
        super(id);
    }

    
    private List enabledPurchaseSubCategoryList;
    
    /**
     * @return Returns the enabledPurchaseSubCategoryList.
     */
    public List getEnabledPurchaseSubCategoryList() {
        return enabledPurchaseSubCategoryList;
    }

    /**
     * @param enabledPurchaseSubCategoryList The enabledPurchaseSubCategoryList to set.
     */
    public void setEnabledPurchaseSubCategoryList(List enabledPurchaseSubCategoryList) {
        this.enabledPurchaseSubCategoryList = enabledPurchaseSubCategoryList;
    }

    public void addEnabledPurchaseSubCategory(PurchaseSubCategory psc) {
        if (enabledPurchaseSubCategoryList == null) {
            enabledPurchaseSubCategoryList = new ArrayList();
            enabledPurchaseSubCategoryList.add(psc);
        } else if (!enabledPurchaseSubCategoryList.contains(psc)) {
            enabledPurchaseSubCategoryList.add(psc);
        }
    }

}
