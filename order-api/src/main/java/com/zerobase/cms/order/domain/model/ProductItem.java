package com.zerobase.cms.order.domain.model;

import com.zerobase.cms.order.domain.product.AddProductItemForm;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
