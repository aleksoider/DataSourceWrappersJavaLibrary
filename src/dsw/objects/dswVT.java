/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsw.objects;

import dsw.abstracts.dswCommon;
import dsw.objects.textFile.dswTextFileSepLinesWithSamePrefix;
import extendedData.xData;

/**
 *
 * @author Alexey Savenkov OIS 2016
 */
public final class dswVT implements dswCommon{

    private dswTextFileSepLinesWithSamePrefix dataSource;
    private final int maxlevel = 0;
    private final String path;
    
    public dswVT(String _tfNumber, String _path) {
        this.path = _path;
        String[] split = _tfNumber.split("=");
        if (split.length == 2 && !split[0].equals("N")) {
            this.dataSource = new dswTextFileSepLinesWithSamePrefix("N=" + split[1], this.path, ";", 0);
        } else if (split.length < 3) {
            this.dataSource = new dswTextFileSepLinesWithSamePrefix("N=" + _tfNumber, this.path, ";", 0);
        } else {
            this.dataSource = new dswTextFileSepLinesWithSamePrefix(_tfNumber, this.path, ";", 0);
        }
        this.dataSource.Open();
    }

    @Override
    public int maxLevel() {
        return this.maxlevel;
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
        this.dataSource.Close();
        this.dataSource.SetDataSource(data);
        this.dataSource.Open();
        return this.dataSource.GetDataSource();
    }

    @Override
    public Object GetDataSource() {
        return this.dataSource.GetDataSource();
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
