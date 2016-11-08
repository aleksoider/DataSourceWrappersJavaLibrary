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
public class dswVLTest {
    
      
    @Test
    public void start() {
        dswVL test=new dswVL("VL.txt","терм");
        testUtils.testDSWParse(test);
    }
}
