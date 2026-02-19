package com.sdewa.hananTest.dtos.filters;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonFilters {

    @Builder.Default
    private Integer page = 1;
    
    @Builder.Default
    private Integer limit = 20;
    
}
