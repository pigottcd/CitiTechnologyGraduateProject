package com.training.graduate.service.test;

import com.graduate.training.dao.StrategyRepository;
import com.graduate.training.entities.Strategy;
import com.graduate.training.service.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.springframework.test.web.client.ExpectedCount.times;
import static org.mockito.Mockito.verify;

public class StrategyServiceImplTest {
    @InjectMocks
    private StrategyServiceImpl strategyService;

    @Mock
    private OrderServiceImpl orderService;

    @Mock
    private PriceFeedService feed;

    @Mock
    private StrategyRepository dao;

    @Mock
    private List<StrategyAlgo> strategies;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        strategyService = new StrategyServiceImpl (orderService, feed, dao);
        strategies = new ArrayList<>();
    }

    @Test
    public void testAddStrategy_registersStrategyAndSavesStrategyAndAddsAlgo() {
        Strategy strategy = new Strategy();
        StrategyAlgo algo = new TwoMovingAveragesAlgo(strategy, 10, 2);
        feed.register(strategy.getTicker());
        verify(feed).register(strategy.getTicker());

        dao.save(strategy);
        verify(dao).save(strategy);

        strategies.add(algo);
        assertFalse(strategies.isEmpty());

    }

}
