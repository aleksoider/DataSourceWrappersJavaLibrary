/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsw.objects.binaryFile;

import dsw.abstracts.dswDirectAccessDataSource;
import dsw.abstracts.dswTextFile;
import extendedData.xData;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author Alexey Savenkov OIS 2016
 */
public class dswBinaryFile extends dswTextFile implements dswDirectAccessDataSource {

    /**
     * block length
     */
    protected int blockLength;

    /**
     * current position in file
     */
    protected long currentPos;

    /**
     * current block number
     */
    protected int currentBlock;

    /**
     * max quantity of blocks in binary files
     */
    protected int blockCount;

    /**
     *
     */
    protected int maxlevel;

    /**
     *
     * @param data
     * @param IOmode
     * @param _blockLength
     * @param _create
     * @param _overwrite
     */
    public dswBinaryFile(Object data, int IOmode, int _blockLength, boolean _create, boolean _overwrite) {
        this.setIOMode(IOmode);
        this.blockLength = _blockLength;
        this.opened = false;
        this.create = _create;
        this.overwrite = _overwrite;
        if (data != null) {
            this.SetDataSourceAccessor(data);
        }
        this.type = "dswBinaryFile";
        this.maxlevel = 0;
    }

    /**
     *
     * @param data
     * @param IOmode
     * @param _blockLength
     */
    public dswBinaryFile(Object data, int IOmode, int _blockLength) {
        this.setIOMode(IOmode);
        this.blockLength = _blockLength;
        this.opened = false;
        this.create = false;
        this.overwrite = false;
        if (data != null) {
            this.SetDataSourceAccessor(data);
        }
        this.type = "dswBinaryFile";
        this.maxlevel = 0;
    }

    /**
     *
     * @param data
     * @param IOmode
     */
    public dswBinaryFile(Object data, int IOmode) {
        this.setIOMode(IOmode);
        this.opened = false;
        this.create = false;
        this.overwrite = false;
        if (data != null) {
            this.SetDataSourceAccessor(data);
        }
        this.type = "dswBinaryFile";
        this.maxlevel = 0;
    }

    /**
     * gets xData Object
     * @param data
     */
    public dswBinaryFile(Object data) {
        this.opened = false;
        this.create = false;
        this.overwrite = false;
        if (data != null) {
            this.SetDataSourceAccessor(data);
        } else {
            this.setIOMode(null);
        }
        this.type = "dswBinaryFile";
        this.maxlevel = 0;
    }

    private int xCheckLevel(int level) {
        if (level > this.maxlevel || level < 0) {
            throw new Error(".xCheckLevel: level is out of range");
        }
        return level;
    }

    @Override
    public int maxLevel() {
        return this.maxlevel;
    }

    /**
     *
     * @param level
     * @return
     */
    protected String xReadLevel(int level) {
        xCheckLevel(level);
        if (!this.opened) {
            throw new Error("dswTextFileLines.xReadLevel" + ": stream is closed");
        }
        if (this.EndOfSource()) {
            throw new Error("dswTextFileLines: cannot xRead from the end of source");
        }
        try {
            if (this.dataSource instanceof RandomAccessFile) {
                byte[] b = new byte[this.blockLength];                     //блок данных побитово читается из файла
                for(int i=0;i<this.blockLength;i++){
                    b[i]=((RandomAccessFile) this.dataSource).readByte();
                }
                this.currentBlock += 1;
                this.currentPos += this.blockLength;
                String UTF8 = new String(new String(b, "Cp1251").getBytes("UTF-8"), "UTF-8");//перевод битового массива в строку               
                return UTF8;                                                    //(в случае запуска на системе отличной от Win7 заменить кодировку Cp1251 на кодировку системы)
            } else {
                throw new Error("dswTextFileChars.xReadLevel : dataSource is not bufferedReader");
            }
        } catch (IOException e) {
            throw new Error("dswTextFileChars.xReadLevel : " + e.getMessage());
        }
    }

    @Override
    public xData xRead() {
        try {
            int xLevel = 0;
            xData res = new xData(this.xReadLevel(0));
            res.add("xLevel", xLevel);
            res.add("xIndexOnLevel", this.currentBlock);
            return res;
        } catch (Exception e) {
            throw new Error("dswTextFileChars.xReadLevel : " + e.getMessage());
        }
    }

    @Override
    public Object SetDataSource(Object data) { //принимает объект xData вида [String filePath, "blockLength", int blockLength]
        Object res = super.SetDataSource(data);// вызов конструктора dswTextFile
        if (data instanceof xData) {
            if (((xData) data).hasOwnProperty("blockLength")) {
                this.blockLength = (Integer) ((xData) data).get("blockLength");
            } else {
                this.blockLength = 1;
            }
        }
        return res;
    }

