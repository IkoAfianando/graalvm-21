package graalvm.ikoafianando.model;

import java.util.UUID;

public class IdentityModel {
    private UUID id;
    private String name;
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
