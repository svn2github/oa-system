/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.business.pr;

import java.io.Serializable;

/**
 * A class that represents a row in the 'capex_req_item_history' table.
 * 
 * @author nicebean
 * @version 1.0 (2005-11-19)
 */
public class CapexRequestHistoryItem extends AbstractCapexRequestHistoryItem implements Serializable {
    /**
     * Simple constructor of CapexRequestHistoryItem instances.
     */
    public CapexRequestHistoryItem() {
    }

    /**
     * Constructor of CapexRequestHistoryItem instances given a simple primary key.
     * 
     * @param id
     */
    public CapexRequestHistoryItem(Integer id) {
        super(id);
    }

    /* Add customized code below */

}
