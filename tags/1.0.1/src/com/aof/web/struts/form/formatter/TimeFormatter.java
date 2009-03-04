package com.aof.web.struts.form.formatter;

import com.shcnc.struts.form.DateFormatter;

public class TimeFormatter extends DateFormatter {

	public TimeFormatter()
	{
		super(java.util.Date.class,"HH:mm");
	}
	
	/*public static void main(String[] args)
	{
		TimeFormatter f=new TimeFormatter();
		System.out.println(f.unformat("12:13"));
	}*/
	
}

