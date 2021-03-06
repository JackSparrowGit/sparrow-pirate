package com.corsair.sparrow.pirate.gamma.config;

import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.thrift.TApplicationException;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.TServiceClientFactory;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author jack
 */
public class ThriftConsumerProxy {

    GenericKeyedObjectPool<String, TSocket> pool = new GenericKeyedObjectPool<>(new ThriftSocketPoolFactory());
    public Object proxy(Class<?> iFaceInterface, String host, int port) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Class<?> factoryClass = getFactoryClass(iFaceInterface);
        String serviceName = iFaceInterface.getDeclaringClass().getName();
        return Proxy.newProxyInstance(classLoader, new Class[] { iFaceInterface }, new InvocationHandler() {

            @SuppressWarnings("unchecked")
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                String key = host+":"+port;
                Object result = null;
                TSocket tsocket = null;
                try {
                    TServiceClientFactory<TServiceClient> clientFactory = (TServiceClientFactory<TServiceClient>) factoryClass.newInstance();
                    tsocket = pool.borrowObject(key);
                    TTransport transport = new TFramedTransport(tsocket);
                    TProtocol protocol = new TBinaryProtocol(transport);
                    TMultiplexedProtocol mpProtocol = new TMultiplexedProtocol(protocol, serviceName);
                    TServiceClient client = clientFactory.getClient(mpProtocol);
                    result = method.invoke(client, args);
                } catch (Exception e) {
                    if(!InvocationTargetException.class.isInstance(e)) {
                        if(null != tsocket) {
                            pool.invalidateObject(key, tsocket);
                        }
                        throw e;
                    }
                    InvocationTargetException inve = (InvocationTargetException) e;
                    if(!TApplicationException.class.isInstance(inve.getTargetException())) {
                        throw e;
                    }

                    TApplicationException appe = (TApplicationException)inve.getTargetException();
                    if(appe.getType() != TApplicationException.MISSING_RESULT) {
                        throw e;
                    }
                    result = null;
                }
                finally {
                    if( null != tsocket) {
                        pool.returnObject(key, tsocket);
                    }
                }
                return result;
            }

        });
    }

    private Class<?> getFactoryClass(Class<?> iFaceInterface) {
        Class<?> factoryClass = null;
        try {
            Class<?> serviceClass = iFaceInterface.getDeclaringClass();
            factoryClass = iFaceInterface.getClassLoader().loadClass(serviceClass.getName() + "$Client$Factory");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return factoryClass;
    }
}
