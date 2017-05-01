/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir;

import java.io.File;
import java.io.IOException;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import java.io.FileInputStream;
import java.util.HashMap;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;

import org.xml.sax.SAXException;

/**
 *
 * @author nischal
 */

public class Extractor {

   public String fileType(File file) throws Exception {
      //Instantiating tika facade class 
      Tika tika = new Tika();
      //detecting the file type using detect method
      String fileType = tika.detect(file);
//    System.out.println(filetype);
      return fileType;
   }
   
// parsing filecontents using tika directly
   public String fileContent(File file) throws IOException, TikaException{
      Tika tika = new Tika();
      String fileContent = tika.parseToString(file);
//      System.out.println("Extracted Content: " + filecontent);
      return fileContent;
   }
   
//	file content extraction using parser library
   public String parseFile(File file) throws IOException,SAXException, TikaException {
       
        //parse method parameters
        // parse the file
        Parser parser = new AutoDetectParser(); 
        //file ko xhtml format bat kun part tanne
        BodyContentHandler handler = new BodyContentHandler();
        //suru ma khali ani paxi metadata sabai key-value pair banera basxa yesma
        Metadata metadata = new Metadata();
//   InputStream stream = TikaInputStream.get(new File(file)); --not needed
        /**
         * byte by byte parse garna lai paila continous bit stream ma convert garne
         * stream ma word ra white space sabai check garna milxa
        */
        FileInputStream inputstream = new FileInputStream(file);
        ParseContext context = new ParseContext();
   
        //parsing the file
        parser.parse(inputstream, handler, metadata, context);
        //System.out.println("File content : " + handler.toString());
        return handler.toString();
   }

public HashMap<String,String> getMetadata(File file)throws IOException,SAXException, TikaException {
       //parse method parameters
   Parser parser = new AutoDetectParser();
   BodyContentHandler handler = new BodyContentHandler();
   Metadata metadata = new Metadata();
   HashMap<String, String> hmap = new HashMap<>();
//   InputStream stream = TikaInputStream.get(new File(file));
   FileInputStream inputstream = new FileInputStream(file);
   ParseContext context = new ParseContext();
   
   //parsing the file
   parser.parse(inputstream, handler, metadata, context);
      //getting the list of all meta data elements 
      String[] metadataNames = metadata.names();
      for(String name : metadataNames) {		        
        hmap.put(name,metadata.get(name));
      }
      return hmap;
}

}