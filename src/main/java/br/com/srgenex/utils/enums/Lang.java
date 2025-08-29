package br.com.srgenex.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Lang {

    DAY("day", "dia"),
    HOUR("hour", "hora"),
    MINUTE("minute", "minuto"),
    SECOND("second", "segundo"),
    MILISECOND("some miliseconds", "alguns milisegundos"),
    AND("and", "e");

    private String english, portuguese;

    public String get(Locale locale){
        return switch (locale) {
            case ENGLISH -> getEnglish();
            case PORTUGUESE -> getPortuguese();
        };
    }

}
