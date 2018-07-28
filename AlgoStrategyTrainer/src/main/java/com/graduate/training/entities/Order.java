package com.graduate.training.entities;

import java.time.LocalDateTime;


public class Order {
    private boolean buy;
    private int id;
    private double price;
    private int size;
    private String stock;
    private LocalDateTime time;

    public Order(boolean buy, int id, double price, int size,
                 String stock, LocalDateTime time) {
        this.buy = buy;
        this.id = id;
        this.price = price;
        this.size = size;
        this.stock = stock;
        this.time = time;
    }
    public String toString() {
        String result = "";
        result +=  "<trade>\n";
        result += "<buy>" + Boolean.toString(buy) + "</buy>\n";
        result += "<id>" + id + "</id>\n";
        result += "<price>" + price + "</price>\n";
        result += "<size>" + size + "</size>\n";
        result += "<stock>" + stock + "</stock>\n";
        result += "<whenAsDate>" + time + "</whenAsDate>\n";
        result += "</trade>";
        return result;
    }
}
