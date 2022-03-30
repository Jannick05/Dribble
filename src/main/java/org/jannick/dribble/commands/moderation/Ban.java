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

public class Ban extends ListenerAdapter {
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        User user = event.getUser();
        String name = event.getName();
        Guild guild = event.getGuild();
        String guildId = guild.getId();
        if (event.getName().equals("ban")) {
            EmbedBuilder banBuilder = new EmbedBuilder();
            if (event.getMember().hasPermission(Permission.BAN_MEMBERS)) {
                Member member = event.getOption("person").getAsMember();
                String reason = event.getOption("grund", "Ingen grund", OptionMapping::getAsString);
                guild.ban(member.getUser(), 0).reason(reason).queue();
                banBuilder.setColor(Color.green);
                banBuilder.setDescription("✅ Bannede " + member.getUser().getAsTag() + " for `" + reason + "`");
                event.replyEmbeds(banBuilder.build()).queue();
            } else {
                banBuilder.setColor(Color.red);
                banBuilder.setDescription("❌ Adgang nægtet");
                event.replyEmbeds(banBuilder.build()).setEphemeral(true).queue();
            }
        }
    }
}
