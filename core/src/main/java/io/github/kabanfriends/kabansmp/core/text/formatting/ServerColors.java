package io.github.kabanfriends.kabansmp.core.text.formatting;

import net.kyori.adventure.text.format.TextColor;
import org.bukkit.ChatColor;

public interface ServerColors {

    CustomColor RED_DARK_2 = new CustomColor(85, 0, 0);
    CustomColor RED_DARK = new CustomColor(170, 0, 0, ChatColor.DARK_RED);
    CustomColor RED = new CustomColor(255, 0, 0);
    CustomColor RED_LIGHT = new CustomColor(255, 85, 85, ChatColor.RED);
    CustomColor RED_LIGHT_2 = new CustomColor(255, 170, 170);
    CustomColor RED_LIGHT_3 = new CustomColor(255, 212, 212);
    CustomColor SCARLET = new CustomColor(255, 36, 0);

    CustomColor ORANGE_DARK = new CustomColor(212, 42, 0);
    CustomColor ORANGE = new CustomColor(255, 85, 0);
    CustomColor ORANGE_LIGHT = new CustomColor(255, 127, 85);
    CustomColor ORANGE_LIGHT_2 = new CustomColor(255, 170, 127);

    CustomColor GOLD_DARK_2 = new CustomColor(127, 42, 0);
    CustomColor GOLD_DARK = new CustomColor(170, 85, 0);
    CustomColor GOLD = new CustomColor(255, 170, 0, ChatColor.GOLD);
    CustomColor GOLD_LIGHT = new CustomColor(255, 212, 127);

    CustomColor MUSTARD_DARK_2 = new CustomColor(170, 127, 0);
    CustomColor MUSTARD_DARK = new CustomColor(212, 170, 42);
    CustomColor MUSTARD = new CustomColor(255, 212, 42);

    CustomColor YELLOW_DARK_3 = new CustomColor(127, 127, 42);
    CustomColor YELLOW_DARK_2 = new CustomColor(170, 170, 85);
    CustomColor YELLOW_DARK = new CustomColor(212, 212, 85);
    CustomColor YELLOW = new CustomColor(255, 255, 85, ChatColor.YELLOW);
    CustomColor YELLOW_LIGHT = new CustomColor(255, 255, 170);
    CustomColor YELLOW_LIGHT_2 = new CustomColor(255, 255, 212);

    CustomColor LIME_DARK_2 = new CustomColor(85, 170, 0);
    CustomColor LIME_DARK = new CustomColor(127, 212, 42);
    CustomColor LIME = new CustomColor(170, 255, 85);

    CustomColor GREEN_DARK_4 = new CustomColor(0, 85, 0);
    CustomColor GREEN_DARK_3 = new CustomColor(42, 127, 0);
    CustomColor GREEN_DARK_2 = new CustomColor(0, 170, 0, ChatColor.DARK_GREEN);
    CustomColor GREEN_DARK = new CustomColor(42, 212, 42);
    CustomColor GREEN = new CustomColor(85, 255, 85, ChatColor.GREEN);
    CustomColor GREEN_LIGHT = new CustomColor(127, 255, 127);
    CustomColor GREEN_LIGHT_2 = new CustomColor(170, 255, 170);
    CustomColor GREEN_LIGHT_3 = new CustomColor(212, 255, 212);

    CustomColor TEAL_DARK = new CustomColor(0, 212, 170);
    CustomColor TEAL = new CustomColor(42, 255, 170);
    CustomColor TEAL_LIGHT = new CustomColor(127, 255, 212);

    CustomColor AQUA_DARK_3 = new CustomColor(0, 85, 85);
    CustomColor AQUA_DARK_2 = new CustomColor(0, 127, 127);
    CustomColor AQUA_DARK = new CustomColor(0, 170, 170, ChatColor.DARK_AQUA);
    CustomColor AQUA = new CustomColor(42, 212, 212);
    CustomColor AQUA_LIGHT = new CustomColor(85, 255, 255, ChatColor.AQUA);
    CustomColor AQUA_LIGHT_2 = new CustomColor(170, 255, 255);
    CustomColor AQUA_LIGHT_3 = new CustomColor(212, 255, 255);

