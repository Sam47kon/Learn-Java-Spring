package ru.otr.learn.entity;

import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class BaseEntity {
	protected long id;

	@Override
	public String toString() {
		return "{id=" + id + '}';
	}
}
