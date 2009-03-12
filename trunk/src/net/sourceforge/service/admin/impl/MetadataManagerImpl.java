/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.service.admin.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sourceforge.dao.admin.MetadataDAO;
import net.sourceforge.model.admin.Metadata;
import net.sourceforge.model.admin.MetadataDetail;
import net.sourceforge.model.metadata.MetadataDetailEnum;
import net.sourceforge.model.metadata.MetadataType;
import net.sourceforge.service.BaseManager;
import net.sourceforge.service.admin.MetadataManager;
import com.shcnc.hibernate.PersistentEnum;

/**
 * MetadataManager的实现类
 * @author ych
 * @version 1.0 (Nov 10, 2005)
 */
public class MetadataManagerImpl extends BaseManager implements MetadataManager {

    private MetadataDAO dao;

    /**
     * @param dao
     */
    public void setMetadataDAO(MetadataDAO dao) {
        this.dao = dao;
    }

    
    /**
     * initial metadata manager bean
     */
    public void init()  {
        Map metadatas = new HashMap();
        Map metadataDetails = new HashMap();
        for (Iterator itor = dao.getAllMetadataDetail().iterator(); itor.hasNext(); ) {
            MetadataDetail md = (MetadataDetail) itor.next();
            Metadata m = md.getType();
            Integer metadataId = m.getId();
            Map mm = (Map) metadataDetails.get(metadataId);
            if (mm == null) {
                metadatas.put(metadataId, m);
                mm = new HashMap();
                metadataDetails.put(metadataId, mm);
            }
            mm.put(md.getId(), md);
        }
        
        Iterator itor = PersistentEnum.getEnumList(MetadataType.class).iterator();
        while (itor.hasNext()) {
            MetadataType mt = (MetadataType)itor.next();
            Metadata m = (Metadata) metadatas.get(mt.getEnumCode());
            Map mm = (Map) metadataDetails.get(mt.getEnumCode());
            if (m == null) {
                mt.setLabel(mt.getDefaultLabel());
                dao.saveMetadata(mt);
            } else {
                mt.setLabel(m.getDescription());
            }
            Iterator itor2 = PersistentEnum.getEnumList(mt.getDetailClass()).iterator();
            while (itor2.hasNext()) {
                MetadataDetailEnum mde = (MetadataDetailEnum)itor2.next();
                MetadataDetail md = (MetadataDetail) (mm == null ? null : mm.get(mde.getEnumCode()));
                if (md == null) {
                    String desc = mde.getLabel(); 
                    mde.setEngDescription(desc);
                    mde.setChnDescription(desc);
                    mde.setEngShortDescription(desc);
                    mde.setChnShortDescription(desc);
                    mde.setColor(null);
                    dao.saveMetadataDetail(mde);
                } else {
                    mde.setEngDescription(md.getEngDescription());
                    mde.setChnDescription(md.getChnDescription());
                    mde.setEngShortDescription(md.getEngShortDescription());
                    mde.setChnShortDescription(md.getChnShortDescription());
                    mde.setColor(md.getColor());
                }
            }
        }
    }

    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.MetadataManager#getMetadata(java.lang.Integer)
     */
    public Metadata getMetadata(Integer id)  {
        return dao.getMetadata(id);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.MetadataManager#saveMetadata(net.sourceforge.model.admin.Metadata)
     */
    public Metadata saveMetadata(Metadata metadata)  {
        return dao.saveMetadata(metadata);
    }

    
    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.MetadataManager#saveMetadata(net.sourceforge.model.metadata.MetadataType)
     */
    public Metadata saveMetadata(MetadataType metadataType)  {
		return dao.saveMetadata(metadataType);
	}


	/* (non-Javadoc)
	 * @see net.sourceforge.service.admin.MetadataManager#getMetadataDetail(java.lang.Integer, net.sourceforge.model.admin.Metadata)
	 */
	public MetadataDetail getMetadataDetail(Integer id, Metadata type)  {
        return dao.getMetadataDetail(id, type);
    }


    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.MetadataManager#getMetadataDetail(java.lang.Integer, java.lang.Integer)
     */
    public MetadataDetail getMetadataDetail(Integer id, Integer typeId)  {
        return dao.getMetadataDetail(id, typeId);
    }


    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.MetadataManager#saveMetadataDetail(net.sourceforge.model.admin.MetadataDetail)
     */
    public MetadataDetail saveMetadataDetail(MetadataDetail metadataDetail)  {
        return dao.saveMetadataDetail(metadataDetail);
    }


	/* (non-Javadoc)
	 * @see net.sourceforge.service.admin.MetadataManager#getMetadataList(int, int)
	 */
	public List getMetadataList(int pageNo, int pageSize) {
		return dao.getMetadataList(pageNo,pageSize);
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.service.admin.MetadataManager#getMetadataListCount()
	 */
	public int getMetadataListCount() {
		return dao.getMetadataListCount();
	}

    
}
