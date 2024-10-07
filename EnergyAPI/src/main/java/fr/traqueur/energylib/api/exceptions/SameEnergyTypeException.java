package fr.traqueur.energylib.api.exceptions;

/**
 * Thrown when a player tries to add a component with energy type next to a component with different energy type.
 */
public class SameEnergyTypeException extends Exception {
    public SameEnergyTypeException() {}
}
