package dev.rosewood.roseloot.loot.condition.tags;

import dev.rosewood.roseloot.loot.LootContext;
import dev.rosewood.roseloot.loot.condition.LootCondition;

public class HumidityCondition extends LootCondition {

    private double humidity;

    public HumidityCondition(String tag) {
        super(tag);
    }

    @Override
    public boolean checkInternal(LootContext context) {
        return context.getLocation().getBlock().getHumidity() >= this.humidity;
    }

    @Override
    public boolean parseValues(String[] values) {
        if (values.length == 0)
            return false;

        try {
            String value = values[0];
            this.humidity = Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}