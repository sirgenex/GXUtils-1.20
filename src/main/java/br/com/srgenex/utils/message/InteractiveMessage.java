package br.com.srgenex.utils.message;

import br.com.srgenex.utils.color.ColorUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class InteractiveMessage {

    private static final LegacyComponentSerializer SERIALIZER = LegacyComponentSerializer.legacyAmpersand();

    public static void sendRequestMessage(Player player, Message footer, Message accept, Message decline, String confirmCommand, String cancelCommand) {
        Map<String, Component> buttons = new HashMap<>();
        Map<String, String> placeholders = Map.of("%player%", player.getName());

        buttons.put("%accept%", buildButton(accept.getMessage().getFirst(), "Click to accept", NamedTextColor.GREEN, confirmCommand.replace("%player%", player.getName())));
        buttons.put("%decline%", buildButton(decline.getMessage().getFirst(), "Click to decline", NamedTextColor.RED, cancelCommand.replace("%player%", player.getName())));

        send(player, footer, buttons, placeholders);
    }

    public static void sendPageMessage(Player player, int page, int maxPages, Message footer, Message previous, Message next, String previousPageCommand, String nextPageCommand) {
        Map<String, Component> buttons = new HashMap<>();
        Map<String, String> placeholders = Map.of("%player%", player.getName(), "%page%", String.valueOf(page), "%pages%", String.valueOf(maxPages));

        buttons.put("%previous%", buildButton(previous.getMessage().getFirst(), "Click to go to the previous page", NamedTextColor.GREEN, previousPageCommand.replace("%player%", player.getName())));
        buttons.put("%next%", buildButton(next.getMessage().getFirst(), "Click to go to the next page", NamedTextColor.GREEN, nextPageCommand.replace("%player%", player.getName())));

        send(player, footer, buttons, placeholders);
    }

    private static void send(Player player, Message footer, Map<String, Component> buttons, Map<String, String> placeholders) {

        for (String line : footer.getMessage()) {
            Component component = parseLine(line, buttons, placeholders);
            player.sendMessage(component);
        }
    }

    private static Component parseLine(String line, Map<String, Component> buttons, Map<String, String> placeholders) {

        Component message = Component.empty();
        int index = 0;

        while (index < line.length()) {

            int nextButtonIndex = -1;
            String matchedKey = null;

            for (String key : buttons.keySet()) {
                int found = line.indexOf(key, index);

                if (found != -1 && (nextButtonIndex == -1 || found < nextButtonIndex)) {
                    nextButtonIndex = found;
                    matchedKey = key;
                }
            }

            if (nextButtonIndex == -1) {
                String remaining = replacePlaceholders(line.substring(index), placeholders);
                message = message.append(text(remaining));
                break;
            }

            if (nextButtonIndex > index) {
                String before = replacePlaceholders(line.substring(index, nextButtonIndex), placeholders);
                message = message.append(text(before));
            }

            message = message.append(buttons.get(matchedKey));

            index = nextButtonIndex + matchedKey.length();
        }

        return message;
    }

    private static String replacePlaceholders(String text, Map<String, String> placeholders) {
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            text = text.replace(entry.getKey(), entry.getValue());
        }
        return text;
    }

    private static Component buildButton(String text, String hover, NamedTextColor hoverColor, String command) {

        return SERIALIZER.deserialize(ColorUtils.translate(text)).hoverEvent(HoverEvent.showText(Component.text(hover).color(hoverColor))).clickEvent(ClickEvent.runCommand(command));
    }

    private static Component text(String text) {
        return SERIALIZER.deserialize(ColorUtils.translate(text));
    }
}