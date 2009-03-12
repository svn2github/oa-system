package net.sourceforge.dao.convert;



public interface QuerySQLConvert {
    public Object convert(StringBuffer sql, Object finalParameters);
}
