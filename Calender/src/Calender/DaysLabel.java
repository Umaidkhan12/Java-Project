package Calender;

import javax.swing.*;
import java.awt.*;

public class DaysLabel extends JLabel {
    public DaysLabel(String text, Color background, Color foreground, Boolean btn) {
        setText(text);
        setHorizontalAlignment(JLabel.CENTER);
        setFont(new Font("Helvetica", Font.PLAIN, 20));
        setOpaque(true);
        setBackground(background);
        setForeground(foreground);
        if (btn) setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}
