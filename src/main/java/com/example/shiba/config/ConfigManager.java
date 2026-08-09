package com.example.shiba.config;

import com.example.shiba.module.Module;
import com.example.shiba.module.ModuleManager;
import com.example.shiba.module.impl.Aura;
import com.example.shiba.module.impl.ESP;
import com.example.shiba.module.impl.Hitbox;
import com.example.shiba.module.impl.Reach;
import com.example.shiba.module.impl.TriggerBot;
import com.example.shiba.module.impl.XrayX;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path FILE =
            FabricLoader.getInstance().getConfigDir().resolve("shiba.json");

    private ConfigManager() {}

    public static void load() {
        if (!Files.exists(FILE)) return;
        try (Reader reader = Files.newBufferedReader(FILE)) {
            JsonObject root = GSON.fromJson(reader, JsonObject.class);
            if (root == null) return;

            for (Module m : ModuleManager.getModules()) {
                if (!root.has(m.getName())) continue;
                JsonObject data = root.getAsJsonObject(m.getName());

                if (data.has("enabled")) m.setEnabled(data.get("enabled").getAsBoolean());
                if (data.has("keybind")) m.setKeybind(data.get("keybind").getAsInt());

                if (m instanceof Hitbox hitbox) {
                    if (data.has("expand")) hitbox.expand = data.get("expand").getAsDouble();
                    if (data.has("renderOutline")) hitbox.renderOutline = data.get("renderOutline").getAsBoolean();
                }
                if (m instanceof Reach reach) {
                    if (data.has("reach")) reach.reach = data.get("reach").getAsDouble();
                }
                if (m instanceof TriggerBot tb) {
                    if (data.has("fov")) tb.fov = data.get("fov").getAsDouble();
                    if (data.has("range")) tb.range = data.get("range").getAsDouble();
                    if (data.has("critEnabled")) tb.critEnabled = data.get("critEnabled").getAsBoolean();
                    if (data.has("attackDelayTicks")) tb.attackDelayTicks = data.get("attackDelayTicks").getAsDouble();
                }
                if (m instanceof Aura aura) {
                    if (data.has("range")) aura.range = data.get("range").getAsDouble();
                    if (data.has("smoothness")) aura.smoothness = data.get("smoothness").getAsDouble();
                    if (data.has("rotationSpeed")) aura.rotationSpeed = data.get("rotationSpeed").getAsDouble();
                    if (data.has("attackDelayTicks")) aura.attackDelayTicks = data.get("attackDelayTicks").getAsDouble();
                    if (data.has("mode")) aura.mode = Aura.Mode.valueOf(data.get("mode").getAsString());
                }
                if (m instanceof ESP esp) {
                    if (data.has("range")) esp.range = data.get("range").getAsDouble();
                }
                if (m instanceof XrayX xray) {
                    if (data.has("range")) xray.range = data.get("range").getAsInt();
                    if (data.has("rescanIntervalTicks")) xray.rescanIntervalTicks = data.get("rescanIntervalTicks").getAsInt();
                }
            }
        } catch (IOException | RuntimeException e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        JsonObject root = new JsonObject();

        for (Module m : ModuleManager.getModules()) {
            JsonObject data = new JsonObject();
            data.add("enabled", new JsonPrimitive(m.isEnabled()));
            data.add("keybind", new JsonPrimitive(m.getKeybind()));

            if (m instanceof Hitbox hitbox) {
                data.add("expand", new JsonPrimitive(hitbox.expand));
                data.add("renderOutline", new JsonPrimitive(hitbox.renderOutline));
            }
            if (m instanceof Reach reach) {
                data.add("reach", new JsonPrimitive(reach.reach));
            }
            if (m instanceof TriggerBot tb) {
                data.add("fov", new JsonPrimitive(tb.fov));
                data.add("range", new JsonPrimitive(tb.range));
                data.add("critEnabled", new JsonPrimitive(tb.critEnabled));
                data.add("attackDelayTicks", new JsonPrimitive(tb.attackDelayTicks));
            }
            if (m instanceof Aura aura) {
                data.add("range", new JsonPrimitive(aura.range));
                data.add("smoothness", new JsonPrimitive(aura.smoothness));
                data.add("rotationSpeed", new JsonPrimitive(aura.rotationSpeed));
                data.add("attackDelayTicks", new JsonPrimitive(aura.attackDelayTicks));
                data.add("mode", new JsonPrimitive(aura.mode.name()));
            }
            if (m instanceof ESP esp) {
                data.add("range", new JsonPrimitive(esp.range));
            }
            if (m instanceof XrayX xray) {
                data.add("range", new JsonPrimitive(xray.range));
                data.add("rescanIntervalTicks", new JsonPrimitive(xray.rescanIntervalTicks));
            }

            root.add(m.getName(), data);
        }

        try {
            Files.createDirectories(FILE.getParent());
            try (Writer writer = Files.newBufferedWriter(FILE)) {
                GSON.toJson(root, writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
