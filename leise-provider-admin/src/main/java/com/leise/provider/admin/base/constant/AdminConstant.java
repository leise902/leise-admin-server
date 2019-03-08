package com.leise.provider.admin.base.constant;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class AdminConstant {
    
    public final static DateTimeFormatter DATE_FROMATTER = DateTimeFormat.forPattern("yyyy-MM-dd");
    
    public final static DateTimeFormatter TIME_FROMATTER = DateTimeFormat.forPattern("HH:mm:ss");
    
    public final static DateTimeFormatter DATETIME_FROMATTER = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
    
    public final static DateTimeFormatter DATETIME_FROMATTER_DEFAULT = DateTimeFormat.forPattern("yyyyMMddHHmmss");

}
