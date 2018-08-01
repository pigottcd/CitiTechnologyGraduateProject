package com.graduate.training.entities;

import com.graduate.training.messaging.ActiveMQSender;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

@Entity
@Table(name = "strategy")

public class Strategy {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name = "type")         private String type;
    @Column(name = "ticker")       private String ticker;
    @Column(name = "active")       private Boolean active;
    @Column(name = "quantity")     private Integer quantity;
    @Column(name = "short_period") private Integer shortPeriod;
    @Column(name = "long_period")  private Integer longPeriod;


    @Column(name = "p_and_l")      private Double pAndL;

    public Strategy() {}
    public Strategy(String type, String ticker, Boolean active, Integer quantity,
                    Integer shortPeriod, Integer longPeriod, double pAndL) {
        this.type = type;
        this.ticker = ticker;
        this.active = active;
        this.quantity = quantity;
        this.shortPeriod = shortPeriod;
        this.longPeriod = longPeriod;
        this.pAndL = pAndL;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getShortPeriod() {
        return shortPeriod;
    }

    public void setShortPeriod(Integer shortPeriod) {
        this.shortPeriod = shortPeriod;
    }

    public Integer getLongPeriod() {
        return longPeriod;
    }

    public void setLongPeriod(Integer longPeriod) {
        this.longPeriod = longPeriod;
    }

    public Double getPAndL() {
        return pAndL;
    }
}
