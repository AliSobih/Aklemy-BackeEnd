package com.e_learning.dto.requestDTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Date;

@Getter
@Setter
@Component
public class EnrollmentSearchRequestDTO extends SearchRequestDTO {

    @Nullable
    private Date enrollmentDateFrom;

    @Nullable
    private Date enrollmentDateTo;

}
