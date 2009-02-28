/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.service.admin.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.aof.dao.UniversalDAO;
import com.aof.dao.admin.SystemLogDAO;
import com.aof.model.Loggable;
import com.aof.model.admin.SystemLog;
import com.aof.model.admin.User;
import com.aof.model.admin.query.SystemLogQueryOrder;
import com.aof.model.metadata.MetadataDetailEnum;
import com.aof.service.BaseManager;
import com.aof.service.admin.SystemLogManager;
import com.shcnc.utils.BeanHelper;

/**
 * 操作SystemLog的实现类
 * 
 * @author nicebean
 * @version 1.0 (2006-02-15)
 */
public class SystemLogManagerImpl extends BaseManager implements SystemLogManager {
	
    private SystemLogDAO dao;
    private UniversalDAO universalDAO;
    
	public void setSystemLogDAO(SystemLogDAO dao) {
        this.dao = dao;
    }

    public void setUniversalDAO(UniversalDAO universalDAO) {
        this.universalDAO = universalDAO;
    }

    /* (non-Javadoc)
     * @see com.aof.service.admin.SystemLogManager#getSystemLog(java.lang.Integer)
     */
    public SystemLog getSystemLog(Integer id)  {
        return dao.getSystemLog(id);
    }

    /* (non-Javadoc)
     * @see com.aof.service.admin.SystemLogManager#insertSystemLog(com.aof.model.admin.SystemLog)
     */
    public SystemLog insertSystemLog(SystemLog systemLog)  {
        return dao.insertSystemLog(systemLog);
    }
    
    /* (non-Javadoc)
     * @see com.aof.service.admin.SystemLogManager#getSystemLogListCount(java.util.Map)
     */
    public int getSystemLogListCount(Map conditions)  {
        return dao.getSystemLogListCount(conditions);
    }

    /* (non-Javadoc)
     * @see com.aof.service.admin.SystemLogManager#getSystemLogList(java.util.Map, int, int, com.aof.model.admin.query.SystemLogQueryOrder, boolean)
     */
    public List getSystemLogList(Map conditions, int pageNo, int pageSize, SystemLogQueryOrder order, boolean descend)  {
        return dao.getSystemLogList(conditions, pageNo, pageSize, order, descend);
    }

    /* (non-Javadoc)
     * @see com.aof.service.admin.SystemLogManager#generateLog(com.aof.model.Loggable, com.aof.model.Loggable)
     */
    public void generateLog(Loggable oldTarget, Loggable newTarget, String action, User currentUser) {
        String[][] fieldInfos = newTarget.getLogFieldInfo(action);
        if (fieldInfos == null) {
            throw new RuntimeException("Action '" + action + "' not supported for log object '" + newTarget.getClass().getName() + "'");
        }
        if (fieldInfos.length == 0) {
            SystemLog systemLog = new SystemLog();
            systemLog.setAction(action);
            systemLog.setSite(newTarget.getLogSite());
            systemLog.setTarget(newTarget.getLogTargetName());
            systemLog.setTargetId(newTarget.getLogTargetId());
            systemLog.setUser(currentUser);
            insertSystemLog(systemLog);
        } else {
            for (int i = 0; i < fieldInfos.length; i++) {
                String[] fieldInfo = fieldInfos[i];
                String fieldName = fieldInfo[1];
                boolean logOldValue = false;
                Object oldValue = null;
                Object newValue = BeanHelper.getBeanPropertyValue(newTarget, fieldName);
                if (oldTarget != null) {
                    oldValue = BeanHelper.getBeanPropertyValue(oldTarget, fieldName);
                    if (oldValue == null) {
                        if (newValue == null) {
                            continue;
                        }
                        logOldValue = true;
                    } else {
                        if (newValue != null) {
                            if (oldValue.equals(newValue)) {
                                continue;
                            }
                        }
                        logOldValue = true;
                    }
                }

                SystemLog systemLog = new SystemLog();
                systemLog.setAction(action);
                systemLog.setSite(newTarget.getLogSite());
                systemLog.setTarget(newTarget.getLogTargetName());
                systemLog.setTargetId(newTarget.getLogTargetId());
                systemLog.setUser(currentUser);

                StringBuffer content = new StringBuffer();
                content.append(fieldInfo[0]).append(':');

                String logPropertyName = fieldInfo[2];
                if (logOldValue) {
                    if (oldValue instanceof Collection) {
                        for (Iterator itor = ((Collection)oldValue).iterator(); ; ) {
                            Object element = itor.next();
                            appendToContent(content, element, logPropertyName, false);
                            if (itor.hasNext()) {
                                content.append(',');
                            } else {
                                break;
                            }
                        }
                    } else {
                        appendToContent(content, oldValue, logPropertyName, false);
                    }
                    content.append(" -> ");
                }
                if (newValue instanceof Collection) {
                    for (Iterator itor = ((Collection)newValue).iterator(); ; ) {
                        Object element = itor.next();
                        appendToContent(content, element, logPropertyName, true);
                        if (itor.hasNext()) {
                            content.append(',');
                        } else {
                            break;
                        }
                    }
                } else {
                    appendToContent(content, newValue, logPropertyName, true);
                }
                systemLog.setContent(content.toString());
                insertSystemLog(systemLog);
            }
        }
    }

    private void appendToContent(StringBuffer content, Object element, String logPropertyName, boolean reload) {
        if (element == null) {
            content.append(element);
            return;
        }
        Object logProperty = element;
        if (logPropertyName != null) {
            logProperty = BeanHelper.getBeanPropertyValue(element, logPropertyName);
            if (logProperty == null && reload) {
                universalDAO.refresh(element);
                logProperty = BeanHelper.getBeanPropertyValue(element, logPropertyName);
            }
        }
        if (logProperty instanceof MetadataDetailEnum) {
            content.append(((MetadataDetailEnum)logProperty).getEngShortDescription());
            return;
        }
        content.append(logProperty);
    }
}
