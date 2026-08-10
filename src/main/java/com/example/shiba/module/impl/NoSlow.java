package com.example.shiba.module.impl;

import com.example.shiba.module.Module;
import com.example.shiba.module.Category;
import com.example.shiba.module.settings.BooleanSetting;

public class NoSlow extends Module {
    public final BooleanSetting noBlockSlow = new BooleanSetting("NoBlockSlow", true);
    public final BooleanSetting noBowSlow = new BooleanSetting("NoBowSlow", true);
    public final BooleanSetting noFoodSlow = new BooleanSetting("NoFoodSlow", true);

    public NoSlow() {
        super("NoSlow", "Không bị chậm khi chặn/bow/ăn", Category.MOVEMENT);
    }
}
