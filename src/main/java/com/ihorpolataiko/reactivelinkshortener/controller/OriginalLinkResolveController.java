package com.ihorpolataiko.reactivelinkshortener.controller;

import com.ihorpolataiko.reactivelinkshortener.domain.ShortenedLink;
import com.ihorpolataiko.reactivelinkshortener.service.LinkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class OriginalLinkResolveController {

    private final LinkService linkService;

    public OriginalLinkResolveController(LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping("/{shortenedLink}")
    public Mono<ResponseEntity<Object>> convertToOriginal(@PathVariable("shortenedLink") String shortenedLink) {
        return linkService.convertToOriginal(new ShortenedLink(shortenedLink))
                .map(originalLink -> ResponseEntity
                                .status(HttpStatus.PERMANENT_REDIRECT)
                                .header("Location", originalLink.getOriginalLink())
                                .build())
                .defaultIfEmpty(ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .build());
    }

}
