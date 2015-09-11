package r;

import s.I;

public class CI implements I {
    @Override
    public void a() {
        // TODO Auto-generated method stub

    }

    public static CI getOne(){
        Class clz = sun.reflect.Reflection.getCallerClass(1);
        System.out.print(clz);
        return new CI();
    }

}
