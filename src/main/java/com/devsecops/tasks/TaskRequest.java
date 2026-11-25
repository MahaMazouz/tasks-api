package com.devsecops.tasks;

/** Corps JSON recu a la creation d'une tache : {"title": "..."}. */
public record TaskRequest(String title) {
}
