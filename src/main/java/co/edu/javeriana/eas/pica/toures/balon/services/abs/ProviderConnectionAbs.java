package co.edu.javeriana.eas.pica.toures.balon.services.abs;

import co.edu.javeriana.eas.pica.toures.balon.enums.ProviderConnectionExceptionCode;
import co.edu.javeriana.eas.pica.toures.balon.exceptions.AbsProviderDatabaseProxyException;
import co.edu.javeriana.eas.pica.toures.balon.exceptions.impl.ProviderConnectionException;
import co.edu.javeriana.eas.pica.toures.balon.exceptions.impl.ProviderStatementExecuteException;
import co.edu.javeriana.eas.pica.toures.balon.services.IProviderConnection;
import co.edu.javeriana.eas.pica.toures.balon.dtos.ConnectionParameterDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.sql.*;
import java.util.*;

public class ProviderConnectionAbs implements IProviderConnection {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProviderConnectionAbs.class);

    private String timedOut;

    protected String prefixUrlConnection;
    protected String milestone;

    @Override
    public Connection establishConnection(UUID idProcess, ConnectionParameterDto parameters) throws AbsProviderDatabaseProxyException {
        LOGGER.info("[ID:{}] INICIANDO CONEXIÓN CON MOTOR [{}]", idProcess, milestone);
        String url = prefixUrlConnection.concat(parameters.getEndpoint());
        try {
            Properties properties = buildConnectionProperties(parameters);
            Connection connection = DriverManager.getConnection(url, properties);
            LOGGER.info("[ID:{}] CONEXIÓN ESTABLECIDA CORRECTAMENTE CON MOTOR [{}]", idProcess, milestone);
            return connection;
        } catch (SQLException ex) {
            LOGGER.error("Error estableciendo la conexión con el proveedor: ", ex);
            throw new ProviderConnectionException(ProviderConnectionExceptionCode.TPC_ESTABLISH_OR_CLOSEABLE_CONNECTION);
        }
    }

    @Override
    public JsonNode executeSelectStatement(UUID idProcess, Connection connection, String statement) throws AbsProviderDatabaseProxyException {
        LOGGER.info("[ID:{}] INICIA PROCESO DE EJECUCION DE CONSULTA CON MOTOR [{}]", idProcess, milestone);
        try (PreparedStatement query = connection.prepareStatement(statement)) {
            ResultSet resultSet = query.executeQuery();
            List<Map<String, Object>> rows = getRowsFromResultSet(idProcess, resultSet);
            LOGGER.info("[ID:{}] FINALIZA PROCESO DE EJECUCION DE CONSULTA CON MOTOR [{}]", idProcess, milestone);
            return serializeToJson(idProcess, rows);
        } catch (SQLException ex) {
            LOGGER.error(String.format("[ID:%s] Error ejecutando la sentencia de consulta con el proveedor: ", idProcess), ex);
            throw new ProviderStatementExecuteException(ProviderConnectionExceptionCode.STATEMENT_EXECUTE_FAILED);
        }
    }

    @Override
    public void executeUpdateStatement(UUID idProcess, Connection connection, String statement) throws AbsProviderDatabaseProxyException {
        LOGGER.info("[ID:{}] INICIA PROCESO DE EJECUCION DE ACTUALIZACIÓN *INSERT-UPDATE-DELETE* CON MOTOR [{}]", idProcess, milestone);
        try (PreparedStatement query = connection.prepareStatement(statement)) {
            query.executeUpdate();
            LOGGER.info("[ID:{}] FINALIZA PROCESO de EJECUCION DE ACTUALIZACIÓN *INSERT-UPDATE-DELETE* CON MOTOR [{}]", idProcess, milestone);
        } catch (SQLException ex) {
            LOGGER.error(String.format("[ID:%s] Error ejecutando la sentencia de actualización con el proveedor: ", idProcess), ex);
            throw new ProviderStatementExecuteException(ProviderConnectionExceptionCode.STATEMENT_EXECUTE_FAILED);
        }
    }

    @Override
    public void closeConnection(UUID idProcess, Connection connection) throws AbsProviderDatabaseProxyException {
        LOGGER.info("[ID:{}] CERRANDO CONEXIÓN CON MOTOR [{}]", idProcess, milestone);
        try {
            if (Objects.nonNull(connection) && !connection.isClosed()) {
                connection.close();
                LOGGER.info("[ID:{}] CONEXIÓN FINALIZADA CORRECTAMENTE CON MOTOR [{}]", idProcess, milestone);
            }
        } catch (SQLException ex) {
            LOGGER.error(String.format("[ID:%s] Error finalizando la conexión con el proveedor: ", idProcess), ex);
            throw new ProviderConnectionException(ProviderConnectionExceptionCode.TPC_ESTABLISH_OR_CLOSEABLE_CONNECTION);
        }
    }

    protected List<Map<String, Object>> getRowsFromResultSet(UUID idProcess, ResultSet resultSet) throws SQLException {
        LOGGER.info("[ID:{}] inicia proceso de mapeo de resultSet a lista por filas", idProcess);
        List<Map<String, Object>> rows = new ArrayList<>();
        ResultSetMetaData metadata = resultSet.getMetaData();
        int columnCount = metadata.getColumnCount();
        while (resultSet.next()) {
            Map<String, Object> row = new HashMap<>();
            for (int i = 1; i <= columnCount; i++) {
                String colName = metadata.getColumnName(i);
                Object colVal = resultSet.getObject(i);
                row.put(colName, colVal);
            }
            rows.add(row);
        }
        LOGGER.info("[ID:{}] finaliza proceso de mapeo de resultSet a lista por filas [ROWS:{}]", idProcess, rows.size());
        return rows;
    }

    protected JsonNode serializeToJson(UUID idProcess, List<Map<String, Object>> rows) {
        LOGGER.info("[ID:{}] inicia proceso de serialización a Json de resultado de consulta", idProcess);
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode objectNode = mapper.valueToTree(rows);
        LOGGER.info("[ID:{}] finaliza proceso de serialización a Json de resultado de consulta [JSON: {}]", idProcess, objectNode);
        return objectNode;
    }

    private Properties buildConnectionProperties(ConnectionParameterDto parameters) {
        String user = parameters.getUser();
        String password = parameters.getPassword();
        Properties properties = new Properties();
        properties.setProperty("user", user);
        properties.setProperty("password", password);
        properties.setProperty("connectTimeout", timedOut);
        return properties;
    }

    @Value("${proxy.sql.timedout.connection}")
    public void setTimedOut(String timedOut) {
        this.timedOut = timedOut;
    }

}
