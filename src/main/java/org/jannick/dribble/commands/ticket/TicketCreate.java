package org.jannick.dribble.commands.ticket;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.Modal;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import org.jetbrains.annotations.NotNull;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

public class TicketCreate extends ListenerAdapter {
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals("ticket")) {
            String id = event.getMember().getId();
            Optional<TextChannel> found = event.getGuild().getTextChannels()
                .stream()
                .filter(c -> id.equals(c.getTopic()))
                .findFirst();
            if (found.isPresent()) {
                TextChannel channel = found.get();
                EmbedBuilder ticketBuilder = new EmbedBuilder();
                ticketBuilder.setDescription("Du har allerede en aktiv ticket - " + channel.getAsMention());
                ticketBuilder.setColor(Color.decode("#2f3136"));
                event.replyEmbeds(ticketBuilder.build()).setEphemeral(true).queue();
            } else {
                TextInput emne = TextInput.create("emne", "Emne", TextInputStyle.SHORT)
                        .setPlaceholder("Skriv hvad emnet er her")
                        .setRequired(true)
                        .setMinLength(1)
                        .setMaxLength(30)
                        .build();

                TextInput beskriv = TextInput.create("beskriv", "Beskriv dit problem lidt mere", TextInputStyle.PARAGRAPH)
                        .setPlaceholder("Skriv mere om problemet her")
                        .setRequired(true)
                        .setMinLength(10)
                        .setMaxLength(1000)
                        .build();

                Modal modal = Modal.create("ticket", "Ticket")
                        .addActionRows(ActionRow.of(emne), ActionRow.of(beskriv))
                        .build();

                event.replyModal(modal).queue();
            }
        }
    }

    public void onModalInteraction(@Nonnull ModalInteractionEvent event) {
        Guild guild = event.getGuild();
        Member memberId = event.getMember();
        String memberName = memberId.getUser().getAsTag();
        if (event.getModalId().equals("ticket")) {
            String emne = event.getValue("emne").getAsString();
            String beskriv = event.getValue("beskriv").getAsString();

            long role = guild.getPublicRole().getIdLong();
            EnumSet<Permission> permission = EnumSet.of(Permission.VIEW_CHANNEL, Permission.MESSAGE_SEND);
            EmbedBuilder ticketBuilder = new EmbedBuilder();
            ticketBuilder.setTitle(memberName + "'s ticket");
            ticketBuilder.addField("Beskrivelse:", beskriv, true);
            ticketBuilder.setDescription("**EMNE:** `" + emne + "`");
            ticketBuilder.setColor(Color.decode("#2f3136"));
            ticketBuilder.setFooter(memberName, event.getMember().getEffectiveAvatarUrl());
            guild.createTextChannel(memberName + "-ticket", guild.getCategoryById(960901543182352384L))
                    .setTopic(event.getMember().getId())
                    .addRolePermissionOverride(role, null, permission)
                    .addMemberPermissionOverride(event.getMember().getIdLong(), permission, null)

                    .addRolePermissionOverride(958379446258257950L, permission, null)
                    .addRolePermissionOverride(958379529959780432L, permission, null)
                    .addRolePermissionOverride(958379732947308576L, permission, null)

                    .flatMap(c -> c.sendMessageEmbeds(ticketBuilder.build())
                            .setActionRow(
                                    Button.danger("close", "Luk Ticket")
                                            .withEmoji(Emoji.fromMarkdown("\uD83D\uDD10"))
                            )
                    )
                    .queue(message -> {
                        message.pin().queue();
                    });

            event.reply("Din ticket er nu åbnet").setEphemeral(true).queue();
        }
    }
}
