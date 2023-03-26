package com.jpabook.jpashop.service;

import com.jpabook.jpashop.domain.Address;
import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.domain.Order;
import com.jpabook.jpashop.domain.OrderStatus;
import com.jpabook.jpashop.domain.exception.NotEnoughStockExcetion;
import com.jpabook.jpashop.domain.item.Book;
import com.jpabook.jpashop.domain.item.Item;
import com.jpabook.jpashop.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
public class OrderServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    @Rollback(false)
    public void 상품주문() throws Exception{
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "경기", "123-123"));
        em.persist(member);

        Book book = new Book();
        book.setName("시골 JPA");
        book.setPrice(10000);
        book.setStockQuantity(10);
        em.persist(book);

        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER, getOrder.getStatus());
        assertEquals("주문한 상품 종류 수가 정확해야 한다", 1, getOrder.getOrderItems().size());
        assertEquals("주문 가격은 가격 * 수량이다.", 20000, getOrder.getTotalPrice());
        assertEquals("상품 주문시 상태는 ORDER", 8, book.getStockQuantity());

    }

    @Test(expected = NotEnoughStockExcetion.class)
    public void 상품주문_재고수량초과() throws Exception{
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "경기", "123-123"));
        em.persist(member);

        Book book = new Book();
        book.setName("시골 JPA");
        book.setPrice(10000);
        book.setStockQuantity(10);
        em.persist(book);

        int orderCount = 11;

        orderService.order(member.getId(), book.getId(), orderCount);

        fail("재고 수량 부족 예외가 발생해야 한다.");

    }

    @Test
    public void 주문취소() throws Exception {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "경기", "123-123"));
        em.persist(member);

        Book book = new Book();
        book.setName("시골 JPA");
        book.setPrice(10000);
        book.setStockQuantity(10);
        em.persist(book);

        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        orderService.cancelOrder(orderId);

        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("주문 취소시 상태는 CANCEL 이다.", OrderStatus.CANCEL, getOrder.getStatus());
        assertEquals("주문이 취소된 상품은 그만큼 재고가 증가해야 한다.", 10, book.getStockQuantity());

    }

}