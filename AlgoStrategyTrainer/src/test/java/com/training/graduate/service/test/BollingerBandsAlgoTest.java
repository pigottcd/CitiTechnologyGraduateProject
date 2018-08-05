package com.training.graduate.service.test;

import com.graduate.training.entities.Order;
import com.graduate.training.entities.Strategy;
import com.graduate.training.service.BollingerBandsAlgo;
import com.graduate.training.service.PriceFeedService;
import com.graduate.training.service.PriceFeedServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class BollingerBandsAlgoTest {

    @InjectMocks
    private BollingerBandsAlgo algo;

    @Mock
    private PriceFeedService service;

    @Mock
    private Strategy strategy;

    private int windowLength;
    private int deviation;
    private List<Double> priceList;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        algo = new BollingerBandsAlgo(strategy, windowLength, deviation);
        priceList = new ArrayList<>();
        for(int i =  0; i <= 8; i++){
            priceList.add(71.0);
        }
    }

    @Test
    public void testRunStrategy_returnNullOrder_IfInsufficientData(){
        priceList.clear();
        algo.setWindowLength(10);
        when(service.getPriceRange(any(String.class), any(Integer.class))).thenReturn(priceList);

        Order order = algo.runStrategy(service);
        assertNull(order);
    }
}
