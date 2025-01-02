package com.zerobase.cms.order.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zerobase.cms.order.domain.model.Product;
import com.zerobase.cms.order.domain.model.QProduct;
import java.util.List;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QProduct qProduct;

    public ProductRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
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
