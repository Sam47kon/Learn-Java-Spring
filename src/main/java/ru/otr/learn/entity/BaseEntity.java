package ru.otr.learn.entity;

import java.io.Serializable;

public interface BaseEntity<Id extends Serializable> {
	Id getId();
}
