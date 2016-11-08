/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsw.objects;

import extendedData.xData;
import org.junit.Test;
import testUtils.testUtils;

/**
 *
 * @author Alexey Savenkov OIS 2016
 */
public class dswSeparatedStringTest {

    public dswSeparatedStringTest() {
    }

    dswSeparatedString testDSWSepStr;

    @Test
    public void testdswSS() {
        System.out.println("testdswSS ------------");
        testDSWSepStr = new dswSeparatedString("aaa;bbb;ccc,ddd", ";");
        testUtils.testDSWParse(testDSWSepStr);
        /*[ aaa; xLevel: 0; xIndexOnLevel: 0; ]
         [ bbb; xLevel: 0; xIndexOnLevel: 1; ]
         [ ccc,ddd; xLevel: 0; xIndexOnLevel: 2;*/
        xData xdatatest = new xData("aaa;bbb;ccc,ddd");
        xdatatest.add("separator", ";");
        xdatatest.add("ignoreLast", true);
        testDSWSepStr = new dswSeparatedString(xdatatest);
        testUtils.testDSWParse(testDSWSepStr);
        /*[ aaa; xLevel: 0; xIndexOnLevel: 0; ]
         [ bbb; xLevel: 0; xIndexOnLevel: 1; ]*/
        System.out.println("testdswSS ------------\n");
    }

    @Test
    public void testConstructorDswSS() {
        System.out.println("testConstructorDswSS ------------");
        System.out.println(testDSWSepStr = new dswSeparatedString(null));
        //[ dswSeparatedString[dswArray[0]: 0] <null> : null ]
        System.out.println(testDSWSepStr = new dswSeparatedString(""));
        //[ dswSeparatedString[dswArray[1]: 0] <null> :  ]
        System.out.println(testDSWSepStr = new dswSeparatedString("aaa,bbb"));
        //[dswSeparatedString[dswArray[7]: 0] <null> : aaa,bbb ]
        System.out.println(testDSWSepStr = new dswSeparatedString("aaa;bbb", ";"));
        //[dswSeparatedString[dswArray[2]: 0] <;> : aaa;bbb ]
        System.out.println(testDSWSepStr.xRead().toString());
        //[ aaa; xLevel: 0; xIndexOnLevel: 0; ]
        System.out.println(testDSWSepStr.toString());
        //[ dswSeparatedString[dswArray[2]: 1] <;> : aaa;bbb ]
        System.out.println("testConstructorDswSS ------------\n");
    }
}
