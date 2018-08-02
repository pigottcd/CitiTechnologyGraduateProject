package com.training.graduate.service.test;

import com.graduate.training.dao.OrderRepository;
import com.graduate.training.entities.Order;
import com.graduate.training.service.OrderService;
import com.graduate.training.service.OrderServiceImpl;
import com.graduate.training.messaging.ActiveMQSender;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static com.sun.org.apache.xerces.internal.util.PropertyState.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;


public class OrderServiceImplTest {
    @InjectMocks
    private OrderServiceImpl service;

    @Mock
    private ActiveMQSender sender;

    @Mock
    private OrderRepository dao;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new OrderServiceImpl(sender, dao);
    }

    @Test
    public void testGetOrderById_returnsOrder() {
        when(dao.findById(any(Integer.class))).thenReturn(new Order());
        Integer id = 1;
        assertNotNull(service.getOrderByID(id));
    }

    @Test
    public void testGetOrderByStrategyId_returnOrder() {
        when(dao.findByStrategyId(any(Integer.class))).thenReturn(new ArrayList<>());
        Integer id = 1;
        assertNotNull(service.getOrderByStrategyID(id));
    }

    @Test
    public void testAddOrder_savesOrderAndSendsOrder(){
        Order o = new Order();
        dao.save(o);
        verify(dao, times(1)).save(o);

        sender.send(o);
        verify(sender, times(1)).send(o);

    }
}
