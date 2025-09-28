package br.com.srgenex.utils.color;

import org.bukkit.ChatColor;
import org.bukkit.Color;

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

        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static Color fromHex(String hex) {
        if (hex.startsWith("#")) {
            hex = hex.substring(1);
        }

        int r = Integer.valueOf(hex.substring(0, 2), 16);
        int g = Integer.valueOf(hex.substring(2, 4), 16);
        int b = Integer.valueOf(hex.substring(4, 6), 16);

        return Color.fromRGB(r, g, b);
    }


}
