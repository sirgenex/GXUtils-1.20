package br.com.srgenex.utils.color;

import org.bukkit.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorUtils {

    private static final Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");

    public static String translate(String message) {
        Matcher matcher = pattern.matcher(message);

        while (matcher.find()) {
            String hexCode = matcher.group();
            String replacement = net.md_5.bungee.api.ChatColor.of(hexCode).toString();
            message = message.replace(hexCode, replacement);
            matcher = pattern.matcher(message);
        }

        System.out.println("TRANSLATED: "+message);

        return ChatColor.translateAlternateColorCodes('&', message);
    }


}
