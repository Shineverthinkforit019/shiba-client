package com.example.shiba.gui;

import com.example.shiba.module.Module;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class ModuleSettingsScreen extends Screen {
    private final Module module;

    public ModuleSettingsScreen(Module module) {
        super(Text.literal(module.getName()));
        this.module = module;
    }

    @Override
    protected void init() {
        super.init();
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Back"), button -> {
            if (this.client != null) this.client.setScreen(null);
        }).dimensions(5, 5, 50, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        context.drawText(textRenderer, "Settings for: " + module.getName(), 10, 30, 0xFFFFFF, false);
        super.render(context, mouseX, mouseY, delta);
    }
}
