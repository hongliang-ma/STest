package com.dagger.dtest.util.log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 打印日志的接口和类的动态代理
 * @author tinghe
 * @version $Id: PrintLogInterceptor.java,v 0.1 2011-12-22 下午03:10:38 tinghe Exp $
 */
public class PrintLogInterceptor implements MethodInterceptor, InvocationHandler {
    private static final Log logger    = LogFactory.getLog(PrintLogInterceptor.class);

    private String           orgBeanName;
    private Object           orgBean;
    private boolean          logParam  = true;
    private boolean          logResult = true;
    private int              logLevel  = 0;

    public PrintLogInterceptor(String orgBeanName, Object orgBean, boolean logParam,
                               boolean logResult, int logLevel) {
        this.orgBeanName = orgBeanName;
        this.orgBean = orgBean;
        this.logParam = logParam;
        this.logResult = logResult;
        this.logLevel = logLevel;
    }

    public PrintLogInterceptor(String orgBeanName, Object orgBean) {
        this(orgBeanName, orgBean, true, true, 0);
    }

    /**
     * 创建一个使用了代理的对象
     * @param targetClass
     * @return
     */
    public Object create(Class<?> targetClass) {
        if (targetClass.isInterface()) {
            return Proxy.newProxyInstance(targetClass.getClassLoader(),
                new Class[] { targetClass }, this);
        }

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetClass);
        enhancer.setCallback(this);
        return enhancer.create();
    }

    protected void printInvokeInfo(Method method) {
        String invokeInfo = "Invoke INFO: bean=[" + this.orgBeanName + "],type=["
                            + this.orgBean.getClass() + "],method=[" + method.getName() + "]";
        logger.warn(invokeInfo);
    }

    protected void printParam(Object[] args) {
        String paramInfo;
        if (null != args && args.length > 0) {
            paramInfo = "Invoke with " + args.length + " params: ";
            for (Object param : args) {
                paramInfo += "[" + param + "] ";
            }
        } else {
            paramInfo = "Invoke with 0 params.";
        }
        logger.warn(paramInfo);
    }

    protected void printResult(Object result) {
        String paramInfo;
        if (null != result) {
            paramInfo = "Invoke result [" + result + "]";
        } else {
            paramInfo = "Invoke result is null.";
        }
        logger.warn(paramInfo);
    }

    /**
     * 类的拦截
     * 
     * @param obj
     * @param method
     * @param args
     * @param proxy
     * @return
     * @throws Throwable
     * @see net.sf.cglib.proxy.MethodInterceptor#intercept(java.lang.Object, java.lang.reflect.Method, java.lang.Object[], net.sf.cglib.proxy.MethodProxy)
     */
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy)
                                                                                        throws Throwable {
        printInvokeInfo(method);
        if (logParam) {
            printParam(args);
        }
        Object result = null;
        result = proxy.invoke(orgBean, args);
        if (logResult) {
            printResult(result);
        }
        return result;
    }

    /**
     * 接口的拦截
     * 
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
     */
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        printInvokeInfo(method);
        if (logParam) {
            printParam(args);
        }
        Object result = null;
        result = method.invoke(orgBean, args);
        if (logResult) {
            printResult(result);
        }
        return result;
    }
}
