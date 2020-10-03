package com.ihorpolataiko.reactivelinkshortener.service;

import com.ihorpolataiko.reactivelinkshortener.domain.OriginalLink;
import com.ihorpolataiko.reactivelinkshortener.domain.ShortenedLink;
import com.ihorpolataiko.reactivelinkshortener.repository.LinkRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LinkServiceTest {

    private static final String APP_BASE_URL = "https://app-base-url/";

    private LinkRepository linkRepository = mock(LinkRepository.class);

    private LinkService linkService = new LinkService(linkRepository, APP_BASE_URL);

    private ArgumentCaptor<ShortenedLink> shortenLinkArgumentCaptor = ArgumentCaptor.forClass(ShortenedLink.class);

    @Test
    void convertToShorten() {

        OriginalLink originalLink = new OriginalLink("/original/link");

        when(linkRepository.save(eq(originalLink), any()))
                .thenAnswer(invocation -> Mono.just(invocation.getArgument(1)));

        Mono<ShortenedLink> actualShortenLink = linkService.convertToShorten(originalLink);

        verify(linkRepository, only()).save(eq(originalLink), shortenLinkArgumentCaptor.capture());

        ShortenedLink expectedShortenedLink = new ShortenedLink(
                APP_BASE_URL + shortenLinkArgumentCaptor.getValue().getShortenLink()
        );

        assertEquals(expectedShortenedLink, actualShortenLink.block());

    }

    @Test
    void convertToOriginal() {

        OriginalLink expectedOriginal = new OriginalLink("/original/link");

        ShortenedLink shortenedLink = new ShortenedLink("/shorten");

        when(linkRepository.findByShortenLink(shortenedLink)).thenReturn(Mono.just(expectedOriginal));

        StepVerifier.create(linkService.convertToOriginal(shortenedLink))
                .expectNext(expectedOriginal)
                .verifyComplete();

        verify(linkRepository, only()).findByShortenLink(shortenedLink);

    }

}