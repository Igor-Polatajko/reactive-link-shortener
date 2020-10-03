package com.ihorpolataiko.reactivelinkshortener.service;

import com.ihorpolataiko.reactivelinkshortener.domain.OriginalLink;
import com.ihorpolataiko.reactivelinkshortener.domain.ShortenedLink;
import com.ihorpolataiko.reactivelinkshortener.repository.LinkRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class LinkService {

    public static final int SHORTEN_LINK_LENGTH = 7;

    private final LinkRepository linkRepository;

    private final String appBaseUrl;

    public LinkService(LinkRepository linkRepository, @Value("${app.baseUrl}") String appBaseUrl) {
        this.linkRepository = linkRepository;
        this.appBaseUrl = appBaseUrl;
    }

    public Mono<ShortenedLink> convertToShorten(OriginalLink originalLink) {

        ShortenedLink shortenedLink = new ShortenedLink(RandomStringUtils.randomAlphabetic(SHORTEN_LINK_LENGTH));

        return linkRepository.save(originalLink, shortenedLink)
                .map(shorten -> new ShortenedLink(appBaseUrl + shorten.getShortenLink()));
    }

    public Mono<OriginalLink> convertToOriginal(ShortenedLink shortenedLink) {

        return linkRepository.findByShortenLink(shortenedLink);
    }

}
