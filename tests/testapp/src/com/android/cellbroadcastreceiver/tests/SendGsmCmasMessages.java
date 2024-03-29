/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.cellbroadcastreceiver.tests;

import android.Manifest;
import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import android.provider.Telephony;
import android.telephony.SmsCbCmasInfo;
import android.telephony.SmsCbLocation;
import android.telephony.SmsCbMessage;
import android.text.TextUtils;

import com.android.internal.telephony.CellBroadcastUtils;
import com.android.internal.telephony.gsm.SmsCbConstants;

/**
 * Send some test GSM CMAS warning notifications.
 */
public class SendGsmCmasMessages {

    private static final String PRES_ALERT =
            "THE PRESIDENT HAS ISSUED AN EMERGENCY ALERT. CHECK LOCAL MEDIA FOR MORE DETAILS";

    private static final String EXTREME_ALERT = "FLASH FLOOD WARNING FOR SOUTH COCONINO COUNTY"
            + " - NORTH CENTRAL ARIZONA UNTIL 415 PM MST";

    private static final String SEVERE_ALERT = "SEVERE WEATHER WARNING FOR SOMERSET COUNTY"
            + " - NEW JERSEY UNTIL 415 PM MST";

    private static final String AMBER_ALERT =
            "AMBER ALERT:Mountain View,CA VEH'07 Blue Honda Civic CA LIC 5ABC123. "
                    + "Check www.amberalert.gov/active.htm, call 858-123-4567, or email "
                    + "amberalert@mountainview.ca.gov for more information.";

    private static final String MONTHLY_TEST_ALERT = "This is a test of the emergency alert system."
            + " This is only a test. Call (123)456-7890.";

    private static final String PUBLIC_SAFETY_MESSAGE = "This is a public safety message.";

    private static final String STATE_LOCAL_ALERT = "This is a state/local test message.";

    private static void sendBroadcast(Context context, SmsCbMessage cbMessage) {
        Intent intent = new Intent(Telephony.Sms.Intents.ACTION_SMS_EMERGENCY_CB_RECEIVED);
        intent.putExtra("message", cbMessage);
        intent.setPackage(CellBroadcastUtils.getDefaultCellBroadcastReceiverPackageName(context));
        context.sendOrderedBroadcastAsUser(intent, UserHandle.ALL,
                Manifest.permission.RECEIVE_EMERGENCY_BROADCAST,
                AppOpsManager.OP_RECEIVE_EMERGECY_SMS, null, null, Activity.RESULT_OK, null, null);
    }

    public static void testSendCmasAlertWithServiceCategory(
            Context context, int serviceCategory, int serialNumber, String messageBody,
            String languageCode, boolean isAdditionalLang) {
        SmsCbMessage cbMessage =
                createCmasSmsMessage(
                        serviceCategory,
                        serialNumber,
                        TextUtils.isEmpty(languageCode) ? "en" : languageCode,
                        TextUtils.isEmpty(messageBody) ? "This is an alert" : messageBody,
                        SmsCbCmasInfo.CMAS_SEVERITY_EXTREME,
                        SmsCbCmasInfo.CMAS_URGENCY_EXPECTED,
                        SmsCbCmasInfo.CMAS_CERTAINTY_LIKELY,
                        SmsCbMessage.MESSAGE_PRIORITY_EMERGENCY);

        sendBroadcast(context, cbMessage);
    }

    public static void testSendCmasPresAlert(
            Context context, int serialNumber, String messageBody, String languageCode,
            boolean isAdditionalLang) {
        SmsCbMessage cbMessage =
                createCmasSmsMessage(
                        isAdditionalLang
                                ? SmsCbConstants.MESSAGE_ID_CMAS_ALERT_PRESIDENTIAL_LEVEL_LANGUAGE
                                : SmsCbConstants.MESSAGE_ID_CMAS_ALERT_PRESIDENTIAL_LEVEL,
                        serialNumber,
                        TextUtils.isEmpty(languageCode) ? "en" : languageCode,
                        TextUtils.isEmpty(messageBody) ? PRES_ALERT : messageBody,
                        SmsCbCmasInfo.CMAS_SEVERITY_EXTREME,
                        SmsCbCmasInfo.CMAS_URGENCY_EXPECTED,
                        SmsCbCmasInfo.CMAS_CERTAINTY_LIKELY,
                        SmsCbMessage.MESSAGE_PRIORITY_EMERGENCY);

        sendBroadcast(context, cbMessage);
    }

