/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

/*
 * Created Fri Sep 23 15:38:35 CST 2005 by MyEclipse Hibernate Tool.
 */
package net.sourceforge.model.admin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sourceforge.model.metadata.ContractStatus;

/**
 * A class that represents a row in the 'supplier' table. This class may be
 * customized as it is never re-generated after being created.
 */
public class Supplier extends AbstractSupplier implements Serializable {
    /**
     * Simple constructor of Supplier instances.
     */
    public Supplier() {
    }

    /**
     * Constructor of Supplier instances given a simple primary key.
     * 
     * @param id
     */
    public Supplier(Integer id) {
        super(id);
    }

    /**
     * ·µ»ØSupplierµÄcontractStatus
     * 
     * @return SupplierµÄcontractStatus
     */
    public ContractStatus getContractStatus() {
        Date today = new Date();
        Date contractStartDate = getContractStartDate();
        Date contractExpireDate = getContractExpireDate();
        if (contractStartDate == null) {
            if (contractExpireDate == null)
                return ContractStatus.ACTIVED;
            else {
                if (today.compareTo(contractExpireDate) < 0)
                    return ContractStatus.ACTIVED;
                else
                    return ContractStatus.EXPIRED;
            }
        } else {
            if (contractExpireDate == null) {
                if (today.compareTo(contractStartDate) < 0)
                    return ContractStatus.NOT_ACTIVED;
                else
                    return ContractStatus.ACTIVED;
            } else {
                if (today.compareTo(contractStartDate) < 0)
                    return ContractStatus.NOT_ACTIVED;
                else {
                    if (today.compareTo(contractExpireDate) < 0)
                        return ContractStatus.ACTIVED;
                    else
                        return ContractStatus.EXPIRED;
                }
            }
        }
    }

    private List items;

    /**
     * @return Returns the items.
     */
    public List getItems() {
        return items;
    }

    /**
     * @param items
     *            The items to set.
     */
    public void setItems(List items) {
        this.items = items;
    }

    public void addItem(SupplierItem si) {
        if (items == null) items = new ArrayList();
        items.add(si);
    }
    
    public void emailed(Date d) {
        this.setEmailDate(d);
        this.setEmailTimes(this.getEmailTimes()+1);
    }
}
