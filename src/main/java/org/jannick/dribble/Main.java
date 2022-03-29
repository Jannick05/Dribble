package org.jannick.dribble;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.jannick.dribble.commands.SlashCommands;
import org.jannick.dribble.commands.VerifyReact;

import javax.security.auth.login.LoginException;

public class Main implements EventListener {

    public static void main(final String[] args) throws InterruptedException {
        try {

            final JDA jda = JDABuilder.createLight(Config.getToken())
                    .setStatus(OnlineStatus.IDLE)
                    .setActivity(Activity.competing("Skywars ⚔️"))
                    .setMemberCachePolicy(MemberCachePolicy.ONLINE)
                    .setChunkingFilter(ChunkingFilter.ALL)
                    .enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES)
                    .enableCache(CacheFlag.ACTIVITY)
                    .build()
                    .awaitReady();

            jda.addEventListener(
                    new SlashCommands(),
                    new VerifyReact()
            );

            //var guild = jda.getGuildById(958069975443177542L);
            jda.updateCommands()
                    .addCommands(Commands.slash("ping", "Gives the current ping"))
                    .addCommands(Commands.slash("info", "Get info"))
                    .addCommands(Commands.slash("purge", "Fjern et antal beskeder")
                            .addOption(OptionType.INTEGER, "antal", "Antal beskeder der skal slettes")
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
