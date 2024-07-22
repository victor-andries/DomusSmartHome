import time
import RPi.GPIO as GPIO
import pigpio
import board
import adafruit_dht
import cbor2 as cbor
import paho.mqtt.client as paho
from paho import mqtt
import configparser
from yeelight import Bulb
import json
import psutil
import os

config = configparser.ConfigParser()
config.read('/home/victor/Licenta/config.ini')

bulb = Bulb(config['MQTT']['bulb_ip'])
mqtt_broker = config['MQTT']['broker']
mqtt_port = int(config['MQTT']['port'])
mqtt_username = config['MQTT']['username']
mqtt_password = config['MQTT']['password']

client = paho.Client()
client.tls_set(tls_version=mqtt.client.ssl.PROTOCOL_TLS)
pi = pigpio.pi()

trig = 17
echo = 27
buzzer = 18

pi.set_mode(buzzer, pigpio.OUTPUT)

GPIO.setmode(GPIO.BCM)
GPIO.setup(trig, GPIO.OUT)
GPIO.setup(echo, GPIO.IN)

motorspeed_pin = 14
DIRA = 15
DIRB = 25

GPIO.setup(motorspeed_pin, GPIO.OUT)
GPIO.setup(DIRA, GPIO.OUT)
GPIO.setup(DIRB, GPIO.OUT)

pwmPIN = GPIO.PWM(motorspeed_pin, 100)
pwmPIN.start(0)
dht_device = adafruit_dht.DHT11(board.D21)

home_mode = "home"
buzzer_timeout = 10
buzzer_start_time = 0
alarm_active = False
motor_mode = "off"
temperature_threshold = None


def on_connect(client, userdata, flags, rc):
    if rc == 0:
        print("Connected with result code " + str(rc))
        client.subscribe("home/#", qos=1)
    else:
        print("Connection failed with result code " + str(rc))


def on_message(client, userdata, msg):
    global home_mode, motor_mode, temperature_threshold
    log_message = f"Received message on topic: {msg.topic}, payload: {msg.payload.decode()}"
    client.publish("home/pi_logs", log_message)
    print(log_message)

    if msg.topic == "home/alarm/mode":
        home_mode = msg.payload.decode()
        print("Home mode set to:", home_mode)
    elif msg.topic == "home/motor/mode":
        motor_mode = msg.payload.decode()
        print("Motor mode set to:", motor_mode)
        if motor_mode == "on":
            run_motor()
        elif motor_mode == "off":
            stop_motor()
    elif msg.topic == "home/temperature/threshold":
        temperature_threshold = float(msg.payload.decode())
        print("Temperature threshold set to:", temperature_threshold)
    elif msg.topic == "home/temperature/threshold/clear":
        temperature_threshold = None
        stop_motor()
        print("Threshold cleared.")
    elif msg.topic == "home/light/power":
        if msg.payload.decode() == "on":
            bulb.turn_on()
            print("Bulb Turn on")
        elif msg.payload.decode() == "off":
            bulb.turn_off()
            print("Bulb Turn off")
    elif msg.topic == "home/light/brightness":
        try:
            brightness = int(msg.payload.decode())
            bulb.set_brightness(brightness)
        except ValueError:
            print("Invalid brightness value")
    elif msg.topic == "home/light/color":
        rgb_code = msg.payload.decode()
        r, g, b = map(int, rgb_code.split(','))
        bulb.set_rgb(r, g, b)
        print(f"Received message '{msg.payload.decode()}' on topic '{msg.topic}'")
    elif msg.topic == "home/light/colorTemperature":
        colorTemperature = int(msg.payload.decode())
        bulb.set_color_temp(colorTemperature)


client.on_connect = on_connect
client.on_message = on_message
client.username_pw_set(mqtt_username, mqtt_password)


def publish_data(data):
    cbor_data = cbor.dumps(data)
    client.publish("home/sensors", payload=cbor_data)


try:
    print("Connecting to MQTT broker...")
    client.connect(mqtt_broker, mqtt_port, 60)
    print("Connection successful.")
    client.loop_start()
except Exception as e:
    print("Error connecting to MQTT broker:", e)


def measure_distance():
    GPIO.output(trig, GPIO.HIGH)
    time.sleep(0.00001)
    GPIO.output(trig, GPIO.LOW)
    start_time = time.time()
    stop_time = time.time()
    while GPIO.input(echo) == 0:
        start_time = time.time()
    while GPIO.input(echo) == 1:
        stop_time = time.time()
    elapsed_time = stop_time - start_time
    distance = (elapsed_time * 34300) / 2
    distance = round(distance, 2)
    print("Distance measured:", distance, "cm")
    return distance


def read_sensor_data():
    try:
        temperature = dht_device.temperature
        humidity = dht_device.humidity
        print("Temperature:", temperature)
        print("Humidity:", humidity)
        return {"temperature": temperature, "humidity": humidity}
    except RuntimeError as error:
        print("Error reading DHT11 sensor:", error.args[0])
        return None


def run_motor():
    pwmPIN.ChangeDutyCycle(100)
    GPIO.output(DIRA, GPIO.HIGH)
    GPIO.output(DIRB, GPIO.LOW)


def stop_motor():
    pwmPIN.ChangeDutyCycle(0)
    GPIO.output(DIRA, GPIO.LOW)
    GPIO.output(DIRB, GPIO.LOW)


def get_pi_stats():
    with open("/sys/class/thermal/thermal_zone0/temp", "r") as temp_file:
        temp = int(temp_file.read()) / 1000.0
    stats = {
        'cpu': psutil.cpu_percent(interval=1),
        'temperature': int(temp),
        'memory': psutil.virtual_memory().percent,
        'uptime': os.popen('uptime -p').read().strip()
    }
    return stats


while True:
    distance = measure_distance()
    stats = get_pi_stats()
    client.publish("home/pi_data", json.dumps(stats))
    time.sleep(5)
    if home_mode == "away":
        if distance <= 15 and not alarm_active:
            log_message = "Intruder detected! Activating buzzer..."
            print(log_message)
            client.publish("home/pi_logs", log_message)
            pi.hardware_PWM(buzzer, 1000, 500000)
            buzzer_start_time = time.time()
            alarm_active = True
            client.publish("home/alarm/detected", payload="true")
        elif time.time() - buzzer_start_time > buzzer_timeout:
            log_message = "Buzzer timeout reached. Deactivating alarm..."
            print(log_message)
            client.publish("home/pi_logs", log_message)
            pi.hardware_PWM(buzzer, 0, 0)
            alarm_active = False
    else:
        pi.hardware_PWM(buzzer, 0, 0)
    sensor_data = read_sensor_data()
    if sensor_data is not None:
        temperature = sensor_data["temperature"]
        if temperature and temperature_threshold is not None:
            if temperature > temperature_threshold:
                run_motor()
            elif temperature <= temperature_threshold:
                stop_motor()
        data = {
            "temperature": {"value": sensor_data["temperature"], "type": "float"},
            "humidity": {"value": sensor_data["humidity"], "type": "float"},
            "distance": {"value": distance, "type": "float"}
        }
        log_message = "Publishing data to MQTT broker"
        print(log_message)
        client.publish("home/pi_logs", log_message)
        publish_data(data)
    else:
        log_message = "Failed to read sensor data"
        print(log_message)
        client.publish("home/pi_logs", log_message)
    time.sleep(3)

print("Script finished")
pi.write(buzzer, 0)
pi.stop()
GPIO.cleanup()