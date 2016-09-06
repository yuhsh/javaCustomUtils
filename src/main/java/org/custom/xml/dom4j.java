package org.custom.xml;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class dom4j {

    private static final String FILE_PATH = "/Users/yuhaisheng/Documents/sts-bundle/githubRepository/javaCustomUtils/src/main/java/org/custom/xml/person.xml";
    
    public static void main(String[] args) throws DocumentException, IOException {

        selectAllName();
       
        selectSin();
        
        addSex();
        
        addAgeBefre();
        
        modifyAge();
        
        deleteSchool();
        
        getValues();
    }
    private static void getValues() throws DocumentException, IOException {
        // 创建解析器
        SAXReader reader = new SAXReader();
        // 得到document
        Document document = reader.read(FILE_PATH);
        // 得到根节点
        Element root = document.getRootElement();
        // 得到指定标签下一个子标签
        Element p1 = root.element("p1");
        // 获得p1的属性值
        System.out.println(p1.attributeValue("id1"));
    }
    
    private static void deleteSchool() throws DocumentException, IOException {
        // 创建解析器
        SAXReader reader = new SAXReader();
        // 得到document
        Document document = reader.read(FILE_PATH);
        // 得到根节点
        Element root = document.getRootElement();
        // 得到指定标签下一个子标签
        Element p1 = root.element("p1");
        // 得到p1下的school
        Element school = p1.element("school");
        // 删除school元素
        // 通过父节点删除
        boolean removeflg = p1.remove(school);
        // 获取父节点
//        school.getParent();
        // 回写xml
        OutputFormat outputFormat = OutputFormat.createPrettyPrint(); //可缩进
        XMLWriter xmlWriter = new XMLWriter(new FileOutputStream(FILE_PATH), outputFormat);
        xmlWriter.write(document);
        xmlWriter.close();
    }
    
    private static void modifyAge() throws DocumentException, IOException {
        // 创建解析器
        SAXReader reader = new SAXReader();
        // 得到document
        Document document = reader.read(FILE_PATH);
        // 得到根节点
        Element root = document.getRootElement();
        // 得到指定标签下一个子标签
        Element p1 = root.element("p1");
        // 得到p1下的age
        Element age = p1.element("age");
        // 修改值
        age.setText("30");
        // 回写xml
        OutputFormat outputFormat = OutputFormat.createPrettyPrint(); //可缩进
        XMLWriter xmlWriter = new XMLWriter(new FileOutputStream(FILE_PATH), outputFormat);
        xmlWriter.write(document);
        xmlWriter.close();
    }
    
    private static void addAgeBefre() throws DocumentException, IOException {
        // 创建解析器
        SAXReader reader = new SAXReader();
        // 得到document
        Document document = reader.read(FILE_PATH);
        // 得到根节点
        Element root = document.getRootElement();
        // 得到指定标签下一个子标签
        Element p1 = root.element("p1");
        // 获取p1下所有元素
        List<Element> list = p1.elements();
        // 创建元素
        Element school = DocumentHelper.createElement("school");
        // 在school下面创建文本
        school.setText("test context");
        // 在特定位置添加
        list.add(1,school);
        // 回写xml
        OutputFormat outputFormat = OutputFormat.createPrettyPrint(); //可缩进
        XMLWriter xmlWriter = new XMLWriter(new FileOutputStream(FILE_PATH), outputFormat);
        xmlWriter.write(document);
        xmlWriter.close();
    }
    
    private static void addSex() throws DocumentException, IOException {
        // 创建解析器
        SAXReader reader = new SAXReader();
        // 得到document
        Document document = reader.read(FILE_PATH);
        // 得到根节点
        Element root = document.getRootElement();
        // 得到指定标签下一个子标签
        Element p1 = root.element("p1");
        // 在p1下直接添加元素
        Element sex1 = p1.addElement("sex");
        // 在sex下添加文本
        sex1.setText("nan");
        // 回写xml
        OutputFormat outputFormat = OutputFormat.createPrettyPrint(); //可缩进
//        OutputFormat outputFormat = OutputFormat.createCompactFormat(); //压缩
        XMLWriter xmlWriter = new XMLWriter(new FileOutputStream(FILE_PATH), outputFormat);
        xmlWriter.write(document);
        xmlWriter.close();
    }
    
    
    private static void selectSin() throws DocumentException {
        // 创建解析器
        SAXReader reader = new SAXReader();
        // 得到document
        Document document = reader.read(FILE_PATH);
        // 得到根节点
        Element root = document.getRootElement();
        // 得到指定标签下一个子标签
        Element p1 = root.element("p1");
        // 获取p1下面的name元素
        Element name1 = p1.element("name");
        // 得到name的值
        System.out.println(name1.getText());
    }
    
    private static void selectAllName() throws DocumentException {
        // 创建解析器
        SAXReader reader = new SAXReader();
        // 得到document
        Document document = reader.read(FILE_PATH);
        // 得到根节点
        Element root = document.getRootElement();
        // 得到指定标签下所有一个子标签（一层）
        List<Element> list = root.elements("p1");
        for (Element element : list) {
            // 获取指定标签下的第一个子标签
            Element nameElement = element.element("name");
            System.out.println(nameElement.getText());
        }
    }

}
