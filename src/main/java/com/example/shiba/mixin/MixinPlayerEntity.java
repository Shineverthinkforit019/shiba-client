package com.example.shiba.mixin;

import com.example.shiba.module.ModuleManager;
import com.example.shiba.module.impl.HitSpeed;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(PlayerEntity.class)
public class MixinPlayerEntity {

    @ModifyVariable(method = "getAttackCooldownProgress", at = @At("HEAD"), argsOnly = true)
    private float modifyCooldown(float progress) {
        HitSpeed hs = ModuleManager.HITSPEED;
        if (hs != null && hs.isEnabled()) {
            return progress * hs.getSpeed();
        }
        return progress;
    }
}
