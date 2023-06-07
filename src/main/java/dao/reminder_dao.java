package dao;

import domain.Reminder;

public interface reminder_dao {
    void add(Reminder reminder);
    void delete(String id);
}
