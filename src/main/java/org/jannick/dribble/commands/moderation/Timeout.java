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
import java.util.concurrent.TimeUnit;

public class Timeout extends ListenerAdapter {
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("mute")) {
            EmbedBuilder timeoutBuilder = new EmbedBuilder();
            if (event.getMember().hasPermission(Permission.MODERATE_MEMBERS)) {
                Member member = event.getOption("person").getAsMember();
                Long time = event.getOption("tid").getAsLong();
                String reason = event.getOption("grund", "Ingen grund", OptionMapping::getAsString);
                member.timeoutFor(time, TimeUnit.SECONDS).reason(reason).queue();
                timeoutBuilder.setColor(Color.green);
                timeoutBuilder.setDescription("✅ Mutede " + member.getUser().getAsTag() + " for `" + reason + "`" + " i " + time + " sekunder");
                event.replyEmbeds(timeoutBuilder.build()).queue();
            } else {
                timeoutBuilder.setColor(Color.red);
                timeoutBuilder.setDescription("❌ Adgang nægtet");
                event.replyEmbeds(timeoutBuilder.build()).setEphemeral(true).queue();
            }
        }
    }
}
