package com.zerobase.cms.order.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zerobase.cms.order.domain.model.Product;
import java.util.List;

import com.zerobase.cms.order.domain.model.QProduct;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QProduct qProduct;

    public ProductRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
        this.qProduct = QProduct.product;
    }

    @Override
    public List<Product> searchByName(String name) {
        return queryFactory
            .selectFrom(qProduct)
            .where(qProduct.name.like("%" + name + "%"))
            .fetch();
    }
}
