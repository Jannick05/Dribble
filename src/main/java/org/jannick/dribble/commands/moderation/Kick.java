package org.jannick.dribble.commands.moderation;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.awt.*;

public class Kick extends ListenerAdapter {
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        User user = event.getUser();
        String name = event.getName();
        Guild guild = event.getGuild();
        String guildId = guild.getId();
        if (event.getName().equals("kick")) {
            EmbedBuilder kickBuilder = new EmbedBuilder();
            if (event.getMember().hasPermission(Permission.KICK_MEMBERS)) {
                Member member = event.getOption("person").getAsMember();
                String reason = event.getOption("grund", "Ingen grund", OptionMapping::getAsString);
                guild.kick(member).reason(reason).queue();
                kickBuilder.setColor(Color.green);
                kickBuilder.setDescription("✅ Kickede " + member.getUser().getAsTag() + " for `" + reason + "`");
                event.replyEmbeds(kickBuilder.build()).queue();
            } else {
                kickBuilder.setColor(Color.red);
                kickBuilder.setDescription("❌ Adgang nægtet");
                event.replyEmbeds(kickBuilder.build()).setEphemeral(true).queue();
            }
        }
    }
}
