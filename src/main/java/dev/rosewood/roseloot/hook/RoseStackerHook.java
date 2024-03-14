package dev.rosewood.roseloot.hook;

import dev.rosewood.rosestacker.api.RoseStackerAPI;
import dev.rosewood.rosestacker.stack.StackedEntity;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;

public class RoseStackerHook {

    private static Boolean enabled;

    public static boolean isEnabled() {
        if (enabled != null)
            return enabled;
        return enabled = Bukkit.getPluginManager().getPlugin("RoseStacker") != null;
    }

    public static boolean shouldIgnoreNormalDeathEvent(LivingEntity entity) {
        RoseStackerAPI api = RoseStackerAPI.getInstance();
        StackedEntity stackedEntity = api.getStackedEntity(entity);
        return api.isEntityStackMultipleDeathEventCalled() && stackedEntity != null && stackedEntity.getStackSize() > 1;
    }

}
