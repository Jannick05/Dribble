package org.jannick.dribble.commands.moderation;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class Untimeout extends ListenerAdapter {
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        User user = event.getUser();
        String name = event.getName();
        Guild guild = event.getGuild();
        String guildId = guild.getId();
        if (event.getName().equals("unmute")) {
            EmbedBuilder untimeoutBuilder = new EmbedBuilder();
            if (event.getMember().hasPermission(Permission.MODERATE_MEMBERS)) {
                Member member = event.getOption("person").getAsMember();
                member.removeTimeout().queue();
                untimeoutBuilder.setColor(Color.green);
                untimeoutBuilder.setDescription("✅ Unmutede " + member.getUser().getAsTag());
                event.replyEmbeds(untimeoutBuilder.build()).queue();
            } else {
                untimeoutBuilder.setColor(Color.red);
                untimeoutBuilder.setDescription("❌ Adgang nægtet");
                event.replyEmbeds(untimeoutBuilder.build()).setEphemeral(true).queue();
            }
        }
    }
}
