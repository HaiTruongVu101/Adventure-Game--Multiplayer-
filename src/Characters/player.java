package Characters;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.Key_handler;

public class player extends characs {

    GamePanel gp;
    Key_handler H;
    String spriteType = "red";

    // Red sprite images
    BufferedImage up1r, up2r, down1r, down2r, left1r, left2r, right1r, right2r;


    public player(GamePanel gp, Key_handler H, String spriteType) {
    
        this.gp = gp;
        this.H = H;
        this.spriteType = spriteType;

        Values();
        getImage();
    }

    public void Values() {

        
    if ("red".equals(spriteType)) {
        x = 300;
        y = 100;
    } else {
        x = 100;
        y = 100;
    }
    speed = 3;
    direction = "right";
    walkingNum = 1;

    }

    public void getImage() {
        try {

            //BLUE BOY
            up1 = ImageIO.read(getClass().getResourceAsStream("/pics/player/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/pics/player/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/pics/player/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/pics/player/boy_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/pics/player/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/pics/player/boy_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/pics/player/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/pics/player/boy_right_2.png"));






            //RED BOY
            up1r = ImageIO.read(getClass().getResourceAsStream("/pics/player2/redboy_up1.png"));
            up2r = ImageIO.read(getClass().getResourceAsStream("/pics/player2/redboy_up2.png"));
            down1r = ImageIO.read(getClass().getResourceAsStream("/pics/player2/redboy_down1.png"));
            down2r = ImageIO.read(getClass().getResourceAsStream("/pics/player2/redboy_down2.png"));
            left1r = ImageIO.read(getClass().getResourceAsStream("/pics/player2/redboy_left1.png"));
            left2r = ImageIO.read(getClass().getResourceAsStream("/pics/player2/redboy_left2.png"));
            right1r = ImageIO.read(getClass().getResourceAsStream("/pics/player2/redboy_right1.png"));
            right2r = ImageIO.read(getClass().getResourceAsStream("/pics/player2/redboy_right2.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
            
    }

    

   public void update() {
    boolean moving = false;

    if(H.upPressed) {
        direction = "up";
        y -= speed;
        moving = true;
    }
    else if(H.downPressed) {
        direction = "down";
        y += speed;
        moving = true;
    }
    else if(H.leftPressed) {
        direction = "left";
        x -= speed;
        moving = true;
    }
    else if(H.rightPressed) {
        direction = "right";
        x += speed;
        moving = true;
    }

    if(moving) {
        walkingCounter++;
        if(walkingCounter > 12) { // Lower = faster animation, higher = slower
            walkingNum = (walkingNum == 1) ? 2 : 1;
            walkingCounter = 0;
        }
    } else {
        walkingNum = 1;         // Standstill frame
        walkingCounter = 0;
    }
}


        

    public void draw(Graphics2D g2) {
    BufferedImage image = null;

    if("red".equals(spriteType)) {
        switch(direction) {
            case "up": image = (walkingNum == 1) ? up1r : up2r; break;
            case "down": image = (walkingNum == 1) ? down1r : down2r; break;
            case "left": image = (walkingNum == 1) ? left1r : left2r; break;
            case "right": image = (walkingNum == 1) ? right1r : right2r; break;
        }
    } else {
        switch(direction) {
            case "up": image = (walkingNum == 1) ? up1 : up2; break;
            case "down": image = (walkingNum == 1) ? down1 : down2; break;
            case "left": image = (walkingNum == 1) ? left1 : left2; break;
            case "right": image = (walkingNum == 1) ? right1 : right2; break;
        }
    }
    g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
}
        
       

    }
