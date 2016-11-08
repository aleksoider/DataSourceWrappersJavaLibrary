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
 * @author 123
 */
public class dswParadigmsTest {
    
    dswParadigms bf;

     @Test
     public void test() {
        bf = new dswParadigms("paradigmsPrefixesIndex.bin","paradigmsIndex.bin","paradigmsPrefixes.bin","paradigms.bin","эта");
        //System.out.println(bf.xRead());
        testUtils.testDSWParse(bf);
     }
}
