package com.travel.to.travel_to.constants;

import java.util.List;

public class ValidationConstants {

    public static final int USER_PASSWORD_MIN_LENGTH = 8;
    public static final int ATTRACTION_DISCUSSION_MIN_LENGTH = 10;
    public static final int ATTRACTION_DISCUSSION_DESCRIPTION_MAX_LENGTH = 50;

    public static final List<String> ALLOWED_IMAGE_FORMATS = List.of("png", "jpg", "jpeg", "svg", "webp", "HEIC", "JPG");
    public static final List<String> ALLOWED_EMAIL_DOMAINS = List.of("com", "org", "ru", "net");
    public static final long MAX_FILE_SIZE = 2 * 1024 * 1024;
}
