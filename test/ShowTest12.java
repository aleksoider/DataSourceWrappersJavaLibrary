/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import dsw.abstracts.dswTextFile;
import dsw.objects.binaryFile.dswBinaryFileSearch;
import dsw.objects.dswArray;
import dsw.objects.dswMultilevel;
import dsw.objects.dswParadigms;
import dsw.objects.dswSeparatedString;
import dsw.objects.dswVL;
import dsw.objects.textFile.dswTextFileLines;
import extendedData.xData;
import extendedData.xDataFactory;
import java.util.ArrayList;
import org.junit.Test;
import testUtils.testUtils;

/**
 *
 * @author 123
 */
public class ShowTest12 {

    @Test
    public void test1() {
        System.out.println("test1--------------------------------------\n----------------------------------");
        ArrayList<xData> testArrDSW;        
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
        dswMultilevel testConv = new dswMultilevel(testArrDSW);
        testUtils.testDSWParse(testConv);
        ((dswTextFile) testArrDSW.get(0).getData()).Close();
        System.out.println("test1--------------------------------------\n----------------------------------\n\n\n");
    }
    
    
   
}
