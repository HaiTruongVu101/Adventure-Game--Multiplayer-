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
        mapNum = new int[gp.maxWorldCol][gp.maxWorldRow];

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
        for (int row = 0; row < gp.maxWorldRow; row++) {
            String lineText = br.readLine();
            String[] numbers = lineText.split(" ");
            for (int col = 0; col < gp.maxWorldCol; col++) {
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

        int Worldcol = 0;
        int Worldrow = 0;
        

        while(Worldcol < gp.maxWorldCol && Worldrow < gp.maxWorldRow) {

            int blockNum = mapNum[Worldcol][Worldrow];

            int World_x = Worldcol * gp.tileSize;
            int World_y = Worldrow * gp.tileSize;
            int Screen_x = World_x - gp.player.World_x + gp.player.Screen_x;
            int Screen_y = World_y - gp.player.World_y + gp.player.Screen_y;

            g2.drawImage(bl[blockNum].image, Screen_x, Screen_y, gp.tileSize, gp.tileSize, null);
            Worldcol++;
            

            if (Worldcol == gp.maxWorldCol) {
    Worldcol = 0;
    Worldrow++;
}
                
            }
        }
    }
