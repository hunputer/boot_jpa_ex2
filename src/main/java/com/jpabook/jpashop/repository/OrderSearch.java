package com.jpabook.jpashop.repository;

import com.jpabook.jpashop.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class OrderSearch {

    private String memberName;
    private OrderStatus orderStatus;
}
