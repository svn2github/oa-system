package com.aof.model.admin.query;

import org.apache.commons.lang.enums.Enum;

public class HotelContractQueryOrder extends Enum{

	/*id*/
	public static final HotelContractQueryOrder ID = new HotelContractQueryOrder("id");

	/*property*/
	public static final HotelContractQueryOrder FILENAME = new HotelContractQueryOrder("fileName");
	public static final HotelContractQueryOrder DESCRIPTION = new HotelContractQueryOrder("description");
	public static final HotelContractQueryOrder FILECONTENT = new HotelContractQueryOrder("fileContent");
	public static final HotelContractQueryOrder UPLOADDATE = new HotelContractQueryOrder("uploadDate");
    
	protected HotelContractQueryOrder(String value) {
		super(value);
	}
	
	public static HotelContractQueryOrder getEnum(String value) {
        return (HotelContractQueryOrder) getEnum(HotelContractQueryOrder.class, value);
    }

}
