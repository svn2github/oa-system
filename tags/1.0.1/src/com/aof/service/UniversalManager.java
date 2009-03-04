/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.service;

import java.io.Serializable;



/**
 * service manager interface for every domain model
 * 
 * @author shilei
 * @version 1.0 (Nov 15, 2005)
 */
public interface UniversalManager {
    public Object load(Class clazz,Serializable value);
}
