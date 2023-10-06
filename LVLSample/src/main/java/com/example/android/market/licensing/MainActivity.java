/*
 * Copyright (C) 2010 The Android Open Source Project
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

//package com.example.android.market.licensing;
package com.example.android.market.licensing;

import com.google.android.vending.licensing.LicenseChecker;
import com.google.android.vending.licensing.LicenseCheckerCallback;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.UiThread;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

/**
 * Welcome to the world of Android Market licensing. We're so glad to have you
 * onboard!
 * <p>
 * The first thing you need to do is get your hands on your public key.
 * Update the BASE64_PUBLIC_KEY constant below with your encoded public key,
 * which you can find on the
 * <a href="http://market.android.com/publish/editProfile">Edit Profile</a>
 * page of the Market publisher site.
 * <p>
 * Log in with the same account on your Cupcake (1.5) or higher phone or
 * your FroYo (2.2) emulator with the Google add-ons installed. Change the
 * test response on the Edit Profile page, press Save, and see how this
 * application responds when you check your license.
 * <p>
 * After you get this sample running, peruse the
 * <a href="http://developer.android.com/guide/publishing/licensing.html">
 * licensing documentation.</a>
 */
public class MainActivity extends Activity implements LicenseCheckerV2.OnResponse, LicenseCheckerV1.OnResponse {
    private static final String BASE64_PUBLIC_KEY = "REPLACE THIS WITH YOUR PUBLIC KEY";

    // Generate your own 20 random bytes, and put them here.
    private static final byte[] SALT = new byte[] {
        -46, 65, 30, -128, -103, -57, 74, -64, 51, 88, -95, -45, 77, -117, -36, -113, -11, 32, -64,
        89
    };

    private TextView mStatusText;
    private Button mCheckLicenseButton;

    private TextView mStatusText2;
    private Button mCheckLicenseButton2;

    private LicenseCheckerCallback mLicenseCheckerCallback;
    private LicenseCheckerV1 mChecker;
    private LicenseCheckerV2 mChecker2;
    // A handler on the UI thread.
    private Handler mHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.main);

        mStatusText = (TextView) findViewById(R.id.status_text);
        mCheckLicenseButton = (Button) findViewById(R.id.check_license_button);
        mCheckLicenseButton.setOnClickListener(view -> doCheck());

        mStatusText2 = (TextView) findViewById(R.id.status_text_2);
        mCheckLicenseButton2 = (Button) findViewById(R.id.check_license_button_2);
        mCheckLicenseButton2.setOnClickListener(view -> doCheckV2());

        mHandler = new Handler();

        // Library calls this when it's done.
        mLicenseCheckerCallback = new MyLicenseCheckerCallback();
        // Construct the LicenseChecker with a policy.
        mChecker = new LicenseCheckerV1(
            this);
        mChecker2 = new LicenseCheckerV2(
            this
        );
        doCheck();
        doCheckV2();
    }

    private void doCheck() {
        mCheckLicenseButton.setEnabled(false);
        mStatusText.setText(R.string.checking_license);
        mChecker.checkAccess(this);
    }

    private void doCheckV2() {
        mCheckLicenseButton2.setEnabled(false);
        mStatusText2.setText(R.string.checking_license);
        mChecker2.checkAccess(this);
    }

    private void displayResult(final String result) {
        mHandler.post(new Runnable() {
            public void run() {
                mStatusText.setText(result);
                setProgressBarIndeterminateVisibility(false);
                mCheckLicenseButton.setEnabled(true);
            }
        });
    }

    @Override
    @UiThread
    public void onResponseV2(int code, String jwt) {
        mStatusText2.setText(
            getString(R.string.result_v2, Util.responseCodeAsText(code), (jwt == null? "(none)" : jwt))
        );
        mCheckLicenseButton2.setEnabled(true);
    }

    @Override
    public void onResponse(int responseCode, String signedData, String signature) {
        mStatusText.setText(
            getString(R.string.result_v1, Util.responseCodeAsText(responseCode), signedData, (signature == null? "(none)" : signature))
        );
        mCheckLicenseButton.setEnabled(true);
    }


    private class MyLicenseCheckerCallback implements LicenseCheckerCallback {
        public void allow(int policyReason) {
            if (isFinishing()) {
                // Don't update UI if Activity is finishing.
                return;
            }
            // Should allow user access.
            displayResult(getString(R.string.allow));
        }

        public void dontAllow(int policyReason) {
            if (isFinishing()) {
                // Don't update UI if Activity is finishing.
                return;
            }
            displayResult(getString(R.string.dont_allow));
            // Should not allow access. In most cases, the app should assume
            // the user has access unless it encounters this. If it does,
            // the app should inform the user of their unlicensed ways
            // and then either shut down the app or limit the user to a
            // restricted set of features.
            // In this example, we show a dialog that takes the user to Market.
            // If the reason for the lack of license is that the service is
            // unavailable or there is another problem, we display a
            // retry button on the dialog and a different message.
        }

        public void applicationError(int errorCode) {
            if (isFinishing()) {
                // Don't update UI if Activity is finishing.
                return;
            }
            // This is a polite way of saying the developer made a mistake
            // while setting up or calling the license checker library.
            // Please examine the error code and fix the error.
            String result = String.format(getString(R.string.application_error), verboseResponseCode(errorCode) + " (" + errorCode + ")");
            displayResult(result);
        }

        private String verboseResponseCode(int responseCode) {
            switch (responseCode) {
                case LicenseCheckerCallback.ERROR_INVALID_PACKAGE_NAME: return "invalid package name error";
                case LicenseCheckerCallback.ERROR_NON_MATCHING_UID: return "non matching UID error";
                case LicenseCheckerCallback.ERROR_NOT_MARKET_MANAGED: return "not market managed error";
                case LicenseCheckerCallback.ERROR_CHECK_IN_PROGRESS: return "check in progress error";
                case LicenseCheckerCallback.ERROR_INVALID_PUBLIC_KEY: return "invalid public key error";
                case LicenseCheckerCallback.ERROR_MISSING_PERMISSION: return "missing permission error";
                default: return null;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mChecker.onDestroy();
        mChecker2.onDestroy();
    }

}
