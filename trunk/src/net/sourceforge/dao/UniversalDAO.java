/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.dao;

import java.io.Serializable;

/**
 * dao interface for all
 * 
 * @author shilei
 * @version 1.0 (Nov 15, 2005)
 */
public interface UniversalDAO {

    /**
     * load domain object
     * @param clazz
     * @param value
     * @return
     */
    public Object load(Class clazz, Serializable value);
    
    /**
     * reload domain object from database
     * @param object
     */
    public void refresh(final Object object);
}
