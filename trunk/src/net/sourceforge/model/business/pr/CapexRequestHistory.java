/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.business.pr;

import java.io.Serializable;

/**
 * A class that represents a row in the 'capex_request_history' table.
 * 
 * @author nicebean
 * @version 1.0 (2005-11-19)
 */
public class CapexRequestHistory extends AbstractCapexRequestHistory implements Serializable {
    /**
     * Simple constructor of CapexRequestHistory instances.
     */
    public CapexRequestHistory() {
    }

    /**
     * Constructor of CapexRequestHistory instances given a simple primary key.
     * 
     * @param id
     */
    public CapexRequestHistory(Integer id) {
        super(id);
    }

    /* Add customized code below */

}
