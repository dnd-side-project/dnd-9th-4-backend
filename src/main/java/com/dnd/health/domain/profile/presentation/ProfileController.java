package com.dnd.health.domain.profile.presentation;

import com.dnd.health.domain.profile.application.ProfileService;
import com.dnd.health.domain.profile.presentation.dto.*;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profile")
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping
    public ResponseEntity<Void> register(@RequestBody ProfileRegisterRequest profileRequest) {
        profileService.save(profileRequest.toCommand());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping
    public ResponseEntity<ProfileResponse> getProfile(@RequestBody ProfileRequest profileRequest) {
        ProfileResponse profileResponse = profileService.getProfile(profileRequest.getMemberId());
        return ResponseEntity.ok(profileResponse);
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody ProfileUpdateRequest profileRequest) {
        profileService.update(profileRequest.toCommand());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/around")
    public ResponseEntity<List<MateResponse>> getMatesAround(@RequestBody ProfileRequest profileRequest) {
        List<MateResponse> mateResponses = profileService.getMatesAround(profileRequest.getMemberId());
        return ResponseEntity.ok(mateResponses);
    }
}
