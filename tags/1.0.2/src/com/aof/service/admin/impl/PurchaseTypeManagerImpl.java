package com.aof.service.admin.impl;

import java.util.List;

import com.aof.dao.admin.PurchaseTypeDAO;
import com.aof.model.admin.PurchaseType;
import com.aof.model.admin.Site;
import com.aof.service.admin.PurchaseTypeManager;

/**
 * service implement for domain model purchase type
 * @author shi1
 * @version 1.0 (Nov 25, 2005)
 */
public class PurchaseTypeManagerImpl implements PurchaseTypeManager{

    private PurchaseTypeDAO dao;

    public void setPurchaseTypeDAO(PurchaseTypeDAO dao) {
        this.dao = dao;
    }

    public PurchaseType getPurchaseType(String code) {
        return dao.getPurchaseType(code);
    }

    public List getEnabledPurchaseTypeList(Site site) {
        return dao.getEnabledPurchaseTypeList( site);
    }

    
    
    
}
