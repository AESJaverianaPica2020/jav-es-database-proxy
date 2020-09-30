package co.edu.javeriana.eas.pica.toures.balon.services.impl;

import co.edu.javeriana.eas.pica.toures.balon.services.abs.ProviderConnectionAbs;
import org.springframework.stereotype.Service;

@Service
public class OracleProviderConnectionImpl extends ProviderConnectionAbs {

    public OracleProviderConnectionImpl() {
        this.prefixUrlConnection = "jdbc:oracle:thin:@";
        this.milestone = "ORACLE";
    }

}
