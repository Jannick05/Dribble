package org.jannick.dribble.commands.utilities;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.awt.*;

public class Avatar extends ListenerAdapter {
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        User user = event.getUser();
        String name = event.getName();
        Guild guild = event.getGuild();
        String guildId = guild.getId();
        if (event.getName().equals("avatar")) {
            EmbedBuilder avatarBuilder = new EmbedBuilder();
            avatarBuilder.setColor(Color.CYAN);
            Member member = event.getOption("person", event.getMember(), OptionMapping::getAsMember);
            avatarBuilder.setTitle(member.getUser().getAsTag() + "'s Avatar");
            avatarBuilder.setImage(member.getEffectiveAvatarUrl());
            event.replyEmbeds(avatarBuilder.build()).setEphemeral(false).queue();
        }
    }
}
