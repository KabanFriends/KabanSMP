package io.github.kabanfriends.kabansmp.text.formatting;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;

public enum Format {

    GENERIC_NOTIFY("◆", ServerColors.SKY, ServerColors.AQUA_LIGHT_3),
    GENERIC_SUCCESS("◆", ServerColors.LIME, ServerColors.GREEN_LIGHT_3),
    GENERIC_FAIL("◆", ServerColors.RED, ServerColors.RED_LIGHT_3),

    JOIN_MESSAGE("»", ServerColors.LIME, ServerColors.GREEN_LIGHT_3),
    QUIT_MESSAGE("«", ServerColors.RED, ServerColors.RED_LIGHT_3),

    PVP_ENABLED("\uD83D\uDDE1", ServerColors.LIME, ServerColors.GREEN_LIGHT_3),
    PVP_DISABLED("\uD83D\uDDE1", ServerColors.RED, ServerColors.RED_LIGHT_3),

    HOME_NOTIFY("⌂", ServerColors.SKY, ServerColors.AQUA_LIGHT_3),
    HOME_SUCCESS("⌂", ServerColors.LIME, ServerColors.GREEN_LIGHT_3),
    HOME_FAIL("⌂", ServerColors.RED, ServerColors.RED_LIGHT_3),

    HARDCORE_NOTIFY("❣", ServerColors.SKY, ServerColors.AQUA_LIGHT_3),
    HARDCORE_SUCCESS("❣", ServerColors.LIME, ServerColors.GREEN_LIGHT_3),
    HARDCORE_FAIL("❣", ServerColors.RED, ServerColors.RED_LIGHT_3),
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
