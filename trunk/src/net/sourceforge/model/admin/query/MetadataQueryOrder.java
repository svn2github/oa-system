/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.model.admin.query;

import org.apache.commons.lang.enums.Enum;

/**
 * Metadata查询顺序的枚举类
 *  
 * @author ych
 * @version 1.0 (Nov 10, 2005)
 */
public class MetadataQueryOrder extends Enum {

    public static final MetadataQueryOrder ID = new MetadataQueryOrder("id");

    /**
     * Constructor of MetadataQueryOrder instances given a simple name.
     * 
     * @param name
     *            the name of the Enum to construct
     */
    protected MetadataQueryOrder(String name) {
        super(name);
    }

    /**
     * 根据name获得MetadataQueryOrder
     * 
     * @param name
     *            the name of the Enum to get
     * @return the MetadataQueryOrder object, or null if the enum does not exist
     */
    public static MetadataQueryOrder getEnum(String name) {
        return (MetadataQueryOrder) getEnum(MetadataQueryOrder.class, name);
    }

}
