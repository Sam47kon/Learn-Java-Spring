package ru.otr.learn.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@NamedQueries(
		@NamedQuery(
				name = "User.findByNameAndAgeGreaterThanNamed",
				query = "select u from User u where u.name = :name and u.age > :age"
				//query = "select u from User u where u.name = ?1 and u.age > ?2"
		)
)

@Entity
@Table(name = "users")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(includeFieldNames = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User implements BaseEntity<Long> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@Column(nullable = false)
	String name;
	@Column(nullable = false)
	int age;

	@Builder.Default
	@Enumerated(EnumType.STRING)
	Role role = Role.DEV;

	@ToString.Exclude
	@Builder.Default
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	private Company company = null;

	@Builder.Default
	@OneToMany(mappedBy = "user")
	@ToString.Exclude
	List<UserChat> userChats = new ArrayList<>();

	public enum Role {
		DEV, MAN, QA;

		static final Random RANDOM = new Random();

		public static Role getRandom() {
			return values()[RANDOM.nextInt(values().length)];
		}
	}
}
