package dev.adrianr.mixi.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import dev.adrianr.mixi.App;
import dev.adrianr.mixi.misc.MessageComposer;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class TrackScheduler extends AudioEventAdapter {

    private final AudioPlayer player;
    private final ArrayDeque<AudioTrack> queue = new ArrayDeque<>();
    private final ArrayList<String> retriedTracks = new ArrayList<>();

    public TrackScheduler(AudioPlayer player) {
        this.player = player;
    }

    @Override
    public void onPlayerPause(AudioPlayer player) {
        // Player was paused
    }

    @Override
    public void onPlayerResume(AudioPlayer player) {
        // Player was resumed
    }

    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track) {
        // A track started playing
        queue.remove(track);
        if (retriedTracks.contains(track.getIdentifier())) {
            System.out.println("RETRYING TRACK: " + track.getIdentifier());
        } else {
            System.out.println("STARTED PLAYING TRACK: " + track.getIdentifier());
            App.getJDA().getChannelById(MessageChannel.class, "748313304400920619").sendMessageEmbeds(MessageComposer.getPlayingTrackMessageEmbed(track))
                    .addActionRow(
                            Button.primary("next", "Siguiente"),
                            Button.primary("pause", "Pausar"))
                    .queue();
        }
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        boolean sendMessage = false;

        // retry playing track once if it failed, sometimes fixes error 403
        if (endReason == AudioTrackEndReason.FINISHED && !retriedTracks.contains(track.getIdentifier())) {
            player.playTrack(track);
            retriedTracks.add(track.getIdentifier());
        }

        if (endReason.mayStartNext) {
            // Start next track
            if (!queue.isEmpty()) {
                player.playTrack(queue.getFirst());
            } else {
                sendMessage = true;
            }

        } else if (endReason == AudioTrackEndReason.STOPPED) sendMessage = true;

        if (sendMessage) {
            App.getJDA().getChannelById(MessageChannel.class, "748313304400920619").sendMessageEmbeds(MessageComposer.getFinishedQueueMessageEmbed())
                    .addActionRow(
                            Button.danger("disconnect", "Desconectar"))
                    .queue();
        }

        // endReason == FINISHED: A track finished or died by an exception (mayStartNext = true).
        // endReason == LOAD_FAILED: Loading of a track failed (mayStartNext = true).
        // endReason == STOPPED: The player was stopped.
        // endReason == REPLACED: Another track started playing while this had not finished
        // endReason == CLEANUP: Player hasn't been queried for a while, if you want you can put a
        //                       clone of this back to your queue
    }

    @Override
    public void onTrackException(AudioPlayer player, AudioTrack track, FriendlyException exception) {
        // An already playing track threw an exception (track end event will still be received separately)
    }

    @Override
    public void onTrackStuck(AudioPlayer player, AudioTrack track, long thresholdMs) {
        // Audio track has been unable to provide us any audio, might want to just start a new track
        queue.remove(track);
        if (!queue.isEmpty()) {
            player.playTrack(queue.getFirst());
        }
    }

    public void queue(AudioTrack track) {
        queue.add(track);
        System.out.println("ADDED TRACK TO THE QUEUE: " + track.getInfo().title);
        if (queue.size() == 1 && player.getPlayingTrack() == null) {
            player.playTrack(track);
        }
    }

    public void pause() {
        player.setPaused(!player.isPaused());

    }

    public void next() {
        if (!queue.isEmpty()) {
            player.playTrack(queue.getFirst());
        } else {
            player.stopTrack();
        }
    }

    public void clearQueue() {
        queue.clear();
    }
}
