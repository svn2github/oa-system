/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.web.struts.form.admin;

import org.apache.struts.action.ActionForm;

/**
 * MetatdataµÄFormÀà
 * @author ych
 * @version 1.0 (Nov 10, 2005)
 */
public class MetadataForm extends ActionForm {
	
	private String id;
	
	private String description;

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
	
	
}
