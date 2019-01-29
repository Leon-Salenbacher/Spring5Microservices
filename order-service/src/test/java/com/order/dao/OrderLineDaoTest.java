package com.order.dao;

import com.order.model.OrderLine;
import org.jooq.DSLContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jooq.JooqTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@JooqTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class OrderLineDaoTest {

    @Autowired
    private DSLContext dslContext;

    private OrderLineDao orderLineDao;

    // Elements used to test the functionality
    private OrderLine orderLine1;
    private OrderLine orderLine2;


    @Before
    public void init() {
        orderLineDao = new OrderLineDao(this.dslContext);

        orderLine1 = orderLineDao.findById(1);
        orderLine2 = orderLineDao.findById(2);
    }


    @Test
    public void getId_whenNullModelIsGiven_thenNullIdIsReturned() {
        // When
        Integer id = orderLineDao.getId(null);

        // Then
        assertNull(id);
    }


    @Test
    public void getId_whenNotNullModelIsGiven_thenItsIdIsReturned() {
        // When
        Integer id = orderLineDao.getId(orderLine1);

        // Then
        assertNotNull(id);
        assertEquals(orderLine1.getId(), id);
    }


    @Test
    public void findByIds_whenNullIdsAreGiven_thenEmptyListIsReturned() {
        // When
        List<OrderLine> orderLines = orderLineDao.findByIds(null);

        // Then
        assertNotNull(orderLines);
        assertTrue(orderLines.isEmpty());
    }


    @Test
    public void findByIds_whenANonExistentIdsIsGiven_thenEmptyListIsReturned() {
        // When
        List<OrderLine> orderLines = orderLineDao.findByIds(-2, -1);

        // Then
        assertNotNull(orderLines);
        assertTrue(orderLines.isEmpty());
    }


    @Test
    public void findByIds_whenAnExistentIdIsGiven_thenRelatedModelIsReturned() {
        // When
        List<OrderLine> orderLines = orderLineDao.findByIds(orderLine1.getId());

        // Then
        assertNotNull(orderLines);
        assertEquals(1, orderLines.size());
        assertThat(orderLines.get(0), samePropertyValuesAs(orderLine1));
    }


    @Test
    public void findByIds_whenExistentIdsAreGiven_thenRelatedModelsAreReturned() {
        // When
        List<OrderLine> orderLines = orderLineDao.findByIds(orderLine1.getId(), orderLine2.getId());

        // Then
        assertNotNull(orderLines);
        assertEquals(2, orderLines.size());
        assertThat(orderLines, contains(orderLine1, orderLine2));
    }


    @Test
    public void findOptionalById_whenNoIdIsGiven_thenOptionalEmptyIsReturned() {
        // When
        Optional<OrderLine> optionalOrderLine = orderLineDao.findOptionalById(null);

        // Then
        assertFalse(optionalOrderLine.isPresent());
    }


    @Test
    public void findOptionalById_whenANonExistentIdIsGiven_thenOptionalEmptyIsReturned() {
        // Given
        Integer nonExistentId = -1;

        // When
        Optional<OrderLine> optionalOrderLine = orderLineDao.findOptionalById(nonExistentId);

        // Then
        assertFalse(optionalOrderLine.isPresent());
    }


    @Test
    public void findOptionalById_whenAnExistentIdIsGiven_thenOptionalWithRelatedModelIsReturned() {
        // When
        Optional<OrderLine> optionalOrderLine = orderLineDao.findOptionalById(orderLine1.getId());

        // Then
        assertTrue(optionalOrderLine.isPresent());
        assertThat(optionalOrderLine.get(), samePropertyValuesAs(orderLine1));
    }


    @Test
    public void findByOrderIds_whenNullIdsAreGiven_thenEmptyListIsReturned() {
        // When
        List<OrderLine> orderLines = orderLineDao.findByOrderIds(null);

        // Then
        assertNotNull(orderLines);
        assertTrue(orderLines.isEmpty());
    }


    @Test
    public void findByOrderIds_whenANonExistentIdsIsGiven_thenEmptyListIsReturned() {
        // When
        List<OrderLine> orderLines = orderLineDao.findByOrderIds(-2, -1);

        // Then
        assertNotNull(orderLines);
        assertTrue(orderLines.isEmpty());
    }


    @Test
    public void findByOrderIds_whenAnExistentIdIsGiven_thenRelatedModelIsReturned() {
        // When
        List<OrderLine> orderLines = orderLineDao.findByOrderIds(orderLine1.getOrderId());

        // Then
        assertNotNull(orderLines);
        assertEquals(2, orderLines.size());
        assertThat(orderLines, contains(orderLine1, orderLine2));
    }


    @Test
    public void findByOrderIds_whenExistentIdsAreGiven_thenRelatedModelsAreReturned() {
        // When
        List<OrderLine> orderLines = orderLineDao.findByOrderIds(orderLine1.getOrderId(), orderLine2.getOrderId());

        // Then
        assertNotNull(orderLines);
        assertEquals(2, orderLines.size());
        assertThat(orderLines, contains(orderLine1, orderLine2));
    }


    @Test
    public void findByPizzaIds_whenNullIdsAreGiven_thenEmptyListIsReturned() {
        // When
        List<OrderLine> orderLines = orderLineDao.findByPizzaIds(null);

        // Then
        assertNotNull(orderLines);
        assertTrue(orderLines.isEmpty());
    }


    @Test
    public void findByPizzaIds_whenANonExistentIdsIsGiven_thenEmptyListIsReturned() {
        // When
        List<OrderLine> orderLines = orderLineDao.findByPizzaIds((short)-2, (short)-1);

        // Then
        assertNotNull(orderLines);
        assertTrue(orderLines.isEmpty());
    }


    @Test
    public void findByPizzaIds_whenAnExistentIdIsGiven_thenRelatedModelIsReturned() {
        // Given (information stored in test database)
        OrderLine orderLine3 = orderLineDao.findOptionalById(3).get();

        // When
        List<OrderLine> orderLines = orderLineDao.findByPizzaIds(orderLine1.getPizzaId());

        // Then
        assertNotNull(orderLines);
        assertEquals(2, orderLines.size());
        assertThat(orderLines, containsInAnyOrder(orderLine1, orderLine3));
    }


    @Test
    public void findByPizzaIds_whenExistentIdsAreGiven_thenRelatedModelsAreReturned() {
        // Given (information stored in test database)
        OrderLine orderLine3 = orderLineDao.findOptionalById(3).get();
        OrderLine orderLine4 = orderLineDao.findOptionalById(4).get();

        // When
        List<OrderLine> orderLines = orderLineDao.findByPizzaIds(orderLine1.getPizzaId(), orderLine2.getPizzaId());

        // Then
        assertNotNull(orderLines);
        assertEquals(4, orderLines.size());
        assertThat(orderLines, containsInAnyOrder(orderLine1, orderLine2, orderLine3, orderLine4));
    }

}
