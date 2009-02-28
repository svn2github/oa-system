/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.dao.admin;

import java.util.List;


import com.aof.model.admin.PurchaseType;
import com.aof.model.admin.Site;

/**
 * dao interface for PurchaseType
 * 
 * @author shilei
 * @version 1.0 (Nov 15, 2005)
 */
public interface PurchaseTypeDAO {

    /**
     * 从数据库取得指定id的PurchaseType
     * 
     * @param id
     *            PurchaseType的id
     * @return 返回指定的PurchaseType
     */
    public PurchaseType getPurchaseType(String code);


    /**
     * get Enabled PurchaseType List
     * @param site
     * @return
     */
    public List getEnabledPurchaseTypeList(final Site site);


}
