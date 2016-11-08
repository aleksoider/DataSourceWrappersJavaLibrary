/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsw.objects;

import extendedData.xData;
import dsw.abstracts.dswCommon;
import dsw.abstracts.dswCommonStringSeparator;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * dswObject implementing string splitted with specified separator
 * @author Alexey Savenkov OIS 2016
 */
public class dswSeparatedString implements dswCommon, dswCommonStringSeparator {

    String data;
    String separator;
    String type;
    int maxLevel;
    dswArray dataSource;
    Boolean ignoreLast;

    /**
     * default constructor
     * @param data xData or String
     */
    public dswSeparatedString(Object data) {
        this.SetDataSourceAccessor(data);
        this.type = "dswSeparatedString";
        this.maxLevel = 0;
    }

    /**
     * constructor with additional separator
     * @param data xData or String
     * @param sep String separator
     */
    public dswSeparatedString(Object data, String sep) {
        this.SetSourceSeparator(sep);
        this.SetDataSourceAccessor(data);
        this.type = "dswSeparatedString";
        this.maxLevel = 0;
    }

    @Override
    public xData xRead() {
        return this.dataSource.xRead();
    }

    @Override
    public Boolean EndOfSource() {
        return this.dataSource.EndOfSource();
    }

    @Override
    public Object SetDataSource(Object data) {
        return SetDataSourceAccessor(data);
    }

    @Override
    public Object GetDataSource() {
        return this.dataSource.GetDataSource();
    }

    @Override
    public final Object SetDataSourceAccessor(Object data) { // принимает или String, тогда нужно предварительно указать разделитель с помощью SetSourceSeparator
        xData buf = null;                                   //  или xData следующего вида [String str,"separator", String sep]
        Object newval = null;
        Object newseparator = null;
        ArrayList<String> valArr = null;
        if (data != null) {
            if (data instanceof xData) {
                xData xbuf = (xData) data;
                newval = xbuf.getData();
                newseparator = xbuf.get("separator");
            } else {
                newval = data;
            }
            if (!(newval instanceof String)) {
                throw new Error("dswSeparatedString.SetDataSource : data type mismatch " + data.toString());
            } else {
                if (!(newseparator instanceof String)) {
                    if (this.separator == null && newseparator != null) {
                        throw new Error("dswSeparatedString.SetDataSource : data type mismatch " + data.toString());
                    }
                }
            }
            if (newseparator != null && this.separator == null) {
                this.SetSourceSeparator((String) newseparator);                     
            }
            newseparator = this.GetSourceSeparator();
            this.data = (String) newval;
            valArr = new ArrayList(Arrays.asList(((String) newval).split((String) newseparator)));
            if (data instanceof xData) {
                buf = (xData) data;
                buf.setData(valArr);
            }
        } else {
//            if (data == null || ((data instanceof String) && (String) data != "")) {
//                throw new Error("dswSeparatedString.SetDataSource : data is null");
//            }
            valArr = new ArrayList();
        }
        if (buf == null) {
            this.dataSource = new dswArray(valArr);
        } else {
            this.dataSource = new dswArray(buf);
        }
        return this.dataSource;
    }

    @Override
    public Object GetDataSourceAccessor() {
        return this.GetDataSource();
    }

    /**
     * check current level
     * @param level
     * @return
     */
    public int xCheckLevel(int level) {
        return this.dataSource.xCheckLevel(level);
    }

    /**
     * read next level
     * @param level
     * @return
     */
    public Object xReadLevel(int level) {
        return this.dataSource.xReadLevel(level);
    }

    @Override
    public String GetSourceSeparator() {
        return (this.separator != null ? this.separator : "");
    }

    @Override
    public final void SetSourceSeparator(String any) {
        if (any == null || "".equals(any)) {
            this.separator = "";
        } else {
            this.separator = any;
        }
    }

    @Override
    public int maxLevel() {
        return this.maxLevel;
    }
    
    @Override
    public String toString(){
        return "[ dswSeparatedString"+this.dataSource.toString()+ " <"+this.separator+"> : "+ this.data +" ]";
    }
}
