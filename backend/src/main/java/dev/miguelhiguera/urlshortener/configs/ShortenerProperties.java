package dev.miguelhiguera.urlshortener.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "shortener")
public class ShortenerProperties {

    private String baseUrl;
    private int shortUrlMinLength;
    private int shortUrlMaxLength;
    private String charset;

}
