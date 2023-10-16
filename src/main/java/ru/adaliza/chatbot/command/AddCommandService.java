package ru.adaliza.chatbot.command;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
@Qualifier("addCommand")
public class AddCommandService extends AbstractBotCommandService {

    @Override
    public SendMessage createMessageForCommand(Long chatId) {
        var text = "Enter product name for adding";
        return createTextReplyMessage(chatId, text);
    }
}
