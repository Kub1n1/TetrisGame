package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Stack;

public class SettingsForm extends JFrame
{
    private Stack<JFrame> _forms;
    private ArrayList<Integer> _settings;
    private Font _font;

    public SettingsForm(Stack<JFrame> forms, ArrayList<Integer> settings, Font font)
    {
        _forms = forms;
        _settings = settings;
        _font = font;
        this.InitColorsSettings();
        this.InitControls();
        this.InitFrame();
        this.InitButtons();
    }

    private void InitColorsSettings()
    {
        int WINDOW_WIDTH = 1100;
        int WINDOW_HEIGHT = 700;

        JLabel colors = new JLabel("Colors");
        colors.setFont(_font.deriveFont(Font.BOLD, 50));
        colors.setBounds(
                (int)(WINDOW_WIDTH * 0.2), (int)(WINDOW_HEIGHT * 0.3),
                250, 50
        );
        this.add(colors);

        ButtonGroup btnGrp = new ButtonGroup();
        JRadioButton set1 = new JRadioButton("Color Set 1");
        set1.setBounds(
                (int)(WINDOW_WIDTH * 0.23), (int)(WINDOW_HEIGHT * 0.3) + 50,
                (int)(WINDOW_WIDTH * 0.1), (int)(WINDOW_HEIGHT * 0.05)
        );
        set1.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                _settings.set(0, 1);
            }
        });

        JRadioButton set2 = new JRadioButton("Color Set 2");
        set2.setBounds(
                (int)(WINDOW_WIDTH * 0.23), (int)(WINDOW_HEIGHT * 0.3) + 75,
                (int)(WINDOW_WIDTH * 0.1), (int)(WINDOW_HEIGHT * 0.05)
        );
        set2.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                _settings.set(0, 2);
            }
        });

        if (_settings.get(0) == 1)
        {
            set1.setSelected(true);
            set2.setSelected(false);
        }
        else
        {
            set1.setSelected(false);
            set2.setSelected(true);
        }

        btnGrp.add(set1);
        this.add(set1);
        btnGrp.add(set2);
        this.add(set2);
    }

    private void InitControls()
    {
        int WINDOW_WIDTH = 1100;
        int WINDOW_HEIGHT = 700;

        JLabel colors = new JLabel("Control");
        colors.setFont(_font.deriveFont(Font.BOLD, 50));
        colors.setBounds(
                (int)(WINDOW_WIDTH * 0.4), (int)(WINDOW_HEIGHT * 0.3),
                250, 50
        );
        this.add(colors);

        int y_Label = 40;
        int nextLabel = 30;

        this.AddControlLabel("LEFT ARROW", "Move block left", y_Label);
        y_Label += nextLabel;
        this.AddControlLabel("RIGHT ARROW", "Move block right", y_Label);
        y_Label += nextLabel;
        this.AddControlLabel("UP ARROW", "Rotate block", y_Label);
        y_Label += nextLabel;
        this.AddControlLabel("DOWN ARROW", "Move block down", y_Label);
        y_Label += nextLabel;
        this.AddControlLabel("SPACE", "Drop block", y_Label);
        y_Label += nextLabel;
    }

    private void AddControlLabel(String key, String description, int y_pos)
    {
        int WINDOW_WIDTH = 1100;
        int WINDOW_HEIGHT = 700;

        JLabel label = new JLabel(key + "  -  " + description);
        label.setFont(_font.deriveFont(Font.BOLD, 15));
        label.setBounds(
                (int)(WINDOW_WIDTH * 0.38), (int)(WINDOW_HEIGHT * 0.3) + y_pos,
                300, 50
        );
        this.add(label);
    }

    private void InitFrame()
    {
        int WINDOW_WIDTH = 1100;
        int WINDOW_HEIGHT = 700;

        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }

    private void InitButtons()
    {
        JButton btnMainMenu = new JButton("Main Menu");
        btnMainMenu.setBounds((this.getWidth() / 2) - 150, 100, 300, 50);
        this.add( btnMainMenu );

        btnMainMenu.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                setVisible(false);
                _forms.pop();
            }
        });
    }
}
