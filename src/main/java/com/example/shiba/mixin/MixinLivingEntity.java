package com.example.shiba.mixin;

import com.example.shiba.module.ModuleManager;
import com.example.shiba.module.impl.Velocity;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public class MixinLivingEntity {

    @ModifyVariable(method = "takeKnockback", at = @At("HEAD"), argsOnly = true)
    private double modifyKnockbackX(double x) {
        Velocity vel = ModuleManager.VELOCITY;
        if (vel != null && vel.isEnabled()) {
            return x * vel.getHorizontal();
        }
        return x;
    }

    @ModifyVariable(method = "takeKnockback", at = @At("HEAD"), argsOnly = true)
    private double modifyKnockbackZ(double z) {
        Velocity vel = ModuleManager.VELOCITY;
        if (vel != null && vel.isEnabled()) {
            return z * vel.getHorizontal();
        }
        return z;
    }

    @ModifyVariable(method = "takeKnockback", at = @At("HEAD"), argsOnly = true)
    private double modifyKnockbackY(double y) {
        Velocity vel = ModuleManager.VELOCITY;
        if (vel != null && vel.isEnabled()) {
            return y * vel.getVertical();
        }
        return y;
    }
}
