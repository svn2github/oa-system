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

import net.sourceforge.model.admin.SupplierContract;
import net.sourceforge.model.admin.query.SupplierContractQueryOrder;

/**
 * 定义操作SupplierContract的接口
 * 
 * @author ych
 * @version 1.0 (Nov 13, 2005)
 */
public interface SupplierContractDAO {

    /**
     * 从数据库取得指定id的SupplierContract
     * 
     * @param id
     *            SupplierContract的id
     * @return 返回指定的SupplierContract
     */
    public SupplierContract getSupplierContract(Integer id);

    /**
     * 返回符合查询条件的SupplierContract对象个数
     * 
     * @param conditions
     *            包含查询条件到条件值映射的Map，其中查询条件应该来自SupplierContractQueryCondition类的预定义常量
     * @return 符合查询条件的SupplierContract对象个数
     */
    public int getSupplierContractListCount(Map conditions);

    /**
     * 返回符合查询条件的SupplierContract对象列表
     * 
     * @param conditions
     *            包含查询条件到条件值映射的Map，其中查询条件应该来自SupplierContractQueryCondition类的预定义常量
     * @param pageNo
     *            第几页，以pageSize为页的大小，pageSize为-1时忽略该参数
     * @param pageSize
     *            页的大小，-1表示不分页
     * @param order
     *            排序条件，null表示不排序
     * @param descend
     *            false表示升序，true表示降序
     * @return 符合查询条件的SupplierContract对象列表
     */
    public List getSupplierContractList(Map conditions, int pageNo, int pageSize, SupplierContractQueryOrder order, boolean descend);

    /**
     * 插入指定的SupplierContract对象到数据库
     * 
     * @param supplierContract
     *            要保存的SupplierContract对象
     * @return 保存后的SupplierContract对象
     */
    public SupplierContract insertSupplierContract(SupplierContract supplierContract);

    /**
     * 更新指定的SupplierContract对象到数据库
     * 
     * @param supplierContract
     *            要更新的SupplierContract对象
     * @return 更新后的SupplierContract对象
     */
    public SupplierContract updateSupplierContract(SupplierContract supplierContract);

    /**
     * 保存指定id的SupplierContract文件内容
     * 
     * @param id
     *            SupplierContract的id
     * @param inputStream
     *            SupplierContract文件输入流
     */
    public void saveSupplierContractContent(Integer id, InputStream inputStream);

    /**
     * 返回指定id的SupplierContract文件输入流
     * 
     * @param id
     *            SupplierContract的id
     * @return 指定id的SupplierContract文件输入流
     */
    public InputStream getSupplierContractContent(Integer id);
}
