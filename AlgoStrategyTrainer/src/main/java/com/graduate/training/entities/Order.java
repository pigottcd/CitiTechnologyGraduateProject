package com.graduate.training.entities;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.persistence.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name = "buy")    private boolean buy;
    @Column(name = "price")  private double price;
    @Column(name = "size")   private int size;
    @Column(name = "stock")  private String stock;
    @Column(name ="time")    private LocalDateTime time;
    @Column(name = "status") private String status;

    public Order() {}

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
