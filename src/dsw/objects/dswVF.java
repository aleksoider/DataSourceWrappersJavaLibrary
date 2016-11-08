/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsw.objects;

import dsw.abstracts.dswCommon;
import dsw.objects.textFile.dswTextFileLines;
import extendedData.xData;

/**
 *
 * @author Alexey Savenkov OIS 2016
 */
public class dswVF implements dswCommon {

    dswTextFileLines dataSource;
    private int maxlevel;
    private String path;
    private String targetWord;

    public dswVF(String _path, String word) {
        this.path = _path;
        this.dataSource = new dswTextFileLines(_path);
        this.targetWord = word;
        this.dataSource.Open();
    }
    
    public dswVF(Object data){
        this.SetDataSource(data);
    }

    @Override
    public int maxLevel() {
        return this.maxlevel;
    }
    
    private xData xReadLevel(){
        while(!this.EndOfSource()){
            xData res = this.dataSource.xRead();
            dswSeparatedString input = new dswSeparatedString(res, ";");
            String buf = ((String) input.xRead().getData()).split("=")[1].toLowerCase();
            if(buf.startsWith("-"))
                buf = buf.substring(1);
            if(this.targetWord.regionMatches(true, this.targetWord.length()-buf.length(), buf, 0, buf.length()))
                return res;
        }
        return null;
    }
    
    @Override
    public xData xRead() {
        return this.xReadLevel();
    }

    @Override
    public Boolean EndOfSource() {
        return this.dataSource.EndOfSource();
    }

    @Override
    public Object SetDataSource(Object data) {
        if (data instanceof xData) {
            xData xd = (xData) data;
            if (xd.getData() instanceof String) {
                this.targetWord = (String) xd.getData();
            } else {
                throw new Error("type mismatch");
            }
            if (xd.hasOwnProperty("path")) {
                this.dataSource = new dswTextFileLines(xd.get("path"));
            } else {
                this.dataSource = new dswTextFileLines("VF.txt");
            }
        } else {
            throw new Error("type mismatch");
        }
        this.dataSource.Close();
        this.dataSource.Open();
        return dataSource.GetDataSource();
    }

    @Override
    public Object GetDataSource() {
        return dataSource;
    }

    @Override
    public Object SetDataSourceAccessor(Object data) {
        return SetDataSource(data);
    }

    @Override
    public Object GetDataSourceAccessor() {
        return GetDataSource();
    }
    
}
