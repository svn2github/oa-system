/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.business.pr;

import java.io.Serializable;

/**
 * A class that represents a row in the 'capex_request_attach' table (exclude
 * file_content column).
 * 
 * @author nicebean
 * @version 1.0 (2005-11-19)
 */
public class CapexRequestAttachment extends AbstractCapexRequestAttachment implements Serializable {
    /**
     * Simple constructor of CapexRequestAttachment instances.
     */
    public CapexRequestAttachment() {
    }

    /**
     * Constructor of CapexRequestAttachment instances given a simple primary key.
     * 
     * @param id
     */
    public CapexRequestAttachment(Integer id) {
        super(id);
    }

    /* Add customized code below */

}
