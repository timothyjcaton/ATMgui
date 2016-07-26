/**
 * Created by tjc4h on 6/23/2016.
 */

import javafx.stage.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by tjc4h on 6/23/2016.
 */
public class Account extends JFrame implements Serializable
{
    private static Account myAcct = new Account();

    protected int firstdate = 1;

    protected int seconddate;

    protected Calendar cal1 = new GregorianCalendar();

    protected Calendar cal2 = new GregorianCalendar();

    protected boolean dateflag = false;

    protected double rate;

    double currencyout;

    private static final int WIDTH = 800;
    private static final int HEIGHT = 500;

    private JLabel topMenu;
    private JTextField menuTF;
    private JButton enterB, exitB;

    private EnterButtonHandler cbHandler;
    private ExitButtonHandler ebHandler;


    int acctSelected;

    public ArrayList<Account> accounts = new ArrayList<Account>();
    public double balance = 100;


    public void addAccount()
    {
            accounts.add(new Account());

            accounts.add(new Account());

            accounts.add(new Account());
    }

    public void loadAccount(String acct)
    {
        if (acct.equals("1"))
        {
            accounts.get(0).menu();
        }
        if (acct.equals("2"))
        {
            accounts.get(1).menu();
        }

        if (acct.equals("3"))
        {
            accounts.get(2).menu();
        }
    }

    public void menu()
    {
        double input;

        topMenu = new JLabel("<html>Please choose from the following options:<br>1.)Deposit<br>2.)Withdraw<br>3.)Check Balance</html>", SwingConstants.CENTER);

        menuTF = new JTextField();
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
        pane.add(menuTF);
        pane.add(enterB);
        pane.add(exitB);

        setSize(WIDTH, HEIGHT);
        setVisible(true);
    }

    public void deposit(double depositAmount) throws IOException
    {
        balance = getBalance() + depositAmount;
        JOptionPane.showMessageDialog(null, "Deposit of " + NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(depositAmount) + " processed\n" );
    }

    public void withdrawal(double withdrawalAmount) throws IOException {
        if(balance - withdrawalAmount < 0)
        {
            JOptionPane.showMessageDialog(null, "Insufficient Funds" );
        }

        if(balance - withdrawalAmount >= 0)
        {
            balance = getBalance() - withdrawalAmount;
            JOptionPane.showMessageDialog(null, "Withdrawal of " + NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(withdrawalAmount) + " processed\n" );
        }
    }

    public double balance (double balance)
    {
        this.balance = balance;
        return balance;
    }

    public double getBalance() throws IOException
    {

        if (dateflag == true)
        {

            getDate2();

            getInterest();

        }

        else
        {

            getDate1();

        }

        return balance;
    }

    public void setBalance(double balance)
    {
        this.balance = balance;
    }

    private void getDate1() throws IOException

    {
        String inputText;

        inputText = JOptionPane.showInputDialog("Enter todays date(mm/dd/yyyy): ");

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

        ParsePosition pos = new ParsePosition(0);


        Date date = formatter.parse(inputText, pos);

        cal1.setTime(date);

        firstdate = cal1.get(Calendar.DAY_OF_YEAR);

        dateflag = true;
    }


    private void getDate2() throws IOException

    {
        String inputText;

        inputText = JOptionPane.showInputDialog("Enter todays date(mm/dd/yyyy):");

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

        ParsePosition pos = new ParsePosition(0);

        Date date;

        date = formatter.parse(inputText, pos);

        cal2.setTime(date);

        seconddate = cal2.get(Calendar.DAY_OF_YEAR);

        if (firstdate > seconddate)
        {

            JOptionPane.showMessageDialog(null, "You must enter a future date.");

            getDate2();

        }
    }

    private void getInterest()

    {
        int datediff = seconddate - firstdate;

        rate = .10/365;

        double ratetime = Math.pow(1+rate,datediff);

        balance = balance * ratetime;

        firstdate = seconddate;
    }

    private String calcInterest() throws IOException
    {

        NumberFormat currencyFormatter;

        String currencyOut;

        currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);

        currencyOut = currencyFormatter.format(balance);

        return currencyOut;
    }

    public void saveAccount()
    {
        try
        {
            FileOutputStream outStream = new FileOutputStream("D:filel.out");
            ObjectOutputStream os = new ObjectOutputStream(outStream);
            os.writeObject(accounts);
            os.flush();
            os.close();

        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    public void accessAccount()
    {
        try
        {
            FileInputStream inStream = new FileInputStream("D:filel.out");
            ObjectInputStream is = new ObjectInputStream(inStream);
            accounts = (ArrayList) is.readObject();
            is.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    class ExitButtonHandler implements ActionListener, Serializable
    {
        public void actionPerformed(ActionEvent e)
        {
            dispose();
        }
    }


    public class EnterButtonHandler implements ActionListener, Serializable {
        public void actionPerformed(ActionEvent e) {
            double input;


            input = Double.parseDouble(menuTF.getText());
            switch ((int) input) {
                case 1:
                    double d = new Double(JOptionPane.showInputDialog("Please enter the amount you would like to deposit:"));
                    try {
                        deposit(d);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    break;

                case 2:
                    double w = new Double(JOptionPane.showInputDialog("Please enter the amount you would like to withdraw:"));
                    try {
                        withdrawal(w);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    break;

                case 3:
                    try {
                        JOptionPane.showMessageDialog(null, "Your balance is: " + NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(balance(getBalance())));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    break;

                case 4:
                    break;
            }
        }
    }
}

