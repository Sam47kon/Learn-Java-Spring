package ru.otr.learn.dto;

import ru.otr.learn.entity.User;

import java.util.List;

public record CompanyDto(long id, String name, List<User> users) {
}
