package com.e_learning.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WatchedLessonsPerSection {
    private Long sectionId;
    private int watchedLessons;
}
