package vn.edu.fpt.capstone.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.capstone.api.configs.AppConfig;
import vn.edu.fpt.capstone.api.requests.OTPAuthenticationRequest;
import vn.edu.fpt.capstone.api.requests.OTPRequest;
import vn.edu.fpt.capstone.api.responses.AppResponse;
import vn.edu.fpt.capstone.api.responses.BaseResponse;
import vn.edu.fpt.capstone.api.responses.OTPAuthenticationResponse;
import vn.edu.fpt.capstone.api.responses.OTPResponse;
import vn.edu.fpt.capstone.api.services.otp.OTPService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(OTPController.PATH)
public class OTPController {

    public static final String PATH = AppConfig.V1_PATH + "/otp";

    private final OTPService otpService;

    @PostMapping("/generate")
    public ResponseEntity<BaseResponse<OTPResponse>> generate(@Valid @RequestBody OTPRequest request) {
        return AppResponse.success(otpService.generateAndSend(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<BaseResponse<OTPAuthenticationResponse>> authenticate(@Valid @RequestBody OTPAuthenticationRequest request) {
        return AppResponse.success(otpService.authenticate(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<OTPResponse>> get(@PathVariable("id") Long id) {
        return AppResponse.success(otpService.get(id));
    }

}
