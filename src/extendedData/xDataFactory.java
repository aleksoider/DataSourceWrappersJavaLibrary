/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package extendedData;

import java.util.ArrayList;

/**
 * Factory class for creating extended data(xData)
 * Simplifies creating elements
 * 
 * @author Alexey Savenkov OIS 2016
 */
public class xDataFactory {
    
    /**   
     * create ArrayList of xData with iput parameters
     * 
     * @param input input Array of Object
     * @return List of xData
     */
    public static ArrayList<xData> createxDataList(Object[] input) {
        ArrayList<xData> res = new ArrayList<>();
        for (Object element : input) {
            res.add(xDataFactory.createxData((Object[]) element));
        }
        return res;
    }
    
    /**
     * create single xData object with input parameters
     * 
     * @param input input Array of Object
     * @return xData object
     */
    public static xData createxData(Object[] input) {
        xData res = new xData(input[0]);
        for (int i = 1; i < input.length; i += 2) {
            if (input[i] instanceof String) {
                if ("maskForNext".equals((String) input[i])) {
                    xData subRes = xDataFactory.createxData((Object[]) input[i + 1]);
                    res.add("maskForNext", subRes);
                } else {
                    res.add((String) input[i], input[i + 1]);
                }
            }
        }
        return res;
    }
}
