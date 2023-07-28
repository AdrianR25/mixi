package dev.adrianr.mixi.commands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import dev.adrianr.mixi.MessageComposer;
import dev.adrianr.mixi.audio.AudioPlayerManagerInstance;
import dev.adrianr.mixi.audio.TrackSchedulerInstance;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ClearCommand extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        super.onSlashCommandInteraction(event);
        if (!event.getName().equals("clear")) return;
        if (!event.isFromGuild()) return;

        // Tell discord we received the command, send a thinking... message to the user
        event.deferReply().queue();

        // Get audio manager. This is a singleton, as a player manager manages several thread pools which make no sense to duplicate.
        AudioPlayerManager playerManager = AudioPlayerManagerInstance.getAudioPlayerManager();
        AudioPlayer player = playerManager.createPlayer();
        TrackSchedulerInstance.getTrackScheduler(player).clearQueue();

        event.getHook().sendMessageEmbeds(MessageComposer.getClearedQueueMessageEmbed()).queue();
    }
}
