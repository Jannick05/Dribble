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
        if (event.getName().equals("banner")) {
            EmbedBuilder bannerBuilder = new EmbedBuilder();
            Member member = event.getOption("person", event.getMember(), OptionMapping::getAsMember);
            String bannerId = member.getUser().retrieveProfile().map(User.Profile::getBannerId).complete();
            if (bannerId == null) {
                bannerBuilder.setColor(Color.RED);
                bannerBuilder.setDescription(":x: Denne person har ikke et banner");
                event.replyEmbeds(bannerBuilder.build()).setEphemeral(true).queue();
            } else if (bannerId.startsWith("a_")) {
                bannerBuilder.setColor(Color.CYAN);
                bannerBuilder.setTitle(member.getUser().getAsTag() + "'s Banner");
                bannerBuilder.setImage("https://cdn.discordapp.com/banners/" + member.getId() + "/" + bannerId + ".gif?size=1024");
                event.replyEmbeds(bannerBuilder.build()).setEphemeral(false).queue();
            } else {
                bannerBuilder.setColor(Color.CYAN);
                bannerBuilder.setTitle(member.getUser().getAsTag() + "'s Banner");
                bannerBuilder.setImage("https://cdn.discordapp.com/banners/" + member.getId() + "/" + bannerId + ".png?size=1024");
                event.replyEmbeds(bannerBuilder.build()).setEphemeral(false).queue();
            }
        }
    }
}
