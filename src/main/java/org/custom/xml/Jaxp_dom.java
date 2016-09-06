package org.custom.xml;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

public class Jaxp_dom {
    private static final String FILE_PATH = "/Users/yuhaisheng/Documents/sts-bundle/githubRepository/javaCustomUtils/src/main/java/org/custom/xml/person.xml";

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, TransformerException {
        selectAll();
        
        selectSin();
        
        listElement();
        
        addSex();
        
        modifySex();
        
        removeSex();
    }
    private static void listElement() throws ParserConfigurationException, SAXException, IOException {
        // 创建解析器工程
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        // 创建解析器
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        // 解析xml放回document
        Document document = builder.parse(FILE_PATH);
        // 编写一个方法实现遍历操作
        list1(document);
    }
    private static void list1(Node node) {
        // 判断是元素类型
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            System.out.println(node.getNodeName());
        }
        // 得到一层子节点
        NodeList list = node.getChildNodes();
        // 遍历list
        for (int i = 0; i < list.getLength(); i++) {
            // 得到每一个节点
            Node node1 = list.item(i);
            // 继续得到node1的子节点
            list1(node1);
        }
    }
    private static void removeSex() throws ParserConfigurationException, SAXException, IOException, TransformerException {
        // 创建解析器工程
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        // 创建解析器
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        // 解析xml放回document
        Document document = builder.parse(FILE_PATH);
        // 得到sex
        Node sex1 = document.getElementsByTagName("sex").item(0);
        // 得到sex的父节点
        Node p1 = sex1.getParentNode();
        // 删除操作
        p1.removeChild(sex1);
        // 回写xml
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.transform(new DOMSource(document), new StreamResult(FILE_PATH));
    }
    private static void modifySex() throws ParserConfigurationException, SAXException, IOException, TransformerException {
        // 创建解析器工程
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        // 创建解析器
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        // 解析xml放回document
        Document document = builder.parse(FILE_PATH);
        // 得到sex
        Node sex1 = document.getElementsByTagName("sex").item(0);
        // 修改sex值
        sex1.setTextContent("nan");
        // 回写xml
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.transform(new DOMSource(document), new StreamResult(FILE_PATH));
    }
    private static void addSex() throws ParserConfigurationException, SAXException, IOException, TransformerException {
        // 创建解析器工程
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        // 创建解析器
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        // 解析xml放回document
        Document document = builder.parse(FILE_PATH);
        // 得到所有的p1
        NodeList list = document.getElementsByTagName("p1");
        // 得到第一个pi，使用item下标得到
        Node p1 = list.item(0);
        // 创建sex标签
        Element sex1 = document.createElement("sex");
        // 创建文本
        Text text1 = document.createTextNode("nv");
        // 把文本添加到sex厦门
        sex1.appendChild(text1);
        // 把sex添加到第一个p1下面
        p1.appendChild(sex1);
        // 回写xml
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.transform(new DOMSource(document), new StreamResult(FILE_PATH));
    }
    private static void selectSin() throws ParserConfigurationException, SAXException, IOException {
        // 创建解析器工程
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        // 创建解析器
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        // 解析xml放回document
        Document document = builder.parse(FILE_PATH);
        // 得到name元素
        NodeList list = document.getElementsByTagName("name");
        
        Node name = list.item(0);
        String s = name.getTextContent();
        System.out.println(s);
        
    }
    private static void selectAll() throws ParserConfigurationException, SAXException, IOException {
        // 创建解析器工程
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        // 创建解析器
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        // 解析xml放回document
        Document document = builder.parse(FILE_PATH);
        // 得到name元素
        NodeList list = document.getElementsByTagName("name");
        // 变量元素集合
        for (int i = 0; i < list.getLength(); i++) {
            // 得到每一个name元素
            Node name = list.item(i);
            // 得到name元素里面的值
            String s = name.getTextContent();
            System.out.println(s);
        }
    }
}
