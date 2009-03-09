/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.dao.admin.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.aof.dao.BaseDAOHibernate;
import com.aof.dao.admin.MetadataDAO;
import com.aof.model.admin.Metadata;
import com.aof.model.admin.MetadataDetail;
import com.aof.model.admin.query.MetadataQueryOrder;
import com.aof.model.metadata.MetadataDetailEnum;
import com.aof.model.metadata.MetadataType;
import com.shcnc.hibernate.PersistentEnum;

/**
 * MetadataManagerDAOµÄHibernateÊµÏÖ 
 * @author ych
 * @version 1.0 (Nov 10, 2005)
 */
public class MetadataDAOHibernate extends BaseDAOHibernate implements MetadataDAO {
    private Log log = LogFactory.getLog(MetadataDAOHibernate.class);

    
    /* (non-Javadoc)
     * @see com.aof.dao.admin.MetadataDAO#getMetadata(java.lang.Integer)
     */
    public Metadata getMetadata(Integer id) {
        if (id == null) {
            if (log.isDebugEnabled()) log.debug("try to get Metadata with null id");
            return null;
        }
        return (Metadata) getHibernateTemplate().get(Metadata.class, id);
    }

    /* (non-Javadoc)
     * @see com.aof.dao.admin.MetadataDAO#saveMetadata(com.aof.model.admin.Metadata)
     */
    public Metadata saveMetadata(final Metadata metadata) {
        if (metadata == null) {
            throw new RuntimeException("try to save null metadata, it's not allowed.");
        }
        Integer id = metadata.getId();
        MetadataType mt = (MetadataType) PersistentEnum.fromEnumCode(MetadataType.class, id);
        if (mt == null) {
            throw new RuntimeException("try to create new metadata with id '" + id + "', it's not allowed.");
        }
        mt.setLabel(metadata.getDescription());
        return saveMetadata(mt);
    }
    
    /* (non-Javadoc)
     * @see com.aof.dao.admin.MetadataDAO#saveMetadata(com.aof.model.metadata.MetadataType)
     */
    public Metadata saveMetadata(final MetadataType mt) {
        if (mt == null) {
            if (log.isDebugEnabled()) log.debug("try to save/update null MetadataType");
            return null;
        }
        Metadata m = getMetadata((Integer)mt.getEnumCode());
        if (m == null) {
            m = new Metadata();
            m.setId((Integer)mt.getEnumCode());
            m.setDescription(mt.getLabel());
            getHibernateTemplate().save(m);
            if (log.isDebugEnabled()) log.debug("init data, insert Metadata '" + m.getId() + "' into table");
            return m;
        }
        m.setDescription(mt.getLabel());
        getHibernateTemplate().update(m);
        return m;
    }

    /* (non-Javadoc)
     * @see com.aof.dao.admin.MetadataDAO#getMetadataDetail(java.lang.Integer, com.aof.model.admin.Metadata)
     */
    public MetadataDetail getMetadataDetail(Integer id, Metadata type) {
        if (id == null) {
            if (log.isDebugEnabled()) log.debug("try to get MetadataDetail with null id");
            return null;
        }
        if (type == null && type.getId() == null) {
            if (log.isDebugEnabled()) log.debug("try to get MetadataDetail with null type");
            return null;
        }
        MetadataDetail md = new MetadataDetail(id, type);
        return (MetadataDetail) getHibernateTemplate().get(MetadataDetail.class, md);
    }

    /* (non-Javadoc)
     * @see com.aof.dao.admin.MetadataDAO#getMetadataDetail(java.lang.Integer, java.lang.Integer)
     */
    public MetadataDetail getMetadataDetail(Integer id, Integer typeId) {
        return getMetadataDetail(id, new Metadata(typeId));
    }

