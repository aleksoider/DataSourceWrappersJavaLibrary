/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsw.abstracts;

import extendedData.xData;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 *
 * @author Alexey Savenkov OIS 2016
 */
public abstract class dswTextFile implements dswExternalDataSource {

    protected boolean create;
    protected boolean overwrite;
    protected boolean opened;
    protected String path;
    protected Object dataSource;
    protected int iomode;
    public String type;
    
    @Override
    public Object SetDataSource(Object data) {// принимает xData вида [String filepath, "iomode", int iomode]
        Object newpath = null;      // остальные параметры опциональны, если iomode == null то по умолчанию установится IOmode=1
        Object newmode = null;
        if (data != null) {
            if (data instanceof xData) {
                xData xdata = (xData) data;
                newpath = xdata.getData();
                newmode = xdata.get("iomode");
            } else {                                
                newpath = data;
            }
            if(!(newpath instanceof String))
                throw new Error("<dswExternalDataSource>.SetDataSource data type mismatch" + data);
            this.setIOMode(newmode);
            if (data instanceof xData) {
                if (((xData) data).hasOwnProperty("create")) { // свойство на создание файла, не используется в непосредственной работе оболочек
                    if (((xData) data).get("create") instanceof Boolean) {// по умолчанию устанавливается на false
                        this.create = (Boolean) ((xData) data).get("create");
                    } else {
                        throw new Error("<dswExternalDataSource>.SetDataSource create property type mismatch");
                    }
                } else {
                    this.create = false;
                }
                if (((xData) data).hasOwnProperty("overwrite")) {// свойство на перезапись файла, не используется в непосредственной работе оболочек
                    if (((xData) data).get("overwrite") instanceof Boolean) {// по умолчанию устанавливается на false
                        this.overwrite = (Boolean) ((xData) data).get("overwrite");
                    } else {
                        throw new Error("<dswExternalDataSource>.SetDataSource overwrite property type mismatch");
                    }
                } else {
                    this.overwrite = false;
                }
            }
        } else {
            throw new Error("<dswExternalDataSource>.SetDataSource path==null");
        }
        this.path = (String)newpath;    
        this.Close();
        this.dataSource = null;
        return this.path;
    }
    
    @Override
    public Object GetDataSource() {
        return this.dataSource;
    }
    
    /**
     * set I/O mode for dswObject
     *
     * @param mode iomode
     * @return iomode value
     */
    public Object setIOMode(Object mode) {  // установка режима работы оболочки: чтение/запись/добавление
        int newmode = -1;                   // на данным момент реализовано только чтение поэтому рекомендуется устанавливать 
        if (mode == null) {                 // IOmode = 1
            newmode = 1;
            mode = 1;
        } else {
            if (mode instanceof Integer) {
                if ((int) mode != 1 && (int) mode != 2 && (int) mode != 8) {
                    throw new Error("<dswTextFile>.SetIOMode : bad I/O value: " + mode);
                } else {
                    newmode = (int) mode;
                }
            } else {
                if (mode instanceof String) {
                    switch (((String) mode).toLowerCase()) {
                        case "r":
                            newmode = 1;
                        case "forreading":
                            newmode = 1;
                        case "1":
                            newmode = 1;
                        case "w":
                            newmode = 2;
                        case "forwriting":
                            newmode = 2;
                        case "2":
                            newmode = 2;
                        case "a":
                            newmode = 8;
                        case "forappending":
                            newmode = 8;
                        case "3":
                            newmode = 8;
                        default:
                            throw new Error("<dswTextFile>.SetIOMode : bad I/O value: " + mode);
                    }
                } else {
                    throw new Error("<dswTextFile>.SetIOMode type mismatch " + mode);
                }
            }
        }
        this.iomode = newmode;
        return mode;
    }

    /**
     * get I/O mode value
     * @return
     */
    public int GetIOMode() {
        return this.iomode;
    }

    /**
     * check if data source is opened for reading
     * @return
     */
    public Boolean IsForReading() {
        return (this.iomode == 1);
    }

    /**
     * check if data source is opened for writing
     * @return
     */
    public Boolean IsForWriting() {
        return (this.iomode == 2);
    }

