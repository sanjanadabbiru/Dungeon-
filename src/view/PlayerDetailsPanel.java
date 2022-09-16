package view;

import dungeon.Treasure;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * This class extends a JPanel where it displays the player's details such as the number of
 * arrows or treasure it holds. It also displays any status when the player attempts to shoot
 * an arrow such as an arrow missing the monster or slaying an otyugh. The details are displayed
 * on the top of the frame.
 */
class PlayerDetailsPanel extends JPanel {

  private final ReadOnlyDungeonModel model;
  private List<Treasure> treasureList;
  private int shootFlag;
  private int moveFlag;
  private BufferedImage diamond1;
  private BufferedImage sapphire1;
  private BufferedImage ruby1;
  private BufferedImage whiteArrow1;

  /**
   * Constructs a panel for displaying the details of a player. It adds the images for treasure and
   * arrows. Sets flags that are used for displaying a particular message depending on the status
   * of the flag.
   *
   * @param model read only dungeon model
   */
  public PlayerDetailsPanel(ReadOnlyDungeonModel model) {
    treasureList = new ArrayList<>();
    this.addImage();
    this.model = model;
    this.shootFlag = -2;
    this.moveFlag = -2;
  }

  private void addImage() {
    try {
      diamond1 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/diamond.png")));
      ruby1 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/ruby.png")));
      sapphire1 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/emerald.png")));
      whiteArrow1 = ImageIO.read(Objects.requireNonNull(getClass().getResource(
              "/arrow-white.png")));
    } catch (IOException ie) {
      JOptionPane.showMessageDialog(this,
              "Cant read input file", "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  public void setShootFlag(int shootFlag) {
    this.shootFlag = shootFlag;
  }

  public void resetShootFlag() {
    this.shootFlag = -2;
  }

  public void setMoveFlag(int moveFlag) {
    this.moveFlag = moveFlag;
  }

  public void resetMoveFlag() {
    this.moveFlag = -5;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    int arrowCount = 0;
    int rubyCount = 0;
    int sapphireCount = 0;
    int diamondCount = 0;
    setBackground(Color.BLACK);
    setSize(super.getWidth(), super.getHeight());
    Graphics2D g2d = (Graphics2D) g;
    g2d.setColor(Color.ORANGE);
    g2d.setFont(new Font("Monospaced", Font.PLAIN, 15));
    if (model.getPlayer() == 1) {
      g2d.drawString(model.playerLocationDetails(), 95, 50);
      arrowCount = model.getPlayerObject().getArrowList().size();
      g2d.drawString("You have " + arrowCount, 105, 80);
      g2d.drawImage(whiteArrow1, 220, 65, 30, 20, null);
      treasureList = model.getPlayerObject().getTreasureList();
      for (Treasure treasure : treasureList) {
        if (treasure == Treasure.DIAMONDS) {
          diamondCount++;
        } else if (treasure == Treasure.SAPPHIRES) {
          sapphireCount++;
        } else if (treasure == Treasure.RUBIES) {
          rubyCount++;
        }
      }
      g2d.drawString("You have " + diamondCount, 105, 110);
      g2d.drawImage(diamond1, 220, 95, 20, 20, null);
      g2d.drawString("You have " + rubyCount, 105, 140);
      g2d.drawImage(ruby1, 220, 125, 20, 20, null);
      g2d.drawString("You have " + sapphireCount, 105, 170);
      g2d.drawImage(sapphire1, 220, 155, 20, 20, null);
    } else if (model.getPlayer() == 0) {
      g2d.setColor(Color.RED);
      g2d.drawString("Oops! You've been killed by the monster! Better luck next time!", 50, 50);
    }
    if (model.getPlayer() == 1 && model.isGameOver()) {
      g2d.setBackground(Color.BLACK);
      g2d.setFont(new Font("Monospaced", Font.ITALIC, 30));
      g2d.setColor(Color.GREEN);
      g2d.drawString("Congratulations! You slayed the monster and won the game!!",
              105, 250);
    }
    if (shootFlag == 1) {
      g2d.setColor(Color.GREEN);
      g2d.drawString("Way to go ! You have slayed the otyugh! ", 105, 200);
    } else if (shootFlag == 2) {
      g2d.setColor(Color.ORANGE);
      g2d.drawString("You have damaged the otyugh! Shoot wisely!", 105, 200);
    } else if (shootFlag == 3) {
      g2d.setColor(Color.RED);
      g2d.drawString("Arrow misses the monster", 105, 200);
    } else if (shootFlag == 0) {
      g2d.setColor(Color.RED);
      g2d.drawString("No more arrows left to shoot :(", 105, 200);
    } else if (shootFlag == -1) {
      g2d.setColor(Color.RED);
      g2d.drawString("Arrow misses the monster", 105, 200);
    }
    if (moveFlag == 5 || moveFlag == 6) {

      g2d.setColor(Color.RED);
      g2d.drawString("You just got robbed", 105, 210);
    }
  }


}

