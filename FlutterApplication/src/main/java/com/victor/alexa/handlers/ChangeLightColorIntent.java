package com.victor.alexa.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Intent;
import com.amazon.ask.model.Slot;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.victor.mqtt.MqttConnection;

import java.awt.*;
import java.util.Map;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class ChangeLightColorIntent implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("ChangeLightColorIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        IntentRequest intentRequest = (IntentRequest) input.getRequestEnvelope().getRequest();
        Intent intent = intentRequest.getIntent();
        Map<String, Slot> slots = intent.getSlots();
        Slot colorSlot = slots.get("Color");
        String colorName = colorSlot.getValue();
        String speechText;

        if (colorName != null && !colorName.isEmpty()) {
            String rgbCode = convertColorNameToRGB(colorName);

            if (rgbCode != null) {
                speechText = "Changing the light color to " + colorName;
                MqttConnection client = MqttConnection.getInstance();
                client.publish("home/light/color", rgbCode, MqttQos.AT_LEAST_ONCE);
            } else {
                speechText = "I couldn't find the RGB code for " + colorName + ". Please try another color.";
            }
        } else {
            speechText = "I'm not sure what color you meant. Please try again.";
        }

        return input.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard("Home Automation", speechText)
                .withReprompt("Please tell me a color to change the light to.")
                .build();
    }

    private String convertColorNameToRGB(String colorName) {
        Map<String, String> colorMap = Map.ofEntries(
                Map.entry("red", "255,0,0"),
                Map.entry("blue", "0,0,255"),
                Map.entry("green", "0,255,0"),
                Map.entry("yellow", "255,255,0"),
                Map.entry("purple", "128,0,128"),
                Map.entry("cyan", "0,255,255"),
                Map.entry("magenta", "255,0,255"),
                Map.entry("orange", "255,165,0"),
                Map.entry("pink", "255,192,203"),
                Map.entry("white", "255,255,255"),
                Map.entry("brown", "165,42,42"),
                Map.entry("lime", "0,255,0"),
                Map.entry("navy", "0,0,128"),
                Map.entry("olive", "128,128,0"),
                Map.entry("teal", "0,128,128"),
                Map.entry("silver", "192,192,192"),
                Map.entry("gold", "255,215,0")
        );
        return colorMap.getOrDefault(colorName.toLowerCase(), null);
    }
}
