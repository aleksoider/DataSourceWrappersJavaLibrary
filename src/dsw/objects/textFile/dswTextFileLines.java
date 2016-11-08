/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsw.objects.textFile;

import extendedData.xData;
import dsw.abstracts.dswTextFile;
import java.io.LineNumberReader;

/**
 * dswObject implements reading specified lines from text file
 * @author Alexey Savenkov OIS 2016
 */
public class dswTextFileLines extends dswTextFile {
   
    protected int maxlevel;

    /**
     * full constructor
     * @param data xData or path String
     * @param IOmode I/O mode value
     * @param _create create file Flag
     * @param _overwrite overwrite file Flag
     */
    public dswTextFileLines(Object data, int IOmode, boolean _create, boolean _overwrite) {
        this.setIOMode(IOmode);
        this.opened = false;
        this.create = _create;
        this.overwrite = _overwrite;
        if (data != null) {
            this.SetDataSourceAccessor(data);
        }
        this.type = "dswTextFileLines";
        this.maxlevel = 0;
    }
    
    /**
     * default constructor
     * @param data xData or path String
     */
    public dswTextFileLines(Object data) {
        this.opened = false;
        this.create = false;
        this.overwrite = false;
        if (data != null) {
            this.SetDataSourceAccessor(data);
        }else
            this.setIOMode(null);
        this.type = "dswTextFileLines";
        this.maxlevel = 0;
    }
    
    /**
     * default constructor with I/O mode value
     * @param data xData or path string
     * @param IOmode I/O value
     */
    public dswTextFileLines(Object data, int IOmode) {
        this.setIOMode(IOmode);
        this.opened = false;
        this.create = false;
        this.overwrite = false;
        if (data != null) {
            this.SetDataSourceAccessor(data);
        }
        this.type = "dswTextFileLines";
        this.maxlevel = 0;
    }

    @Override
    public int maxLevel() {
        return this.maxlevel;
    }
    
    private int xCheckLevel(int level) {
        if (level > this.maxlevel || level < 0) {
            throw new Error(".xCheckLevel: level is out of range");
        }
        return level;
    }

    private String xReadLevel(int level) {
        xCheckLevel(level);
        if (!this.opened) {
            throw new Error("dswTextFileLines.xReadLevel" + ": stream is closed");
        }
        if (this.EndOfSource()) {
            throw new Error("dswTextFileLines: cannot xRead from the end of source");
        }
        try {
            if (this.dataSource instanceof LineNumberReader) {               
                return ((LineNumberReader) this.dataSource).readLine();              
            } else {
                throw new Error("dswTextFileChars.xReadLevel : dataSource is not bufferedReader");
            }
        } catch (Exception e) {
            throw new Error("dswTextFileChars.xReadLevel : " + e.getMessage());
        }
    }

    @Override
    public xData xRead() {
        try {
            int xLevel = 0;
            if (this.dataSource instanceof LineNumberReader) {               
                int xIndexOnLevel=((LineNumberReader) this.dataSource).getLineNumber();
                xData res=new xData(this.xReadLevel(0));
                res.add("xLevel", xLevel);
                res.add("xIndexOnLevel", xIndexOnLevel);
                return res;
            } else {
                throw new Error("dswTextFileChars.xReadLevel : dataSource is not bufferedReader");
            }
        } catch (Exception e) {
            throw new Error("dswTextFileChars.xReadLevel : " + e.getMessage());
        }
    }

    @Override
    public Object SetDataSourceAccessor(Object data) {
        return this.SetDataSource(data);
    }

    @Override
    public Object GetDataSourceAccessor() {
        return this.GetDataSource();
    }

}
