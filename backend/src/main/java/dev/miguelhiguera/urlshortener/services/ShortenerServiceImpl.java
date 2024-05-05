package dev.miguelhiguera.urlshortener.services;

import dev.miguelhiguera.urlshortener.configs.ShortenerProperties;
import dev.miguelhiguera.urlshortener.dtos.UrlDto;
import dev.miguelhiguera.urlshortener.entities.Url;
import dev.miguelhiguera.urlshortener.repositories.ShortenerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShortenerServiceImpl implements ShortenerService {
    private final ShortenerRepository shortenerRepository;
    private final ShortenerProperties shortenerProperties;

    public ShortenerServiceImpl(ShortenerRepository shortenerRepository, ShortenerProperties shortenerProperties) {
        this.shortenerRepository = shortenerRepository;
        this.shortenerProperties = shortenerProperties;
    }

    @Override
    public Long getUrlCount() {
        return shortenerRepository.count();
    }

    @Override
    public String getOriginalUrlFromShortUrl(String shortUrl) {
        Optional<Url> optionalUrl = shortenerRepository.findByShortUrl(shortUrl);

        if (optionalUrl.isEmpty()) {
            return "";
        }

        return optionalUrl.get().getOriginalUrl();
    }

    @Override
    public Optional<Url> getUrlObjFromShortUrl(String shortUrl) {
        return shortenerRepository.findByShortUrl(shortUrl);
    }

    @Override
    public Url createShortUrl(UrlDto urlDto) {
        // Check if the originalUrl already exists in the database and return it
        Optional<Url> optionalUrl = shortenerRepository.findByOriginalUrl(urlDto.getOriginalUrl());
        if (optionalUrl.isPresent()) {
            return optionalUrl.get();
        }

        String shortened = generateShortUrl();

        Url url = Url.builder().originalUrl(urlDto.getOriginalUrl()).shortUrl(shortened).timesVisited(0).build();
        return shortenerRepository.save(url);
    }

    @Override
    public void increaseVisitCount(Long urlId) {
        Optional<Url> optionalUrl = shortenerRepository.findById(urlId);
        if (optionalUrl.isPresent()) {
            Url url = optionalUrl.get();
            url.setTimesVisited(url.getTimesVisited() + 1);
            shortenerRepository.save(url);
        }
    }

    @Override
    public void deleteUrl(Long id) {
        shortenerRepository.deleteById(id);
    }

    private String generateShortUrl() {
        // Choose a random length for the short url
        int minUrlLength = shortenerProperties.getShortUrlMinLength();
        int maxUrlLength = shortenerProperties.getShortUrlMaxLength();

        if (minUrlLength > maxUrlLength || minUrlLength <= 0) {
            throw new IllegalArgumentException("Invalid URL length configuration");
        }

        String characters = shortenerProperties.getCharset();
        String shortUrl;

        do {
            int urlLength = (int) (Math.random() * (maxUrlLength - minUrlLength + 1)) + minUrlLength;
            shortUrl = generateRandomString(urlLength, characters);
        } while (shortenerRepository.findByShortUrl(shortUrl).isPresent());

        return shortUrl;
    }

    private String generateRandomString(int length, String charset) {
        StringBuilder randomString = new StringBuilder();
        for (int i = 0; i < length; i++) {
            randomString.append(charset.charAt((int) (Math.random() * charset.length())));
        }
        return randomString.toString();
    }
}
