package net.sourceforge.dao.admin;

import java.util.List;

import net.sourceforge.model.admin.ProjectCode;
import net.sourceforge.model.admin.Site;

public interface ProjectCodeDAO {

    public ProjectCode getProjectCode(Integer id);
    
    /**
     * get Enabled ProjectCode List
     * 
     * @return Enabled ProjectCode List
     */
    public List getEnabledProjectCodeListBySite(Site site);
}
