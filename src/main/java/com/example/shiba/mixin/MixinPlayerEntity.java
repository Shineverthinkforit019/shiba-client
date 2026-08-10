package com.example.shiba.mixin;

import com.example.shiba.module.ModuleManager;
import com.example.shiba.module.impl.HitSpeed;
import com.example.shiba.module.impl.Reach;
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

    @ModifyVariable(method = "getAttackDistance", at = @At("HEAD"), argsOnly = true)
    private float modifyAttackDistance(float distance) {
        Reach reach = ModuleManager.REACH;
        if (reach != null && reach.isEnabled()) {
            return reach.getReach();
        }
        return distance;
    }
