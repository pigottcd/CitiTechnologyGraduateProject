package com.graduate.training.entities;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.persistence.MappedSuperclass;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Order {


    private boolean buy;
    private int id;
    private double price;
    private int size;
    private String stock;
    private LocalDateTime time;
    private String status;

    public Order(boolean buy, int id, double price, int size,
                 String stock, LocalDateTime time) {
        this.buy = buy;
        this.id = id;
        this.price = price;
        this.size = size;
        this.stock = stock;
        this.time = time;
        this.status = null;
    }

    public Order(String executionXML) throws ParserConfigurationException,
             SAXException, java.io.IOException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        ByteArrayInputStream input = new ByteArrayInputStream(
                    executionXML.getBytes("UTF-8")
        );
        Document doc = builder.parse(input);

        Element element = (Element) doc.getElementsByTagName("trade").item(0);
        this.buy = Boolean.parseBoolean(
                element.getElementsByTagName("buy").item(0).getTextContent()
        );
        this.id = Integer.parseInt(
                element.getElementsByTagName("id").item(0).getTextContent()
        );
        this.price = Double.parseDouble(
                element.getElementsByTagName("price").item(0).getTextContent()
        );
        this.size = Integer.parseInt(
                element.getElementsByTagName("size").item(0).getTextContent()
        );
        this.stock = element.getElementsByTagName("stock").item(0).getTextContent();
        DateTimeFormatter format = DateTimeFormatter.ISO_DATE_TIME;
        this.time = LocalDateTime.parse(
                element.getElementsByTagName("whenAsDate").item(0).getTextContent(),
                format
        );
        this.status = element.getElementsByTagName("result").item(0).getTextContent();
    }

    public String toString() {
        String result = "";
        result += "<trade>\n";
        result += "<buy>" + Boolean.toString(buy) + "</buy>\n";
        result += "<id>" + id + "</id>\n";
        result += "<price>" + price + "</price>\n";
        result += "<size>" + size + "</size>\n";
        result += "<stock>" + stock + "</stock>\n";
        result += "<whenAsDate>" + time + "</whenAsDate>\n";
        if(status != null){
            result += "<result>" + status + "</result>\n";
        }
        result += "</trade>";
        return result;
    }

    public boolean isBuy() {
        return buy;
    }

    public void setBuy(boolean buy) {
        this.buy = buy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
