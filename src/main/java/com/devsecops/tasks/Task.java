package com.devsecops.tasks;

/** Une tache : identifiant, titre et etat (terminee ou non). */
public record Task(long id, String title, boolean done) {
}
