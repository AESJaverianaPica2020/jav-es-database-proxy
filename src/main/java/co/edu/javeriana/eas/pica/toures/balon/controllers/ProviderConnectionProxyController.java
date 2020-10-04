package co.edu.javeriana.eas.pica.toures.balon.controllers;

import co.edu.javeriana.eas.pica.toures.balon.dtos.ProcessInfoDto;
import co.edu.javeriana.eas.pica.toures.balon.dtos.RequestBaseDto;
import co.edu.javeriana.eas.pica.toures.balon.enums.EngineType;
import co.edu.javeriana.eas.pica.toures.balon.exceptions.AbsProviderDatabaseProxyException;
import co.edu.javeriana.eas.pica.toures.balon.services.IProviderHandlerManagementConnection;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin("*")
@RestController
@RequestMapping("/V1/Utility/db-proxy")
public class ProviderConnectionProxyController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProviderConnectionProxyController.class);

    private IProviderHandlerManagementConnection providerHandlerManagementConnection;

    @PostMapping("provider")
    public ResponseEntity<RequestBaseDto> validateConnection(@RequestHeader("X-Engine") EngineType engineType,
                                                             @RequestBody JsonNode parameters) {
        UUID uuid = UUID.randomUUID();
        LOGGER.info("INICIA PROCESO DE INTEGRACIÓN CON PROVEEDOR [ID:{}]", uuid);
        ProcessInfoDto processInfoDto = new ProcessInfoDto();
        processInfoDto.setEngineType(engineType);
        processInfoDto.setIdProcess(uuid);
        processInfoDto.setParameter(parameters);
        try {
            RequestBaseDto requestBaseDto = providerHandlerManagementConnection.executeProcess(processInfoDto);
            return new ResponseEntity<>(requestBaseDto, HttpStatus.OK);
        } catch (AbsProviderDatabaseProxyException ex) {
            return new ResponseEntity<>(ex.getExceptionCode().getCode());
        }
    }

    @Autowired
    public void setProviderHandlerManagementConnection(IProviderHandlerManagementConnection providerHandlerManagementConnection) {
        this.providerHandlerManagementConnection = providerHandlerManagementConnection;
    }
}
