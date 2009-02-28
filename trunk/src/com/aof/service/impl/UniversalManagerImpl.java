package com.aof.service.impl;

import java.io.Serializable;

import com.aof.dao.UniversalDAO;
import com.aof.service.UniversalManager;

public class UniversalManagerImpl implements UniversalManager {

    private UniversalDAO universalDAO;

    public Object load(Class clazz, Serializable idValue) {
        return universalDAO.load(clazz, idValue);
    }

    public void setUniversalDAO(UniversalDAO universalDAO) {
        this.universalDAO = universalDAO;
    }

}
