package com.example.shiba.config;

import com.example.shiba.module.Module;
import com.example.shiba.module.ModuleManager;
import com.example.shiba.module.impl.Aura;
import com.example.shiba.module.impl.ESP;
import com.example.shiba.module.impl.Hitbox;
import com.example.shiba.module.impl.TriggerBot;
import com.example.shiba.module.impl.XrayX;
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

                saveLegacyFields(m, props);
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

                loadLegacyFields(m, props);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Module cu dung field double/boolean thuong, chua chuyen sang he Setting.
    private static void saveLegacyFields(Module m, Properties props) {
        String prefix = m.getName() + ".";

        if (m instanceof Hitbox hitbox) {
            props.setProperty(prefix + "expand", String.valueOf(hitbox.expand));
            props.setProperty(prefix + "renderOutline", String.valueOf(hitbox.renderOutline));
        }
        if (m instanceof TriggerBot tb) {
            props.setProperty(prefix + "fov", String.valueOf(tb.fov));
            props.setProperty(prefix + "range", String.valueOf(tb.range));
            props.setProperty(prefix + "critEnabled", String.valueOf(tb.critEnabled));
            props.setProperty(prefix + "attackDelayTicks", String.valueOf(tb.attackDelayTicks));
        }
        if (m instanceof Aura aura) {
            props.setProperty(prefix + "range", String.valueOf(aura.range));
            props.setProperty(prefix + "smoothness", String.valueOf(aura.smoothness));
            props.setProperty(prefix + "rotationSpeed", String.valueOf(aura.rotationSpeed));
            props.setProperty(prefix + "attackDelayTicks", String.valueOf(aura.attackDelayTicks));
            props.setProperty(prefix + "mode", aura.mode.name());
        }
        if (m instanceof ESP esp) {
            props.setProperty(prefix + "range", String.valueOf(esp.range));
        }
        if (m instanceof XrayX xray) {
            props.setProperty(prefix + "range", String.valueOf(xray.range));
            props.setProperty(prefix + "rescanIntervalTicks", String.valueOf(xray.rescanIntervalTicks));
        }
    }

    private static void loadLegacyFields(Module m, Properties props) {
        String prefix = m.getName() + ".";

        if (m instanceof Hitbox hitbox) {
            String v = props.getProperty(prefix + "expand");
            if (v != null) hitbox.expand = Double.parseDouble(v);
            String r = props.getProperty(prefix + "renderOutline");
            if (r != null) hitbox.renderOutline = Boolean.parseBoolean(r);
        }
        if (m instanceof TriggerBot tb) {
            String fov = props.getProperty(prefix + "fov");
            if (fov != null) tb.fov = Double.parseDouble(fov);
            String range = props.getProperty(prefix + "range");
            if (range != null) tb.range = Double.parseDouble(range);
            String crit = props.getProperty(prefix + "critEnabled");
            if (crit != null) tb.critEnabled = Boolean.parseBoolean(crit);
            String delay = props.getProperty(prefix + "attackDelayTicks");
            if (delay != null) tb.attackDelayTicks = Double.parseDouble(delay);
        }
        if (m instanceof Aura aura) {
            String range = props.getProperty(prefix + "range");
            if (range != null) aura.range = Double.parseDouble(range);
            String smooth = props.getProperty(prefix + "smoothness");
            if (smooth != null) aura.smoothness = Double.parseDouble(smooth);
            String speed = props.getProperty(prefix + "rotationSpeed");
            if (speed != null) aura.rotationSpeed = Double.parseDouble(speed);
            String delay = props.getProperty(prefix + "attackDelayTicks");
            if (delay != null) aura.attackDelayTicks = Double.parseDouble(delay);
            String mode = props.getProperty(prefix + "mode");
            if (mode != null) aura.mode = Aura.Mode.valueOf(mode);
        }
        if (m instanceof ESP esp) {
            String range = props.getProperty(prefix + "range");
            if (range != null) esp.range = Double.parseDouble(range);
        }
        if (m instanceof XrayX xray) {
            String range = props.getProperty(prefix + "range");
            if (range != null) xray.range = Integer.parseInt(range);
            String rescan = props.getProperty(prefix + "rescanIntervalTicks");
            if (rescan != null) xray.rescanIntervalTicks = Integer.parseInt(rescan);
        }
    }
}
