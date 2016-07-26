/**
 * Created by tjc4h on 6/23/2016.
 */
import com.sun.istack.internal.Nullable;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class ATM extends JFrame

{
    private static Account myAcct = new Account();
    private static final int WIDTH = 800;
    private static final int HEIGHT = 500;
    private static int selectedIndex;

    private JLabel topMenu;
    private JTextField topMenuTF;
    public JComboBox optionSelector;
    private JButton enterB, exitB;

    //Button handlers:
    private EnterButtonHandler cbHandler;
    private ExitButtonHandler ebHandler;
    private int input;

    public ATM()
    {
        topMenu = new JLabel("<html>Please choose one of the following:<br>1.)Populate Accounts<br>2.)Load Accounts<br>3.)Access Accounts<br>4.)Save To File</html>", SwingConstants.CENTER);

        optionSelector = new JComboBox();
        optionSelector.addItem("Populate Accounts");
        optionSelector.addItem("Select Accounts");
        optionSelector.addItem("Access Accounts");
        optionSelector.addItem("Save To File");

        enterB = new JButton("Enter");
        cbHandler = new EnterButtonHandler();
        enterB.addActionListener(cbHandler);
        exitB = new JButton("Exit");
        ebHandler = new ExitButtonHandler();
        exitB.addActionListener(ebHandler);

        setTitle("Welcome to ATM");
        Container pane = getContentPane();
        pane.setLayout(new GridLayout(4, 2));


        pane.add(topMenu);
        pane.add(optionSelector);
        pane.add(enterB);
        pane.add(exitB);

        setSize(WIDTH, HEIGHT);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }


    private class EnterButtonHandler implements ActionListener
    {

        public void actionPerformed(ActionEvent e)
        {

            int input = optionSelector.getSelectedIndex();

            switch(input)
            {
                case 0:
                    myAcct.addAccount();
                    break;
                case 1:
                    String acct;
                    acct = JOptionPane.showInputDialog("Please choose account 1, 2 or 3:");
                    myAcct.loadAccount(acct);
                    break;
                case 2:
                    myAcct.accessAccount();
                    acct = JOptionPane.showInputDialog("Please choose account 1, 2 or 3:");
                    myAcct.loadAccount(acct);
                    break;
                case 3:
                    myAcct.saveAccount();;
                    break;
            }
        }
    }

    public class ExitButtonHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            System.exit(0);
        }
    }

    public void setSelectedIndex(int input)
    {
        this.input = selectedIndex;
    }


    public int getSelectedIndex()
    {
        return selectedIndex;
    }

    public int selectedIndex(int input)
    {
        this.input = selectedIndex;
        return selectedIndex;
    }

    public static void main(String[] args)
    {
        ATM myATM = new ATM();
    }
}


