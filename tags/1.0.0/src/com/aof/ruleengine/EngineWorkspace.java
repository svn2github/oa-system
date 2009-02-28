/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.ruleengine;

import java.util.HashMap;
import java.util.Map;

/**
 * Rule Engine 工作区，用来保存一系列工作的Flow
 * 
 * @author nicebean
 * @version 1.0 (2005-11-15)
 */
public class EngineWorkspace {
    private Map flows;

    /**
     * 默认构造函数，创建一个新的工作区
     */
    public EngineWorkspace() {
        flows = new HashMap();
    }

    /**
     * 通过Flow的名字查询工作区中的Flow
     * 
     * @param name
     *            Flow的名字
     * @return 该名字的Flow，如果不存在返回null
     */
    public EngineFlow getFlow(String name) {
        return (EngineFlow) flows.get(name);
    }

    /**
     * 从工作区中移除指定名字的Flow
     * 
     * @param name
     *            Flow的名字
     * @return 被移除的Flow，如果不存在返回null
     */
    public EngineFlow removeFlow(String name) {
        EngineFlow ef = getFlow(name);
        if (ef == null) return null;
        ef.lockForUpdate();
        try {
            flows.remove(name);
        } finally {
            ef.releaseLock();
        }
        return ef;
    }

    /**
     * 在工作区中创建指定名字的Flow，如果指定的名字已经存在，产生Exception
     * 
     * @param name
     *            Flow的名字
     * @return 新创建的Flow
     */
    public EngineFlow createFlow(String name) {
        if (flows.containsKey(name))
            throw new RuntimeException("Flow '" + name + "' already existed");
        EngineFlow f = new EngineFlow();
        flows.put(name, f);
        return f;
    }
}
