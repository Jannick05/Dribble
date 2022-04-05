package org.jannick.dribble.commands.utilities;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class Emotes extends ListenerAdapter  {
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        Guild guild = event.getGuild();
        if (event.getName().equals("emotes")) {
            List<Emote> emotes = guild.getEmotes();
            EmbedBuilder emoteBuilder = new EmbedBuilder();
            emoteBuilder.setColor(Color.decode("#2f3136"));
            emoteBuilder.setTitle("Discorden " + guild.getName() + " har følgende emotes:");
            emoteBuilder.setDescription(emotes.stream().map(Emote::getAsMention).collect(Collectors.joining()));
            event.replyEmbeds(emoteBuilder.build()).setEphemeral(true).queue();
        }
    }
}
