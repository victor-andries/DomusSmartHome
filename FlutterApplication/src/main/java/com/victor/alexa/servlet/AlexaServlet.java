package com.victor.alexa.servlet;

import com.amazon.ask.Skill;
import com.amazon.ask.Skills;
import com.amazon.ask.servlet.SkillServlet;
import com.victor.alexa.handlers.*;
import com.victor.alexa.interceptors.request.LogRequestInterceptor;
import com.victor.alexa.interceptors.response.LogResponseInterceptor;

public class AlexaServlet extends SkillServlet {

    public AlexaServlet() {
        super(getSkill());
    }

    private static Skill getSkill() {
        return Skills.standard()
                .addRequestHandlers(
                        new CancelandStopIntentHandler(),
                        new TurnOnFanHandler(),
                        new TurnOffFanHandler(),
                        new TurnOnLightHandler(),
                        new TurnOffLightHandler(),
                        new TurnOnAwayModeHandler(),
                        new TurnOffAwayModeHandler(),
                        new ChangeLightColorIntent(),
                        new ChangeLightBrightnessIntent(),
                        new ClearTemperatureThresholdIntent(),
                        new ChangeLightTemperatureIntent(),
                        new SetTemperatureThresholdIntent(),
                        new HelpIntentHandler(),
                        new LaunchRequestHandler(),
                        new SessionEndedRequestHandler(),
                        new FallbackIntentHandler())
                .addExceptionHandler(new MyExceptionHandler())
                .addRequestInterceptors(
                        new LogRequestInterceptor())
                .addResponseInterceptors(new LogResponseInterceptor())
                .withSkillId("amzn1.ask.skill.e89ecd5a-746c-493c-b3a9-d9f23028bdf2")
                .build();
    }
}