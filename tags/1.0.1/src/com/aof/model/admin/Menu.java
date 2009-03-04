/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

/*
 * Created Thu Sep 22 17:05:58 CST 2005 by MyEclipse Hibernate Tool.
 */
package com.aof.model.admin;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;


/**
 * A class that represents a row in the 't_menu' table. This class may be
 * customized as it is never re-generated after being created.
 */
public class Menu extends AbstractMenu implements Serializable {
    /**
     * Simple constructor of Menu instances.
     */
    public Menu() {
    }

    /**
     * Constructor of Menu instances given a simple primary key.
     * 
     * @param id
     */
    public Menu(java.lang.Integer id) {
        super(id);
    }

    /* Add customized code below */
    
    /**
     * 实现Menu对象间的排序规则，目前以根据ID升序排
     */
    public final static Comparator MENU_COMPARATOR = new Comparator() {

        public int compare(Object o1, Object o2) {
            if (!(o1 instanceof Menu && o2 instanceof Menu)) return 0;
            Menu m1 = (Menu) o1;
            Menu m2 = (Menu) o2;
            if (m1 == null) return -1;
            if (m2 == null) return 1;
            return m1.getId().compareTo(m2.getId());
        }
        
    };
    
    private Set children;
    
    public Set getChildren() {
        return children;
    }
    
    public void addChild(Menu m) {
        if (children == null) children = new TreeSet(MENU_COMPARATOR);
        children.add(m);
    }
    
}
