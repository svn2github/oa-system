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

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate.HibernateCallback;

import net.sourceforge.dao.BaseDAOHibernate;
import net.sourceforge.dao.admin.HotelContractDAO;
import net.sourceforge.dao.convert.LikeConvert;
import net.sourceforge.model.admin.HotelContract;
import net.sourceforge.model.admin.HotelContractContent;
import net.sourceforge.model.admin.query.HotelContractQueryCondition;
import net.sourceforge.model.admin.query.HotelContractQueryOrder;

/**
 * hibernate implement for HotelContractDAO
 * @author shilei
 * @version 1.0 (Nov 15, 2005)
 */
public class HotelContractDAOHibernate extends BaseDAOHibernate implements
		HotelContractDAO {
	private Log log = LogFactory.getLog(HotelContractDAOHibernate.class);

	/* (non-Javadoc)
	 * @see net.sourceforge.dao.admin.HotelContractDAO#getHotelContract(java.lang.Integer)
	 */
	public HotelContract getHotelContract(Integer id) {
		if (id == null) {
			if (log.isDebugEnabled())
				log.debug("try to get HotelContract with null id");
			return null;
		}
		return (HotelContract) getHibernateTemplate().get(HotelContract.class,
				id);
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.dao.admin.HotelContractDAO#updateHotelContract(net.sourceforge.model.admin.HotelContract)
	 */
	public HotelContract updateHotelContract(HotelContract hotelContract) {
		Integer id = hotelContract.getId();
		if (id == null) {
			throw new RuntimeException(
					"cannot save a hotelContract with null id");
		}
		HotelContract oldHotelContract = getHotelContract(id);
		if (oldHotelContract != null) {
			try {
                PropertyUtils.copyProperties(oldHotelContract, hotelContract);
			} catch (Exception e) {
                throw new RuntimeException("copy error£º"+e);
			}
			getHibernateTemplate().update(oldHotelContract);
			return oldHotelContract;
		} else {
			throw new RuntimeException("hotelContract not found");
		}
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.dao.admin.HotelContractDAO#insertHotelContract(net.sourceforge.model.admin.HotelContract)
	 */
	public HotelContract insertHotelContract(HotelContract hotelContract) {
		getHibernateTemplate().save(hotelContract);
		return hotelContract;
	}

	private final static String SQL_COUNT = "select count(*) from HotelContract hotelContract";

	private final static String SQL = "from HotelContract hotelContract";

	private final static Object[][] QUERY_CONDITIONS = {
			/* id */
			{ HotelContractQueryCondition.ID_EQ, "hotelContract.id = ?", null },

			/* keyPropertyList */

			/* kmtoIdList */

			/* mtoIdList */
			{ HotelContractQueryCondition.HOTEL_ID_EQ,
					"hotelContract.hotel.id = ?", null },

			/* property */
			{ HotelContractQueryCondition.FILENAME_LIKE,
					"hotelContract.fileName like ?", new LikeConvert() },
			{ HotelContractQueryCondition.DESCRIPTION_LIKE,
					"hotelContract.description like ?", new LikeConvert() },
			{ HotelContractQueryCondition.FILECONTENT_EQ,
					"hotelContract.fileContent = ?", null },
			{ HotelContractQueryCondition.UPLOADDATE_EQ,
					"hotelContract.uploadDate = ?", null }, };

	private final static Object[][] QUERY_ORDERS = {
			/* id */
			{ HotelContractQueryOrder.ID, "hotelContract.id" },

			/* property */
			{ HotelContractQueryOrder.FILENAME, "hotelContract.fileName" },
			{ HotelContractQueryOrder.DESCRIPTION, "hotelContract.description" },
			{ HotelContractQueryOrder.FILECONTENT, "hotelContract.fileContent" },
			{ HotelContractQueryOrder.UPLOADDATE, "hotelContract.uploadDate" }, };

	/* (non-Javadoc)
	 * @see net.sourceforge.dao.admin.HotelContractDAO#getHotelContractListCount(java.util.Map)
	 */
	public int getHotelContractListCount(final Map conditions) {
		return getListCount(conditions, SQL_COUNT, QUERY_CONDITIONS);
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.dao.admin.HotelContractDAO#getHotelContractList(java.util.Map, int, int, net.sourceforge.model.admin.query.HotelContractQueryOrder, boolean)
	 */
	public List getHotelContractList(final Map conditions, final int pageNo,
			final int pageSize, final HotelContractQueryOrder order,
			final boolean descend) {
		return getList(conditions, pageNo, pageSize, order, descend, SQL,
				QUERY_CONDITIONS, QUERY_ORDERS);
	}

    /* (non-Javadoc)
     * @see net.sourceforge.dao.admin.HotelContractDAO#saveHotelContractContent(java.lang.Integer, java.io.InputStream)
     */
    public void saveHotelContractContent(final Integer id, final InputStream inputStream) {
        getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                HotelContractContent hcc = (HotelContractContent) session.get(HotelContractContent.class, id);
                if (hcc == null) return null;
                try {
                    Blob content = Hibernate.createBlob(inputStream);
                    hcc.setFileContent(content);
                    session.update(hcc);
                } catch (IOException e) {
                    throw new HibernateException(e);
                }
                return null;
            }

        });
    }

    /* (non-Javadoc)
     * @see net.sourceforge.dao.admin.HotelContractDAO#getHotelContractContent(java.lang.Integer)
     */
    public InputStream getHotelContractContent(final Integer id) {
        return (InputStream) getHibernateTemplate().execute( new HibernateCallback() {
            
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                HotelContractContent hcc = (HotelContractContent) session.get(HotelContractContent.class, id);
                if (hcc == null) return null;
                Blob fileContent = hcc.getFileContent();
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
