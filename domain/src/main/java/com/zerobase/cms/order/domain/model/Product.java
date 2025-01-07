package com.zerobase.cms.order.domain.model;

import com.zerobase.cms.order.domain.product.AddProductForm;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Audited
@AuditOverride(forClass = BaseEntity.class)
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long sellerId;
    private String name;
    private String description;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    @Builder.Default
    private List<ProductItem> productItems = new ArrayList<>();

    public static Product of(Long sellerId, AddProductForm form) {
        Product product = Product.builder()
                .sellerId(sellerId)
                .name(form.getName())
                .description(form.getDescription())
                .build();
                
        product.setProductItems(form.getItems().stream()
                .map(piForm -> ProductItem.builder()
                        .sellerId(sellerId)
                        .name(piForm.getName())
                        .price(piForm.getPrice())
                        .count(piForm.getCount())
                        .product(product)
                        .build())
                .collect(Collectors.toList()));
                
        return product;
    }
} 