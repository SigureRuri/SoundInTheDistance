package com.github.sigureruri.soundinthedistance.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.TranslatableText;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class SoundInTheDistanceClient implements ClientModInitializer {

    private static KeyBinding muteMasterSoundKeyBinding;
    private static SoundMuter masterSoundMuter;

    private static KeyBinding muteMusicKeyBinding;
    private static SoundMuter musicMuter;

    @Override
    public void onInitializeClient() {
        muteMasterSoundKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.sound_in_the_distance.mute_master_sound",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_M,
                "category.sound_in_the_distance.mute_sound"
        ));
        masterSoundMuter = new SoundMuter(MinecraftClient.getInstance(), SoundCategory.MASTER);

        muteMusicKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.sound_in_the_distance.mute_music",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_N,
                "category.sound_in_the_distance.mute_sound"
        ));
        musicMuter = new SoundMuter(MinecraftClient.getInstance(), SoundCategory.MUSIC);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            ClientPlayerEntity player = client.player;
            if (player == null) return;

            while (muteMasterSoundKeyBinding.wasPressed()) {
                masterSoundMuter.toggleMute();

                if (masterSoundMuter.isMuting()) {
                    player.sendMessage(new TranslatableText("key.sound_in_the_distance.mute_master_sound.pressed.mute"), false);
                } else {
                    player.sendMessage(new TranslatableText("key.sound_in_the_distance.mute_master_sound.pressed.unmute"), false);
                }
            }

            while (muteMusicKeyBinding.wasPressed()) {
                musicMuter.toggleMute();

                if (musicMuter.isMuting()) {
                    player.sendMessage(new TranslatableText("key.sound_in_the_distance.mute_music.pressed.mute"), false);
                } else {
                    player.sendMessage(new TranslatableText("key.sound_in_the_distance.mute_music.pressed.unmute"), false);
                }
            }
        });

    }
}