    public static void testSendCmasExtremeAlert(
            Context context, int serialNumber, String messageBody, String languageCode,
            boolean isAdditionalLang) {
        SmsCbMessage cbMessage =
                createCmasSmsMessage(
                        isAdditionalLang
                                ? SmsCbConstants
                                        .MESSAGE_ID_CMAS_ALERT_EXTREME_IMMEDIATE_OBSERVED_LANGUAGE
                                : SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_IMMEDIATE_OBSERVED,
                        serialNumber,
                        TextUtils.isEmpty(languageCode) ? "en" : languageCode,
                        TextUtils.isEmpty(messageBody) ? EXTREME_ALERT : messageBody,
                        SmsCbCmasInfo.CMAS_SEVERITY_EXTREME,
                        SmsCbCmasInfo.CMAS_URGENCY_EXPECTED,
                        SmsCbCmasInfo.CMAS_CERTAINTY_OBSERVED,
                        SmsCbMessage.MESSAGE_PRIORITY_EMERGENCY);

        sendBroadcast(context, cbMessage);
    }

    public static void testSendCmasSevereAlert(
            Context context, int serialNumber, String messageBody, String languageCode,
            boolean isAdditionalLang) {
        SmsCbMessage cbMessage =
                createCmasSmsMessage(
                        isAdditionalLang
                                ? SmsCbConstants
                                        .MESSAGE_ID_CMAS_ALERT_SEVERE_EXPECTED_OBSERVED_LANGUAGE
                                : SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_EXPECTED_OBSERVED,
                        serialNumber,
                        TextUtils.isEmpty(languageCode) ? "en" : languageCode,
                        TextUtils.isEmpty(messageBody) ? SEVERE_ALERT : messageBody,
                        SmsCbCmasInfo.CMAS_SEVERITY_SEVERE,
                        SmsCbCmasInfo.CMAS_URGENCY_IMMEDIATE,
                        SmsCbCmasInfo.CMAS_CERTAINTY_LIKELY,
                        SmsCbMessage.MESSAGE_PRIORITY_EMERGENCY);

        sendBroadcast(context, cbMessage);
    }

    public static void testSendCmasAmberAlert(
            Context context, int serialNumber, String messageBody, String languageCode,
            boolean isAdditionalLang) {
        SmsCbMessage cbMessage =
                createCmasSmsMessage(
                        isAdditionalLang
                                ? SmsCbConstants
                                        .MESSAGE_ID_CMAS_ALERT_CHILD_ABDUCTION_EMERGENCY_LANGUAGE
                                : SmsCbConstants.MESSAGE_ID_CMAS_ALERT_CHILD_ABDUCTION_EMERGENCY,
                        serialNumber,
                        TextUtils.isEmpty(languageCode) ? "en" : languageCode,
                        TextUtils.isEmpty(messageBody) ? AMBER_ALERT : messageBody,
                        SmsCbCmasInfo.CMAS_SEVERITY_UNKNOWN,
                        SmsCbCmasInfo.CMAS_URGENCY_UNKNOWN,
                        SmsCbCmasInfo.CMAS_CERTAINTY_UNKNOWN,
                        SmsCbMessage.MESSAGE_PRIORITY_EMERGENCY);

        sendBroadcast(context, cbMessage);
    }

