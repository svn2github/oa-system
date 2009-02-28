package com.aof.service.admin.impl;

import java.util.List;

import com.aof.dao.admin.ProjectCodeDAO;
import com.aof.model.admin.ProjectCode;
import com.aof.model.admin.Site;
import com.aof.service.BaseManager;
import com.aof.service.admin.ProjectCodeManager;

public class ProjectCodeManagerImpl extends BaseManager implements ProjectCodeManager {

    private ProjectCodeDAO projectCodeDAO;
    
    public List getEnabledProjectCodeListBySite(Site site) {
        return projectCodeDAO.getEnabledProjectCodeListBySite(site);
    }

    public ProjectCode getProjectCode(Integer id) {
        return projectCodeDAO.getProjectCode(id);
    }

    public ProjectCodeDAO getProjectCodeDAO() {
        return projectCodeDAO;
    }

    public void setProjectCodeDAO(ProjectCodeDAO projectCodeDAO) {
        this.projectCodeDAO = projectCodeDAO;
    }

}
