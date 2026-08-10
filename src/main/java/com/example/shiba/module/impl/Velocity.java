package com.example.shiba.module.impl;

import com.example.shiba.module.Module;
import com.example.shiba.module.Category;
import com.example.shiba.module.settings.NumberSetting;

public class Velocity extends Module {
    public final NumberSetting horizontal = new NumberSetting("Horizontal", 0.0, 100.0, 75.0, 1.0);
    public final NumberSetting vertical = new NumberSetting("Vertical", 0.0, 100.0, 80.0, 1.0);

    public Velocity() {
        super("Velocity", "Giảm knockback khi bị đánh", Category.COMBAT);
    }

    public float getHorizontal() {
        return isEnabled() ? (float) (horizontal.getValue() / 100.0) : 1.0f;
    }

    public float getVertical() {
        return isEnabled() ? (float) (vertical.getValue() / 100.0) : 1.0f;
    }
}
