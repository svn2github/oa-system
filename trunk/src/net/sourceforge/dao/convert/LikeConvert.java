/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.dao.convert;

public class LikeConvert implements QueryParameterConvert {

    public Object convert(Object src) {
        return '%' + src.toString() + '%';
    }

}
