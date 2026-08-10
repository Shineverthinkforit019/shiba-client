package com.example.shiba.mixin;

import com.example.shiba.module.ModuleManager;
import com.example.shiba.module.impl.Reach;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerInteractionManager.class)
public class MixinClientPlayerInteractionManager {

    @Inject(method = "attackEntity", at = @At("HEAD"), cancellable = false)
    private void onAttackEntity(PlayerEntity player, Entity target, CallbackInfo ci) {
        Reach reach = ModuleManager.REACH;
        if (reach != null && reach.isEnabled()) {
            // Kiểm tra khoảng cách với Reach mới
            double reachDistance = reach.getReach();
            if (player.distanceTo(target) > reachDistance) {
                // Nếu quá xa, không đánh (có thể hủy hoặc bỏ qua)
                // Có thể thêm logic để hủy nếu cần
            }
        }
    }
}
