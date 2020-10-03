package com.ihorpolataiko.reactivelinkshortener.controller;

import com.ihorpolataiko.reactivelinkshortener.domain.OriginalLink;
import com.ihorpolataiko.reactivelinkshortener.domain.ShortenedLink;
import com.ihorpolataiko.reactivelinkshortener.service.LinkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController("/api")
public class LinkShortenerApiController {

    private final LinkService linkService;

    public LinkShortenerApiController(LinkService linkService) {
        this.linkService = linkService;
    }

    @PostMapping(value = "/shorten",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ShortenedLink> convertToShorten(@Validated @RequestBody OriginalLink originalLink) {
        return linkService.convertToShorten(originalLink);
    }

}
