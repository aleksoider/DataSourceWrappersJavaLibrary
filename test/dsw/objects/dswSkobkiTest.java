/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsw.objects;

import dsw.abstracts.dswTextFile;
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
public class dswSkobkiTest {
    
    String target;
    private dswMultilevel testConv;
    private dswTextFileLines skobki;
    
    private boolean simpleCompare(String input) {
        return (target.toLowerCase().startsWith(input));
    }

    private boolean concatCompare(String input) {
        String[] basis = input.split(",");
        if (basis.length == 3) {
            if (basis[0].length() >= new Integer(basis[2])) {
                String base = basis[0].substring(0, basis[0].length() - new Integer(basis[2])) + basis[1];
                if ((target.toLowerCase().startsWith(base))) {
                    return true;
                } else {
                    return simpleCompare(basis[0]);
                }
            } else {
                if ((target.toLowerCase().startsWith(basis[1]))) {
                    return true;
                } else {
                    return simpleCompare(basis[0]);
                }
            }
        } else if (basis.length == 2) {
            if (target.toLowerCase().startsWith(basis[0] + basis[1])) {
                return true;
            } else {
                return simpleCompare(basis[0]);
            }
        } else {
            return simpleCompare(basis[0]);
        }
    }
    
    private ArrayList<xData> search(){
        ArrayList<xData>res=new ArrayList<>();
        while(!skobki.EndOfSource()){
            xData buf=skobki.xRead();
            dswSeparatedString input=new dswSeparatedString(buf,";");
            if(concatCompare((String)input.xRead().getData()))
            res.add(buf);
        }
        for(int i=0;i<res.size();i++){
            System.out.println(res.get(i).toString());
        }
        return res;
    }
    
    @Test
    public void start() {
        this.target = "вершины";
        skobki = new dswTextFileLines("VL.txt");
        skobki.Open();
        search();
        skobki.Close();
    }
}
