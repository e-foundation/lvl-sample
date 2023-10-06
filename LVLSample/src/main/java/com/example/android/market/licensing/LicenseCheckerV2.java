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

package com.example.android.market.licensing;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;

import com.google.android.vending.licensing.ILicenseV2ResultListener;
import com.google.android.vending.licensing.ILicensingService;
import com.google.android.vending.licensing.Policy;
import com.google.android.vending.licensing.util.Base64;
import com.google.android.vending.licensing.util.Base64DecoderException;

/**
 * Client library for Android Market license verifications.
 * <p>
 * The LicenseChecker is configured via a {@link Policy} which contains the
 * logic to determine whether a user should have access to the application. For
 * example, the Policy can define a threshold for allowable number of server or
 * client failures before the library reports the user as not having access.
 * <p>
 * Must also provide the Base64-encoded RSA public key associated with your
 * developer account. The public key is obtainable from the publisher site.
 */
public class LicenseCheckerV2 implements ServiceConnection {
    private static final String TAG = "LicenseV2Checker";

    private ILicensingService mService;

    private final Context mContext;
    /**
     * A handler for running tasks on a background thread. We don't want license
     * processing to block the UI thread.
     */
    private final Handler mHandler;
    private final String mPackageName;
    private OnResponse then;

    /**
     * @param context a Context
     * @throws IllegalArgumentException if encodedPublicKey is invalid
     */
    public LicenseCheckerV2(Context context) {
        mContext = context;
        mPackageName = mContext.getPackageName();
        HandlerThread handlerThread = new HandlerThread("background thread 2");
        handlerThread.start();
        mHandler = new Handler(handlerThread.getLooper());
    }

    /**
     * Checks if the user should have access to the app.  Binds the service if necessary.
     * <p>
     * NOTE: This call uses a trivially obfuscated string (base64-encoded).  For best security,
     * we recommend obfuscating the string that is passed into bindService using another method
     * of your own devising.
     * <p>
     * source string: "com.android.vending.licensing.ILicensingService"
     * <p>
     */
    public synchronized void checkAccess(OnResponse then) {
        this.then = then;

        if (mService == null) {
            Log.i(TAG, "Binding to licensing service.");
            try {
                boolean bindResult = mContext
                        .bindService(
                                new Intent(
                                        new String(
                                            // com.android.vending.licensing.ILicensingService
                                                Base64.decode("Y29tLmFuZHJvaWQudmVuZGluZy5saWNlbnNpbmcuSUxpY2Vuc2luZ1NlcnZpY2U="))),
                                this, // ServiceConnection.
                                Context.BIND_AUTO_CREATE);

                if (bindResult) {
                    // Wait for runChecks() to be called by onServiceConnected
                } else {
                    Log.e(TAG, "Could not bind to service.");
                }
            } catch (SecurityException e) {
                then.onResponseV2(-1, "Missing permissions.");
            } catch (Base64DecoderException e) {
                e.printStackTrace();
            }
        } else {
            runChecks();
        }
    }

    private void runChecks() {
            try {
                Log.i(TAG, "Calling checkLicenseV2 on service for " + mPackageName);
                mService.checkLicenseV2(
                    mPackageName, new ILicenseV2ResultListener.Stub() {
                        @Override
                        public void verifyLicense(int responseCode, Bundle responsePayload) {
                            Log.d(TAG, "Response: " + responseCode);
                            Log.d(TAG, "Payload: " + responsePayload.getString("LICENSE_DATA"));
                            new Handler(Looper.getMainLooper()).post(() ->
                                then.onResponseV2(responseCode, responsePayload.getString("LICENSE_DATA"))
                            );
                        }
                    }, new Bundle());
            } catch (RemoteException e) {
                Log.w(TAG, "RemoteException in checkLicenseV2 call.", e);
                new Handler(Looper.getMainLooper()).post(() ->
                    then.onResponseV2(-1, "An exception occured during the query.")
                );
            }
    }

    public synchronized void onServiceConnected(ComponentName name, IBinder service) {
        mService = ILicensingService.Stub.asInterface(service);
        runChecks();
    }

    public synchronized void onServiceDisconnected(ComponentName name) {
        // Called when the connection with the service has been
        // unexpectedly disconnected. That is, Market crashed.
        // If there are any checks in progress, the timeouts will handle them.
        Log.w(TAG, "Service unexpectedly disconnected.");
        mService = null;
    }

    /** Unbinds service if necessary and removes reference to it. */
    private void cleanupService() {
        if (mService != null) {
            try {
                mContext.unbindService(this);
            } catch (IllegalArgumentException e) {
                // Somehow we've already been unbound. This is a non-fatal
                // error.
                Log.e(TAG, "Unable to unbind from licensing service (already unbound)");
            }
            mService = null;
        }
    }

    /**
     * Inform the library that the context is about to be destroyed, so that any
     * open connections can be cleaned up.
     * <p>
     * Failure to call this method can result in a crash under certain
     * circumstances, such as during screen rotation if an Activity requests the
     * license check or when the user exits the application.
     */
    public synchronized void onDestroy() {
        cleanupService();
        mHandler.getLooper().quit();
    }

    interface OnResponse {
        public void onResponseV2(int code, String jwt);
    }
}