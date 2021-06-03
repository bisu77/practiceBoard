package com.practice.repository.util;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class QueryUtil {
    public static List<OrderSpecifier> getOrderSpecifier(Sort sort, Class<?> domainClass){
        List<OrderSpecifier> orders = new ArrayList<>();

        sort.stream().forEach(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String prop = order.getProperty();
            PathBuilder orderByExpression = new PathBuilder(domainClass, domainClass.getSimpleName().toLowerCase()+"1");
            orders.add(new OrderSpecifier(direction, orderByExpression.get(prop)));
        });

        for (OrderSpecifier order : orders) {
            System.out.println("order = " + order);
        }
        return orders;
    }
}
