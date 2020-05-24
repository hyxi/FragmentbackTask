/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /Users/hyxi/workspace/ASworkspace/ReferProject/FragmentbackTask/app/src/main/aidl/com/example/user/fragmentbacktask/messager/IRemoteServiceCallback.aidl
 */
package com.example.user.fragmentbacktask.messager;
// Declare any non-default types here with import statements

public interface IRemoteServiceCallback extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.example.user.fragmentbacktask.messager.IRemoteServiceCallback
{
private static final java.lang.String DESCRIPTOR = "com.example.user.fragmentbacktask.messager.IRemoteServiceCallback";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.example.user.fragmentbacktask.messager.IRemoteServiceCallback interface,
 * generating a proxy if needed.
 */
public static com.example.user.fragmentbacktask.messager.IRemoteServiceCallback asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.example.user.fragmentbacktask.messager.IRemoteServiceCallback))) {
return ((com.example.user.fragmentbacktask.messager.IRemoteServiceCallback)iin);
}
return new com.example.user.fragmentbacktask.messager.IRemoteServiceCallback.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
java.lang.String descriptor = DESCRIPTOR;
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(descriptor);
return true;
}
case TRANSACTION_valueChange:
{
data.enforceInterface(descriptor);
int _arg0;
_arg0 = data.readInt();
this.valueChange(_arg0);
return true;
}
default:
{
return super.onTransact(code, data, reply, flags);
}
}
}
private static class Proxy implements com.example.user.fragmentbacktask.messager.IRemoteServiceCallback
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
/**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
@Override public void valueChange(int value) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(value);
mRemote.transact(Stub.TRANSACTION_valueChange, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
}
static final int TRANSACTION_valueChange = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
/**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
public void valueChange(int value) throws android.os.RemoteException;
}
