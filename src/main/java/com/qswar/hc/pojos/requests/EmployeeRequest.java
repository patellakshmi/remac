package com.qswar.hc.pojos.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonProperty;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployeeRequest {

    @JsonProperty("govEmpId")
    private String govEmpId;

    @JsonProperty("username")
    private String username;

    @JsonProperty("name")
    private String name;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("email")
    private String email;

    @JsonProperty("position")
    private String position;

    @JsonProperty("authorised")
    private String authorised; // Maps to BOOLEAN DEFAULT TRUE

    @JsonProperty("department")
    private String department;

    @JsonProperty("userPhotoLink")
    private String userPhotoLink;
}
