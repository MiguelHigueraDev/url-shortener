package dev.miguelhiguera.urlshortener.controllers;

import dev.miguelhiguera.urlshortener.configs.ShortenerProperties;
import dev.miguelhiguera.urlshortener.dtos.UrlDto;
import dev.miguelhiguera.urlshortener.entities.Url;
import dev.miguelhiguera.urlshortener.services.ShortenerService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@RestController
public class ShortenerController {

    private final ShortenerProperties shortenerProperties;
    private final ShortenerService shortenerService;

    public ShortenerController(ShortenerService shortenerService, ShortenerProperties shortenerProperties) {
        this.shortenerService = shortenerService;
        this.shortenerProperties = shortenerProperties;
    }

    @GetMapping("/")
    public RedirectView redirectToHome() {
        return new RedirectView(shortenerProperties.getHomeUrl());
    }

    @GetMapping("/{shortUrl}")
    public RedirectView redirectToUrl(@PathVariable String shortUrl) {
        Optional<Url> url = shortenerService.getUrlObjFromShortUrl(shortUrl);
        // Redirect to home url if the short url is not found
        if (url.isEmpty()) {
           return new RedirectView(shortenerProperties.getHomeUrl());
        }

        shortenerService.increaseVisitCount(url.get().getId());
        String originalUrl = url.get().getOriginalUrl();
        return new RedirectView(originalUrl);
    }

    @PostMapping("/shorten")
    public String shortenUrl(@Valid @RequestBody UrlDto urlDto) {
        Url url = shortenerService.createShortUrl(urlDto);
        return "{\"url\":\"" + shortenerProperties.getBaseUrl() + "/" + url.getShortUrl() + "\"}";
    }
}
