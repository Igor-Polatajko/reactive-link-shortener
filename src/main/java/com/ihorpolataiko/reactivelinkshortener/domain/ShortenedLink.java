package com.ihorpolataiko.reactivelinkshortener.domain;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ShortenedLink {

    @NotNull(message = "Link must be specified")
    private final String shortenLink;

}
