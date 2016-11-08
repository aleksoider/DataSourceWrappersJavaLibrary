/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsw.abstracts;

import extendedData.xData;

/**
 * Data Source Wrapper main inteface each dsw object has al methods from
 * dswCommon
 *
 * @author Alexey Savenkov OIS 2016
 */
public interface dswCommon {

    /**
     * return maxLevel value of dswObject
     * @return maxlevel
     */
    public int maxLevel();

    /**
     * execute dswObject
     * @return xData
     */
    public xData xRead();

    /**
     * check if data source of dswObject is EOF
     * @return bool EOF
     */
    public Boolean EndOfSource();

    /**
     * set specified data source to dswObject
     * @param data dataSource
     * @return dataSource
     */
    public Object SetDataSource(Object data);

    /**
     * get dswObject's data source
     * @return dataSource
     */
    public Object GetDataSource();

    /**
     * set specified data source to dswObject
     * @param data dataSource
     * @return dataSource
     */
    public Object SetDataSourceAccessor(Object data);

    /**
     * dswObject's data source
     * @return dataSource
     */
    public Object GetDataSourceAccessor();
}
