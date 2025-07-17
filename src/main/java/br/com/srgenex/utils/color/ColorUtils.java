package br.com.srgenex.utils.color;

import org.bukkit.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorUtils {

    private static final Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");

    public static String translate(String input) {
        String message = ChatColor.translateAlternateColorCodes('&', input);
        Matcher matcher = pattern.matcher(message);

        while (matcher.find()) {
            String hexCode = message.substring(matcher.start(), matcher.end());
            message = message.replace(hexCode, net.md_5.bungee.api.ChatColor.of(hexCode).toString());
            matcher = pattern.matcher(message);
        }

        return message;
    }

}
