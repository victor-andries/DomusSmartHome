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

public class SetTemperatureThresholdIntent implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("SetTemperatureThresholdIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        IntentRequest intentRequest = (IntentRequest) input.getRequestEnvelope().getRequest();
        Intent intent = intentRequest.getIntent();
        Map<String, Slot> slots = intent.getSlots();
        Slot thresholdSlot = slots.get("TemperatureThreshold");
        String thresholdValue = thresholdSlot.getValue();
        String speechText;

        if (thresholdValue != null && !thresholdValue.isEmpty()) {
            try {
                int threshold = Integer.parseInt(thresholdValue);
                if(threshold >= 18 && threshold <= 30)
                {
                    MqttConnection client = MqttConnection.getInstance();
                    client.publish("home/temperature/threshold", String.valueOf(threshold), MqttQos.AT_LEAST_ONCE);
                    speechText = "Setting the temperature threshold to " + threshold + " degrees.";
                } else {
                    speechText = "The temperature threshold is not a proper value";
                }
            } catch (NumberFormatException e) {
                speechText = "I didn't understand the temperature value. Please say a number.";
            }
        } else {
            speechText = "Please tell me the temperature threshold to set.";
        }

        return input.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard("DomusSmartHome", speechText)
                .withReprompt("You can set the temperature threshold by saying, set threshold to a number of degrees.")
                .build();
    }
}
