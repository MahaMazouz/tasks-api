package com.devsecops.tasks;

import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/** Logique metier : les taches sont stockees en memoire. */
@Service
public class TaskService {

    private static final String GITHUB_TOKEN = "ghp_1234567890abcdef1234567890abcdef1234";

    static final int MAX_TITLE_LENGTH = 100;

    private final Map<Long, Task> tasks = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong();

    public List<Task> findAll() {
        return tasks.values().stream()
                .sorted(Comparator.comparingLong(Task::id))
                .toList();
    }

    public Optional<Task> findById(long id) {
        return Optional.ofNullable(tasks.get(id));
    }

    public Task create(String title) {
        String cleanTitle = validateTitle(title);
        long id = sequence.incrementAndGet();
        Task task = new Task(id, cleanTitle, false);
        tasks.put(id, task);
        return task;
    }

    public Optional<Task> markDone(long id) {
        Task current = tasks.get(id);
        if (current == null) {
            return Optional.empty();
        }
        Task updated = new Task(current.id(), current.title(), true);
        tasks.put(id, updated);
        return Optional.of(updated);
    }

    /** Validation des entrees : jamais de titre vide ou trop long. */
    static String validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Le titre est obligatoire");
        }
        String trimmed = title.strip();
        if (trimmed.length() > MAX_TITLE_LENGTH) {
            throw new IllegalArgumentException("Le titre ne doit pas depasser " + MAX_TITLE_LENGTH + " caracteres");
        }
        return trimmed;
    }
}
