/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsw.objects.textFile;

import dsw.abstracts.dswCommonStringSeparator;
import dsw.abstracts.dswTextFile;
import java.util.ArrayList;
import java.util.Map;
import extendedData.xData;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alexey Savenkov OIS 2016
 */
public class dswTextFileSepLinesWithSamePrefix extends dswTextFile implements dswCommonStringSeparator{
    
    protected boolean ignoreLastInLine;
    protected int SegmentNumber=-1;
    private String goalString;
    protected String separator;
    private int indexOnLevel;
    protected int maxlevel;
    private Map<String,Object> prefixes;
    
    public dswTextFileSepLinesWithSamePrefix(Object data, String _path, String sep, int segmentNumber, int IOmode, boolean _create, boolean _overwrite) {
        this.setIOMode(IOmode);
        this.SetSourceSeparator(sep);
        this.SegmentNumber = segmentNumber;
        this.opened = false;
        this.create = _create;
        this.overwrite = _overwrite;
        this.path = _path;
        if (data != null) {
            this.SetDataSourceAccessor(data);
        }
        this.type = "dswTextFileSepLinesWithSamePrefix";
        this.maxlevel = 0;
    }
    
    public dswTextFileSepLinesWithSamePrefix(Object data, String _path, String sep, int segmentNumber) {
        this.SetSourceSeparator(sep);
        this.SegmentNumber = segmentNumber;
        this.opened = false;
        this.create = false;
        this.overwrite = false;
        this.path = _path;
        if (data != null) {
            this.SetDataSourceAccessor(data);
        } 
        this.setIOMode(null);
        this.type = "dswTextFileSepLinesWithSamePrefix";
        this.maxlevel = 0;
    }

    public dswTextFileSepLinesWithSamePrefix(Object data) {        
        this.opened = false;
        this.create = false;
        this.overwrite = false;
        if (data != null) {
            this.SetDataSourceAccessor(data);
        }else
            this.setIOMode(null);
        this.type = "dswTextFileSepLinesWithSamePrefix";
        this.maxlevel = 0;
    }
    
    @Override
    public int maxLevel() {
        return this.maxlevel;
    }

    @Override
    public xData xRead() {
        Object data = this.xReadLevel(0);
        if (data == null)
            return null;
        xData res = new xData(null);
	res.add("xLevel", 0);
	res.add("xIndexOnLevel", this.indexOnLevel++);
	res.setData(data);
	return res;
    }

    @Override
    public Object SetDataSource(Object data) {    
        if (data instanceof xData) {
            if (((xData) data).hasOwnProperty("ignoreLastInLine")) {
                this.ignoreLastInLine = (Boolean) ((xData) data).get("ignoreLastInLine");
            } else {
                this.ignoreLastInLine = false;
            }
            Object newsep = ((xData) data).get("separator");
            if (newsep != null && newsep instanceof String) {
                this.SetSourceSeparator((String) newsep);
            }
            if (((xData) data).hasOwnProperty("SegmentNumber")) {
                this.SegmentNumber = (Integer) ((xData) data).get("SegmentNumber");
            }
            if (((xData) data).hasOwnProperty("path")) {
                this.path = (String) ((xData) data).get("path");
            }
        } else {
            if (data instanceof String) {
                this.goalString = (String) data;
            } else {
                throw new Error("<dswTextFileSepLinesWithSamePrefix>.SetDataSource() type mismatch");
            }
        }
        if (this.path == null) {
            this.path = "VT.txt";
        }
        return this.path;
    }

    @Override
    public Object SetDataSourceAccessor(Object data) {
        return this.SetDataSource(data);
    }

    @Override
    public Object GetDataSourceAccessor() {
        return this.GetDataSource();
    }

    @Override
    public void SetSourceSeparator(String any) {
        if (any == null || "".equals(any)) {
            this.separator = "";
        } else {
            this.separator = any;
        }
    }

    @Override
    public String GetSourceSeparator() {
        return (this.separator != null ? this.separator : "");
    }
    
    public void setSegmentValue(String value){
        this.goalString=value;
    }
    
    public void setSegmentNumber(int num){
        this.SegmentNumber=num;
    }
    
    private int xCheckLevel(int level) {
        if (level > this.maxlevel || level < 0) {
            throw new Error(".xCheckLevel: level is out of range");
        }
        return level;
    }

    public ArrayList<String> xReadLevel(int level) {
        String msgPref = "dswTextFileSepLinesWithSamePrefix.xReadLevel";
        this.xCheckLevel(level);
        if (!this.opened) {
            throw new Error(msgPref + ": stream is closed");
        }
        if (this.EndOfSource()) {
            throw new Error("dswTextFileSepLinesWithSamePrefix: cannot xRead from the end of source");
        }
        try {
            while (!this.EndOfSource()) {
                if (this.dataSource instanceof LineNumberReader) {
                    String[] buf = ((LineNumberReader) this.dataSource).readLine().split(";");
                    if (buf[this.SegmentNumber].equals(this.goalString)) {
                        return new ArrayList<>(Arrays.asList(buf));
                    }
                }
            }
            return null;          
        } catch (Exception e) {
            throw new Error(msgPref + " : " + e.getMessage());
        }
    }
    
    @Override
    public String toString(){
        String s = "[" + this.type + "<" + this.iomode + "><" + this.separator + ">";
	return s + ": " + this.path + "]";
    }
}
