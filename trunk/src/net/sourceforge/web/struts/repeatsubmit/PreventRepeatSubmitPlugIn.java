package net.sourceforge.web.struts.repeatsubmit;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;

import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ActionConfig;
import org.apache.struts.config.ModuleConfig;

/**
 * the plugin for Preventing Repeat Submit
 * @author shilei
 * @version 1.0 (Nov 15, 2005)
 */
public class PreventRepeatSubmitPlugIn implements PlugIn{

    /* (non-Javadoc)
     * @see org.apache.struts.action.PlugIn#destroy()
     */
    public void destroy() {
        
    }

    /**
     * init relationship of action mappings
     */
    public void init(ActionServlet servlet, ModuleConfig config) throws ServletException {
        Map fromMap=new HashMap();
        Map toMap=new HashMap();
        
        Map pathToActionMap=new HashMap();
        ActionConfig[] actionConfigs = config.findActionConfigs();
        for (int i = 0; i < actionConfigs.length; i++) {
            ActionConfig actionConfig=actionConfigs[i];
            pathToActionMap.put(this.processPath(actionConfig.getPath()),actionConfig);
        }
        
        for (int i = 0; i < actionConfigs.length; i++) {
            ActionConfig actionConfig=actionConfigs[i];
            if(actionConfig.getInput()!=null)
            {
                String input=processPath(actionConfig.getInput());
                ActionConfig fromActionConfig=(ActionConfig) pathToActionMap.get(input);
                if(fromActionConfig!=null)
                {
                    fromMap.put(fromActionConfig.getPath(),input);
                    toMap.put(actionConfig.getPath(),input);
                }
            }
        }
        servlet.getServletContext().setAttribute(ATTRIBUTE_FROMMAP,fromMap);
        servlet.getServletContext().setAttribute(ATTRIBUTE_TOMAP,toMap);
    }

    
    private String processPath(String path) {
        if(path.charAt(0)=='/') path= path.substring(1);
        int index=path.lastIndexOf(".do");
        if(index==-1) return path;
        return path.substring(0,index);
    }

    public static final String ATTRIBUTE_FROMMAP="com.shcnc.struts.PreventRepeatSubmitPlugIn.frommap";
    public static final String ATTRIBUTE_TOMAP="com.shcnc.struts.PreventRepeatSubmitPlugIn.tomap";
    public static final String ATTRIBUTE_TOKENMAP="com.shcnc.struts.PreventRepeatSubmitPlugIn.tokenmap";
  

}
