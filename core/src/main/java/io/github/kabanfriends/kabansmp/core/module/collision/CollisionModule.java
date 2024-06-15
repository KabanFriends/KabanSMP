package io.github.kabanfriends.kabansmp.core.module.collision;

import io.github.kabanfriends.kabansmp.core.config.LanguageConfig;
import io.github.kabanfriends.kabansmp.core.module.Module;
import io.github.kabanfriends.kabansmp.core.text.Components;
import io.github.kabanfriends.kabansmp.core.module.collision.event.CollisionEventHandler;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.translation.GlobalTranslator;

public class CollisionModule implements Module {

    public static Component kickReason;

    @Override
    public void load() {
        kickReason = GlobalTranslator.translator().translate(Components.translatable("collision.kickReason"), LanguageConfig.DEFAULT_LOCALE.get());

        registerEvents(new CollisionEventHandler());
    }
}
