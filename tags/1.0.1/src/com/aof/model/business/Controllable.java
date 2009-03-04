/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.model.business;


/**
 * 支持审核的对象应该实现该接口
 * 
 * @author nicebean
 * @version 1.0 (2005-11-25)
 */
public interface Controllable {

    /**
     * 返回用于审核该对象的Flow的名称
     * @return Flow的名称
     */
    public String getControlFlowName();
    
}
