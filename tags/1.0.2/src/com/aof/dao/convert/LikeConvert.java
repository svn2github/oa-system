/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package com.aof.dao.convert;

public class LikeConvert implements QueryParameterConvert {

    public Object convert(Object src) {
        return '%' + src.toString() + '%';
    }

}
