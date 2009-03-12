/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.service.admin.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import net.sourceforge.dao.admin.DepartmentDAO;
import net.sourceforge.model.admin.Department;
import net.sourceforge.model.admin.Site;
import net.sourceforge.model.admin.query.DepartmentQueryCondition;
import net.sourceforge.model.admin.query.DepartmentQueryOrder;
import net.sourceforge.model.metadata.EnabledDisabled;
import net.sourceforge.service.BaseManager;
import net.sourceforge.service.admin.DepartmentManager;
import com.shcnc.utils.XmlBuilder;

/**
 * DepartmentManager的实现
 * @author nicebean
 * @version 1.0 (2005-11-14)
 */
public class DepartmentManagerImpl extends BaseManager implements DepartmentManager {

    private DepartmentDAO dao;

    /**
     * 设置DepartmentDAO给dao属性
     * @param dao DepartmentDAO对象
     */
    public void setDepartmentDAO(DepartmentDAO dao) {
        this.dao = dao;
    }

    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.DepartmentManager#getDepartment(java.lang.Integer)
     */
    public Department getDepartment(Integer id)  {
        return dao.getDepartment(id);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.DepartmentManager#saveDepartment(net.sourceforge.model.admin.Department)
     */
    public Department saveDepartment(Department department)  {
        department = dao.saveDepartment(department);
        EnabledDisabled enabled = department.getEnabled();

        if (EnabledDisabled.ENABLED.equals(enabled)) {
            /*
             * 保存的部门状态为可用，则更新上级部门的状态为可用
             */
            Department d = department.getParentDepartment();
            if (d != null) {
                d = dao.getDepartment(d.getId());
                while (d != null && !EnabledDisabled.ENABLED.equals(d.getEnabled())) {
                    d.setEnabled(EnabledDisabled.ENABLED);
                    dao.saveDepartment(d);
                    d = d.getParentDepartment();
                }
            }
        } else {
            /*
             * 保存的部门状态为不可用，则更新所有下级部门的状态为不可用
             */
            Map conditions = new HashMap();
            conditions.put(DepartmentQueryCondition.SITE_EQ, department.getSite().getId());
            conditions.put(DepartmentQueryCondition.ENABLED_EQ, EnabledDisabled.ENABLED.getEnumCode());
            List departmentList = dao.getDepartmentList(conditions, 0, -1, null, false);
            HashMap parentChildrenMap = new HashMap();
            Iterator itor = departmentList.iterator();
            while (itor.hasNext()) {
                Department d = (Department) itor.next();
                Department pd = d.getParentDepartment();
                if (pd != null) {
                    Integer pid = pd.getId();
                    List childList = (List) parentChildrenMap.get(pid);
                    if (childList == null) {
                        childList = new ArrayList();
                        parentChildrenMap.put(pid, childList);
                    }
                    childList.add(d);
                }
            }
            setChildrenDisabled(department, parentChildrenMap);
            
        }
        return department;
    }

    private void setChildrenDisabled(Department parent, Map parentChildrenMap) {
        List children = (List) parentChildrenMap.get(parent.getId());
        if (children == null) return;
        for (int i = 0; i < children.size(); i++) {
            Department child = (Department) children.get(i);
            if (EnabledDisabled.DISABLED.equals(child.getEnabled())) continue;
            child.setEnabled(EnabledDisabled.DISABLED);
            dao.saveDepartment(child);
            setChildrenDisabled(child, parentChildrenMap);
        }
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.DepartmentManager#removeDepartment(java.lang.Integer)
     */
    public void removeDepartment(Integer id)  {
        dao.removeDepartment(id);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.DepartmentManager#getDepartmentListCount(java.util.Map)
     */
    public int getDepartmentListCount(Map conditions)  {
        return dao.getDepartmentListCount(conditions);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.DepartmentManager#getDepartmentList(java.util.Map, int, int, net.sourceforge.model.admin.query.DepartmentQueryOrder, boolean)
     */
    public List getDepartmentList(Map conditions, int pageNo, int pageSize, DepartmentQueryOrder order, boolean descend)
             {
        return dao.getDepartmentList(conditions, pageNo, pageSize, order, descend);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.DepartmentManager#getDepartmentXmlBySite(java.lang.Integer)
     */
    public String getDepartmentXmlBySite(Integer siteId)  {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
        Document doc = builder.newDocument();

        Map conditions = new HashMap();
        conditions.put(DepartmentQueryCondition.SITE_EQ, siteId);
        List departmentList = dao.getDepartmentList(conditions, 0, -1, null, false);
        List rootList = new ArrayList();
        HashMap departmentItems = new HashMap();
        Iterator itor = departmentList.iterator();
        while (itor.hasNext()) {
            Department d = (Department) itor.next();
            departmentItems.put(d.getId(), d);
        }
        itor = departmentList.iterator();
        while (itor.hasNext()) {
            Department d = (Department) itor.next();
            d = (Department) departmentItems.get(d.getId());
            Department pd = d.getParentDepartment();
            if (pd == null) {
                rootList.add(d);
            } else {
                pd = (Department) departmentItems.get(pd.getId());
                pd.addChild(d);
            }
        }
        Element tree = doc.createElement("tree");

        buildTree(rootList, doc, tree);
        doc.appendChild(tree);
        XmlBuilder xmlBuilder = new XmlBuilder();
        xmlBuilder.setXmlHeader("");
        return xmlBuilder.printDOMTree(doc);
    }

    private void buildTree(List roots, Document doc, Node parent) {
        int len = roots.size();
        for (int i = 0; i < len; i++) {
            Element e = null;
            Department d = (Department) (roots.get(i));
            Integer id = d.getId();
            List children = d.getChildren();
            if (children == null || children.isEmpty()) {
                e = doc.createElement("leaf");
            } else {
                e = doc.createElement("branch");
                buildTree(children, doc, e);
            }
            e.setAttribute("id", id.toString());
            e.setAttribute("desc", d.getName());
            parent.appendChild(e);
        }
    }

    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.DepartmentManager#fillDepartment(net.sourceforge.model.admin.Site)
     */
    public void fillDepartment(Site site, boolean onlyEnabled)  {
        List l = new ArrayList();
        l.add(site);
        this.fillDepartment(l, onlyEnabled);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.service.admin.DepartmentManager#fillDepartment(java.util.List)
     */
    public void fillDepartment(List siteList, boolean onlyEnabled)  {
        dao.fillSiteDepartment(siteList, onlyEnabled);
        for (Iterator iter = siteList.iterator(); iter.hasNext();) {
            Site site = (Site) iter.next();
            site.setDepartments(buildList(site.getDepartments()));
        }
    }

    private void buildList(List resultList, List rootList, HashMap departmentItems, int indent) {
        if (rootList == null)
            return;
        String strIndent = getIndenetString(indent);
        for (int i = 0; i < rootList.size(); i++) {
            Department d = (Department) rootList.get(i);
            d.setIndentName(strIndent + d.getName());
            resultList.add(d);
            List l = (List) departmentItems.get(d.getId());
            buildList(resultList, l, departmentItems, indent + 1);
        }
    }

    private String getIndenetString(int indent) {
        StringBuffer space = new StringBuffer();
        while (indent-- > 0)
            space.append("　");
        return space.toString();
    }

    private List buildList(List departmentList) {
        List rootList = new ArrayList();
        HashMap departmentItems = new HashMap();
        Iterator itor = departmentList.iterator();
        while (itor.hasNext()) {
            Department d = (Department) itor.next();
            Department pd = d.getParentDepartment();
            if (pd == null) {
                rootList.add(d);
            } else {
                List l = (List) departmentItems.get(pd.getId());
                if (l == null)
                    l = new ArrayList();
                l.add(d);
                departmentItems.put(pd.getId(), l);
            }
        }
        List resultList = new ArrayList();
        buildList(resultList, rootList, departmentItems, 0);
        return resultList;
    }

    public List getEnabledDepartmentTreeOfSite(Site site) {
        List siteList=new ArrayList();
        siteList.add(site);
        this.fillDepartment(siteList,true);
        return site.getDepartments(); 
    }

}
