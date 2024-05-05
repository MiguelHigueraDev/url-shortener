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
    public void ShortenerService_ShouldReturnUrlCount() {
        when(shortenerRepository.count()).thenReturn(5L);

        Long count = shortenerService.getUrlCount();

        Assertions.assertThat(count).isEqualTo(5L);
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

    @Test
    public void ShortenerService_ShouldIncreaseVisitCount() {
        Url url = Url.builder().originalUrl(urlDto.getOriginalUrl()).shortUrl("abc").timesVisited(0).build();
        when(shortenerRepository.findById(1L)).thenReturn(Optional.of(url));
        when(shortenerRepository.save(any(Url.class))).thenAnswer(invocation -> invocation.getArgument(0));

        shortenerService.increaseVisitCount(1L);

        Assertions.assertThat(url.getTimesVisited()).isEqualTo(1);

    }

    @Test
    public void ShortenerService_ShouldReturnOriginalUrlFromShortUrl() {
        Url url = Url.builder().originalUrl(urlDto.getOriginalUrl()).shortUrl("abc").timesVisited(0).build();
        when(shortenerRepository.findByShortUrl("abc")).thenReturn(Optional.of(url));

        String originalUrl = shortenerService.getOriginalUrlFromShortUrl("abc");

        Assertions.assertThat(originalUrl).isEqualTo(urlDto.getOriginalUrl());
    }

    @Test
    public void ShortenerService_ShouldReturnEmptyStringIfShortUrlDoesNotExist() {
        when(shortenerRepository.findByShortUrl("abc")).thenReturn(Optional.empty());

        String originalUrl = shortenerService.getOriginalUrlFromShortUrl("abc");

        Assertions.assertThat(originalUrl).isEmpty();
    }

    private static void assertShortUrlHasValidCharacters(String shortUrl, String charset) {
        for (char c : shortUrl.toCharArray()) {
            Assertions.assertThat(charset.contains(String.valueOf(c))).isTrue();
        }
    }
}
