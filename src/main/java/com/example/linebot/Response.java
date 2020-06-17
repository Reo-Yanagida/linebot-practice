package com.example.linebot;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.action.PostbackAction;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.FollowEvent;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.template.ButtonsTemplate;
import com.linecorp.bot.model.response.BotApiResponse;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URI;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * @author Miura Kazuto
 */
@LineMessageHandler
public class Response {

    private final LineMessagingClient lineMessagingClient;

    @Autowired
    public Response(LineMessagingClient lineMessagingClient) {
        this.lineMessagingClient = lineMessagingClient;
    }

    /**
     * アカウントが登録されたときに実行される。
     */
    @EventMapping
    public void handleFollowEvent(FollowEvent event) {
        this.reply(
                event.getReplyToken(),
                "登録してくれてありがとう!"
        );
    }

    @EventMapping
    public void handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        this.reply(
                event.getReplyToken(),
                new TemplateMessage("test",
                        new ButtonsTemplate(
                                URI.create("https://4.bp.blogspot.com/-grFsR7IJJTs/Us_NJ_QCtZI/AAAAAAAAdFc/Lyf0Qem4p0c/s800/car_bus.png"),
                                "BUS",
                                "目的地を選択して下さい",
                                Arrays.asList(
                                        new PostbackAction("千歳駅", "CK"),
                                        new PostbackAction("千歳駅", "CK"),
                                        new PostbackAction("千歳駅", "CK"),
                                        new PostbackAction("千歳駅", "CK")
                                )
                        )
                )
        );
    }

    /**
     * 登録されていないEventはここで処理される。
     */
    @EventMapping
    public void handleDefaultMessageEvent(Event event) {
        System.out.println("event: " + event);
    }

    private void reply(String replyToken, String... texts) {
        Message[] messages = Arrays
                .stream(texts)
                .map(TextMessage::new)
                .collect(Collectors.toList())
                .toArray(new Message[texts.length]);
        reply(replyToken, messages);
    }

    private void reply(String replyToken, Message... messages) {
        try {
            BotApiResponse botApiResponse = lineMessagingClient
                    .replyMessage(
                            new ReplyMessage(
                                    replyToken,
                                    Arrays.asList(messages)
                            )
                    )
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
