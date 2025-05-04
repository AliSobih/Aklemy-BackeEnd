package com.e_learning.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LessonDTO extends BaseDTO {
    private String title;
    private String contentType;
    private String contentURL;
    private int duration;
    private int position;
    private Boolean isVisible;

//    TODO: for arabic
    private String titleAr;
    private String contentTypeAr;

//    TODO: if you will add lesson only in the request but if you will add section with lesson don't use it
    private Long sectionId;
    private boolean isWatched;
}
