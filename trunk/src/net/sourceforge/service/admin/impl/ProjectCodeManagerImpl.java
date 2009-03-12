package net.sourceforge.service.admin.impl;

import java.util.List;

import net.sourceforge.dao.admin.ProjectCodeDAO;
import net.sourceforge.model.admin.ProjectCode;
import net.sourceforge.model.admin.Site;
import net.sourceforge.service.BaseManager;
import net.sourceforge.service.admin.ProjectCodeManager;

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
