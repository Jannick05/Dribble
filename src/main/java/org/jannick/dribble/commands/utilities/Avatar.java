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
        if (event.getName().equals("avatar")) {
            EmbedBuilder avatarBuilder = new EmbedBuilder();
            Member member = event.getOption("person", event.getMember(), OptionMapping::getAsMember);
            String avatarId = member.getUser().getAvatarId();
            if (avatarId == null) {
                avatarBuilder.setColor(Color.RED);
                avatarBuilder.setDescription(":x: Denne person har ikke et avatar");
                event.replyEmbeds(avatarBuilder.build()).setEphemeral(true).queue();
            } else if (avatarId.startsWith("a_")) {
                avatarBuilder.setColor(Color.CYAN);
                avatarBuilder.setTitle(member.getUser().getAsTag() + "'s Avatar");
                avatarBuilder.setImage("https://cdn.discordapp.com/avatars/" + member.getId() + "/" + avatarId + ".gif?size=512");
                event.replyEmbeds(avatarBuilder.build()).setEphemeral(false).queue();
            } else {
                avatarBuilder.setColor(Color.CYAN);
                avatarBuilder.setTitle(member.getUser().getAsTag() + "'s Avatar");
                avatarBuilder.setImage("https://cdn.discordapp.com/avatars/" + member.getId() + "/" + avatarId + ".png?size=512");
                event.replyEmbeds(avatarBuilder.build()).setEphemeral(false).queue();
            }
        }
    }
}
