package net.sourceforge.service.impl;

import java.io.Serializable;

import net.sourceforge.dao.UniversalDAO;
import net.sourceforge.service.UniversalManager;

public class UniversalManagerImpl implements UniversalManager {

    private UniversalDAO universalDAO;

    public Object load(Class clazz, Serializable idValue) {
        return universalDAO.load(clazz, idValue);
    }

    public void setUniversalDAO(UniversalDAO universalDAO) {
        this.universalDAO = universalDAO;
    }

}
