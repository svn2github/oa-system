/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.service.admin.impl;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;


import net.sourceforge.dao.admin.SupplierContractDAO;
import net.sourceforge.model.admin.SupplierContract;
import net.sourceforge.model.admin.query.SupplierContractQueryOrder;
import net.sourceforge.service.BaseManager;
import net.sourceforge.service.admin.SupplierContractManager;

/**
 * SupplierContractManagerµÄÊµÏÖ
 * @author ych
 * @version 1.0 (Nov 17, 2005)
 */
public class SupplierContractManagerImpl extends BaseManager implements SupplierContractManager {
    

    private SupplierContractDAO dao;

    public void setSupplierContractDAO(SupplierContractDAO dao) {
        this.dao = dao;
    }
    
     public SupplierContract getSupplierContract(Integer id)  {
        return dao.getSupplierContract(id);
    }

    public SupplierContract updateSupplierContract(SupplierContract function)  {
        return dao.updateSupplierContract(function);
    }
    
    public SupplierContract insertSupplierContract(SupplierContract function)  {
        return dao.insertSupplierContract(function);
    }
    

    public int getSupplierContractListCount(Map conditions)  {
        return dao.getSupplierContractListCount(conditions);
    }

    public List getSupplierContractList(Map conditions, int pageNo, int pageSize, SupplierContractQueryOrder order, boolean descend)  {
        return dao.getSupplierContractList(conditions, pageNo, pageSize, order, descend);
    }
    
    public SupplierContract insertSupplierContract(SupplierContract supplierContract, InputStream inputStream) {
		supplierContract.setUploadDate(new Date());
		SupplierContract sc = dao.insertSupplierContract(supplierContract);
        dao.saveSupplierContractContent(sc.getId(), inputStream);
        return sc;
	}
    
    public InputStream getSupplierContractContent(Integer id) {
        return dao.getSupplierContractContent(id);
    }

}
