package dev.miguelhiguera.urlshortener.repositories;

import dev.miguelhiguera.urlshortener.entities.Url;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ShortenerRepository extends CrudRepository<Url, Long>{
    Optional<Url> findByShortUrl(String shortUrl);
    Optional<Url> findByOriginalUrl(String originalUrl);
}
