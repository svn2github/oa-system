/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

/*
 * Created Tue Jun 14 10:11:27 CST 2005 by MyEclipse Hibernate Tool.
 */
package com.aof.model.admin;

import java.io.Serializable;

/**
 * A class that represents a row in the metadata_mst table. This class may
 * be customized as it is never re-generated after being created.
 * 
 * @author ych
 * @version 1.0 (Nov 10, 2005)
 */
public class Metadata extends AbstractMetadata implements Serializable {
    /**
     * Simple constructor of Metadata instances.
     */
    public Metadata() {
    }

    /**
     * Constructor of Metadata instances given a simple primary key.
     * 
     * @param id
     */
    public Metadata(Integer id) {
        super(id);
    }
}