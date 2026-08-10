package com.example.shiba.mixin;

import com.example.shiba.module.ModuleManager;
import com.example.shiba.module.impl.LowFire;
import net.minecraft.client.gui.hud.InGameOverlayRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(InGameOverlayRenderer.class)
public class MixinInGameOverlayRenderer {

    @ModifyVariable(method = "renderFireOverlay", at = @At("HEAD"), argsOnly = true)
    private static float modifyFireHeight(float height) {
        LowFire lf = ModuleManager.LOWFIRE;
        if (lf != null && lf.isEnabled()) {
            return lf.getFireHeight();
        }
        return height;
    }
}
