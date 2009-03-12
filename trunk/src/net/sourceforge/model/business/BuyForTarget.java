/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.business;

import net.sourceforge.model.admin.Department;
import net.sourceforge.model.admin.User;

/**
 * 支持设置buy for对象的对象应该实现该接口
 * 
 * @author nicebean
 * @version 1.0 (2005-12-12)
 */
public interface BuyForTarget extends Rechargeable {

    /**
     * 返回对象的buyForUser属性
     * 
     * @return buyForUser属性
     */
    public User getBuyForUser();

    /**
     * 设置对象的buyForUser属性
     * 
     * @param buyForUser
     *            buyForUser属性
     */
    public void setBuyForUser(User buyForUser);

    /**
     * 返回对象的buyForDepartment属性
     * 
     * @return buyForDepartment属性
     */
    public Department getBuyForDepartment();

    /**
     * 设置对象的buyForDepartment属性
     * 
     * @param buyForDepartment
     *            buyForDepartment属性
     */
    public void setBuyForDepartment(Department buyForDepartment);

}
