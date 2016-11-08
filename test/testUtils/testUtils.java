/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testUtils;

import dsw.abstracts.dswCommon;
import extendedData.xData;

/**
 *
 * @author Alexey Savenkov OIS 2016
 */
public class testUtils {

    public static void testDSWParse(dswCommon dsw) {
        while (!dsw.EndOfSource()) {
            xData r = dsw.xRead();
            if (r != null) {
                System.out.println(r.toString());
            } else {
                //System.out.println("null");
            }
            if (dsw.EndOfSource())
                break;
        }
    }
}
