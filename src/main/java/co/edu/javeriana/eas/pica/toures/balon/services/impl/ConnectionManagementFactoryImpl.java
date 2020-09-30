package co.edu.javeriana.eas.pica.toures.balon.services.impl;

import co.edu.javeriana.eas.pica.toures.balon.enums.EngineType;
import co.edu.javeriana.eas.pica.toures.balon.services.IConnectionManagementFactory;
import co.edu.javeriana.eas.pica.toures.balon.services.IProviderConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConnectionManagementFactoryImpl implements IConnectionManagementFactory {

    private IProviderConnection mySqlProviderConnectionImpl;
    private IProviderConnection oracleProviderConnectionImpl;
    private IProviderConnection postgreSqlProviderConnectionImpl;
    private IProviderConnection sqlServerProviderConnectionImpl;

    @Override
    public IProviderConnection getConnectionManagement(EngineType engineType) {
        switch (engineType) {
            case ORACLE:
                return oracleProviderConnectionImpl;
            case POSTGRESQL:
                return postgreSqlProviderConnectionImpl;
            case SQL_SERVER:
                return sqlServerProviderConnectionImpl;
            default:
                return mySqlProviderConnectionImpl;
        }
    }

    @Autowired
    public void setMySqlProviderConnectionImpl(IProviderConnection mySqlProviderConnectionImpl) {
        this.mySqlProviderConnectionImpl = mySqlProviderConnectionImpl;
    }

    @Autowired
    public void setOracleProviderConnectionImpl(IProviderConnection oracleProviderConnectionImpl) {
        this.oracleProviderConnectionImpl = oracleProviderConnectionImpl;
    }

    @Autowired
    public void setPostgreSqlProviderConnectionImpl(IProviderConnection postgreSqlProviderConnectionImpl) {
        this.postgreSqlProviderConnectionImpl = postgreSqlProviderConnectionImpl;
    }

    @Autowired
    public void setSqlServerProviderConnectionImpl(IProviderConnection sqlServerProviderConnectionImpl) {
        this.sqlServerProviderConnectionImpl = sqlServerProviderConnectionImpl;
    }
}
