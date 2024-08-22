package ru.otr.learn.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chat")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(includeFieldNames = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Chat implements BaseEntity<Long> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Column(unique = true, nullable = false, length = 100)
	String name;

	@Builder.Default
	@OneToMany(mappedBy = "chat")
	List<UserChat> userChats = new ArrayList<>();
}
