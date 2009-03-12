/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.business.pr;

import java.io.Serializable;

/**
 * A class that represents a row in the 'capex_department' table.
 * 
 * @author nicebean
 * @version 1.0 (2005-11-19)
 */
public class CapexDepartment extends AbstractCapexDepartment implements Serializable {
    /**
     * Simple constructor of CapexDepartment instances.
     */
    public CapexDepartment() {
    }

    /**
     * Constructor of CapexDepartment instances given a simple primary key.
     * 
     * @param id
     */
    public CapexDepartment(Integer id) {
        super(id);
    }

    /* Add customized code below */

}
