package com.example.apt_process;

import com.example.apt_process.model.FieldViewBinding;
import com.example.apt_process.model.ProxyClass;
import com.google.auto.service.AutoService;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

import static java.lang.reflect.Modifier.PRIVATE;
import static java.lang.reflect.Modifier.STATIC;

//@SupportedAnnotationTypes("com.example.apt_annotation.AutoCreate")
//@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class AutoCreateProcess extends AbstractProcessor {
    private Filer mFiler; //文件相关工具类
    private Elements mElementUtils; //元素相关的工具类
    private Messager mMessager; //日志相关的工具类

    private Map<String, ProxyClass> mProxyClassMap = new HashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFiler = processingEnv.getFiler();
        mElementUtils = processingEnv.getElementUtils();
        mMessager = processingEnv.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        //处理被AutoCreate注解的元素
        for (Element element : roundEnvironment.getElementsAnnotatedWith(AutoCreate.class)) {
            if (!isValid(AutoCreate.class, "fields", element)) {
                return true;
            }
            parseCreateAnnotation(element);
        }

        //为每个宿主类生成所对应的代理类
        for (ProxyClass proxyClass_ : mProxyClassMap.values()) {
            try {
                proxyClass_.generateProxy().writeTo(mFiler);
            } catch (IOException e) {
                error(null, e.getMessage());
            }
        }
        mProxyClassMap.clear();

        return true;
    }

    private void parseCreateAnnotation(Element element) {
        ProxyClass proxyClass = getProxyClass(element);
        //把被注解的view对象封装成一个model，放入代理类的集合中
        FieldViewBinding bindView = new FieldViewBinding(element);
        proxyClass.add(bindView);
    }

    /**
     * 生成或获取注解元素所对应的ProxyClass类
     */
    private ProxyClass getProxyClass(Element element) {
        //被注解的变量所在的类
        TypeElement classElement = (TypeElement) element.getEnclosingElement();
        String qualifiedName = classElement.getQualifiedName().toString();
        ProxyClass proxyClass = mProxyClassMap.get(qualifiedName);
        if (proxyClass == null) {
            //生成每个宿主类所对应的代理类，后面用于生产java文件
            proxyClass = new ProxyClass(classElement, mElementUtils);
            mProxyClassMap.put(qualifiedName, proxyClass);
        }
        return proxyClass;
    }

    // 对注解进行检测
    private boolean isValid(Class<? extends Annotation> annotationClass, String targetThing, Element element) {
        boolean isVaild = true;
        //获取变量的所在的父元素，肯能是类、接口、枚举
        TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();

        //父元素的全限定名
        String qualifiedName = enclosingElement.getQualifiedName().toString();
        Set<Modifier> modifiers = element.getModifiers();
        // 修饰不能是private static
        if (modifiers.contains(PRIVATE) || modifiers.contains(STATIC)) {
            error(element, "@%s %s must not be private or static. (%s.%s)",
                    annotationClass.getSimpleName(), targetThing, enclosingElement.getQualifiedName(),
                    element.getSimpleName());
            isVaild = false;
        }
        // 父元素必须是类，而不能是接口或枚举
        if (enclosingElement.getKind() != ElementKind.CLASS) {
            error(enclosingElement, "@%s %s may only be contained in classes. (%s.%s)",
                    annotationClass.getSimpleName(), targetThing, enclosingElement.getQualifiedName(),
                    element.getSimpleName());
            isVaild = false;
        }

        //不能在Android框架层注解
        if (qualifiedName.startsWith("android.")) {
            error(element, "@%s-annotated class incorrectly in Android framework package. (%s)",
                    annotationClass.getSimpleName(), qualifiedName);
            return false;
        }
        //不能在java框架层注解
        if (qualifiedName.startsWith("java.")) {
            error(element, "@%s-annotated class incorrectly in Java framework package. (%s)",
                    annotationClass.getSimpleName(), qualifiedName);
            return false;
        }
        return isVaild;
    }

    private void error(Element e, String msg, Object... args) {
        mMessager.printMessage(Diagnostic.Kind.WARNING, String.format(msg, args), e);
    }

    /**
     * 指定哪些注解应该被注解处理器注册
     */
//    @Override
//    public Set<String> getSupportedOptions() {
//        Set<String> types = new LinkedHashSet<>();
//        types.add(AutoCreate.class.getName());
//        return types;
//    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(AutoCreate.class.getName());
        return types;
    }

    /**
     * 用来指定你使用的 java 版本
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
