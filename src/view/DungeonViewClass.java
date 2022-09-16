package view;

import controller.DungeonControllerView;
import dungeon.Directions;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;


/**
 * Implements the dungeon view which implements the dungeon view interface and implements methods
 * to show error messages for exception handling, etc. The constructor of this class initalises the
 * JFrame and adds components such as JMenu and JPanel to facilitate the menu options as well as
 * grid layout. The menu offers options to start a new game, to quit the game and to restart the
 * game. Also implements private methods to set a model when the game is restarting or starting a
 * new game. The class also implements methods to add click listeners both mouse click and
 * key press.
 */
public class DungeonViewClass extends JFrame implements DungeonView {
  private DungeonPanel gamePanel;
  private PlayerDetailsPanel playerDetailsPanel;
  private ReadOnlyDungeonModel model;
  private JScrollPane scrollable;
  private final JButton submitButton;
  private final JButton restartButton;
  private boolean previousPressedS;
  private final JTextField row;
  private final JTextField col;
  private final JRadioButton wrapped;
  private final JTextField interconnectivity;
  private final JTextField numberofmonsters;
  private final JTextField percentage;
  private int endFlag;
  private final JPanel infoPanel;

  /**
   * Constructs a dungeon view which creates a JFrame instance and add components such as JPanel,
   * JMenu. The JPanels include the dungeon grid and a panel that displays player details as well
   * as shoot status. The text fields for each input taken by the user in the form of a JOption pane
   * are initialised, the menu bar private method is called which creates the menu on top of the
   * JFrame. Also sets default close operation as exit on close and sets preferred sizes for panels
   * as well as setting the layout of the frame with respect to the panels.
   *
   * @param model read only model
   */
  public DungeonViewClass(ReadOnlyDungeonModel model) {
    super("Dungeon");
    infoPanel = new JPanel();
    endFlag = -1;
    previousPressedS = false;
    row = new JTextField(5);
    col = new JTextField(5);
    interconnectivity = new JTextField(5);
    numberofmonsters = new JTextField(5);
    percentage = new JTextField(5);
    wrapped = new JRadioButton();
    submitButton = new JButton("Submit");
    restartButton = new JButton("Restart Game");
    this.setSize(1000, 1000);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new GridLayout(2, 0, 0, 0));
    viewMenu();
    gamePanel = new DungeonPanel(model);
    playerDetailsPanel = new PlayerDetailsPanel(model);
    playerDetailsPanel.setPreferredSize(new Dimension(super.getWidth(), 300));
    add(playerDetailsPanel);
    gamePanel.setPreferredSize(new Dimension(2000, 2000));
    scrollable = new JScrollPane(gamePanel);
    this.add(scrollable);
    this.model = model;
    this.setVisible(true);
  }

  @Override
  public void setModel(ReadOnlyDungeonModel model) {
    this.setLayout(new GridLayout(2, 0, -20, 0));
    this.model = model;
    this.remove(scrollable);
    this.remove(playerDetailsPanel);
    playerDetailsPanel = new PlayerDetailsPanel(this.model);
    gamePanel = new DungeonPanel(this.model);
    gamePanel.setPreferredSize(new Dimension(2000, 2000));
    scrollable = new JScrollPane(gamePanel);
    this.add(playerDetailsPanel);
    this.add(scrollable);
  }

  @Override
  public void resetFlags() {
    this.endFlag = -1;
  }

  private void viewMenu() {
    JMenu menu;
    JMenuItem i1;
    JMenuItem i2;
    menu = new JMenu("Menu");
    i1 = new JMenuItem("Start Game");
    i2 = new JMenuItem("Quit the game");
    JMenuItem i3;
    i3 = new JMenuItem("Instructions to Play");
    menu.add(i3);
    menu.add(i1);
    menu.add(i2);
    menu.add(restartButton);
    JMenuBar mb = new JMenuBar();
    mb.add(menu);

    i3.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        JOptionPane.showMessageDialog(infoPanel, "Instructions to play\n "
                + "1. To move, press the up,down,right or left arrow key or even "
                + "click on the direction you want to move towards on the dungeon.\n"
                + "2. To shoot a monster, first press s then mention the direction "
                + "you want to shoot by pressing up,down,right or left arrow key.\n Then enter "
                + "the distance you want to shoot your arrow at by entering the number "
                + "in a textbox.\n"
                + "3. To pick up treasure, press t on the keyboard.\n"
                + "4. To pick up arrows, press a on the keyboard.\n"
                + "5. If you choose to quit the game, choose quit game in the menu option.\n"
                + "6. To restart the game with the same dungeon settings, choose Restart game "
                + "in the menu option.");
      }
    });
    i1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("row:"));
        myPanel.add(row);
        myPanel.add(Box.createHorizontalStrut(15));
        myPanel.add(new JLabel("col:"));
        myPanel.add(col);
        myPanel.add(Box.createHorizontalStrut(15));
        myPanel.add(new JLabel("interconnectivity:"));
        myPanel.add(interconnectivity);
        myPanel.add(Box.createHorizontalStrut(15));
        myPanel.add(new JLabel("wrapped/unwrapped:"));
        myPanel.add(wrapped);
        myPanel.add(Box.createHorizontalStrut(15));
        myPanel.add(new JLabel("number of monsters:"));
        myPanel.add(numberofmonsters);
        myPanel.add(Box.createVerticalStrut(15));
        myPanel.add(new JLabel("percentage of treasure and arrows:"));
        myPanel.add(percentage);
        submitButton.setActionCommand("Start game");
        myPanel.add(submitButton);
        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Please enter game settings ", JOptionPane.OK_OPTION);
        if (result == JOptionPane.OK_OPTION) {
          refresh();
        }
      }
    });

    i2.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        System.exit(0);
      }
    });


    this.setJMenuBar(mb);
    this.setVisible(true);
  }


  private int checkEndGame() {
    if (endFlag == 1) {
      JOptionPane.showMessageDialog(this, "You have won ! you can no more make a move!");
      return -1;
    } else if (endFlag == 2) {
      JOptionPane.showMessageDialog(this, "You have died ! you can no more make a move!");
      return 1;
    }
    return 0;
  }

  @Override
  public void setFeatures(Features f) {
    this.submitButton.addActionListener(l -> f.processInput(row.getText(), col.getText(),
            interconnectivity.getText(),
            wrapped.isSelected(), percentage.getText(), numberofmonsters.getText()));
    this.restartButton.addActionListener(l -> f.restartGame());
    this.addKeyListener(new KeyAdapter() {

      @Override
      public void keyTyped(KeyEvent e) {
        if (checkEndGame() != 0) {
          return;
        }

        if (e.getKeyChar() == 'a') {
          f.pickUpArrow();
        } else if (e.getKeyChar() == 't') {
          f.pickUpTreasure();
        }
      }

      @Override
      public void keyPressed(KeyEvent e) {
        if (checkEndGame() != 0) {
          return;
        }
        if (previousPressedS) {
          if (e.getKeyCode() == KeyEvent.VK_UP) {
            distanceForShoot(Directions.NORTH, f);
          } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            distanceForShoot(Directions.SOUTH, f);
          } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            distanceForShoot(Directions.EAST, f);
          } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            distanceForShoot(Directions.WEST, f);
          }
          previousPressedS = false;
          return;
        }
        playerDetailsPanel.resetShootFlag();
        playerDetailsPanel.resetMoveFlag();
        if (e.getKeyCode() == KeyEvent.VK_UP) {
          int moveFlag = f.movePlayer(Directions.NORTH);
          playerDetailsPanel.setMoveFlag(moveFlag);
          refresh();
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
          int moveFlag = f.movePlayer(Directions.SOUTH);
          playerDetailsPanel.setMoveFlag(moveFlag);
          refresh();
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
          int moveFlag = f.movePlayer(Directions.EAST);
          playerDetailsPanel.setMoveFlag(moveFlag);
          refresh();
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
          int moveFlag = f.movePlayer(Directions.WEST);
          playerDetailsPanel.setMoveFlag(moveFlag);
          refresh();
        }

        if (model.getPlayer() == 1 && model.isGameOver()) {
          endFlag = 1;
        } else if (model.getPlayer() == 0 && model.isGameOver()) {
          endFlag = 2;
        }

        if (e.getKeyCode() == KeyEvent.VK_S) {
          previousPressedS = true;
          return;
        }
        previousPressedS = false;
      }

    });
  }

  private void distanceForShoot(Directions direction, Features f) {
    JTextField distance = new JTextField(5);
    JPanel distancePanel = new JPanel();
    distancePanel.add(new JLabel("distance:"));
    distancePanel.add(distance);
    int result = JOptionPane.showConfirmDialog(null, distancePanel,
            "Please enter distance to shoot", JOptionPane.OK_CANCEL_OPTION);
    if (result == JOptionPane.OK_OPTION) {
      try {
        if (Integer.parseInt(distance.getText()) < 0) {
          showErrorMessage("Distance must be greater than 0!");
        }
        int flag = f.shootArrow(direction, Integer.parseInt(distance.getText()));
        playerDetailsPanel.setShootFlag(flag);
        refresh();
      } catch (NumberFormatException ne) {
        showErrorMessage("Distance must be a number!");
      } catch (IllegalArgumentException ie) {
        showErrorMessage("Try shooting again!");
      }

    }
  }

  @Override
  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }


  @Override
  public void makeVisible() {
    this.setVisible(true);
  }


  @Override
  public void addClickListener(DungeonControllerView listener) {

    MouseAdapter clickAdapter = new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (checkEndGame() != 0) {
          return;
        }
        super.mouseClicked(e);
        int mouseX = e.getX();
        int mouseY = e.getY();
        int playerX = (model.getStartCave().getY() + 1) * 100 + 50;
        int playerY = (model.getStartCave().getX() + 1) * 100 + 50;
        double x = mouseX - playerX;
        double y = mouseY - playerY;
        double angle = Math.atan2(y, x);
        if (angle < 0.0) {
          angle += 2.0 * Math.PI;
        }
        angle = (180 / Math.PI) * angle;

        if (angle > 45 && angle <= 135) {
          int moveFlag = listener.handleCellClick(Directions.SOUTH);
          playerDetailsPanel.setMoveFlag(moveFlag);
        } else if (angle > 135 && angle <= 225) {
          int moveFlag = listener.handleCellClick(Directions.WEST);
          playerDetailsPanel.setMoveFlag(moveFlag);
        } else if (angle > 225 && angle <= 315) {
          int moveFlag = listener.handleCellClick(Directions.NORTH);
          playerDetailsPanel.setMoveFlag(moveFlag);

        } else {
          int moveFlag = listener.handleCellClick(Directions.EAST);
          playerDetailsPanel.setMoveFlag(moveFlag);

        }
        if (model.getPlayer() == 1 && model.isGameOver()) {
          endFlag = 1;
        } else if (model.getPlayer() == 0 && model.isGameOver()) {
          endFlag = 2;
        }
      }
    };
    gamePanel.addMouseListener(clickAdapter);
  }

  @Override
  public void showErrorMessage(String error) {
    JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void refresh() {
    repaint();
    setVisible(true);
  }
}
