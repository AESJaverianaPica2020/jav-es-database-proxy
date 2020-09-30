package co.edu.javeriana.eas.pica.toures.balon.services.impl;

import co.edu.javeriana.eas.pica.toures.balon.services.abs.ProviderConnectionAbs;
import org.springframework.stereotype.Service;

@Service
public class PostgreSqlProviderConnectionImpl extends ProviderConnectionAbs {

    public PostgreSqlProviderConnectionImpl() {
        this.prefixUrlConnection = "jdbc:postgresql://";
        this.milestone = "POSTGRESQL";
    }

}
