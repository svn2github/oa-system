package net.sourceforge.web.struts.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import net.sourceforge.web.struts.action.SecureActionMapping;
import com.shcnc.struts.form.BeanQueryForm;

public class BeanSessionQueryForm extends BeanQueryForm {
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
