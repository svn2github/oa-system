/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.web.struts.form.admin;

import com.aof.web.struts.form.BaseSessionQueryForm;


/**
 * ²éÑ¯SupplierContractµÄForm
 * @author ych
 * @version 1.0 (Nov 14, 2005)
 */
public class SupplierContractQueryForm extends BaseSessionQueryForm {


	private String id;
	
	private String Supplier_id;

    private String fileSize;
	
	private String fileName;
	
	private String description;
	
	private String uploadDate;

    /**
     * @return Returns the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return Returns the fileName.
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName The fileName to set.
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return Returns the fileSize.
     */
    public String getFileSize() {
        return fileSize;
    }

    /**
     * @param fileSize The fileSize to set.
     */
    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    /**
     * @return Returns the id.
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id to set.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return Returns the supplier_id.
     */
    public String getSupplier_id() {
        return Supplier_id;
    }

    /**
     * @param supplier_id The supplier_id to set.
     */
    public void setSupplier_id(String supplier_id) {
        Supplier_id = supplier_id;
    }

    /**
     * @return Returns the uploadDate.
     */
    public String getUploadDate() {
        return uploadDate;
    }

    /**
     * @param uploadDate The uploadDate to set.
     */
    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }
	
    
}
