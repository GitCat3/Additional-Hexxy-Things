package me.gitcat3.additionalhexxythings.client;

import com.mojang.blaze3d.systems.RenderSystem;
import me.gitcat3.additionalhexxythings.client.render.ShieldRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;

public class AdditionalHexxyThingsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        WorldRenderEvents.AFTER_ENTITIES.register(context -> {
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.disableCull();
            RenderSystem.depthMask(false);

            MatrixStack matrices = context.matrixStack();
            Vec3d camPos = context.camera().getPos();

            // Call the new ShieldRenderer class with desired position and radius
            ShieldRenderer.drawShield(matrices,
                    (float)(100 - camPos.x),
                    (float)(64 - camPos.y),
                    (float)(100 - camPos.z),
                    10f // radius 10 = 20 blocks across
            );

            RenderSystem.depthMask(true);
            RenderSystem.enableCull();
            RenderSystem.disableBlend();
        });
    }
}
