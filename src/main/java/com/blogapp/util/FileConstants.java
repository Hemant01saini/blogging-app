package com.blogapp.util;

import java.util.List;

public class FileConstants {

    public static final String UPLOAD_DIR = "uploads/";

    public static final String PROFILE_DIR = "uploads/profile/";

    public static final List<String> ALLOWED_IMAGE_TYPES = List.of(
            "image/jpeg",
            "image/png",
            "image/jpg"
    );

    public static final long MAX_FILE_SIZE = 5 * 1024 * 1024; //5MB
}
