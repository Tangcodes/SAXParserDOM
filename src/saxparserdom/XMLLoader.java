/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saxparserdom;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


/**
 *
 * @author dale
 * @author Jingxuan Tang
 * http://www.saxproject.org/apidoc/org/xml/sax/helpers/DefaultHandler.html
 * https://docs.oracle.com/javase/8/docs/api/org/xml/sax/SAXException.html#SAXException--
 */
public class XMLLoader {
    public static List<List<String>> load(File xmlCourseFile) throws Exception{
        List<List<String>> result=new ArrayList<>();
        try{
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            
            DefaultHandler handler = new DefaultHandler() {
                List<String> node=new ArrayList<>();
                String title;
                String Att;
            
                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    title = qName;
                    String attri=attributes.getValue(0);
                    if(attri!=null){
                        Att=qName;
                        node.add(qName.toString()+"  "+attri);
                    }
                }
                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    if(qName==Att){
                        result.add(node);
                        Att=null;
                        List<String> listString=new ArrayList<>();
                        node=listString;
                    }
                    else if(qName==title){
                        result.add(node);
                        title=null;
                        List<String> listString=new ArrayList<>();
                        node=listString;
                    }
                }

                @Override
                public void characters(char[] ch, int start, int length) throws SAXException {
                    String newString=new String(ch, start, length);
                    node.add(newString);
                }
            };
            
            saxParser.parse(xmlCourseFile.getAbsoluteFile(), handler);
            
        } catch (Exception e) {
            throw e;
        }
        
        return result;
    }
}
