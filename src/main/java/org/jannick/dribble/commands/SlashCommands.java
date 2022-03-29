package org.jannick.dribble.commands;

import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SlashCommands extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        User user = event.getUser();
        String name = event.getName();
        Guild guild = event.getGuild();
        String guildId = guild.getId();
        if (event.getName().equals("info")) {
            event.reply("Click the buttons for more info").setEphemeral(true)
                    .addActionRow( // link buttons don't send events, they just open a link in the browser when clicked
                            Button.link("https://github.com/DV8FromTheWorld/JDA", "GitHub")
                                    .withEmoji(Emoji.fromMarkdown("<:github:849286315580719104>")), // Link Button with label and emoji
                            Button.link("https://ci.dv8tion.net/job/JDA/javadoc/", "Javadocs")) // Link Button with only a label
                    .queue();
        } else if (event.getName().equals("purge")) {
            Integer antal = event.getOption("antal").getAsInt();
            List<Message> messages = event.getChannel().getHistory().retrievePast(antal).complete();
            event.getTextChannel().deleteMessages(messages).queue();
            event.getChannel().sendMessage("✅ " + antal.toString() + " beskeder fjernet!").queue(m ->
            m.delete().queueAfter(5, TimeUnit.SECONDS));

        }
    }
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if (event.getComponentId().equals("emoji")) {
            event.editMessage("That button didn't say click me").queue(); // update the message
        }
    }
}
