package br.com.srgenex.utils.message;

import br.com.srgenex.utils.GXUtils;
import br.com.srgenex.utils.color.ColorUtils;
import br.com.srgenex.utils.placeholder.ReplacementPlaceholder;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@SuppressWarnings({"unused"})
@Data
public class Message {

    private final List<String> message;

    public static HashMap<String, List<String>> messages = new HashMap<>();

    public static void load(FileConfiguration c, String path) {
        Objects.requireNonNull(c.getConfigurationSection(path)).getKeys(true).forEach(s ->
                messages.put(s, c.getStringList(path + "." + s)));
    }

    public static void load(FileConfiguration c) {
        load(c, "");
    }

    public static Message of(String path) {
        return new Message(messages.getOrDefault(path, Collections.singletonList("&cUnknown message. Path: " + path + " - Plugin: "+ GXUtils.getInstance().getName())));
    }

    public void send(CommandSender sender, List<ReplacementPlaceholder> replacements) {
        if (sender instanceof Player) send((Player) sender, replacements);
    }

    public void send(Player p, List<ReplacementPlaceholder> replacements) {
        message.forEach(msg -> {
            for (ReplacementPlaceholder replacement : replacements)
                msg = msg.replace(replacement.getPlaceholder(), ColorUtils.translate(replacement.getReplacement()));
            p.sendMessage(ColorUtils.translate(msg.trim()));
        });
    }

    public void send(CommandSender sender, String... replacements) {
        if (sender instanceof Player) {
            message.forEach(msg -> {
                for (String replacement : replacements) {
                    String[] s = replacement.split(", ");
                    msg = msg.replace(s[0], ColorUtils.translate(s[1]));
                }
                sender.sendMessage(msg.trim().replace("&", "§"));
            });
        }
    }

    public void send(Player p, String... replacements) {
        send((CommandSender) p, replacements);
    }

    public void send(OfflinePlayer sender, String... replacements) {
        if (sender.isOnline()) send(sender.getPlayer(), replacements);
    }

    public void broadcast(String... replacements) {
        Bukkit.getOnlinePlayers().forEach(player -> send(player, replacements));
    }

}
