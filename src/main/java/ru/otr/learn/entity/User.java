package ru.otr.learn.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.otr.learn.entity.chat.UserChat;

import java.time.LocalDate;
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
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString(includeFieldNames = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends AuditingEntity<Long> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Column(nullable = false, unique = true)
	String login;

	@Column(nullable = false)
	String name;

	@Column(nullable = false)
	int age;

	@Column(nullable = false)
	LocalDate birthDate;

	@Builder.Default
	@Enumerated(EnumType.STRING)
	Role role = Role.DEV;

	@ToString.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	private Company company;

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
