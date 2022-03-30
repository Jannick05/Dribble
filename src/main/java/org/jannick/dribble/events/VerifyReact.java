package org.jannick.dribble.events;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.requests.restaction.AuditableRestAction;

public class VerifyReact extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        if (event.getAuthor().isBot()) return;
        Message message = event.getMessage();
        String content = message.getContentRaw();
        if (content.equals("verifyMessage")) {
            if (event.getMember().hasPermission(Permission.MANAGE_SERVER)) {
                MessageChannel channel = event.getChannel();
                channel.sendMessage("> Nedenunder skal du vælge __✅ Verify,__ for at få **adgang** til discord.")
                        .setActionRow(
                                Button.success("verify", "Verify")
                                        .withEmoji(Emoji.fromMarkdown("✅"))
                        )
                        .queue();
            }
        }
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if (event.getComponentId().equals("verify")) {
            event.reply("Du er blevet verificeret!").setEphemeral(true).queue();

            Member member = event.getMember();
            Role role = event.getGuild().getRoleById(958380098061467648L);
            AuditableRestAction<Void> action = event
                    .getGuild()
                    .addRoleToMember(member, role);
            action.queue();
        }
    }
}
