package br.com.srgenex.utils.enums;

import br.com.srgenex.utils.GXUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Lang {

    DAY("day", "dia"),
    HOUR("hour", "hora"),
    MINUTE("minute", "minuto"),
    SECOND("second", "segundo"),
    MILLISECOND("0ms", "0ms"),
    AND("and", "e"),
    NO_ONE("no one", "ninguém");

    private final String english, portuguese;

    public String get(Locale locale){
        return switch (locale) {
            case ENGLISH -> getEnglish();
            case PORTUGUESE -> getPortuguese();
        };
    }

    public String getCapitalized(Locale locale){
        String text = get(locale);
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }

    public String get(){
        return get(GXUtils.getLocale());
    }

    public String getCapitalized(){
        return getCapitalized(GXUtils.getLocale());
    }

}
