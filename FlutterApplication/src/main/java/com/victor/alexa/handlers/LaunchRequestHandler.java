package com.victor.alexa.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;
import com.victor.mqtt.MqttConnection;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.requestType;

public class LaunchRequestHandler implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(requestType(LaunchRequest.class));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        String speechText = "Welcome, to the domus smart home.";
        MqttConnection client = MqttConnection.getInstance();
        return input.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard("DomusSmartHome", speechText)
                .withReprompt(speechText)
                .build();
    }

}