package dev.rosewood.roseloot.loot.item.meta;

import dev.rosewood.roseloot.loot.LootContext;
import dev.rosewood.roseloot.util.LootUtils;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;

public class FireworkItemLootMeta extends ItemLootMeta {

    private Integer power;
    private List<FireworkEffect> fireworkEffects;

    public FireworkItemLootMeta(ConfigurationSection section) {
        super(section);

        if (section.isInt("power")) this.power = LootUtils.clamp(section.getInt("power"), 0, 128);

        ConfigurationSection fireworkEffectSection = section.getConfigurationSection("firework-effects");
        if (fireworkEffectSection != null) {
            this.fireworkEffects = new ArrayList<>();

            for (String key : fireworkEffectSection.getKeys(false)) {
                ConfigurationSection effectSection = fireworkEffectSection.getConfigurationSection(key);
                if (effectSection == null)
                    continue;

                FireworkEffect.Builder builder = FireworkEffect.builder();

                if (effectSection.isString("type")) {
                    String type = effectSection.getString("type");
                    for (FireworkEffect.Type value : FireworkEffect.Type.values()) {
                        if (value.name().equalsIgnoreCase(type)) {
                            builder.with(value);
                            break;
                        }
                    }
                }

                if (effectSection.getBoolean("flicker", false)) builder.withFlicker();
                if (effectSection.getBoolean("trail", false)) builder.withTrail();

                List<String> colors = effectSection.getStringList("colors");
                for (String color : colors) {
                    if (color.startsWith("#")) {
                        try {
                            java.awt.Color awtColor = java.awt.Color.decode(color);
                            builder.withColor(Color.fromRGB(awtColor.getRed(), awtColor.getGreen(), awtColor.getBlue()));
                        } catch (NumberFormatException ignored) { }
                    } else {
                        Color value = LootUtils.FIREWORK_COLORS.get(color.toUpperCase());
                        if (value != null)
                            builder.withColor(value);
                    }
                }

                List<String> fadeColors = effectSection.getStringList("fade-colors");
                for (String color : fadeColors) {
                    if (color.startsWith("#")) {
                        try {
                            builder.withColor(Color.fromRGB(java.awt.Color.decode(color).getRGB()));
                        } catch (NumberFormatException ignored) { }
                    } else {
                        Color value = LootUtils.FIREWORK_COLORS.get(color.toUpperCase());
                        if (value != null)
                            builder.withFade(value);
                    }
                }

                this.fireworkEffects.add(builder.build());
            }
        }
    }

    @Override
    public ItemStack apply(ItemStack itemStack, LootContext context) {
        super.apply(itemStack, context);

        FireworkMeta itemMeta = (FireworkMeta) itemStack.getItemMeta();
        if (itemMeta == null)
            return itemStack;

        if (this.power != null) itemMeta.setPower(this.power);
        if (this.fireworkEffects != null) itemMeta.addEffects(this.fireworkEffects);

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

}