package com.victor.alexa.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.Predicates;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.victor.mqtt.MqttConnection;

import java.util.Optional;

public class ClearTemperatureThresholdIntent implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(Predicates.intentName("ClearTemperatureThresholdIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        String speechText = "The temperature threshold has been cleared.";
        MqttConnection client = MqttConnection.getInstance();
        client.publish("home/temperature/threshold/clear", "clear", MqttQos.AT_LEAST_ONCE);

        return input.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard("DomusSmartHome", speechText)
                .withReprompt("You can set a new temperature threshold at any time by telling me the temperature.")
                .build();
    }
}
