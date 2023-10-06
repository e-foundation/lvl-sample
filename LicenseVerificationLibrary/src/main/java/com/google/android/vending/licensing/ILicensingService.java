/*
 * This file is auto-generated.  DO NOT MODIFY.
 */
package com.google.android.vending.licensing;
public interface ILicensingService extends android.os.IInterface
{
    /** Default implementation for ILicensingService. */
    public static class Default implements com.google.android.vending.licensing.ILicensingService
    {
        @Override public void checkLicense(long nonce, java.lang.String packageName, com.google.android.vending.licensing.ILicenseResultListener listener) throws android.os.RemoteException
        {
        }
        @Override public void checkLicenseV2(java.lang.String packageName, com.google.android.vending.licensing.ILicenseV2ResultListener listener, android.os.Bundle extraParams) throws android.os.RemoteException
        {
        }
        @Override
        public android.os.IBinder asBinder() {
            return null;
        }
    }
    /** Local-side IPC implementation stub class. */
    public static abstract class Stub extends android.os.Binder implements com.google.android.vending.licensing.ILicensingService
    {
        /** Construct the stub at attach it to the interface. */
        public Stub()
        {
            this.attachInterface(this, DESCRIPTOR);
        }
        /**
         * Cast an IBinder object into an com.google.android.vending.licensing.ILicensingService interface,
         * generating a proxy if needed.
         */
        public static com.google.android.vending.licensing.ILicensingService asInterface(android.os.IBinder obj)
        {
            if ((obj==null)) {
                return null;
            }
            android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (((iin!=null)&&(iin instanceof com.google.android.vending.licensing.ILicensingService))) {
                return ((com.google.android.vending.licensing.ILicensingService)iin);
            }
            return new com.google.android.vending.licensing.ILicensingService.Stub.Proxy(obj);
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
                case TRANSACTION_checkLicense:
                {
                    long _arg0;
                    _arg0 = data.readLong();
                    java.lang.String _arg1;
                    _arg1 = data.readString();
                    com.google.android.vending.licensing.ILicenseResultListener _arg2;
                    _arg2 = com.google.android.vending.licensing.ILicenseResultListener.Stub.asInterface(data.readStrongBinder());
                    this.checkLicense(_arg0, _arg1, _arg2);
                    break;
                }
                case TRANSACTION_checkLicenseV2:
                {
                    java.lang.String _arg0;
                    _arg0 = data.readString();
                    com.google.android.vending.licensing.ILicenseV2ResultListener _arg1;
                    _arg1 = com.google.android.vending.licensing.ILicenseV2ResultListener.Stub.asInterface(data.readStrongBinder());
                    android.os.Bundle _arg2;
                    _arg2 = _Parcel.readTypedObject(data, android.os.Bundle.CREATOR);
                    this.checkLicenseV2(_arg0, _arg1, _arg2);
                    break;
                }
                default:
                {
                    return super.onTransact(code, data, reply, flags);
                }
            }
            return true;
        }
        private static class Proxy implements com.google.android.vending.licensing.ILicensingService
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
            @Override public void checkLicense(long nonce, java.lang.String packageName, com.google.android.vending.licensing.ILicenseResultListener listener) throws android.os.RemoteException
            {
                android.os.Parcel _data = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeLong(nonce);
                    _data.writeString(packageName);
                    _data.writeStrongInterface(listener);
                    boolean _status = mRemote.transact(Stub.TRANSACTION_checkLicense, _data, null, android.os.IBinder.FLAG_ONEWAY);
                }
                finally {
                    _data.recycle();
                }
            }
            @Override public void checkLicenseV2(java.lang.String packageName, com.google.android.vending.licensing.ILicenseV2ResultListener listener, android.os.Bundle extraParams) throws android.os.RemoteException
            {
                android.os.Parcel _data = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeStrongInterface(listener);
                    _Parcel.writeTypedObject(_data, extraParams, 0);
                    boolean _status = mRemote.transact(Stub.TRANSACTION_checkLicenseV2, _data, null, android.os.IBinder.FLAG_ONEWAY);
                }
                finally {
                    _data.recycle();
                }
            }
        }
        static final int TRANSACTION_checkLicense = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
        static final int TRANSACTION_checkLicenseV2 = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
    }
    public static final java.lang.String DESCRIPTOR = "com.android.vending.licensing.ILicensingService";
    public void checkLicense(long nonce, java.lang.String packageName, com.google.android.vending.licensing.ILicenseResultListener listener) throws android.os.RemoteException;
    public void checkLicenseV2(java.lang.String packageName, com.google.android.vending.licensing.ILicenseV2ResultListener listener, android.os.Bundle extraParams) throws android.os.RemoteException;
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
