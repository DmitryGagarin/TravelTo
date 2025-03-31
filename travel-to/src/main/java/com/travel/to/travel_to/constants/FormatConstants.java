package com.travel.to.travel_to.constants;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;

public class FormatConstants {
    public static final DateTimeFormatter DAY_MONTH_YEAR_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static final DecimalFormat ONE_FLOATING_POINT_FORMATTER = new DecimalFormat("#.##");
}
