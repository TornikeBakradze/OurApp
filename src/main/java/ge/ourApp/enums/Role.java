package ge.ourApp.enums;

public enum Role {
    ADMIN,
    USER;

    public String getAuthority() {
        return this.name();
    }
}