    /* (non-Javadoc)
     * @see com.aof.dao.admin.MetadataDAO#saveMetadataDetail(com.aof.model.admin.MetadataDetail)
     */
    public MetadataDetail saveMetadataDetail(MetadataDetail metadataDetail) {
        if (metadataDetail == null) {
            throw new RuntimeException("try to save null metadataDetail, it's not allowed.");
        }
        Integer id = metadataDetail.getId();
        Metadata type = metadataDetail.getType();
        if (type == null) {
            throw new RuntimeException("try to save metadataDetail with null type, it's not allowed.");
        }
        MetadataType mt = (MetadataType) PersistentEnum.fromEnumCode(MetadataType.class, type.getId());
        if (mt == null) {
            throw new RuntimeException("try to save metadataDetail with a unknow type id '" + type.getId() + "', it's not allowed.");
        }
        MetadataDetailEnum mde = (MetadataDetailEnum) PersistentEnum.fromEnumCode(mt.getDetailClass(), id);
        if (mde == null) {
            throw new RuntimeException("try to create new metadataDetail with id '" + id + "' and type id '" + type.getId() + "', it's not allowed.");
        }
        mde.setEngDescription(metadataDetail.getEngDescription());
        mde.setChnDescription(metadataDetail.getChnDescription());
        mde.setEngShortDescription(metadataDetail.getEngShortDescription());
        mde.setChnShortDescription(metadataDetail.getChnShortDescription());
        mde.setColor(metadataDetail.getColor().trim());
        return saveMetadataDetail(mde);
    }

    /* (non-Javadoc)
     * @see com.aof.dao.admin.MetadataDAO#saveMetadataDetail(com.aof.model.metadata.MetadataDetailEnum)
     */
    public MetadataDetail saveMetadataDetail(MetadataDetailEnum mde) {
        if (mde == null) {
            if (log.isDebugEnabled()) log.debug("try to save/update null MetadataDetailEnum");
            return null;
        }
        Integer id = (Integer)mde.getEnumCode();
        MetadataType type = mde.getType();
        Metadata m = getMetadata((Integer)type.getEnumCode());
        if (m == null) m = saveMetadata(type);
        MetadataDetail md = getMetadataDetail(id, m);
        String color = mde.getColor();
        if (md == null) {
            md = new MetadataDetail();
            md.setId(id);
            md.setType(m);
            md.setEngDescription(mde.getEngDescription());
            md.setChnDescription(mde.getChnDescription());
            md.setEngShortDescription(mde.getEngShortDescription());
            md.setChnShortDescription(mde.getChnShortDescription());
            md.setColor(color == null ? "" : color.trim());
            getHibernateTemplate().save(md);
            if (log.isDebugEnabled()) log.debug("init data, insert MetadataDetail '" + md.getId() + ", " + m.getId() + "' into table");
            return md;
        }
        md.setEngDescription(mde.getEngDescription());
        md.setChnDescription(mde.getChnDescription());
        md.setEngShortDescription(mde.getEngShortDescription());
        md.setChnShortDescription(mde.getChnShortDescription());
        md.setColor(color == null ? "" : color.trim());
        getHibernateTemplate().update(md);
        return md;
    }

    /* (non-Javadoc)
     * @see com.aof.dao.admin.MetadataDAO#getAllMetadataDetail()
     */
    public List getAllMetadataDetail() {
        return getHibernateTemplate().find("from MetadataDetail md");
    }

    private final static String SQL_COUNT = "select count(*) from Metadata metadata";
    
    private final static String SQL = "from Metadata metadata";
    
    private final static Object[][] QUERY_ORDERS = {
        { MetadataQueryOrder.ID, "metadata.id" },
    };
    
	/* (non-Javadoc)
	 * @see com.aof.dao.admin.MetadataDAO#getMetadataList(int, int)
	 */
	public List getMetadataList(int pageNo, int pageSize) {
		return getList(null, pageNo, pageSize,MetadataQueryOrder.ID, false, SQL, null, QUERY_ORDERS);
	}

	/* (non-Javadoc)
	 * @see com.aof.dao.admin.MetadataDAO#getMetadataListCount()
	 */
	public int getMetadataListCount() {
		return getListCount(null, SQL_COUNT, null);
	}
    
}
