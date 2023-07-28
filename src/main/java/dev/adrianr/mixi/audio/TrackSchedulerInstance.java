package dev.adrianr.mixi.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

public class TrackSchedulerInstance {

    private static TrackScheduler trackScheduler;

    /**
     * Creates a TrackScheduler instance if there isn't one and returns it
     * @param player
     * @return the TrackScheduler instance
     */
    public static TrackScheduler getTrackScheduler(AudioPlayer player) {
        if (trackScheduler == null) {
            trackScheduler = new TrackScheduler(player);
        }
        return trackScheduler;
    }

    /**
     * @return the TrackSheduler instance if there is one or null otherwise
     */
    public static TrackScheduler getTrackScheduler() {
        return trackScheduler;
    }

    public static void destroy() {
        trackScheduler = null;
    }

}
