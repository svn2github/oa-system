/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.dao.admin;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import net.sourceforge.dao.DAO;
import net.sourceforge.model.admin.Site;
import net.sourceforge.model.admin.query.SiteQueryOrder;

/**
 * 定义操作Site的接口
 * 
 * @author nicebean
 * @version 1.0 (2005-11-11)
 */
public interface SiteDAO extends DAO {

    /**
     * 从数据库取得指定id的Site对象
     * 
     * @param id
     *            Site对象的id
     * @return 返回指定的Site对象
     */
    public Site getSite(Integer id);

    /**
     * 保存Site对象到数据库
     * 
     * @param site
     *            要保存的Site对象
     * @return 返回保存后的Site对象
     */
    public Site saveSite(final Site site);

    /**
     * 从数据库删除指定id的Site对象
     * 
     * @param id
     *            Site对象的id
     */
    public void removeSite(Integer id);

    /**
     * 返回符合查询条件的Site对象个数
     * 
     * @param conditions
     *            包含查询条件到条件值映射的Map，其中查询条件应该来自SiteQueryCondition类的预定义常量
     * @return 符合查询条件的Site对象个数
     */
    public int getSiteListCount(Map conditions);

    /**
     * 返回符合查询条件的Site对象列表
     * 
     * @param conditions
     *            包含查询条件到条件值映射的Map，其中查询条件应该来自SiteQueryCondition类的预定义常量
     * @param pageNo
     *            第几页，以pageSize为页的大小，pageSize为-1时忽略该参数
     * @param pageSize
     *            页的大小，-1表示不分页
     * @param order
     *            排序条件，null表示不排序
     * @param descend
     *            false表示升序，true表示降序
     * @return 符合查询条件的Site对象列表
     */
    public List getSiteList(Map conditions, int pageNo, int pageSize, SiteQueryOrder order, boolean descend);

    /**
     * 查询指定id的Site对象是否有logo
     * 
     * @param id
     *            Site对象的id
     * @return 返回true表示有logo，false没有logo
     */
    public boolean isSiteHasLogo(Integer id);

    /**
     * 取得读取指定id的Site对象logo数据的InputStream
     * 
     * @param id
     *            Site对象的id
     * @return 返回该Site对象logo的InputStream，如该Site对象没有logo，则返回null
     */
    public InputStream getSiteLogo(Integer id);

    /**
     * 保存指定id的Site对象的logo
     * 
     * @param id
     *            Site对象的id
     * @param in
     *            可以读取logo数据的InputStream
     */
    public void saveSiteLogo(Integer id, InputStream in);
    
    /**
     * get enabeld site list
     * @return
     */
    public List getEnabledSiteList();

}
