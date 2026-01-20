/*
 * Copyright (C) 2026 The Android Open Source Project
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

package com.android.cellbroadcastreceiver;

import android.app.PendingIntent;
import android.content.Context;
import android.icu.util.ULocale;
import android.view.autofill.AutofillId;

import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/**
 * Stub implementation of CellBroadcastTranslateManager for 25Q2 Wear OS backport.
 * This class provides no-op implementations to maintain API surface compatibility.
 */
public class CellBroadcastTranslateManager {

    /**
     * Callback interface for translation state updates.
     */
    public interface TranslateManagerCallback {
        default void onLanguageDetectionCompleted(Optional<ULocale> detectedLocaleOptional) {}
        default void onTranslatorCreated(boolean success) {}
        default void onTranslationCompleted(CharSequence translatedText, boolean success) {}
    }

    public CellBroadcastTranslateManager(Context context, Executor executor,
            TranslateManagerCallback callback) {
        // No-op constructor
    }

    public void resolveTargetLanguage(Locale defaultLocale, Consumer<Locale> callback) {
        // Stub: immediately pass back null or defaultLocale
        if (callback != null) {
            callback.accept(defaultLocale);
        }
    }

    public void checkOnDeviceTranslationCapability(Consumer<Boolean> callback) {
        // Stub: return false (not supported on 25Q2 Wear)
        if (callback != null) {
            callback.accept(false);
        }
    }

    public boolean isTranslationManagerAvailable() {
        return false;
    }

    public PendingIntent getSettingsIntent() {
        return null;
    }

    public boolean isTranslatorReady() {
        return false;
    }

    public void initializeTranslator(ULocale sourceLocale, ULocale targetLocale) {
        // No-op
    }

    public void startOnDeviceTranslation(String text, AutofillId autofillId) {
        // No-op
    }

    public void detectLanguage(String text) {
        // No-op
    }

    public void destroyTranslator() {
        // No-op
    }
}
