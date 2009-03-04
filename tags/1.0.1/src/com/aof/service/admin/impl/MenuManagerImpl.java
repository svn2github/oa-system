/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.service.admin.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.aof.dao.admin.MenuDAO;
import com.aof.model.admin.Menu;
import com.aof.model.admin.query.MenuQueryOrder;
import com.aof.service.BaseManager;
import com.aof.service.admin.MenuManager;
import com.shcnc.utils.XmlBuilder;

/**
 * MenuManager的实现
 * 
 * @author nicebean
 * @version 1.0 (2005-11-14)
 */
public class MenuManagerImpl extends BaseManager implements MenuManager {

    private MenuDAO dao;

    /**
     * 设置MenuDAO给dao属性
     * 
     * @param dao
     *            MenuDAO对象
     */
    public void setMenuDAO(MenuDAO dao) {
        this.dao = dao;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.service.admin.MenuManager#getMenu(java.lang.Integer)
     */
    public Menu getMenu(Integer id) {
        return dao.getMenu(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.service.admin.MenuManager#saveMenu(com.aof.model.admin.Menu)
     */
    public Menu saveMenu(Menu menu) {
        return dao.saveMenu(menu);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.service.admin.MenuManager#removeMenu(java.lang.Integer)
     */
    public void removeMenu(Integer id) {
        dao.removeMenu(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.service.admin.MenuManager#getMenuListCount(java.util.Map)
     */
    public int getMenuListCount(Map conditions) {
        return dao.getMenuListCount(conditions);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.service.admin.MenuManager#getMenuList(java.util.Map, int,
     *      int, com.aof.model.admin.query.MenuQueryOrder, boolean)
     */
    public List getMenuList(Map conditions, int pageNo, int pageSize, MenuQueryOrder order, boolean descend) {
        return dao.getMenuList(conditions, pageNo, pageSize, order, descend);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aof.service.admin.MenuManager#getMenuXml(java.util.Locale)
     */
    public String getMenuXml(Locale locale) {
        /*
         * 构造Menu树。依赖Hibernate保证对象唯一性(几个menu如果有相同的parentMenu，getParentMenu将返回同一个对象)
         */
        Set rootSet = new TreeSet(Menu.MENU_COMPARATOR);

        for (Iterator itor = dao.getMenuList(null, 0, -1, null, false).iterator(); itor.hasNext();) {
            Menu m = (Menu) itor.next();
            Menu pm = m.getParentMenu();
            if (pm == null) {
                rootSet.add(m);
            } else {
                pm.addChild(m);
            }
        }

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
        Document doc = builder.newDocument();

        Element tree = doc.createElement("tree");

        buildTree(rootSet, doc, tree, locale);
        doc.appendChild(tree);
        XmlBuilder xmlBuilder = new XmlBuilder();
        xmlBuilder.setXmlHeader("");
        return xmlBuilder.printDOMTree(doc);
    }

    private void buildTree(Set roots, Document doc, Node parent, Locale locale) {
        for (Iterator itor = roots.iterator(); itor.hasNext();) {
            Element e = null;
            Menu m = (Menu) itor.next();
            Integer id = m.getId();
            Set children = m.getChildren();
            if (children == null || children.isEmpty()) {
                e = doc.createElement("leaf");
            } else {
                e = doc.createElement("branch");
                buildTree(children, doc, e, locale);
            }
            e.setAttribute("id", id.toString());
            e.setAttribute("desc", Locale.ENGLISH.equals(locale) ? m.getName() : m.getSecondName());
            parent.appendChild(e);
        }
    }

}
