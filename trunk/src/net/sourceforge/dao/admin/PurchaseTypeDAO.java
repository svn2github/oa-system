/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.dao.admin;

import java.util.List;


import net.sourceforge.model.admin.PurchaseType;
import net.sourceforge.model.admin.Site;

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
