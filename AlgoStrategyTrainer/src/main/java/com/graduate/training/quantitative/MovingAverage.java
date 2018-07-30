package com.graduate.training.quantitative;
import java.util.Queue;
import java.util.LinkedList;

public class MovingAverage {
    private int period;
    private double sum;
    private Queue<Double> range = new LinkedList<>();

    public MovingAverage(int period) {
        this.period = period;
    }

    public MovingAverage(int period, Queue range){
        this.period = period;
        this.range = range;
    }

    public void addPrice(double price){
        range.add(price);
        sum += price;
        if (range.size() > period){
            sum -= range.remove();
        }
    }

    public double getAverage(){
        return sum/period;
    }

}
