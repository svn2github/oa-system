/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.dao;

/**
 * 定义DAO应该提供的方法
 * 
 * @author nicebean
 * @version 1.0 (2005-11-11)
 */
public interface DAO {

    /**
     * 从数据库重新获取指定的对象
     * 
     * @param o
     *            需要重新获取的对象
     * @return 重新获取的对象
     */
    public Object refreshObject(Object o);

    /**
     * 保存对象到数据库 - insert
     * 
     * @param o
     *            要保存的对象
     */
    public void saveObject(Object o);

    /**
     * 更新对象到数据库 - update
     * 
     * @param o
     *            要更新的对象
     */
    public void updateObject(Object o);

    /**
     * 从数据库删除对象
     * 
     * @param o
     *            要删除的对象
     */
    public void removeObject(Object o);
}
