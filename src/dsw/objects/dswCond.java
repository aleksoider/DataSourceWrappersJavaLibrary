/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsw.objects;

import extendedData.xData;
import dsw.abstracts.dswCommon;
import java.util.ArrayList;

/**
 *
 * @author Alexey Savenkov OIS 2016
 */
public class dswCond implements dswCommon{
    private int maxlevel;
    private final String type;
    private ArrayList<xData> dataSource;
    private int count;
    private int currIndex;

    public dswCond(Object data){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        this.SetDataSourceAccessor(data);
//	this.type = "dswCond";
	//this.maxLevel = this.dataSource.length; 
    }
    
    @Override
    public int maxLevel() {
        return this.maxlevel;
    }

    @Override
    public xData xRead() {
        return ((dswCommon)this.dataSource.get(this.currIndex).getData()).xRead();
    }

    @Override
    public Boolean EndOfSource() {
        return (this.dataSource.size()==0 || ((dswCommon)this.dataSource.get(this.currIndex).getData()).EndOfSource());
    }

    @Override
    public Object SetDataSource(Object data) {
        for (int i = 0; i < this.count; i++) {
            if (xEval(this.dataSource.get(i).get("applyCondition"), data) == true) {
                this.currIndex = i;
                ((dswCommon)this.dataSource.get(i).getData()).SetDataSource(data);
                return this.currIndex;
            }
        }
        return this.currIndex;
    }

    @Override
    public Object GetDataSource() {
        return this.dataSource;
    }

    @Override
    public Object SetDataSourceAccessor(Object data) {
        Object newval;
        String msgPref = "dswCond.SetDataSourceAccessor";
        if (data != null) {
            if (data instanceof xData) {
                newval = ((xData) data).getData();
                msgPref += " xData";
            } else {
                newval = data;
            }
        } else {
            if (data == null) {
                throw new Error(msgPref + ": data is null");
            }
            newval = new ArrayList<xData>();
        }
        if (!(newval instanceof ArrayList)) {
            throw new Error(msgPref + ": newval type mismatch");
        }
        this.dataSource = (ArrayList<xData>) newval;
        for (xData dataSource1 : dataSource) {
            if (!dataSource1.hasOwnProperty("applyCondition")) {
                dataSource1.add("applyCondition", true);
            }
        }
        this.count = dataSource.size();
        this.currIndex = 0;
        return this.dataSource;
    }

    @Override
    public Object GetDataSourceAccessor() {
        return this.GetDataSource();
    }

    private boolean xEval(Object get, Object data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
