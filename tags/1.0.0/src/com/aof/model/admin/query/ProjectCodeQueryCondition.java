package com.aof.model.admin.query;

import org.apache.commons.lang.enums.Enum;

public class ProjectCodeQueryCondition extends Enum {
    
    /*id*/
    public static final ProjectCodeQueryCondition ID_EQ =
         new ProjectCodeQueryCondition("id_eq");

    public static final ProjectCodeQueryCondition SITE_ID_EQ =
        new ProjectCodeQueryCondition("site_id_eq");

    public static final ProjectCodeQueryCondition ENABLED_EQ =
         new ProjectCodeQueryCondition("enabled_eq");

    protected ProjectCodeQueryCondition(String value) {
        super(value);
    }
    
    public static ProjectCodeQueryCondition getEnum(String value) {
        return (ProjectCodeQueryCondition) getEnum(ProjectCodeQueryCondition.class, value);
    }
}
