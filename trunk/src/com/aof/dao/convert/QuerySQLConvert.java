package com.aof.dao.convert;



public interface QuerySQLConvert {
    public Object convert(StringBuffer sql, Object finalParameters);
}
