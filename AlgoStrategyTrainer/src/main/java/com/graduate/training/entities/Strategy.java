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
    @Column(name = "type")     private String type;
    @Column(name = "ticker")   private String ticker;
    @Column(name = "active")   private Boolean active;
    @Column(name = "quantity") private Integer quantity;

    public Strategy() {}
    public Strategy(String type, String ticker, Boolean active, Integer quantity) {
        this.type = type;
        this.ticker = ticker;
        this.active = active;
        this.quantity = quantity;
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
}
