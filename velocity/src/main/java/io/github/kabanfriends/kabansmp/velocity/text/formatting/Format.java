package io.github.kabanfriends.kabansmp.velocity.text.formatting;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;

public enum Format {

    GENERIC_NOTIFY("◆", ServerColors.SKY, ServerColors.AQUA_LIGHT_3),
    GENERIC_SUCCESS("◆", ServerColors.LIME, ServerColors.GREEN_LIGHT_3),
    GENERIC_FAIL("◆", ServerColors.RED, ServerColors.RED_LIGHT_3),
    ;

    private final Component prefix;
    private final TextColor textColor;

    Format(String icon, TextColor iconColor, TextColor textColor) {
        prefix = Component.text(icon).color(iconColor);
        this.textColor = textColor;
    }

    public Component apply(Component component) {
        TextComponent.Builder builder = Component.text();
        builder.append(prefix, Component.space(), component.applyFallbackStyle(textColor));
        return builder.build();
    }
}
