package model.user;

import java.io.Serializable;

// Represents a Data Transfer Object (DTO) for a user in the application.
public final class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    // Immutable fields holding user information
    private final String username; // Stores the unique username of the user
    private final String id; // Unique identifier for the user

    // Constructor to initialize UserDTO with the given username and id
    public UserDTO(String username, String id) {
        this.username = username;
        this.id = id;
    }

    // Returns the username of this user
    public String getUsername() {
        return username;
    }

    // Returns the unique identifier of this user
    public String getId() {
        return id;
    }


}
