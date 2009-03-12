package net.sourceforge.model.admin.query;

import org.apache.commons.lang.enums.Enum;

public class CountryQueryCondition extends Enum {

	/* id */
	public static final CountryQueryCondition ID_EQ = new CountryQueryCondition(
			"id_eq");

	/* keyPropertyList */

	/* kmtoIdList */

	/* mtoIdList */

	/* property */
	public static final CountryQueryCondition SHORTNAME_LIKE = new CountryQueryCondition(
			"shortName_like");

	public static final CountryQueryCondition ENGNAME_LIKE = new CountryQueryCondition(
			"engName_like");

	public static final CountryQueryCondition CHNNAME_LIKE = new CountryQueryCondition(
			"chnName_like");

	public static final CountryQueryCondition ENABLED_EQ = new CountryQueryCondition(
			"enabled_eq");

	

	protected CountryQueryCondition(String value) {
		super(value);
	}

	public static CountryQueryCondition getEnum(String value) {
		return (CountryQueryCondition) getEnum(CountryQueryCondition.class,
				value);
	}

}
