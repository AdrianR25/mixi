package dev.adrianr.mixi.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

public class TrackSchedulerInstance {

    private static TrackScheduler trackScheduler;

    public static TrackScheduler getTrackScheduler(AudioPlayer player) {
        if (trackScheduler == null) {
            trackScheduler = new TrackScheduler(player);
        }
        return trackScheduler;
    }

}
