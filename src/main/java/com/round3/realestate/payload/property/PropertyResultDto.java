package com.round3.realestate.payload.property;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class PropertyResultDto {
    private PropertyResultDataDto data;
    private Boolean saved;
}
