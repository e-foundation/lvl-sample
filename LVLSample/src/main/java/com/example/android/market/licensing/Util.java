package com.example.android.market.licensing;

public class Util {
    private static final int LICENSED = 0x0;
    private static final int NOT_LICENSED = 0x1;
    private static final int LICENSED_OLD_KEY = 0x2;
    private static final int ERROR_NOT_MARKET_MANAGED = 0x3;
    private static final int ERROR_SERVER_FAILURE = 0x4;
    private static final int ERROR_OVER_QUOTA = 0x5;
    private static final int ERROR_CONTACTING_SERVER = 0x101;
    private static final int ERROR_INVALID_PACKAGE_NAME = 0x102;
    private static final int ERROR_NON_MATCHING_UID = 0x103;

    public static String responseCodeAsText(int responseCode) {
        switch (responseCode) {
            case LICENSED: return "LICENSED";
            case NOT_LICENSED: return "NOT_LICENSED";
            case LICENSED_OLD_KEY: return "LICENSED_OLD_KEY";
            case ERROR_NOT_MARKET_MANAGED: return "ERROR_NOT_MARKET_MANAGED";
            case ERROR_SERVER_FAILURE: return "ERROR_SERVER_FAILURE";
            case ERROR_OVER_QUOTA: return "ERROR_OVER_QUOTA";
            case ERROR_CONTACTING_SERVER: return "ERROR_CONTACTING_SERVER";
            case ERROR_INVALID_PACKAGE_NAME: return "ERROR_INVALID_PACKAGE_NAME";
            case ERROR_NON_MATCHING_UID: return "ERROR_NON_MATCHING_UID";
        }
        return "(unknown or invalid response code)";
    }
}
