package org.jannick.dribble.commands.utilities;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.awt.*;

public class Banner extends ListenerAdapter {
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        User user = event.getUser();
        String name = event.getName();
        Guild guild = event.getGuild();
        String guildId = guild.getId();
        if (event.getName().equals("banner")) {
            EmbedBuilder bannerBuilder = new EmbedBuilder();
            bannerBuilder.setColor(Color.CYAN);
            Member member = event.getOption("person", event.getMember(), OptionMapping::getAsMember);
            bannerBuilder.setTitle(member.getUser().getAsTag() + "'s Banner");
            bannerBuilder.setImage(member.getUser().retrieveProfile().map(User.Profile::getBannerUrl).complete());
            event.replyEmbeds(bannerBuilder.build()).setEphemeral(false).queue();
        }
    }
}
