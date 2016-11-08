/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsw.objects;

import extendedData.xData;
import dsw.abstracts.dswCommon;
import java.util.ArrayList;
import java.util.stream.IntStream;

/**
 * dswObject implementing array of different dswObjects
 * @author Alexey Savenkov OIS 2016
 */
public class dswMultilevel implements dswCommon {

    private String type;
    private int currIndex;
    private int count;
    private int isLastParsing = -1;
    private int maxLevel;
    private ArrayList<xData> dataSource;
    private int[] indexesToPrint=null;

    /**
     * default constructor
     * @param data ArrayList of xData
     */
    public dswMultilevel(Object data) {
        this.SetDataSourceAccessor(data);
        this.type = "dswMultilevel";
    }

    @Override
    public xData xRead() {
        if (this.EndOfSource()) {
            throw new Error("dswMultilevel: cannot xRead from the end of source");
        }
        xData currXWrap = this.dataSource.get(this.currIndex);
        dswCommon currWObj = (dswCommon) currXWrap.getData();
        if (currWObj.EndOfSource()) {
            this.currIndex--;
            return this.xRead();
        }
        xData resLev = currWObj.xRead();
        int currIndexSave = this.currIndex;
        if (this.currIndex < (this.maxLevel - 1) && (int) resLev.get("xLevel") == currWObj.maxLevel()) {
            Object toNext = (xData) currXWrap.get("maskForNext");
            if (toNext != null) {
                ((xData) toNext).setData(resLev.getData());
            } else {
                toNext = resLev.getData();
            }
            ((xData) toNext).add("xIndexOnLevel", resLev.get("xIndexOnLevel"));
            ((xData) toNext).add("xLevel", (int) resLev.get("xLevel") + currIndexSave);
            this.currIndex += 1;
            ((dswCommon) this.dataSource.get(currIndex).getData()).SetDataSource(toNext);
        }
        if (this.isLastParsing != -1 && ((dswCommon) this.dataSource.get(this.isLastParsing).getData()).EndOfSource()) {
            this.isLastParsing = (this.isLastParsing == (this.dataSource.size() - 1)) ? 0 : this.currIndex;
        }
        if (this.currIndex == 1 && ((dswCommon) this.dataSource.get(0).getData()).EndOfSource() && this.dataSource.size() > 1) {
            this.isLastParsing = 1;
        }
        if (resLev != null) {
            resLev.add("xLevel", (int) resLev.get("xLevel") + currIndexSave);
        }
        if (this.indexesToPrint != null) {
            if (isInArray(currIndexSave)) {
                return resLev;
            } else {
                if (!this.EndOfSource()) {
                    return this.xRead();
                } else {
                    return null;
                }
            }
        } else {
            return resLev;
        }
    }
    
    private boolean isInArray(int n){
        return IntStream.of(this.indexesToPrint).anyMatch(x -> x == n);
    }
    
    @Override
    public Boolean EndOfSource() {       
        if (this.isLastParsing != -1 && ((dswCommon) this.dataSource.get(this.isLastParsing).getData()).EndOfSource()) {
            this.isLastParsing
                    = (this.isLastParsing == (this.dataSource.size() - 1)) ? 0 : this.currIndex;
        }
        return (this.dataSource.isEmpty() || ((dswCommon) this.dataSource.get(0).getData()).EndOfSource() && this.isLastParsing == 0);
    }

    @Override
    public Object SetDataSource(Object data) {
        return ((dswCommon) this.dataSource.get(0).getData()).SetDataSource(data);
    }

    @Override
    public Object GetDataSource() {
        return this.dataSource;
    }

    @Override
    public final Object SetDataSourceAccessor(Object data) {
        xData xdata = null;
        Object xdatalist = null;
        this.indexesToPrint = null;
        if (data != null) {
            if (data instanceof xData) {
                xdata = (xData) data;
                if(xdata.hasOwnProperty("indexesToReturn")){
                    Object buf = xdata.get("indexesToReturn");
                    if(buf instanceof int[])
                        this.indexesToPrint = (int[]) buf;
                }
            } else {
                xdatalist = data;
            }
        } else {
           if (data == null) {
               throw new Error("dswMultilevel.SetDataSource : data is null");
            }
            xdatalist = new ArrayList();
        }
        if (xdatalist!=null && !(xdatalist instanceof ArrayList)) {
            throw new Error("dswMultilevel.SetDataSource : data type mismatch");
        }
        if (xdata != null) {
            this.dataSource = (ArrayList<xData>) xdata.getData();
        } else {
            this.dataSource = (ArrayList<xData>) xdatalist;
        }
        this.count = this.dataSource.size();
        this.currIndex = 0;
        this.isLastParsing = 0;
        this.maxLevel = this.dataSource.size();
        return this.dataSource;
    }

    @Override
    public Object GetDataSourceAccessor() {
        return this.GetDataSource();
    }

    /**
     * set dataSource for specified level
     * @param level
     * @param data
     * @return
     */
    public Object setDataSourceOnLevel(int level, Object data) {
        this.xCheckLevel(level);
        return ((dswCommon) this.dataSource.get(level).getData()).SetDataSource(data);
    }

    /**
     * get dataSource for specified level
     * @param level
     * @return
     */
    public Object GetDataSourceOnLevel(int level) {
        this.xCheckLevel(level);
        return this.dataSource.get(level).getData();
    }

    private int xCheckLevel(int level) {
        if (level > this.maxLevel || level < 0) {
            throw new Error(".xCheckLevel: level is out of range");
        }
        return level;
    }
    


    @Override
    public int maxLevel() {
        return this.maxLevel;
    }

    @Override
    public String toString() {
        String pn = "[" + this.type + "[" + this.dataSource.size();
        pn += "]: " + this.currIndex + "]";
        return pn;
    }
}
