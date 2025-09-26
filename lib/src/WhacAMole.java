import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;
public class WhacAMole{
    int boardwidth=600;
    int boardheigth=650;

    JFrame frame=new JFrame("Mario: Whac A Mole");

    JLabel textLabel= new JLabel();
    JPanel texPanel= new JPanel();
    JPanel boardPanel=new JPanel();

    JButton currMoleTile; //to keep track of which tile has the mole
    JButton currPlantTile;

    Random random= new Random(); //keeps track of placement of each button(to move the tile randomly)
    Timer setMoleTimer;
    Timer setPlantTimer;

    JButton[] board=new JButton[9];

    ImageIcon moleIcon;
    ImageIcon plantIcon;
    int score=0;
    WhacAMole(){
        frame.setSize(boardwidth,boardheigth);
        frame.setLocationRelativeTo(null); //opens up the windows at center of the screen
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //when clicked on "X" the window closes
        frame.setLayout(new BorderLayout());

        textLabel.setBackground(Color.darkGray);
        textLabel.setForeground(Color.white);
        textLabel.setFont(new Font("Arial",Font.BOLD,50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Score: 0");
        textLabel.setOpaque(true); //to display the bg color

        texPanel.setLayout(new BorderLayout());
        texPanel.add(textLabel);
        frame.add(texPanel,BorderLayout.NORTH);

        boardPanel.setLayout(new GridLayout(3,3));
        boardPanel.setBackground(Color.darkGray);
        frame.add(boardPanel);


        Image plantImg=new ImageIcon(getClass().getResource("./plant2.png")).getImage(); // Load the image using ImageIcon
        Image scaledPlantImg=plantImg.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH); // Scale the image to 150x150 with smooth scaling
        plantIcon = new ImageIcon(scaledPlantImg); // Create a new ImageIcon with the scaled image

        Image moleImg=new ImageIcon(getClass().getResource("./mole.png")).getImage();
        Image scaledMoleImg=moleImg.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH);
        moleIcon = new ImageIcon(scaledMoleImg);
        for(int i=0;i<9;i++){
            JButton tile=new JButton();
            board[i]=tile;
            boardPanel.add(tile);
            tile.setFocusable(false); //used to diasable the keyboard focus o the tile
            tile.addActionListener(new ActionListener() { //Action listeners are objects that "listen" for certain events.
                public void actionPerformed(ActionEvent e){ //Idefines what happens when the button is clicked
                    JButton tile=(JButton) e.getSource(); //identifies which button triggered the event
                    if(tile==currMoleTile){
                        score+=10;
                        textLabel.setText("Score: "+Integer.toString(score));
                    }
                    else{
                        textLabel.setText("Game Over: "+Integer.toString(score));
                        setMoleTimer.stop();
                        setPlantTimer.stop();
                        for(int i=0;i<9;i++){
                            board[i].setEnabled(false); //dims out the board
                        }
                    }
                }
            });




        }                   //Timer(millisec's,action) 
        setMoleTimer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currMoleTile != null) {
                    currMoleTile.setIcon(null);
                    currMoleTile = null;
                }
                int num = random.nextInt(9); //0-8
                JButton tile = board[num];

                //if tile is occupied by plant, skip tile for this turn
                if (currPlantTile == tile) return;
                currMoleTile = tile;
                currMoleTile.setIcon(moleIcon);
            }
        });
        setPlantTimer = new Timer(1500, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currPlantTile != null) {
                    currPlantTile.setIcon(null);
                    currPlantTile = null;
                }
                int num = random.nextInt(9);
                JButton tile = board[num];
                if (currMoleTile == tile) return;
                currPlantTile = tile;
                currPlantTile.setIcon(plantIcon);
            }
        });


        setMoleTimer.start();
        setPlantTimer.start();

        frame.setVisible(true); //to avoid delay in loading of preview(shows all the 9 buttons at atime)
    }
}