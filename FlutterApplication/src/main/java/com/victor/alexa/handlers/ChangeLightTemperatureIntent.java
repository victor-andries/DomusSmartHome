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

public class ChangeLightTemperatureIntent implements RequestHandler {
    private static final Map<String, Integer> temperaturePresets = Map.of(
            "warm", 2700,
            "cold", 5000
    );

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("ChangeLightTemperatureIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        IntentRequest intentRequest = (IntentRequest) input.getRequestEnvelope().getRequest();
        Intent intent = intentRequest.getIntent();
        Map<String, Slot> slots = intent.getSlots();
        Slot temperatureSettingSlot = slots.get("TemperatureSetting");
        String setting = temperatureSettingSlot.getValue();
        String speechText;

        if (setting != null && !setting.isEmpty()) {
            Integer temperature = temperaturePresets.get(setting.toLowerCase());
            if (temperature != null) {
                MqttConnection client = MqttConnection.getInstance();
                client.publish("home/light/colorTemperature", String.valueOf(temperature), MqttQos.AT_LEAST_ONCE);
                speechText = "Setting the light to " + setting + " temperature.";
            } else {
                speechText = "I couldn't understand the temperature setting. Please say 'warm' or 'cold'.";
            }
        } else {
            speechText = "Please tell me whether to set the light to warm or cold.";
        }

        return input.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard("DomusSmartHome", speechText)
                .withReprompt("You can say, set the light to warm or cold.")
                .build();
    }
}
