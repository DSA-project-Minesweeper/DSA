package icons;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;


public class Icons {

    private static Image num1;
    private static Image num2;
    private static Image num3;
    private static Image num4;
    private static Image num5;
    private static Image num6;
    private static Image num7;
    private static Image num8;

    private static Image mine;
    private static Image mineRed;

    private static Image flag;

    private static Image timer;

    public static void loadIcon() throws IOException, URISyntaxException {
        num1 = ImageIO.read(Icons.class.getResource("/images/1.png"));
        num2 = ImageIO.read(Icons.class.getResource("/images/2.png"));
        num3 = ImageIO.read(Icons.class.getResource("/images/3.png"));
        num4 = ImageIO.read(Icons.class.getResource("/images/4.png"));
        num5 = ImageIO.read(Icons.class.getResource("/images/5.png"));
        num6 = ImageIO.read(Icons.class.getResource("/images/6.png"));
        num7 = ImageIO.read(Icons.class.getResource("/images/7.png"));
        num8 = ImageIO.read(Icons.class.getResource("/images/8.png"));

        mine = ImageIO.read(Icons.class.getResource("/images/mine.png"));
        mineRed = ImageIO.read(Icons.class.getResource("/images/mine_red.png"));

        flag = ImageIO.read(Icons.class.getResource("/images/flag.png"));

        timer = ImageIO.read(Icons.class.getResource("/images/timer.png"));
    }

    public static Image[] getArrayNum() {
        Image[] nums = {num1, num2, num3, num4, num5, num6, num7, num8};
        return nums;
    }

    public static Image getMineIcon() {
        return mine;
    }
    public static Image getMineRedIcon() {return mineRed;}

    public static Image getFlagIcon() {
        return flag;
    }

    public static Image getTimerIcon() {return timer;}

}
