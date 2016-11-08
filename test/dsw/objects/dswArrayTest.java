/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsw.objects;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import extendedData.xData;
/**
 *
 * @author Alexey Savenkov OIS 2016
 */
public class dswArrayTest {
    
    private dswArray dswArray;
    
    public dswArrayTest() {
    }
    
    @Test()
    public void emptyTest() {
        dswArray = new dswArray(null);
        System.out.println("emptyTest --- " + dswArray.toString()+"\n");//emptyTest --- [dswArray[0]: 0]
    }

    @Test
    public void typeMismatch1() {
         System.out.println("type test------------------");
        try{
            dswArray = new dswArray(0);//dswArray.SetDataSource: type mismatch
        }catch(Error e){
            System.out.println("------------\n"+e.toString()+"\n------------");
        }
        try{
            dswArray = new dswArray(true);//dswArray.SetDataSource: type mismatch
        }catch(Error e){
            System.out.println("------------\n"+e.toString()+"\n------------");
        }
        try{
            dswArray = new dswArray("String");//dswArray.SetDataSource: type mismatch
        }catch(Error e){
            System.out.println("------------\n"+e.toString()+"\n------------");
        }
        System.out.println("type test------------------\n");
    }    
    
    @Test//check methods for object with empty array:
    public void testEmptyArr() {
        System.out.println("testEmptyArr --------------");
        xData testxd = new xData(new ArrayList());
        testxd.add("ignoreLast", true);
        testxd.add("ignoreSingle", false);
        dswArray = new dswArray(testxd);
        System.out.println("testArr1 ------------ \n" + dswArray.toString());//testArr1 --- [dswArray[0/-1]: 0]
        Object res=dswArray.GetDataSource();
        if(res instanceof ArrayList){
            System.out.println("size="+((ArrayList)res).size());//0
        }else
            fail("type mismatch");
        res=dswArray.SetDataSource(null);
        System.out.println("testArr2 ------------ \n" + dswArray.toString());//testArr2 --- [dswArray[0]: 0]
        if(res instanceof ArrayList){
            System.out.println("size="+((ArrayList)res).size());//0
        }else
            fail("type mismatch");
        res=dswArray.SetDataSource(new ArrayList());
        System.out.println(dswArray.toString());//testArr2 --- [dswArray[0]: 0]
        if(res instanceof ArrayList){
            System.out.println("size="+((ArrayList)res).size());//0
        }else
            fail("type mismatch");
        System.out.println(dswArray.EndOfSource());//true
        System.out.println(0 == dswArray.xCheckLevel(0));//true
        try{
            dswArray.xCheckLevel(1);//.xCheckLevel: level is out of range
        }catch(Error e){
            System.out.println(e.toString());
        }
        try{
            dswArray.xReadLevel(0);//dswArray: cannot xRead from the end of source
        }catch(Error e){
            System.out.println(e.toString());
        }
        try{
            dswArray.xRead();//dswArray: cannot xRead from the end of source
        }catch(Error e){
            System.out.println(e.toString());
        }
        System.out.println("testEmptyArr --------------\n");
    }
    
    @Test//non-empty array
    public void testArray(){
        System.out.println("testArray -------------");
        ArrayList<String> testArr=new ArrayList<>();
        testArr.add("aa");testArr.add("bb");
        xData testxd = new xData(testArr);
        System.out.println(dswArray=new dswArray(testxd));//[dswArray[2]: 0]
        System.out.println(dswArray.type);//dswArray
        System.out.println(dswArray.maxLevel);//0
        System.out.println(dswArray.dataSource);//aa,bb
        System.out.println(dswArray.dataSource.size());//2
        System.out.println(dswArray.count);//2
        System.out.println(dswArray.currIndex);//0
        System.out.println(dswArray.EndOfSource());//false
        System.out.println(dswArray.xRead().toString());//[ aa; xLevel: 0; xIndexOnLevel: 0; ]
        System.out.println(dswArray.toString());//[dswArray[2]: 1]
        System.out.println(dswArray.maxLevel);//0
        System.out.println(dswArray.currIndex);//1
         System.out.println(dswArray.xRead().toString());//[ bb; xLevel: 0; xIndexOnLevel: 1; ]
        System.out.println(dswArray.toString());//[dswArray[2]: 2]
        System.out.println(dswArray.maxLevel);//0
        System.out.println(dswArray.currIndex);//1
        try{
            System.out.println(dswArray.xRead().toString());//dswArray: cannot xRead from the end of source
        }catch(Error e){
            System.out.println(e.toString());
        }
        System.out.println("testArray -----------------------\n");
    }
}
