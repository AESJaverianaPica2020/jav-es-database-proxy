package co.edu.javeriana.eas.pica.toures.balon.services;

import co.edu.javeriana.eas.pica.toures.balon.enums.EngineType;

public interface IConnectionManagementFactory {

    IProviderConnection getConnectionManagement(EngineType engineType);

}
