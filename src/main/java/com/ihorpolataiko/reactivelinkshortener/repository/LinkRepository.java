package com.ihorpolataiko.reactivelinkshortener.repository;

import com.ihorpolataiko.reactivelinkshortener.domain.OriginalLink;
import com.ihorpolataiko.reactivelinkshortener.domain.ShortenedLink;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class LinkRepository {

    private final ReactiveStringRedisTemplate redisTemplate;

    public LinkRepository(ReactiveStringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Mono<ShortenedLink> save(OriginalLink originalLink, ShortenedLink shortenedLink) {
        return redisTemplate.opsForValue()
                .set(shortenedLink.getShortenLink(), originalLink.getOriginalLink())
                .map(__ -> shortenedLink);
    }

    public Mono<OriginalLink> findByShortenLink(ShortenedLink shortenedLink) {
        return redisTemplate.opsForValue()
                .get(shortenedLink.getShortenLink())
                .map(OriginalLink::new);
    }

}
