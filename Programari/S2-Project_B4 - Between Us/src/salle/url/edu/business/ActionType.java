package salle.url.edu.business;

/**
 * Enumeration for witch action has a Room
 * NONE: The Room doesn't have any action
 * LOG: The Room has the action of consulting the logs table
 * CLASSIFICATION: The Room has the action of checking the classification table
 */
public enum ActionType {
    NONE,
    LOG,
    CLASSIFICATION;
}
