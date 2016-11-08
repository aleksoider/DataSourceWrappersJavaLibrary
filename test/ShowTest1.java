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
public class ShowTest1 {
    
    @Test
    public void test2() {
        System.out.println("test2--------------------------------------\n----------------------------------");
        dswMultilevel testConv = new dswMultilevel(xDataFactory.createxDataList(
                                    new Object[]{
                                        new Object[]{
                                            new dswArray(new String[]{"test Array","     of                String"}),
                                            "maskForNext", new Object[]{null, "ignoreLast", false}},
                                        new Object[]{new dswSeparatedString(null, "\\s+")}}));
                
        testUtils.testDSWParse(testConv);
        System.out.println("test2--------------------------------------\n----------------------------------\n\n\n");
    }
       
   
}
