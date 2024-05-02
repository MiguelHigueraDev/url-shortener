package dev.miguelhiguera.urlshortener.services;

import dev.miguelhiguera.urlshortener.dtos.UrlDto;
import dev.miguelhiguera.urlshortener.entities.Url;

import java.util.Optional;

public interface ShortenerService {
    String getOriginalUrlFromShortUrl(String shortUrl);
    Optional<Url> getUrlObjFromShortUrl(String shortUrl);
    Url createShortUrl(UrlDto urlDto);
    void increaseVisitCount(Long urlId);
    void deleteUrl(Long id);
}
