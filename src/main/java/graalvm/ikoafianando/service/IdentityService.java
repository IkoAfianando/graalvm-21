package graalvm.ikoafianando.service;

import graalvm.ikoafianando.model.IdentityModel;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class IdentityService {
    private final HashMap<UUID, IdentityModel> identityMap = new HashMap<>();

    //create
    public IdentityModel createIdentity(IdentityModel identity) {
        identityMap.put(identity.setId(), identity);
        return identity;
    }

    //update
    public Optional<IdentityModel> updateIdentity(UUID id, String name, int age) {
      if (identityMap.containsKey(id)) {
        IdentityModel identity = identityMap.get(id);
        identity.setName(name);
        identity.setAge(age);
        return Optional.of(identity);
      }
      return Optional.empty();
    }

    //delete
    public boolean deleteIdentity(UUID id) {
        return identityMap.remove(id) != null;
    }

    //read
    public List<IdentityModel> getIdentity() {
        return new ArrayList<>(identityMap.values());
    }

    // optional get identity by name
    public Optional<IdentityModel> getIdentityByName(String name) {
        return identityMap.values().stream()
                .filter(identity -> identity.getName().equals(name))
                .findFirst();
    }

}
