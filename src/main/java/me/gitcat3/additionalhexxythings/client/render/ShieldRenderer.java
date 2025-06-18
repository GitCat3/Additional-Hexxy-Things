package me.gitcat3.additionalhexxythings.client.render;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import org.joml.Matrix4f;
import net.minecraft.client.util.math.MatrixStack;

public class ShieldRenderer {

    private static final float EPSILON = 0.001f; // Small offset to reduce Z-fighting

    public static void drawShield(MatrixStack matrices, float x, float y, float z, float radius) {
        matrices.push();
        matrices.translate(x, y, z);

        drawCube(matrices, radius);
        drawColumn(matrices, radius / 10f, radius * 2);

        matrices.pop();
    }

    private static void drawCube(MatrixStack matrices, float radius) {
        VertexConsumerProvider.Immediate immediate = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
        VertexConsumer vc = immediate.getBuffer(getTranslucentColorLayer());
        Matrix4f model = matrices.peek().getPositionMatrix();

        int r = 0, g = 128, b = 255, a = 100;

        // Bottom face
        drawQuad(vc, model,
                -radius - EPSILON, -radius - EPSILON, -radius - EPSILON,
                radius + EPSILON, -radius - EPSILON, -radius - EPSILON,
                radius + EPSILON, -radius - EPSILON, radius + EPSILON,
                -radius - EPSILON, -radius - EPSILON, radius + EPSILON,
                r, g, b, a);

        // Top face
        drawQuad(vc, model,
                -radius - EPSILON, radius + EPSILON, -radius - EPSILON,
                radius + EPSILON, radius + EPSILON, -radius - EPSILON,
                radius + EPSILON, radius + EPSILON, radius + EPSILON,
                -radius - EPSILON, radius + EPSILON, radius + EPSILON,
                r, g, b, a);

        // North face
        drawQuad(vc, model,
                -radius - EPSILON, -radius - EPSILON, -radius - EPSILON,
                radius + EPSILON, -radius - EPSILON, -radius - EPSILON,
                radius + EPSILON, radius + EPSILON, -radius - EPSILON,
                -radius - EPSILON, radius + EPSILON, -radius - EPSILON,
                r, g, b, a);

        // South face
        drawQuad(vc, model,
                -radius - EPSILON, -radius - EPSILON, radius + EPSILON,
                radius + EPSILON, -radius - EPSILON, radius + EPSILON,
                radius + EPSILON, radius + EPSILON, radius + EPSILON,
                -radius - EPSILON, radius + EPSILON, radius + EPSILON,
                r, g, b, a);

        // West face
        drawQuad(vc, model,
                -radius - EPSILON, -radius - EPSILON, -radius - EPSILON,
                -radius - EPSILON, -radius - EPSILON, radius + EPSILON,
                -radius - EPSILON, radius + EPSILON, radius + EPSILON,
                -radius - EPSILON, radius + EPSILON, -radius - EPSILON,
                r, g, b, a);

        // East face
        drawQuad(vc, model,
                radius + EPSILON, -radius - EPSILON, -radius - EPSILON,
                radius + EPSILON, -radius - EPSILON, radius + EPSILON,
                radius + EPSILON, radius + EPSILON, radius + EPSILON,
                radius + EPSILON, radius + EPSILON, -radius - EPSILON,
                r, g, b, a);

        immediate.draw();
    }

    private static void drawColumn(MatrixStack matrices, float radius, float height) {
        VertexConsumerProvider.Immediate immediate = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
        VertexConsumer vc = immediate.getBuffer(getTranslucentColorLayer());
        Matrix4f model = matrices.peek().getPositionMatrix();

        float minY = -height / 2;
        float maxY = height / 2;

        int r = 0, g = 200, b = 255, a = 150;

        // Front face
        drawQuad(vc, model,
                -radius - EPSILON, minY - EPSILON, -radius - EPSILON,
                radius + EPSILON, minY - EPSILON, -radius - EPSILON,
                radius + EPSILON, maxY + EPSILON, -radius - EPSILON,
                -radius - EPSILON, maxY + EPSILON, -radius - EPSILON,
                r, g, b, a);

        // Back face
        drawQuad(vc, model,
                -radius - EPSILON, minY - EPSILON, radius + EPSILON,
                radius + EPSILON, minY - EPSILON, radius + EPSILON,
                radius + EPSILON, maxY + EPSILON, radius + EPSILON,
                -radius - EPSILON, maxY + EPSILON, radius + EPSILON,
                r, g, b, a);

        // Left face
        drawQuad(vc, model,
                -radius - EPSILON, minY - EPSILON, -radius - EPSILON,
                -radius - EPSILON, minY - EPSILON, radius + EPSILON,
                -radius - EPSILON, maxY + EPSILON, radius + EPSILON,
                -radius - EPSILON, maxY + EPSILON, -radius - EPSILON,
                r, g, b, a);

        // Right face
        drawQuad(vc, model,
                radius + EPSILON, minY - EPSILON, -radius - EPSILON,
                radius + EPSILON, minY - EPSILON, radius + EPSILON,
                radius + EPSILON, maxY + EPSILON, radius + EPSILON,
                radius + EPSILON, maxY + EPSILON, -radius - EPSILON,
                r, g, b, a);

        immediate.draw();
    }

    private static void drawQuad(VertexConsumer vc, Matrix4f model,
                                 float x1, float y1, float z1,
                                 float x2, float y2, float z2,
                                 float x3, float y3, float z3,
                                 float x4, float y4, float z4,
                                 int r, int g, int b, int a) {
        vc.vertex(model, x1, y1, z1).color(r, g, b, a).light(0xF000F0).next();
        vc.vertex(model, x2, y2, z2).color(r, g, b, a).light(0xF000F0).next();
        vc.vertex(model, x3, y3, z3).color(r, g, b, a).light(0xF000F0).next();
        vc.vertex(model, x4, y4, z4).color(r, g, b, a).light(0xF000F0).next();
    }

    private static RenderLayer getTranslucentColorLayer() {
        return RenderLayer.of(
                "translucent_color_layer",
                VertexFormats.POSITION_COLOR_LIGHT,
                VertexFormat.DrawMode.QUADS,
                256,
                false,
                true,
                RenderLayer.MultiPhaseParameters.builder()
                        .program(RenderPhase.POSITION_COLOR_LIGHTMAP_PROGRAM)
                        .transparency(RenderPhase.TRANSLUCENT_TRANSPARENCY)
                        .cull(RenderPhase.DISABLE_CULLING)
                        .lightmap(RenderPhase.ENABLE_LIGHTMAP)
                        .build(true)
        );
    }
}