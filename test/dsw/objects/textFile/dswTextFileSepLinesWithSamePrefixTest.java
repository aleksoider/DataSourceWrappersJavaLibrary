/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsw.objects.textFile;

import org.junit.Test;
import testUtils.testUtils;

/**
 *
 * @author 123
 */
public class dswTextFileSepLinesWithSamePrefixTest {
    
    dswTextFileSepLinesWithSamePrefix tf;
    
    public dswTextFileSepLinesWithSamePrefixTest() {
    }

    @Test
    public void test() {
        tf = new dswTextFileSepLinesWithSamePrefix("N=1","VT.txt", ";", 0);
        tf.Open();
        testUtils.testDSWParse(tf);
        tf.Close();
    }
}
