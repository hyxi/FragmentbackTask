package com.example.apt_process.model;

import com.example.apt_process.AutoCreate;

import javax.lang.model.element.Element;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

/**
 * Created by huangyuxi on 2019/4/10
 * Title:
 */
public class FieldViewBinding {
    private VariableElement mElement;

    private int mResId;
    private String mVariableName;
    // 变量类型
    private TypeMirror mTypeMirror;

    public FieldViewBinding(Element element) {
        mElement = (VariableElement) element;
        AutoCreate viewById = element.getAnnotation(AutoCreate.class);
        mResId = viewById.value();
        //变量名
        mVariableName = element.getSimpleName().toString();
        //变量类型
        mTypeMirror = element.asType();
    }


    public VariableElement getElement() {
        return mElement;
    }

    public int getResId() {
        return mResId;
    }

    public String getVariableName() {
        return mVariableName;
    }

    public TypeMirror getTypeMirror() {
        return mTypeMirror;
    }
}
