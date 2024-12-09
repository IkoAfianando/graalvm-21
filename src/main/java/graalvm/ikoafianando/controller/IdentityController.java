package graalvm.ikoafianando.controller;

import graalvm.ikoafianando.model.IdentityModel;
import graalvm.ikoafianando.service.IdentityService;
import graalvm.ikoafianando.validation.ValidationGroups;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController()
@RequestMapping("/api/identities")
public class IdentityController {
   private final IdentityService identityService;

    public IdentityController(IdentityService identityService) {
         this.identityService = identityService;
    }

    @PostMapping
    public ResponseEntity<IdentityModel> createIdentity(@Validated(ValidationGroups.Create.class)  @RequestBody IdentityModel identity) {
        IdentityModel identityModel = identityService.createIdentity(identity);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(identityModel.getId())
                .toUri();
        return ResponseEntity.created(location).body(identityModel);
    }

    @GetMapping
    public ResponseEntity<List<IdentityModel>> getAllIdentities() {
        return ResponseEntity.ok(identityService.getIdentity());
    }

    @GetMapping("/{name}")
    public ResponseEntity<IdentityModel> getIdentityByName(@PathVariable String name) {
        return identityService.getIdentityByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<IdentityModel> updateIdentity(@PathVariable UUID id, @RequestBody IdentityModel identity) {
        return identityService.updateIdentity(id, identity.getName(), identity.getAge())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIdentity(@PathVariable UUID id) {
        return identityService.deleteIdentity(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
