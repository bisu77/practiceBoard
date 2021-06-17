package com.practice.repository.util;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.PathBuilderFactory;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class QueryUtil {
    /**
     * tableAlias : Q타입 별칭명
     *              ex) QMember 기본 static 확인 or 사용자 QMember 변수 확인
     * @param sort
     * @param domainClass
     * @param tableAlias
     * @return
     */
    public static List<OrderSpecifier> getOrderSpecifier(Sort sort, Class<?> domainClass, String tableAlias){
        List<OrderSpecifier> orders = new ArrayList<>();

        sort.stream().forEach(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String prop = order.getProperty();
            PathBuilder<?> orderByExpression = new PathBuilder<>(domainClass, tableAlias);
            //PathBuilder<?> orderByExpression = new PathBuilderFactory().create(domainClass);
            orders.add(new OrderSpecifier(direction, orderByExpression.get(prop)));
        });

        for (OrderSpecifier order : orders) {
            System.out.println("order = " + order);
        }
        return orders;
    }
}
