package network.client.extractors;

import network.util.message.Message;
import network.util.message.response.*;
import network.client.observer.ClientGameObserver;

import java.util.ArrayList;

public class GameExtractor implements IExtractor{

    private ArrayList<ClientGameObserver> gameObservers;

    public GameExtractor(ArrayList<ClientGameObserver> gameObservers) {
        this.gameObservers = gameObservers;
    }

    public void extractContext(Message message) {
        switch (message.getType()) {
            case START_GAME_RESPONSE_MESSAGE:
                gameObservers.forEach(ClientGameObserver::gameStarted);
                break;
            case SENTENCE_IS_DONE_RESPONSE_MESSAGE:
                gameObservers.forEach(ClientGameObserver::sentenceIsDone);
                break;
            case COUNTDOWN_FINISHED_RESPONSE_MESSAGE:
                gameObservers.forEach(ClientGameObserver::countdownFinished);
                break;
            case COUNTDOWN_UPDATE_RESPONSE_MESSAGE:
                CountdownUpdateResponseMessage countdownUpdateResponseMessage = (CountdownUpdateResponseMessage) message;
                gameObservers.forEach(observer -> observer.countdownUpdate(countdownUpdateResponseMessage.getRemainingSeconds()));
                break;
            case TIMER_FINISHED_RESPONSE_MESSAGE:
                gameObservers.forEach(ClientGameObserver::timerFinished);
                break;
            case TIMER_UPDATED_RESPONSE_MESSAGE:
                TimerUpdatedResponseMessage timerUpdatedResponseMessage = (TimerUpdatedResponseMessage) message;
                gameObservers.forEach(observer -> observer.timerUpdated(timerUpdatedResponseMessage.getRemainingSeconds()));
                break;
            case WORD_CHECKER_CORRECT_WORD_RESPONSE_MESSAGE:
                gameObservers.forEach(ClientGameObserver::wordCheckerCorrectWord);
                break;
            case WORD_CHECKER_UPDATE_RESPONSE_MESSAGE:
                WordCheckerUpdateResponseMessage wordCheckerUpdateResponseMessage = (WordCheckerUpdateResponseMessage) message;
                gameObservers.forEach(observer -> observer.wordCheckerUpdate(wordCheckerUpdateResponseMessage.getWordLength()));
                break;
            case UPDATE_WPM_RESPONSE_MESSAGE:
                UpdateWPMResponseMessage updateWPMResponseMessage = (UpdateWPMResponseMessage) message;
                gameObservers.forEach(observer -> observer.updateWPM(updateWPMResponseMessage.getWpm()));
                break;
            case GAME_FINISHED_FINAL_WPM_RESPONSE_MESSAGE:
                GameFinishedFinalWPMResponseMessage gameFinishedFinalWPMResponseMessage = (GameFinishedFinalWPMResponseMessage) message;
                gameObservers.forEach(observer -> observer.gameFinishedFinalWPM(gameFinishedFinalWPMResponseMessage.getWpm()));
                break;
            case GET_CURRENT_SENTENCE_RESPONSE_MESSAGE:
                GetCurrentSentenceResponseMessage getSentenceResponseMessage = (GetCurrentSentenceResponseMessage) message;
                gameObservers.forEach(observer -> observer.getSentence(getSentenceResponseMessage.getSentence()));
                break;
            case UPDATE_ACCURACY_RESPONSE_MESSAGE:
                UpdateAccuracyResponseMessage updateAccuracyResponseMessage = (UpdateAccuracyResponseMessage) message;
                gameObservers.forEach(observer -> observer.updateAccuracy(updateAccuracyResponseMessage.getAccuracy()));
                break;
            case UPDATE_ACCURACY_OPPONENT_RESPONSE_MESSAGE:
                UpdateAccuracyOpponentResponseMessage updateAccuracyOpponentResponseMessage = (UpdateAccuracyOpponentResponseMessage) message;
                gameObservers.forEach(observer -> observer.updateAccuracyOpponent(updateAccuracyOpponentResponseMessage.getAccuracy()));
                break;
            case UPDATE_WPM_OPPONENT_RESPONSE_MESSAGE:
                UpdateWPMOpponentResponseMessage updateWpmOpponentResponseMessage = (UpdateWPMOpponentResponseMessage) message;
                gameObservers.forEach(observer -> observer.WPMUpdateOpponent(updateWpmOpponentResponseMessage.getWpm()));
                break;
            case ALL_PLAYER_JOINED_RESPONSE_MESSAGE:
                gameObservers.forEach(ClientGameObserver::allPlayersJoined);
                break;
            case ELO_CHANGE_RESPONSE_MESSAGE:
                EloChangeResponseMessage eloChangeResponseMessage = (EloChangeResponseMessage) message;
                gameObservers.forEach(observer -> observer.eloChange(eloChangeResponseMessage.getEloChange()));
                break;
            case GAME_RESULT_RESPONSE_MESSAGE:
                GameResultResponseMessage gameResultResponseMessage = (GameResultResponseMessage) message;
                gameObservers.forEach(observer -> observer.gameResult(gameResultResponseMessage.isAWin()));
                break;
            case GAME_RESULT_WITH_TIE_GAME_RESPONSE_MESSAGE:
                gameObservers.forEach(ClientGameObserver::gameResultWithTieGame);
                break;
            case OPPONENT_NAME_RESPONSE_MESSAGE:
                OpponentNameResponseMessage opponentNameResponseMessage = (OpponentNameResponseMessage) message;
                gameObservers.forEach(observer -> observer.opponentProfileName(opponentNameResponseMessage.getOpponentName()));
                break;
            case CURRENT_WORD_RESPONSE_MESSAGE:
                CurrentWordResponseMessage currentWordResponseMessage = (CurrentWordResponseMessage) message;
                gameObservers.forEach(observer -> observer.currentWord(currentWordResponseMessage.getCurrentWord()));
                break;
            default:
                throw new IllegalArgumentException("Invalid message type: " + message.getType());

        }
    }

}