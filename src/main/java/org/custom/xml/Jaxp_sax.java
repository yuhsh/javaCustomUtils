package org.custom.xml;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Jaxp_sax {
    private static final String FILE_PATH = "/Users/yuhaisheng/Documents/sts-bundle/githubRepository/javaCustomUtils/src/main/java/org/custom/xml/person.xml";

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {

        // 创建解析器工厂
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        // 创建解析器
        SAXParser saxParser = saxParserFactory.newSAXParser();
        // 执行parse方法
//        saxParser.parse(FILE_PATH, new CustomDefaultHandler());
//        saxParser.parse(FILE_PATH, new CustomSearchDefaultHandler());
        saxParser.parse(FILE_PATH, new CustomIdxDefaultHandler());
    }
}
class CustomIdxDefaultHandler extends DefaultHandler {
    boolean flg = false;
    int idx = 2;
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        // 判断qName是否是指定元素
        if ("name".equals(qName)) {
            flg = true;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (flg && idx == 2) {
            System.out.println(new String(ch, start, length));
        }
    }
    

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if ("name".equals(qName)) {
            flg = false;
            idx++;
        }
    }
}

class CustomSearchDefaultHandler extends DefaultHandler {
    boolean flg = false;
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        // 判断qName是否是指定元素
        if ("name".equals(qName)) {
            flg = true;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (flg) {
            System.out.println(new String(ch, start, length));
        }
    }
    

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if ("name".equals(qName)) {
            flg = false;
        }
    }
}

class CustomDefaultHandler extends DefaultHandler {

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        System.out.print("<" + qName + ">");
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        System.out.print(new String(ch, start, length));
    }
    

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        System.out.print("</" + qName + ">");
        
    }
}
