package me.gitcat3.additionalhexxythings;

import me.gitcat3.additionalhexxythings.registry.Patterns;
import net.fabricmc.api.ModInitializer;

public class AdditionalHexxyThings implements ModInitializer {
    public static final String MODID = "additionalhexxythings";

    @Override
    public void onInitialize() {
        Patterns.register();
    }
}
