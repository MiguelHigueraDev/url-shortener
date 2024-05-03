package dev.miguelhiguera.urlshortener.configs;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
public class ShortenerProperties {

    @Value("${SHORTENER_BASE_URL}")
    private String baseUrl;

    @Value("${SHORTENER_SHORT_URL_MIN_LENGTH}")
    private int shortUrlMinLength;

    @Value("${SHORTENER_SHORT_URL_MAX_LENGTH}")
    private int shortUrlMaxLength;

    @Value("${SHORTENER_CHARSET}")
    private String charset;

}
