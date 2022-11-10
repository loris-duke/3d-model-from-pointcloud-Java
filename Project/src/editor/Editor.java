package editor;

import javax.swing.*;

public class Editor extends JPanel
{
    public JSlider slider;
    public Editor()
    {
        JLabel idLabel = new JLabel("test");
        this.add(idLabel);
        slider = new JSlider(JSlider.HORIZONTAL, 0, 10, 1);
        slider.setMinorTickSpacing(2);
        slider.setMajorTickSpacing(10);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        this.add(slider);
    }
}
