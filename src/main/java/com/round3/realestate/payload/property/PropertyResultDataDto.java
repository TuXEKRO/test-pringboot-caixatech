package com.round3.realestate.payload.property;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class PropertyResultDataDto {
    private String fullTitle;
    private String rooms;
    private String size;
    private String price;
    private String location;
    private String type;
    private String url;
}
