package ru.adaliza.chatbot.command;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import ru.adaliza.chatbot.button.InlineKeyboardInitializer;
import ru.adaliza.chatbot.language.LanguageData;
import ru.adaliza.chatbot.property.BotProperties;
import ru.adaliza.chatbot.service.ProductService;
import ru.adaliza.chatbot.service.UserService;

import java.io.Serializable;

@Service("addCommand")
@RequiredArgsConstructor
public class AddCommandService extends AbstractCommandService {
    private final UserService userService;
    private final ProductService productService;
    private final BotProperties properties;
    private final InlineKeyboardInitializer keyboardInitializer;
    private LanguageData languageData;

    @Override
    public BotApiMethod<Serializable> createMessageForCommand(UpdateContext updateContext) {
        int productQuantity = productService.getProductQuantity(updateContext.chatId());
        languageData = updateContext.languageData();
        if (productQuantity >= properties.getMaxProductQuantity()) {
            return createKeyboardReplyMessage(updateContext, languageData.errorAdding());
        } else {
            userService.updateMainMessageId(updateContext.chatId(), updateContext.messageId());
            return createKeyboardReplyMessage(updateContext, languageData.add());
        }
    }

    @Override
    protected InlineKeyboardMarkup getReplyMarkup() {
        return keyboardInitializer.inlineInnerMenuMarkup(languageData);
    }
}