package fr.traqueur.energylib.tests;

import fr.traqueur.commands.api.CommandManager;
import fr.traqueur.energylib.EnergyLib;
import fr.traqueur.energylib.api.types.ComponentsTypes;

public class EnergyTest {

    public EnergyTest(EnergyLib energyLib) {
        CommandManager commandManager = new CommandManager(energyLib);

        commandManager.registerConverter(ComponentsTypes.class,"component-type", new ComponentsTypeConverter());

        commandManager.registerCommand(new EnergyCommand(energyLib));
    }

}
