package block;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;

public class blockManager {
    GamePanel gp;
    block[] bl;
    int mapNum[][];

    public blockManager(GamePanel gp) {

        this.gp = gp;

        bl = new block[10];
        mapNum = new int[gp.maxScreenCol][gp.maxScreenRow];

        getBlockImage();
        loadMap();

    }

    public void getBlockImage() {

        try {

            bl[0] = new block();
            bl[0].image = ImageIO.read(getClass().getResourceAsStream("/pics/block/grass.png"));

            bl[1] = new block();
            bl[1].image = ImageIO.read(getClass().getResourceAsStream("/pics/block/wall.png"));

            bl[2] = new block();
            bl[2].image = ImageIO.read(getClass().getResourceAsStream("/pics/block/water.png"));

            bl[3] = new block();
            bl[3].image = ImageIO.read(getClass().getResourceAsStream("/pics/block/dirt.png"));

            bl[4] = new block();
            bl[4].image = ImageIO.read(getClass().getResourceAsStream("/pics/block/sand.png"));

            bl[5] = new block();
            bl[5].image = ImageIO.read(getClass().getResourceAsStream("/pics/block/tree.png"));

        } catch(IOException e) {
            e.printStackTrace();
        }
    }


   public void loadMap() {
    try {
        InputStream ml = getClass().getResourceAsStream("/pics/maps/map(2).txt");
        if (ml == null) {
            System.out.println("Map file not found!");
            return;
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(ml));
        for (int row = 0; row < gp.maxScreenRow; row++) {
            String lineText = br.readLine();
            String[] numbers = lineText.split(" ");
            for (int col = 0; col < gp.maxScreenCol; col++) {
                int num = Integer.parseInt(numbers[col]);
                mapNum[col][row] = num;
            }
        }
        br.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    public void draw(Graphics2D g2) {

        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while(col < gp.maxScreenCol && row < gp.maxScreenRow) {

            int blockNum = mapNum[col][row];

            g2.drawImage(bl[blockNum].image, x, y, gp.tileSize, gp.tileSize, null);
            col++;
            x = x + gp.tileSize;

            if (col == gp.maxScreenCol) {
                col = 0;
                x = 0;
                row++;
                y = y + gp.tileSize;
            }
        }
    }
}