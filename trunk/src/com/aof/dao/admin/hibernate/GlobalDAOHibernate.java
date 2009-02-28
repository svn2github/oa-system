/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.dao.admin.hibernate;

import java.util.List;

import com.aof.dao.BaseDAOHibernate;
import com.aof.dao.admin.GlobalDAO;
import com.aof.model.admin.GlobalParameter;

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