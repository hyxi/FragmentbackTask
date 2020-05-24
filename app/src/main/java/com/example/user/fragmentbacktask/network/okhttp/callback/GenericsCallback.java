package com.example.user.fragmentbacktask.network.okhttp.callback;

import android.util.Log;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;


public abstract class GenericsCallback<T> extends Callback<T> {
    private IGenericsSerializable mGenericsSerializator;

    public GenericsCallback(IGenericsSerializable serializator) {
        mGenericsSerializator = serializator;
    }

    @Override
    public T parseNetworkResponse(Response response, int id) throws IOException {
        //当前对象的直接超类的 Type
        try {
            String string = response.body().string();
            Class<T> entityClass;
            Type genericSuperclass = getClass().getGenericSuperclass();
            if (genericSuperclass instanceof ParameterizedType) {
                //参数化类型
                ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
                //返回表示此类型实际类型参数的 Type 对象的数组
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                entityClass = (Class<T>) actualTypeArguments[0];
            } else {
                entityClass = (Class<T>) genericSuperclass;
            }
            if (entityClass == String.class) {
                return (T) string;
            }

            return mGenericsSerializator.transform(string, entityClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
