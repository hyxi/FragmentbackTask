package com.example.apt_process;

import java.util.LinkedHashMap;
import java.util.Map;

public class FactoryGroupedClasses {

    private String qualifiedClassName;
    private Map<Integer, FactoryGroupedClasses> itemsMap = new LinkedHashMap<>();

    public FactoryGroupedClasses(String qualifiedClassName) {
        this.qualifiedClassName = qualifiedClassName;
    }

    public void add(FactoryAnnotatedClass toInsert) throws IdAlreadyUsedException {

//        if (existing != null) {
//            throw new IdAlreadyUsedException(existing);
//        }

    }

}
