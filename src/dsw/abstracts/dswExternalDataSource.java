/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsw.abstracts;

import java.io.File;

/**
 * dswObject for external data sources
 *
 * @author Alexey Savenkov OIS 2016
 */
public interface dswExternalDataSource extends dswCommon {

    /**
     * open data source text file
     */
    public void Open();

    /**
     * create and open data source
     */
    public void Create();

    /**
     * close data source
     */
    public void Close();

    /**
     * check if data source stream is opened
     *
     * @return bool
     */
    public boolean IsOpened();

    /**
     * check if data source exists
     *
     * @return bool
     */
    public boolean Exists();

    /**
     * move data source to specified destination
     *
     * @param dest destination
     */
    public void Move(String dest);

    /**
     * copy data source to specified destination
     *
     * @param dest destination
     */
    public void Copy(String dest);

    /**
     * delete data source
     */
    public void Delete();

}
