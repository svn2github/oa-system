/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.dao.admin;

import java.util.List;

import net.sourceforge.dao.DAO;
import net.sourceforge.model.admin.Metadata;
import net.sourceforge.model.admin.MetadataDetail;
import net.sourceforge.model.metadata.MetadataDetailEnum;
import net.sourceforge.model.metadata.MetadataType;

/**
 * 定义操作Metadata的接口
 * @author ych
 * @version 1.0 (Nov 10, 2005)
 */
public interface MetadataDAO extends DAO {

    /**
     * 从数据库取得指定id的Metadata
     * 
     * @param id
     *            Metadata的id
     * @return 返回指定的Metadata
     */
    public Metadata getMetadata(Integer id);
    
    /**
     * 保存Metadata对象到数据库
     * 
     * @param metadata
     *            所要保存的Metadata
     * @return 返回保存的Metadata
     */
    public Metadata saveMetadata(Metadata metadata);

    /**
     * 保存MetadataType对象到数据库
     * 
     * @param mt
     *            所要保存的MetadataType
     * @return 返回保存的Metadata
     */
    public Metadata saveMetadata(MetadataType mt);

    /**
     * 从数据库取得指定id和type的MetadataDetail
     * 
     * @param id
     *            MetadataDetail的id
     * @param type
     *            MetadataDetail的type
     * @return 返回指定的MetadataDetail
     */
    public MetadataDetail getMetadataDetail(Integer id, Metadata type);

    /**
     * 从数据库取得指定id和typeId的MetadataDetail
     * 
     * @param id
     *            MetadataDetail的id
     * @param typeId
     *            MetadataDetail的type的id
     * @return
     * @throws Exception
     */
    public MetadataDetail getMetadataDetail(Integer id, Integer typeId);

    /**
     * 保存MetadataDetail
     * 
     * @param metadataDetail
     *            所要保存的MetadataDetail
     * @return 返回保存的MetadataDetail
     */
    public MetadataDetail saveMetadataDetail(MetadataDetail metadataDetail);

    /**
     * 保存 MetadataDetail
     * @param mde 所要保存的MetadataDetailEnum对象
     * @return 返回保存的MetadataDetail
     */
    public MetadataDetail saveMetadataDetail(MetadataDetailEnum mde);
    
    /**
     * 获得所有MetadataDetail的列表
     * @return 返回所有MetadataDetail列表
     */
    public List getAllMetadataDetail();
    
    /**
     *  获得所有Metadata的数量
     * @return 所有Metadata的数量
     */
    public int getMetadataListCount();

    /**
     * 返回Metadata对象列表
     * 
     * @param pageNo
     *            第几页，以pageSize为页的大小，pageSize为-1时忽略该参数
     * @param pageSize
     *            页的大小，-1表示不分页
     * @return 返回Metadata对象列表
     */
    public List getMetadataList(int pageNo,int pageSize);
    
}
