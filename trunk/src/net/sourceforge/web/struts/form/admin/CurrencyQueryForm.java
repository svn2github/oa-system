/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.web.struts.form.admin;

import net.sourceforge.web.struts.form.BaseSessionQueryForm;


/**
 * ²éÑ¯CurrencyµÄForm
 * @author ych
 * @version 1.0 (Nov 14, 2005)
 */
public class CurrencyQueryForm extends BaseSessionQueryForm {
	private String code;
	
    private String name;
    
    private String status;
    

    /**
     * @return Returns the code.
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code The code to set.
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    
    /**
     * @return Returns the status.
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status The status to set.
     */
    public void setStatus(String status) {
        this.status = status;
    }


	
}
