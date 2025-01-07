package com.zerobase.cms.order.domain.model;

import com.zerobase.cms.order.domain.product.AddProductItemForm;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Audited
@AuditOverride(forClass = BaseEntity.class)
public class ProductItem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long sellerId;
    private String name;
    private Integer price;
    private Integer count;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    public static ProductItem of(Product product, AddProductItemForm form) {
        return ProductItem.builder()
                .sellerId(product.getSellerId())
                .name(form.getName())
                .price(form.getPrice())
                .count(form.getCount())
                .product(product)
                .build();
    }
} 