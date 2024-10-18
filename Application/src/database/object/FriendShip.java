package database.object;

public class FriendShip {

    private int friendship_id;
    private int user_id;
    private int friend_id;

    public FriendShip(int friendship_id, int user_id, int friendId) {
        this.friendship_id = friendship_id;
        this.user_id = user_id;
        friend_id = friendId;
    }

    public int getFriendship_id() {
        return friendship_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getFriend_id() {
        return friend_id;
    }
}
