package com.example.user.fragmentbacktask.network.okhttp.callback;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IGenericsSerializable {

    /**
     * 返回bean类型数据
     *
     * @param response json 字符串
     * @param classOfT 实体类
     * @return 返回bean类型数据
     */
    public <T> T transform(String response, Class<T> classOfT) {
        return new Gson().fromJson(response, classOfT);
    }

    public <T, T1> ResponseEntity<T> transform(String response, Class<T> clazz, Class<T1> bean) {
        ResponseEntity<T> qr = null;
        try {
            qr = new ResponseEntity<>();
            T1 origin = bean.newInstance();

            JSONObject jsonObj = new JSONObject(response);
            Iterator<String> iterator = jsonObj.keys();
            while (iterator.hasNext()) {
                String key = iterator.next();
                Object objValue = jsonObj.get(key);
                if (objValue instanceof JSONArray) {
                    ArrayList<T> list = new ArrayList<>();
                    ArrayList<T> listObj = new Gson().fromJson(objValue.toString(),
                            new TypeToken<List<T>>() {
                            }.getType());
                    for (T t : listObj) {
                        T perT = new Gson().fromJson(t.toString(), clazz);
                        list.add(perT);
                    }
                    qr.setList(list);
                } else if (objValue instanceof String) {
                    try {
                        Field field1 = bean.getField(key);
                        if (field1 != null) {
                            field1.set(origin, objValue.toString());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            qr.setBean(origin);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return qr;
    }
}