    public static void testSendCmasMonthlyTest(
            Context context, int serialNumber, String messageBody, String languageCode,
            boolean isAdditionalLang) {
        SmsCbMessage cbMessage =
                createCmasSmsMessage(
                        isAdditionalLang
                                ? SmsCbConstants
                                        .MESSAGE_ID_CMAS_ALERT_REQUIRED_MONTHLY_TEST_LANGUAGE
                                : SmsCbConstants.MESSAGE_ID_CMAS_ALERT_REQUIRED_MONTHLY_TEST,
                        serialNumber,
                        TextUtils.isEmpty(languageCode) ? "en" : languageCode,
                        TextUtils.isEmpty(messageBody) ? MONTHLY_TEST_ALERT : messageBody,
                        SmsCbCmasInfo.CMAS_SEVERITY_UNKNOWN,
                        SmsCbCmasInfo.CMAS_URGENCY_UNKNOWN,
                        SmsCbCmasInfo.CMAS_CERTAINTY_UNKNOWN,
                        SmsCbMessage.MESSAGE_PRIORITY_EMERGENCY);

        sendBroadcast(context, cbMessage);
    }

    public static void testSendCmasExerciseTest(
            Context context, int serialNumber, String messageBody, String languageCode,
            boolean isAdditionalLang) {
        SmsCbMessage cbMessage =
                createCmasSmsMessage(
                        isAdditionalLang
                                ? SmsCbConstants
                                .MESSAGE_ID_CMAS_ALERT_EXERCISE_LANGUAGE
                                : SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXERCISE,
                        serialNumber,
                        TextUtils.isEmpty(languageCode) ? "en" : languageCode,
                        TextUtils.isEmpty(messageBody) ? MONTHLY_TEST_ALERT : messageBody,
                        SmsCbCmasInfo.CMAS_SEVERITY_UNKNOWN,
                        SmsCbCmasInfo.CMAS_URGENCY_UNKNOWN,
                        SmsCbCmasInfo.CMAS_CERTAINTY_UNKNOWN,
                        SmsCbMessage.MESSAGE_PRIORITY_EMERGENCY);

        sendBroadcast(context, cbMessage);
    }

    public static void testSendPublicSafetyMessagesAlert(
            Context context, int serialNumber, String messageBody, String languageCode,
            boolean isAdditionalLang) {
        SmsCbMessage cbMessage =
                createCmasSmsMessage(
                        isAdditionalLang
                                ? SmsCbConstants.MESSAGE_ID_CMAS_ALERT_PUBLIC_SAFETY_LANGUAGE
                                : SmsCbConstants.MESSAGE_ID_CMAS_ALERT_PUBLIC_SAFETY,
                        serialNumber,
                        TextUtils.isEmpty(languageCode) ? "en" : languageCode,
                        TextUtils.isEmpty(messageBody) ? PUBLIC_SAFETY_MESSAGE : messageBody,
                        SmsCbCmasInfo.CMAS_SEVERITY_UNKNOWN,
                        SmsCbCmasInfo.CMAS_URGENCY_UNKNOWN,
                        SmsCbCmasInfo.CMAS_CERTAINTY_UNKNOWN,
                        SmsCbMessage.MESSAGE_PRIORITY_NORMAL);

        sendBroadcast(context, cbMessage);
    }

    public static void testSendStateLocalTestAlert(
            Context context, int serialNumber, String messageBody, String languageCode,
            boolean isAdditionalLang) {
        SmsCbMessage cbMessage =
                createCmasSmsMessage(
                        isAdditionalLang
                                ? SmsCbConstants.MESSAGE_ID_CMAS_ALERT_STATE_LOCAL_TEST_LANGUAGE
                                : SmsCbConstants.MESSAGE_ID_CMAS_ALERT_STATE_LOCAL_TEST,
                        serialNumber,
                        TextUtils.isEmpty(languageCode) ? "en" : languageCode,
                        TextUtils.isEmpty(messageBody) ? STATE_LOCAL_ALERT : messageBody,
                        SmsCbCmasInfo.CMAS_SEVERITY_UNKNOWN,
                        SmsCbCmasInfo.CMAS_URGENCY_UNKNOWN,
                        SmsCbCmasInfo.CMAS_CERTAINTY_UNKNOWN,
                        SmsCbMessage.MESSAGE_PRIORITY_NORMAL);

        sendBroadcast(context, cbMessage);
    }

