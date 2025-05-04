package com.e_learning.util;

public class Constants {

    public static final String ENGLISH_CHARACTERS = "^[a-zA-Z ]+$";
    public static final String ENGLISH_CHARACTERS_OR_DIGITS = "^[a-zA-Z \\d]+$";
    public static final String ARABIC_CHARACTERS = "^[\\u0621-\\u064A ]+$";
    public static final String ARABIC_CHARACTERS_OR_DIGITS = "^[\\u0621-\\u064A \\d]+$";
    public static final String DIGITS_ONLY_14 = "^[0-9]{14}$";
    public static final String DIGITS_ONLY = "^[0-9]+$";
    public static final String DIGITS_ONLY_11 = "^[01][0-9]{10}$";
    public static final String required = "مطلوب";
    public static final int COURSE_IMAGE = 1;
    public static final int COURSE_VIDEO = 2;
    public static final int ADS_SEL = 3;
    public static final int CATEGORY_IMAGE = 4;
    public static final int QUESTION_IMAGE = 5;

    public static final String QUESTION_STATUS_DRAG_AND_DROP = "DRAG_DROP";
    public static final String QUESTION_STATUS_MULTIPLE_CHOICE = "MULTIPLE";
    public static final String QUESTION_STATUS_CHOICE = "SINGLE";
}
