# DomusSmartHome
Domus Smart Home is an integrated home automation project using a Raspberry Pi. The project manages multiple sensors and devices including an HC-SR04 ultrasonic sensor, DHT11 temperature and humidity sensor, a DC motor with L293N H-bridge, a buzzer, and a Yeelight smart bulb. Communication between the Raspberry Pi and the Flutter application is achieved via MQTT using the HiveMQ cloud service. Additionally, the project includes an Alexa Skill Set for voice control through an Alexa Echo device.

# Overview
The Domus Smart Home project provides a robust solution for home automation. The system uses a Raspberry Pi to interface with various sensors and devices, enabling control via a Flutter mobile application and voice commands through Alexa.

# Features
HC-SR04 Ultrasonic Sensor: Measures distance.
DHT11 Sensor: Monitors temperature and humidity.
DC Motor with L293N H-Bridge: Acts like a fan and it's utilised with DHT11
Buzzer: Alert the owner when someone is within a certain range.
Yeelight Smart Bulb: Controls lighting and blink red when an intruder is detected.
MQTT Communication: Facilitates data exchange between Raspberry Pi and Flutter application and Alexa Cloud.
Alexa Integration: Voice control for all the sensors and the smart bulb through Alexa Skill Set.

# Hardware Requirements
Raspberry Pi (any model with GPIO pins)
HC-SR04 Ultrasonic Sensor
DHT11 Temperature and Humidity Sensor
DC Motor
L293N H-Bridge Motor Driver
Buzzer
Yeelight Smart Bulb
Alexa Echo Device
Breadboard and Jumper Wires

# Software Requirements
Python 3
Paho-MQTT (for MQTT communication)
Yeelight Python Library
HiveMQ Cloud Account
Alexa Developer Account
Flutter (for mobile and web application development)

# Solution Arhitecture

![image](https://github.com/user-attachments/assets/13e7037d-312f-45db-aa27-f7895387043c)



