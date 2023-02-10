package com.eureka.client.service;

import com.eureka.client.dto.ToTranslate;
import com.eureka.client.dto.Translated;
import com.eureka.client.exception.RequestBuildException;
import com.eureka.client.model.Translation;
import com.eureka.client.publisher.MessagePublisher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TranslateService {
    private HttpClient httpClient;
    private ObjectMapper mapper;
    private TranslateCrudService crudService;
    private MessagePublisher messagePublisher;

    public Translated translate(ToTranslate toTranslate) {
        HttpResponse<String> response;
        String message;
        Translated translated = new Translated();
        Optional<Translation> translation = crudService.get(toTranslate.getMessage());
        if (translation.isPresent()) {
            translated.setMessage(translation.get().getMessage());
            return translated;
        }
        try {
            response = httpClient.send(request(toTranslate), HttpResponse.BodyHandlers.ofString());
            List list = mapper.readValue(response.body(), List.class);
            List innerList = (List) list.get(0);
            List messageList = (List) innerList.get(0);
            message = (String) messageList.get(0);
            messagePublisher.sendMessage(createJsonTranslation(toTranslate,message));
            translated.setMessage(message);
        } catch (IOException | InterruptedException e) {
            throw new RequestBuildException(e.getMessage(), e);
        }
        return translated;
    }

    private HttpRequest request(ToTranslate toTranslate) {
        try {
            return HttpRequest.newBuilder()
                    .uri(new URI("https://translate.googleapis.com/translate_a/single?client=gtx&sl="
                            + toTranslate.getFrom() + "&tl=" + toTranslate.getTo() + "&dt=t&q=" + UriUtils.encode(toTranslate.getMessage(), "UTF-8")))
                    .headers("Content-Type", "application/json;charset=UTF-8")
                    .GET()
                    .build();
        } catch (URISyntaxException e) {
            throw new RequestBuildException(e.getMessage(), e);
        }
    }

    private String createJsonTranslation(ToTranslate toTranslate, String translatedMessage) throws JsonProcessingException {
        Translation t = initTranslation(toTranslate, translatedMessage);
        return mapper.writeValueAsString(t);
    }

    private Translation initTranslation(ToTranslate toTranslate, String translatedMessage) {
        Translation t = new Translation();
        t.setCreated(LocalDateTime.now());
        t.setIsActive(true);
        t.setNew(true);
        t.setMessage(toTranslate.getMessage());
        t.setFrom(toTranslate.getFrom());
        t.setTo(toTranslate.getTo());
        t.setTranslated(translatedMessage);
        return t;
    }

}
