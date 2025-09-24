package main;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import java.awt.Color;
import Characters.player;
import block.blockManager;
import Characters.javaClient;
import java.util.HashMap;
import javax.swing.JOptionPane;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class GamePanel extends JPanel implements Runnable{
    
    // SCREEN SETTINGS

    javaClient client;
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48x48 tile
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels

    blockManager blockM = new blockManager(this);
    Key_handler H = new Key_handler();
    Thread gameThread;
    String mySprite;
    player player = new player(this, H, mySprite);
    


    // FPS counters
    int currentFps = 0;
    int frameCount = 0;


    
    HashMap<String, Object[]> otherPlayers = new HashMap<>();
    String myId = String.valueOf(System.currentTimeMillis() + (int)(Math.random()*10000));

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // for better rendering performance
        this.addKeyListener(H);
        this.setFocusable(true);
        this.addFocusListener(new FocusListener() {
        @Override
        public void focusGained(FocusEvent e) {}

        @Override
        public void focusLost(FocusEvent e) {
            H.reset();
        }
    });


      
        JLabel 
        String[] choosing = {"Blue", "Red"};
        int choice  = JOptionPane.showOptionDialog(
            null,
            ,
            "Character Selection",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.DEFAULT_OPTION,
            null,
            choosing,
            choosing[0]
        );
       
        if (choice == JOptionPane.CLOSED_OPTION) {
            System.exit(0);
        }
         mySprite = choosing[choice];

        player = new player(this, H, mySprite);
        client = javaClient.createOrNull();
        

    }


    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }


    @Override

    // public void run() {

    //     int fps = 60;
    //     double drawInterval = 1000000000.0/fps;
    //     double nextDrawTime = System.nanoTime() + drawInterval;
        
    //     while (gameThread != null) {
            
            
          
    //     update();
    //                                                                                                  ALL OF THIS IS A DIFFERENT WAY                                                                                                                    


    //     repaint();

    //         try {
    //     double remainingTime = nextDrawTime - System.nanoTime();
    //     remainingTime = remainingTime / 10000000;

    //     if (remainingTime < 0) {
    //         remainingTime = 0;
    //     }

    //     Thread.sleep((long) remainingTime);

    //     nextDrawTime += drawInterval;

    //         } catch (InterruptedException e) {
    //             e.printStackTrace();

    //         }
        
    //     }
    // }
    public void run() {

        double drawInterval = 1000000000.0 / 60.0; // nanoseconds per frame (60 FPS)
        double delta = 0;
        long lastTime = System.nanoTime();
        long timer = 0;
        int frameCount = 0;
        

        while (gameThread != null) {
            long currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;


            while (delta >= 1) {
                update();      // game logic
                repaint();     // render
                delta--;
                frameCount++;
            }

            // Optional FPS print (once per second)
            if (timer >= 1000000000) {
             System.out.println("FPS: " + frameCount);
                frameCount = 0;
                timer = 0;
                
                
            }
        }
    }
    public void update() {
        player.update();
        String data = null;
        try {
            if (client != null) {
                data = client.getLastReceived();
                client.sendMessage(myId + ":" + player.x + "," + player.y + "," + mySprite + "," + player.direction + "," + player.walkingNum);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    otherPlayers.clear();
    if (data != null && !data.isEmpty()) {
        String[] players = data.split(";");
        for (String p : players) {
            String[] parts = p.split(":");
            if (parts.length == 2) {
                String id = parts[0];
                String[] xy = parts[1].split(",");
                if (xy.length >= 2) {
    try {
        int ox = Integer.parseInt(xy[0]);
        int oy = Integer.parseInt(xy[1]);
        String spriteType = (xy.length >= 3) ? xy[2] : "blue";
        String direction = (xy.length >= 4) ? xy[3] : "down";
        int walkingNum = (xy.length >= 5) ? Integer.parseInt(xy[4]) : 1;
    if (!id.equals(client.getID())) {
        otherPlayers.put(id, new Object[]{ox, oy, spriteType, direction, walkingNum});
}
            } catch (NumberFormatException ignored) {}
}
            }
        }
    }


    }
    

    public void paintComponent(Graphics g){

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        blockM.draw(g2);
        player.draw(g2);
        
        g2.setColor(Color.RED);
    for (Object[] data : otherPlayers.values()) {
    int ox = (int) data[0];
    int oy = (int) data[1];
    String spriteType = (String) data[2];
    String direction = (String) data[3];
    int walkingNum = (int) data[4];

    player tempPlayer = new player(this, null, spriteType);
    tempPlayer.x = ox;
    tempPlayer.y = oy;
    tempPlayer.direction = direction;
    tempPlayer.walkingNum = walkingNum;
    tempPlayer.draw(g2);
    
    
        }
     g2.dispose();
    }
        

}






