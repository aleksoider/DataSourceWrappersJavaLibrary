/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsw.objects;

import extendedData.xData;
import extendedData.xDataFactory;
import dsw.abstracts.dswTextFile;
import dsw.objects.textFile.dswTextFileLines;
import java.util.ArrayList;
import org.junit.Test;
import testUtils.testUtils;

/**
 *
 * @author Alexey Savenkov OIS 2016
 */
public class dswMultilevelTest {
    
    dswMultilevel testConv;
    
    public dswMultilevelTest() {
    }

//    @Test
//    public void test() {//using xDataFactory
//        System.out.println("test1------------");
//        ArrayList<xData> testArrDSW = new ArrayList<>();        
//        testArrDSW = xDataFactory.createxDataList(
//                new Object[]{
//                    new Object[]{
//                        new dswTextFileLines("Vl-3.txt"),
//                        "maskForNext", new Object[]{null, "ignoreLast", false}},
//                    new Object[]{new dswSeparatedString(null, ";"),
//                        "maskForNext", new Object[]{null, "ignoreSingle", true}},
//                    new Object[]{new dswSeparatedString(null, "="),
//                        "maskForNext", new Object[]{null, "ignoreSingle", true}},
//                    new Object[]{new dswSeparatedString(null, ",")}});
//        ((dswTextFile) testArrDSW.get(0).getData()).Open();
//        testConv = new dswMultilevel(testArrDSW);
//        testUtils.testDSWParse(testConv);
//        ((dswTextFile) testArrDSW.get(0).getData()).Close();
//        System.out.println("test1------------\n");
//    }
//
//    @Test
//    public void test2() {//simple initialization
//        System.out.println("test2------------");
//        ArrayList<xData> testArrDSW = new ArrayList<>();
//        xData xd1 = new xData(new dswTextFileLines("Vl-1.txt"));
//        xData xdmfn = new xData(null);
//        xdmfn.add("ignoreLast", false);
//        xd1.add("maskForNext", xdmfn);
//        xData xd2 = new xData(new dswSeparatedString(null, ";"));
//        xdmfn = new xData(null);
//        xdmfn.add("ignoreSingle", true);
//        xd2.add("maskForNext", xdmfn);
//        xData xd3 = new xData(new dswSeparatedString(null, "="));
//        xdmfn = new xData(null);
//        xdmfn.add("ignoreSingle", true);
//        xd3.add("maskForNext", xdmfn);
//        xData xd4 = new xData(new dswSeparatedString(null, ","));
//        testArrDSW.add(xd1);
//        testArrDSW.add(xd2);
//        testArrDSW.add(xd3);
//        testArrDSW.add(xd4);
//        ((dswTextFile) testArrDSW.get(0).getData()).Open();
//        testConv = new dswMultilevel(testArrDSW);
//        testUtils.testDSWParse(testConv);
//        ((dswTextFile) testArrDSW.get(0).getData()).Close();
//        System.out.println("test2------------\n");
//    }
    
    @Test
    public void test3() {//using xDataFactory
        System.out.println("test3------------");
        ArrayList<xData> testArrDSW = new ArrayList<>();        
        testArrDSW = xDataFactory.createxDataList(
                new Object[]{
                    new Object[]{
                        new dswTextFileLines("Vl-3.txt"),
                        "maskForNext", new Object[]{null, "ignoreLast", false}},
                    new Object[]{new dswSeparatedString(null, ";"),
                        "maskForNext", new Object[]{null, "ignoreSingle", true}},
                    new Object[]{new dswSeparatedString(null, "="),
                        "maskForNext", new Object[]{null, "ignoreSingle", true}},
                    new Object[]{new dswSeparatedString(null, ",")}});
        ((dswTextFile) testArrDSW.get(0).getData()).Open();
        testConv = new dswMultilevel(xDataFactory.createxData(new Object[]{testArrDSW,"indexesToPrint", new int[]{1,2}}));
        testUtils.testDSWParse(testConv);
        ((dswTextFile) testArrDSW.get(0).getData()).Close();
        System.out.println("test3------------\n");
    }
    
}
