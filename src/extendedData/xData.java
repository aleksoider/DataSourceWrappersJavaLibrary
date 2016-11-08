/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package extendedData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * extended data object(xData)
 *
 * @author Alexey Savenkov OIS 2016
 */
public class xData {

    private Object xData;
    private HashMap<String, Object> props;

    /**
     * default constructor
     *
     * @param data
     */
    public xData(Object data) {
        this.xData = data;
        props = new HashMap<>();
    }

    /**
     * add a property value with a specified key
     *
     * @param key
     * @param object
     */
    public void add(String key, Object object) {
        if (this.props.containsKey(key)) {
            this.props.replace(key, object);
        } else {
            this.props.put(key, object);
        }
    }

    /**
     * set new data
     *
     * @param data everything
     */
    public void setData(Object data) {
        this.xData = data;
    }

    /**
     * get data
     *
     * @return xData value
     */
    public Object getData() {
        return xData;
    }

    /**
     * get property value with specified key
     *
     * @param key key string
     * @return property value
     */
    public Object get(String key) {
        return this.props.get(key);
    }

    /**
     * check if property value with specified key exists in xData
     *
     * @param key
     * @return
     */
    public Boolean hasOwnProperty(String key) {
        return this.props.get(key) != null;
    }

    @Override
    public String toString() {
        String str;
        if (xData instanceof char[]) {
            str = "[ " + Arrays.toString((char[]) xData) + "; ";
        } else if (xData instanceof int[]) {
            str = "[ " + Arrays.toString((int[]) xData) + "; ";
        } else if (xData instanceof String[]) {
            str = "[ " + Arrays.toString((String[]) xData) + "; ";
        } else if (xData instanceof ArrayList) {
            str = "[ ";
            for (int i = 0; i < ((ArrayList) xData).size(); i++) {
                str += ((ArrayList) xData).get(i).toString() + "; ";
            }
        } else {
            str = "[ " + xData + "; ";
        }
        for (Map.Entry entry : props.entrySet()) {
            str += entry.getKey() + ": " + entry.getValue() + "; ";
        }
        return str + "]";
    }
}
