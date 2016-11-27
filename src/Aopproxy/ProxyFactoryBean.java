package Aopproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyFactoryBean implements InvocationHandler {

    private String proxyInterfaces;
    private Object object;
    private String interceptorNames;

    public void setFactory(BeanFactory factory) {
        this.factory = factory;
    }

    private BeanFactory factory;

    public BeanFactory getFactory() {
        return factory;
    }

    public String getProxyInterfaces() {
        return proxyInterfaces;
    }

    public void setProxyInterfaces(String proxyInterfaces) {
        this.proxyInterfaces = proxyInterfaces;
    }


    public String getInterceptorNames() {
        return interceptorNames;
    }

    public void setInterceptorNames(String interceptorNames) {
        this.interceptorNames = interceptorNames;
    }
	
	public Object getObj() {
        return object;
    }

    public void setObj(Object object) {
        this.object = object;
    }
	

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(interceptorNames!=null) {
            Object advice = factory.getBean(interceptorNames);
            if ( advice instanceof MethodBeforeAdvice) {
                ((MethodBeforeAdvice)advice).before(method,args,object);
                Object result = method.invoke(object,args);
                return result;
            }
        }
        Object result = method.invoke(object,args);
        return result;
    }

    @Override
    public String toString() {
        return "ProxyFactoryBean{" + "proxyInterfaces='" + proxyInterfaces + '\'' + ", object=" + object +  ", interceptorNames='" + interceptorNames + '\'' + '}';
    }

    public Object getProxy(){
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                object.getClass().getInterfaces(),
                this);
    }
}
