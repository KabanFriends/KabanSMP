package io.github.kabanfriends.kabansmp.velocity.text;

import io.github.kabanfriends.kabansmp.translation.language.LanguageTranslator;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.text.TranslationArgument;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

import java.util.*;

public class Components {

    public static Component of(Component... components) {
        return Component.text().append(components).build();
    }

    public static TranslatableComponent translatable(String key, Object... objects) {
        List<TranslationArgument> argumentList = Arrays.stream(objects).map(object -> {
            if (object instanceof String string) {
                return TranslationArgument.component(Component.text(string));
            }
            if (object instanceof Component component) {
                return TranslationArgument.component(component);
            }
            if (object instanceof Integer integer) {
                return TranslationArgument.numeric(integer);
            }
            if (object instanceof Double number) {
                return TranslationArgument.numeric(number);
            }
            if (object instanceof Float number) {
                return TranslationArgument.numeric(number);
            }
            return null;
        }).filter(Objects::nonNull).toList();
        return Component.translatable(key, argumentList);
    }

    public static Component translate(Component component, Locale locale) {
        return LanguageTranslator.recursivelyTranslate(component, locale);
    }

    public static Component newlined(Collection<Component> components) {
        return newlined(components.toArray(new Component[]{}));
    }

    public static Component newlined(Component... components) {
        return Component.join(JoinConfiguration.newlines(), components);
    }

    public static Component legacy(String legacy) {
        return LegacyComponentSerializer.legacySection().deserializeOrNull(legacy);
    }

    public static String legacy(Component legacy) {
        return LegacyComponentSerializer.legacySection().serializeOrNull(legacy);
    }

    public static String plain(Component component) {
        return PlainTextComponentSerializer.plainText().serialize(component);
    }
}
