package dev.adrianr.mixi.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;

public class AudioPlayerManagerInstance {
    private static AudioPlayerManager playerManager;

    public static AudioPlayerManager getAudioPlayerManager() {
        if (playerManager == null) {
            playerManager = new DefaultAudioPlayerManager();
            AudioSourceManagers.registerRemoteSources(playerManager);
        }
        return playerManager;
    }
}
