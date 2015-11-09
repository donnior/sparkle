package org.agilej.sparkle.core.dev;


import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class SimpleTemplateEngine {

    private boolean usePrevious = false;

    public  String render(String template, Map<String, String> valus) throws Exception {
        Reader sr = new StringReader(template);
        StringWriter wr = new StringWriter();

        for (;;) {
            int c;
            if (usePrevious){
                c = '@';
                usePrevious =false;
            } else {
                c = sr.read();
            }
            if (c == -1) { //EOF
                break;
            }
            if (c == '@') {
                String var = readVariable(sr);
                String val = getValue(var, valus);
                wr.append(val);
            }
            else {
                wr.write(c);
            }
        }

        return wr.toString();
    }


    private  String getValue(String var, Map<String, String> map)  {

        return map.get(var);
    }

    private  String readVariable(Reader sr)throws Exception {
        StringBuilder nameSB = new StringBuilder();
        for (;;) {
            int c = sr.read();
            if (c == -1) {
                if (nameSB.toString().isEmpty()) {
                    throw new IllegalStateException("Premature EOF.");
                } else {
                    return nameSB.toString();
                }
            }
            if (c == ' '){
                return nameSB.toString();
            }
            if (c == '@') {
                usePrevious = true;
                break;
            }
            nameSB.append((char)c);
        }
        return nameSB.toString();
    }

}
