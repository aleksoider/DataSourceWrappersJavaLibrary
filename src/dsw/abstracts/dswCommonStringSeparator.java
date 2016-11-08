/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsw.abstracts;

/**
 * string separator interface
 *
 * @author Alexey Savenkov OIS 2016
 */
public interface dswCommonStringSeparator {

    /**
     * set specified separator for dswObject
     *
     * @param any
     */
    public void SetSourceSeparator(String any);

    /**
     * get specified separator of dswObject
     *
     * @return
     */
    public String GetSourceSeparator();
}
