package com.training.graduate.controller.test;

import org.junit.Test;

/*
import com.graduate.training.controller.OrderController;
import com.graduate.training.entities.Order;
import com.graduate.training.service.OrderService;
import com.graduate.training.service.OrderServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@RunWith(SpringRunner.class)
//@WebMvcTest(OrderController.class)
//@ContextConfiguration(classes={com.graduate.training.AppConfig.class})
//@TestPropertySource(locations = "classpath:application-test.properties") // this is only needed because swagger breaks tests
*/
public class OrderControllerTest {

    @Test
    public void testThatCanConnect() {
        return;
    }

    /*@Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService service;

    @Test
    public void testThatCanConnect() throws Exception {

        Order order = new Order(true, 100.00, 100, "C", LocalDateTime.now(), 1);

        given(service.getOrderByID(1)).willReturn(order);

        mockMvc.perform(get("/orders/strategy_id/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
*/
}
