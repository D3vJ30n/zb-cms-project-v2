package com.zerobase.cms.user.domain.model;

import com.zerobase.cms.user.domain.SignUpForm;
import java.time.LocalDateTime;
import java.util.Locale;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.AuditOverride;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@AuditOverride(forClass = BaseEntity.class) // createdAt, UpdatedAt 자동 변경 위함.
public class Seller extends BaseEntity{
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String name;
    private String password;
    private String phone;
    private LocalDateTime verifyExpiredAt;
    private String verificationCode;
    private boolean verify;

    public static Seller from(SignUpForm form) {
        return Seller.builder()
                .email(form.getEmail())
                .name(form.getName())
                .password(form.getPassword())
                .phone(form.getPhone())
                .verify(false)
                .build();
    }
}
