package com.example.shiba.mixin;

import com.example.shiba.module.ModuleManager;
import com.example.shiba.module.impl.Reach;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ClientPlayerInteractionManager.class)
public class MixinClientPlayerInteractionManager {

    @ModifyConstant(method = "attackEntity", constant = @Constant(doubleValue = 6.0))
    private double modifyAttackReach(double original) {
        Reach reach = ModuleManager.REACH;
        if (reach != null && reach.isEnabled()) {
            return reach.getReach();
        }
        return original;
    }
}
