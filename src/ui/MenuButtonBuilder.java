package ui;

import gameStates.GameStates;

public class MenuButtonBuilder {

    private int xPos, yPos, rowIndex;
    private GameStates state;

    public MenuButtonBuilder xPos(int xPos) {
        this.xPos = xPos;
        return this;
    }

    public MenuButtonBuilder yPos(int yPos) {
        this.yPos = yPos;
        return this;
    }

    public MenuButtonBuilder rowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
        return this;
    }

    public MenuButtonBuilder state(GameStates state) {
        this.state = state;
        return this;
    }

    public MenuButton build() {
        return new MenuButton(xPos,yPos,rowIndex,state);
    }

}
