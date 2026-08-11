package com.example.shiba.mixin;

import com.example.shiba.module.ModuleManager;
import com.example.shiba.module.impl.HitSpeed;
import com.example.shiba.module.impl.Reach;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class MixinPlayerEntity {

    @Inject(method = "getEntityInteractionRange", at = @At("RETURN"), cancellable = true)
    private void shiba$expandReach(CallbackInfoReturnable<Double> cir) {
        PlayerEntity self = (PlayerEntity) (Object) this;

        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null || self != mc.player) return;

        Reach reach = ModuleManager.REACH;
        if (reach == null || !reach.isEnabled()) return;

        cir.setReturnValue(reach.getReach());
    }

    @Inject(method = "getAttackCooldownProgress", at = @At("RETURN"), cancellable = true)
    private void shiba$overrideCooldown(float baseTime, CallbackInfoReturnable<Float> cir) {
        PlayerEntity self = (PlayerEntity) (Object) this;

        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null) return;

        // So sanh theo UUID thay vi tham chieu object, de ap dung cho ca
        // ban server tich hop (chi hoat dong tren singleplayer/server tu host chung tien trinh).
        if (!self.getUuid().equals(mc.player.getUuid())) return;

        HitSpeed hitSpeed = ModuleManager.HITSPEED;
        if (hitSpeed == null || !hitSpeed.isEnabled()) return;

        float multiplier = hitSpeed.getCooldownMultiplier();
        if (multiplier >= 1.0F) return;

        // multiplier = 0.0 -> cooldown gan nhu bang khong (day nhanh gan len 1.0)
        // multiplier = 1.0 -> giu nguyen cooldown vanilla
        float original = cir.getReturnValue();
        float boosted = Math.min(1.0F, original + (1.0F - multiplier));
        cir.setReturnValue(boosted);
    }
}
