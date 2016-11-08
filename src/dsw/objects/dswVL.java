/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsw.objects;

import dsw.abstracts.dswCommon;
import dsw.abstracts.dswTextFile;
import dsw.objects.textFile.dswTextFileLines;
import dsw.objects.textFile.dswTextFileSepLinesWithSamePrefix;
import extendedData.xData;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alexey Savenkov OIS 2016
 */
public class dswVL implements dswCommon{
    
    String target;
    private dswTextFileLines VL;
    private int maxlevel=0;
    private String path = null;
    private boolean endOfSourceFlag = false;
    
    public dswVL(Object data){
        this.SetDataSource(data);
    }

    public dswVL(String _path, String _targetWord) {
        this.path=_path;
        this.VL = new dswTextFileLines(_path);
        this.VL.Open();
        this.target = _targetWord;
    }

    private boolean simpleCompare(String input) {
        return (target.toLowerCase().startsWith(input));
    }

    private boolean concatCompare(String input) {
        String[] basis = input.split(",");
        if (basis.length == 3) {//ясен,ный,2;ЛН=19838....
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
        } else if (basis.length == 2) {//ячейк,а;ЛН=43606....
            if (target.toLowerCase().startsWith(basis[0] + basis[1])) {
                return true;
            } else {
                return simpleCompare(basis[0]);
            }
        } else {//ящик;ЛН=43607...
            return simpleCompare(basis[0]);
        }
    }

    @Override
    public int maxLevel() {
        return this.maxlevel;
    }

    private xData xReadLevel() {//последловательно выдает все омонимы до тех пор пока они есть
        xData res;              //когда слово не найдено вернет null
        while (!VL.EndOfSource()) {
            res = VL.xRead();
            dswSeparatedString input = new dswSeparatedString(res, ";");
            String buf = ((String) input.xRead().getData());
            if (buf.startsWith("*"))
                buf = buf.substring(1);
            if (target.toLowerCase().substring(0, 1).compareTo(buf.toLowerCase().substring(0, 1)) > 0) {
                this.endOfSourceFlag = true;
                return null;
            }
            if (concatCompare(buf.toLowerCase())) {
                return res;
            }
        }
        return null;
    }

    @Override
    public xData xRead() {
        return xReadLevel();    
    }

    @Override
    public Boolean EndOfSource() {
        if(VL!=null)
            return this.VL.EndOfSource() || this.endOfSourceFlag;
        else 
            return true;
    }

    @Override
    public Object SetDataSource(Object data) {
        endOfSourceFlag = false;
        if(data!=null){
            if (data instanceof xData) {
                xData xd = (xData) data;
                if (xd.getData() instanceof String) {
                    this.target = (String) xd.getData();
                } else {
                    throw new Error("type mismatch");
                }
                if (xd.hasOwnProperty("path")) {
                    this.VL = new dswTextFileLines(xd.get("path"));
                } else {
                    this.VL = new dswTextFileLines("VL.txt");
                }
            } else {
                throw new Error("type mismatch");
            }
            this.VL.Open();
            return VL.GetDataSource();
        }else{
            this.VL=null;
            return null;
        } 
    }

    @Override
    public Object GetDataSource() {
        return this.VL.GetDataSource();
    }

    @Override
    public Object SetDataSourceAccessor(Object data) {
        return this.SetDataSource(data);
    }

    @Override
    public Object GetDataSourceAccessor() {
        return this.GetDataSource();
    }
    
    public void Close(){
        this.VL.Close();
    }
    
    public void Sort() {
        this.VL.Close();
        this.VL.Open();
        LineNumberReader lr = (LineNumberReader) this.VL.GetDataSource();
        ArrayList<String> buf = new ArrayList<>();
        while (!this.EndOfSource()) {
            try {
                buf.add(lr.readLine());
            } catch (IOException ex) {
                Logger.getLogger(dswTextFileSepLinesWithSamePrefix.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.Close();
        Collections.sort(buf, new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                String[] split = a.toLowerCase().split(";");
                String[] split1 = b.toLowerCase().split(";");
                if(split[0].startsWith("*"))
                    return split[0].substring(1).compareTo(split1[0]);
                if(split1[0].startsWith("*"))
                    return split[0].compareTo(split1[0].substring(1));               
                return split[0].compareTo(split1[0]);
            }
        }.reversed());
        try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path),"UTF-8"))) {
            for (String buf1 : buf) {
                out.write(buf1);
                out.newLine();
            }
        } catch (IOException ex) {
            Logger.getLogger(dswTextFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.VL.Open();
    }
    
}
