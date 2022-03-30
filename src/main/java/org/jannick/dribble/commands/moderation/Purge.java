package org.jannick.dribble.commands.moderation;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.List;

public class Purge extends ListenerAdapter {
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        User user = event.getUser();
        String name = event.getName();
        Guild guild = event.getGuild();
        String guildId = guild.getId();
        if (event.getName().equals("purge")) {
            EmbedBuilder purgeBuilder = new EmbedBuilder();
            if (event.getMember().hasPermission(Permission.MESSAGE_MANAGE)) {
                Integer antal = event.getOption("antal").getAsInt();
                List<Message> messages = event.getChannel().getHistory().retrievePast(antal).complete();
                event.getTextChannel().deleteMessages(messages).queue();
                purgeBuilder.setColor(Color.green);
                purgeBuilder.setDescription("✅ " + antal.toString() + " beskeder fjernet");
                event.replyEmbeds(purgeBuilder.build()).queue();
            } else {
                purgeBuilder.setColor(Color.red);
                purgeBuilder.setDescription("❌ Adgang nægtet");
                event.replyEmbeds(purgeBuilder.build()).setEphemeral(true).queue();
            }
        }
    }
}
