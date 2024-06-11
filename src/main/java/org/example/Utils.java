package org.example;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class Utils {
   public static <T> T cache(T obj){
       Class cls = obj.getClass();
       return (T) Proxy.newProxyInstance(
               cls.getClassLoader()
               ,cls.getInterfaces()
               , new ObjInvHandler(obj)
       );
   }

   static class ObjInvHandler implements InvocationHandler {
       private final Object curObj;
       public ObjInvHandler(Object obj) {
           this.curObj = obj;
       }

       Map<Method,Object> cachedMeth = new HashMap<>();

       @Override
       public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
           Object retObj;
           Method curMeth;

           //method из аргументов - это метод интерфеса. Надо найти метод которые его реализует в классе(обекте)
           curMeth = curObj.getClass().getMethod(method.getName(), method.getParameterTypes());

           //Если это кэшируемый метод
           //Проверить наличие в кэше и если есть - вернуть от туда значение без вычисления
           if(curMeth.isAnnotationPresent(Cache.class)){
               if (cachedMeth.containsKey(curMeth)) {
                   return cachedMeth.get(curMeth);
               }
               //а если в кэше еще нет значения для этого метода
               retObj = method.invoke(curObj,args);// то вычислить его
               cachedMeth.put(curMeth,retObj); // и занести в кэш
               return retObj; //и его же вернуть
            }

           //Если этот метод должен сбромить кэш
           //почистим кэш для всего обьекта (считается что обьект изменился и все ранее вычисленное стало не актуально)
            if(curMeth.isAnnotationPresent(Mutator.class)) {
                cachedMeth.clear();

            }

            //Если до сих пор ни чего не вернули.
           // Значит просто вернем то что метод вычислит без кэща (просто вызов метода без всей этой магии)
            return method.invoke(curObj, args);
       }
   }
}
