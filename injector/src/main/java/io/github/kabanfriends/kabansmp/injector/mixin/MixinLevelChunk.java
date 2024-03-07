package io.github.kabanfriends.kabansmp.injector.mixin;

import io.github.kabanfriends.kabansmp.injector.api.MixinConfigAPI;
import io.github.kabanfriends.kabansmp.injector.mixin.accessor.TrialSpawnerAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.entity.trialspawner.TrialSpawner;
import net.minecraft.world.level.block.entity.trialspawner.TrialSpawnerConfig;
import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LevelChunk.class)
public class MixinLevelChunk {

    private BlockEntity blockEntity;

    @ModifyArg(method = "lambda$updateBlockEntityTicker$5", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/chunk/LevelChunk;createTicker(Lnet/minecraft/world/level/block/entity/BlockEntity;Lnet/minecraft/world/level/block/entity/BlockEntityTicker;)Lnet/minecraft/world/level/block/entity/TickingBlockEntity;"))
    public <T extends BlockEntity> T getBlockEntity(T blockEntity) {
        this.blockEntity = blockEntity;
        return blockEntity;
    }

    @Redirect(method = "lambda$updateBlockEntityTicker$5", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;addBlockEntityTicker(Lnet/minecraft/world/level/block/entity/TickingBlockEntity;)V"))
    public void filterBlockEntity(ServerLevel level, TickingBlockEntity ticker) {
        if (MixinConfigAPI.filterBlockEntities) {
            if (blockEntity instanceof TheEndGatewayBlockEntity gateway) {
                gateway.age = 1000;
                return;
            } else if (blockEntity instanceof SpawnerBlockEntity spawnerBlock) {
                BaseSpawner spawner = spawnerBlock.getSpawner();
                spawner.requiredPlayerRange = 0;
                spawner.setEntityId(EntityType.AREA_EFFECT_CLOUD, level, level.getRandom(), spawnerBlock.getBlockPos());
                return;
            } else if (blockEntity instanceof TrialSpawnerBlockEntity spawnerBlock) {
                TrialSpawner spawner = spawnerBlock.getTrialSpawner();
                ((TrialSpawnerAccessor)(Object) spawner).setConfig(new TrialSpawnerConfig(
                        1, 1, 0f, 0f, 0f ,0f, 1000, 1000,
                        SimpleWeightedRandomList.empty(),
                        SimpleWeightedRandomList.empty()
                ));
                return;
            }
        }
        level.addBlockEntityTicker(ticker);
    }

}
