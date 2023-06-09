package dao;

import domain.Reminder;

import java.util.List;

public interface reminder_dao {
    boolean add(Reminder reminder);
    boolean delete(String id);
    List<String> getMaxId();
}
