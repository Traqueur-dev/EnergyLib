package fr.traqueur.energylib.api.types;

import fr.traqueur.energylib.api.components.EnergyComponent;
import fr.traqueur.energylib.api.mechanics.*;

public enum MechanicTypes implements MechanicType {

    PRODUCER(EnergyProducer.class),
    CONSUMER(EnergyConsumer.class),
    STORAGE(EnergyStorage.class),
    TRANSPORTER(EnergyTransporter.class);

    private final Class<? extends EnergyMechanic> clazz;

    MechanicTypes(Class<? extends EnergyMechanic> clazz) {
        this.clazz = clazz;
    }

    public Class<? extends EnergyMechanic> getClazz() {
        return clazz;
    }

    public boolean isInstance(EnergyComponent<?> component) {
        return this.clazz.isInstance(component.getMechanic());
    }
}
