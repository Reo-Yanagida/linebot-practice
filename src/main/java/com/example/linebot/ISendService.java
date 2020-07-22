package com.example.linebot;

import com.linecorp.bot.model.message.Message;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Miura Kazuto
 */
@Service
public interface ISendService {
    public void reply(String replyToken, Message... messages);
    public void reply(String replyToken, String... texts);
    public void push(List<String> userIdList, Message... messages);
    public void push(List<String> userIdList, String... texts);
}
