package dev.adrianr.mixi;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class MessageComposer {
    public static MessageEmbed getPlayingTrackMessageEmbed(AudioTrack track) {
        EmbedBuilder builder = new EmbedBuilder();

        builder.setAuthor("Reproduciendo ahora");

        builder.setTitle(track.getInfo().title, track.getInfo().uri);
        builder.setDescription(track.getInfo().author);
        builder.addField("Duración", Utils.formatMillisToDuration(track.getInfo().length), false);
        return builder.build();
    }
}
