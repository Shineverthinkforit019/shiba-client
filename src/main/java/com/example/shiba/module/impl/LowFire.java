package com.example.shiba.module.impl;

import com.example.shiba.module.Module;
import com.example.shiba.module.Category;
import com.example.shiba.module.settings.NumberSetting;

public class LowFire extends Module {
    public final NumberSetting fireHeight = new NumberSetting("FireHeight", 0.0, 1.0, 0.2, 0.05);

    public LowFire() {
        super("LowFire", "Giảm hiệu ứng lửa trên màn hình", Category.RENDER);
    }

    public float getFireHeight() {
        return isEnabled() ? fireHeight.getValue().floatValue() : 1.0f;
    }
}
