package ru.course.b2b.service;

import javafx.collections.ObservableList;
import ru.course.b2b.HistoryRecord;
import ru.course.b2b.data.HistoryRepository;

public class HistoryService {

    public ObservableList<HistoryRecord> getHistory() {
        return HistoryRepository.getHistory();
    }

    public void addRecord(
            HistoryRecord record
    ) {
        HistoryRepository.addRecord(record);
    }
}