package com.aof.service.admin;

import java.util.List;

import com.aof.model.admin.ProjectCode;
import com.aof.model.admin.Site;

public interface ProjectCodeManager {
    
    public ProjectCode getProjectCode(Integer id);

    /**
     * get Enabled ProjectCode List
     * 
     * @return Enabled ProjectCode List
     */
    public List getEnabledProjectCodeListBySite(Site site);
}
