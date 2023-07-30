package dev.adrianr.mixi;

import dev.adrianr.mixi.commands.ClearCommand;
import dev.adrianr.mixi.commands.PlayCommand;
import dev.adrianr.mixi.listeners.ButtonInteractionListener;
import dev.adrianr.mixi.listeners.GuildReadyListener;
import dev.adrianr.mixi.misc.ConfigHandler;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

public class App {

    private static JDA jda;

    public static void main(String[] args) {

        String token = ConfigHandler.getProperty("botToken");
        if (token.equals("YOUR TOKEN HERE")) {
            System.out.println("Bot is running for the first time. Please set it up in the config.properties file.");
            return;
        }

        JDABuilder builder = JDABuilder.createDefault(token)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE)
                .setBulkDeleteSplittingEnabled(false)
                .setActivity(Activity.listening("música"))
                .addEventListeners(new GuildReadyListener(), new PlayCommand(), new ClearCommand(), new ButtonInteractionListener());

        jda = builder.build();

    }

    public static JDA getJDA(){
        return jda;
    }
}