    /**
     * check if data source is opened for appending
     * @return
     */
    public Boolean IsForAppending() {
        return (this.iomode == 8);
    }

    
    @Override
    public void Open() {
        if (!this.opened) {
            try {
                if (this.iomode == 1) { //LineNumberReader == BufferedReader
                    LineNumberReader reader = new LineNumberReader(new InputStreamReader(new FileInputStream(this.path), "UTF-8"));
                    this.dataSource = reader;
                    this.opened = true;
                } else if (this.iomode == 2) {
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path),"UTF-8"));
                    this.dataSource = out;
                    this.opened = true;
                } else if (this.iomode == 8) {
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path,true),"UTF-8"));
                    this.dataSource = out;
                    this.opened = true;
                }
            } catch (Exception e) {
                throw new Error("<dswTextFile>.Open error occurred trying to open " + this.path + " " + e.getMessage());
            }
        }else{
            throw new Error("<dswTextFile>.Open: file is already opened");
        }
    }

    
    @Override
    public void Create() {
        if (!this.opened) {
            try {
                File file = new File(this.path);
                Boolean r = file.createNewFile();
                if (r) {
                    System.out.println("<dswTextFile>.Create : File is created!");
                } else {
                    System.out.println("<dswTextFile>.Create : File already exists.");
                }
                this.Open();
            } catch (Exception e) {
                throw new Error("<dswTextFile>.Create : " + e.getMessage());
            }
        } else {
            throw new Error("<dswTextFile>.Create : file is already opened");
        }
    }
    
    @Override
    public void Close() {
        if (this.opened) {
            if (this.dataSource instanceof LineNumberReader) {
                try {
                    ((LineNumberReader) this.dataSource).close();
                } catch (IOException ex) {
                    throw new Error("<dswExternalDataSource>.Close: "+ex.getMessage());
                }
            } else if (this.dataSource instanceof BufferedWriter) {
                try {
                    ((BufferedWriter) this.dataSource).close();
                } catch (IOException ex) {
                    throw new Error("<dswExternalDataSource>.Close: "+ex.getMessage());
                }
            }
            this.opened = false;
        } else {
            //System.out.println("<dswExternalDataSource>.Close: file is already closed");
        }
    }

    @Override
    public boolean IsOpened() {
        return this.opened;
    }

    @Override
    public boolean Exists() {
        return Files.exists(Paths.get(this.path));
    }

    @Override
    public void Move(String dest) {
        if (!this.opened) {
            try {
                Files.move(Paths.get(this.path), Paths.get(dest), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                throw new Error("<dswExternalDataSource>.Move : " + ex.getMessage());
            }
        } else {
            throw new Error("dswExternalDataSource file is opened, can not move file");
        }
    }

    @Override
    public void Copy(String dest) {
        if (!this.opened) {
            try {
                Files.copy(Paths.get(this.path), Paths.get(dest), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                throw new Error("<dswExternalDataSource>.Copy : "+ex.getMessage());
            }
        } else {
            throw new Error("dswExternalDataSource file is opened, can not copy file");
        }
    }

    @Override
    public void Delete() {
        if (!this.opened) {
            try {
                Files.delete(Paths.get(this.path));
            } catch (IOException ex) {
                throw new Error("<dswExternalDataSource>.Delete : "+ex.getMessage());
            }
        } else {
            throw new Error("dswExternalDataSource file is opened, can not delete file");
        }
    }

    /**
     * get data source file
     *
     * @return
     */
    public File GetFile() {       
        if (!this.opened) {
            return new File(this.path);            
        } else {
            throw new Error("dswExternalDataSource file is opened, can not delete file");
        }
    }
    
    @Override
    public Boolean EndOfSource() {
        if (!this.opened) 
            throw new Error("<dswTextFile>.endOfSource: stream is closed");
        if (this.dataSource instanceof LineNumberReader) {
            try {
                ((LineNumberReader) this.dataSource).mark(1000000);
                String s = ((LineNumberReader) this.dataSource).readLine();
                ((LineNumberReader) this.dataSource).reset();
                if (s == null) {
                    return true;
                }
            } catch (IOException ex) {
                 throw new Error("<dswTextFile>.endOfSource : " + ex.getMessage());
            }
        }
        return false;
    }

    private boolean isDigit(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public String toString(){
        return "[" + this.type + "<" + this.iomode + ">: " + this.path + "]";
    }
}
