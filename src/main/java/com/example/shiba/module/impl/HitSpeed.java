package com.example.shiba.module.impl;

import com.example.shiba.module.Category;
import com.example.shiba.module.Module;
import com.example.shiba.module.settings.NumberSetting;

public class HitSpeed extends Module {

    // 1.0 = cooldown vanilla binh thuong, 0.0 = khong cooldown (danh lien tuc)
    public final NumberSetting multiplier = new NumberSetting("Multiplier", 0.0, 1.0, 0.0, 0.05);

    public HitSpeed() {
        super("HitSpeed", "Giam hoac xoa cooldown khi tan cong.", Category.COMBAT);
    }

    public float getCooldownMultiplier() {
        return isEnabled() ? (float) multiplier.getValue() : 1.0F;
    }
}
