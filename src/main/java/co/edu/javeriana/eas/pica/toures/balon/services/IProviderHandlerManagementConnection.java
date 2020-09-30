package co.edu.javeriana.eas.pica.toures.balon.services;

import co.edu.javeriana.eas.pica.toures.balon.dtos.ProcessInfoDto;
import co.edu.javeriana.eas.pica.toures.balon.dtos.RequestBaseDto;
import co.edu.javeriana.eas.pica.toures.balon.exceptions.AbsProviderDatabaseProxyException;

public interface IProviderHandlerManagementConnection {

    RequestBaseDto executeProcess(ProcessInfoDto processInfoDto) throws AbsProviderDatabaseProxyException;

}
