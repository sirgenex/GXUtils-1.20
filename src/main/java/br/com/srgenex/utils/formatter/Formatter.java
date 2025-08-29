package br.com.srgenex.utils.formatter;

import br.com.srgenex.utils.GXUtils;
import br.com.srgenex.utils.enums.Lang;
import br.com.srgenex.utils.enums.Locale;

import java.text.DecimalFormat;
import java.time.Duration;

@SuppressWarnings("unused")
public class Formatter {

    private static final int[] VALUES = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    private static final String[] SYMBOLS = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

    public static String toRoman(int num) {
        if (num < 1 || num > 3999) {
            throw new IllegalArgumentException("Number must be between 1 and 3999 for Roman numeral conversion.");
        }

        StringBuilder roman = new StringBuilder();

        for (int i = 0; i < VALUES.length; i++) {
            while (num >= VALUES[i]) {
                roman.append(SYMBOLS[i]);
                num -= VALUES[i];
            }
        }
        return roman.toString();
    }

    private static final String[] suffix = new String[]{"K", "M", "B", "T", "Q", "QD", "QN", "SX", "SP", "O", "N", "DE", "UD", "DD", "TDD", "QDD", "QND", "SXD", "SPD", "OCD", "NVD", "VGN", "UVG", "DVG", "TVG", "QTV", "QNV", "SEV", "SPG", "OVG", "NVG", "TGN", "UTG", "DTG", "TSTG", "QTTG", "QNTG", "SSTG", "SPTG", "OCTG", "NOTG"};

    public static String format(int number) {
        return format((double) number);
    }

    public static String format(double number) {
        return format(number, 0);
    }

    public static String format(double n, int iteration) {
        if (n < 1000) return String.format("%.0f", n);
        double d = (n / 100) / 10.0;
        boolean isRound = (d * 10) % 10 == 0;
        return d < 1000 ? (isRound || d > 9.99 ?
                (int) d * 10 / 10 : String.format("%.0f", d) + (((int) d * 10 / 10) > 0 ? "," : "") + ((int) d * 10 / 10)
        ) + suffix[iteration] : format(d, iteration + 1);
    }

    public static String getRemainingTime(long time) {
        return getRemainingTime(time, GXUtils.getLocale());
    }

    public static String getRemainingTime(long time, Locale locale) {
        StringBuilder s = new StringBuilder();
        Duration remainingTime = Duration.ofMillis(time);
        long days = remainingTime.toDays();
        remainingTime = remainingTime.minusDays(days);
        long hours = remainingTime.toHours();
        remainingTime = remainingTime.minusHours(hours);
        long minutes = remainingTime.toMinutes();
        remainingTime = remainingTime.minusMinutes(minutes);
        long seconds = remainingTime.getSeconds();

        if (days > 0) s.append(days).append(" ").append(Lang.DAY.get(locale)).append(days > 1 ? "s" : "");

        if (hours > 0) {
            if (days > 0 && minutes > 0 || days > 0 && seconds > 0) s.append(", ");
            if(minutes <= 0 && seconds <= 0 && days > 0) s.append(" ").append(Lang.AND.get(locale)).append(" ");
            s.append(hours).append(" ").append(Lang.HOUR.get(locale)).append(hours > 1 ? "s" : "");
        }

        if (minutes > 0) {
            if (hours > 0 && seconds > 0) s.append(", ");
            if(seconds <= 0 && hours > 0) s.append(" ").append(Lang.AND.get(locale)).append(" ");
            s.append(minutes).append(" ").append(Lang.MINUTE.get(locale)).append(minutes > 1 ? "s" : "");
        }

        if (seconds > 0) {
            if (!s.isEmpty() && minutes > 0) s.append(" ").append(Lang.AND.get(locale)).append(" ");
            s.append(seconds).append(" ").append(Lang.SECOND.get(locale)).append(seconds > 1 ? "s" : "");
        }

        return !s.isEmpty() ? s.toString().trim() : Lang.MILLISECOND.get(locale);
    }

    public static String getRemainingTimeSmall(long time) {
        StringBuilder s = new StringBuilder();
        Duration remainingTime = Duration.ofMillis(time);
        long days = remainingTime.toDays();
        remainingTime = remainingTime.minusDays(days);
        long hours = remainingTime.toHours();
        remainingTime = remainingTime.minusHours(hours);
        long minutes = remainingTime.toMinutes();
        remainingTime = remainingTime.minusMinutes(minutes);
        long seconds = remainingTime.getSeconds();
        long months = days/30;
        long years = months/12;
        if(years > 0) s.append((int)years).append("A");
        if(months > 0) s.append((int)months).append("M");

        if (days > 0) s.append(days).append("d");
        if (hours > 0) s.append(hours).append("h");
        if (minutes > 0) s.append(minutes).append("m");
        if (seconds > 0) s.append(seconds).append("s");
        return !s.isEmpty() ? s.toString().trim() : "0ms";
    }

    public static String getRemainingTimeSmaller(long time) {
        StringBuilder s = new StringBuilder();
        Duration remainingTime = Duration.ofMillis(time);
        long hours = remainingTime.toHours();
        remainingTime = remainingTime.minusHours(hours);
        long minutes = remainingTime.toMinutes();
        remainingTime = remainingTime.minusMinutes(minutes);
        long seconds = remainingTime.getSeconds();

        if (hours >= 1)
            s.append(hours < 10 ? "0" + hours : hours);

        if (minutes >= 1) {
            if (hours >= 1) s.append(":");
            s.append(minutes < 10 ? "0" + minutes : minutes);
        } else if (hours >= 1) s.append("00:");

        if (seconds >= 1) {
            if (minutes >= 1) s.append(":");
            else if (hours < 1) s.append("00:");
            s.append(seconds < 10 ? "0" + seconds : seconds);
        } else if (hours >= 1) s.append(":00");
        else if (minutes >= 1) s.append(":00");

        return s.isEmpty() ? "00:00" : s.toString().trim();
    }

    public static String formatPercent(double percent){
        return new DecimalFormat("##.##").format(percent);
    }

    public static String formatPercent(int percent){
        return new DecimalFormat("##.##").format(percent);
    }

}
