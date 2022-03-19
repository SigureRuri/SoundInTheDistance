package com.github.sigureruri.soundinthedistance.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.sound.SoundCategory;

public class SoundMuter {

    private final MinecraftClient client;

    private final SoundCategory category;

    // toggleMuteが最初に呼ばれたときにmute状態であれば1.0fが使われ、そうでなければその時の値で上書きされるため、初期値は1.0でよい
    private float masterSoundVolumeBeforeMute = 1.0f;

    public SoundMuter(MinecraftClient client, SoundCategory category) {
        this.client = client;
        this.category = category;
    }

    public void toggleMute() {
        float volume = client.options.getSoundVolume(category);

        if (isMuting()) {
            client.options.setSoundVolume(category, masterSoundVolumeBeforeMute);
        } else {
            client.options.setSoundVolume(category, 0.0f);
            masterSoundVolumeBeforeMute = volume;
        }
    }

    public boolean isMuting() {
        return client.options.getSoundVolume(category) == 0.0f;
    }

}
