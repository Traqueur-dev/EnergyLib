package fr.traqueur.energylib.api.mechanics;

/**
 * This interface is used to represent an object that can store energy.
 * It is used to represent a battery, a capacitor, a power cell, etc.
 * It is used to store energy and to provide energy to other objects.
 */
public interface EnergyStorage  extends EnergyMechanic {

    /**
     * Returns the maximum amount of energy that can be stored.
     * @return the maximum amount of energy that can be stored
     */
    double getMaximumCapacity();

    /**
     * Returns the amount of energy that can be stored.
     * @return the amount of energy that can be stored
     */
    default double getAvailableCapacity() {
        return getMaximumCapacity() - getStoredEnergy();
    }

    /**
     * Stores the given amount of energy.
     * @param energyStored the amount of energy to store
     * @return the amount of energy that could be stored
     */
    double storeEnergy(double energyStored);

    /**
     * Returns the amount of energy stored.
     * @return the amount of energy stored
     */
    double getStoredEnergy();

    /**
     * Consumes the given amount of energy.
     * @param energyTaken the amount of energy to consume
     * @return the amount of energy that could be consumed
     */
    double consumeEnergy(double energyTaken);
}
