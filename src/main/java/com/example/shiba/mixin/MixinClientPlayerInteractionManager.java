package com.example.shiba.mixin;

import com.example.shiba.module.ModuleManager;
import com.example.shiba.module.impl.Reach;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ClientPlayerInteractionManager.class)
public class MixinClientPlayerInteractionManager {

    @ModifyVariable(method = "attackEntity", at = @At("HEAD"), argsOnly = true)
    private Entity modifyTarget(Entity target) {
        Reach reach = ModuleManager.REACH;
        if (reach != null && reach.isEnabled()) {
            double reachDistance = reach.getReach();
            if (ModuleManager.mc != null && ModuleManager.mc.player != null) {
                if (ModuleManager.mc.player.distanceTo(target) > reachDistance) {
                    return null;
                }
            }
        }
        return target;
    }
}
