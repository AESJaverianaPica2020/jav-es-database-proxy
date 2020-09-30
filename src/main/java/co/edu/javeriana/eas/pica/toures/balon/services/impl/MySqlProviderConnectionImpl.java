package co.edu.javeriana.eas.pica.toures.balon.services.impl;

import co.edu.javeriana.eas.pica.toures.balon.services.abs.ProviderConnectionAbs;
import org.springframework.stereotype.Service;

@Service
public class MySqlProviderConnectionImpl extends ProviderConnectionAbs {

    public MySqlProviderConnectionImpl() {
        this.prefixUrlConnection = "jdbc:mysql://";
        this.milestone = "MYSQL";
    }

}
