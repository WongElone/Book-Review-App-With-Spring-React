package com.example.demo.auditable;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.Instant;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class ReviewAuditable {
//    @CreatedBy
//    protected String createdBy;

//    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    @Column(nullable = false, updatable = false)
    protected Instant createdAt;

//    @LastModifiedBy
//    protected String lastModifiedBy;

//    @Temporal(TemporalType.TIMESTAMP)
//    @LastModifiedDate
//    protected Instant updatedAt;
}
