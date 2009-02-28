/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.service.admin.impl;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aof.dao.admin.SiteDAO;
import com.aof.model.admin.Site;
import com.aof.model.admin.query.SiteQueryCondition;
import com.aof.model.admin.query.SiteQueryOrder;
import com.aof.model.metadata.EnabledDisabled;
import com.aof.service.BaseManager;
import com.aof.service.admin.SiteManager;

/**
 * SiteManager的实现
 * 
 * @author nicebean
 * @version 1.0 (2005-11-14)
 */
public class SiteManagerImpl extends BaseManager implements SiteManager {

    private SiteDAO dao;

    /**
     * 设置SiteDAO给dao属性
     * 
     * @param dao
     *            SiteDAO对象
     */
    public void setSiteDAO(SiteDAO dao) {
        this.dao = dao;
    }

    /* (non-Javadoc)
     * @see com.aof.service.admin.SiteManager#getSite(java.lang.Integer)
     */
    public Site getSite(Integer id)  {
        return dao.getSite(id);
    }

    /* (non-Javadoc)
     * @see com.aof.service.admin.SiteManager#saveSite(com.aof.model.admin.Site)
     */
    public Site saveSite(Site site)  {
        return dao.saveSite(site);
    }

    /* (non-Javadoc)
     * @see com.aof.service.admin.SiteManager#saveSite(com.aof.model.admin.Site, java.io.InputStream)
     */
    public Site saveSite(Site site, InputStream in)  {
        Site s = this.saveSite(site);
        this.saveSiteLogo(s.getId(), in);
        return s;
    }

    /* (non-Javadoc)
     * @see com.aof.service.admin.SiteManager#removeSite(java.lang.Integer)
     */
    public void removeSite(Integer id)  {
        dao.removeSite(id);
    }

    /* (non-Javadoc)
     * @see com.aof.service.admin.SiteManager#getAllEnabledSiteList()
     */
    public List getAllEnabledSiteList()  {
        Map conditions = new HashMap();
        conditions.put(SiteQueryCondition.ENABLED_EQ, EnabledDisabled.ENABLED.getEnumCode());
        return dao.getSiteList(conditions, 0, -1, SiteQueryOrder.NAME, false);
    }

    /* (non-Javadoc)
     * @see com.aof.service.admin.SiteManager#getAllEnabledSiteListAndIncludeThis(java.lang.Integer)
     */
    public List getAllEnabledSiteListAndIncludeThis(Integer id)  {
        Site s = dao.getSite(id);
        List l = getAllEnabledSiteList();
        if (s == null)
            return l;
        if (l.contains(s))
            return l;
        for (int i = 0; i < l.size(); i++) {
            Site s2 = (Site) l.get(i);
            if (s2.getName().compareTo(s.getName()) > 0) {
                l.add(i, s);
                return l;
            }
        }
        l.add(s);
        return l;
    }

    /* (non-Javadoc)
     * @see com.aof.service.admin.SiteManager#getSiteListCount(java.util.Map)
     */
    public int getSiteListCount(Map conditions)  {
        return dao.getSiteListCount(conditions);
    }

    /* (non-Javadoc)
     * @see com.aof.service.admin.SiteManager#getSiteList(java.util.Map, int, int, com.aof.model.admin.query.SiteQueryOrder, boolean)
     */
    public List getSiteList(Map conditions, int pageNo, int pageSize, SiteQueryOrder order, boolean descend)  {
        return dao.getSiteList(conditions, pageNo, pageSize, order, descend);
    }

    /* (non-Javadoc)
     * @see com.aof.service.admin.SiteManager#isSiteHasLogo(java.lang.Integer)
     */
    public boolean isSiteHasLogo(Integer id)  {
        return dao.isSiteHasLogo(id);
    }

    /* (non-Javadoc)
     * @see com.aof.service.admin.SiteManager#getSiteLogo(java.lang.Integer)
     */
    public InputStream getSiteLogo(Integer id)  {
        return dao.getSiteLogo(id);
    }

    /* (non-Javadoc)
     * @see com.aof.service.admin.SiteManager#saveSiteLogo(java.lang.Integer, java.io.InputStream)
     */
    public void saveSiteLogo(Integer id, InputStream in)  {
        dao.saveSiteLogo(id, in);
    }


}
