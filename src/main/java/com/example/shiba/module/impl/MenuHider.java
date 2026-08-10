package com.example.shiba.module.impl;

import com.example.shiba.module.Module;
import com.example.shiba.module.Category;
import com.example.shiba.module.settings.KeybindSetting;

public class MenuHider extends Module {
    public final KeybindSetting toggleKey = new KeybindSetting("ToggleKey", 0);

    public MenuHider() {
        super("MenuHider", "Ẩn menu ClickGUI (giả dạng mod fix lag)", Category.MISC);
    }

    private boolean visible = true;

    public boolean isVisible() {
        return !isEnabled() || visible;
    }

    public void toggleVisibility() {
        visible = !visible;
    }
}
