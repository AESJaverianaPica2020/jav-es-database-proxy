package co.edu.javeriana.eas.pica.toures.balon.dtos;

import co.edu.javeriana.eas.pica.toures.balon.enums.EngineType;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.UUID;

public class ProcessInfoDto {

    private EngineType engineType;
    private JsonNode parameter;
    private UUID idProcess;

    public EngineType getEngineType() {
        return engineType;
    }

    public void setEngineType(EngineType engineType) {
        this.engineType = engineType;
    }

    public JsonNode getParameter() {
        return parameter;
    }

    public void setParameter(JsonNode parameter) {
        this.parameter = parameter;
    }

    public UUID getIdProcess() {
        return idProcess;
    }

    public void setIdProcess(UUID idProcess) {
        this.idProcess = idProcess;
    }

}
