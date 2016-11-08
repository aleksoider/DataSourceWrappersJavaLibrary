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
 * @author Alexey Savenkov OIS 2016
 */
public class dswTextFileCharsTest {

    public dswTextFileCharsTest() {
    }
    
    dswTextFileChars tf;

    @Test
    public void test() {
        tf = new dswTextFileChars("test.txt");
        tf.Open(); 
        try{
            testUtils.testDSWParse(tf);
        }catch(Error e){
            System.out.println(e.getMessage());
        }
        tf.Close();
    }
}
