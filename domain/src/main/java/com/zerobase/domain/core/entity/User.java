package com.zerobase.domain.core.entity;

import com.zerobase.domain.api.common.UserType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Audited
@AuditOverride(forClass = BaseEntity.class)
@Table(name = "users")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private String email;
    
    private String password;
    private String name;
    private String phone;
    
    @Enumerated(EnumType.STRING)
    private UserType userType;
    
    private LocalDateTime verifyExpiredAt;
    private String verificationCode;
    private boolean verified;
}
