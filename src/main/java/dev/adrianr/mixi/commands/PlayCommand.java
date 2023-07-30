package dev.adrianr.mixi.commands;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import dev.adrianr.mixi.misc.ConfigHandler;
import dev.adrianr.mixi.misc.MessageComposer;
import dev.adrianr.mixi.audio.AudioPlayerManagerInstance;
import dev.adrianr.mixi.audio.AudioPlayerSendHandler;
import dev.adrianr.mixi.audio.TrackSchedulerInstance;
import dev.adrianr.mixi.audio.TrackScheduler;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

public class PlayCommand extends ListenerAdapter {

    //private AudioPlayerManager apm =

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        super.onSlashCommandInteraction(event);
        if (!event.getName().equals("play")) return;
        if (!event.isFromGuild()) return;
        if (!event.getChannel().getId().equals(ConfigHandler.getProperty("musicTextChannel"))) return;
        
        String identifier = event.getOption("identifier").getAsString();
        if (!identifier.startsWith("http://") && !identifier.startsWith("https://")) identifier = "ytsearch:" + identifier;

        // Tell discord we received the command, send a thinking... message to the user
        event.deferReply().queue();

        // Get audio manager. This is a singleton, as a player manager manages several thread pools which make no sense to duplicate.
        AudioPlayerManager playerManager = AudioPlayerManagerInstance.getAudioPlayerManager();
        AudioPlayer player = playerManager.createPlayer();


        TrackScheduler trackScheduler = TrackSchedulerInstance.getTrackScheduler(player);
        player.addListener(trackScheduler);

        playerManager.loadItem(identifier, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                trackScheduler.queue(track);
                event.getHook().sendMessageEmbeds(MessageComposer.getAddedTrackMessageEmbed(track)).queue();
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                for (AudioTrack track : playlist.getTracks()) {
                    trackScheduler.queue(track);
                }
                event.getHook().sendMessageEmbeds(MessageComposer.getAddedPlaylistMessageEmbed(playlist)).queue();
            }

            @Override
            public void noMatches() {
                // Notify the user that we've got nothing
                event.getHook().sendMessage("No matches").queue();
            }

            @Override
            public void loadFailed(FriendlyException e) {
                // Notify the user that everything exploded
                event.getHook().sendMessage("Load failed").queue();
            }
        });

        VoiceChannel voiceChannel = event.getGuild().getVoiceChannelById("751817924288970753");
        AudioManager audioManager = event.getGuild().getAudioManager();
        if (!audioManager.isConnected()){
            audioManager.setSelfDeafened(true);
            audioManager.openAudioConnection(voiceChannel);
            audioManager.setSendingHandler(new AudioPlayerSendHandler(player));
        }
    }
}
