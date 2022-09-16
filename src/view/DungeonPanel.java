package view;

import dungeon.Directions;
import dungeon.Location;
import dungeon.Smell;
import dungeon.Treasure;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


/**
 * Class that extends JPanel and helps in creating the dungeon grid. This class particularly
 * The paint components paints a grid of dimensions given by the user and overlays images
 * of treasure, player, arrows and monsters. Depending on the player moving the paint method
 * draws images only as the player explores the dungeon. The dungeon panel grid is created when the
 * this class's object is instantiated in the view class and is set a preferred size.
 */
class DungeonPanel extends JPanel {

  private final ReadOnlyDungeonModel model;
  private BufferedImage myPicture = null;
  private BufferedImage cave1;
  private BufferedImage cave2;
  private BufferedImage cave3;
  private BufferedImage cave4;
  private BufferedImage cave5;
  private BufferedImage cave6;
  private BufferedImage cave7;
  private BufferedImage cave8;
  private BufferedImage player;
  private BufferedImage cave9;
  private BufferedImage otyugh;
  private BufferedImage strongSmell;
  private BufferedImage weakSmell;
  private BufferedImage tunnel1;
  private BufferedImage tunnel2;
  private BufferedImage tunnel3;
  private BufferedImage tunnel4;
  private BufferedImage tunnel5;
  private BufferedImage tunnel6;
  private BufferedImage diamond1;
  private BufferedImage ruby1;
  private BufferedImage thief;
  private BufferedImage sapphire1;
  private BufferedImage whiteArrow1;
  private Map<List<Directions>, BufferedImage> imageMap;

  /**
   * Constructs a panel for the dungeon grid with a grid layout. Initialises the read only model
   * and uses the instance to access public facing methods that the view uses. Adds images using
   * the private method that creates objects to add paths to images. Sets the background colour
   * to this panel and also sets a grid layout for this grid panel.
   *
   * @param model Read only dungeon model
   */
  public DungeonPanel(ReadOnlyDungeonModel model) {
    super();
    setBackground(Color.darkGray);
    setLayout(new GridLayout(10, 10, 0, 0));
    this.addImage();
    this.createImageMap();
    this.model = model;

  }

