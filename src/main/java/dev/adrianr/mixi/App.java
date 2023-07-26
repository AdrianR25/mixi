package dev.adrianr.mixi;

import dev.adrianr.mixi.commands.PlayCommand;
import dev.adrianr.mixi.listeners.GuildReadyListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

public class App {

    public static void main(String[] args) {

        JDABuilder builder = JDABuilder.createDefault("MTExOTMwNjU2NzI0NjkzODE3Mg.GSr8aX.ea03qqY2DzCRnDpOmUEHwU8_LmnnXHdnNLV_vg")
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE)
                .setBulkDeleteSplittingEnabled(false)
                .setActivity(Activity.listening("música"))
                .addEventListeners(new GuildReadyListener(), new PlayCommand());

        JDA jda = builder.build();

    }
}
