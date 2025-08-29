package br.com.srgenex.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Locale {

    ENGLISH("en_US"),
    PORTUGUESE("pt_BR");

    private final String code;

}
