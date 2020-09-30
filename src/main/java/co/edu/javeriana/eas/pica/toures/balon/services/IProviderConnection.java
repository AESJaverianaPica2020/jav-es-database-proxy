package co.edu.javeriana.eas.pica.toures.balon.services;

import co.edu.javeriana.eas.pica.toures.balon.dtos.ConnectionParameterDto;
import co.edu.javeriana.eas.pica.toures.balon.exceptions.AbsProviderDatabaseProxyException;
import com.fasterxml.jackson.databind.JsonNode;

import java.sql.Connection;
import java.util.UUID;

public interface IProviderConnection {

    Connection establishConnection(UUID idProcess, ConnectionParameterDto parameters) throws AbsProviderDatabaseProxyException;

    JsonNode executeSelectStatement(UUID idProcess, Connection connection, String statement) throws AbsProviderDatabaseProxyException;

    void executeUpdateStatement(UUID idProcess, Connection connection, String statement) throws AbsProviderDatabaseProxyException;

    void closeConnection(UUID idProcess, Connection connection) throws AbsProviderDatabaseProxyException;

}
