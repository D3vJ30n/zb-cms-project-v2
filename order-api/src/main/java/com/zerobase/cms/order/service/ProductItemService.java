package com.zerobase.cms.order.service;

import com.zerobase.cms.order.domain.model.Product;
import com.zerobase.cms.order.domain.model.ProductItem;
import com.zerobase.cms.order.domain.product.AddProductItemForm;
import com.zerobase.cms.order.domain.product.UpdateProductItemForm;
import com.zerobase.cms.order.domain.repository.ProductItemRepository;
import com.zerobase.cms.order.domain.repository.ProductRepository;
import com.zerobase.cms.order.exception.CustomException;
import com.zerobase.cms.order.exception.ErrorCode;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductItemService {
    private final ProductRepository productRepository;
    private final ProductItemRepository productItemRepository;

    @Transactional
    public ProductItem getProductItem(Long id) {
        return productItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("상품 아이템을 찾을 수 없습니다. id = " + id));
    }

    @Transactional
    public ProductItem addProductItem(Long sellerId, AddProductItemForm form) {
        Product product = productRepository.findById(form.getProductId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_PRODUCT));
        
        if (!product.getSellerId().equals(sellerId)) {
            throw new CustomException(ErrorCode.NOT_FOUND_PRODUCT);
        }
        
        ProductItem productItem = ProductItem.of(product, form);
        product.getProductItems().add(productItem);
        return productItemRepository.save(productItem);
    }

    @Transactional
    public ProductItem updateProductItem(Long sellerId, UpdateProductItemForm form) {
        ProductItem productItem = productItemRepository.findById(form.getId())
                .filter(pi -> pi.getSellerId().equals(sellerId))
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ITEM));
        productItem.setName(form.getName());
        productItem.setCount(form.getCount());
        productItem.setPrice(form.getPrice());
        return productItem;
    }

    @Transactional
    public void deleteProductItem(Long sellerId, Long productItemId) {
        ProductItem productItem = productItemRepository.findById(productItemId)
                .filter(pi -> pi.getSellerId().equals(sellerId))
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ITEM));
        productItemRepository.delete(productItem);
    }
}
