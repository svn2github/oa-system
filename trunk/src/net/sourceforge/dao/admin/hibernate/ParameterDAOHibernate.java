/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.dao.admin.hibernate;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.orm.hibernate.HibernateCallback;

import net.sourceforge.dao.BaseDAOHibernate;
import net.sourceforge.dao.admin.ParameterDAO;
import net.sourceforge.model.admin.DataTransferParameter;
import net.sourceforge.model.admin.GlobalMailReminder;
import net.sourceforge.model.admin.GlobalParameter;
import net.sourceforge.model.admin.Site;
import net.sourceforge.model.admin.SiteMailReminder;
import net.sourceforge.model.metadata.EnabledDisabled;
import net.sourceforge.model.metadata.ExportFileType;
import net.sourceforge.model.metadata.GlobalMailReminderType;

/**
 * GlobalParameterDAOµÄHibernateÊµÏÖ 
 * @author ych
 * @version 1.0 (Dec 14, 2005)
 */
public class ParameterDAOHibernate extends BaseDAOHibernate implements ParameterDAO {

    public GlobalParameter getGlobalParameter() {
        List ls=getHibernateTemplate().find("from GlobalParameter");
        if (ls.size()==0){
            GlobalParameter para=new GlobalParameter();
            getHibernateTemplate().save(para);
            return para;
        }
        return (GlobalParameter) ls.get(0);
    }

    public GlobalParameter updateGlobalParameter(GlobalParameter globalParameter) {
        Integer id = globalParameter.getId();
        if (id == null) {
            throw new RuntimeException("cannot save a globalParameter with null id");
        }
        GlobalParameter oldGlobalParameter = getGlobalParameter();
        if (oldGlobalParameter != null) {
        	try{
                PropertyUtils.copyProperties(oldGlobalParameter,globalParameter);
        	}
        	catch(Exception e)
        	{
                throw new RuntimeException("copy error£º"+e);
        	}
        	getHibernateTemplate().update(oldGlobalParameter);	
        	return oldGlobalParameter;
        }
        else
        {
        	throw new RuntimeException("globalParameter not found");
        }
    }

    public GlobalMailReminder getGlobalMailReminder(GlobalMailReminderType type){
        if (type == null) {
            return null;
        }
        return (GlobalMailReminder) getHibernateTemplate().get(GlobalMailReminder.class, type);
    }
    
    public List getGlobalMailReminderList() {
        return getHibernateTemplate().find("from GlobalMailReminder gmr order by gmr.type");
    }
    
    private void deleteGlobalMailReminderList() {
        getHibernateTemplate().delete("from GlobalMailReminder");
    }
    
    public void updateGlobalMailReminder(List reminderList) {
        deleteGlobalMailReminderList();
        for (Iterator itor = reminderList.iterator(); itor.hasNext();) {
            GlobalMailReminder reminder = (GlobalMailReminder) itor.next();
            getHibernateTemplate().save(reminder);
        }
    }

    
    public DataTransferParameter getDataTransferParameter(Site site) {
        List result= getHibernateTemplate().find("from DataTransferParameter dtp where dtp.site.id=?",site.getId(),Hibernate.INTEGER);
        if (result.size()==0){
            DataTransferParameter dataTransferParameter=new DataTransferParameter();
            dataTransferParameter.setSite(site);
            dataTransferParameter.setExportFileType(ExportFileType.TEXT);
            getHibernateTemplate().save(dataTransferParameter);
            return dataTransferParameter;
        } else {
            return (DataTransferParameter)result.get(0);
        }
    }

    
    public DataTransferParameter updateDataTransferParameter(DataTransferParameter dataTransferParameter) {
        Integer id = dataTransferParameter.getSite().getId();
        if (id == null) {
            throw new RuntimeException("cannot save a dataTransferParameter with null site id");
        }
        DataTransferParameter oldDataTransferParameter = getDataTransferParameter(dataTransferParameter.getSite());
        if (oldDataTransferParameter != null) {
            try{
                PropertyUtils.copyProperties(oldDataTransferParameter,dataTransferParameter);
            }
            catch(Exception e)
            {
                throw new RuntimeException("copy error£º"+e);
            }
            getHibernateTemplate().update(oldDataTransferParameter);
            getHibernateTemplate().flush();
            return oldDataTransferParameter;
        }
        else
        {
            throw new RuntimeException("oldDataTransferParameter not found");
        }
    }
    
    private void deleteSiteMailReminderList(Site site) {
        getHibernateTemplate().delete("from SiteMailReminder smr where smr.site.id=?",site.getId(),Hibernate.INTEGER);
    }
    
    public void updateSiteMailReminder(Site site,List reminderList) {
        deleteSiteMailReminderList(site);
        for (Iterator itor = reminderList.iterator(); itor.hasNext();) {
            SiteMailReminder reminder = (SiteMailReminder) itor.next();
            getHibernateTemplate().save(reminder);
        }
    }

    
    public List getSiteMailReminderList(Site site) {
        return getHibernateTemplate().find("from SiteMailReminder smr where smr.site.id=? order by smr.type",site.getId(),Hibernate.INTEGER);
    }

    public Set getEnabledSiteSetWithMailReminders() {
        List smrList= (List) getHibernateTemplate().execute( new HibernateCallback() {
            
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query q=session.createQuery("from SiteMailReminder smr where smr.site.enabled=?");
                q.setParameter(0,EnabledDisabled.ENABLED.getEnumCode());
                return q.list();
            }
        });
        Set retVal=new HashSet();
        for (Iterator iter = smrList.iterator(); iter.hasNext();) {
            SiteMailReminder smr = (SiteMailReminder) iter.next();
            Site site=smr.getSite();
            if(site.getMailReminders()==null) site.setMailReminders(new HashMap());
            site.getMailReminders().put(smr.getType(),smr);
            retVal.add(site);
        }
        return retVal;
    }
    
    
    

}
