package com.mygame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class MyGame extends JFrame implements ActionListener
{
    JLabel heading,clockLabel;
    Font font= new Font("",Font.BOLD,40);

    JPanel mainPanel;

    JButton[] btns= new JButton[9];   // 9 size array for button panel
    //game instance variable..
     int gameChances[]={2,2,2,2,2,2,2,2,2};
     int activePlayer = 0;

     //Winning array
    int wps[][]={
             {0,1,2},
             {3,4,5},
             {6,7,8},
             {0,3,6},
             {1,4,7},
             {2,5,8},
             {0,4,8},
             {2,4,6}
     };

    int winner = 2;

    boolean gameOver = false;
    public MyGame()
    {

        System.out.println("Creating Instance Of game");
        setTitle("My Tic Tac Toe Game..");
        setSize(750,750);
        ImageIcon icon = new ImageIcon("img/icon.png");  //this gives icon as logo in left top

        setIconImage(icon.getImage());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createGUI();
        setVisible(true);
    }

    private void createGUI()
    {
       this.getContentPane().setBackground(Color.decode("#2196f3") ); //for setting up background of frame
     this.setLayout(new BorderLayout());

     //North Heading...

        heading=new JLabel("Tic Tac Toe");
       // heading.setIcon(new ImageIcon("img/icon.png"));     //setting Tic Tac Toe Icon in center
        heading.setFont(font);
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setForeground(Color.BLACK);
        heading.setHorizontalTextPosition(SwingConstants.CENTER);  //this brings Tic Tac Toe Heading in center
        heading.setVerticalTextPosition(SwingConstants.BOTTOM);    //this brings the heading in bottom of the icon img

        this.add(heading,BorderLayout.NORTH);

        //object of JLabel for clock

        clockLabel=new JLabel("Clock");
        clockLabel.setForeground(Color.BLACK);
        clockLabel.setFont(font);
        clockLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(clockLabel,BorderLayout.SOUTH);

        //  Setting Time running Dynamically clock value with fetch
        //we can set Time even with Timer Class and scheduliing time in each second

        // But we r doing it with Thread
         Thread t= new Thread(){
             public void run(){
                 try{
                     while(true){  //Loop Runs For Infinite Time ,condition is never  wrong

                     String  datetime =new Date().toLocaleString();  //calc new time

                       clockLabel.setText(datetime);   //set new time in dateTime refrence

                       Thread.sleep(1000);  //1000 millisecond means 1 second ,sleep for next one second
                     }
                 }
                 catch(Exception e) {
                     e.printStackTrace();
                 }

             }

         };
         t.start();  // Strated the thread

        ///////////////Panel  Section/////////////////////
        mainPanel=new JPanel();

        mainPanel.setLayout(new GridLayout(3,3));

        for(int i=1; i<=9; i++){
             JButton btn= new JButton();
            // btn.setIcon(new ImageIcon("img/0.png"));
             btn.setBackground(Color.decode("#90caf9"));

             btn.setFont(font);
             mainPanel.add(btn);
             btns[i-1]=btn;
             btn.addActionListener(this);   //listener will see if the grid cell was clicked through action performed method
             btn.setName(String.valueOf(i-1));
        }
        this.add(mainPanel,BorderLayout.CENTER);
    }

    @Override  // implement all abstract method
    public void actionPerformed(ActionEvent e) {
      JButton currentButton=(JButton)e.getSource();

      String nameStr =currentButton.getName();
       // System.out.println(nameStr);

        int name=Integer.parseInt(nameStr.trim());

        if(gameOver){
            JOptionPane.showMessageDialog(this,"Game Already Over!!!");
            return;

        }

        if(gameChances[name]==2){
            if(activePlayer==1){
                currentButton.setIcon(new ImageIcon("img/x.png"));

                gameChances[name]=activePlayer;
                activePlayer=0;
            }else{
                currentButton.setIcon(new ImageIcon("img/0.png"));

                gameChances[name]=activePlayer;
                activePlayer=1;
            }
            //find the winner.......
            for(int[] temp:wps){
                if((gameChances[temp[0]]==gameChances[temp[1]]) && (gameChances[temp[1]]==gameChances[temp[2]]) && gameChances[temp[2]]!=2){
                    winner=gameChances[temp[0]];
                    gameOver=true;
                    JOptionPane.showMessageDialog(null,"Player "+ winner + " has won the game");

                   int  i = JOptionPane.showConfirmDialog(this," do you want to play more ???");
                   if(i==0){
                       this.setVisible(false);
                       new MyGame();
                   }else if(i==1){
                       System.exit(555);
                   }else{

                   }
                    System.out.println(i);
                    break;
                }
            }
//draw logic
            int count=0;

            for(int x:gameChances){
                if(x==2){
                    count++;
                    break;
                }
            }

            if(count==0 && gameOver==false) {
                JOptionPane.showMessageDialog(null, "Its Draw...");

                int i = JOptionPane.showConfirmDialog(this, "Play more??");
                if (i == 0) {
                    this.setVisible(false);
                    new MyGame();
                } else if (i == 1) {
                    System.exit(1212);
                } else {

                }
                gameOver = true;
            }
        }else{
            JOptionPane.showMessageDialog(this,"position occupied...");
        }
    }
}
