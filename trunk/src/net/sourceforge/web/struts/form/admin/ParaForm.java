/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.web.struts.form.admin;

import org.apache.struts.action.ActionForm;

public class ParaForm extends ActionForm {
	String suppday;
	String hotelday;
	
	public String getHotelday() {
		return hotelday;
	}
	public void setHotelday(String hotelday) {
		this.hotelday = hotelday;
	}
	public String getSuppday() {
		return suppday;
	}
	public void setSuppday(String suppday) {
		this.suppday = suppday;
	}
	
}