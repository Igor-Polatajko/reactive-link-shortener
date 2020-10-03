package com.ihorpolataiko.reactivelinkshortener.repository;

import com.ihorpolataiko.reactivelinkshortener.domain.OriginalLink;
import com.ihorpolataiko.reactivelinkshortener.domain.ShortenLink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class LinkRepository {

    private final ReactiveStringRedisTemplate redisTemplate;

    @Autowired
    public LinkRepository(ReactiveStringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    Mono<ShortenLink> save(OriginalLink originalLink, ShortenLink shortenLink) {
        return redisTemplate.opsForValue()
                .set(shortenLink.getShortenLink(), originalLink.getOriginalLink())
                .map(__ -> shortenLink);
    }

    Mono<OriginalLink> findByShortenLink(ShortenLink shortenLink) {
        return redisTemplate.opsForValue()
                .get(shortenLink.getShortenLink())
                .map(OriginalLink::new);
    }

}
