package org.custom.xml;

import java.io.IOException;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class dom4jXpath {

    private static final String FILE_PATH = "/Users/yuhaisheng/Documents/sts-bundle/githubRepository/javaCustomUtils/src/main/java/org/custom/xml/person.xml";
    
    public static void main(String[] args) throws DocumentException, IOException {
        selectNodes();
        
        selectSingleNode();
    }
    private static void selectSingleNode() throws DocumentException, IOException {
        // 创建解析器
        SAXReader reader = new SAXReader();
        // 得到document
        Document document = reader.read(FILE_PATH);
        // 使用selectNodes("//name")方法得到所有name元素
        Node node =  document.selectSingleNode("//p1[@id1='aaaa']/name");
        
        System.out.println("Text " + node.getText());
    }

    private static void selectNodes() throws DocumentException, IOException {
        // 创建解析器
        SAXReader reader = new SAXReader();
        // 得到document
        Document document = reader.read(FILE_PATH);
        // 使用selectNodes("//name")方法得到所有name元素
        List<Node> list =  document.selectNodes("//name");
        for (Node node : list) {
            System.out.println("Text " + node.getText());
        }
        
    }
}
