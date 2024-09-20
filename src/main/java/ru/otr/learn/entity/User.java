package ru.otr.learn.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.ColumnDefault;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.GrantedAuthority;
import ru.otr.learn.entity.chat.UserChat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

	public static final String DEFAULT_PASSWORD = "{noop}1234";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Column(nullable = false, unique = true)
	String username;

	@Builder.Default
	@ColumnDefault(value = '\'' + DEFAULT_PASSWORD + '\'')
	String password = DEFAULT_PASSWORD;

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

	@PrePersist
	public void prePersist() {
		if (null == password) {
			password = DEFAULT_PASSWORD;
		}
	}

	public enum Role  implements GrantedAuthority {
		ADMIN, DEV, MAN, QA;

		@Contract(pure = true)
		@Override
		public @NotNull String getAuthority() {
			return name();
		}
	}
}