    /**
     * Create a new SmsCbMessage for testing GSM CMAS support.
     * @param serviceCategory the GSM service category
     * @param serialNumber the 16-bit message identifier
     * @param language message language code
     * @param body message body
     * @param severity CMAS severity
     * @param urgency CMAS urgency
     * @param certainty CMAS certainty
     * @return the newly created SmsMessage object
     */
    private static SmsCbMessage createCmasSmsMessage(int serviceCategory, int serialNumber,
            String language, String body, int severity, int urgency, int certainty, int priority) {
        int messageClass = getCmasMessageClass(serviceCategory);
        SmsCbCmasInfo cmasInfo =
                new SmsCbCmasInfo(
                        messageClass,
                        SmsCbCmasInfo.CMAS_CATEGORY_UNKNOWN,
                        SmsCbCmasInfo.CMAS_RESPONSE_TYPE_UNKNOWN,
                        severity,
                        urgency,
                        certainty);
        return new SmsCbMessage(SmsCbMessage.MESSAGE_FORMAT_3GPP, 0, serialNumber,
                new SmsCbLocation("123456"), serviceCategory, language, body,
                priority, null, cmasInfo, 0, 1);
    }

    /**
     * Returns the CMAS message class.
     *
     * @param messageId message identifier
     * @return the CMAS message class as defined in {@link SmsCbCmasInfo}
     */
    private static int getCmasMessageClass(int messageId) {
        switch (messageId) {
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_PRESIDENTIAL_LEVEL:
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_PRESIDENTIAL_LEVEL_LANGUAGE:
                return SmsCbCmasInfo.CMAS_CLASS_PRESIDENTIAL_LEVEL_ALERT;

            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_IMMEDIATE_OBSERVED:
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_IMMEDIATE_OBSERVED_LANGUAGE:
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_IMMEDIATE_LIKELY:
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_IMMEDIATE_LIKELY_LANGUAGE:
                return SmsCbCmasInfo.CMAS_CLASS_EXTREME_THREAT;

            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_EXPECTED_OBSERVED:
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_EXPECTED_OBSERVED_LANGUAGE:
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_EXPECTED_LIKELY:
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_EXPECTED_LIKELY_LANGUAGE:
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_IMMEDIATE_OBSERVED:
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_IMMEDIATE_OBSERVED_LANGUAGE:
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_IMMEDIATE_LIKELY:
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_IMMEDIATE_LIKELY_LANGUAGE:
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_EXPECTED_OBSERVED:
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_EXPECTED_OBSERVED_LANGUAGE:
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_EXPECTED_LIKELY:
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_EXPECTED_LIKELY_LANGUAGE:
                return SmsCbCmasInfo.CMAS_CLASS_SEVERE_THREAT;

            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_CHILD_ABDUCTION_EMERGENCY:
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_CHILD_ABDUCTION_EMERGENCY_LANGUAGE:
                return SmsCbCmasInfo.CMAS_CLASS_CHILD_ABDUCTION_EMERGENCY;

            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_REQUIRED_MONTHLY_TEST:
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_REQUIRED_MONTHLY_TEST_LANGUAGE:
                return SmsCbCmasInfo.CMAS_CLASS_REQUIRED_MONTHLY_TEST;

            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXERCISE:
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXERCISE_LANGUAGE:
                return SmsCbCmasInfo.CMAS_CLASS_CMAS_EXERCISE;

            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_OPERATOR_DEFINED_USE:
            case SmsCbConstants.MESSAGE_ID_CMAS_ALERT_OPERATOR_DEFINED_USE_LANGUAGE:
                return SmsCbCmasInfo.CMAS_CLASS_OPERATOR_DEFINED_USE;

            default:
                return SmsCbCmasInfo.CMAS_CLASS_UNKNOWN;
        }
    }
}
