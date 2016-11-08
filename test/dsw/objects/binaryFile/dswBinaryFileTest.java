/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsw.objects.binaryFile;

import extendedData.xDataFactory;
import org.junit.Test;
import testUtils.testUtils;

/**
 *
 * @author Alexey Savenkov OIS 2016
 */
public class dswBinaryFileTest {

    dswBinaryFile bf;

    @Test
    public void test() {
        //bf=new dswBinaryFile("paradigmsIndex.bin",1,226);61
        //bf=new dswBinaryFile("paradigmsPrefixesIndex.bin",1,61);
        bf=new dswBinaryFile(xDataFactory.createxData(new Object[]{"VLIndex.bin","blockLength",153}));
        //bf=new dswBinaryFile(xDataFactory.createxData(new Object[]{"VL.bin","blockLength",157}));
        bf.Open();
        testUtils.testDSWParse(bf);
        bf.Close();
        /*[ aaaaaa; xLevel: 0; xIndexOnLevel: 1; ]
          [ bbbbbb; xLevel: 0; xIndexOnLevel: 2; ]
          [ cccccc; xLevel: 0; xIndexOnLevel: 3; ]
          [ dddddd; xLevel: 0; xIndexOnLevel: 4; ]
          [ eeeeee; xLevel: 0; xIndexOnLevel: 5; ]
          [ ffffff; xLevel: 0; xIndexOnLevel: 6; ]
          [ gggggg; xLevel: 0; xIndexOnLevel: 7; ]
          [ hhhhhh; xLevel: 0; xIndexOnLevel: 8; ]*/
    }
}
