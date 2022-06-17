package org.jannick.dribble.events;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.EnumSet;
import java.util.concurrent.TimeUnit;

public class TicketReact extends ListenerAdapter {
    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        Guild guild = event.getGuild();
        String staff = event.getMember().getRoles().get(0).getName();
        EnumSet<Permission> permission = EnumSet.of(Permission.MESSAGE_SEND);
        if (event.getComponentId().equals("close")) {
            if (staff.equals("Owner") || staff.equals("Administrator") || staff.equals("Moderator")) {
                Member klient = guild.getMemberById(event.getTextChannel().getTopic());
                event.getTextChannel().getManager().putMemberPermissionOverride(klient.getIdLong(), null, permission).queue();

                EmbedBuilder ticketBuilder = new EmbedBuilder();
                ticketBuilder.setDescription("Ticketen er blevet lukket");
                ticketBuilder.setColor(Color.decode("#2f3136"));
                ticketBuilder.setFooter(event.getMember().getUser().getAsTag(), event.getMember().getEffectiveAvatarUrl());
                event.replyEmbeds(ticketBuilder.build()).queue();

                Button open = Button.success("open", "Åben ticket").withEmoji(Emoji.fromMarkdown("✅"));
                event.getInteraction().editButton(open).queue();

            } else if (event.getTextChannel().getTopic().equals(event.getMember().getId())) {
                EmbedBuilder ticketBuilder = new EmbedBuilder();
                ticketBuilder.setDescription("Du har ikke adgang til at lukke ticketen");
                ticketBuilder.setColor(Color.decode("#2f3136"));
                ticketBuilder.setFooter(event.getMember().getUser().getAsTag(), event.getMember().getEffectiveAvatarUrl());
                event.replyEmbeds(ticketBuilder.build()).setEphemeral(true).queue();
            } else {
                System.out.println("virkede slet ikke");
            }
        } else if (event.getComponentId().equals("open")) {
            if (staff.equals("Owner") || staff.equals("Administrator") || staff.equals("Moderator")) {
                Member klient = guild.getMemberById(event.getTextChannel().getTopic());
                event.getTextChannel().getManager().putMemberPermissionOverride(klient.getIdLong(), permission, null).queue();

                EmbedBuilder ticketBuilder = new EmbedBuilder();
                ticketBuilder.setDescription("Ticketen er åben igen");
                ticketBuilder.setColor(Color.decode("#2f3136"));
                ticketBuilder.setFooter(event.getMember().getUser().getAsTag(), event.getMember().getEffectiveAvatarUrl());
                event.replyEmbeds(ticketBuilder.build()).queue();

                Button close = Button.danger("close", "Luk ticket").withEmoji(Emoji.fromMarkdown("\uD83D\uDD10"));
                event.getInteraction().editButton(close).queue();
            } else if (event.getTextChannel().getTopic().equals(event.getMember().getId())) {
                EmbedBuilder ticketBuilder = new EmbedBuilder();
                ticketBuilder.setDescription("Du har ikke adgang til at åbne ticketen");
                ticketBuilder.setColor(Color.decode("#2f3136"));
                ticketBuilder.setFooter(event.getMember().getUser().getAsTag(), event.getMember().getEffectiveAvatarUrl());
                event.replyEmbeds(ticketBuilder.build()).setEphemeral(true).queue();
            } else {
                System.out.println("virkede slet ikke");
            }
        }
    }
}
