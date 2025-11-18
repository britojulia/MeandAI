package br.com.fiap.meandai.config;

import org.springframework.stereotype.Component;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

@Component
public class MessageHelper {
    private final MessageSource messageSource;

    public MessageHelper(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String get(String code){
        return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
    }
}
