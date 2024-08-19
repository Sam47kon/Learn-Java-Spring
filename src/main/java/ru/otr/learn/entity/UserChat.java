package ru.otr.learn.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "users_chat")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(includeFieldNames = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserChat implements BaseEntity<Long> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "chat_id")
	Chat chat;

	public void setUser(User user) {
		this.user = user;
		this.user.getUserChats().add(this);
	}

	public void setChat(Chat chat) {
		this.chat = chat;
		this.chat.getUserChats().add(this);
	}
}
