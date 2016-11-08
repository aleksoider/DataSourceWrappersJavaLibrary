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
public class dswTextFileLinesTest {
    
    dswTextFileLines tf;
    
    public dswTextFileLinesTest() {
    }        

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void test() {
        Object d="test.txt";
        System.out.println("test1----------------------------");
        tf=new dswTextFileLines("test.txt");
        tf.Open();
        testUtils.testDSWParse(tf);
        /*[ aaaaa; xLevel: 0; xIndexOnLevel: 0; ]
          [ bbbbb; xLevel: 0; xIndexOnLevel: 1; ]
          [ ccccc; xLevel: 0; xIndexOnLevel: 2; ]*/
        tf.Close();
        System.out.println("test1----------------------------\n");
    }
    
    @Test
    public void test2() {
        System.out.println("test2----------------------------");
        try{
            System.out.println(tf=new dswTextFileLines("123123.txt"));
            tf.Open();//<dswTextFile>.Open error occurred trying to open 123123.txt 123123.txt (Не удается найти указанный файл)
        }catch(Error e){
            System.out.println(e.getMessage());
        }
        System.out.println(tf=new dswTextFileLines("test.txt"));
        tf.Open();
        System.out.println(tf.xRead());//[ aaaaa; xLevel: 0; xIndexOnLevel: 0; ]
        System.out.println(tf.xRead());//[ bbbbb; xLevel: 0; xIndexOnLevel: 1; ]
        System.out.println(tf.xRead());//[ ccccc; xLevel: 0; xIndexOnLevel: 2; ]
        tf.Close();
        System.out.println("test2----------------------------\n");
    }
}
