package com.example.linebot;

import com.linecorp.bot.model.action.*;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.FollowEvent;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.template.ButtonsTemplate;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URI;
import java.util.Arrays;

/**
 * @author Miura Kazuto
 */
@LineMessageHandler
public class Response {

    private final ISendService sendService;

    @Autowired
    public Response(ISendService sendService) {
        this.sendService = sendService;
    }

    /**
     * アカウントが登録されたときに実行される。
     */
    @EventMapping
    public void handleFollowEvent(FollowEvent event) {
        sendService.reply(
                event.getReplyToken(),
                "登録してくれてありがとう!"
        );
    }

    @EventMapping
    public void handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        sendService.reply(
                event.getReplyToken(),
                TemplateMessage.builder()
                        .altText("test")
                        .template(ButtonsTemplate.builder()
                                .thumbnailImageUrl(URI.create("https://4.bp.blogspot.com/-grFsR7IJJTs/Us_NJ_QCtZI/AAAAAAAAdFc/Lyf0Qem4p0c/s800/car_bus.png"))
                                .title("BUS")
                                .text("目的地を選択してください")
                                .actions(Arrays.asList(
                                        PostbackAction.builder()
                                                .label("家")
                                                .data("HOME")
                                                .build(),
                                        PostbackAction.builder()
                                                .label("家")
                                                .data("HOME")
                                                .build()
                                        )
                                )
                                .build()
                        )
                        .build()
        );
    }

    /**
     * 登録されていないEventはここで処理される。
     */
    @EventMapping
    public void handleDefaultMessageEvent(Event event) {
        System.out.println("event: " + event);
    }
}
