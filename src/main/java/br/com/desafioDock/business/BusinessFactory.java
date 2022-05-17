package br.com.desafioDock.business;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class BusinessFactory {

    private static final Logger LOGGER = Logger.getLogger(BusinessFactory.class);

    private static final Map<String, Object> mapBusiness = new HashMap<>();

    private static BusinessFactory instance = null;

    static {
        getInstance();
    }

    public BusinessFactory() {
    }

    public static synchronized BusinessFactory getInstance() {
        if (instance == null) {
            instance = new BusinessFactory();
        }

        return instance;
    }

    @SuppressWarnings("unchecked")
    public static <F> F getBusiness(Class<F> businessClass) {

        try {
            String chave = businessClass.getName();
            F business = (F) mapBusiness.get(chave);

            if (business == null) {
                business = (F) getConstrutorSemParametro(businessClass).newInstance();
                mapBusiness.put(chave, business);
            }
            return business;

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    private static <F> Constructor<?> getConstrutorSemParametro(Class<F> fbusinessClass) {
        for (Constructor<?> constructor : fbusinessClass.getConstructors()) {
            if (constructor.getParameterCount() == 0) {
                return constructor;
            }
        }
        return null;
    }
}
