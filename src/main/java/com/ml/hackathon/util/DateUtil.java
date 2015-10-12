package com.ml.hackathon.util;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

import java.util.Date;

/**
 * Created by gurodriguez on 6/18/15.
 */
public class DateUtil {

    public static Date getDateFromString(String date){
        DateTime dateTime = ISODateTimeFormat.dateTimeParser().parseDateTime(date);
        return dateTime.toDate();
    }
}
