/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.business.pr;

import java.io.Serializable;

/**
 * A class that represents a row in the 'capex_request_attach' table (only
 * file_content column).
 * 
 * @author nicebean
 * @version 1.0 (2005-11-19)
 */
public class CapexRequestAttachmentContent extends AbstractCapexRequestAttachmentContent implements Serializable {
    /**
     * Simple constructor of CapexRequestAttachmentContent instances.
     */
    public CapexRequestAttachmentContent() {
    }

    /**
     * Constructor of CapexRequestAttachmentContent instances given a simple primary key.
     * 
     * @param id
     */
    public CapexRequestAttachmentContent(Integer id) {
        super(id);
    }

    /* Add customized code below */

}
