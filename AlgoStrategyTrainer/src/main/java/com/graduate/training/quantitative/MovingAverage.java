package com.graduate.training.quantitative;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;

public class MovingAverage {
    private int period;
    private double sum;
    private Queue<Double> range = new LinkedList<>();

    public MovingAverage(int period) {
        this.period = period;
    }

    public MovingAverage(int period, List<Double> range){
        this.period = period;
        this.range = new LinkedList<>(range);
        double initialSum = 0.0;
        for(double price : range){
            initialSum += price;
        }
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
