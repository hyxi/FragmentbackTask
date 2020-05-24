// IRemoteServiceCallback.aidl
package com.example.user.fragmentbacktask.messager;

// Declare any non-default types here with import statements
oneway interface IRemoteServiceCallback {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
     void valueChange(int value);
}
