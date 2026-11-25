package com.devsecops.tasks;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TaskServiceTest {

    private final TaskService service = new TaskService();

    @Test
    void createAddsTask() {
        Task task = service.create("Configurer Trivy");

        assertThat(task.id()).isEqualTo(1L);
        assertThat(task.done()).isFalse();
        assertThat(service.findAll()).hasSize(1);
    }

    @Test
    void createTrimsTitle() {
        assertThat(service.create("  Lire le rapport  ").title()).isEqualTo("Lire le rapport");
    }

    @Test
    void createRejectsInvalidTitle() {
        assertThatThrownBy(() -> service.create("  ")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> service.create(null)).isInstanceOf(IllegalArgumentException.class);
        String tooLong = "a".repeat(TaskService.MAX_TITLE_LENGTH + 1);
        assertThatThrownBy(() -> service.create(tooLong)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void markDoneUpdatesTask() {
        Task task = service.create("Pousser l'image");

        assertThat(service.markDone(task.id())).hasValueSatisfying(t -> assertThat(t.done()).isTrue());
        assertThat(service.findById(task.id())).hasValueSatisfying(t -> assertThat(t.done()).isTrue());
    }

    @Test
    void markDoneOnUnknownTaskReturnsEmpty() {
        assertThat(service.markDone(42L)).isEmpty();
    }
}