    CustomColor SKY_DARK = new CustomColor(42, 112, 212);
    CustomColor SKY = new CustomColor(85, 170, 255);
    CustomColor SKY_LIGHT = new CustomColor(170, 212, 255);

    CustomColor BLUE_DARK_3 = new CustomColor(0, 0, 85);
    CustomColor BLUE_DARK_2 = new CustomColor(0, 0, 170, ChatColor.DARK_BLUE);
    CustomColor BLUE_DARK = new CustomColor(85, 0, 255);
    CustomColor BLUE = new CustomColor(85, 85, 255, ChatColor.BLUE);
    CustomColor BLUE_LIGHT = new CustomColor(112, 112, 255);
    CustomColor BLUE_LIGHT_2 = new CustomColor(170, 170, 255);
    CustomColor BLUE_LIGHT_3 = new CustomColor(212, 212, 255);

    CustomColor PURPLE_DARK_3 = new CustomColor(42, 0, 127);
    CustomColor PURPLE_DARK_2 = new CustomColor(85, 0, 170);
    CustomColor PURPLE_DARK = new CustomColor(127, 42, 212);
    CustomColor PURPLE = new CustomColor(170, 85, 255);
    CustomColor PURPLE_LIGHT = new CustomColor(170, 127, 255);
    CustomColor PURPLE_LIGHT_2 = new CustomColor(212, 170, 255);

    CustomColor MAGENTA_DARK_3 = new CustomColor(85, 0, 85);
    CustomColor MAGENTA_DARK_2 = new CustomColor(127, 0, 127);
    CustomColor MAGENTA_DARK = new CustomColor(170, 0, 170, ChatColor.DARK_PURPLE);
    CustomColor MAGENTA = new CustomColor(212, 42, 212);
    CustomColor MAGENTA_LIGHT = new CustomColor(255, 85, 255, ChatColor.LIGHT_PURPLE);
    CustomColor MAGENTA_LIGHT_2 = new CustomColor(255, 170, 255);
    CustomColor MAGENTA_LIGHT_3 = new CustomColor(255, 212, 255);

    CustomColor PINK_DARK_2 = new CustomColor(213, 0, 127);
    CustomColor PINK_DARK = new CustomColor(255, 0, 170);
    CustomColor PINK = new CustomColor(255, 85, 170);
    CustomColor PINK_LIGHT = new CustomColor(255, 127, 212);

    CustomColor ROSE_DARK_2 = new CustomColor(255, 0, 85);
    CustomColor ROSE_DARK = new CustomColor(255, 85, 127);
    CustomColor ROSE = new CustomColor(255, 127, 170);
    CustomColor ROSE_LIGHT = new CustomColor(255, 170, 212);

    CustomColor BROWN_DARK_2 = new CustomColor(85, 42, 0);
    CustomColor BROWN_DARK = new CustomColor(128, 85, 42);
    CustomColor BROWN = new CustomColor(170, 128, 85);
    CustomColor BROWN_LIGHT = new CustomColor(212, 170, 128);
    CustomColor BROWN_LIGHT_2 = new CustomColor(255, 212, 170);

    CustomColor BLACK = new CustomColor(0, 0, 0, ChatColor.BLACK);
    CustomColor GRAY_DARK_2 = new CustomColor(42, 42, 42);
    CustomColor GRAY_DARK = new CustomColor(85, 85, 85, ChatColor.DARK_GRAY);
    CustomColor GRAY = new CustomColor(128, 128, 128);
    CustomColor GRAY_LIGHT = new CustomColor(170, 170, 170, ChatColor.GRAY);
    CustomColor GRAY_LIGHT_2 = new CustomColor(212, 212, 212);
    CustomColor WHITE = new CustomColor(255, 255, 255, ChatColor.WHITE);

    class CustomColor implements TextColor {

        private final String code;
        private final int rgb;

        public CustomColor(int r, int g, int b) {
            this.rgb = r << 16 | g << 8 | b << 0;
            this.code = ChatColor.COLOR_CHAR + "#" + String.format("%06x", rgb);
        }

        public CustomColor(int r, int g, int b, ChatColor legacyColor) {
            this.rgb = r << 16 | g << 8 | b << 0;
            this.code = legacyColor.toString();
        }

        @Override
        public int value() {
            return rgb;
        }

        @Override
        public String toString() {
            return code;
        }
    }
}
