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
    @Pattern(regexp = "^(https://[a-zA-Z0-9\\-.]+\\.[a-zA-Z]{2,}(\\.[a-zA-Z]{2,})?).*$", message = "Url must start with http:// or https:// and contain a valid domain")
    @Size(min = 10, max = 255, message = "Url must be between 10 and 255 characters")
    private String originalUrl;
}
