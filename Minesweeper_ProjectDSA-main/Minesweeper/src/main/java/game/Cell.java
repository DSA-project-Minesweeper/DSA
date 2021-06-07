package game;


public class Cell {
    private boolean open;
    private boolean mark;
    private boolean bom;
    private int value;

    public Cell() {
        open = false;
        mark = false;
        bom = false;
        value = -1;
    }

    public Cell(boolean open, boolean mark, boolean bom, int value) {
        this.open = open;
        this.mark = mark;
        this.bom = bom;
        this.value = value;
    }

    public boolean isOpen() {
        return open;
    }

    public boolean isMark() {
        return mark;
    }

    public boolean isBom() {
        return bom;
    }

    public int getValue() {
        return value;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public void setMark(boolean mark) {
        this.mark = mark;
    }

    public void setBom(boolean bom) {
        this.bom = bom;
        value = -1;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
