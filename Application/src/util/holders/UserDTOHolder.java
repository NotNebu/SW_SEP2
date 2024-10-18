package util.holders;

import model.user.UserDTO;

// Manages a singleton instance of the current user details.
public class UserDTOHolder {
    private static UserDTO userDTO;

    // Returns the current UserDTO in a synchronized manner.
    public synchronized static UserDTO getUserDTO() {
        return userDTO;
    }

    // Sets the UserDTO instance.
    public synchronized static void setUserDTO(UserDTO userDTO) {
        UserDTOHolder.userDTO = userDTO;
    }

    // Sets the UserDTO using individual fields.
    public synchronized static void setUserDTO(String username, String id) {
        UserDTOHolder.userDTO = new UserDTO(username, id);
    }

    // Clears the stored UserDTO reference.
    public synchronized static void clearUserDTO() {
        userDTO = null;
    }

    // Checks if a UserDTO is set.
    public synchronized static boolean hasUserDTO() {
        return userDTO != null;
    }

    // Retrieves the current username.
    public synchronized static String getUsername() {
        return userDTO.getUsername();
    }

    // Retrieves the current user ID.
    public synchronized static String getID() {
        return userDTO.getId();
    }
}
