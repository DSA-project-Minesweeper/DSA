package gui;

public interface GameListener {
    void onRestartGame();
    int onGameOver(boolean playerWin, int xLast, int yLast, int time);
}
