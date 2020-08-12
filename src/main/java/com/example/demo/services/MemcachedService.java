package com.example.demo.services;

import com.google.code.ssm.Cache;
import com.google.code.ssm.api.format.SerializationType;
import com.google.code.ssm.providers.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeoutException;

@Service
public class MemcachedService {

    private static Cache memcached;
    private final static SerializationType SERIALIZATION = SerializationType.PROVIDER;

    @Autowired
    public MemcachedService(Cache memcached) {
        this.memcached = memcached;
    }

    public Boolean saveToCache(String key, int expiry, Object data) throws TimeoutException, CacheException {
        return memcached.add(key, expiry, data, SERIALIZATION);
    }

    public Object getFromCache(String key) throws TimeoutException, CacheException {
        Object object = memcached.get(key, SERIALIZATION);
        return object;
    }

    public static void deleteWholeCache() throws TimeoutException, CacheException {
        memcached.flush();
    }
}
