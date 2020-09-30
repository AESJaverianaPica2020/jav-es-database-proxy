package co.edu.javeriana.eas.pica.toures.balon.dtos;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.UUID;

public class RequestBaseDto {

    private UUID idProcess;
    private JsonNode details;

    public UUID getIdProcess() {
        return idProcess;
    }

    public void setIdProcess(UUID idProcess) {
        this.idProcess = idProcess;
    }

    public JsonNode getDetails() {
        return details;
    }

    public void setDetails(JsonNode details) {
        this.details = details;
    }

}
