package dao;

import domain.Reminder;

public interface reminder_dao {
    boolean add(Reminder reminder);
    boolean delete(String id);
}
