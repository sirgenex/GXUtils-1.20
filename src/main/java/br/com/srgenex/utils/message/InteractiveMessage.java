package br.com.srgenex.utils.message;

import br.com.srgenex.utils.color.ColorUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;

@SuppressWarnings("unused")
public class InteractiveMessage {

    public static void sendRequestMessage(Player player, Message footer, Message accept, Message decline, String confirmCommand, String cancelCommand){
        footer.getMessage().forEach(line -> {
            Component message = Component.empty();

            for (String word : line.split(" ")) {
                String replaced = word
                        .replace("%player%", player.getName());

                if (word.contains("%accept%")) {
                    String acceptText = accept.getMessage().getFirst();
                    Component acceptComp = LegacyComponentSerializer.legacyAmpersand()
                            .deserialize(ColorUtils.translate(acceptText));

                    acceptComp = acceptComp.hoverEvent(Component.text("Click to accept").color(NamedTextColor.GREEN).asHoverEvent())
                            .clickEvent(ClickEvent.runCommand(confirmCommand.replace("%player%", player.getName())));

                    message = message.append(acceptComp).append(Component.space());
                    continue;
                }

                if (word.contains("%decline%")) {
                    String declineText = decline.getMessage().getFirst();
                    Component declineComp = LegacyComponentSerializer.legacyAmpersand()
                            .deserialize(ColorUtils.translate(declineText));

                    declineComp = declineComp.hoverEvent(Component.text("Click to decline").color(NamedTextColor.RED).asHoverEvent())
                            .clickEvent(ClickEvent.runCommand(cancelCommand.replace("%player%", player.getName())));

                    message = message.append(declineComp).append(Component.space());
                    continue;
                }

                Component normal = LegacyComponentSerializer.legacyAmpersand()
                        .deserialize(ColorUtils.translate(replaced));
                message = message.append(normal).append(Component.space());
            }

            player.sendMessage(message);
        });
    }

    public static void sendPageMessage(Player player, int page, int maxPages, Message footer, Message previous, Message next, String previousPageCommand, String nextPageCommand){
        footer.getMessage().forEach(line -> {
            Component message = Component.empty();

            for (String word : line.split(" ")) {
                String replaced = word
                        .replace("%pages%", String.valueOf(maxPages))
                        .replace("%page%", String.valueOf(page))
                        .replace("%player%", player.getName());

                if (word.contains("%previous%")) {
                    String acceptText = previous.getMessage().getFirst();
                    Component acceptComp = LegacyComponentSerializer.legacyAmpersand()
                            .deserialize(ColorUtils.translate(acceptText));

                    acceptComp = acceptComp.hoverEvent(Component.text("Click to go to the previous page").color(NamedTextColor.GREEN).asHoverEvent())
                            .clickEvent(ClickEvent.runCommand(previousPageCommand.replace("%player%", player.getName())));

                    message = message.append(acceptComp).append(Component.space());
                    continue;
                }

                if (word.contains("%next%")) {
                    String declineText = next.getMessage().getFirst();
                    Component declineComp = LegacyComponentSerializer.legacyAmpersand()
                            .deserialize(ColorUtils.translate(declineText));

                    declineComp = declineComp.hoverEvent(Component.text("Click to go to the next page").color(NamedTextColor.GREEN).asHoverEvent())
                            .clickEvent(ClickEvent.runCommand(nextPageCommand.replace("%player%", player.getName())));

                    message = message.append(declineComp).append(Component.space());
                    continue;
                }

                Component normal = LegacyComponentSerializer.legacyAmpersand()
                        .deserialize(ColorUtils.translate(replaced));
                message = message.append(normal).append(Component.space());
            }

            player.sendMessage(message);
        });
    }

}
