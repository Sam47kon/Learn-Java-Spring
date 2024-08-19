package ru.otr.learn.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

	@Column(nullable = true)
	@Enumerated(EnumType.STRING)
	Role role;

	/*@ManyToMany(mappedBy = "users")
	private List<Company> companies;*/

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