  private void addImage() {
    try {
      myPicture = ImageIO.read(Objects.requireNonNull(getClass().getResource("/black.png")));
      cave1 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/NSE.png")));
      cave2 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/NEW.png")));
      cave3 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/SEW.png")));
      cave4 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/NSW.png")));
      cave5 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/E.png")));
      cave6 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/N.png")));
      cave7 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/S.png")));
      cave8 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/W.png")));
      cave9 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/NSEW.png")));
      tunnel1 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/SE.png")));
      tunnel2 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/EW.png")));
      tunnel3 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/NE.png")));
      tunnel4 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/NW.png")));
      tunnel5 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/SW.png")));
      tunnel6 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/NS.png")));
      player = ImageIO.read(Objects.requireNonNull(getClass().getResource("/player.png")));
      diamond1 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/diamond.png")));
      ruby1 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/ruby.png")));
      thief = ImageIO.read(Objects.requireNonNull(getClass().getResource("/thief.png")));
      sapphire1 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/emerald.png")));
      whiteArrow1 = ImageIO.read(Objects.requireNonNull(getClass().getResource(
              "/arrow-white.png")));
      otyugh = ImageIO.read(Objects.requireNonNull(getClass().getResource("/otyugh.png")));
      strongSmell = ImageIO.read(Objects.requireNonNull(getClass().getResource("/stench02.png")));
      weakSmell = ImageIO.read(Objects.requireNonNull(getClass().getResource("/stench01.png")));
    } catch (IOException ie) {
      JOptionPane.showMessageDialog(this, "Cant read input file",
              "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  private void createImageMap() {
    List<Directions> directionList = new ArrayList<>();
    List<Directions> directList;
    List<List<Directions>> entireList = new ArrayList<>();
    imageMap = new HashMap<>();
    for (Directions directions : Directions.values()) {
      directionList.add(directions);
    }
    List<Directions> tList = new ArrayList<>();
    tList.add(directionList.get(0));
    imageMap.put(tList, cave6);
    tList = new ArrayList<>();
    tList.add(directionList.get(1));
    imageMap.put(tList, cave7);
    tList = new ArrayList<>();
    tList.add(directionList.get(2));
    imageMap.put(tList, cave8);
    tList = new ArrayList<>();
    tList.add(directionList.get(3));
    imageMap.put(tList, cave5);
    imageMap.put(directionList, cave9);

    for (int i = 0; i < 4; i++) {
      directList = new ArrayList<>();
      for (int j = 0; j < 4; j++) {
        if (i != j) {
          directList.add(directionList.get(j));
        }
      }
      entireList.add(directList);
    }
    imageMap.put(entireList.get(0), cave3);
    imageMap.put(entireList.get(1), cave2);
    imageMap.put(entireList.get(2), cave1);
    imageMap.put(entireList.get(3), cave4);
    entireList = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
      directList = new ArrayList<>();
      for (int j = i + 1; j < 4; j++) {
        directList.add(directionList.get(i));
        directList.add(directionList.get(j));
        entireList.add(directList);
        directList = new ArrayList<>();
      }

    }
    imageMap.put(entireList.get(0), tunnel6);
    imageMap.put(entireList.get(1), tunnel4);
    imageMap.put(entireList.get(2), tunnel3);
    imageMap.put(entireList.get(3), tunnel5);
    imageMap.put(entireList.get(4), tunnel1);
    imageMap.put(entireList.get(5), tunnel2);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    g2d.setFont(new Font("Monospaced", Font.PLAIN, 15));
    for (int i = 0; i < model.getRow(); i++) {
      for (int j = 0; j < model.getCol(); j++) {
        if (myPicture != null) {
          g2d.drawImage(myPicture, (j + 1) * 100, (i + 1) * 100, 100, 100,
                  null);
        }
      }
    }
    for (Map.Entry<Location, List<Directions>> entry : model.getState().entrySet()) {
      List<Directions> tempList = entry.getValue();
      Collections.sort(tempList);
      g2d.drawImage(imageMap.get(tempList), (entry.getKey().getY() + 1) * 100,
              (entry.getKey().getX() + 1) * 100, 100, 100, null);

      if (entry.getKey().getTreasureList().size() > 0) {
        if (entry.getKey().getTreasureList().contains(Treasure.RUBIES)) {
          g2d.drawImage(ruby1, (entry.getKey().getY() + 1) * 100 + 15,
                  (entry.getKey().getX() + 1) * 100 + 15, 25, 25, null);
        }
        if (entry.getKey().getTreasureList().contains(Treasure.DIAMONDS)) {
          g2d.drawImage(diamond1, (entry.getKey().getY() + 1) * 100 + 60,
                  (entry.getKey().getX() + 1) * 100, 25, 25, null);
        }
        if (entry.getKey().getTreasureList().contains(Treasure.SAPPHIRES)) {
          g2d.drawImage(sapphire1, (entry.getKey().getY() + 1) * 100 + 60,
                  (entry.getKey().getX() + 1) * 100 + 60, 25, 25, null);
        }
      }
      if (entry.getKey().getArrowList().size() > 0) {
        g2d.drawImage(whiteArrow1, (entry.getKey().getY() + 1) * 100 + 10,
                (entry.getKey().getX() + 1) * 100 + 40, 20, 20, null);
      }

      if (entry.getKey().isMonster()) {
        g2d.drawImage(otyugh, (entry.getKey().getY() + 1) * 100 + 30,
                (entry.getKey().getX() + 1) * 100 + 30, 50, 50, null);
      }

      if (entry.getKey().isThief()) {
        g2d.drawImage(thief, (entry.getKey().getY() + 1) * 100,
                (entry.getKey().getX() + 1) * 100 + 60, 40, 40, null);
      }
    }
    if (model.getPlayer() == 1) {
      g2d.drawImage(player, (model.getStartCave().getY() + 1) * 100 + 26,
              (model.getStartCave().getX() + 1) * 100 + 26, 50, 50, null);
      if (model.getSmell() == Smell.LESS_PUNGENT) {
        g2d.drawImage(weakSmell, (model.getStartCave().getY() + 1) * 100,
                (model.getStartCave().getX() + 1) * 100, 100, 100, null);
      }
      if (model.getSmell() == Smell.MORE_PUNGENT) {
        g2d.drawImage(strongSmell, (model.getStartCave().getY() + 1) * 100,
                (model.getStartCave().getX() + 1) * 100, 100, 100, null);
      }
    }
  }
}


