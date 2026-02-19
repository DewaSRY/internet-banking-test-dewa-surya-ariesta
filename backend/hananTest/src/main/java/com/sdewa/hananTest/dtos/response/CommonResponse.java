package com.sdewa.hananTest.dtos.response;

import java.util.ArrayList;
import java.util.List;

import com.sdewa.hananTest.dtos.common.PaginateMetaData;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse<T> {
    
    private String message;
    @Builder.Default
    private List<T> data = new ArrayList<>();
    private PaginateMetaData metaData;
}
