package dev.adrianr.mixi.commands;

import dev.adrianr.mixi.misc.MessageComposer;
import dev.adrianr.mixi.audio.TrackScheduler;
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

        TrackScheduler scheduler = TrackSchedulerInstance.getTrackScheduler();
        if (scheduler != null) scheduler.clearQueue();

        event.getHook().sendMessageEmbeds(MessageComposer.getClearedQueueMessageEmbed()).queue();
    }
}
