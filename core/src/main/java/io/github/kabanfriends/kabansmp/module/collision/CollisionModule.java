package io.github.kabanfriends.kabansmp.module.collision;

import io.github.kabanfriends.kabansmp.config.LanguageConfig;
import io.github.kabanfriends.kabansmp.module.Module;
import io.github.kabanfriends.kabansmp.module.collision.event.CollisionEventHandler;
import io.github.kabanfriends.kabansmp.text.Components;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.translation.GlobalTranslator;

public class CollisionModule implements Module {

    public static Component kickReason;

    @Override
    public void load() {
        kickReason = GlobalTranslator.translator().translate(Components.translatable("collision.kickReason"), LanguageConfig.defaultLocale);

        registerEvents(new CollisionEventHandler());
    }
}
