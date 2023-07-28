package dev.adrianr.mixi.listeners;

import dev.adrianr.mixi.audio.TrackScheduler;
import dev.adrianr.mixi.audio.TrackSchedulerInstance;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public class ButtonInteractionListener extends ListenerAdapter {
    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        super.onButtonInteraction(event);

        TrackScheduler scheduler = TrackSchedulerInstance.getTrackScheduler();

        switch (event.getComponentId()) {
            case "pause" -> {
                if (scheduler != null) scheduler.pause();
                String label = event.getButton().getLabel().equals("Pausar") ? "Reanudar" : "Pausar";
                event.editButton(Button.primary("pause", label)).queue();
            }
            case "next" -> {
                if (scheduler != null) scheduler.next();
                event.getMessage().delete().queue();
            }
            case "disconnect" -> {
                event.getGuild().getAudioManager().closeAudioConnection();
                TrackSchedulerInstance.destroy();
                event.editButton(event.getButton().asDisabled()).queue();
            }
        }


    }
}
