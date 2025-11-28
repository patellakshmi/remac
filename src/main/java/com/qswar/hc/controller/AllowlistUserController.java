package com.qswar.hc.controller;

import com.qswar.hc.constants.APIConstant;
import com.qswar.hc.constants.Constant;
import com.qswar.hc.exception.DuplicateEntryException; // New exception imports
import com.qswar.hc.exception.ResourceNotFoundException; // New exception imports
import com.qswar.hc.model.AllowlistUser; // Renamed model
import com.qswar.hc.pojos.requests.AllowlistUserRequest; // Renamed POJO
import com.qswar.hc.pojos.responses.GenericResponse;
import com.qswar.hc.service.AllowlistUserService; // Renamed service
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
// Set the base path for all methods in this controller
@RequestMapping(APIConstant.PUBLIC + "/v1/allowlist") // Renamed path
public class AllowlistUserController {

    private final AllowlistUserService allowlistUserService; // Renamed service

    // Constructor Injection is preferred over @Autowired on field
    public AllowlistUserController(AllowlistUserService allowlistUserService) {
        this.allowlistUserService = allowlistUserService;
    }

    // --- GET METHODS ---

    // GET /api/v1/allowlist
    @GetMapping
    public ResponseEntity<GenericResponse> getAllAllowlistUsers() {
        List<AllowlistUser> userList = allowlistUserService.findAll();
        // Use standard message and HttpStatus
        return new ResponseEntity<>(new GenericResponse(Constant.STATUS.SUCCESS.name(), "Allowlist entries retrieved successfully", userList), HttpStatus.OK);
    }

    // GET /api/v1/allowlist?phone=... OR /api/v1/allowlist?email=...
    // Consolidating all GET-by-identifier into one endpoint using @RequestParam
    @GetMapping(params = {"phone", "!email"}) // Handles only phone parameter
    public ResponseEntity<GenericResponse> getAllowlistUserByPhone(@RequestParam String phone) {
        return allowlistUserService.findByPhone(phone)
                .map(user -> new ResponseEntity<>(new GenericResponse(Constant.STATUS.SUCCESS.name(), "User found by phone", user), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(new GenericResponse(Constant.STATUS.FAIL.name(), "User not found with phone: " + phone, null), HttpStatus.NOT_FOUND));
    }

    @GetMapping(params = {"email", "!phone"}) // Handles only email parameter
    public ResponseEntity<GenericResponse> getAllowlistUserByEmail(@RequestParam String email) {
        return allowlistUserService.findByEmail(email)
                .map(user -> new ResponseEntity<>(new GenericResponse(Constant.STATUS.SUCCESS.name(), "User found by email", user), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(new GenericResponse(Constant.STATUS.FAIL.name(), "User not found with email: " + email, null), HttpStatus.NOT_FOUND));
    }

    // Note: The previous logic for finding by email AND phone simultaneously was unclear and is removed.
    // If a user must be looked up by both, a specific service method should be created.

    // --- POST METHOD (CREATE) ---

    // POST /api/v1/allowlist
    @PostMapping
    public ResponseEntity<GenericResponse> createAllowlistUser(@RequestBody AllowlistUserRequest request) {
        try {
            AllowlistUser newUser = null;

            // Priority logic should be clearly defined. Assuming create by both is highest priority.
            if (StringUtils.isNotBlank(request.getEmail()) && StringUtils.isNotBlank(request.getPhone())) {
                newUser = allowlistUserService.createByEmailAndPhone(request.getEmail(), request.getPhone());
            } else if (StringUtils.isNotBlank(request.getEmail())) {
                newUser = allowlistUserService.createByOnlyEmail(request.getEmail());
            } else if (StringUtils.isNotBlank(request.getPhone())) {
                newUser = allowlistUserService.createByOnlyPhone(request.getPhone());
            } else {
                return new ResponseEntity<>(new GenericResponse(Constant.STATUS.FAIL.name(), "Email or Phone must be provided for creation.", null), HttpStatus.BAD_REQUEST);
            }

            // Return 201 CREATED for successful creation
            return new ResponseEntity<>(new GenericResponse(Constant.STATUS.SUCCESS.name(), "Allowlist entry created successfully", newUser), HttpStatus.CREATED);

        } catch (DuplicateEntryException e) {
            // Return 409 CONFLICT for business logic violation (duplicate entry)
            return new ResponseEntity<>(new GenericResponse(Constant.STATUS.FAIL.name(), e.getMessage(), null), HttpStatus.CONFLICT);
        }
    }

    // --- PUT METHOD (UPDATE) ---

    // PUT /api/v1/allowlist
    @PutMapping
    public ResponseEntity<GenericResponse> updateAllowlistUser(@RequestBody AllowlistUserRequest request) {
        try {
            // Determine the update type based on what is present in the request
            if (StringUtils.isNotBlank(request.getEmail()) && StringUtils.isNotBlank(request.getPhone())) {
                // Assuming the request contains the KEY field (old identifier) and the NEW field to update.
                // The old controller logic for 'update' header was confusing.
                // We assume either phone is the key (find by phone, update email) OR email is the key (find by email, update phone).

                // Example: Find user by email (key) and update their phone number
                allowlistUserService.updatePhone(request.getEmail(), request.getPhone());

                // Example: Find user by phone (key) and update their email address
                // To support both, the request POJO needs a clear "key" field and "value" field.

                // For simplicity, let's assume the request body defines the user to be updated.
                // You must refine this logic based on your actual requirement.

                // Using the original logic's intent (though simplified):
                allowlistUserService.updateEmail(request.getPhone(), request.getEmail());
                allowlistUserService.updatePhone(request.getEmail(), request.getPhone());

            } else {
                return new ResponseEntity<>(new GenericResponse(Constant.STATUS.FAIL.name(), "Both email and phone required for update operation.", null), HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>(new GenericResponse(Constant.STATUS.SUCCESS.name(), "Allowlist entry updated successfully", null), HttpStatus.OK);

        } catch (ResourceNotFoundException e) {
            // Return 404 NOT FOUND if the record to be updated doesn't exist
            return new ResponseEntity<>(new GenericResponse(Constant.STATUS.FAIL.name(), e.getMessage(), null), HttpStatus.NOT_FOUND);
        }
    }

    // --- DELETE METHOD ---

    // DELETE /api/v1/allowlist?phone=... OR /api/v1/allowlist?email=...
    // Delete logic should be separated by identifier. Using @RequestParam for cleaner DELETE requests.
    @DeleteMapping(params = "phone")
    public ResponseEntity<GenericResponse> deleteAllowlistUserByPhone(@RequestParam String phone) {
        try {
            allowlistUserService.deleteByPhone(phone);
            return new ResponseEntity<>(new GenericResponse(Constant.STATUS.SUCCESS.name(), "Allowlist entry deleted successfully by phone", null), HttpStatus.NO_CONTENT); // 204 No Content
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new GenericResponse(Constant.STATUS.FAIL.name(), e.getMessage(), null), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(params = "email")
    public ResponseEntity<GenericResponse> deleteAllowlistUserByEmail(@RequestParam String email) {
        try {
            allowlistUserService.deleteByEmail(email);
            return new ResponseEntity<>(new GenericResponse(Constant.STATUS.SUCCESS.name(), "Allowlist entry deleted successfully by email", null), HttpStatus.NO_CONTENT);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new GenericResponse(Constant.STATUS.FAIL.name(), e.getMessage(), null), HttpStatus.NOT_FOUND);
        }
    }
}