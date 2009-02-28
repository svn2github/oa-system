/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.web.struts.form.business.expense;

import com.aof.web.struts.form.BaseSessionQueryForm;


/**
 * ²éÑ¯ExpenseAttachmentµÄForm
 * @author ych
 * @version 1.0 (Nov 23, 2005)
 */
public class ExpenseAttachmentQueryForm extends BaseSessionQueryForm {

	/*id*/
	private String id;
	public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

	/*keyPropertyList*/

	/*kmtoIdList*/


	/*mtoIdList*/
	private String expense_id;
	public String getExpense_id() {
        return expense_id;
    }
    public void setExpense_id(String expense_id) {
        this.expense_id = expense_id;
    }
      

	/*property*/
	private String fileSize;
	public String getFileSize() {
        return fileSize;
    }
    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }
	private String fileName;
	public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
	private String description;
	public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
	private String uploadDate;
	public String getUploadDate() {
        return uploadDate;
    }
    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }
}
