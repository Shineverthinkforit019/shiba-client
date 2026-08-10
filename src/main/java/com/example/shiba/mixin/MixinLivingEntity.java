package com.example.shiba.mixin;

import com.example.shiba.module.ModuleManager;
import com.example.shiba.module.impl.Velocity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class MixinLivingEntity {

    @Inject(method = "takeKnockback", at = @At("HEAD"), cancellable = true)
    private void onTakeKnockback(double strength, double x, double z, CallbackInfo ci) {
        Velocity vel = ModuleManager.VELOCITY;
        if (vel != null && vel.isEnabled()) {
            float h = vel.getHorizontal();
            float v = vel.getVertical();
            // Nếu cả hai đều là 1.0, không cần sửa
            if (h == 1.0f && v == 1.0f) return;

            // Tính toán lại knockback với tỉ lệ mới
            // (Giữ nguyên hướng, chỉ thay đổi độ lớn)
            double newStrength = strength * v;
            double newX = x * h;
            double newZ = z * h;

            // Hủy method gốc và gọi lại với tham số đã sửa
            ci.cancel();
            // Gọi lại method với tham số mới (cần dùng reflection hoặc gọi trực tiếp)
            // Cách đơn giản: set lại velocity của entity
            LivingEntity entity = (LivingEntity) (Object) this;
            Vec3d currentVel = entity.getVelocity();
            entity.setVelocity(
                currentVel.x + newX,
                currentVel.y + newStrength,
                currentVel.z + newZ
            );
        }
    }
}
