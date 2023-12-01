package ru.adaliza.chatbot.command;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import ru.adaliza.chatbot.button.InlineKeyboardInitializer;
import ru.adaliza.chatbot.language.LanguageData;

import java.io.Serializable;

@Service("helpCommand")
@RequiredArgsConstructor
public class HelpCommandService extends AbstractCommandService {
    private final InlineKeyboardInitializer keyboardInitializer;
    private LanguageData languageData;

    @Override
    public BotApiMethod<Serializable> createMessageForCommand(UpdateContext updateContext) {
        languageData = updateContext.languageData();
        return createKeyboardReplyMessage(updateContext, languageData.help());
    }

    @Override
    protected InlineKeyboardMarkup getReplyMarkup() {
        return keyboardInitializer.inlineInnerMenuMarkup(languageData);
    }
}