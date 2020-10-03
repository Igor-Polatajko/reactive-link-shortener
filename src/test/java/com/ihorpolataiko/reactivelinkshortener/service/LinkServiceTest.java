package com.ihorpolataiko.reactivelinkshortener.service;

import com.ihorpolataiko.reactivelinkshortener.domain.OriginalLink;
import com.ihorpolataiko.reactivelinkshortener.domain.ShortenLink;
import com.ihorpolataiko.reactivelinkshortener.repository.LinkRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LinkServiceTest {

    @Mock
    private LinkRepository linkRepository;

    @InjectMocks
    private LinkService linkService;

    @Captor
    private ArgumentCaptor<ShortenLink> shortenLinkArgumentCaptor;

    @Test
    void convertToShorten() {

        OriginalLink originalLink = new OriginalLink("/original/link");

        when(linkRepository.save(eq(originalLink), any()))
                .thenAnswer(invocation -> Mono.just(invocation.getArgument(1)));

        Mono<ShortenLink> actualShortenLink = linkService.convertToShorten(originalLink);

        verify(linkRepository, only()).save(eq(originalLink), shortenLinkArgumentCaptor.capture());

        ShortenLink expectedShortenLink = shortenLinkArgumentCaptor.getValue();

        assertEquals(expectedShortenLink, actualShortenLink.block());

    }

    @Test
    void convertToOriginal() {

        OriginalLink expectedOriginal = new OriginalLink("/original/link");

        ShortenLink shortenLink = new ShortenLink("/shorten");

        when(linkRepository.findByShortenLink(shortenLink)).thenReturn(Mono.just(expectedOriginal));

        StepVerifier.create(linkService.convertToOriginal(shortenLink))
                .expectNext(expectedOriginal)
                .verifyComplete();

        verify(linkRepository, only()).findByShortenLink(shortenLink);

    }

}