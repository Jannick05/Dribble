//package org.jannick.dribble.task;

import net.dv8tion.jda.api.entities.Activity;
//import org.jannick.dribble.DiscordBot;

import java.util.concurrent.TimeUnit;

//public class BotStatusTask implements Runnable {

//    private final DiscordBot bot;
//    private int currentIndex = 0;
//
//    public BotStatusTask(DiscordBot bot) {
//        this.bot = bot;
//        this.bot.getExecutorService().scheduleAtFixedRate(this, 1L,15L, TimeUnit.SECONDS);
//    }

//    @Override
//    public void run() {
//        String[] status = {"Bedwars \uD83D\uDECF️", "Skywars ⚔️"};
//        DiscordBot.getJda().getPresence().setActivity(Activity.competing(status[currentIndex]));
//        currentIndex = (currentIndex + 1) % status.length;
//    }
//}
