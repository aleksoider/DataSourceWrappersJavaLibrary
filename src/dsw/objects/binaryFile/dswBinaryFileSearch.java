/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsw.objects.binaryFile;

import dsw.abstracts.dswCommonStringSeparator;
import extendedData.xData;

/**
 *
 * @author Alexey Savenkov OIS 2016
 */
public class dswBinaryFileSearch extends dswBinaryFile implements dswCommonStringSeparator{
    
    protected String goalString;
    protected String separator;
    protected Boolean returned = false;
    
    public dswBinaryFileSearch(Object data, int IOmode, int _blockLength, String _goalString, String _separator, boolean _create, boolean _overwrite) {
        super(data, IOmode, _blockLength, _create, _overwrite);
        this.goalString=_goalString;
        this.separator=_separator;
    }
    
    public dswBinaryFileSearch(Object data, int IOmode, int _blockLength, String _goalString, String _separator) {
        super(data, IOmode, _blockLength);
        this.goalString=_goalString;
        this.separator=_separator;
    }
    
    public dswBinaryFileSearch(Object data, int IOmode) {
        super(data, IOmode);
    }
    
    public dswBinaryFileSearch(Object data) {
        super(data);
    }
    
    @Override
    public Object SetDataSource(Object data) {
        returned = false;
        if (data!=null){
            if (data instanceof xData) {
                xData xdata = (xData) data;
                Object get = xdata.getData();
                if (get instanceof String) {
                    this.goalString = (String) get;
                }
                if (xdata.hasOwnProperty("path")) {
                    this.path = (String) ((xData) data).get("path");
                } else {
                    this.goalString=null;
                }
                if (xdata.hasOwnProperty("separator")) {
                    this.separator = (String) ((xData) data).get("separator");
                } else {
                    this.separator=null;
                }
                if (xdata.hasOwnProperty("blockLength")) {
                    this.blockLength = (Integer) ((xData) data).get("blockLength");
                } else {
                    this.blockLength=1;
                }
                this.Close();
                this.Open();
            }
            if(data instanceof String){
                this.path=(String)data;
                this.goalString=null;
                this.separator=null;
                this.Close();
                this.Open();
            }
        }else{
            this.path = null;
            this.goalString=null;
            this.separator=null;
        }
        return this.path;
    }
    
    @Override
    public xData xRead() {
        if (this.path != null) {
            int middle;
            int LB = 0;
            int UB = this.blockCount;
            String res = null;
            this.MoveFirst();
            res = this.xReadLevel(maxlevel);
            String cut = res.split(this.separator)[0].substring(0);
            //  String cut = res.split("\\s+")[0].substring(0);
            int c = goalString.compareTo(cut);
            if (c < 0) {
                res = null;
            } else {
                this.MoveLast();
                res = this.xReadLevel(maxlevel);
                cut = res.split(this.separator)[0];
                c = goalString.compareTo(cut);
                if (c > 0) {
                    res = null;
                } else {
                    while (LB < UB) {
                        middle = (LB + UB) / 2;
                        this.currentBlock = middle;
                        this.MoveAt(this.currentBlock);
                        res = this.xReadLevel(maxlevel);
                        cut = res.split(this.separator)[0];
                        c = goalString.compareTo(cut);
                        if (c == 0) {
                            break;
                        } else if (c < 0) {
                            UB = middle;
                        } else {
                            LB = middle + 1;
                        }
                    }
                }
            }
            try {
                int xLevel = 0;
                xData result = new xData(res);
                result.add("xLevel", xLevel);
                result.add("xIndexOnLevel", this.currentBlock);
                returned = true;
                return result;
            } catch (Exception e) {
                throw new Error("dswTextFileChars.xReadLevel : " + e.getMessage());
            }
        }else{
            returned = true;
            return null;
        }
    }

    @Override
    public void SetSourceSeparator(String any) {
        this.separator=any;
    }

    @Override
    public String GetSourceSeparator() {
        return this.separator;
    }
    
    @Override
    public Boolean EndOfSource() {
        return this.returned;
    }
}
