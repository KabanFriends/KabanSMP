package io.github.kabanfriends.kabansmp.injector.mixin.accessor;

import net.minecraft.world.level.block.entity.trialspawner.TrialSpawner;
import net.minecraft.world.level.block.entity.trialspawner.TrialSpawnerConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(TrialSpawner.class)
public interface TrialSpawnerAccessor {

    @Accessor("config") @Mutable
    void setConfig(TrialSpawnerConfig config);
}
