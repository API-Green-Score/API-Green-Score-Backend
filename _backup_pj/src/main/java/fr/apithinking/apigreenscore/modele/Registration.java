package fr.apithinking.apigreenscore.modele;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class Registration implements Serializable {

    private static final long serialVersionUID = -2831801471568186398L;

    @JsonProperty("registration_status")
    public TypeRegistrationStatus registrationStatus;

    private transient String registrationToken;
}
