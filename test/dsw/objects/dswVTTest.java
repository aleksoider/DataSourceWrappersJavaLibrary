/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsw.objects;

import org.junit.Test;
import testUtils.testUtils;

/**
 *
 * @author Alexey Savenkov OIS 2016
 */
public class dswVTTest {
    
    dswVT fl;
    
     @Test
     public void test1() {
        fl=new dswVT("ТФ=1","VT.txt");
        testUtils.testDSWParse(fl);
     }
     
     @Test
     public void test2() {
        fl=new dswVT("2","VT.txt");
        testUtils.testDSWParse(fl);
     }
}
