/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nischal
 */
public class FileOperator {
    
    private String absolutePath;

    public FileOperator() {
        this.absolutePath = "C:\\testFiles\\";
        new File(this.absolutePath).mkdir();
    }
    
    public final Desktop desktop = Desktop.getDesktop();
//    BufferedWriter outStream = null;
    
    void openFile(File file) {
        try{
            desktop.open(file);
        }catch (IOException ex) {
            Logger.getLogger(MainApplication.class.getName()).log(
                    Level.SEVERE, null, ex
                );
        }
    }
    
    public void createFile(String fileName, String fileContent, String fileType) throws FileNotFoundException, IOException{
//        FileInputStream inputStream = new FileInputStream(fileContent);
        String parsedFileName = parseFileName(fileName);
        String parsedFolderName = parseFolderName(fileType);
        new File(this.absolutePath+parsedFolderName+File.separator).mkdirs();
        File targetFile = new File(this.absolutePath+parsedFolderName+File.separator,parsedFileName+".txt");
        targetFile.createNewFile();
//        outStream = new FileOutputStream(targetFile);
        try {
            BufferedWriter outStream = new BufferedWriter(new FileWriter(targetFile));
            outStream.write(fileContent);  //Replace with the string you are trying to write  
            outStream.close();
        }
        catch (IOException e)
        {
            System.out.println("Exception ");
        }
//        int c;
//        while ((c = inputStream.read()) != -1) {
//           outStream.write(c);
//        }
    }
    
    private String parseFileName(String fileName){
        int lastIndex;
        lastIndex = fileName.lastIndexOf(".");
        if (lastIndex >= 0) {
            fileName = fileName.substring(0,lastIndex);
        }
        return fileName;
    }
    
    private String parseFolderName(String fileType){
        String folderName;
        folderName = fileType.replaceAll("/", "-");
        return folderName;
    }
    
}
