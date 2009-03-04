/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.service.admin.impl;

import java.util.List;

import com.aof.dao.admin.ParameterDAO;
import com.aof.model.admin.DataTransferParameter;
import com.aof.model.admin.GlobalParameter;
import com.aof.model.admin.Site;
import com.aof.model.admin.SiteMailReminder;
import com.aof.service.BaseManager;
import com.aof.service.admin.DataTransferManager;
import com.aof.service.admin.ParameterManager;
/**
 * GlobalParameterManagerµÄÊµÏÖ
 * @author ych
 * @version 1.0 (Dec 14, 2005)
 */
public class ParameterManagerImpl extends BaseManager implements ParameterManager {

    private ParameterDAO dao;

    private DataTransferManager dataTransferManager;
    
    
    
    /**
     * @param dataTransferManager The dataTransferManager to set.
     */
    public void setDataTransferManager(DataTransferManager dataTransferManager) {
        this.dataTransferManager = dataTransferManager;
    }

    public void setParameterDAO(ParameterDAO dao) {
        this.dao = dao;
    }
    
     public GlobalParameter getGlobalParameter(){
        return dao.getGlobalParameter();
    }

    public void updateGlobalParameter(GlobalParameter globalParameter,List reminderList) {
        dao.updateGlobalParameter(globalParameter);
        dao.updateGlobalMailReminder(reminderList);
    }

    public List getGlobalMailReminderList() {
        return dao.getGlobalMailReminderList();
    }

    
    public DataTransferParameter getDataTransferParameter(Site site) {
        return dao.getDataTransferParameter(site);
    }

    
    public void updateSiteParameter(DataTransferParameter para, List reminderList) {
        boolean needReset=false;
        boolean needRemove=false;
        DataTransferParameter oldPara=dao.getDataTransferParameter(para.getSite());
        if (para.getStartTime()==null || para.getIntervalMin()==null || para.getTimePerDay()==null) needRemove=true; 
        if (!oldPara.schemeEqual(para)) needReset=true; 
        dao.updateDataTransferParameter(para);
        dao.updateSiteMailReminder(((SiteMailReminder)reminderList.get(0)).getSite(),reminderList);
        dataTransferManager.updateJobParameter(para);
        if (needRemove) {
            dataTransferManager.removeJob(para);
        } else {
            if (needReset) {
                dataTransferManager.resetJob(para);
            }
        }
    }
    
    public List getSiteMailReminderList(Site site) {
        return dao.getSiteMailReminderList(site);
    }
    
    
}
