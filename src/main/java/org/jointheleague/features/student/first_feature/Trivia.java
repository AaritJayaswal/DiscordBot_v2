package org.jointheleague.features.student.first_feature;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

import java.util.ArrayList;
import java.util.Random;

public class Trivia extends Feature {

    public final String COMMAND = "!trivia";
    public boolean questionRight = false;
    int questionNumber;
    String answer;
    Random ran = new Random();
    public Trivia(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "Trivia questions for the user to answer. Start the game with !trivia and enter a guess using !trivia guess. E.g. !trivia b"
        );
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();

        System.out.println(messageContent);

        if (messageContent.equals(COMMAND)) {
            questionRight = false;
            questionNumber = ran.nextInt(6);

            switch(questionNumber) {
                case 0:
                    event.getChannel().sendMessage("Trivia: What is the main ingredient in guacamole?\na) Tomatoes\nb) Avocados\nc) Lemons\nd) Tortilla chips");
                    answer = "b";
                    break;
                case 1:
                    event.getChannel().sendMessage("Trivia: How many Harry Potter books are there?\na) 8\nb) 6\nc) 5\nd) 7");
                    answer = "d";
                    break;
                case 2:
                    event.getChannel().sendMessage("Trivia: What does www stand for in a website addresss bar?\na) World Wide Web\nb) Web World Wide\n Wild Wild West\n Wet Windows Web");
                    answer = "a";
                    break;
                case 3:
                    event.getChannel().sendMessage("Trivia: Which country drinks the most amount of coffee per person?\na) USA\nb) Italy\nc) Finland\nd) Columbia");
                    answer = "c";
                    break;
                case 4:
                    event.getChannel().sendMessage("Trivia: How many stars are there on the US Flag?\na) USA\nb) 50\nc) 75\nd) 45");
                    answer = "b";
                    break;
                case 5:
                    event.getChannel().sendMessage("Trivia: In what year was the iPhone first released?\na) 2007\nb) 2008\nc) 1998\nd) 2002");
                    answer = "a";
                    break;
            }

        }
        //check a guess
        else if (messageContent.contains(COMMAND)
                && !messageContent.contains("Trivia:"))
        {

            String guessMessage = messageContent.replaceAll(" ", "").replace(COMMAND, "");
                if (guessMessage.equalsIgnoreCase(answer)) {

                    event.getChannel().sendMessage("Trivia: "+answer + " is correct!");
                    questionRight = true;
                } else {
                    questionRight = false;
                    event.getChannel().sendMessage("Trivia: " + "Wrong, try again!");

                }
        }
    }

}