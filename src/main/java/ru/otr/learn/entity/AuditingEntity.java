package ru.otr.learn.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.Instant;

@Setter
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditingEntity<T extends Serializable> implements BaseEntity<T> {

	@CreatedDate
	private Instant createdAt;
	@LastModifiedDate
	private Instant updatedAt;
	@CreatedBy
	private String createdBy;
	@LastModifiedBy
	private String updatedBy;

	@Override
	public String toString() {
		return "{createdAt=" + createdAt +
				", updatedAt=" + updatedAt +
				", createdBy=" + createdBy +
				", updatedBy=" + updatedBy
				+ "}";
	}
}


