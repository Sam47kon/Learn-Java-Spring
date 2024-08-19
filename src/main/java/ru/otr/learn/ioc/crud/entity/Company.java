package ru.otr.learn.ioc.crud.entity;


import java.util.List;

public record Company(String name, List<User> users) {
}
