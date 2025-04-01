package com.round3.realestate.payload.scrapping;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ScrappingDto {
    @NotBlank
    @Pattern(regexp = "https://www.idealista.com/inmueble/\\d+/?")
    private String url;
    @NotNull
    private Boolean store;
}
