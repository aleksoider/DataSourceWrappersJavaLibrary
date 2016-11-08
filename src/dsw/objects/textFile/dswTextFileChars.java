/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsw.objects.textFile;

import extendedData.xData;
import dsw.abstracts.dswTextFile;
import java.io.IOException;
import java.io.LineNumberReader;

/**
 * dswObject implements reading specified number of chars from text file
 * @author Alexey Savenkov OIS 2016
 */
public class dswTextFileChars extends dswTextFile {

    private final String type;
    private int maxlevel;
    private int currFilePos;
    private int numChars = 1;
    private int currentPosition = 0;

    /**
     * full constructor
     * @param data xData or path String
     * @param IOmode I/O mode value
     * @param _create create file Flag
     * @param _overwrite overwrite file Flag
     */
    public dswTextFileChars(Object data, int IOmode, boolean _create, boolean _overwrite) {
        this.setIOMode(IOmode);
        this.opened = false;
        this.create = _create;
        this.overwrite = _overwrite;
        if (data != null) {
            this.SetDataSourceAccessor(data);
        }
        this.type = "dswTextFileChars";
        this.maxlevel = 0;
    }
    
    /**
     * default constructor
     * @param data xData or path String
     */
    public dswTextFileChars(Object data) {
        this.opened = false;
        this.create = false;
        this.overwrite = false;
        if (data != null) {
            this.SetDataSourceAccessor(data);
        }else
            this.setIOMode(null);
        this.type = "dswTextFileChars";
        this.maxlevel = 0;
    }
    
    /**
     * default constructor with I/O mode value
     * @param data xData or path string
     * @param IOmode I/O value
     */
    public dswTextFileChars(Object data, int IOmode) {
        this.setIOMode(IOmode);
        this.opened = false;
        this.create = false;
        this.overwrite = false;
        if (data != null) {
            this.SetDataSourceAccessor(data);
        }
        this.type = "dswTextFileChars";
        this.maxlevel = 0;
    }

    @Override
    public int maxLevel() {
        return this.maxlevel;
    }

    private int xCheckNumChars(int nch, String msgPref) {
        if (nch < 1) {
            throw new Error(msgPref + " : number of characters is out of range");
        } else {
            return nch;
        }
    }

    private int xCheckLevel(int level) {
        if (level > this.maxlevel || level < 0) {
            throw new Error(".xCheckLevel: level is out of range");
        }
        return level;
    }

    /**
     * get number of characters for reading
     * @return
     */
    public int GetNumberOfCharactersToRead() {
        return this.numChars;
    }

    /**
     * set number of characters for reading
     * @param nchars
     * @return
     */
    public int SetNumberOfCharactersToRead(int nchars) {
        this.numChars = xCheckNumChars(nchars, "dswTextFileChars.SetNumberOfCharactersToRead");
        return nchars;
    }

    private char[] xReadLevel(int level) {
        this.xCheckLevel(level);
        if (!this.opened) {
            throw new Error("dswTextFileChars.xReadLevel: stream is closed");
        }
        if (this.EndOfSource()) {
            throw new Error("dswTextFileChars: cannot xRead from the end of source");
        }
        try {
            if (this.dataSource instanceof LineNumberReader) {
                xCheckNumChars(this.numChars, "dswTextFileChars.xReadLevel");
                char[] res = new char[this.numChars];
                for(int i=0;i<this.numChars;i++){                    
                    int c=((LineNumberReader) this.dataSource).read();
                    if(c!=10){
                        res[i]=(char)c;
                        this.currentPosition ++;
                    }else{
                        i--;
                    }
                }                               
                return res;
            } else {
                throw new Error("dswTextFileChars.xReadLevel : dataSource is not bufferedReader");
            }
        } catch (IOException e) {
            throw new Error("dswTextFileChars.xReadLevel : " + e.getMessage());
        }
    }

    @Override
    public xData xRead() {
        try {
            int xLevel = 0;
            int xIndexOnLevel = this.currFilePos++;
            if (this.dataSource instanceof LineNumberReader) {
                int textFileLine = ((LineNumberReader) this.dataSource).getLineNumber();
                int textFilePos = this.currentPosition;
                xData res = new xData(this.xReadLevel(0));
                res.add("xLevel", xLevel);
                res.add("xIndexOnLevel", xIndexOnLevel);
                res.add("textFileLine", textFileLine);
                res.add("textFilePos", textFilePos);
                return res;
            } else {
                throw new Error("dswTextFileChars.xRead : dataSource is not bufferedReader");
            }
        } catch (Exception e) {
            throw new Error("dswTextFileChars.xRead : " + e.getMessage());
        }
    }

    @Override
    public final Object SetDataSourceAccessor(Object data) {
        this.currFilePos = 0;
        return this.SetDataSource(data);
    }

    @Override
    public Object GetDataSourceAccessor() {
        return this.GetDataSource();
    }

}
