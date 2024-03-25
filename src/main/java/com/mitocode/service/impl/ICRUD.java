package com.mitocode.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface ICRUD<T, ID> {
        T save(T t);
        T update(T t, ID id);
        List<T> findAll();
        T findById(ID id);
        void deleteById(ID id);

}
