/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.dao.admin.hibernate;

import java.util.List;

import net.sourceforge.dao.BaseDAOHibernate;
import net.sourceforge.dao.admin.GlobalDAO;
import net.sourceforge.model.admin.GlobalParameter;

public class GlobalDAOHibernate extends BaseDAOHibernate implements GlobalDAO {
    
	public GlobalParameter getParameter() {
		List ls=getHibernateTemplate().find("from GlobalParameter");
		if (ls.size()==0){
			GlobalParameter para=new GlobalParameter();
			getHibernateTemplate().save(para);
			return para;
		}
		return (GlobalParameter) ls.get(0);
	}

	public GlobalParameter saveUser(GlobalParameter para) {
        getHibernateTemplate().saveOrUpdate(para);
        return para;
	}

}