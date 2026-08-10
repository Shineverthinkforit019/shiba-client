package com.example.shiba.gui;

import com.example.shiba.module.Module;
import net.minecraft.client.gui.DrawContext;
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
if (module instanceof Reach reach) {
            var setting = reach.range;
            double range = setting.getMax() - setting.getMin();
            SliderWidget reachSlider = new SliderWidget(rowX, y, rowW, SLIDER_HEIGHT, Text.literal(""),
                    (setting.getValue() - setting.getMin()) / range) {
                {
                    setMessage(Text.literal("Reach: " + String.format("%.2f", setting.getValue())));
                }
                @Override
                protected void updateMessage() {
                    double v = setting.getMin() + this.value * range;
                    this.setMessage(Text.literal("Reach: " + String.format("%.2f", v)));
                }
                @Override
                protected void applyValue() {
                    setting.setValue(setting.getMin() + this.value * range);
                }
            };
            this.addDrawableChild(reachSlider);
            y += SLIDER_HEIGHT + SPACING;
}
