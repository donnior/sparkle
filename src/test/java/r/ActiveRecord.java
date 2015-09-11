package r;


import com.google.common.base.Throwables;
import s.I;

public abstract class ActiveRecord {



    public static ActiveRecord find(String id){

        Class clz = sun.reflect.Reflection.getCallerClass(1);
        System.out.println("active record is finding" + clz);

        Throwable t = new Throwable();
        StackTraceElement directCaller = t.getStackTrace()[1];
        for (StackTraceElement e : t.getStackTrace()){
            System.out.println(e);
        }
//        System.out.println(t.getStackTrace());

//        System.out.println(ClassloaderUtil.getCallingClass());

        return null;
    }

}
