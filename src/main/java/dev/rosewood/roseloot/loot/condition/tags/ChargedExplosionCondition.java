package dev.rosewood.roseloot.loot.condition.tags;

import dev.rosewood.roseloot.loot.ExplosionType;
import dev.rosewood.roseloot.loot.LootContext;
import dev.rosewood.roseloot.loot.condition.LootCondition;

public class ChargedExplosionCondition extends LootCondition {

    public ChargedExplosionCondition(String tag) {
        super(tag);
    }

    @Override
    public boolean checkInternal(LootContext context) {
        return context.getExplosionType() == ExplosionType.CHARGED_ENTITY;
    }

    @Override
    public boolean parseValues(String[] values) {
        return values.length == 0;
    }

}
