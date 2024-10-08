package fr.traqueur.energylib.api.persistents;

import fr.traqueur.energylib.api.types.EnergyType;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;

/**
 * This class is used to save the EnergyType in a ItemStack
 */
public class EnergyTypePersistentDataType implements PersistentDataType<String, EnergyType> {

    /**
     * The instance of the class
     */
    public static final EnergyTypePersistentDataType INSTANCE = new EnergyTypePersistentDataType();

    /**
     * Get the primitive type of the class
     * @return the primitive type
     */
    @Override
    public Class<String> getPrimitiveType() {
        return String.class;
    }

    /**
     * Get the complex type of the class
     * @return the complex type
     */
    @Override
    public Class<EnergyType> getComplexType() {
        return EnergyType.class;
    }

    /**
     * Convert the EnergyType to a primitive type
     * @param energyType the EnergyType
     * @param persistentDataAdapterContext the context
     * @return the primitive type
     */
    @Override
    public String toPrimitive(EnergyType energyType, PersistentDataAdapterContext persistentDataAdapterContext) {
        return energyType.getName();
    }

    /**
     * Convert the primitive type to a EnergyType
     * @param s the primitive type
     * @param persistentDataAdapterContext the context
     * @return the EnergyType
     */
    
    @Override
    public EnergyType fromPrimitive(String s, PersistentDataAdapterContext persistentDataAdapterContext) {
        return EnergyType.TYPES.stream()
                .filter(type -> type.getName().equals(s))
                .findFirst()
                .orElseThrow();
    }
}
