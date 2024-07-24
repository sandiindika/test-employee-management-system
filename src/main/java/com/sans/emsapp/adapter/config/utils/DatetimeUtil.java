package com.sans.emsapp.adapter.config.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Component
public class DatetimeUtil {
    private final String TIMEZONE;

    public DatetimeUtil(
            @Value("${app.ems.properties.date_format}") String timezone
    ) {
        TIMEZONE = timezone;
    }

    public String generateDatetime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone(TIMEZONE));

        return sdf.format(new Date());
    }
}
