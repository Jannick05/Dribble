package org.jannick.dribble.commands.ticket;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.Modal;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.EnumSet;

public class TicketCreate extends ListenerAdapter {
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals("ticket")) {
            TextInput emne = TextInput.create("emne", "Emne", TextInputStyle.SHORT)
                    .setPlaceholder("Skriv hvad emnet er her")
                    .setRequired(true)
                    .setMinLength(2)
                    .setMaxLength(10)
                    .build();

            TextInput beskriv = TextInput.create("beskriv", "Beskriv dit problem lidt mere", TextInputStyle.PARAGRAPH)
                    .setPlaceholder("Skriv mere om problemet her")
                    .setRequired(true)
                    .setMinLength(25)
                    .setMaxLength(1000)
                    .build();

            Modal modal = Modal.create("support", "Support")
                    .addActionRows(ActionRow.of(emne), ActionRow.of(beskriv))
                    .build();

            event.replyModal(modal).queue();
        }
    }
    public void onModalInteraction(@Nonnull ModalInteractionEvent event) {
        Guild guild = event.getGuild();
        Member memberId = event.getMember();
        String memberName = memberId.getUser().getAsTag();
        if (event.getModalId().equals("support")) {
            String email = event.getValue("emne").getAsString();
            String body = event.getValue("beskriv").getAsString();

            System.out.println(email + "\n" + body);

            long role = guild.getPublicRole().getIdLong();
            EnumSet<Permission> permission = EnumSet.of(Permission.VIEW_CHANNEL, Permission.MESSAGE_SEND);
            guild.createTextChannel(memberName, guild.getCategoryById(960901543182352384L))
                    .addRolePermissionOverride(role, null, permission)
                    .addMemberPermissionOverride(event.getMember().getIdLong(), permission, null)

                    .addRolePermissionOverride(958379446258257950L, permission, null)
                    .addRolePermissionOverride(958379529959780432L, permission, null)
                    .addRolePermissionOverride(958379732947308576L, permission, null)

                    .flatMap(c -> c.sendMessage("okay"))
                    .queue();

            event.reply("Din ticket er nu åbnet").setEphemeral(true).queue();
        }
    }
}
