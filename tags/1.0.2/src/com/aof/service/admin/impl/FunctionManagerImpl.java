/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.service.admin.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.config.ActionConfig;
import org.apache.struts.config.ModuleConfig;

import com.aof.dao.admin.FunctionDAO;
import com.aof.model.admin.Function;
import com.aof.model.admin.query.FunctionQueryOrder;
import com.aof.service.BaseManager;
import com.aof.service.admin.FunctionManager;
import com.aof.web.struts.action.SecureActionMapping;

/**
 * FunctionManager的实现
 * 
 * @author nicebean
 * @version 1.0 (2005-11-14)
 */
public class FunctionManagerImpl extends BaseManager implements FunctionManager {
    private Log log = LogFactory.getLog(FunctionManagerImpl.class);

    private FunctionDAO dao;

    private Map functions;

    private Map specialFunctions;

    private ModuleConfig lastConfig = null;

    /**
     * 设置FunctionDAO给dao属性
     * 
     * @param dao
     */
    public void setFunctionDAO(FunctionDAO dao) {
        this.dao = dao;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.service.admin.FunctionManager#isInitiated()
     */
    public boolean isInitiated() {
        return lastConfig != null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.service.admin.FunctionManager#initiate(org.apache.struts.config.ModuleConfig)
     */
    public void initiate(ModuleConfig config) {
        if (lastConfig == config)
            return;

        synchronized (this) {
            if (lastConfig == config)
                return;
            if (lastConfig != null)
                log.info("struts config changed, re-initiate");
            ActionConfig[] configs = config.findActionConfigs();
            if (functions == null) {
                functions = new HashMap();
                specialFunctions = new HashMap();
            } else {
                functions.clear();
                specialFunctions.clear();
            }
            for (int i = 0; i < configs.length; i++) {
                if (configs[i] instanceof SecureActionMapping) {
                    SecureActionMapping mapping = (SecureActionMapping) configs[i];
                    Integer functionId = mapping.getFunctionId();
                    if (functionId == null)
                        continue;
                    String description = mapping.getFunctionDescription();
                    String level = mapping.getLevel();
                    Function f = (Function) functions.get(functionId);
                    if (f == null) {
                        f = new Function(functionId);
                        f.setDescription(description);
                        f.setLevel(level);
                        functions.put(functionId, f);
                    } else {
                        if (description != null) {
                            if (f.getDescription() != null) {
                                throw new RuntimeException("Description duplicate defined for function id " + functionId + " in action mapping '"
                                        + mapping.getPath() + "'. In struts config, each function id can only has one description definition.");
                            }
                            f.setDescription(description);
                        }
                        if (level != null) {
                            if (f.getLevel() != null) {
                                throw new RuntimeException("Level duplicate defined for function id " + functionId + " in action mapping '" + mapping.getPath()
                                        + "'. In struts config, each function id can only has one level definition.");
                            }
                            f.setLevel(level);
                        }
                    }
                    f.addActionMapping(mapping);

                    String functionType = mapping.getFunctionType();
                    if (functionType != null) {
                        Function f2 = (Function) specialFunctions.put(functionType, f);
                        if (!(f2 == null || f.equals(f2))) {
                            throw new RuntimeException("Type '" + functionType + "' duplicate defined.");
                        }
                    }
                }
            }

            Iterator itor = functions.values().iterator();
            while (itor.hasNext()) {
                Function f = (Function) itor.next();
                if (f.getDescription() == null) {
                    switch (f.getId().intValue()) {
                    case 1:
                        f.setDescription("Common Global Function");
                        break;
                    case 2:
                        f.setDescription("Common Site Function");
                        break;
                    case 3:
                        f.setDescription("Common Department Function");
                        break;
                    default:
                        throw new RuntimeException("Description definition missing for function id " + f.getId()
                                + ". In struts config, each function id must has one description definition.");
                    }
                }
                if (f.getLevel() == null) {
                    switch (f.getId().toString().charAt(0)) {
                    case '1':
                        f.setLevel("g");
                        break;
                    case '2':
                        f.setLevel("s");
                        break;
                    case '3':
                        f.setLevel("d");
                        break;
                    default:
                        throw new RuntimeException("Level definition missing for function id " + f.getId()
                                + ". In struts config, each function id must has one level definition.");
                    }

                }
                Function f2 = dao.getFunction(f.getId());
                if (f2 == null) {
                    f.setName(f.getDescription());
                    dao.saveFunction(f);
                } else {
                    f.setName(f2.getName());
                    f.setDescription(f2.getDescription());
                }
            }
            lastConfig = config;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.service.admin.FunctionManager#getFunction(java.lang.String)
     */
    public Function getFunction(String functionType) {
        if (lastConfig == null) {
            throw new RuntimeException("use function manager before it initiated.");
        }
        return (Function) specialFunctions.get(functionType);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.service.admin.FunctionManager#getFunction(java.lang.Integer)
     */
    public Function getFunction(Integer id) {
        if (lastConfig == null) {
            throw new RuntimeException("use function manager before it initiated.");
        }
        Function f = (Function) functions.get(id);
        if (f == null) {
            throw new RuntimeException("Try to load function with id '" + id + "', it's not found in current struts config.");
        }
        Function f2 = dao.getFunction(id);
        f2.setActionMappings(f.getActionMappings());
        f2.setLevel(f.getLevel());
        return f2;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.service.admin.FunctionManager#saveFunction(com.aof.model.admin.Function)
     */
    public Function saveFunction(Function function) {
        if (lastConfig == null) {
            throw new RuntimeException("use function manager before it initiated.");
        }
        Integer id = function.getId();
        if (functions.get(id) == null) {
            throw new RuntimeException("try to save function with id '" + id + "', it's not found in current struts config.");
        }
        return dao.saveFunction(function);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.service.admin.FunctionManager#getFunctionListCount(java.util.Map)
     */
    public int getFunctionListCount(Map conditions) {
        if (lastConfig == null) {
            throw new RuntimeException("use function manager before it initiated.");
        }
        return dao.getFunctionListCount(conditions);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.service.admin.FunctionManager#getFunctionList(java.util.Map,
     *      int, int, com.aof.model.admin.query.FunctionQueryOrder, boolean)
     */
    public List getFunctionList(Map conditions, int pageNo, int pageSize, FunctionQueryOrder order, boolean descend) {
        if (lastConfig == null) {
            throw new RuntimeException("use function manager before it initiated.");
        }
        List result = dao.getFunctionList(conditions, pageNo, pageSize, order, descend);
        Iterator itor = result.iterator();
        while (itor.hasNext()) {
            Function function = (Function) itor.next();
            Integer id = function.getId();
            Function f = (Function) functions.get(id);
            if (f == null) {
                itor.remove();
            } else {
                function.setActionMappings(f.getActionMappings());
                function.setLevel(f.getLevel());
            }
        }
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.service.admin.FunctionManager#getAllFunction()
     */
    public List getAllFunction() {
        return getFunctionList(null, 0, -1, FunctionQueryOrder.NAME, false);
    }

}
