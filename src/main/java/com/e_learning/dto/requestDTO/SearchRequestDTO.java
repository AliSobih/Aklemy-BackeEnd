package com.e_learning.dto.requestDTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class SearchRequestDTO {
    private String searchValue;
    private String sortDirection;
    private String sortBy;

    @Nullable
    private String loggedInUserEmail;

    @Nullable
    private Boolean isAdminUser;
}
