package co.edu.javeriana.eas.pica.toures.balon.services.impl;

import co.edu.javeriana.eas.pica.toures.balon.dtos.ConnectionParameterDto;
import co.edu.javeriana.eas.pica.toures.balon.dtos.ProcessInfoDto;
import co.edu.javeriana.eas.pica.toures.balon.dtos.RequestBaseDto;
import co.edu.javeriana.eas.pica.toures.balon.enums.ProviderConnectionExceptionCode;
import co.edu.javeriana.eas.pica.toures.balon.exceptions.AbsProviderDatabaseProxyException;
import co.edu.javeriana.eas.pica.toures.balon.exceptions.impl.ProviderConnectionException;
import co.edu.javeriana.eas.pica.toures.balon.services.IConnectionManagementFactory;
import co.edu.javeriana.eas.pica.toures.balon.services.IProviderConnection;
import co.edu.javeriana.eas.pica.toures.balon.services.IProviderHandlerManagementConnection;
import co.edu.javeriana.eas.pica.toures.balon.utilities.Base64Utility;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.Objects;
import java.util.UUID;

@Service
public class ProviderHandlerManagementConnectionImpl implements IProviderHandlerManagementConnection {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProviderHandlerManagementConnectionImpl.class);

    private IConnectionManagementFactory connectionManagementFactory;

    @Override
    public RequestBaseDto executeProcess(ProcessInfoDto processInfoDto) throws AbsProviderDatabaseProxyException {
        UUID idProcess = processInfoDto.getIdProcess();
        LOGGER.info("[ID:{}] INICIA EJECUCIÓN DE HANDLER DE PROCESO.", idProcess);
        JsonNode parameters = processInfoDto.getParameter();
        validateParameters(idProcess, parameters);
        RequestBaseDto requestBaseDto = handlerStatementExecute(idProcess, processInfoDto);
        LOGGER.info("[ID:{}] FINALIZA EJECUCIÓN DE HANDLER DE PROCESO.", idProcess);
        return requestBaseDto;
    }

    private RequestBaseDto handlerStatementExecute(UUID idProcess, ProcessInfoDto processInfoDto) throws AbsProviderDatabaseProxyException {
        LOGGER.info("[ID:{}] inicia ejecución de handler.", idProcess);
        RequestBaseDto requestBaseDto = new RequestBaseDto();
        requestBaseDto.setIdProcess(idProcess);
        JsonNode parameters = processInfoDto.getParameter();
        IProviderConnection providerConnection = connectionManagementFactory.getConnectionManagement(processInfoDto.getEngineType());
        ConnectionParameterDto connectionParameterDto = buildParameterConnection(idProcess, parameters);
        Connection connection = providerConnection.establishConnection(idProcess, connectionParameterDto);
        String type = parameters.get("statement").get("type").textValue().toUpperCase();
        try {
            switch (type) {
                case "SELECT":
                    JsonNode response = providerConnection.executeSelectStatement(idProcess, connection,
                            Base64Utility.decodeString(parameters.get("statement").get("value").textValue()));
                    requestBaseDto.setDetails(response);
                    break;
                case "UPDATE":
                    providerConnection.executeUpdateStatement(idProcess, connection,
                            Base64Utility.decodeString(parameters.get("statement").get("value").textValue()));
                    break;
                default:
                    throw new ProviderConnectionException(ProviderConnectionExceptionCode.INVALID_PARAMETERS);
            }
        } finally {
            providerConnection.closeConnection(idProcess, connection);
        }
        LOGGER.info("[ID:{}] finaliza ejecución de handler.", idProcess);
        return requestBaseDto;
    }

    private void validateParameters(UUID idProcess, JsonNode parameters) throws AbsProviderDatabaseProxyException {
        LOGGER.info("[ID:{}] inicia validación de parametros de entrada.", idProcess);
        validateParameter(parameters.get("endpoint"));
        validateParameter(parameters.get("user"));
        validateParameter(parameters.get("password"));
        validateParameter(parameters.get("statement"));
        LOGGER.info("[ID:{}] finaliza validación de parametros de entrada.", idProcess);
    }

    private void validateParameter(Object parameter) throws ProviderConnectionException {
        if (Objects.isNull(parameter)) {
            throw new ProviderConnectionException(ProviderConnectionExceptionCode.INVALID_PARAMETERS);
        }
    }

    private ConnectionParameterDto buildParameterConnection(UUID idProcess, JsonNode parameters) {
        LOGGER.info("[ID:{}] inicia contrucción de parametros de conexión.", idProcess);
        String endpoint = Base64Utility.decodeString(parameters.get("endpoint").textValue());
        String user = Base64Utility.decodeString(parameters.get("user").textValue());
        String password = Base64Utility.decodeString(parameters.get("password").textValue());
        ConnectionParameterDto connectionParameterDto = new ConnectionParameterDto();
        connectionParameterDto.setEndpoint(endpoint);
        connectionParameterDto.setUser(user);
        connectionParameterDto.setPassword(password);
        LOGGER.info("[ID:{}] finaliza contrucción de parametros de conexión.", idProcess);
        return connectionParameterDto;
    }

    @Autowired
    public void setConnectionManagementFactory(IConnectionManagementFactory connectionManagementFactory) {
        this.connectionManagementFactory = connectionManagementFactory;
    }
}
