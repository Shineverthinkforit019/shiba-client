package com.example.shiba.mixin;

import com.example.shiba.module.ModuleManager;
import com.example.shiba.module.impl.Reach;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ClientPlayerInteractionManager.class)
public class MixinClientPlayerInteractionManager {

    @ModifyVariable(method = "attackEntity", at = @At("HEAD"), argsOnly = true, ordinal = 0)
    private Entity modifyAttackTarget(Entity target) {
        Reach reach = ModuleManager.REACH;
        if (reach != null && reach.isEnabled()) {
            // Nếu target xa hơn reach, trả về null để không đánh
            if (ModuleManager.mc.player.distanceTo(target) > reach.getReach()) {
                return null;
            }
        }
        return target;
    }
}
