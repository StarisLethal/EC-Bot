package fr.etrangecantina.ecbot.events;

import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

public abstract class MessageListener {

    public Mono<Void> processCommand(Message eventMessage) {
        return Mono.just(eventMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .flatMap(message -> {
                    String content = message.getContent().trim();
                    if (content.equalsIgnoreCase("!todo")) {
                        return onToDoCommand(message);
                    }
                    else if (content.equalsIgnoreCase("!sleep")) {
                        return onSleepCommand(message);
                    } else {
                        return Mono.empty();
                    }
                })
                .then();
    }

    private Mono<Void> onToDoCommand(Message message){
        return message.getChannel()
                .flatMap(channel -> channel.createMessage("Things to do : \n" +
                        " - Write the bot\n" +
                        " - create mod\n" +
                        " - gather a job\n" +
                        " - live happily"))
                .then();
    }

    private Mono<Void> onSleepCommand (Message message){
        return message.getChannel()
                .flatMap(channel -> channel.createMessage("only afterlife"))
                .then();
    }
}
