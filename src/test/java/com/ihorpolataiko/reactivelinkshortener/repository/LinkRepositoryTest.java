package com.ihorpolataiko.reactivelinkshortener.repository;

import com.ihorpolataiko.reactivelinkshortener.domain.OriginalLink;
import com.ihorpolataiko.reactivelinkshortener.domain.ShortenLink;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

@SpringBootTest
class LinkRepositoryTest extends AbstractContainerBaseTest {

    @Autowired
    private LinkRepository linkRepository;

    @Test
    void saveAndThenFindByShortenLink() {

        OriginalLink originalLink = new OriginalLink("/original/link");
        ShortenLink shortenLink = new ShortenLink("/shorten");

        // save
        StepVerifier.create(linkRepository.save(originalLink, shortenLink))
                .expectNext(shortenLink)
                .verifyComplete();

        // findByShortenLink
        StepVerifier.create(linkRepository.findByShortenLink(shortenLink))
                .expectNext(originalLink)
                .verifyComplete();

    }

}