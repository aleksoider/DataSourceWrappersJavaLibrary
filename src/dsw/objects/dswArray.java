/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsw.objects;

import extendedData.xData;
import dsw.abstracts.dswCommon;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * dswObject implementing Array 
 * @author Alexey Savenkov OIS 2016
 */
public class dswArray implements dswCommon {

    Boolean ignoreLast;
    Boolean ignoreSingle;
    int maxLevel;
    int currIndex;
    int count;
    String type;
    ArrayList<String> dataSource;

    /**
     * default constructor
     * @param data xData or ArrayList of String
     */
    public dswArray(Object data) {
        this.SetDataSourceAccessor(data);
        this.type = "dswArray";
        this.maxLevel = 0;
    }
    
    @Override
    public String toString() {
	String pn = "[" + this.type + "[" + this.dataSource.size();
	if(this.dataSource.size()>this.count) pn += "/" + this.count;
	pn += "]: " + this.currIndex + "]";
	return pn;
}
    
    @Override
    public xData xRead() {//последовательно выдает элементы массива
        int xLevel = 0;
        int xIndexOnLevel = this.currIndex;
        xData res = new xData(this.xReadLevel(0));
        res.add("xLevel", xLevel);
        res.add("xIndexOnLevel", xIndexOnLevel);
        return res;
    }

    @Override
    public Boolean EndOfSource() {
        return (this.currIndex == this.count || (this.ignoreSingle && (1 == this.dataSource.size())));
    }

    @Override
    public Object SetDataSource(Object data) {
        return this.SetDataSourceAccessor(data);
    }

    @Override
    public Object GetDataSource() {  
        return this.dataSource;
    }

    @Override
    public final Object SetDataSourceAccessor(Object data) {// принимает xData вида 
        ArrayList<String> buf = null;                   //[ArrayList<String> array, "ignoreLast",boolean,"ignoreSingle",boolean]
        Boolean ignLastP = false;                       //[String[]array, "ignoreLast",boolean,"ignoreSingle",boolean]
        Boolean ignoreSingleP = false;                  //дополнительные параметры опциональны и по умолчанию устанавливаются в false
        if (data != null) {
            if (data instanceof xData) {
                xData xdata = (xData) data;
                if(xdata.getData() instanceof ArrayList)
                    buf = (ArrayList<String>) xdata.getData();
                if (xdata.getData() instanceof String[]) {
                    buf = new ArrayList<String>(Arrays.asList((String[]) xdata.getData()));
                }
                if (xdata.hasOwnProperty("ignoreLast")) {
                    if (xdata.get("ignoreLast") instanceof Boolean) {
                        ignLastP = (Boolean) xdata.get("ignoreLast");
                    } else {
                        throw new Error("dswArray.SetDataSourceAccessor" + "ignoreLast property");
                    }
                } else {
                    ignLastP = false;
                }
                if (xdata.hasOwnProperty("ignoreSingle")) {
                    if (xdata.get("ignoreSingle") instanceof Boolean) {
                        ignoreSingleP = (Boolean) xdata.get("ignoreSingle");
                    } else {
                        throw new Error("dswArray.SetDataSourceAccessor" + "ignoreSingle property");
                    }
                } else {
                    ignoreSingleP = false;
                }
            } else if (data instanceof ArrayList) {
                buf = (ArrayList) data;
            }else if (data instanceof String[]) {
                buf = new ArrayList<String>(Arrays.asList((String[]) data));
            }
        } else {           
            buf = new ArrayList<>();
        }
        if ( !(buf instanceof ArrayList) ){
		throw new Error("dswArray.SetDataSourceAccessor : data type mismatch "+data);
	}
        this.ignoreSingle = ignoreSingleP;
        this.ignoreLast = ignLastP;
        this.count = buf.size();
        if (ignoreLast) {
            this.count -= 1;
        }
        this.dataSource = buf;
        this.currIndex = 0;
        return this.dataSource;
    }

    @Override
    public Object GetDataSourceAccessor() {
        return this.GetDataSource();
    }

    /**
     * read next level
     * @param level
     * @return
     */
    public Object xReadLevel(int level) {
        this.xCheckLevel(level);
        if (!this.EndOfSource()) {
            String res = this.dataSource.get(this.currIndex);
            this.currIndex++;
            return res;
        }else
            throw new Error("dswArray: cannot xRead from the end of source");
    }

    /**
     * check current level
     * @param level
     * @return
     */
    public int xCheckLevel(int level) {
        if (level > this.maxLevel || level < 0) {
            throw new Error(".xCheckLevel: level is out of range");
        }
        return level;
    }

    @Override
    public int maxLevel() {
        return this.maxLevel;
    }

}
