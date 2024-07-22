package com.victor.alexa.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PrivacyPolicyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().write("<html><head><title>Privacy Policy</title></head><body>" +
                "<h1>Privacy Policy for Domus</h1>" +
                "<h2>Introduction</h2>" +
                "<p>Welcome to Domus. This privacy policy explains how we use the personal data we collect from you when you use our Alexa skill.</p>" +
                "<h2>What data do we collect?</h2>" +
                "<p>Domus collects the following data:</p>" +
                "<ul>" +
                "<li>Interaction data: We collect data about how you interact with our skill, such as the commands you use and the time of interaction.</li>" +
                "<li>Device data: We collect information from your device such as device ID to provide personalized responses.</li>" +
                "</ul>" +
                "<h2>How do we collect your data?</h2>" +
                "<p>You directly provide Domus with most of the data we collect. We collect data and process data when you:</p>" +
                "<ul>" +
                "<li>Enable and use Domus on your device.</li>" +
                "<li>Voluntarily provide feedback or participate in surveys.</li>" +
                "</ul>" +
                "<h2>How will we use your data?</h2>" +
                "<p>Domus collects your data so that we can:</p>" +
                "<ul>" +
                "<li>Operate and manage our Alexa skill effectively.</li>" +
                "<li>Understand how you use our skill and services.</li>" +
                "<li>Improve and personalize your experience.</li>" +
                "</ul>" +
                "<h2>How do we store your data?</h2>" +
                "<p>We securely stores your data at Location and description of the data storage solution.</p>" +
                "<p>We will keep your interaction data for time period. Once this time period has expired, we will delete your data by deletion methods.</p>" +
                "<h2>What are your data protection rights?</h2>" +
                "<p>Domus would like to make sure you are fully aware of all of your data protection rights. Every user is entitled to the following:</p>" +
                "<ul>" +
                "<li>The right to access – You have the right to request copies of your personal data from us.</li>" +
                "<li>The right to rectification – You have the right to request that we correct any information you believe is inaccurate or incomplete.</li>" +
                "<li>The right to erasure – You have the right to request that we erase your personal data, under certain conditions.</li>" +
                "<li>The right to restrict processing – You have the right to request that we restrict the processing of your personal data, under certain conditions.</li>" +
                "<li>The right to object to processing – You have the right to object to our processing of your personal data, under certain conditions.</li>" +
                "<li>The right to data portability – You have the right to request that we transfer the data that we have collected to another organization, or directly to you, under certain conditions.</li>" +
                "</ul>" +
                "<h2>Changes to our privacy policy</h2>" +
                "<p>Domus keeps its privacy policy under regular review and places any updates on this web page. This privacy policy was last updated 21.04.2024.</p>" +
                "<h2>How to contact us</h2>" +
                "<p>If you have any questions Domus's privacy policy, the data we hold on you, or you would like to exercise one of your data protection rights, please do not hesitate to contact us.</p>" +
                "<p>Email us at: victorandries22@gmail.com</p>" +
                "<h2>How to contact the appropriate authority</h2>" +
                "<p>Should you wish to report a complaint or if you feel that Domus has not addressed your concern in a satisfactory manner, you may contact the Information Commissioner’s Office.</p>" +
                "</body></html>");
    }
}
