/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsw.objects;

import dsw.abstracts.dswCommon;
import dsw.objects.binaryFile.dswBinaryFile;
import dsw.objects.binaryFile.dswBinaryFileSearch;
import extendedData.xData;

/**
 *
 * @author Alexey Savenkov OIS 2016
 */
public class dswParadigms implements dswCommon{

    String target;
    private dswBinaryFile bin=null;
    private dswBinaryFileSearch binSearch=null;
    private int maxlevel=0;
    boolean prefixCheck=true;
    private Object paradimgsPrefixesPath;
    private Object paradigmsPath;
    private dswBinaryFile prefixes = null;
    private dswBinaryFile paradigms = null;
    private String[] blockNumbers = null;
    private int currentBlockInd = 0;
    
    public dswParadigms(Object data){
        this.SetDataSource(data);
    }
    
    public dswParadigms(String _paradimgsPrefixesIndexPath,String _paradigmsIndexPath,String _paradimgsPrefixesPath,String _paradigmsPath, String _targetWord){
        this.bin = new dswBinaryFile(_paradimgsPrefixesIndexPath,1,61);
        this.binSearch = new dswBinaryFileSearch(_paradigmsIndexPath,1,226,_targetWord,"\\s+");
        this.target=_targetWord;
        this.paradigmsPath = _paradigmsPath;
        this.paradimgsPrefixesPath = _paradimgsPrefixesPath;
        this.bin.Open();
//        this.binSearch.Open();
    }
    
    @Override
    public int maxLevel() {
        return this.maxlevel;
    }
    
    private xData xReadLevel() {
        if (this.prefixes == null && this.paradigms == null) {// проверка на наличие открытых файлов
            if (this.prefixes == null) {//сначала попытка найти слово в словаре префиксов
                while (!this.bin.EndOfSource()) {
                    xData buf = this.bin.xRead();
                    String[] block = ((String) buf.getData()).split("\\s+");
                    String word = block[0].substring(1);
                    if(this.target.regionMatches(true, this.target.length()-word.length(),
                            word, 0, word.length())){
                        this.prefixes = new dswBinaryFile(this.paradimgsPrefixesPath, 1, 51);
                        this.prefixes.Open();
                        this.blockNumbers = block[1].split(",");                  
                        this.prefixes.MoveAt(Integer.parseInt(this.blockNumbers[this.currentBlockInd]));
                        this.currentBlockInd++;
                        return this.prefixes.xRead();
                    }
                }
            }
            if (this.paradigms == null) {// если слово не найдено в словаре префиксов осуществляется поиск в основном словаре
                xData buf = binSearch.xRead();
                this.paradigms = new dswBinaryFile(this.paradigmsPath, 1, 102);
                this.paradigms.Open();
                this.blockNumbers = ((String) buf.getData()).split("\\s+")[1].split(",");
                this.paradigms.MoveAt(Integer.parseInt(this.blockNumbers[this.currentBlockInd]));
                this.currentBlockInd++;
                return this.paradigms.xRead();
            }
        } else {//выводятся все возможные омонимы входной словоформы, когда словоформ больше нет вернется null
            if (this.prefixes != null && currentBlockInd<this.blockNumbers.length) {
                this.prefixes.MoveAt(Integer.parseInt(this.blockNumbers[this.currentBlockInd]));               
                this.currentBlockInd++;
                return this.prefixes.xRead();
            }
            if (this.paradigms != null && currentBlockInd<this.blockNumbers.length) {
                this.paradigms.MoveAt(Integer.parseInt(this.blockNumbers[this.currentBlockInd]));
                this.currentBlockInd++;
                return this.paradigms.xRead();
            }
        }
        return null;
    }

    @Override
    public xData xRead() {
        return xReadLevel();
    }

    @Override
    public Boolean EndOfSource() {
        if(this.bin == null && this.binSearch == null)
            return true;
        if(this.blockNumbers!=null)
            return (this.currentBlockInd >= this.blockNumbers.length);
        else 
            return false;
    }

    @Override
    public Object SetDataSource(Object data) {//на вход принимает xData в следующем виде
        if (data != null) {            //[String targetWord,"paradimgsPrefixesIndexPath",String path,
            if (data instanceof xData) {                  //"paradigmsIndexPath", String path,  
                this.bin=null;                            //"paradimgsPrefixesPath",String path,
                this.binSearch=null;                      //"paradigmsPath",String path]
                this.currentBlockInd = 0;                 //в случае отсутствия какого либо пути будет установлено значение по умолчанию
                this.prefixes = null;
                this.paradigms = null;
                this.blockNumbers = null;
                xData xd = (xData) data;
                if (xd.getData() instanceof String) {
                    this.target = (String) xd.getData();
                } else {
                    throw new Error("type mismatch");
                }
                if (xd.hasOwnProperty("paradimgsPrefixesIndexPath")) {
                    this.bin = new dswBinaryFile(xd.get("paradimgsPrefixesIndexPath"), 1, 61);
                } else {
                    this.bin = new dswBinaryFile("paradigmsPrefixesIndex.bin", 1, 61);
                    if (xd.hasOwnProperty("paradigmsIndexPath")) {
                        this.binSearch = new dswBinaryFileSearch(xd.get("paradigmsIndexPath"), 1, 226, this.target, "\\s+");
                    } else {
                        this.binSearch = new dswBinaryFileSearch("paradigmsIndex.bin", 1, 226, this.target, "\\s+");
                    }
                }
                if (xd.hasOwnProperty("paradimgsPrefixesPath")) {
                    this.paradimgsPrefixesPath = xd.get("paradimgsPrefixesPath");
                } else {
                    this.paradimgsPrefixesPath = "paradigmsPrefixes.bin";
                    if (xd.hasOwnProperty("paradigmsPath")) {
                        this.paradigmsPath = xd.get("paradigmsPath");
                    } else {
                        this.paradigmsPath = "paradigms.bin";
                    }
                }
            } else {
                throw new Error("type mismatch");
            }
            this.bin.Open();
//            this.binSearch.Open();
            return bin.GetDataSource();
        } else {
            this.currentBlockInd = 0;
            this.prefixes = null;
            this.paradigms = null;
            this.blockNumbers = null;
            return null;
        }
    }

    @Override
    public Object GetDataSource() {
        return this.bin;
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
