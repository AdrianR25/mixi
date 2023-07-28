package dev.adrianr.mixi;

import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

public class MessageComposer {
    public static MessageEmbed getPlayingTrackMessageEmbed(AudioTrack track) {
        EmbedBuilder builder = new EmbedBuilder();

        builder.setColor(new Color(87, 242, 135));

        builder.setAuthor("Reproduciendo ahora");

        builder.setTitle(track.getInfo().title, track.getInfo().uri);
        builder.setDescription(track.getInfo().author);
        builder.addField("Duración", Utils.formatMillisToDuration(track.getInfo().length), true);
        return builder.build();
    }

    public static MessageEmbed getFinishedQueueMessageEmbed() {
        EmbedBuilder builder = new EmbedBuilder();

        builder.setColor(new Color(237, 66, 69));

        builder.setAuthor("Se ha terminado de reproducir la cola");

        return builder.build();
    }

    public static MessageEmbed getAddedTrackMessageEmbed(AudioTrack track) {
        EmbedBuilder builder = new EmbedBuilder();

        builder.setColor(new Color(88, 101, 242));

        builder.setAuthor("Pista añadida a la cola");

        builder.setTitle(track.getInfo().title, track.getInfo().uri);
        builder.setDescription(track.getInfo().author);

        return builder.build();
    }

    public static MessageEmbed getAddedPlaylistMessageEmbed(AudioPlaylist playlist) {
        EmbedBuilder builder = new EmbedBuilder();

        builder.setColor(new Color(88, 101, 242));

        builder.setAuthor("Lista de reproducción añadida a la cola");

        builder.setTitle(playlist.getName());
        builder.addField("Pistas", String.valueOf(playlist.getTracks().size()), true);

        long totalDuration = 0;
        for (AudioTrack track : playlist.getTracks()) {
            totalDuration += track.getDuration();
        }
        builder.addField("Duración", Utils.formatMillisToDuration(totalDuration), true);

        return builder.build();
    }

    public static MessageEmbed getClearedQueueMessageEmbed() {
        EmbedBuilder builder = new EmbedBuilder();

        builder.setColor(new Color(254, 231, 92));

        builder.setAuthor("Se ha vaciado la cola");

        return builder.build();
    }
}
