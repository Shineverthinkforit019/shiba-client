package com.example.shiba;

import com.example.shiba.gui.ClickGuiScreen;
import com.example.shiba.module.ModuleManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class ShibaClient implements ClientModInitializer {
    private static KeyBinding openGuiKey;

    @Override
    public void onInitializeClient() {
        openGuiKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.shiba.open_gui",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                "category.shiba"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (openGuiKey.wasPressed()) {
                if (client.currentScreen == null) {
                    client.setScreen(new ClickGuiScreen());
                }
            }
            ModuleManager.tick();
        });

        HudRenderCallback.EVENT.register((context, tickCounter) -> {
            MinecraftClient mc = MinecraftClient.getInstance();
            if (mc.player == null || mc.options.hudHidden) return;
            int x = 4, y = 4;
            if (ModuleManager.COORDS.isEnabled()) {
                String text = String.format("XYZ: %.1f, %.1f, %.1f",
                        mc.player.getX(), mc.player.getY(), mc.player.getZ());
                context.drawText(mc.textRenderer, text, x, y, 0xFFFFFF, true);
                y += 10;
            }
            if (ModuleManager.FPS.isEnabled()) {
                String text = "FPS: " + mc.fpsDebugString;
                context.drawText(mc.textRenderer, text, x, y, 0xFFFFFF, true);
                y += 10;
            }
        });

        System.out.println("[Shiba] Initialized!");
    }
}
