package com.victor.alexa.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Intent;
import com.amazon.ask.model.Slot;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.victor.mqtt.MqttConnection;

import java.util.Map;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class ChangeLightBrightnessIntent implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("ChangeLightBrightnessIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        IntentRequest intentRequest = (IntentRequest) input.getRequestEnvelope().getRequest();
        Intent intent = intentRequest.getIntent();
        Map<String, Slot> slots = intent.getSlots();
        Slot brightnessSlot = slots.get("Brightness");
        String brightnessValue = brightnessSlot.getValue();
        String speechText;

        if (brightnessValue != null && !brightnessValue.isEmpty()) {
            try {
                int brightness = Integer.parseInt(brightnessValue);
                if (brightness >= 0 && brightness <= 100) {
                    MqttConnection client = MqttConnection.getInstance();
                    client.publish("home/light/brightness", String.valueOf(brightness), MqttQos.AT_LEAST_ONCE);
                    speechText = "Setting the brightness to " + brightness + " percent.";
                } else {
                    speechText = "Please provide a brightness level between 0 and 100.";
                }
            } catch (NumberFormatException e) {
                speechText = "I didn't understand the brightness level. Please say a number between 0 and 100.";
            }
        } else {
            speechText = "I didn't catch the brightness level. Please tell me a brightness level to set.";
        }

        return input.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard("Home Automation", speechText)
                .withReprompt("Please tell me a brightness level to set.")
                .build();
    }
}
