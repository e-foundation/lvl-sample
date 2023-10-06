/*
 * This file is auto-generated.  DO NOT MODIFY.
 */
package com.google.android.vending.licensing;
public interface ILicenseV2ResultListener extends android.os.IInterface
{
    /** Default implementation for ILicenseV2ResultListener. */
    public static class Default implements com.google.android.vending.licensing.ILicenseV2ResultListener
    {
        @Override public void verifyLicense(int responseCode, android.os.Bundle responsePayload) throws android.os.RemoteException
        {
        }
        @Override
        public android.os.IBinder asBinder() {
            return null;
        }
    }
    /** Local-side IPC implementation stub class. */
    public static abstract class Stub extends android.os.Binder implements com.google.android.vending.licensing.ILicenseV2ResultListener
    {
        /** Construct the stub at attach it to the interface. */
        public Stub()
        {
            this.attachInterface(this, DESCRIPTOR);
        }
        /**
         * Cast an IBinder object into an com.google.android.vending.licensing.ILicenseV2ResultListener interface,
         * generating a proxy if needed.
         */
        public static com.google.android.vending.licensing.ILicenseV2ResultListener asInterface(android.os.IBinder obj)
        {
            if ((obj==null)) {
                return null;
            }
            android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (((iin!=null)&&(iin instanceof com.google.android.vending.licensing.ILicenseV2ResultListener))) {
                return ((com.google.android.vending.licensing.ILicenseV2ResultListener)iin);
            }
            return new com.google.android.vending.licensing.ILicenseV2ResultListener.Stub.Proxy(obj);
        }
        @Override public android.os.IBinder asBinder()
        {
            return this;
        }
        @Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
        {
            java.lang.String descriptor = DESCRIPTOR;
            if (code >= android.os.IBinder.FIRST_CALL_TRANSACTION && code <= android.os.IBinder.LAST_CALL_TRANSACTION) {
                data.enforceInterface(descriptor);
            }
            switch (code)
            {
                case INTERFACE_TRANSACTION:
                {
                    reply.writeString(descriptor);
                    return true;
                }
            }
            switch (code)
            {
                case TRANSACTION_verifyLicense:
                {
                    int _arg0;
                    _arg0 = data.readInt();
                    android.os.Bundle _arg1;
                    _arg1 = _Parcel.readTypedObject(data, android.os.Bundle.CREATOR);
                    this.verifyLicense(_arg0, _arg1);
                    break;
                }
                default:
                {
                    return super.onTransact(code, data, reply, flags);
                }
            }
            return true;
        }
        private static class Proxy implements com.google.android.vending.licensing.ILicenseV2ResultListener
        {
            private android.os.IBinder mRemote;
            Proxy(android.os.IBinder remote)
            {
                mRemote = remote;
            }
            @Override public android.os.IBinder asBinder()
            {
                return mRemote;
            }
            public java.lang.String getInterfaceDescriptor()
            {
                return DESCRIPTOR;
            }
            @Override public void verifyLicense(int responseCode, android.os.Bundle responsePayload) throws android.os.RemoteException
            {
                android.os.Parcel _data = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(responseCode);
                    _Parcel.writeTypedObject(_data, responsePayload, 0);
                    boolean _status = mRemote.transact(Stub.TRANSACTION_verifyLicense, _data, null, android.os.IBinder.FLAG_ONEWAY);
                }
                finally {
                    _data.recycle();
                }
            }
        }
        static final int TRANSACTION_verifyLicense = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
    }
    public static final java.lang.String DESCRIPTOR = "com.android.vending.licensing.ILicenseV2ResultListener";
    public void verifyLicense(int responseCode, android.os.Bundle responsePayload) throws android.os.RemoteException;
    /** @hide */
    static class _Parcel {
        static private <T> T readTypedObject(
            android.os.Parcel parcel,
            android.os.Parcelable.Creator<T> c) {
            if (parcel.readInt() != 0) {
                return c.createFromParcel(parcel);
            } else {
                return null;
            }
        }
        static private <T extends android.os.Parcelable> void writeTypedObject(
            android.os.Parcel parcel, T value, int parcelableFlags) {
            if (value != null) {
                parcel.writeInt(1);
                value.writeToParcel(parcel, parcelableFlags);
            } else {
                parcel.writeInt(0);
            }
        }
    }
}
