package graalvm.ikoafianando.model;

import java.util.UUID;

import graalvm.ikoafianando.validation.ValidationGroups;
import jakarta.validation.constraints.*;

public class IdentityModel {
    private UUID id;

    @NotBlank(message = "Name cannot be empty", groups = {ValidationGroups.Create.class})
    private String name;

    @NotNull(message = "Age cannot be empty", groups = {ValidationGroups.Create.class})
    @Min(value = 8, message = "Age must be greater than 0", groups = {ValidationGroups.Create.class})
    private int age;

    public IdentityModel() {
    }

    public IdentityModel(UUID id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public UUID getId() {
        return id;
    }

    public UUID setId() {
        return this.id = UUID.randomUUID();
    }

}
