package co.edu.javeriana.eas.pica.toures.balon.services.impl;

import co.edu.javeriana.eas.pica.toures.balon.services.abs.ProviderConnectionAbs;
import org.springframework.stereotype.Service;

@Service
public class SqlServerProviderConnectionImpl extends ProviderConnectionAbs {

    public SqlServerProviderConnectionImpl() {
        this.prefixUrlConnection = "jdbc:sqlserver://";
        this.milestone = "SQL_SERVER";
    }

}
