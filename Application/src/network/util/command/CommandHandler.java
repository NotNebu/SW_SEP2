package network.util.command;

import network.server.socket.SocketServer;
import network.util.command.commands.*;
import network.util.message.Message;
import network.util.message.request.*;
import network.util.message.response.ProfileNameExistsResponseMessage;
import network.util.message.response.UserNameExistResponseMessage;

// Factory class for creating commands based on message type
public class CommandHandler {

    // Returns a command instance based on the given message type
    public ICommand getCommand(Message message, SocketServer.ClientHandler clientHandler) {
        switch (message.getType()) {
            case START_GAME_REQUEST_MESSAGE:
                return new StartGameCommand(clientHandler, (StartGameRequestMessage) message);
            case GENERATE_NEW_SENTENCE_REQUEST_MESSAGE:
                return new GenerateNewSentenceCommand(clientHandler, (GenerateNewSentenceRequestMessage) message);
            case GET_CURRENT_SENTENCE_REQUEST_MESSAGE:
                return new GetCurrentSentenceCommand(clientHandler, (GetCurrentSentenceRequestMessage) message);
            case CHECK_WORD_REQUEST_MESSAGE:
                return new CheckWordCommand(clientHandler, (CheckWordRequestMessage) message);
            case JOIN_QUEUE_REQUEST_MESSAGE:
                return new JoinQueueCommand(clientHandler, (JoinQueueRequestMessage) message);
            case LEAVE_QUEUE_REQUEST_MESSAGE:
                return new LeaveQueueCommand(clientHandler, (LeaveQueueRequestMessage) message);
            case USER_NAME_EXIST_REQUEST_MESSAGE:
                return new UsernameExistsCommand(clientHandler, (UserNameExistRequestMessage) message);
            case PROFILE_NAME_EXISTS_REQUEST_MESSAGE:
                return new ProfileNameExistsCommand(clientHandler, (ProfileNameExistsRequestMessage) message);
            case SIGN_UP_REQUEST_MESSAGE:
                return new SignUpCommand(clientHandler, (SignUpRequestMessage) message);
            case LOGIN_REQUEST_MESSAGE:
                return new LoginCommand(clientHandler, (LoginRequestMessage) message);
            case GET_PROFILE_REQUEST_MESSAGE:
                return new GetProfileCommand(clientHandler, (GetProfileRequestMessage) message);
            case GET_USERNAMES_FOR_FRIEND_REQUEST_MESSAGE:
                return new GetUsernamesForFriendsCommand(clientHandler, (GetUsernamesForFriendRequestMessage) message);
            case ADD_FRIEND_REQUEST_MESSAGE:
                return new AddFriendCommand(clientHandler, (AddFriendRequestMessage) message);
            case USERS_FRIENDS_GOTTEN_REQUEST_MESSAGE:
                return new GetUsersFriendsCommand(clientHandler, (GetUsersFriendsRequestMessage) message);
            case REMOVE_FRIEND_REQUEST_MESSAGE:
                return new RemoveFriendCommand(clientHandler, (RemoveFriendRequestMessage) message);
            default:
                throw new IllegalArgumentException("Unknown message type: " + message.getType());
        }
    }
}
