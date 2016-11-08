/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsw.abstracts;


/**
 *
 * @author Alexey Savenkov OIS 2016
 */
public interface dswDirectAccessDataSource extends dswExternalDataSource {

    /**
     * return current position in file
     * @return int value
     */
    public Object getCurrPos();
    /**
     * move to specified position in file
     * @param pos in position number
     */
    public void MoveAt(int pos);

    /**
     * move to first position in file
     */
    public void MoveFirst();

    /**
     * move to last position in file
     */
    public void MoveLast();
}
