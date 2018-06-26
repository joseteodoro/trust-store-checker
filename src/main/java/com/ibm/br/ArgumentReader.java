package com.ibm.br;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by josets on 4/3/17.
 */
public class ArgumentReader {

    public Map<String, String> readProperties(String [] args) {
        Map<String, String> properties = new HashMap<String,String>();
        for (String arg : args) {
            String key = arg.replaceAll("(=).*","");
            String value = arg.replaceAll(".*(=)","");
            properties.put(key, value);
        }
        return properties;
    }

}
