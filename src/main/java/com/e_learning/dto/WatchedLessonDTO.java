package com.e_learning.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WatchedLessonDTO extends BaseDTO {
    private Long userId;
    private Long lessonId;
    private Long courseId;
    private Long sectionId;
}
