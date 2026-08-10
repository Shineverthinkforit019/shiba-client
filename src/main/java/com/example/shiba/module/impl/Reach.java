package com.example.shiba.module.impl;

import com.example.shiba.module.Module;
import com.example.shiba.module.Category;
import com.example.shiba.module.settings.NumberSetting;

public class Reach extends Module {
    public final NumberSetting range = new NumberSetting("Range", 3.0, 6.0, 3.5, 0.05);

    public Reach() {
        super("Reach", "Tăng khoảng cách tấn công", Category.COMBAT);
    }

    public double getReach() {
        return isEnabled() ? range.getValue() : 3.0;
    }
}
