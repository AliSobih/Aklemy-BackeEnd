package com.e_learning.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DragAndDropDTO extends BaseDTO {

    private String dragItem;

    private String dropItem;

    private String randomDropItem;

    private String dragItemAr;

    private String dropItemAr;

    private String randomDropItemAr;

    private Long questionId;
}
