package dev.adrianr.mixi.listeners;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import dev.adrianr.mixi.audio.AudioPlayerManagerInstance;
import dev.adrianr.mixi.audio.TrackSchedulerInstance;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public class ButtonInteractionListener extends ListenerAdapter {
    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        super.onButtonInteraction(event);

        // Get audio manager. This is a singleton, as a player manager manages several thread pools which make no sense to duplicate.
        AudioPlayerManager playerManager = AudioPlayerManagerInstance.getAudioPlayerManager();
        AudioPlayer player = playerManager.createPlayer();

        switch (event.getComponentId()) {
            case "pause" -> {
                TrackSchedulerInstance.getTrackScheduler(player).pause();
                String label = event.getButton().getLabel().equals("Pausar") ? "Reanudar" : "Pausar";
                event.editButton(Button.primary("pause", label)).queue();
            }
            case "next" -> {
                TrackSchedulerInstance.getTrackScheduler(player).next();
                event.getMessage().delete().queue();
            }
            case "disconnect" -> {
                event.getGuild().getAudioManager().closeAudioConnection();
                event.editButton(event.getButton().asDisabled()).queue();
            }
        }


    }
}
