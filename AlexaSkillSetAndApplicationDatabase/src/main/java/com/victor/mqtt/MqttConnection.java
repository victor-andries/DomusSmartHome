package com.victor.mqtt;

import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient;
import com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3Publish;

import java.awt.*;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class MqttConnection {

    private static final String BROKER_URL = "d8cdc7d26efa4674b00dd18da312be67.s1.eu.hivemq.cloud";
    private static final int PORT = 8883;
    private static final String USERNAME = "Alexaserver";
    private static final String PASSWORD = "Alexa2024";

    private static MqttConnection instance;
    private Mqtt3AsyncClient client;

    private MqttConnection() {
        connect();
    }

    public static synchronized MqttConnection getInstance() {
        if (instance == null) {
            instance = new MqttConnection();
        }
        return instance;
    }

    private void connect() {
        this.client = MqttClient.builder()
                .useMqttVersion3()
                .serverHost(BROKER_URL)
                .serverPort(PORT)
                .sslWithDefaultConfig()
                .automaticReconnect()
                .applyAutomaticReconnect()
                .buildAsync();
        this.client.connectWith()
                .simpleAuth()
                .username(USERNAME)
                .password(PASSWORD.getBytes(StandardCharsets.UTF_8))
                .applySimpleAuth()
                .send()
                .whenComplete((mqtt3ConnAck, throwable) ->  {
                    if (throwable != null) {
                        System.out.println("Connection failed: " + throwable.getMessage());
                    } else {
                        System.out.println("Connected to MQTT broker");
                    }
                });
    }

    public void publish(String topic, String content, MqttQos qos) {
        if (!client.getState().isConnected()) {
            System.out.println("Client is not connected, trying to reconnect...");
            connect();
        }

        Mqtt3Publish publish = Mqtt3Publish.builder()
                .topic(topic)
                .payload(content.getBytes(StandardCharsets.UTF_8))
                .qos(qos)
                .build();

        CompletableFuture<Mqtt3Publish> future = client.publish(publish);
        future.whenComplete((unused, throwable) -> {
            if (throwable != null) {
                System.out.println("Publish failed: " + throwable.getMessage());
            } else {
                System.out.println("Publish successful");
            }
        });
    }

    public void disconnect() {
        this.client.disconnect().whenComplete((unused, throwable) -> {
            if (throwable != null) {
                System.out.println("Error during disconnection: " + throwable.getMessage());
            } else {
                System.out.println("Disconnected from MQTT broker");
            }
        });
    }
}
