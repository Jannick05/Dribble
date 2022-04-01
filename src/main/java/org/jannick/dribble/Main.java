package org.jannick.dribble;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.jannick.dribble.commands.moderation.Ban;
import org.jannick.dribble.commands.moderation.Timeout;
import org.jannick.dribble.commands.moderation.Untimeout;
import org.jannick.dribble.commands.utilities.Avatar;
import org.jannick.dribble.commands.utilities.Banner;
import org.jannick.dribble.commands.moderation.Purge;
import org.jannick.dribble.events.VerifyReact;

import javax.security.auth.login.LoginException;

public class Main implements EventListener {

    public static void main(final String[] args) throws InterruptedException {
        try {

            final JDA jda = JDABuilder.createLight(Config.getToken())
                    .setStatus(OnlineStatus.IDLE)
                    .setActivity(Activity.streaming("Skywars ⚔️", "https://www.twitch.tv/jannick_05"))
                    .setMemberCachePolicy(MemberCachePolicy.ONLINE)
                    .setChunkingFilter(ChunkingFilter.ALL)
                    .enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES)
                    .enableCache(CacheFlag.ACTIVITY)
                    .build()
                    .awaitReady();

            jda.addEventListener(
                    new Avatar(),
                    new Banner(),
                    new Purge(),
                    new Ban(),
                    new Timeout(),
                    new Untimeout(),
                    new VerifyReact()
            );

            Guild guild = jda.getGuildById(958069975443177542L);
            jda.updateCommands().queue();
            guild.updateCommands()
                    .addCommands(Commands.slash("avatar", "Få en persons avatar")
                            .addOption(OptionType.USER, "person", "Hvis avatar vil du have")
                    )
                    .addCommands(Commands.slash("banner", "Få en persons banner")
                            .addOption(OptionType.USER, "person", "Hvis banner vil du have")
                    )
                    .addCommands(Commands.slash("purge", "Fjern et antal beskeder")
                            .addOptions(
                                    new OptionData(OptionType.INTEGER,
                                            "antal", "Antal beskeder der skal slettes", true)
                                            .setMaxValue(100)
                                            .setMinValue(2)
                            )
                    )
                    .addCommands(Commands.slash("ban", "Ban en person")
                            .addOption(OptionType.USER, "person", "Hvem skal bannes", true)
                            .addOption(OptionType.STRING, "grund", "Hvorfor skal personen bannes")
                    )
                    .addCommands(Commands.slash("kick", "Kick en person")
                            .addOption(OptionType.USER, "person", "Hvem skal kickes", true)
                            .addOption(OptionType.STRING, "grund", "Hvorfor skal personen kickes")
                    )
                    .addCommands(Commands.slash("mute", "Mute en person")
                            .addOption(OptionType.USER, "person", "Hvem skal mutes", true)
                            .addOption(OptionType.INTEGER, "tid", "Hvor lang tid skal personen mutes i", true)
                            .addOption(OptionType.STRING, "grund", "Hvorfor skal personen mutes")
                    )
                    .addCommands(Commands.slash("unmute", "Unmute en person")
                            .addOption(OptionType.USER, "person", "Hvem skal unmutes", true)
                    )
                    .queue();

        } catch (LoginException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    @Override
    public void onEvent(GenericEvent event) {
        if (event instanceof ReadyEvent)
            System.out.println("\n\n\n\nBot is now online!");
    }

}
