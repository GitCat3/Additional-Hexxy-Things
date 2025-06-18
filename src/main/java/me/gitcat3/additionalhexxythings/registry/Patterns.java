package me.gitcat3.additionalhexxythings.registry;

import at.petrak.hexcasting.api.casting.ActionRegistryEntry;
import at.petrak.hexcasting.api.casting.math.HexDir;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import at.petrak.hexcasting.common.lib.hex.HexActions;
import me.gitcat3.additionalhexxythings.AdditionalHexxyThings;
import net.minecraft.registry.Registry;

public class Patterns {
    public static void register() {
        Registry.register(HexActions.REGISTRY, AdditionalHexxyThings.MODID+":"+"testthing", new ActionRegistryEntry(
                HexPattern.fromAngles("qdawawadqdawawa", HexDir.EAST), HexActions.GET_CASTER.action()));
    }
}
