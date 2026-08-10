package com.example.shiba;

import com.example.shiba.config.ConfigManager;
import com.example.shiba.gui.ClickGuiScreen;
import com.example.shiba.module.ModuleManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class ShibaClient implements ClientModInitializer {
    private static KeyBinding openGuiKey;

    @Override
    public void onInitializeClient() {
        // Load config
        ConfigManager.load();

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

        // Save config khi game exit
        Runtime.getRuntime().addShutdownHook(new Thread(ConfigManager::save));

        System.out.println("[Shiba] Initialized with Config System!");
    }
}
