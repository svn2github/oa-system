/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.dao.admin.hibernate;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sourceforge.dao.BaseDAOHibernate;
import net.sourceforge.dao.admin.SupplierContractDAO;
import net.sourceforge.model.admin.SupplierContract;
import net.sourceforge.model.admin.SupplierContractContent;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.orm.hibernate.HibernateCallback;

import net.sourceforge.model.admin.query.SupplierContractQueryCondition;
import net.sourceforge.model.admin.query.SupplierContractQueryOrder;

/**
 * SupplierContractDAOµÄHibernateÊµÏÖ 
 * @author ych
 * @version 1.0 (Nov 13, 2005)
 */
public class SupplierContractDAOHibernate extends BaseDAOHibernate implements SupplierContractDAO {
    private Log log = LogFactory.getLog(SupplierContractDAOHibernate.class);


    public SupplierContract getSupplierContract(Integer id){
        if (id == null) {
            if (log.isDebugEnabled()) log.debug("try to get SupplierContract with null id");
            return null;
        }
        return (SupplierContract) getHibernateTemplate().get(SupplierContract.class, id);
    }

    public SupplierContract updateSupplierContract(SupplierContract supplierContract) {
        Integer id = supplierContract.getId();
        if (id == null) {
            throw new RuntimeException("cannot save a supplierContract with null id");
        }
        SupplierContract oldSupplierContract = getSupplierContract(id);
        if (oldSupplierContract != null) {
        	try{
                PropertyUtils.copyProperties(oldSupplierContract,supplierContract);
        	}
        	catch(Exception e)
        	{
                throw new RuntimeException("copy error£º"+e);
        	}
        	getHibernateTemplate().update(oldSupplierContract);	
        	return oldSupplierContract;
        }
        else
        {
        	throw new RuntimeException("supplierContract not found");
        }
    }

	public SupplierContract insertSupplierContract(SupplierContract supplierContract) {
       	getHibernateTemplate().save(supplierContract);
       	return supplierContract;
    }

    private final static String SQL_COUNT = "select count(*) from SupplierContract supplierContract";

    private final static String SQL = "from SupplierContract supplierContract";

    private final static Object[][] QUERY_CONDITIONS = {
		{ SupplierContractQueryCondition.ID_EQ, "supplierContract.id = ?", null },
		{ SupplierContractQueryCondition.SUPPLIER_ID_EQ, "supplierContract.Supplier.id = ?", null },
    };
    
    private final static Object[][] QUERY_ORDERS = {
        { SupplierContractQueryOrder.ID, "supplierContract.id" },
        { SupplierContractQueryOrder.FILESIZE, "supplierContract.fileSize" },
        { SupplierContractQueryOrder.FILENAME, "supplierContract.fileName" },
        { SupplierContractQueryOrder.DESCRIPTION, "supplierContract.description" },
        { SupplierContractQueryOrder.UPLOADDATE, "supplierContract.uploadDate" },
    };
    
    public int getSupplierContractListCount(final Map conditions) {
        return getListCount(conditions, SQL_COUNT, QUERY_CONDITIONS);
    }

    public List getSupplierContractList(final Map conditions, final int pageNo, final int pageSize, final SupplierContractQueryOrder order, final boolean descend) {
        return getList(conditions, pageNo, pageSize, order, descend, SQL, QUERY_CONDITIONS, QUERY_ORDERS);
    }


	public void saveSupplierContractContent(final Integer id, final InputStream inputStream) {
        getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                SupplierContractContent scc = (SupplierContractContent) session.get(SupplierContractContent.class, id);
                if (scc == null) return null;
                try {
                    Blob content = Hibernate.createBlob(inputStream);
                    scc.setFileContent(content);
                    session.update(scc);
                } catch (IOException e) {
                    throw new HibernateException(e);
                }
                return null;
            }

        });
    }

    public InputStream getSupplierContractContent(final Integer id) {
        return (InputStream) getHibernateTemplate().execute( new HibernateCallback() {
            
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                SupplierContractContent scc = (SupplierContractContent) session.get(SupplierContractContent.class, id);
                if (scc == null) return null;
                Blob fileContent = scc.getFileContent();
                if (fileContent == null) return null;
                try {
                    return preRead(fileContent.getBinaryStream());
                } catch (IOException e) {
                    throw new HibernateException(e);
                }
            }
            
        });
    }
    

}
