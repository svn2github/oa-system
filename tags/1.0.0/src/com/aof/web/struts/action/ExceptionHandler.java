/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

/*
 * Created on 2005-8-15
 *
 */
package com.aof.web.struts.action;

import java.util.HashSet;
import java.util.Set;

/**
 * @author nicebean
 *
 */
public class ExceptionHandler extends com.shcnc.struts.action.ExceptionHandler {
    private Set setDialogActionPath;
    
    public ExceptionHandler() {
        setDialogActionPath = new HashSet();
        setDialogActionPath.add("/newUser");
        setDialogActionPath.add("/insertUser");
        setDialogActionPath.add("/editUser");
        setDialogActionPath.add("/updateUser");
        setDialogActionPath.add("/editFunction");
        setDialogActionPath.add("/updateFunction");
        setDialogActionPath.add("/newCurrency");
        setDialogActionPath.add("/insertCurrency");
        setDialogActionPath.add("/editCurrency");
        setDialogActionPath.add("/updateCurrency");
        setDialogActionPath.add("/newPurchaseCategory");
        setDialogActionPath.add("/insertPurchaseCategory");
        setDialogActionPath.add("/editPurchaseCategory");
        setDialogActionPath.add("/updatePurchaseCategory");
        setDialogActionPath.add("/newPurchaseSubCategory");
        setDialogActionPath.add("/insertPurchaseSubCategory");
        setDialogActionPath.add("/editPurchaseSubCategory");
        setDialogActionPath.add("/updatePurchaseSubCategory");
        setDialogActionPath.add("/newExchangeRate");
        setDialogActionPath.add("/insertExchangeRate");
        setDialogActionPath.add("/editExchangeRate");
        setDialogActionPath.add("/updateExchangeRate");
        setDialogActionPath.add("/selectSupplier");
        
    }

    public Set getDialogActionPathSet() {
        return setDialogActionPath;
    }
}
