/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.web.struts.form.admin;

import com.aof.web.struts.form.BaseSessionQueryForm;


/**
 * ²éÑ¯Hotel ContractµÄForm
 * 
 * @author nicebean
 * @version 1.0 (2005-11-15)
 */
public class HotelContractQueryForm extends BaseSessionQueryForm {

    /* id */
    private String id;

    /**
     * @return Returns the id.
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *            The id to set.
     */
    public void setId(String id) {
        this.id = id;
    }

    /* keyPropertyList */

    /* kmtoIdList */

    /* mtoIdList */
    private String hotel_id;

    /**
     * @return Returns the hotel_id.
     */
    public String getHotel_id() {
        return hotel_id;
    }

    /**
     * @param hotel_id
     *            The hotel_id to set.
     */
    public void setHotel_id(String hotel_id) {
        this.hotel_id = hotel_id;
    }

    /* property */
    private String fileName;

    /**
     * @return Returns the fileName.
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName
     *            The fileName to set.
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    private String description;

    /**
     * @return Returns the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     *            The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    private String fileContent;

    /**
     * @return Returns the fileContent.
     */
    public String getFileContent() {
        return fileContent;
    }

    /**
     * @param fileContent
     *            The fileContent to set.
     */
    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    private String uploadDate;

    /**
     * @return Returns the uploadDate.
     */
    public String getUploadDate() {
        return uploadDate;
    }

    /**
     * @param uploadDate
     *            The uploadDate to set.
     */
    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }
}
