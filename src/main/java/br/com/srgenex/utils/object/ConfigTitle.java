package br.com.srgenex.utils.object;

import br.com.srgenex.utils.placeholder.ReplacementPlaceholder;
import lombok.Data;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.title.Title;
import org.bukkit.entity.Player;

import java.util.List;

@Data
public class ConfigTitle {

    private final String title;
    private final String subtitle;

    public void send(Player p, String... replacements){
        String t1 = title;
        String t2 = subtitle;
        for (String replacement : replacements) {
            t1 = t1.replace(replacement.split(", ")[0], replacement.split(", ")[1]);
            t2 = t2.replace(replacement.split(", ")[0], replacement.split(", ")[1]);
        }
        Component t1Component = LegacyComponentSerializer.legacyAmpersand().deserialize(t1);
        Component t2Component = LegacyComponentSerializer.legacyAmpersand().deserialize(t2);
        p.showTitle(Title.title(t1Component, t2Component));
    }

    public void send(Player p, List<ReplacementPlaceholder> replacements){
        String t1 = title;
        String t2 = subtitle;
        for (ReplacementPlaceholder replacement : replacements) {
            t1 = t1.replace(replacement.getPlaceholder(), replacement.getReplacement());
            t2 = t2.replace(replacement.getPlaceholder(), replacement.getReplacement());
        }
        Component t1Component = LegacyComponentSerializer.legacyAmpersand().deserialize(t1);
        Component t2Component = LegacyComponentSerializer.legacyAmpersand().deserialize(t2);
        p.showTitle(Title.title(t1Component, t2Component));
    }

}
