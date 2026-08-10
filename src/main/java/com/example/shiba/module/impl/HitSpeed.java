package com.example.shiba.module.impl;

import com.example.shiba.module.Module;
import com.example.shiba.module.Category;
import com.example.shiba.module.settings.NumberSetting;

public class HitSpeed extends Module {
    public final NumberSetting speed = new NumberSetting("Speed", 1.0, 2.0, 1.25, 0.05);

    public HitSpeed() {
        super("HitSpeed", "Giảm thời gian cooldown giữa các lần đánh", Category.COMBAT);
    }

    public float getSpeed() {
        return isEnabled() ? (float) speed.getValue() : 1.0f;
    }
}
