package com.example.shiba.config;

import com.example.shiba.module.Module;
import com.example.shiba.module.ModuleManager;
import com.example.shiba.module.settings.BooleanSetting;
import com.example.shiba.module.settings.NumberSetting;
import com.example.shiba.module.settings.ModeSetting;
import com.example.shiba.module.settings.Setting;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.nio.file.Path;
import java.util.Properties;

public class ConfigManager {
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("shiba/config.properties");

    public static void save() {
        try {
            File configFile = CONFIG_PATH.toFile();
            configFile.getParentFile().mkdirs();
            Properties props = new Properties();

            for (Module m : ModuleManager.getModules()) {
                props.setProperty(m.getName() + ".enabled", String.valueOf(m.isEnabled()));
                props.setProperty(m.getName() + ".keybind", String.valueOf(m.getKeybind()));

                for (java.lang.reflect.Field field : m.getClass().getDeclaredFields()) {
                    if (Setting.class.isAssignableFrom(field.getType())) {
                        field.setAccessible(true);
                        Setting setting = (Setting) field.get(m);
                        String key = m.getName() + "." + setting.getName();
                        if (setting instanceof NumberSetting) {
                            props.setProperty(key, String.valueOf(((NumberSetting) setting).getValue()));
                        } else if (setting instanceof BooleanSetting) {
                            props.setProperty(key, String.valueOf(((BooleanSetting) setting).getValue()));
                        } else if (setting instanceof ModeSetting) {
                            props.setProperty(key, ((ModeSetting) setting).getValue());
                        }
                    }
                }
            }

            try (FileOutputStream fos = new FileOutputStream(configFile)) {
                props.store(fos, "Shiba Client Configuration");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void load() {
        try {
            File configFile = CONFIG_PATH.toFile();
            if (!configFile.exists()) return;

            Properties props = new Properties();
            try (FileInputStream fis = new FileInputStream(configFile)) {
                props.load(fis);
            }

            for (Module m : ModuleManager.getModules()) {
                String enabled = props.getProperty(m.getName() + ".enabled");
                if (enabled != null) m.setEnabled(Boolean.parseBoolean(enabled));

                String keybind = props.getProperty(m.getName() + ".keybind");
                if (keybind != null) m.setKeybind(Integer.parseInt(keybind));

                for (java.lang.reflect.Field field : m.getClass().getDeclaredFields()) {
                    if (Setting.class.isAssignableFrom(field.getType())) {
                        field.setAccessible(true);
                        Setting setting = (Setting) field.get(m);
                        String key = m.getName() + "." + setting.getName();
                        String value = props.getProperty(key);
                        if (value == null) continue;

                        if (setting instanceof NumberSetting) {
                            ((NumberSetting) setting).setValue(Double.parseDouble(value));
                        } else if (setting instanceof BooleanSetting) {
                            ((BooleanSetting) setting).setValue(Boolean.parseBoolean(value));
                        } else if (setting instanceof ModeSetting) {
                            ((ModeSetting) setting).setValue(value);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
