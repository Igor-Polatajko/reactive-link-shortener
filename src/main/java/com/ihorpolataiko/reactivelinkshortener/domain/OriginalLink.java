package com.ihorpolataiko.reactivelinkshortener.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OriginalLink {

    @NotNull(message = "Link must be specified")
    private String originalLink;

}
