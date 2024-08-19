package ru.otr.learn.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "company")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Company implements BaseEntity<Long> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Column(nullable = false)
	String name;

	@Builder.Default
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "company_user",
			joinColumns = @JoinColumn(name = "company_id"),
			inverseJoinColumns = @JoinColumn(name = "user_id"))
	List<User> users = new ArrayList<>();
}
