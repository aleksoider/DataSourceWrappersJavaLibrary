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
public class ShowTest {
    
    @Test
    public void test3() {
        System.out.println("test3--------------------------------------\n----------------------------------");
        dswMultilevel testConv = new dswMultilevel(xDataFactory.createxData(
                            new Object[]{xDataFactory.createxDataList(
                                    new Object[]{
                                        new Object[]{
                                            new dswArray(new String[]{"тестовая проверка наличия  слова в           словаре   для презентации"}),
                                            "maskForNext", new Object[]{null, "ignoreLast", false}},
                                        new Object[]{new dswSeparatedString(null, "\\s+"),
                                            "maskForNext", new Object[]{null}},
                                        new Object[]{new dswVL(null)}}),
                            "indexesToReturn", new int[]{2}
                            }));
        testUtils.testDSWParse(testConv);
        System.out.println("test3--------------------------------------\n----------------------------------\n\n\n");
    }   
   
}
