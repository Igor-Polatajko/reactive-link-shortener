package com.ihorpolataiko.reactivelinkshortener.controller;

import com.ihorpolataiko.reactivelinkshortener.domain.OriginalLink;
import com.ihorpolataiko.reactivelinkshortener.domain.ShortenedLink;
import com.ihorpolataiko.reactivelinkshortener.service.LinkService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.result.view.Rendering;

@Controller
@RequestMapping("/ui")
public class LinkShortenerWebController {

    private final LinkService linkService;

    public LinkShortenerWebController(LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping
    public Rendering index() {
        return Rendering.view("index").build();
    }

    @PostMapping
    public Rendering shortenLink(@ModelAttribute @Validated OriginalLink originalLink) {
        return Rendering.view("show-shortened")
                .modelAttribute("shortenedLink",
                        linkService.convertToShorten(originalLink)
                                .map(ShortenedLink::getShortenLink))
                .build();
    }

}
