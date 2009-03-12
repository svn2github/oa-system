/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

/*
 * Created on 2005-9-24
 *
 */
package net.sourceforge.web.struts.action;

import com.shcnc.struts.action.CustomActionMapping;


/**
 * 扩展Struts Action Mapping的定义，以便支持权限控制
 * @author nicebean
 *
 */
public class SecureActionMapping extends CustomActionMapping {
    private Integer functionId;
    private String functionDescription;
    private String level;
    private String functionType;
    
    public String getAttribute() {
        if (this.attribute == null) {
            if(this.getScope().equals("session"))
            {
                String path=this.getPath().trim();
                if(path.startsWith("/")) path=path.substring(1);
                int index=path.indexOf(".do");
                if(index!=-1) path=path.substring(0,index);
                return path+"_"+this.name;
            }
            else
                return (this.name);
        } else {
            return (this.attribute);
        }
    }
    
	public SecureActionMapping() {
        super();
    }
    
    /**
     * @return Returns the functionDescription.
     */
    public String getFunctionDescription() {
        return functionDescription;
    }

    /**
     * @param functionDescription The functionDescription to set.
     */
    public void setFunctionDescription(String functionDescription) {
        this.functionDescription = functionDescription;
    }

    /**
     * @return Returns the functionId.
     */
    public Integer getFunctionId() {
        return functionId;
    }

    /**
     * @param functionId The functionId to set.
     */
    public void setFunctionId(Integer functionId) {
        this.functionId = functionId;
    }

    /**
     * @return Returns the functionType.
     */
    public String getFunctionType() {
        return functionType;
    }

    /**
     * @param functionType The functionType to set.
     */
    public void setFunctionType(String functionType) {
        this.functionType = functionType;
    }

    /**
     * @return Returns the level.
     */
    public String getLevel() {
        return level;
    }

    /**
     * @param level The level to set.
     */
    public void setLevel(String level) {
        this.level = level;
    }

}
