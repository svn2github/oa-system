package net.sourceforge.web.struts.action;

import java.io.Serializable;

import net.sourceforge.service.UniversalManager;
import com.shcnc.struts.form.beanloader.UnableToLoadException;

public class BeanLoader implements com.shcnc.struts.form.beanloader.BeanLoader {

    private UniversalManager universalManager=null;
    
    public Object load(Class clazz, String idKey, Serializable idValue) throws UnableToLoadException {
        return universalManager.load(clazz,idValue);
    }

    public void setUniversalManager(UniversalManager universalManager) {
        this.universalManager = universalManager;
    }
    
    
}
