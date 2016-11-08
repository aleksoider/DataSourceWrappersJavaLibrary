/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsw.objects.binaryFile;

import extendedData.xDataFactory;
import org.junit.Test;

/**
 *
 * @author Alexey Savenkov OIS 2016
 */
public class dswBinaryFileSearchTest {
    
    dswBinaryFileSearch bf;
//
//     @Test
//     public void test() {
//        bf=new dswBinaryFileSearch("testBin.bin",1,6,"вввввв");
//        bf.Open();
//        System.out.println(bf.xRead());
//        bf.Close();
//     }
     
     @Test
     public void test2() {
        //bf=new dswBinaryFileSearch("paradigms.bin",1,102,"пятикратный","\\s+");
     //   bf=new dswBinaryFileSearch(xDataFactory.createxData(new Object[]{"привет","path","paradigms.bin","blockLength",102,"separator","\\s+"}));
//        bf.Open();
        System.out.println(bf.xRead());
        bf.Close();
     }
}
