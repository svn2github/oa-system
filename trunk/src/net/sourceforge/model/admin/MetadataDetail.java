/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

/*
 * Created Tue Jun 14 10:11:27 CST 2005 by MyEclipse Hibernate Tool.
 */
package net.sourceforge.model.admin;

import java.io.Serializable;

/**
 * A class that represents a row in the metadata_det table. This class may
 * be customized as it is never re-generated after being created.
 * @author ych
 * @version 1.0 (Nov 10, 2005)
 */
public class MetadataDetail extends AbstractMetadataDetail implements Serializable {
    /**
     * Simple constructor of MetadataDetail instances.
     */
    public MetadataDetail() {
    }

    /**
     * Constructor of Metadata instances given a composite primary key.
     * 
     * @param id
     * @param type
     */
    public MetadataDetail(Integer id, Metadata type) {
        super(id, type);
    }
}