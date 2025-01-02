package view;

import java.awt.*;
import javax.swing.border.Border;

public class RoundBorder implements Border {
    private int radius;

    public RoundBorder(int radius) {
        this.radius = radius;
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(this.radius + 5, this.radius + 5, this.radius + 5, this.radius + 5);
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.setColor(Color.BLACK);
        g.fillRoundRect(x, y, width - 1, height - 1, radius, radius);  // Redondea los bordes
    }
}
