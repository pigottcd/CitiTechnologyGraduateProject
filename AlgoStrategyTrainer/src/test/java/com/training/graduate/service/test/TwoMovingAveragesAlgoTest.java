package com.training.graduate.service.test;

import com.graduate.training.entities.Order;
import com.graduate.training.entities.Strategy;
import com.graduate.training.service.PriceFeedService;
import com.graduate.training.service.PriceFeedServiceImpl;
import com.graduate.training.service.StrategyAlgo;
import com.graduate.training.service.TwoMovingAveragesAlgo;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TwoMovingAveragesAlgoTest {

    @InjectMocks
    private TwoMovingAveragesAlgo algo;

    @Mock
    private PriceFeedService service;

    @Mock
    private Strategy strategy;

    int shortPeriod;
    int longPeriod;
    List<Double> priceList = new ArrayList<>();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        algo = new TwoMovingAveragesAlgo(strategy, shortPeriod, longPeriod);

        for(int i =  0; i <= 8; i++){
            priceList.add(71.0);
        }

    }


    @Test
    public void testRunStrategy_returnsNullOrder_IfInsufficientData() {
        priceList.clear();
        algo.setLongPeriod(10);
        when(service.getPriceRange(any(String.class), any(Integer.class))).thenReturn(priceList);
        assertNull(algo.runStrategy(service));

    }

    @Test
    public void testRunStrategy_createSellOrder_IfShortAverageCrossesBelow() {
        priceList.add(40.0);

        when(service.getPriceRange(any(String.class),any(Integer.class))).thenReturn(priceList.subList(8, 10)).thenReturn(priceList);

        algo.setLongPeriod(10);
        algo.setShortPeriod(2);
        algo.setShortAverage(71.0);
        algo.setLongAverage(70.0);

        Order order = algo.runStrategy(service);
        assertFalse(order.isBuy());
    }

    @Test
    public void testRunStrategy_createBuyOrder_IfShortAverageCrossesAbove() {

        priceList.add(100.0);

        when(service.getPriceRange(any(String.class),any(Integer.class))).thenReturn(priceList.subList(8, 10)).thenReturn(priceList);

        algo.setLongPeriod(10);
        algo.setShortPeriod(2);
        algo.setShortAverage(70.0);
        algo.setLongAverage(71.0);

        Order order = algo.runStrategy(service);
        assertTrue(order.isBuy());
    }

    @Test
    public void runStrategy_returnNullOrder_IfPastBuyFalseAndShortAverageCrossesBelow() {

        priceList.add(100.0);

        when(service.getPriceRange(any(String.class),any(Integer.class))).thenReturn(priceList.subList(8, 10)).thenReturn(priceList);

        algo.setLongPeriod(10);
        algo.setShortPeriod(2);
        algo.setShortAverage(70.0);
        algo.setLongAverage(71.0);
        algo.setPastBuy(true);

        Order order = algo.runStrategy(service);
        assertNull(order);
    }

    @Test
    public void runStrategy_returnNullOrder_IfPastBuyTrueAndShortAverageCrossesAbove() {

        priceList.add(40.0);

        when(service.getPriceRange(any(String.class),any(Integer.class))).thenReturn(priceList.subList(8, 10)).thenReturn(priceList);

        algo.setLongPeriod(10);
        algo.setShortPeriod(2);
        algo.setShortAverage(71.0);
        algo.setLongAverage(70.0);
        algo.setPastBuy(false);

        Order order = algo.runStrategy(service);
        assertNull(order);
    }

}