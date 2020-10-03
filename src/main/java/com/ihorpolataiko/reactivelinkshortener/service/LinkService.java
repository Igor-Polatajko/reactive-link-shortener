package com.ihorpolataiko.reactivelinkshortener.service;

import com.ihorpolataiko.reactivelinkshortener.domain.OriginalLink;
import com.ihorpolataiko.reactivelinkshortener.domain.ShortenLink;
import com.ihorpolataiko.reactivelinkshortener.repository.LinkRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class LinkService {

    public static final int SHORTEN_LINK_LENGTH = 7;
    private final LinkRepository linkRepository;

    public LinkService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public Mono<ShortenLink> convertToShorten(OriginalLink originalLink) {

        ShortenLink shortenLink = new ShortenLink(RandomStringUtils.random(SHORTEN_LINK_LENGTH));

        return linkRepository.save(originalLink, shortenLink);
    }

    public Mono<OriginalLink> convertToOriginal(ShortenLink shortenLink) {

        return linkRepository.findByShortenLink(shortenLink);
    }

}
