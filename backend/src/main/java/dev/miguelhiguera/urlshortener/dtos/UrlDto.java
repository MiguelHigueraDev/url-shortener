package dev.miguelhiguera.urlshortener.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UrlDto {

    @NotBlank
    @Pattern(regexp = "^(http|https)://.*$", message = "Url must start with http:// o https://")
    @Size(min = 10, max = 255, message = "Url must be between 10 and 255 characters")
    private String originalUrl;
}
