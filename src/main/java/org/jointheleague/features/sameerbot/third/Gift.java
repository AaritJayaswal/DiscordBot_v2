package org.jointheleague.features.sameerbot.third;
import org.bson.Document;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.Client;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

import com.mongodb.client.model.Updates;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Gift extends Feature {

    public final String COMMAND = "!gift";

    public Gift(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "!gift <amount> <@user> Gift someone money"
        );
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
            List<User> users = event.getMessage().getMentionedUsers();
            if (users.size() != 1) {
                event.getChannel().sendMessage("You didn't mention a user or you mentioned multiple users");
                return;
            }

            int money;
            try {
                money = Integer.parseInt(messageContent.split(" +")[1]);
            } catch (Exception e) {
                event.getChannel().sendMessage("You wrote an invalid amount of minco dollars");
                return;
            }
            int userMoney = (int) Client.findOne(event.getMessageAuthor().getIdAsString()).get("mincoDollars");
            if (money > userMoney) {
                event.getChannel().sendMessage("You don't have that many minco dollars!");
                return;
            }

            Client.findOneAndUpdate(users.get(0).getIdAsString(), new Document("$inc", new Document("mincoDollars", money)));
            Client.findOneAndUpdate(event.getMessageAuthor().getIdAsString(), new Document("$inc", new Document("mincoDollars", -money)));

            event.getChannel().sendMessage("You gave " + money + " minco dollars to <@" + users.get(0).getIdAsString() + ">");
        }
    }
}