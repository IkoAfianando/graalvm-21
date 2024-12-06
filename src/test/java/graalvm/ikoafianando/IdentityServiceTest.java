package graalvm.ikoafianando;

import graalvm.ikoafianando.model.IdentityModel;
import graalvm.ikoafianando.service.IdentityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IdentityServiceTest {
    private IdentityService identityService;

    @BeforeEach
    public void setUp() {
        identityService = new IdentityService();
    }


    @Test
    public void testCreateIdentity() {
        IdentityModel identityModel = new IdentityModel();
        identityModel.setName("Iko Afianando");
        identityModel.setAge(17);
        identityService.createIdentity(identityModel);

        assertEquals("Iko Afianando", identityModel.getName());
        assertEquals(17, identityModel.getAge());
    }

    @Test
    public void testGetAllIdentity() {
        IdentityModel identityModel = new IdentityModel();
        identityModel.setName("Iko Afianando");
        identityModel.setAge(17);
        identityService.createIdentity(identityModel);

        assertEquals(1, identityService.getIdentity().size());
        assertEquals("Iko Afianando", identityService.getIdentity().getFirst().getName());
        assertEquals(17, identityService.getIdentity().getFirst().getAge());
    }

    @Test
    public void testUpdateIdentity() {
        IdentityModel identityModel = new IdentityModel();
        identityModel.setName("Iko Afianando");
        identityModel.setAge(17);
        identityService.createIdentity(identityModel);

        identityService.updateIdentity(identityModel.getId(), "Iko Afianando", 19);
        assertEquals(19, identityService.getIdentity().getFirst().getAge());
    }

    @Test
    public void testDeleteIdentity() {
        IdentityModel identityModel = new IdentityModel();
        identityModel.setName("Iko Afianando");
        identityModel.setAge(17);
        identityService.createIdentity(identityModel);

        identityService.deleteIdentity(identityModel.getId());
        assertEquals(0, identityService.getIdentity().size());
    }

    @Test
    public void testGetIdentityByName() {
        IdentityModel identityModel = new IdentityModel();
        identityModel.setName("Iko Afianando");
        identityModel.setAge(17);
        identityService.createIdentity(identityModel);

        assertEquals("Iko Afianando", identityService.getIdentityByName("Iko Afianando").get().getName());
    }

}
