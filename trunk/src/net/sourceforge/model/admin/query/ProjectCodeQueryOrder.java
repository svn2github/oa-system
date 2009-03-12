package net.sourceforge.model.admin.query;

import org.apache.commons.lang.enums.Enum;

public class ProjectCodeQueryOrder extends Enum {

    /*id*/
    public static final ProjectCodeQueryOrder ID = new ProjectCodeQueryOrder("id");
    
    protected ProjectCodeQueryOrder(String value) {
        super(value);
    }
    
    public static ProjectCodeQueryOrder getEnum(String value) {
        return (ProjectCodeQueryOrder) getEnum(ProjectCodeQueryOrder.class, value);
    }
}
