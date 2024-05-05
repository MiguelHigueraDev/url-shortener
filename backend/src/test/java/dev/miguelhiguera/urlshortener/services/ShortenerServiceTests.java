package dev.miguelhiguera.urlshortener.services;

import dev.miguelhiguera.urlshortener.configs.ShortenerProperties;
import dev.miguelhiguera.urlshortener.dtos.UrlDto;
import dev.miguelhiguera.urlshortener.entities.Url;
import dev.miguelhiguera.urlshortener.repositories.ShortenerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ShortenerServiceTests {

    @Mock
    private ShortenerRepository shortenerRepository;

    @Mock
    private ShortenerProperties shortenerProperties;

    @InjectMocks
    private ShortenerServiceImpl shortenerService;

    private UrlDto urlDto;

    @BeforeEach
    public void init() {
        urlDto = UrlDto.builder().originalUrl("https://www.google.com").build();
    }

    @Test
    public void ShortenerService_ShouldGenerateValidUrl() {
        // Mock ShortenerProperties
        when(shortenerProperties.getShortUrlMinLength()).thenReturn(3);
        when(shortenerProperties.getShortUrlMaxLength()).thenReturn(5);
        when(shortenerProperties.getCharset()).thenReturn("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");

        when(shortenerRepository.findByOriginalUrl(urlDto.getOriginalUrl())).thenReturn(Optional.empty());

        when(shortenerRepository.save(any(Url.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Url url = shortenerService.createShortUrl(urlDto);

        Assertions.assertThat(url.getShortUrl().length()).isLessThan(6).isGreaterThan(2);
        Assertions.assertThat(url.getOriginalUrl()).isEqualTo(urlDto.getOriginalUrl());
        Assertions.assertThat(url.getTimesVisited()).isEqualTo(0);
        assertShortUrlHasValidCharacters(url.getShortUrl(), shortenerProperties.getCharset());
    }

    private static void assertShortUrlHasValidCharacters(String shortUrl, String charset) {
        for (char c : shortUrl.toCharArray()) {
            Assertions.assertThat(charset.contains(String.valueOf(c))).isTrue();
        }
    }
}
