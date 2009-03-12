/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

/*
 * Created Fri Sep 23 14:18:06 CST 2005 by MyEclipse Hibernate Tool.
 */
package net.sourceforge.model.admin;

import java.io.Serializable;

/**
 * A class that represents a row (only content filed) in the 'supplier_contract'
 * table. This class may be customized as it is never re-generated after being
 * created.
 */
public class SupplierContractContent extends AbstractSupplierContractContent implements Serializable {
    /**
     * Simple constructor of SupplierContractContent instances.
     */
    public SupplierContractContent() {
    }

    /**
     * Constructor of SupplierContractContent instances given a simple primary key.
     * 
     * @param id
     */
    public SupplierContractContent(Integer id) {
        super(id);
    }
   

}
