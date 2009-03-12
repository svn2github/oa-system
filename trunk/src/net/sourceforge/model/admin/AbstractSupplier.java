/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

/*
 * WARNING: DO NOT EDIT THIS FILE. This is a generated file that is synchronized
 * by MyEclipse Hibernate tool integration.
 *
 * Created Fri Sep 23 15:38:35 CST 2005 by MyEclipse Hibernate Tool.
 */
package net.sourceforge.model.admin;

import java.io.Serializable;
import java.util.Date;

import net.sourceforge.model.metadata.ExportStatus;
import net.sourceforge.model.metadata.SupplierConfirmStatus;
import net.sourceforge.model.metadata.YesNo;

/**
 * A class that represents a row in the t_supplier table. You can customize the
 * behavior of this class by editing the class, {@link Supplier()}. WARNING:
 * DO NOT EDIT THIS FILE. This is a generated file that is synchronized by
 * MyEclipse Hibernate tool integration.
 */
public abstract class AbstractSupplier extends BaseSupplier implements Serializable {
    /** The value of the simple expStatus property. */
    private ExportStatus exportStatus;

    /** The value of the simple lastExpFile property. */
    private java.lang.String lastExportFile;

    private SupplierHistory history;

    private YesNo confirmed;

    private String promoteMessage;
    
    private SupplierConfirmStatus confirmStatus;
    
    private Date emailDate;
    
    private int emailTimes=0;
    
    private Date lastModifyDate;

    /**
     * Simple constructor of AbstractSupplier instances.
     */
    public AbstractSupplier() {
    }

    /**
     * Constructor of AbstractSupplier instances given a simple primary key.
     * 
     * @param id
     */
    public AbstractSupplier(Integer id) {
        this.setId(id);
    }

    
    
	/**
     * @return Returns the emailDate.
     */
    public Date getEmailDate() {
        return emailDate;
    }

    /**
     * @param emailDate The emailDate to set.
     */
    public void setEmailDate(Date emailDate) {
        this.emailDate = emailDate;
    }

    /**
     * @return Returns the emailTimes.
     */
    public int getEmailTimes() {
        return emailTimes;
    }

    /**
     * @param emailTimes The emailTimes to set.
     */
    public void setEmailTimes(int emailTimes) {
        this.emailTimes = emailTimes;
    }

    /**
     * @return Returns the confirmed.
     */
    public YesNo getConfirmed() {
        return confirmed;
    }

    /**
     * @param confirmed The confirmed to set.
     */
    public void setConfirmed(YesNo confirmed) {
        this.confirmed = confirmed;
    }

    /**
     * @return Returns the exportStatus.
     */
    public ExportStatus getExportStatus() {
        return exportStatus;
    }

    /**
     * @param exportStatus The exportStatus to set.
     */
    public void setExportStatus(ExportStatus exportStatus) {
        this.exportStatus = exportStatus;
    }

    /**
     * @return Returns the history.
     */
    public SupplierHistory getHistory() {
        return history;
    }

    /**
     * @param history The history to set.
     */
    public void setHistory(SupplierHistory history) {
        this.history = history;
    }

    /**
     * @return Returns the lastExportFile.
     */
    public java.lang.String getLastExportFile() {
        return lastExportFile;
    }

    /**
     * @param lastExportFile The lastExportFile to set.
     */
    public void setLastExportFile(java.lang.String lastExportFile) {
        this.lastExportFile = lastExportFile;
    }

    /**
     * @return Returns the promoteMessage.
     */
    public String getPromoteMessage() {
        return promoteMessage;
    }

    
    
    /**
	 * @return Returns the confirmStatus.
	 */
	public SupplierConfirmStatus getConfirmStatus() {
		return confirmStatus;
	}

	/**
	 * @param confirmStatus The confirmStatus to set.
	 */
	public void setConfirmStatus(SupplierConfirmStatus confirmStatus) {
		this.confirmStatus = confirmStatus;
	}

	/**
     * @param promoteMessage The promoteMessage to set.
     */
    public void setPromoteMessage(String promoteMessage) {
        this.promoteMessage = promoteMessage;
    }

    public Date getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(Date lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }

    /**
     * Implementation of the equals comparison on the basis of equality of the primary key values.
     * @param rhs
     * @return boolean
     */
    public boolean equals(Object rhs) {
        if (rhs == null)
            return false;
        if (this == rhs)
            return true;
        if (!(rhs instanceof Supplier))
            return false;
        Supplier that = (Supplier) rhs;
        if (this.getId() != null)
            return this.getId().equals(that.getId());
        return that.getId() == null;
    }

}