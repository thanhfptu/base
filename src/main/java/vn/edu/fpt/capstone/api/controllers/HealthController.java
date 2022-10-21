package vn.edu.fpt.capstone.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.fpt.capstone.api.configs.AppConfig;
import vn.edu.fpt.capstone.api.responses.AppResponse;
import vn.edu.fpt.capstone.api.responses.BaseResponse;

@RestController
@Tag(name = "Health", description = "Heath Check")
@RequestMapping(AppConfig.V1_PATH + "/health")
public class HealthController {

    @GetMapping(value = "/ping")
    @Operation(summary = "Ping")
    public ResponseEntity<BaseResponse<String>> ping() {
        return AppResponse.success(BaseResponse.success("pong"));
    }

}