    @Override
    public final Object SetDataSourceAccessor(Object data) {
        return this.SetDataSource(data);
    }

    @Override
    public Object GetDataSourceAccessor() {
        return this.GetDataSource();
    }

    /**
     *
     * @return
     */
    @Override
    public Object getCurrPos() {
        return this.currentPos;
    }

    /**
     *
     * @return
     */
    public Object getCurrBlock() {
        return this.currentBlock;
    }
    
    @Override
    public void MoveAt(int pos) {
        if (this.opened) {
            if (this.dataSource instanceof RandomAccessFile) {
                try {
                    if (pos >= 0 && pos < this.blockCount) {
                        this.currentPos = pos * this.blockLength;
                        ((RandomAccessFile) this.dataSource).seek(this.currentPos);
                    } else {
                        throw new Error("<dswBinaryFile>.MoveAt: block number is out of range");
                    }
                } catch (IOException ex) {
                    throw new Error("<dswBinaryFile>.MoveAt: " + ex.getMessage());
                }
            } else {
                throw new Error("<dswBinaryFile>.MoveAt:  file reader type error");
            }
        } else {
            throw new Error("<dswBinaryFile>.MoveAt:  file is closed");
        }
    }

    @Override
    public void MoveFirst() {
        if (this.opened) {
            if (this.dataSource instanceof RandomAccessFile) {
                try {
                    this.currentPos = 0;
                    ((RandomAccessFile) this.dataSource).seek(0);
                } catch (IOException ex) {
                    throw new Error("<dswBinaryFile>.MoveAt: " + ex.getMessage());
                }
            } else {
                throw new Error("<dswBinaryFile>.MoveAt:  file reader type error");
            }
        } else {
            throw new Error("<dswBinaryFile>.MoveAt:  file is closed");
        }
    }

    @Override
    public void MoveLast() {
        if (this.opened) {
            if (this.dataSource instanceof RandomAccessFile) {
                try {
                    this.currentPos = this.blockCount * this.blockLength - this.blockLength;
                    ((RandomAccessFile) this.dataSource).seek(this.currentPos);
                } catch (IOException ex) {
                    throw new Error("<dswBinaryFile>.MoveAt: " + ex.getMessage());
                }
            } else {
                throw new Error("<dswBinaryFile>.MoveAt:  file reader type error");
            }
        } else {
            throw new Error("<dswBinaryFile>.MoveAt:  file is closed");
        }
    }

    @Override
    public void Close() {
        if (this.opened) {
            if (this.dataSource instanceof RandomAccessFile) {
                try {
                    ((RandomAccessFile) this.dataSource).close();
                } catch (IOException ex) {
                    throw new Error("<dswExternalDataSource>.Close: " + ex.getMessage());
                }
            } else {
                super.Close();
            }
            this.opened = false;
        } else {
            //System.out.println("<dswExternalDataSource>.Close: file is already closed");
        }
    }

    @Override
    public void Open() {
        if (!this.opened) {
            try {
                RandomAccessFile file = new RandomAccessFile(this.path, "r");
                this.dataSource = file;
                this.opened = true;
                this.blockCount = (int) (file.length() / this.blockLength);
            } catch (FileNotFoundException ex) {
                throw new Error("<dswTextFileSepLinesWithSamePrefix>.Open error occurred trying to open " + this.path + " " + ex.getMessage());
            } catch (IOException ex) {
                throw new Error("<dswTextFileSepLinesWithSamePrefix>.Open error occurred trying to open " + this.path + " " + ex.getMessage());
            }
        } else {
            throw new Error("<dswTextFileSepLinesWithSamePrefix>.Open: file is already opened");
        }

    }

    @Override
    public Boolean EndOfSource() {
        long n;
        try {
            n = ((RandomAccessFile) this.dataSource).getFilePointer();
            int s = ((RandomAccessFile) this.dataSource).read();
            ((RandomAccessFile) this.dataSource).seek(n);
            return (s == -1);
        } catch (IOException ex) {
            throw new Error("<dswTextFileSepLinesWithSamePrefix>.EndOfSource : " + ex.getMessage());
        }
    }
    
    @Override
    public String toString(){
        String s = "[" + this.type + "<" + this.iomode + "><" + this.blockLength + ">";
	if (this.opened)
		s += "[ "+this.currentBlock+" / "+this.blockCount+" ]";
	return s + ": " + this.path + "]";
    }
}
