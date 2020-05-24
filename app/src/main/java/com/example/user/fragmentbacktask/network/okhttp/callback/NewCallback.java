package com.example.user.fragmentbacktask.network.okhttp.callback;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;


public abstract class NewCallback<T,T1> extends Callback<ResponseEntity<T>> {

    private IGenericsSerializable mGenericsSerializable;

    public NewCallback(IGenericsSerializable serializable) {
        mGenericsSerializable = serializable;
    }

    @Override
    public ResponseEntity<T> parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        Class<T1> bean;
        Class<T> clazz;
        Type genericSuperclass = getClass().getGenericSuperclass();
        if(genericSuperclass instanceof ParameterizedType){
            //参数化类型
            ParameterizedType parameterizedType= (ParameterizedType) genericSuperclass;
            //返回表示此类型实际类型参数的 Type 对象的数组
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            clazz = (Class<T>)actualTypeArguments[0];
            bean= (Class<T1>)actualTypeArguments[1];
        }else{
            clazz = (Class<T>)genericSuperclass;
            bean= (Class<T1>)genericSuperclass;
        }

        return mGenericsSerializable.transform(string,clazz,bean);
    }

}
