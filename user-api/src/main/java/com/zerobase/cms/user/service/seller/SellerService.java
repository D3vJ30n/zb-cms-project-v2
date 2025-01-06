package com.zerobase.cms.user.service.seller;

import com.zerobase.cms.user.domain.model.Seller;
import com.zerobase.cms.user.domain.repository.SellerRepository;
import com.zerobase.cms.user.exception.CustomException;
import com.zerobase.cms.user.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SellerService {
    private final SellerRepository sellerRepository;

    public Optional<Seller> findByIdAndEmail(Long id, String email) {
        return sellerRepository.findById(id)
                .filter(seller -> seller.getEmail().equals(email));
    }

    public Optional<Seller> findValidSeller(String email, String password) {
        return sellerRepository.findByEmail(email)
                .filter(seller -> seller.getPassword().equals(password) && seller.isVerify());
    }

    public Seller findByEmail(String email) {
        return sellerRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
    }

    @Transactional
    public void verifyEmail(String email, String code) {
        Seller seller = findByEmail(email);
        if (seller.isVerify()) {
            throw new CustomException(ErrorCode.ALREADY_VERIFIED);
        } else if (!seller.getVerificationCode().equals(code)) {
            throw new CustomException(ErrorCode.WRONG_VERIFICATION);
        } else if (seller.getVerifyExpiredAt().isBefore(LocalDateTime.now())) {
            throw new CustomException(ErrorCode.EXPIRED_CODE);
        }
        seller.setVerify(true);
    }
}
