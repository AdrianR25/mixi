package dev.adrianr.mixi.listeners;

import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

public class GuildReadyListener extends ListenerAdapter {

    @Override
    public void onGuildReady(GuildReadyEvent event) {
        super.onGuildReady(event);
        event.getGuild().updateCommands().addCommands(
                Commands.slash("play", "Busca y reproduce música.")
                        .addOption(OptionType.STRING, "identifier", "El link o búsqueda.")
        ).queue();
        System.out.println("Comandos registrados");
    }
}
