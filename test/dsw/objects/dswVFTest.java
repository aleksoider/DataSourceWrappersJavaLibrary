/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsw.objects;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import testUtils.testUtils;

/**
 *
 * @author Alexey Savenkov OIS 2016
 */
public class dswVFTest {
    
    dswVF vf;

     @Test
     public void test() {
         vf=new dswVF("VF.txt","кабинет");
         testUtils.testDSWParse(vf);
     }
}
