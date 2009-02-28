package com.aof.web.struts.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.aof.web.struts.action.SecureActionMapping;
import com.shcnc.struts.form.BaseQueryForm;

public class BaseSessionQueryForm extends BaseQueryForm {
    public String getValidationKey(ActionMapping mapping, HttpServletRequest request) {
        return mapping.getName();
    }
    
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        if(!(mapping instanceof SecureActionMapping))throw new RuntimeException("mapping is not org.apache.struts.action.ActionMapping");
        if(!mapping.getScope().equals("session")) throw new RuntimeException("scope is not session");
        request.setAttribute(mapping.getName(),this);
        String pageNo=this.getPageNo();
        boolean desc=this.isDescend();
        super.reset(mapping, request);
        this.setPageNo(pageNo);
        this.setDescend(desc);
        this.setExportType("");
    }
}
