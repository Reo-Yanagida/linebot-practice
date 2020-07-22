package com.example.linebot;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * @author Miura Kazuto
 */
public class SendService implements ISendService {

    private final LineMessagingClient lineMessagingClient;

    @Autowired
    public SendService(LineMessagingClient lineMessagingClient) {
        this.lineMessagingClient = lineMessagingClient;
    }

    public void reply(String replyToken, Message... messages) {
        try {
            BotApiResponse botApiResponse = lineMessagingClient
                    .replyMessage(new ReplyMessage(replyToken, Arrays.asList(messages)))
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void reply(String replyToken, String... texts) {
        Message[] messages = Arrays
                .stream(texts)
                .map(TextMessage::new)
                .collect(Collectors.toList())
                .toArray(new Message[texts.length]);
        this.reply(replyToken, messages);
    }

    public void push(List<String> userIdList, Message... messages) {
        try {
            for (String userId : userIdList) {
                BotApiResponse botApiResponse = lineMessagingClient
                        .pushMessage(new PushMessage(userId, Arrays.asList(messages)))
                        .get();
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void push(List<String> userIdList, String... texts) {
        Message[] messages = Arrays
                .stream(texts)
                .map(TextMessage::new)
                .collect(Collectors.toList())
                .toArray(new Message[texts.length]);
        this.push(userIdList, messages);
    }
}
