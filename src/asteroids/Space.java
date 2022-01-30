/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JPanel;
import java.io.*;
import java.net.URL;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.ImageIcon;

/**
 *
 * @author borenste_848114
 */
public class Space extends JPanel {

    private long startTime = 0;
    public static final int BOARD_WIDTH = 640;
    public static final int BOARD_HEIGHT = 640;
    private final int INITIAL_DELAY = 100;
    private final int PERIOD_INTERVAL = 1000 / 60;
    private Timer timer;
    private boolean isStarted = false;
    private boolean isPaused = false;
    private long cycleCount;

    public SpaceShip player;
    public int lives;
    public ArrayList<Asteroid> rubble;
    public ArrayList<Laser> lasers;
    public ArrayList<Alien> aliens;
    public ArrayList<Particle> vacuum;
    public long score;
    public long displayScore;
    public long scoreForLives;
    public int level;
    public int playerRotate;
    public boolean thrust;
    public boolean toThrustOrNotToThrust;
    public boolean fire;
    public boolean showControls;
    public long levelDelay;
    public boolean levelDelayOn;
    public boolean displayNextLevel;
    public boolean gameOver;
    public double bigAlienChance = 0;
    public double smallAlienChance = 0;
    public boolean beat1 = true;
    public int gameOverCountdown = 30;
    public HighScoreList highScores;
    public Settings settings;
    public boolean previewOn;
    public boolean highScoreInput;
    public boolean highScoreDisplay;
    public String[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".split("");
    public String[] numberLine = "0123456789".split("");
    public int initialInput;
    public int initialLetter;
    public int[] name;
    public float alienTimer;
    public float alienTime;
    public int totalAsteroids;
    public float maxDelay;
    public float minDelay;
    public float currentDelay;
    public float bgTimer;
    public boolean playLowPulse;
    public long menuSelect;
    public long menuOptions;
    public float controlsPage;
    public float highScorePage;
    public boolean showSettings;
    public float settingSelect;
    public boolean numberInput;
    public boolean programStarted;
    public long beginProgram;
    final Image logo;
    public long hyperSpaceCoolDown;
    public long hyperSpaceTimer;
    public boolean hyperSpaceOn;
    private float alpha = 0;
    private final boolean printIt = false;
    public boolean hasWon;
    public double alienSoundTime = 0;
    public boolean addNewHighScoreScoreInput = false;
    public boolean deleteHighScore = false;
    public boolean clearHighScores = false;
    public boolean timerChange = false;
    public Color lineColor;
    public Color controlColor;
    public boolean youStillThereQuestioning = false;
    public boolean wentThereButLeft = false;
    public boolean giveUpWaiting = false;
    public boolean rageIncreaseLevelDifficulty = false;
    public boolean gauntletOn = false;
    public int gauntletLivesLost = 0;
    public boolean onceRaged = false;
    public boolean doneWithRageText = false;
    public boolean aprilFools = false;
    public Color lastColor;
    public static boolean showHitBoxes = false;
    public boolean konami = false;
    public int konamiProgress = 0;
    public boolean doingCycle = false;
    public int[][] chunkLoad;
    public boolean addLife;
    public boolean justAddedLife;
    public boolean justLostLife;
    public SpaceShip lostLife;
    public int livesSpent;
    public boolean firstGame;
    public Background bckgrnd;

    public Space(Asteroids parent) throws IOException {
        initBoard();
        firstGame = true;
        startTime = -1;
        programStarted = false;
        beginProgram = 0;
        Date date = new Date();
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        if (calendar.get(Calendar.MONTH) == 3 && calendar.get(Calendar.DAY_OF_MONTH) == 1) {
            URL url = getClass().getResource("resources/images/Sega_logo.png");
            logo = new ImageIcon(url).getImage();
        } else {
            URL url = getClass().getResource("resources/images/Atari_Logo.jpeg");
            logo = new ImageIcon(url).getImage();
        }
        timer = new Timer();
        timer.scheduleAtFixedRate(new ScheduleTask(),
                INITIAL_DELAY, PERIOD_INTERVAL);
        addKeyListener(new TAdapter());
    }

    public void proceedInitialProgram() {
        beginProgram++;
        if (beginProgram == 90) {
            Date date = new Date();
            Calendar calendar = GregorianCalendar.getInstance();
            calendar.setTime(date);
            if (calendar.get(Calendar.MONTH) == 3 && calendar.get(Calendar.DAY_OF_MONTH) == 1) {
                SoundEffect.INTRO_SEGA.play();
            } else {
                SoundEffect.INTRO.play();
            }
        }
        repaint();
        if (beginProgram > (6 * 60)) {
            initBoard();
            preview();
            String i = getCurrentColor();
            settings.setLineColor(settings.getLineColor());
            programStarted = true;
        }
    }

    public long getBeginProgram() {
        return beginProgram;
    }

    private void initBoard() {
        bckgrnd = new Background(250);
        addLife = false;
        justAddedLife = false;
        chunkLoad = new int[10][10];
        doneWithRageText = false;
        gauntletOn = false;
        gauntletLivesLost = 0;
        onceRaged = false;
        programStarted = true;
        hasWon = false;
        hyperSpaceTimer = 0;
        setFocusable(true);
        resetMenu(1);
        numberInput = false;
        hyperSpaceOn = false;
        settingSelect = -1;
        showSettings = false;
        maxDelay = 1;
        minDelay = .1f;
        currentDelay = maxDelay;
        bgTimer = maxDelay;
        playLowPulse = true;
        alienTime = 15;
        alienTimer = 0;
        previewOn = false;
        cycleCount = 0;
        if (!highScoreInput) {
            score = 0;
            displayScore = 0;
        }
        level = 1;
        lasers = new ArrayList<>();
        rubble = new ArrayList<>();
        aliens = new ArrayList<>();
        vacuum = new ArrayList<>();
        playerRotate = 0;
        thrust = false;
        toThrustOrNotToThrust = true;
        fire = false;
        showControls = false;
        lives = 5;
        levelDelay = -1000;
        levelDelayOn = false;
        displayNextLevel = false;
        gameOver = false;
        scoreForLives = 0;
        if (!highScoreInput) {
            highScoreInput = false;
        }
        isStarted = false;
        resetHighScores();
        resetSettings();
        save();
        hyperSpaceCoolDown = (settings.getHyperSpaceChargeTime() * 60);
        lineColor = settings.getLineColor();
        lastColor = settings.getLineColor();
    }

    public void addScore(long s) {
        score += s;
        scoreForLives += s;
        if (scoreForLives >= 10000) {
            scoreForLives = scoreForLives % 10000;
            addLife = true;
        }
    }

    public void preview() {
        previewOn = true;
        showMenu();
        cycleCount = 0;
        levelDelay = 0;
        for (int i = 0; i < 25 + (Math.random() * 15); i++) {
            int j = (int) (Math.random() * 3);
            if (j == 0) {
                rubble.add(new Asteroid(Math.random() * BOARD_WIDTH,
                        Math.random() * BOARD_HEIGHT, Math.random() * 360));
            } else if (j == 1) {
                rubble.add(new Meteor(Math.random() * BOARD_WIDTH,
                        Math.random() * BOARD_HEIGHT, Math.random() * 360));
                i--;
            } else {
                rubble.add(new Meteorite(Math.random() * BOARD_WIDTH,
                        Math.random() * BOARD_HEIGHT, Math.random() * 360));
                i--;
            }
            if (i < -25) {
                i = 10;
            }
        }
    }

    public void start() {
        initBoard();
        previewOn = false;
        levelDelayOn = false;
        highScoreDisplay = false;
        player = new SpaceShip();
        lives = (int) settings.getStartingLives();
        gameOver = false;
        livesSpent = 0;
        levelDelayOn = false;
        rubble.clear();
        if (konami) {
            score = 99900;
            displayScore = 99900;
            scoreForLives = 99900 % 10000;
            konami = false;
            konamiProgress = 0;
            level = 9;
        }
        createAsteroids();
        print("\nNew Game", true, false);
        startTime = System.currentTimeMillis();
        isStarted = true;
    }

    private void pause() {
        System.out.println("Pause Flopped!");
        if (!isStarted) {
            return;
        }
        isPaused = !isPaused;
        if (isPaused) {
            print("Paused", false, true);
        } else {
            showControls = false;
            print("Unpaused", true, false);
        }
    }

    private void resetMenu(int i) {
        menuOptions = i;
        menuSelect = menuOptions * 10;
        if (menuOptions <= 0) {
            menuOptions = 1;
        }
        controlsPage = 1;
        highScorePage = 1;
    }

    private void doDrawing(Graphics g) {
        if (lineColor != Color.BLACK) {
            g.setColor(Color.BLACK);
        } else {
            g.setColor(Color.WHITE);
        }
        g.fillRect(0, 0, 9999, 9999);
        g.setColor(lineColor);
        if (!programStarted) {
            drawInitial(g);
        } else {
            drawNormal(g);
        }
        if (konami) {
            drawString(g, "Konami Code Activated",
                    center("Konami Code Activated"), 10);
            g.drawLine(center("Konami Code Activated") - 5, 55,
                    (BOARD_WIDTH - center("Konami Code Activated")), 55);
        }
    }

    public String getHolidays() {
        Date date = new Date();
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        String i = "";
        if (calendar.get(Calendar.MONTH) == 3 && calendar.get(Calendar.DAY_OF_MONTH) == 1) {
            if (i.length() > 0) {
                i += "@New_Line";
            }
            i += "Happy April Fools Day!";
        }
        if (calendar.get(Calendar.MONTH) == 10 && calendar.get(Calendar.DAY_OF_MONTH) == 31) {
            if (i.length() > 0) {
                i += "@New_Line";
            }
            i += "Happy Halloween!";
        }
        if (calendar.get(Calendar.MONTH) == 12 && calendar.get(Calendar.DAY_OF_MONTH) == 24) {
            if (i.length() > 0) {
                i += "@New_Line";
            }
            i += "Merry Christmas Eve";
        }
        if (calendar.get(Calendar.MONTH) == 12 && calendar.get(Calendar.DAY_OF_MONTH) == 25) {
            if (i.length() > 0) {
                i += "@New_Line";
            }
            i += "Merry Christmas!";
        }
        if (calendar.get(Calendar.MONTH) == 12 && calendar.get(Calendar.DAY_OF_MONTH) == 31) {
            if (i.length() > 0) {
                i += "@New_Line";
            }
            i += "Happy New Years Eve";
        }
        if (calendar.get(Calendar.MONTH) == 12 && calendar.get(Calendar.DAY_OF_MONTH) == 31) {
            if (i.length() > 0) {
                i += "@New_Line";
            }
            i += "My Creator's Birthday!";
        }
        if (calendar.get(Calendar.MONTH) == 1 && calendar.get(Calendar.DAY_OF_MONTH) == 1) {
            if (i.length() > 0) {
                i += "@New_Line";
            }
            i += "Happy New Years Day!";
        }
        if (calendar.get(Calendar.MONTH) == 2 && calendar.get(Calendar.DAY_OF_MONTH) == 14) {
            if (i.length() > 0) {
                i += "@New_Line";
            }
            i += "Happy Valentine's";
        }
        if (calendar.get(Calendar.MONTH) == 11 && calendar.get(Calendar.DAY_OF_MONTH) == 28) {
            if (i.length() > 0) {
                i += "@New_Line";
            }
            i += "Happy Thanksgiving!";
        }
        if (calendar.get(Calendar.MONTH) == 7 && calendar.get(Calendar.DAY_OF_MONTH) == 4) {
            if (i.length() > 0) {
                i += "@New_Line";
            }
            i += "Happy 4th of July!";
        }
        if (calendar.get(Calendar.MONTH) == 5 && calendar.get(Calendar.DAY_OF_MONTH) == 4) {
            if (i.length() > 0) {
                i += "@New_Line";
            }
            i += "May the 4th be with you!";
        }
        if (calendar.get(Calendar.MONTH) == 5 && calendar.get(Calendar.DAY_OF_MONTH) == 5) {
            if (i.length() > 0) {
                i += "@New_Line";
            }
            i += "Happy Cinco de Mayo";
        }
        if (calendar.get(Calendar.MONTH) == 4 && calendar.get(Calendar.DAY_OF_MONTH) == 19) {
            if (i.length() > 0) {
                i += "@New_Line";
            }
            i += "Yay! Good Friday!";
        }
        if (calendar.get(Calendar.MONTH) == 4 && calendar.get(Calendar.DAY_OF_MONTH) == 22) {
            if (i.length() > 0) {
                i += "@New_Line";
            }
            i += "Happy Easter!";
        }
        if (calendar.get(Calendar.MONTH) == 1 && calendar.get(Calendar.DAY_OF_MONTH) == 24) {
            if (i.length() > 0) {
                i += "@New_Line";
            }
            i += "Happy BellyLaugh Day!";
        }
        if (calendar.get(Calendar.MONTH) == 1 && calendar.get(Calendar.DAY_OF_MONTH) == 21) {
            if (i.length() > 0) {
                i += "@New_Line";
            }
            i += "Happy MLK Day!";
        }
        if (calendar.get(Calendar.MONTH) == 2 && calendar.get(Calendar.DAY_OF_MONTH) == 2) {
            if (i.length() > 0) {
                i += "@New_Line";
            }
            i += "Happy GroundHog Day!";
        }
        if (calendar.get(Calendar.MONTH) == 2 && calendar.get(Calendar.DAY_OF_MONTH) == 12) {
            if (i.length() > 0) {
                i += "@New_Line";
            }
            i += "Lincon's BDay's Today!";
        }
        if (calendar.get(Calendar.MONTH) == 2 && calendar.get(Calendar.DAY_OF_MONTH) == 18) {
            if (i.length() > 0) {
                i += "@New_Line";
            }
            i += "Happy President's Day!";
        }
        if (calendar.get(Calendar.MONTH) == 2 && calendar.get(Calendar.DAY_OF_MONTH) == 18) {
            if (i.length() > 0) {
                i += "@New_Line";
            }
            i += "as well as @New_LineGeorge Washington's BDay";
        }
        if (calendar.get(Calendar.MONTH) == 3 && calendar.get(Calendar.DAY_OF_MONTH) == 5) {
            if (i.length() > 0) {
                i += "@New_Line";
            }
            i += "Merry Mardi Gras!";
        }
        if ((calendar.get(Calendar.MONTH) == 2 && calendar.get(Calendar.DAY_OF_MONTH) == 28)
                || (calendar.get(Calendar.MONTH) == 11 && calendar.get(Calendar.DAY_OF_MONTH) == 3)) {
            if (i.length() > 0) {
                i += "@New_Line";
            }
            i += "DayLight Savings!!!";
        }
        if (calendar.get(Calendar.MONTH) == 2 && calendar.get(Calendar.DAY_OF_MONTH) == 17) {
            if (i.length() > 0) {
                i += "@New_Line";
            }
            i += "Top of the Morning to ya!";
            i += "@New_Line";
            i += "Happy St.Pattys Day!";
        }
        if (calendar.get(Calendar.MONTH) == 4 && calendar.get(Calendar.DAY_OF_MONTH) == 22) {
            if (i.length() > 0) {
                i += "@New_Line";
            }
            i += "Happy Earth Day!";
        }
        if (calendar.get(Calendar.MONTH) == 5 && calendar.get(Calendar.DAY_OF_MONTH) == 12) {
            if (i.length() > 0) {
                i += "@New_Line";
            }
            i += "Happy Mother's Day!";
        }
        if (calendar.get(Calendar.MONTH) == 5 && calendar.get(Calendar.DAY_OF_MONTH) == 18) {
            if (i.length() > 0) {
                i += "@New_Line";
            }
            i += "It's Armed Forces Day!";
        }
        if (calendar.get(Calendar.MONTH) == 5 && calendar.get(Calendar.DAY_OF_MONTH) == 27) {
            if (i.length() > 0) {
                i += "@New_Line";
            }
            i += "Have a great@New_LineMemorial Day!";
        }
        if (calendar.get(Calendar.MONTH) == 6 && calendar.get(Calendar.DAY_OF_MONTH) == 9) {
            if (i.length() > 0) {
                i += "@New_Line";
            }
            i += "Happy Pentecost!";
        }
        if (calendar.get(Calendar.MONTH) == 6 && calendar.get(Calendar.DAY_OF_MONTH) == 14) {
            if (i.length() > 0) {
                i += "@New_Line";
            }
            i += "Happy Flag Day!";
        }
        if (calendar.get(Calendar.MONTH) == 6 && calendar.get(Calendar.DAY_OF_MONTH) == 16) {
            if (i.length() > 0) {
                i += "@New_Line";
            }
            i += "Happy Fathers Day!";
        }
        if (calendar.get(Calendar.MONTH) == 7 && calendar.get(Calendar.DAY_OF_MONTH) == 28) {
            if (i.length() > 0) {
                i += "@New_Line";
            }
            i += "Happy Parent's Day!";
        }
        if (calendar.get(Calendar.MONTH) == 9 && calendar.get(Calendar.DAY_OF_MONTH) == 2) {
            if (i.length() > 0) {
                i += "@New_Line";
            }
            i += "Happy Labor Day!";
        }
        if (calendar.get(Calendar.MONTH) == 9 && calendar.get(Calendar.DAY_OF_MONTH) == 8) {
            if (i.length() > 0) {
                i += "@New_Line";
            }
            i += "Happy GrandParent Day!";
        }
        if (calendar.get(Calendar.MONTH) == 9 && calendar.get(Calendar.DAY_OF_MONTH) == 16) {
            if (i.length() > 0) {
                i += "@New_Line";
            }
            i += "Happy StepFamily Day!";
        }
        if (calendar.get(Calendar.MONTH) == 9 && calendar.get(Calendar.DAY_OF_MONTH) == 11) {
            if (i.length() > 0) {
                i += "@New_Line";
            }
            i += "Happy Patriot Day!";
        }
        if (calendar.get(Calendar.MONTH) == 9 && calendar.get(Calendar.DAY_OF_MONTH) == 28) {
            if (i.length() > 0) {
                i += "@New_Line";
            }
            i += "Happy Citizenship Day!";
        }
        if (calendar.get(Calendar.MONTH) == 9 && calendar.get(Calendar.DAY_OF_MONTH) == 27) {
            if (i.length() > 0) {
                i += "@New_Line";
            }
            i += "Yay! Native Americans Day";
        }
        if (calendar.get(Calendar.MONTH) == 10 && calendar.get(Calendar.DAY_OF_MONTH) == 14) {
            if (i.length() > 0) {
                i += "@New_Line";
            }
            i += "Happy Columbus Day!";
        }
        if (calendar.get(Calendar.MONTH) == 10 && calendar.get(Calendar.DAY_OF_MONTH) == 16) {
            if (i.length() > 0) {
                i += "@New_Line";
            }
            i += "Happy Boss's Day!";
        }
        if (calendar.get(Calendar.MONTH) == 10 && calendar.get(Calendar.DAY_OF_MONTH) == 19) {
            if (i.length() > 0) {
                i += "@New_Line";
            }
            i += "Happy Sweetest Day!";
        }
        if (calendar.get(Calendar.MONTH) == 11 && calendar.get(Calendar.DAY_OF_MONTH) == 11) {
            if (i.length() > 0) {
                i += "@New_Line";
            }
            i += "Happy Veterans Day!";
        }
        if (calendar.get(Calendar.MONTH) == 10 && calendar.get(Calendar.DAY_OF_MONTH) == 14) {
            if (i.length() > 0) {
                i += "@New_Line";
            }
            i += "It's Black Friday!";
            i += "@New_Line";
            i += "Better playing me here";
            i += "@New_Line";
            i += "Than dying out there";
        }
        if (calendar.get(Calendar.MONTH) == 12 && calendar.get(Calendar.DAY_OF_MONTH) == 7) {
            if (i.length() > 0) {
                i += "@New_Line";
            }
            i += "Remember Pearl Harbor";
        }
        return i;
    }

    public double drawSmallString(Graphics g, String s, int x, int y) {
        int color = -1;
        s = s.toUpperCase();
        s = s.trim();
        String[] ss = s.split("");
        boolean numberPastColon = false;
        for (String i : ss) {
            color++;
            if (" ".equals(i)) {
                color--;
            }
            if (!i.equals(" ")) {
                Color c = g.getColor();
                if (lineColor != Color.BLACK) {
                    g.setColor(Color.BLACK);
                } else {
                    g.setColor(Color.WHITE);
                }
                g.fillRect(x - 5, y - 5, 10, 10);
                g.setColor(c);
            }
            if (lineColor.getRGB() == Color.PINK.getRGB() && programStarted) {
                if (color % 6 == 0) {
                    g.setColor(Color.MAGENTA);
                } else if (color % 6 == 1) {
                    g.setColor(Color.BLUE);
                } else if (color % 6 == 2) {
                    g.setColor(Color.YELLOW);
                } else if (color % 6 == 3) {
                    g.setColor(Color.GREEN);
                } else if (color % 6 == 4) {
                    g.setColor(Color.ORANGE);
                } else {
                    g.setColor(Color.RED);
                }
                if (!numberPastColon) {
                    if (i.equals(":")) {
                        numberPastColon = true;
                        g.setColor(Color.CYAN);
                    }
                } else {
                    if (s.contains("MENU TIMER")) {
                        if (gameOverCountdown > 20) {
                            g.setColor(Color.GREEN);
                        } else if (gameOverCountdown > 10) {
                            g.setColor(Color.YELLOW);
                        } else {
                            g.setColor(Color.RED);
                        }
                    } else if (s.contains("LIVES LOST")) {
                        if (score <= 25000) {
                            g.setColor(Color.RED);
                        } else if (score <= 50000) {
                            g.setColor(Color.ORANGE);
                        } else if (score <= 75000) {
                            g.setColor(Color.YELLOW);
                        } else {
                            g.setColor(Color.GREEN);
                        }
                    } else {
                        g.setColor(g.getColor());
                    }
                }
            } else if (!programStarted) {
                g.setColor(Color.WHITE);
            } else {
                g.setColor(lineColor);
            }

            switch (i) {
                case "0":
                    g.drawLine(x - 4, y - 5, x + 4, y - 5);
                    g.drawLine(x - 4, y - 5, x - 4, y + 5);
                    g.drawLine(x - 4, y + 5, x + 4, y + 5);
                    g.drawLine(x + 4, y - 5, x + 4, y + 5);
                    g.drawLine(x - 4, y - 5, x + 4, y + 5);
                    break;
                case "1":
                    g.drawLine(x + 0, y - 5, x + 0, y + 5);
                    g.drawLine(x + 0, y - 5, x - 4, y + 0);
                    g.drawLine(x - 4, y + 5, x + 4, y + 5);
                    break;
                case "2":
                    g.drawLine(x + 4, y - 5, x - 4, y - 5);
                    g.drawLine(x + 4, y - 5, x + 4, y - 0);
                    g.drawLine(x + 4, y + 0, x - 4, y - 0);
                    g.drawLine(x - 4, y + 0, x - 4, y + 5);
                    g.drawLine(x - 4, y + 5, x + 4, y + 5);
                    break;
                case "3":
                    g.drawLine(x + 4, y - 5, x + 4, y + 5);
                    g.drawLine(x - 4, y - 5, x + 4, y - 5);
                    g.drawLine(x - 4, y + 0, x + 4, y + 0);
                    g.drawLine(x - 4, y + 5, x + 4, y + 5);
                    break;
                case "4":
                    g.drawLine(x + 4, y - 5, x + 4, y + 5);
                    g.drawLine(x - 4, y + 0, x + 4, y + 0);
                    g.drawLine(x - 4, y - 5, x - 4, y + 0);
                    break;
                case "5":
                    g.drawLine(x + 4, y - 5, x - 4, y - 5);
                    g.drawLine(x - 4, y - 5, x - 4, y + 0);
                    g.drawLine(x - 4, y + 0, x + 4, y + 0);
                    g.drawLine(x + 4, y + 0, x + 4, y + 5);
                    g.drawLine(x - 4, y + 5, x + 4, y + 5);
                    break;
                case "6":
                    g.drawLine(x - 4, y - 5, x + 4, y - 5);
                    g.drawLine(x - 4, y - 5, x - 4, y + 5);
                    g.drawLine(x - 4, y + 5, x + 4, y + 5);
                    g.drawLine(x + 4, y - 0, x + 4, y + 5);
                    g.drawLine(x - 4, y - 0, x + 4, y + 0);
                    break;
                case "7":
                    g.drawLine(x - 4, y - 5, x + 4, y - 5);
                    g.drawLine(x + 4, y - 5, x + 4, (int) Math.round(y - 2.333333));
                    g.drawLine(x + 4, (int) Math.round(y - 2.333333), x + 0, y + 0);
                    g.drawLine(x + 0, y + 0, x + 0, y + 5);
                    break;
                case "8":
                    g.drawLine(x - 4, y - 5, x + 4, y - 5);
                    g.drawLine(x - 4, y - 5, x - 4, y + 5);
                    g.drawLine(x - 4, y + 5, x + 4, y + 5);
                    g.drawLine(x + 4, y - 5, x + 4, y + 5);
                    g.drawLine(x - 4, y - 0, x + 4, y + 0);
                    break;
                case "9":
                    g.drawLine(x - 4, y - 5, x + 4, y - 5);
                    g.drawLine(x - 4, y - 5, x - 4, y + 0);
                    g.drawLine(x + 4, y - 5, x + 4, y + 5);
                    g.drawLine(x - 4, y - 0, x + 4, y + 0);
                    break;
                case ":":
                    g.drawRect(x - 1, y - 3, 2, 2);
                    g.drawRect(x - 1, y + 1, 2, 2);
                    break;
                case "L":
                    g.drawLine(x - 4, y - 5, x - 4, y + 5);
                    g.drawLine(x - 4, y + 5, x + 4, y + 5);
                    break;
                case "O":
                    g.drawRect(x - 4, y - 5, 8, 10);
                    break;
                case "I":
                    g.drawLine(x - 4, y - 5, x + 4, y - 5);
                    g.drawLine(x - 0, y - 5, x + 0, y + 5);
                    g.drawLine(x - 4, y + 5, x + 4, y + 5);
                    break;
                case "V":
                    g.drawLine(x - 4, y - 5, x + 0, y + 5);
                    g.drawLine(x - 0, y + 5, x + 4, y - 5);
                    break;
                case "E":
                    g.drawLine(x - 4, y + 5, x - 4, y - 5);
                    g.drawLine(x - 4, y - 5, x + 4, y - 5);
                    g.drawLine(x - 4, y + 0, x + 2, y + 0);
                    g.drawLine(x - 4, y + 5, x + 4, y + 5);
                    break;
                case "S":
                    g.drawLine(x + 4, y - 5, x - 4, y - 5);
                    g.drawLine(x - 4, y - 5, x - 4, y + 0);
                    g.drawLine(x - 4, y + 0, x + 4, y + 0);
                    g.drawLine(x + 4, y + 0, x + 4, y + 5);
                    g.drawLine(x + 4, y + 5, x - 4, y + 5);
                    break;
                case "P":
                    g.drawLine(x - 4, y - 5, x - 4, y + 5);
                    g.drawRect(x - 4, y - 5, 4, 5);
                    break;
                case "N":
                    g.drawLine(x - 4, y + 5, x - 4, y - 5);
                    g.drawLine(x - 4, y - 5, x + 4, y + 5);
                    g.drawLine(x + 4, y + 5, x + 4, y - 5);
                    break;
                case "T":
                    g.drawLine(x - 4, y - 5, x + 4, y - 5);
                    g.drawLine(x + 0, y - 5, x + 0, y + 5);
                    break;
                case "M":
                    g.drawLine(x - 4, y - 5, x - 4, y + 5);
                    g.drawLine(x - 4, y - 5, x + 0, y + 0);
                    g.drawLine(x + 0, y + 0, x + 4, y - 5);
                    g.drawLine(x + 4, y + 5, x + 4, y - 5);
                    break;
                case "U":
                    g.drawLine(x - 4, y + 5, x - 4, y - 5);
                    g.drawLine(x - 4, y + 5, x + 4, y + 5);
                    g.drawLine(x + 4, y - 5, x + 4, y + 5);
                    break;
                case "R":
                    g.drawRect(x - 4, y - 5, 8, 5);
                    g.drawLine(x - 4, y - 5, x - 4, y + 5);
                    g.drawLine(x - 1, y + 0, x + 4, y + 5);
                    break;
            }
            x += 12;
            g.setColor(lineColor);
        }
        return x;
    }

    public void drawString(Graphics g, String s, int x, int y) {
        int color = -1;
        s = s.toUpperCase();
        s = s.trim();
        String[] ss = s.split("");
        for (String i : ss) {
            color++;
            if (" ".equals(i)) {
                color--;
            }
            if (!i.equals(" ")) {
                Color c = g.getColor();
                if (lineColor != Color.BLACK) {
                    g.setColor(Color.BLACK);
                } else {
                    g.setColor(Color.WHITE);
                }
                g.fillRect(x - 3, y - 3, 26, 36);
                g.setColor(c);
            }
            if (lineColor.getRGB() == Color.PINK.getRGB() && programStarted) {
                if (color % 6 == 0) {
                    g.setColor(Color.RED);
                } else if (color % 6 == 1) {
                    g.setColor(Color.ORANGE);
                } else if (color % 6 == 2) {
                    g.setColor(Color.YELLOW);
                } else if (color % 6 == 3) {
                    g.setColor(Color.GREEN);
                } else if (color % 6 == 4) {
                    g.setColor(Color.BLUE);
                } else {
                    g.setColor(Color.MAGENTA);
                }
            } else if (!programStarted) {
                g.setColor(Color.WHITE);
            } else {
                g.setColor(lineColor);
            }
            switch (i) {
                case "0":
                    g.drawRect(x, y, 20, 30);
                    g.drawLine(x, y + 30, x + 20, y);
                    break;
                case "1":
                    g.drawLine(x + 10, y, x + 10, y + 30);
                    g.drawLine(x + 10, y, x, y + 15);
                    g.drawLine(x, y + 30, x + 20, y + 30);
                    break;
                case "2":
                    g.drawLine(x + 20, y, x, y);
                    g.drawLine(x + 20, y, x + 20, y + 15);
                    g.drawLine(x + 20, y + 15, x, y + 15);
                    g.drawLine(x, y + 15, x, y + 30);
                    g.drawLine(x, y + 30, x + 20, y + 30);
                    break;
                case "3":
                    g.drawLine(x + 20, y, x + 20, y + 30);
                    g.drawLine(x, y, x + 20, y);
                    g.drawLine(x, y + 15, x + 20, y + 15);
                    g.drawLine(x, y + 30, x + 20, y + 30);
                    break;
                case "4":
                    g.drawLine(x + 20, y, x + 20, y + 30);
                    g.drawLine(x, y + 15, x + 20, y + 15);
                    g.drawLine(x, y, x, y + 15);
                    break;
                case "5":
                    g.drawLine(x + 20, y, x, y);
                    g.drawLine(x, y, x, y + 15);
                    g.drawLine(x, y + 15, x + 20, y + 15);
                    g.drawLine(x + 20, y + 15, x + 20, y + 30);
                    g.drawLine(x, y + 30, x + 20, y + 30);
                    break;
                case "6":
                    g.drawLine(x, y, x, y + 30);
                    g.drawRect(x, y + 15, 20, 15);
                    break;
                case "7":
                    g.drawLine(x, y, x + 20, y);
                    g.drawLine(x + 20, y, x + 20, y + 7);
                    g.drawLine(x + 20, y + 7, x + 10, y + 15);
                    g.drawLine(x + 10, y + 15, x + 10, y + 30);
                    break;
                case "8":
                    g.drawRect(x, y, 20, 30);
                    g.drawLine(x, y + 15, x + 20, y + 15);
                    break;
                case "9":
                    g.drawRect(x, y, 20, 15);
                    g.drawLine(x + 20, y, x + 20, y + 30);
                    break;
                case "A":
                    g.drawLine(x + 20, y + 10, x + 10, y);
                    g.drawLine(x, y + 10, x + 10, y);
                    g.drawLine(x, y + 10, x, y + 30);
                    g.drawLine(x, y + 15, x + 20, y + 15);
                    g.drawLine(x + 20, y + 10, x + 20, y + 30);
                    break;
                case "B":
                    g.drawLine(x, y, x, y + 30);
                    g.drawLine(x, y, x + 15, y);
                    g.drawLine(x, y + 30, x + 15, y + 30);
                    g.drawLine(x + 15, y, x + 20, y + 5);
                    g.drawLine(x + 15, y + 30, x + 20, y + 25);
                    g.drawLine(x + 20, y + 5, x + 20, y + 10);
                    g.drawLine(x + 20, y + 20, x + 20, y + 25);
                    g.drawLine(x, y + 15, x + 15, y + 15);
                    g.drawLine(x + 15, y + 15, x + 20, y + 10);
                    g.drawLine(x + 15, y + 15, x + 20, y + 20);
                    break;
                case "C":
                    g.drawLine(x, y, x + 20, y);
                    g.drawLine(x, y, x, y + 30);
                    g.drawLine(x, y + 30, x + 20, y + 30);
                    break;
                case "D":
                    g.drawLine(x, y, x, y + 30);
                    g.drawLine(x, y, x + 15, y);
                    g.drawLine(x, y + 30, x + 15, y + 30);
                    g.drawLine(x + 15, y, x + 20, y + 5);
                    g.drawLine(x + 15, y + 30, x + 20, y + 25);
                    g.drawLine(x + 20, y + 5, x + 20, y + 25);
                    break;
                case "E":
                    g.drawLine(x, y, x, y + 30);
                    g.drawLine(x, y, x + 20, y);
                    g.drawLine(x, y + 15, x + 15, y + 15);
                    g.drawLine(x, y + 30, x + 20, y + 30);
                    break;
                case "F":
                    g.drawLine(x, y, x, y + 30);
                    g.drawLine(x, y, x + 20, y);
                    g.drawLine(x, y + 15, x + 15, y + 15);
                    break;
                case "G":
                    g.drawLine(x + 20, y, x, y);
                    g.drawLine(x, y, x, y + 30);
                    g.drawLine(x, y + 30, x + 20, y + 30);
                    g.drawLine(x + 20, y + 30, x + 20, y + 15);
                    g.drawLine(x + 20, y + 15, x + 10, y + 15);
                    break;
                case "H":
                    g.drawLine(x, y, x, y + 30);
                    g.drawLine(x + 20, y, x + 20, y + 30);
                    g.drawLine(x, y + 15, x + 20, y + 15);
                    break;
                case "I":
                    g.drawLine(x, y, x + 9, y);
                    g.drawLine(x + 11, y, x + 20, y);
                    g.drawLine(x + 10, y + 1, x + 10, y + 29);
                    g.drawLine(x, y + 30, x + 9, y + 30);
                    g.drawLine(x + 11, y + 30, x + 20, y + 30);
                    break;
                case "J":
                    g.drawLine(x, y, x + 20, y);
                    g.drawLine(x + 10, y, x + 10, y + 25);
                    g.drawLine(x + 10, y + 25, x + 7, y + 30);
                    g.drawLine(x + 7, y + 30, x + 3, y + 30);
                    g.drawLine(x + 3, y + 30, x, y + 25);
                    break;
                case "K":
                    g.drawLine(x, y, x, y + 30);
                    g.drawLine(x, y + 15, x + 20, y);
                    g.drawLine(x, y + 15, x + 20, y + 30);
                    break;
                case "L":
                    g.drawLine(x, y, x, y + 30);
                    g.drawLine(x, y + 30, x + 20, y + 30);
                    break;
                case "M":
                    g.drawLine(x, y + 30, x, y);
                    g.drawLine(x, y, x + 10, y + 15);
                    g.drawLine(x + 10, y + 15, x + 20, y);
                    g.drawLine(x + 20, y, x + 20, y + 30);
                    break;
                case "N":
                    g.drawLine(x, y + 30, x, y);
                    g.drawLine(x, y, x + 20, y + 30);
                    g.drawLine(x + 20, y + 30, x + 20, y);
                    break;
                case "O":
                    g.drawRect(x, y, 20, 30);
                    break;
                case "P":
                    g.drawRect(x, y, 20, 15);
                    g.drawLine(x, y, x, y + 30);
                    break;
                case "Q":
                    g.drawRect(x, y, 15, 30);
                    g.drawLine(x + 10, y + 15, x + 20, y + 30);
                    break;
                case "R":
                    g.drawRect(x, y, 20, 15);
                    g.drawLine(x, y, x, y + 30);
                    g.drawLine(x + 5, y + 15, x + 20, y + 30);
                    break;
                case "S":
                    g.drawLine(x + 20, y, x, y);
                    g.drawLine(x, y, x, y + 15);
                    g.drawLine(x, y + 15, x + 20, y + 15);
                    g.drawLine(x + 20, y + 15, x + 20, y + 30);
                    g.drawLine(x + 20, y + 30, x, y + 30);
                    break;
                case "$":
                    g.drawLine(x + 18, y + 2, x + 2, y + 2);
                    g.drawLine(x + 2, y + 2, x + 2, y + 15);
                    g.drawLine(x + 2, y + 15, x + 18, y + 15);
                    g.drawLine(x + 18, y + 15, x + 18, y + 28);
                    g.drawLine(x + 18, y + 28, x + 2, y + 28);
                    g.drawLine(x + 10, y - 2, x + 10, y + 32);
                    break;
                case "T":
                    g.drawLine(x, y, x + 20, y);
                    g.drawLine(x + 10, y, x + 10, y + 30);
                    break;
                case "U":
                    g.drawLine(x, y, x, y + 30);
                    g.drawLine(x, y + 30, x + 20, y + 30);
                    g.drawLine(x + 20, y, x + 20, y + 30);
                    break;
                case "V":
                    g.drawLine(x, y, x + 10, y + 30);
                    g.drawLine(x + 10, y + 30, x + 20, y);
                    break;
                case "W":
                    g.drawLine(x, y, x + 5, y + 30);
                    g.drawLine(x + 5, y + 30, x + 10, y + 15);
                    g.drawLine(x + 10, y + 15, x + 15, y + 30);
                    g.drawLine(x + 15, y + 30, x + 20, y);
                    break;
                case "X":
                    g.drawLine(x, y, x + 20, y + 30);
                    g.drawLine(x, y + 30, x + 20, y);
                    break;
                case "Y":
                    g.drawLine(x, y, x + 10, y + 15);
                    g.drawLine(x + 10, y + 15, x + 20, y);
                    g.drawLine(x + 10, y + 15, x + 10, y + 30);
                    break;
                case "Z":
                    g.drawLine(x, y, x + 20, y);
                    g.drawLine(x + 20, y, x, y + 30);
                    g.drawLine(x, y + 30, x + 20, y + 30);
                    break;
                case ":":
                    g.drawRect(x + 7, y + 10, 5, 5);
                    g.drawRect(x + 7, y + 20, 5, 5);
                    break;
                case ";":
                    g.drawRect(x + 7, y + 10, 5, 5);
                    g.drawRect(x + 7, y + 20, 5, 5);
                    g.drawLine(x + 7 + 5, y + 25, x + 7, y + 30);
                    break;
                case "-":
                    g.drawLine(x, y + 15, x + 20, y + 15);
                    break;
                case "+":
                    g.drawLine(x, y + 15, x + 20, y + 15);
                    g.drawLine(x + 10, y, x + 10, y + 30);
                    break;
                case "!":
                    g.drawRect(x + 12, y, 5, 20);
                    g.drawRect(x + 12, y + 25, 5, 5);
                    break;
                case ".":
                    g.drawRect(x + 7, y + 30, 5, 5);
                    break;
                case "'":
                    g.drawRect(x + 10, y, 5, 5);
                    g.drawLine(x + 15, y + 5, x + 10, y + 10);
                    break;
                case "/":
                    g.drawLine(x + 5, y + 30, x + 15, y);
                    break;
                case "_":
                    g.drawLine(x, y + 30, x + 20, y + 30);
                    break;
                case "`":
                    g.fillRect(x, y, 20, 30);
                    break;
                case ">":
                    g.drawLine(x, y, x + 20, y + 15);
                    g.drawLine(x + 20, y + 15, x, y + 30);
                    break;
                case "<":
                    g.drawLine(x + 20, y, x, y + 15);
                    g.drawLine(x, y + 15, x + 20, y + 30);
                    break;
                case ",":
                    g.drawRect(x + 7, y + 15, 5, 5);
                    g.drawLine(x + 12, y + 20, x + 7, y + 25);
                    break;
                case "?":
                    g.drawRect(x + 7, y + 25, 5, 5);
                    g.drawLine(x + 10, y + 20, x + 10, y + 15);
                    g.drawLine(x + 10, y + 15, x + 20, y + 15);
                    g.drawLine(x + 20, y + 15, x + 20, y + 0);
                    g.drawLine(x + 20, y + 0, x + 0, y + 0);
                    g.drawLine(x + 0, y + 0, x + 0, y + 10);
                    break;
                case "(":
                    g.drawLine(x + 10, y, x + 5, y + 10);
                    g.drawLine(x + 5, y + 10, x + 5, y + 20);
                    g.drawLine(x + 5, y + 20, x + 10, y + 30);
                    break;
                case ")":
                    g.drawLine(x + 10, y, x + 15, y + 10);
                    g.drawLine(x + 15, y + 10, x + 15, y + 20);
                    g.drawLine(x + 15, y + 20, x + 10, y + 30);
                    break;
                default:
                    break;
            }
            x += 25;
            g.setColor(lineColor);
        }
    }

    public void createParticles(SpaceShip a) {
        for (int i = 0; i < 40; i++) {
            vacuum.add(new HyperJumpParticle((float) a.getX(), (float) a.getY(), 3));
        }
    }

    public void createParticles(GameObject a) {
        int j;
        if (a instanceof Meteor && !(a instanceof Meteorite)) {
            j = 4;
        } else if (a instanceof Asteroid && !(a instanceof Meteorite)) {
            j = 6;
        } else if (a instanceof Meteorite) {
            j = 2;
        } else if (a instanceof Alien) {
            j = 12;
        } else if (a instanceof Laser) {
            j = 40;
        } else {
            j = 20;
        }
        for (int i = 0; i < j; i++) {
            if (a instanceof Laser) {
                vacuum.add(new Particle((float) a.getX(), (float) a.getY(), 3));
            } else {
                vacuum.add(new Particle((float) a.getX(), (float) a.getY()));
            }
        }
    }

    public void createFireWorks(GameObject a) {
        int j;
        if (a instanceof Meteor && !(a instanceof Meteorite)) {
            j = 4;
        } else if (a instanceof Asteroid && !(a instanceof Meteorite)) {
            j = 6;
        } else if (a instanceof Meteorite) {
            j = 2;
        } else if (a instanceof Alien) {
            j = 12;
        } else {
            j = 20;
        }
        for (int i = 0; i < j; i++) {
            vacuum.add(new Firework((float) a.getX(), (float) a.getY()));
        }
    }

    public void select() {
        if (clearHighScores) {
            if (settingSelect == 10) {
                if (menuSelect % menuOptions == 1) {
                    settings = new Settings();
                }
                menuSelect = 0;
                save();
                lineColor = settings.getLineColor();
                showSettings();
                clearHighScores = false;
                return;
            } else {
                if (menuSelect % menuOptions == 1) {
                    highScores.clearAll();
                    showSettings();
                } else {
                    showSettings();
                }
                clearHighScores = false;
                return;
            }
        }
        if (deleteHighScore) {
            if (menuSelect % menuOptions < 5) {
                highScores.remove((int) (((highScorePage - 1) * 5)
                        + (menuSelect % menuOptions)));
                deleteHighScore = false;
                showHighScore();
            } else {
                if (menuSelect % menuOptions == 5) {
                    highScorePage--;
                    if (highScorePage <= 0) {
                        highScorePage = 1;
                    }
                }
                if (menuSelect % menuOptions == 6) {
                    highScorePage++;
                    if (highScorePage >= 7) {
                        highScorePage = 6;
                    }
                }
                if (menuSelect % menuOptions == 7) {
                    deleteHighScore = false;
                    showSettings();
                }
            }
            return;
        }
        if (gameOver) {
            if (menuSelect % menuOptions == 0) {
                highScoreInput = true;
                inputValue();
            } else if (menuSelect % menuOptions == 1) {
                restart();
            } else if (menuSelect % menuOptions == 2) {
                initBoard();
                preview();
                showMenu();
            } else {
                showMenu();
            }
            gauntletOn = false;
            return;
        }
        if (!gameOver && addNewHighScoreScoreInput) {
            addNewHighScoreScoreInput = false;
            highScoreInput = true;
            inputValue();
        }
        if (!highScoreDisplay && !numberInput
                && !showControls && !showSettings) {
            if (menuSelect % menuOptions == 0) {
                initBoard();
                start();
            } else if (menuSelect % menuOptions == 1) {
                showControls();
            } else if (menuSelect % menuOptions == 2) {
                showHighScore();
            } else if (menuSelect % menuOptions == 3) {
                showSettings();
            } else if (menuSelect % menuOptions == 4) {
                quit();
            } else {
                showMenu();
            }
        } else if (!highScoreDisplay && !numberInput
                && showControls && !showSettings) {
            if (menuSelect % menuOptions == 0) {
                controlsPage--;
                if (controlsPage <= 0) {
                    controlsPage = 1;
                }
            } else if (menuSelect % menuOptions == 1) {
                controlsPage++;
                if (controlsPage >= 9) {
                    controlsPage = 8;
                }
            } else if (menuSelect % menuOptions == 2) {
                showMenu();
            } else {
                showControls();
            }
        } else if (highScoreDisplay && !showControls && !showSettings) {
            if (menuSelect % menuOptions == 0) {
                highScorePage--;
                if (highScorePage <= 0) {
                    highScorePage = 1;
                }
            } else if (menuSelect % menuOptions == 1) {
                highScorePage++;
                if (highScorePage >= 7) {
                    highScorePage = 6;
                }
            } else if (menuSelect % menuOptions == 2) {
                showMenu();
            } else {
                showHighScore();
            }
        } else if (!highScoreDisplay && !numberInput
                && !showControls && showSettings) {
            if (settingSelect == -1) {
                if (menuSelect % menuOptions > 10) {
                    if ((menuSelect % menuOptions) == 11) {
                        save();
                        showMenu();
                    }
                } else {
                    settingSelect = (menuSelect % menuOptions) + 1;
                    resetMenu(10);
                }
                if (settingSelect == 1) {
                    resetMenu(4);
                }
                if (settingSelect == 2) {
                    resetMenu(4);
                }
                if (settingSelect == 3) {
                    resetMenu(8);
                    numberInput = true;
                    inputValue();
                }
                if (settingSelect == 4) {
                    resetMenu(8);
                    numberInput = true;
                    inputValue();
                }
                if (settingSelect == 5) {
                    resetMenu(8);
                    numberInput = true;
                    inputValue();
                }
                if (settingSelect == 6) {
                    addNewHighScoreScoreInput = true;
                    numberInput = true;
                    highScoreInput = false;
                    inputValue();
                }
                if (settingSelect == 7) {
                    deleteHighScore = true;
                    showHighScore();
                    resetMenu(8);
                }
                if (settingSelect == 8) {
                    clearHighScores = true;
                    resetMenu(2);
                    return;
                }
                if (settingSelect == 9) {
                    resetMenu(10);
                    return;
                }
                if (settingSelect == 10) {
                    clearHighScores = true;
                    resetMenu(2);
                }
                if (settingSelect == 11) {
                    save();
                    showMenu();
                }
                if (settingSelect > 11 || settingSelect < -1) {
                    settingSelect = -1;
                    resetMenu(10);
                }
            } else if (settingSelect == 1) {
                settings.setMusicVolume((int) (menuSelect % menuOptions));
                save();
                showSettings();
            } else if (settingSelect == 2) {
                settings.setSFXVolume((int) (menuSelect % menuOptions));
                save();
                showSettings();
            } else if (settingSelect == 3) {
                resetMenu(10);
                numberInput = true;
                inputValue();
            } else if (settingSelect == 4) {
                resetMenu(10);
                numberInput = true;
                inputValue();
            } else if (settingSelect == 5) {
                resetMenu(10);
                numberInput = true;
                inputValue();
            } else if (settingSelect == 6) {
                showMenu();
            } else if (settingSelect == 9) {
                if (menuSelect % menuOptions == 0) {
                    settings.setLineColor(Color.WHITE);
                } else if (menuSelect % menuOptions == 1) {
                    settings.setLineColor(Color.RED);
                } else if (menuSelect % menuOptions == 2) {
                    settings.setLineColor(Color.ORANGE);
                } else if (menuSelect % menuOptions == 3) {
                    settings.setLineColor(Color.YELLOW);
                } else if (menuSelect % menuOptions == 4) {
                    settings.setLineColor(Color.GREEN);
                } else if (menuSelect % menuOptions == 5) {
                    settings.setLineColor(Color.BLUE);
                } else if (menuSelect % menuOptions == 6) {
                    settings.setLineColor(Color.MAGENTA);
                } else if (menuSelect % menuOptions == 7) {
                    settings.setLineColor(Color.PINK);
                } else if (menuSelect % menuOptions == 8) {
                    settings.setLineColor(Color.BLACK);
                } else if (menuSelect % menuOptions == 9) {
                    settings.setLineColor(controlColor);
                } else {
                    settings.setLineColor(Color.WHITE);
                }
                controlColor = null;
                lineColor = settings.getLineColor();
                save();
                showSettings();
            } else {
                showSettings();
            }
        } else {
            showMenu();
        }
    }

    @Override
    public void paintComponent(Graphics g
    ) {
        super.paintComponent(g);
        doDrawing(g);
    }

    private void doGameCycle() {
        doingCycle = true;
        if (isStarted && lostLife != null) {
            if (lostLife.getState() == 2) {
                lostLife.updateDeath();
            } else {
                lostLife = null;
            }
        }
        if (isStarted) {
            int i = 0;
            while (score - i > displayScore && i < 500) {
                displayScore++;
                i += 100;
            }
            if (score >= 99990 && displayScore < 99000) {
                while (score - i > displayScore) {
                    displayScore++;
                    i += 100;
                }
            }
            if (addLife && (displayScore % 10000 < 100
                    || (player.getState() > 1 && lives <= 0))) {
                lives++;
                SoundEffect.NEW_LIFE.play();
                addLife = false;
                justAddedLife = true;
            }
            if (displayScore >= score) {
                displayScore = score;
            }
        }
        Color base = null;
        if (gauntletOn && levelDelay < cycleCount - (9 * 60)) {
            base = lineColor;
            lineColor = Color.RED;
        }
        repaint();
        if (isStarted) {
            if (!hyperSpaceOn && player.getState() == 0) {
                hyperSpaceOn = true;
            }
        }
        if ((aliens.isEmpty() || gameOver) && timerChange) {
            timer.cancel();
            timer = new Timer();
            timer.scheduleAtFixedRate(new ScheduleTask(),
                    0, PERIOD_INTERVAL);
            timerChange = false;
            
            
            
            
        }
        if (isStarted && !isPaused && !gameOver) {
            bckgrnd.timeStep();
        } 
        if (isStarted && !isPaused && !gameOver && !highScoreInput
                && !highScoreDisplay && !numberInput && !displayNextLevel) {
            if (settings.getMusicVolume() == 0) {
                SoundEffect.volume = SoundEffect.Volume.MUTE;
            } else if (settings.getMusicVolume() == 1) {
                SoundEffect.volume = SoundEffect.Volume.LOW;
            } else if (settings.getMusicVolume() == 2) {
                SoundEffect.volume = SoundEffect.Volume.MEDIUM;
            } else if (settings.getMusicVolume() == 3) {
                SoundEffect.volume = SoundEffect.Volume.HIGH;
            } else {
                SoundEffect.volume = SoundEffect.Volume.HIGH;
            }
            bgTimer += 0.01666666666;
            if (currentDelay > maxDelay) {
                currentDelay = maxDelay;
            }
            if (currentDelay < minDelay) {
                currentDelay = minDelay;
            }
            if (player.getState() == 1 && bgTimer >= currentDelay) {
                if (playLowPulse) {
                    SoundEffect.BEAT1.play();
                } else {
                    SoundEffect.BEAT2.play();
                }
                playLowPulse = !playLowPulse;
                bgTimer = 0;
            }
        }
        if (settings == null) {
            resetSettings();
        }
        if (settings.getSFXVolume() == 0) {
            SoundEffect.volume = SoundEffect.Volume.MUTE;
        } else if (settings.getSFXVolume() == 1) {
            SoundEffect.volume = SoundEffect.Volume.LOW;
        } else if (settings.getSFXVolume() == 2) {
            SoundEffect.volume = SoundEffect.Volume.MEDIUM;
        } else if (settings.getSFXVolume() == 3) {
            SoundEffect.volume = SoundEffect.Volume.HIGH;
        } else {
            SoundEffect.volume = SoundEffect.Volume.HIGH;
        }
        if (isStarted && !isPaused && !gameOver
                && !highScoreInput && !highScoreDisplay) {
            print("Cycle " + cycleCount + " Begin :: ", true, false);
            moveShip();
            if (!displayNextLevel && aliens.isEmpty()) {
                alienTimer += 0.0166;
            }
            if (alienTimer > alienTime) {
                alienTimer = 0;
                alienTime = (float) (10 + (Math.random() * 20));
                int t = (int) Math.round(Math.random() * 2);
                if ((t == 0 || t == 1) && score < 40000) {
                    aliens.add(new Alien());
                } else {
                    aliens.add(new Alien_Small());
                }
                alienSoundTime = 0;
            }
            if (player != null) {
                print(player.toString(), false, false);
            }
            for (int i = 0; i < lasers.size(); i++) {
                Laser l = lasers.get(i);
                if (l != null) {
                    print(l.toString(), false, false);
                }
                if (l == null) {
                    lasers.remove(i);
                    i--;
                }
            }
            for (int i = 0; i < rubble.size(); i++) {
                Asteroid a = rubble.get(i);
                if (a != null) {
                    print(a.toString(), false, false);
                }
                if (a == null) {
                    rubble.remove(i);
                    i--;
                }
            }
            for (int i = 0; i < aliens.size(); i++) {
                Alien a = aliens.get(i);
                if (a != null) {
                    print(a.toString(), false, false);
                }
                if (a == null) {
                    rubble.remove(i);
                    i--;
                }
            }
            testCollisions();
            print("Cycle " + cycleCount + " End ", false, true);
            cycleCount++;
        }
        if (isStarted && !aliens.isEmpty() && !gameOver) {
            double[] a;
            if (aliens.get(0) instanceof Alien_Small) {
                a = tone(900 + (100 * Math.cos((alienSoundTime
                        - Math.PI) / 2)), 0.01666666666);
            } else {
                a = tone(700 + (100 * Math.cos((alienSoundTime
                        - Math.PI) / 2)), 0.01666666666);
            }
            repaint();
            if (SoundEffect.volume == SoundEffect.Volume.MUTE) {
                for (int i = 0; i < a.length; i++) {
                    a[i] = 0;
                }
            }
            StdAudio.play(a);
            timer.cancel();
            timer = new Timer();
            timer.scheduleAtFixedRate(new ScheduleTask(), 0, 1);
            timerChange = true;
            repaint();
            alienSoundTime++;
        }
        if (!isPaused && isStarted) {
            if (hyperSpaceTimer < hyperSpaceCoolDown
                    && player.getState() == 1) {
                hyperSpaceTimer++;
            }
            if (hyperSpaceTimer >= hyperSpaceCoolDown) {
                hyperSpaceTimer = hyperSpaceCoolDown;
            }
            if (hyperSpaceTimer < hyperSpaceCoolDown && hyperSpaceOn) {
                hyperSpaceOn = false;
            }
            if (hyperSpaceOn && player.getState() != 0) {
                if (player.getState() > 2) {
                    hyperSpaceOn = false;
                }
            }
            if (hyperSpaceOn && player.getState() == 0
                    && player.getTimeOfDeath() < cycleCount - (10 * 60)) {
                hyperSpaceOn = false;
                player.setState(1);
                player.setTimeOfDeath(0);
            }
            if (!hyperSpaceOn && player.getState() == 0) {
                hyperSpaceOn = true;
            }
            if (hyperSpaceOn) {
                hyperJump();
            }
        }

        if (!isPaused) {
            moveAsteroids();
            moveParticles();
            moveAliens();
            moveLasers();
        }
        if ((highScoreInput && !highScoreDisplay) || numberInput) {
            name[initialInput] = initialLetter;
            cycleCount++;
        }

        repaint();
        if (lives
                < 0 && player.getState()
                >= 3 && !highScoreInput) {
            gameOver();
            displayScore = score;
            cycleCount++;
            repaint();
            long d = cycleCount - levelDelay;
            d = d - (d % 60);
            d /= 60;
            gameOverCountdown = (int) (30 - d);
            if (gameOverCountdown <= 0) {
                restartMenu();
            }
        }
        if (displayScore >= 99990 && !hasWon) {
            hasWon = true;
            beginProgram = -1;
        }
        if (hasWon && beginProgram < (15 * 60)) {
            if (!isPaused && isStarted) {
                beginProgram++;
                if (beginProgram % 5 == 0) {
                    createFireWorks(new GameObject(Math.random()
                            * BOARD_WIDTH, Math.random() * BOARD_HEIGHT));
                    SoundEffect.METEORITE_DIE.play();
                }
            }
        }
        if (base != null) {
            lineColor = base;
        }
        if (!gauntletOn && settingSelect != 9
                && lineColor.getRGB() != settings.getLineColor().getRGB()) {
            lineColor = settings.getLineColor();
        }
        doingCycle = false;
    }

    public void print(String i, boolean topLineBreak,
            boolean bottomLineBreak) {
        String form = "0.00";
        DecimalFormat format = new DecimalFormat(form);
        long endTime = System.currentTimeMillis();
        long timeElapsed = endTime - startTime;
        Double te = (double) timeElapsed / 1000;
        String second = format.format(te % 60);
        timeElapsed /= 1000;
        long minute = (timeElapsed - (timeElapsed % 60)) / 60;
        i = minute + ":" + second + " :: " + i;

        if (printIt) {
            if (topLineBreak) {
                for (int j = 0; j < i.length() + (i.length() % 5); j++) {
                    System.out.print("*");
                }
                System.out.println("");
            }
            System.out.println(i);
            if (bottomLineBreak) {
                for (int j = 0; j < i.length() + (i.length() % 5); j++) {
                    System.out.print("*");
                }
                System.out.println("");
            }
        }
    }

    public void gameOver() {
        if (!gameOver) {
            print("Final Score: " + score, true, true);
            levelDelay = cycleCount;
            resetMenu(3);
        }
        gameOver = true;
    }

    public void quit() {
        save();
        System.exit(0);
    }

    public void fire() {
        int lCount = 0;
        for (Laser l : lasers) {
            if (l != null && !(l instanceof Alien_Laser)) {
                lCount++;
            }
        }

        if (player.getState() == 1 && lCount < settings.getMaxLasers()
                && !displayNextLevel) {
            SoundEffect.SHOOT.play();
            lasers.add(new Laser(player, lasers.size()));
        }
    }

    public void hyperJump() {
        boolean quit = false;
        if (vacuum.size() > 0 && !vacuum.isEmpty()) {
            for (Particle p : vacuum) {
                if (p instanceof HyperJumpParticle && player.getState() != 0) {
                    hyperSpaceOn = false;
                    player.setState(1);
                    quit = true;
                    return;
                }
            }
        }
        if (quit) {
            return;
        }
        if (hyperSpaceTimer >= hyperSpaceCoolDown
                && hyperSpaceOn && player.getState() == 0) {
            boolean tOrf = true;
            double x;
            double y;
            player.setState(0);
            x = Math.random() * BOARD_WIDTH;
            y = Math.random() * BOARD_HEIGHT;
            for (int ii = 0; ii < rubble.size(); ii++) {
                Asteroid a = rubble.get(ii);
                for (double i = x - 25; i < x + 25; i++) {
                    for (double j = y - 25; j < y + 25; j++) {
                        if (a.isTouching(new SpaceShip(i, j)) || new SpaceShip(i, j).isTouching(a)
                                || !tOrf) {
                            tOrf = false;
                            break;
                        }
                    }
                }
            }
            for (int ii = 0; ii < aliens.size(); ii++) {
                Alien a = aliens.get(ii);
                for (double i = x - 25; i < x + 25; i++) {
                    for (double j = y - 25; j < y + 25; j++) {
                        if (a.isTouching(new SpaceShip(i, j))
                                || new SpaceShip(i, j).isTouching(a)
                                || !tOrf) {
                            tOrf = false;
                            break;
                        }
                    }
                }
            }
            if (tOrf) {
                SoundEffect.HYPER_JUMP.play();
                player.setX(x);
                player.setXSpeed(0);
                player.setY(y);
                player.setYSpeed(0);
                player.setState(1);
                player.setTimeOfDeath(0);
                if (!aliens.isEmpty()) {
                    aliens.get(0).setFire(0);
                }
                createParticles(player);
                hyperSpaceOn = false;
                hyperSpaceTimer = 0;
                player.setState(1);
            } else {
                hyperSpaceOn = true;
                player.setState(0);
            }
        }
    }

    public void moveShip() {
        if (player.getState() == 1) {
            if (playerRotate > 0) {
                player.rotateRight();
            } else if (playerRotate < 0) {
                player.rotateLeft();
            } else {
                // player.standStill();
            }

            if (thrust) {
                if (toThrustOrNotToThrust) {
                    player.thrust();
                    if (!SoundEffect.THRUST.isRunning()) {
                        SoundEffect.THRUST.play();
                    }
                }
                toThrustOrNotToThrust = !toThrustOrNotToThrust;
            } else {
                player.notThrust();
                SoundEffect.THRUST.stop();
            }
            player.move();
        } else if (player.getState() == 2) {
            SoundEffect.THRUST.stop();
            player.updateDeath();
        } else if (player.getState() == 3) {
            player.setState(4);
            if (lives > 0) {
                justLostLife = true;
            } else {
                lives--;
                livesSpent++;
            }
            if (gauntletOn) {
                gauntletLivesLost++;
            }
            for (int[] ii : chunkLoad) {
                for (int i = 0; i < ii.length; i++) {
                    ii[i] = 2;
                }
            }
        } else if (player.getState() == 4) {
            boolean allClear = true;
            if (!aliens.isEmpty()) {
                aliens.get(0).setFire(0);
            }
            if (player.getTimeOfDeath() > cycleCount - (15 * 60)) {
                for (Asteroid a : rubble) {
                    if (a instanceof Meteorite) {
                        Meteorite m = (Meteorite) a;
                        if (m.getX() > (BOARD_WIDTH / 2) - 57
                                && m.getX() < (BOARD_WIDTH / 2) + 57
                                && m.getY() > (BOARD_HEIGHT / 2) - 57
                                && m.getY() < (BOARD_HEIGHT / 2) + 57) {
                            allClear = false;
                            break;
                        }
                    } else if (a instanceof Meteor) {
                        Meteor m = (Meteor) a;
                        if (m.getX() > (BOARD_WIDTH / 2) - 63
                                && m.getX() < (BOARD_WIDTH / 2) + 63
                                && m.getY() > (BOARD_HEIGHT / 2) - 63
                                && m.getY() < (BOARD_HEIGHT / 2) + 63) {
                            allClear = false;
                            break;
                        }
                    } else {
                        if (a.getX() > (BOARD_WIDTH / 2) - 75
                                && a.getX() < (BOARD_WIDTH / 2) + 75
                                && a.getY() > (BOARD_HEIGHT / 2) - 75
                                && a.getY() < (BOARD_HEIGHT / 2) + 75) {
                            allClear = false;
                            break;
                        }
                    }
                }
                if (allClear) {
                    for (Alien a : aliens) {
                        if (a.getX() > (BOARD_WIDTH / 2) - 50
                                && a.getX() < (BOARD_WIDTH / 2) + 50
                                && a.getY() > (BOARD_HEIGHT / 2) - 50
                                && a.getY() < (BOARD_HEIGHT / 2) + 50) {
                            allClear = false;
                            break;
                        }
                    }
                }
            } else {
                for (Asteroid a : rubble) {
                    if (a.getX() > (BOARD_WIDTH / 2) - 25
                            && a.getX() < (BOARD_WIDTH / 2) + 25
                            && a.getY() > (BOARD_HEIGHT / 2) - 25
                            && a.getY() < (BOARD_HEIGHT / 2) + 25) {
                        allClear = false;
                        break;
                    }
                }
                if (allClear) {
                    for (Alien a : aliens) {
                        if (a.getX() > (BOARD_WIDTH / 2) - 25
                                && a.getX() < (BOARD_WIDTH / 2) + 25
                                && a.getY() > (BOARD_HEIGHT / 2) - 25
                                && a.getY() < (BOARD_HEIGHT / 2) + 25) {
                            allClear = false;
                            break;
                        }
                    }
                }
            }
            if (allClear) {
                player = new SpaceShip();
                createParticles(new Laser(player.getX(), player.getY()));
            }
        } else if (player.getState() == 0) {
        } else {
            player.setState(1);
        }
    }

    public void moveLasers() {
        for (int i = 0; i < lasers.size(); i++) {
            Laser l = lasers.get(i);
            boolean laserExpired = l.move();
            if (laserExpired) {
                lasers.remove(i);
                i--;
            }
        }
    }

    public void moveParticles() {
        for (int i = 0; i < vacuum.size(); i++) {
            Particle p = vacuum.get(i);
            if (p != null) {
                p.move();
                if (p.getRemove() == true) {
                    vacuum.remove(i);
                    i--;
                }
            }
        }
    }

    public void moveAsteroids() {
        if (rubble.size() > 0 || aliens.size() > 0) {
            if (!rubble.isEmpty() && rubble.size() > 0) {
                for (Asteroid a : rubble) {
                    if (a != null) {
                        a.move();
                    }
                }
            }
        } else {
            if (!levelDelayOn) {
                levelDelay = cycleCount;
                levelDelayOn = true;
            } else {
                if ((youStillThereQuestioning
                        || giveUpWaiting
                        || wentThereButLeft) && isStarted
                        && ((player.getX() < 370 && player.getX() > 270)
                        && (player.getY() < 370 && player.getY() > 270))
                        && levelDelay < cycleCount - (60 * 9)
                        && level != 1) {
                    cycleCount = levelDelay;
                    if (wentThereButLeft) {
                        giveUpWaiting = true;
                    }
                    if (!wentThereButLeft) {
                        wentThereButLeft = true;
                    }
                }
                if ((levelDelay < cycleCount - (60 * 3) && isStarted
                        && levelDelay > cycleCount - (60 * 9)
                        && ((player.getX() < 370 && player.getX() > 270)
                        && (player.getY() < 370 && player.getY() > 270))
                        && level != 1)
                        || (levelDelay < cycleCount - (60 * 3) && isStarted
                        && ((player.getX() < 370 && player.getX() > 270)
                        && (player.getY() < 370 && player.getY() > 270))
                        && level == 1)) {
                    youStillThereQuestioning = false;
                    wentThereButLeft = false;
                    giveUpWaiting = false;
                    displayNextLevel = false;
                    levelDelayOn = false;
                    gauntletLivesLost = 0;
                    gauntletOn = false;
                    level++;
                    if (firstGame) {
                        firstGame = false;
                    }
                    createAsteroids();
                } else {
                    if (isStarted) {
                        displayNextLevel = true;
                    }
                }
            }
        }
    }

    public void moveAliens() {
        for (int i = 0; i < aliens.size(); i++) {
            Alien a = aliens.get(i);
            if (a instanceof Alien_Small) {
                Alien_Small aa = (Alien_Small) a;
                double decide = aa.shoot(player);
                if (decide != -1) {
                    if (!SoundEffect.ALIEN_SHOOT.isRunning()) {
                        SoundEffect.ALIEN_SHOOT.play();
                    }
                    lasers.add(new Alien_Laser(aa));
                }
                aa.decideDirection();
                boolean die = aa.move();
                if (die) {
                    aliens.remove(aa);
                }
            } else {
                double decide = a.shoot(player);
                if (decide != -1) {
                    if (!SoundEffect.ALIEN_SHOOT.isRunning()) {
                        SoundEffect.ALIEN_SHOOT.play();
                    }
                    lasers.add(new Alien_Laser(a));
                }
                a.decideDirection();
                boolean die = a.move();
                if (die) {
                    aliens.remove(a);
                }
            }
        }
    }

    public static double[] tone(double hz, double duration) {
        int n = (int) (StdAudio.SAMPLE_RATE * duration);
        double[] a = new double[n + 1];
        for (int i = 0; i <= n; i++) {
            a[i] = Math.sin(2 * Math.PI * i * hz / StdAudio.SAMPLE_RATE);
        }
        return a;
    }

    private void testCollisions() {
        // Test Laser Collisions
        // Use Asteroids as Basis
        for (int i = 0; i < rubble.size(); i++) {
            if (i < 0) {
                i = 0;
            }
            if (rubble.size() <= 0) {
                break;
            }
            Asteroid a = rubble.get(i);
            for (int j = 0; j < lasers.size(); j++) {
                Laser l = lasers.get(j);
                if (a != null && l != null) {
                    if (a.isTouching(l) || l.isTouching(a)) {
                        createParticles(a);
                        if (!(l instanceof Alien_Laser)) {
                            addScore(a.getScore());
                        }
                        rubble.remove(a);
                        double numerator = ((maxDelay - minDelay)
                                * rubble.size());
                        if (!aliens.isEmpty()) {
                            for (int iii = 0; i < 3; i++) {
                                if (numerator <= 0) {
                                    while (numerator <= 0) {
                                        numerator++;
                                    }
                                    break;
                                } else {
                                    numerator--;
                                }
                            }
                        }
                        currentDelay = (float) ((numerator / totalAsteroids) + minDelay);
                        if (currentDelay <= minDelay) {
                            currentDelay = minDelay;
                        }
                        if (!(l instanceof Alien_Laser)) {
                            addScore(a.getScore());
                        }
                        lasers.remove(l);
                        if (a instanceof Meteor && !(a instanceof Meteorite)) {
                            SoundEffect.METEOR_DIE.play();
                            rubble.add(new Meteorite(Math.random()
                                    * 360, a.getX(), a.getY()));
                            rubble.add(new Meteorite(Math.random()
                                    * 360, a.getX(), a.getY()));
                        } else if (a instanceof Asteroid
                                && !(a instanceof Meteorite)) {
                            SoundEffect.ASTEROID_DIE.play();
                            rubble.add(new Meteor(Math.random()
                                    * 360, a.getX(), a.getY()));
                            rubble.add(new Meteor(Math.random()
                                    * 360, a.getX(), a.getY()));
                        } else {
                            SoundEffect.METEORITE_DIE.play();
                        }
                        j--;
                        i--;
                    }
                }
            }
            if (rubble.contains(a)) {
                for (int j = 0; j < aliens.size(); j++) {
                    Alien al = aliens.get(j);
                    if (a != null && al != null) {
                        if (a.isTouching(al) || al.isTouching(a)) {
                            rubble.remove(a);
                            createParticles(a);
                            currentDelay = ((((maxDelay - minDelay)
                                    * rubble.size()) / totalAsteroids) + minDelay);
                            if (currentDelay <= minDelay) {
                                currentDelay = minDelay;
                            }
                            SoundEffect.ALIEN_DIE.play();
                            aliens.remove(al);
                            createParticles(al);
                            if (a instanceof Meteor && !(a instanceof Meteorite)) {
                                SoundEffect.METEOR_DIE.play();
                                rubble.add(new Meteorite(Math.random()
                                        * 360, a.getX(), a.getY()));
                                rubble.add(new Meteorite(Math.random()
                                        * 360, a.getX(), a.getY()));
                            } else if (a instanceof Asteroid
                                    && !(a instanceof Meteorite)) {
                                SoundEffect.ASTEROID_DIE.play();
                                rubble.add(new Meteor(Math.random()
                                        * 360, a.getX(), a.getY()));
                                rubble.add(new Meteor(Math.random()
                                        * 360, a.getX(), a.getY()));
                            } else {
                                SoundEffect.METEORITE_DIE.play();
                            }
                            j--;
                            i--;
                        }
                    }
                }
            }
        }

        // Test Alien Laser Collisions
        for (int i = 0; i < aliens.size(); i++) {
            Alien a = aliens.get(i);
            for (int j = 0; j < lasers.size(); j++) {
                Laser l = lasers.get(j);
                if (l instanceof Alien_Laser) {

                } else {
                    if ((a.isTouching(l) || l.isTouching(a))
                            && !(l instanceof Alien_Laser)) {
                        addScore(a.getScore());
                        lasers.remove(l);
                        aliens.remove(a);
                        createParticles(a);
                        if (i > 0) {
                            i--;
                        }
                        if (j > 0) {
                            j--;
                        }
                    }
                }
            }
        }

        // Test Asteroid Collisions  
        if (player.getState() == 1) {
            for (int i = 0; i < rubble.size(); i++) {
                Asteroid a = rubble.get(i);
                if (player.isTouching(a) || a.isTouching(player)) {
                    die();
                    break;
                }
            }
        }
        if (player.getState() == 1) {
            for (int i = 0; i < lasers.size(); i++) {
                Laser l = lasers.get(i);
                if (l instanceof Alien_Laser) {
                    if (player.isTouching(l) || l.isTouching(player)) {
                        die();
                        break;
                    }
                }
            }
        }
        if (player.getState() == 1) {
            for (int i = 0; i < aliens.size(); i++) {
                Alien a = aliens.get(i);
                if (player.isTouching(a) || a.isTouching(player)) {
                    aliens.remove(a);
                    createParticles(a);
                    die();
                    break;
                }
            }
        }
    }

    private void die() {
        SoundEffect.YOU_DIE.play();
        player.setState(2);
        player.setTimeOfDeath(cycleCount);
        player.setXSpeed(0);
        player.setYSpeed(0);
        player.hit();
    }

    private void createAsteroids() {
        currentDelay = maxDelay;
        int ii = level;
        if (ii == 0) {
            ii = 1;
        }
        if (rageIncreaseLevelDifficulty) {
            ii += 6;
            rageIncreaseLevelDifficulty = false;
            gauntletOn = true;
            levelDelay = cycleCount;
            onceRaged = true;
            doneWithRageText = false;
        }
        Date date = new Date();
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        if (calendar.get(Calendar.MONTH) == 3 && calendar.get(Calendar.DAY_OF_MONTH) == 1) {
            if ((0 == (int) (Math.random() * 10)) && level != 1) {
                ii += 10;
                aprilFools = true;
            } else {
                aprilFools = false;
            }
        } else {
            aprilFools = false;
        }
        for (int i = 0; i < (ii + 1) * 2; i++) {
            rubble.add(new Asteroid((Math.random() * 400) - 200,
                    (Math.random() * 400) - 200,
                    Math.random() * 360));
        }
        totalAsteroids = rubble.size() * 4;
    }

    private void restartMenu() {
        if (!previewOn) {
            initBoard();
            preview();
        }
    }

    private void restartMenuWHighSCore() {
        initBoard();
        preview();
        showHighScore();
    }

    private void restart() {
        initBoard();
        start();
    }

    private int center(String i) {
        return ((BOARD_WIDTH - (i.length() * 25)) / 2);
    }

    public void showHighScore() {
        highScoreDisplay = true;
        highScoreInput = false;
        showControls = false;
        showSettings = false;
        resetHighScores();
        resetMenu(3);
    }

    public void showControls() {
        highScoreDisplay = false;
        showControls = true;
        showSettings = false;
        resetMenu(3);
    }

    public void showMenu() {
        highScoreDisplay = false;
        showControls = false;
        showSettings = false;
        resetMenu(5);
    }

    public void showSettings() {
        highScoreDisplay = false;
        showControls = false;
        showSettings = true;
        settingSelect = -1;
        resetSettings();
        resetMenu(11);
    }

    private void saveScore() {
        HighScore hs = new HighScore(
                alphabet[name[0] % 26],
                alphabet[name[1] % 26],
                alphabet[name[2] % 26],
                score);
        highScores.add(hs);
        save();
        restartMenuWHighSCore();
    }

    private void saveSettings() {
        numberInput = false;
        String n = "";
        for (int i = 0; i < 3; i++) {
            n += numberLine[name[i] % numberLine.length];
        }
        int value = Integer.parseInt(n);
        if (settingSelect == 3) {
            highScores.switchLives(settings.getStartingLives(), value);
            settings.setStartingLives(value);
        }
        if (settingSelect == 4) {
            settings.setMaxLasers(value);
        }
        if (settingSelect == 5) {
            settings.setHyperSpaceChargeTime(value);
            hyperSpaceCoolDown = (settings.getHyperSpaceChargeTime() * 60);
        }
        settingSelect = -1;
        save();
        showSettings();
    }

    private void save() {
        highScores.save(settings.getStartingLives());
        try {
            FileOutputStream fos = new FileOutputStream(
                    new File("resources/scores.ser"));
            try (ObjectOutputStream out = new ObjectOutputStream(fos)) {
                out.writeObject(highScores);
            }
            print("Serialized data "
                    + "is saved in resources/scores.ser",
                    false, false);
        } catch (IOException i) {
            print("Data Not Saved in resources/scores.ser", true, true);
        }

        try {
            try (FileOutputStream fileOut = new FileOutputStream(
                    "resources/settings.ser");
                    ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
                out.writeObject(settings);
            }
            print("Serialized data is saved in "
                    + "resources/settings.ser",
                    false, false);
        } catch (IOException i) {
            print("Data Not Saved in "
                    + "resources/settings.ser", true, true);
        }
    }

    public void inputValue() {
        if (addNewHighScoreScoreInput) {
            name = new int[9];
        } else {
            name = new int[3];
        }
        Arrays.fill(name, 0);
        if (highScoreInput) {
            numberInput = false;
            highScoreInput = true;
        } else {
            numberInput = true;
            highScoreInput = false;
        }
        initialInput = 0;
        initialLetter = 0;
        isStarted = false;
        restartMenu();
    }

    private void drawHighScore(Graphics g) {
        if (highScoreDisplay && !showSettings && !showControls) {
            String input = highScores.toString();
            String[] output = new String[31];
            String[] newOutput = input.split("\n");
            int i;
            if (newOutput.length < output.length) {
                for (i = 0; i < newOutput.length; i++) {
                    output[i] = i + ". " + newOutput[i];
                }
                for (int j = i; j < 31; j++) {
                    output[j] = j + ". ";
                }
            } else {
                for (i = 0; i < output.length; i++) {
                    output[i] = i + ". " + newOutput[i];
                }
                for (int j = i; j < 31; j++) {
                    output[j] = j + ". ";
                }
            }
            String title = "HighScores: ";
            if (highScorePage == 1) {
                title += "1-5";
            } else if (highScorePage == 2) {
                title += "6-10";
            } else if (highScorePage == 3) {
                title += "11-15";
            } else if (highScorePage == 4) {
                title += "16-20";
            } else if (highScorePage == 5) {
                title += "21-25";
            } else if (highScorePage == 6) {
                title += "25-30";
            } else {
                title = "HighScores:";
            }
            drawString(g, title, center(title), 90);
            for (i = 0; i < output.length; i++) {
                output[i] = output[i].trim();
            }

            if (highScorePage == 1) {
                for (i = 1; i < output.length; i++) {
                    int x = 145;
                    if (i > 9) {
                        x -= 25;
                    }
                    if (i > 5) {
                        break;
                    }
                    drawString(g, output[i], x, 90 + (i * 50));
                }
            } else if (highScorePage == 2) {
                for (i = 6; i < output.length; i++) {
                    int x = 145;
                    if (i > 9) {
                        x -= 25;
                    }
                    if (i > 10) {
                        break;
                    }
                    drawString(g, output[i], x, 90 + ((i - 5) * 50));
                }
            } else if (highScorePage == 3) {
                for (i = 11; i < output.length; i++) {
                    int x = 145;
                    if (i > 9) {
                        x -= 25;
                    }
                    if (i > 15) {
                        break;
                    }
                    drawString(g, output[i], x, 90 + ((i - 10) * 50));
                }
            } else if (highScorePage == 4) {
                for (i = 16; i < output.length; i++) {
                    int x = 145;
                    if (i > 9) {
                        x -= 25;
                    }
                    if (i > 20) {
                        break;
                    }
                    drawString(g, output[i], x, 90 + ((i - 15) * 50));
                }
            } else if (highScorePage == 5) {
                for (i = 21; i < output.length; i++) {
                    int x = 145;
                    if (i > 9) {
                        x -= 25;
                    }
                    if (i > 25) {
                        break;
                    }
                    drawString(g, output[i], x, 90 + ((i - 20) * 50));
                }
            } else if (highScorePage == 6) {
                for (i = 26; i < output.length; i++) {
                    int x = 145;
                    if (i > 9) {
                        x -= 25;
                    }
                    if (i > 30) {
                        break;
                    }
                    drawString(g, output[i], x, 90 + ((i - 25) * 50));
                }
            } else {

            }
            if (deleteHighScore) {
                if (menuSelect % menuOptions <= 4) {
                    drawString(g, "->", 145 - 75, (int) (140
                            + ((menuSelect % menuOptions) * 50)));
                } else {
                    if (menuSelect % menuOptions == 5) {
                        drawString(g, "->", center("->   " + "Previous Page"),
                                BOARD_HEIGHT - 180);
                    }
                    if (menuSelect % menuOptions == 6) {
                        drawString(g, "->", center("->   " + "Next Page"),
                                BOARD_HEIGHT - 140);
                    }
                }
            }

            if (menuSelect % menuOptions == 0 && !deleteHighScore) {
                drawString(g, "->", center("->   Previous  Page"),
                        BOARD_HEIGHT - 180);
            }
            drawString(g, "Previous  Page", center("Previous  Page"),
                    BOARD_HEIGHT - 180);

            if (menuSelect % menuOptions == 1 && !deleteHighScore) {
                drawString(g, "->", center("->   Next Page"),
                        BOARD_HEIGHT - 140);
            }
            drawString(g, "Next Page", center("Next Page"),
                    BOARD_HEIGHT - 140);

            if ((menuSelect % menuOptions == 2 && !deleteHighScore)
                    || (deleteHighScore && menuSelect % menuOptions == 7)) {
                drawString(g, "->", center("->   Return"), BOARD_HEIGHT - 100);
            }
            if (!deleteHighScore) {
                drawString(g, "Return", center("Return"), BOARD_HEIGHT - 100);
            } else {
                drawString(g, "Cancel", center("Cancel"), BOARD_HEIGHT - 100);
            }
        }
    }

    private void drawInput(Graphics g) {
        if (highScoreInput || numberInput) {
            drawString(g, "Input Here:", center("Input Here:"), 280 - 60);
            String i = "";
            for (int j = 0; j < name.length; j++) {
                if (highScoreInput && !numberInput) {
                    i += "" + alphabet[name[j] % 26];
                } else {
                    i += "" + numberLine[name[j] % 10];
                }
            }
            drawString(g, i, center(i), 280 + 20);
            g.drawLine(center(i) + (initialInput * 25), 340,
                    center(i) + (initialInput * 25) + 25, 340);
            drawString(g, "Use Rotate Buttons",
                    center("Use Rotate Buttons"), 360);
            drawString(g, "to Move Btwn initials",
                    center("to Move Btwn initials"), 400);
            drawString(g, "Use Up/Down Buttons",
                    center("Use Up/Down Buttons"), 440);
            drawString(g, "to Change Letters",
                    center("to Change Letters"), 480);
            drawString(g, "Press Space When Done",
                    center("Press Space When Done"), 520);
            drawString(g, "Press Escape to Cancel",
                    center("Press Escape to Cancel"), 560);
        }
    }

    private void drawGameOver(Graphics g) {
        if (gameOver && !highScoreInput) {
            if (gauntletOn) {
                drawString(g, "That's what you get",
                        center("That's what you get"), 280 - 150);
                if (gauntletLivesLost == 1) {
                    drawString(g, "You lost your life",
                            center("You lost your life"),
                            280 - 110);
                } else {
                    drawString(g, "You lost all " + gauntletLivesLost + " live(s)",
                            center("You lost all " + gauntletLivesLost + " live(s)"),
                            280 - 110);
                }
                drawString(g, "Don't test me again",
                        center("Don't test me again"), 280 - 70);
            }
            drawString(g, "Game Over!", center("Game Over!"), 280 - 20);
            if (menuSelect % menuOptions == 0) {
                drawString(g, "->", center("->   Record Score"), 280 + 20);
            }
            drawString(g, "Record Score", center("Record Score"), 280 + 20);
            if (menuSelect % menuOptions == 1) {
                drawString(g, "->", center("->   Restart"), 280 + 60);
            }
            drawString(g, "Restart", center("Restart"), 280 + 60);
            if (menuSelect % menuOptions == 2) {
                drawString(g, "->", center("->   Return to Menu"), 280 + 100);
            }
            drawString(g, "Return to Menu",
                    center("Return to Menu"), 280 + 100);
            drawString(g, "Final Score:" + score, 10, 10);
            if (lineColor.getRGB() == Color.PINK.getRGB()) {
                Color c = lineColor;
                if (gameOverCountdown > 20) {
                    lineColor = Color.GREEN;
                } else if (gameOverCountdown > 10) {
                    lineColor = Color.YELLOW;
                } else {
                    lineColor = Color.RED;
                }
                drawString(g, "" + gameOverCountdown,
                        center("" + gameOverCountdown), BOARD_HEIGHT - 60);
                lineColor = c;
            } else {
                drawString(g, "" + gameOverCountdown,
                        center("" + gameOverCountdown), BOARD_HEIGHT - 60);
            }
        }
    }

    private void drawMenu(Graphics g) {
        if (!isStarted && !showControls && !gameOver
                && !highScoreInput && !highScoreDisplay
                && !numberInput && !showSettings) {
            drawString(g, "Asteroids", center("Asteroids"), 100);
            drawString(g, "By Mason Borenstein",
                    center("By Mason Borenstein"), 100 + 40);
            if (menuSelect % menuOptions == 0) {
                drawString(g, "->", center("->   Start"), 280);
            }
            drawString(g, "Start", center("Start"), 280);
            if (menuSelect % menuOptions == 1) {
                drawString(g, "->", center("->   Controls"), 280 + 40);
            }
            drawString(g, "Controls", center("Controls"), 280 + 40);
            if (menuSelect % menuOptions == 2) {
                drawString(g, "->", center("->   High Scores"), 280 + 80);
            }
            drawString(g, "High Scores", center("High Scores"), 280 + 80);
            if (menuSelect % menuOptions == 3) {
                drawString(g, "->", center("->   Settings"), 280 + 120);
            }
            drawString(g, "Settings", center("Settings"), 280 + 120);
            if (menuSelect % menuOptions == 4) {
                drawString(g, "->", center("->   Quit"), 280 + 160);
            }
            drawString(g, "Quit", center("Quit"), 280 + 160);
            g.setColor(lineColor);

        }
    }

    private void drawInitial(Graphics g) {
        g.setColor(Color.WHITE);
        if (beginProgram < (2 * 60)) {
            alpha += (1f) / 60;
            if (alpha >= 1f) {
                alpha = 1f;
            }
        }
        if (beginProgram > (4 * 60)) {
            alpha += (-1f) / 60;
            if (alpha <= 0) {
                alpha = 0;
            }
        }
        Graphics2D g2 = (Graphics2D) g;
        g2.setComposite(AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, alpha));
        Date date = new Date();
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        if (calendar.get(Calendar.MONTH) == 3 && calendar.get(Calendar.DAY_OF_MONTH) == 1) {
            g2.drawImage(logo, 80, 50, null);
        } else {
            g2.drawImage(logo, 118, 50, null);
        }
        String i = "Good ";
        if (calendar.get(Calendar.HOUR_OF_DAY) > 0
                && calendar.get(Calendar.HOUR_OF_DAY) < 5) {
            i += "Evening. You should be Asleep.";
        } else if (calendar.get(Calendar.HOUR_OF_DAY) < 12) {
            i += "Morning";
        } else if (calendar.get(Calendar.HOUR_OF_DAY) < 18) {
            i += "Afternoon";
        } else if (calendar.get(Calendar.HOUR_OF_DAY) < 21) {
            i += "Evening";
        } else {
            i += "Evening";
        }
        drawString(g2, i, center(i), 460);
        i = "";
        if (calendar.get(Calendar.HOUR_OF_DAY) < 4
                || calendar.get(Calendar.HOUR_OF_DAY) >= 23) {
            i += "You Should be in Bed Lol";
        }
        i += getHolidays();

        String[] output = i.split("@New_Line");
        int y = 500;
        for (String ii : output) {
            drawString(g, ii, center(ii), y);
            y += 40;
        }
        g.setColor(lineColor);
    }

    private void drawNormal(Graphics g) {
        if (gauntletOn && (g.getColor().getRGB() != Color.RED.getRGB()
                || lineColor.getRGB() != Color.RED.getRGB())
                && ((levelDelay < cycleCount - (9 * 60)
                && (int) (Math.random() * 2) == 0))) {
            if (levelDelay < cycleCount - (24 * 60)
                    && (int) (Math.random() * 2) == 0) {
                lineColor = Color.RED;
            }
            g.setColor(Color.RED);
        } else {
            g.setColor(lineColor);
        }
        // Draw Ship
        if (isStarted && !showControls
                && !highScoreInput && !highScoreDisplay) {
            if (lineColor.getRGB() == Color.PINK.getRGB()) {
                g.setColor(Color.PINK);
            }
            Color c = null;
            if (gauntletOn) {
                if (levelDelay > cycleCount - (9 * 60)) {
                    double i = ((double) ((9 * 60) - (cycleCount - levelDelay)) / (9 * 60)) * 10;
                    if ((int) (Math.random() * i) == 0) {
                        c = g.getColor();
                        g.setColor(Color.RED);
                    }
                } else {
                    c = g.getColor();
                    g.setColor(Color.RED);
                }
            }
            player.draw(g);
            if (c != null) {
                g.setColor(c);
            }
        }
        // Display Level
        bckgrnd.draw(g, this);
        drawLevel(g);
        // Draw Laser(s)
        if (isStarted && !showControls
                && !highScoreInput && !highScoreDisplay) {
            for (int i = 0; i < lasers.size(); i++) {
                Laser l = lasers.get(i);
                if (lineColor.getRGB() == Color.PINK.getRGB()
                        && !(gauntletOn
                        && g.getColor().getRGB() == Color.RED.getRGB())) {
                    if (l instanceof Alien_Laser) {
                        g.setColor(Color.MAGENTA);
                    } else {
                        g.setColor(Color.GREEN);
                    }
                }
                if (l != null) {
                    l.draw(g);
                }
                if (gauntletOn && levelDelay < cycleCount - (9 * 60)
                        && (int) (Math.random() * 3) == 0) {
                    g.setColor(Color.RED);
                } else {
                    g.setColor(lineColor);
                }
            }
        }
        // Draw Asteroid(s) (and Particles)
        if (!isStarted || (isStarted && !isPaused)
                || (isPaused && !showControls)
                || highScoreInput || highScoreDisplay) {
            for (int i = 0; i < rubble.size(); i++) {
                Asteroid a = rubble.get(i);
                if (a != null) {
                    if (lineColor.getRGB() == Color.PINK.getRGB()
                            && !(gauntletOn
                            && g.getColor().getRGB() == Color.RED.getRGB())) {
                        g.setColor(a.getColor());
                    }
                    a.draw(g);
                }
            }
            for (int i = 0; i < vacuum.size(); i++) {
                Particle p = vacuum.get(i);
                if (p != null) {
                    Color c = g.getColor();
                    if (lineColor.getRGB() == Color.PINK.getRGB()
                            && !(gauntletOn
                            && g.getColor().getRGB() == Color.RED.getRGB())) {
                        if (p instanceof HyperJumpParticle) {
                            g.setColor(Color.CYAN);
                        } else if (p instanceof Firework) {
                            Firework f = (Firework) p;
                            g.setColor(f.getColor());
                        } else {
                            g.setColor(Color.LIGHT_GRAY);
                        }
                    }
                    p.draw(g);
                    g.setColor(c);
                }
            }
        }
        // Draw Alien(s)
        if (!isStarted || (isStarted && !isPaused)
                || (isPaused && !showControls)) {
            for (int i = 0; i < aliens.size(); i++) {
                Alien a = aliens.get(i);
                if (lineColor.getRGB() == Color.PINK.getRGB()) {
                    g.setColor(Color.PINK);
                }
                a.draw(g);
            }
        }
        if (lineColor.getRGB() == Color.PINK.getRGB()
                && g.getColor().getRGB() != Color.RED.getRGB()) {
            g.setColor(lineColor);
        }
        if (gauntletOn && g.getColor().getRGB() != lastColor.getRGB()) {
            SoundEffect.STATIC.play();
        }
        lastColor = g.getColor();
        // Draw Points
        drawScore(g);
        // Controls Explanation
        drawControls(g);
        // Show HighScore 
        drawHighScore(g);
        // Draw Game Over
        drawGameOver(g);
        // Show HighScore Input
        drawInput(g);
        // Waiting Startup Screen
        drawMenu(g);
        // Draw Settings
        drawSettings(g);

        // Draw Death Ship
        if (isStarted && !showControls && lostLife != null
                && !highScoreInput && !highScoreDisplay) {
            if (lineColor.getRGB() == Color.PINK.getRGB()) {
                g.setColor(Color.RED);
            }
            lostLife.draw(g);
            g.setColor(lineColor);
        }
        // Draw is Paused if Paused
        if (isPaused) {
            drawString(g, "<Paused>", center("<Paused>"), 305);
        }
        if (hasWon && beginProgram < (15 * 60) && !gameOver
                && isStarted && !showControls
                && !highScoreInput && !highScoreDisplay) {
            drawString(g, "You won! Keep Going!",
                    center("You won! Keep Going!"), 10);
            if (gauntletOn && SoundEffect.STATIC.isRunning()) {
                SoundEffect.STATIC.stop();
            }
        }
        if (player != null) {
            if (player.getTimeOfDeath() < cycleCount - (5 * 60)
                    && player.getState() != 1
                    && !gameOver
                    && isStarted) {
                drawString(g, "Please Hold,",
                        center("Please Hold,"), BOARD_HEIGHT - 90);
                if (hyperSpaceOn) {
                    drawString(g, "Returning from HyperSpace",
                            center("Returning from HyperSpace"),
                            BOARD_HEIGHT - 50);
                } else {
                    drawString(g, "Awaiting Respawn...",
                            center("Awaiting Respawn..."), BOARD_HEIGHT - 50);
                }
            }
        }
        if (player != null) {
            /**
            *g.drawString("Ship X: " + player.getX(), 10, 100);
            *g.drawString("Ship Y: " + player.getY(), 10, 110);
            *g.drawString("Ship XS: " + player.getXSpeed(), 10, 120);
            *g.drawString("Ship YS: " + player.getYSpeed(), 10, 130);
            * */
        }
    }

    private void drawControls(Graphics g) {
        if (showControls && !highScoreInput && !highScoreDisplay) {
            if (konami) {
                drawString(g, Math.round(controlsPage) + "/8",
                        center(controlsPage + "/8"), 60);
            } else {
                drawString(g, Math.round(controlsPage) + "/8",
                        center(controlsPage + "/8"), 10);
            }
            if (controlsPage == 1) {
                drawString(g, "You wake up one day",
                        center("You wake up one day"), 140);
                drawString(g, "And get out of bed",
                        center("And get out of bed"), 140 + 40);
                drawString(g, "And only one thought",
                        center("And only one thought"), 140 + 80);
                drawString(g, "crosses your mind:",
                        center("crosses your mind:"), 140 + 120);
                drawString(g, "“I wanna shoot rocks”",
                        center("“I wanna shoot rocks”"), 140 + 160);
                drawString(g, "So board your Rocket",
                        center("So board your Rocket"), 140 + 200);
                drawString(g, "Fly to the Kuiper Belt",
                        center("Fly to the Kuiper Belt"), 140 + 240);
                drawString(g, "For some Far out Fun!!!",
                        center("For some Far out Fun!!!"), 140 + 280);
            } else if (controlsPage == 2) {
                drawString(g, "Fly around the screen",
                        center("Fly around the screen"), 140);
                drawString(g, "Shoot all the rocks",
                        center("Shoot all the rocks"), 140 + 40);
                drawString(g, "Shoot all the aliens",
                        center("Shoot all the aliens"), 140 + 80);
                drawString(g, "Don’t get shot by Aliens",
                        center("Don’t get shot by Aliens"), 140 + 120);
                drawString(g, "Don’t run into the Rocks",
                        center("Don’t run into the Rocks"), 140 + 160);
                drawString(g, "Don't die in general",
                        center("Don't die in general"), 140 + 200);
                drawString(g, "Collect lots of Points",
                        center("Collect lots of Points"), 140 + 240);
                drawString(g, "Have fun!",
                        center("Have fun!"), 140 + 280);
            } else if (controlsPage == 3) {
                drawString(g, "Press Up to Thrust",
                        center("Press Up to Thrust"), 140);
                drawString(g, "Press Left and Right",
                        center("Press Left and Right  "), 140 + 40);
                drawString(g, "to Rotate the ship",
                        center("to Rotate the ship"), 140 + 80);
                drawString(g, "Press Space to Fire",
                        center("Press Space to Fire"), 140 + 120);
                drawString(g, "Press P to pause",
                        center("Press P to pause"), 140 + 160);
                drawString(g, "Press Q if Paused to quit",
                        center("Press Q if Paused to quit"), 140 + 200);
                drawString(g, "Press H to HyperJump",
                        center("Press H to HyperJump"), 140 + 240);
                drawString(g, "when it is charged",
                        center("when it’s charged"), 140 + 280);
            } else if (controlsPage == 4) {
                drawString(g, "Here's some advice:",
                        center("Here's some advice:"), 140);
                g.drawLine(center("Here's some advice:") - 5, 180,
                        center("Here's some advice:") + (19 * 25), 180);
                drawString(g, "Killing aliens is not",
                        center("Killing aliens is not"), 140 + 50);
                drawString(g, "neccessary.But if you do",
                        center("neccessary.But if you do"), 140 + 90);
                drawString(g, "you'll get bonus points",
                        center("you'll get bonus points"), 140 + 130);
                drawString(g, "big ships aim randomly",
                        center("big ships aim randomly"), 140 + 170);
                drawString(g, "but small ships shoots",
                        center("but small ships shoots"), 140 + 210);
                drawString(g, "directly at you",
                        center("directly at you"), 140 + 250);
                drawString(g, "so keep moving",
                        center("so keep moving"), 140 + 290);
            } else if (controlsPage == 5) {
                drawString(g, "Here's some advice:",
                        center("Here's some advice:"), 140);
                g.drawLine(center("Here's some advice:") - 5, 180,
                        center("Here's some advice:") + (19 * 25), 180);
                drawString(g, "The HyperJump is special",
                        center("The HyperJump is special"), 140 + 50);
                drawString(g, "When the HyperJump meter",
                        center("When the HyperJump meter"), 140 + 90);
                drawString(g, "has been filled,",
                        center("has been filled,"), 140 + 130);
                drawString(g, "jump to a random place",
                        center("jump to a random place"), 140 + 170);
                drawString(g, "Use it when in a jam",
                        center("Use it when in a jam"), 140 + 210);
                drawString(g, "but you may end up",
                        center("but you may end up"), 140 + 250);
                drawString(g, "in front of a rock",
                        center("in front of a rock"), 140 + 290);
            } else if (controlsPage == 6) {
                drawString(g, "Here's some advice:",
                        center("Here's some advice:"), 140);
                g.drawLine(center("Here's some advice:") - 5, 180,
                        center("Here's some advice:") + (19 * 25), 180);
                drawString(g, "All objects on the screen",
                        center("All objects on the screen"), 140 + 50);
                drawString(g, "Fly off the right side",
                        center("Fly off the right side"), 140 + 90);
                drawString(g, "To reappear on the left",
                        center("To reappear on the left"), 140 + 130);
                drawString(g, "You don't decelerate",
                        center("You don't decelerate"), 140 + 170);
                drawString(g, "Because You're in space",
                        center("Because You're in space"), 140 + 210);
                drawString(g, "Thrust the other way",
                        center("Thrust the other way"), 140 + 250);
                drawString(g, "to not fly too fast",
                        center("to not fly too fast"), 140 + 290);
            } else if (controlsPage == 7) {
                drawString(g, "Here's some advice:",
                        center("Here's some advice:"), 140);
                g.drawLine(center("Here's some advice:") - 5, 180,
                        center("Here's some advice:") + (19 * 25), 180);
                drawString(g, "In order to win",
                        center("In order to win"), 140 + 50);
                drawString(g, "collect 99990 points",
                        center("collect 99990 points"), 140 + 90);
                drawString(g, "The rocks have square",
                        center("The rocks have square"), 140 + 130);
                drawString(g, "hitboxe$,don't get close",
                        center("hitboxe$,don't get close"), 140 + 170);
                drawString(g, "If " + getCurrentColor() + " is weird,",
                        center("If " + getCurrentColor() + " is weird,"), 140 + 210);
                drawString(g, "you can change the color",
                        center("you can change the color"), 140 + 250);
                drawString(g, "in the settings",
                        center("in the settings"), 140 + 290);
            } else if (controlsPage == 8) {
                drawString(g, "Here's some advice:",
                        center("Here's some advice:"), 140);
                g.drawLine(center("Here's some advice:") - 5, 180,
                        center("Here's some advice:") + (19 * 25), 180);
                drawString(g, "If the arrows",
                        center("If the arrows"), 140 + 50);
                drawString(g, "are awkward for you",
                        center("are awkward for you"), 140 + 90);
                drawString(g, "Use WASD instead",
                        center("Use WASD instead"), 140 + 130);
                drawString(g, "And/or Enter to fire",
                        center("And/or Enter to fire"), 140 + 170);
                drawString(g, "Enjoy the game, dear",
                        center("Enjoy the game, dear"), 140 + 210);
                drawString(g, "Space Cadet!!!",
                        center("Space Cadet!!!"), 140 + 250);
                drawString(g, "-Mason Borenstein",
                        center("-Mason Borenstein"), 140 + 290);
            } else {

            }
            if (menuSelect % menuOptions == 0) {
                drawString(g, "->",
                        center("->   Previous Page"), BOARD_HEIGHT - 150);
            }
            drawString(g, "Previous Page",
                    center("Previous Page"), BOARD_HEIGHT - 150);
            if (menuSelect % menuOptions == 1) {
                drawString(g, "->",
                        center("->   Next Page"), BOARD_HEIGHT - 110);
            }
            drawString(g, "Next Page",
                    center("Next Page"), BOARD_HEIGHT - 110);
            if (menuSelect % menuOptions == 2) {
                drawString(g, "->",
                        center("->   Return"), BOARD_HEIGHT - 70);
            }
            drawString(g, "Return",
                    center("Return"), BOARD_HEIGHT - 70);
            g.setColor(lineColor);
        }
    }

    private void drawScore(Graphics g) {
        if (showControls && !gameOver && !highScoreDisplay
                && !numberInput && !showSettings) {
            if (!konami) {
                drawString(g, "Score", 10, 10);
                drawString(g, "Lives", 10, 50);
                drawString(g, "HyperJump", 10, 90);
            }
        } else if (!isStarted) {

        } else if (highScoreInput) {
            drawString(g, "Final Score: " + Long.toString(score), 10, 10);
        } else if (highScoreDisplay) {

        } else {
            if (!gameOver) {
                g.setColor(lineColor);
                drawString(g, Long.toString(displayScore), 10, 10);
                if (lineColor.getRGB() == Color.PINK.getRGB()) {
                    g.setColor(Color.CYAN);
                }
                g.drawLine(50, 60, 50, 75);
                g.drawLine(0, 60, 50, 60);
                g.drawLine(0, 75, 50, 75);
                g.drawLine(0, 60, 0, 75);
                g.fillRect(0, 60, (int) Math.round(50
                        * ((double) hyperSpaceTimer / hyperSpaceCoolDown)), 15);
                if (hyperSpaceTimer == 0) {
                    g.fillRect(0, 60, (int) Math.round(50), 15);
                }
                if (aprilFools) {
                    Color c = lineColor;
                    lineColor = Color.PINK;
                    drawString(g, "April Fools!!!", center("April Fools!!!"), 50);
                    lineColor = c;
                }
            }

            double x = 10;
            double y = 50;
            if (!gameOver) {
                if (lives >= 0) {
                    x = drawSmallString(g, lives + ":",
                            (int) Math.round(x), (int) Math.round(y));
                } else {
                    x = drawSmallString(g, "0:",
                            (int) Math.round(x), (int) Math.round(y));
                }
            } else {
                drawSmallString(g, "Menu Timer:" + gameOverCountdown,
                        (int) Math.round(x), (int) Math.round(y + 21));
                x = drawSmallString(g, "Lives Lost:" + livesSpent,
                        (int) Math.round(x), (int) Math.round(y + 5));
            }
            if (lineColor.getRGB() == Color.PINK.getRGB()) {
                g.setColor(Color.GREEN);
            }
            for (int i = 1; i <= lives; i++) {
                double x1 = x;
                double y1 = y;

                double x2 = 6 * Math.cos(Math.toRadians(90 - 60)) + x;
                double y2 = 6 * Math.sin(Math.toRadians(90 - 60)) + y;

                double x3 = 6 * Math.cos(Math.toRadians(90 - 180)) + x;
                double y3 = 6 * Math.sin(Math.toRadians(90 - 180)) + y;

                double x4 = 6 * Math.cos(Math.toRadians(90 + 60)) + x;
                double y4 = 6 * Math.sin(Math.toRadians(90 + 60)) + y;

                g.drawLine((int) Math.round(x1), (int) Math.round(y1),
                        (int) Math.round(x2), (int) Math.round(y2));
                g.drawLine((int) Math.round(x2), (int) Math.round(y2),
                        (int) Math.round(x3), (int) Math.round(y3));
                g.drawLine((int) Math.round(x3), (int) Math.round(y3),
                        (int) Math.round(x4), (int) Math.round(y4));
                g.drawLine((int) Math.round(x4), (int) Math.round(y4),
                        (int) Math.round(x1), (int) Math.round(y1));
                x += 15;
            }
            if (justLostLife) {
                lives--;
                livesSpent++;
                lostLife = new SpaceShip(x - 15, y);
                lostLife.setShape();
                lostLife.setTimeOfDeath(cycleCount);
                lostLife.setXSpeed(0);
                lostLife.setYSpeed(0);
                lostLife.hit();
                justLostLife = false;
                if (SoundEffect.volume != SoundEffect.Volume.MUTE) {
                    SoundEffect.Volume v = SoundEffect.volume;
                    SoundEffect.volume = SoundEffect.Volume.LOW;
                    SoundEffect.METEORITE_DIE.play();
                    SoundEffect.volume = v;
                }
            }
            if (justAddedLife) {
                createParticles(new Laser(x - 15, y));
                justAddedLife = false;
            }
        }
        g.setColor(lineColor);
    }

    private void drawSettings(Graphics g) {
        if (clearHighScores) {
            drawString(g, "Are you sure?", center("Are you sure?"), 150);
            drawString(g, "No", center("No"), 190);
            if (menuSelect % menuOptions == 0) {
                drawString(g, "->", center("->   No"), 190);
            }
            drawString(g, "Yes", center("Yes"), 230);
            if (menuSelect % menuOptions == 1) {
                drawString(g, "->", center("->   Yes"), 230);
            }
        } else {
            if (!highScoreDisplay && !numberInput
                    && !showControls && showSettings) {
                if (settingSelect == -1) {
                    if (!konami) {
                        drawString(g, "Settings: ", 10, 10);
                    }
                }
                if (settingSelect == -1) {
                    String i = "Music Volume: ";
                    if (settings.getMusicVolume() == 0) {
                        i += "OFF";
                    } else if (settings.getMusicVolume() == 1) {
                        i += "LOW";
                    } else if (settings.getMusicVolume() == 2) {
                        i += "MED";
                    } else if (settings.getMusicVolume() == 3) {
                        i += "HIGH";
                    } else {
                        settings.setMusicVolume(3);
                        i += "HIGH";
                    }
                    drawString(g, i, center(i), 80);
                    if (menuSelect % menuOptions == 0 && settingSelect == -1) {
                        drawString(g, "->", center("->   " + i), 80);
                    }

                    i = "SFX Volume: ";
                    if (settings.getSFXVolume() == 0) {
                        i += "OFF";
                    } else if (settings.getSFXVolume() == 1) {
                        i += "LOW";
                    } else if (settings.getSFXVolume() == 2) {
                        i += "MED";
                    } else if (settings.getSFXVolume() == 3) {
                        i += "HIGH";
                    } else {
                        settings.setMusicVolume(3);
                        i += "HIGH";
                    }
                    drawString(g, i, center(i), 120);
                    if (menuSelect % menuOptions == 1 && settingSelect == -1) {
                        drawString(g, "->", center("->   " + i), 120);
                    }
                    i = "Starting Lives: " + settings.getStartingLives();
                    drawString(g, i, center(i), 160);
                    if (menuSelect % menuOptions == 2 && settingSelect == -1) {
                        drawString(g, "->", center("->   " + i), 160);
                    }

                    i = "Max Lasers: " + Math.round(settings.getMaxLasers());
                    drawString(g, i, center(i), 200);
                    if (menuSelect % menuOptions == 3 && settingSelect == -1) {
                        drawString(g, "->", center("->   " + i), 200);
                    }

                    i = "HyperSpace: " + Math.round(
                            settings.getHyperSpaceChargeTime()) + " seconds";
                    drawString(g, i, center(i), 240);
                    if (menuSelect % menuOptions == 4 && settingSelect == -1) {
                        drawString(g, "->", center("->   " + i), 240);
                    }
                    i = "Add HighScore";
                    drawString(g, i, center(i), 280);
                    if (menuSelect % menuOptions == 5 && settingSelect == -1) {
                        drawString(g, "->", center("->   " + i), 280);
                    }
                    i = "Remove HighScore";
                    drawString(g, i, center(i), 320);
                    if (menuSelect % menuOptions == 6 && settingSelect == -1) {
                        drawString(g, "->", center("->   " + i), 320);
                    }
                    i = "Clear All HighScores";
                    drawString(g, i, center(i), 360);
                    if (menuSelect % menuOptions == 7 && settingSelect == -1) {
                        drawString(g, "->", center("->   " + i), 360);
                    }
                    i = "Color: " + getCurrentColor();
                    drawString(g, i, center(i), 400);
                    if (menuSelect % menuOptions == 8 && settingSelect == -1) {
                        drawString(g, "->", center("->   " + i), 400);
                    }
                    i = "Reset Settings";
                    drawString(g, i, center(i), 440);
                    if (menuSelect % menuOptions == 9 && settingSelect == -1) {
                        drawString(g, "->", center("->   " + i), 440);
                    }

                    drawString(g, "Return",
                            center("Return"), BOARD_HEIGHT - 120);
                    if (menuSelect % menuOptions == 10 && settingSelect == -1) {
                        drawString(g, "->",
                                center("->   Return"), BOARD_HEIGHT - 120);
                    }
                } else if (settingSelect == 1) {
                    drawString(g, "Set Music Volume to:",
                            center("Set Music Volume to:"), 320 - 95 - 40);
                    if (menuSelect % menuOptions == 0) {
                        drawString(g, "->", center("->   off"), 320 - 95);
                    }
                    drawString(g, "OFF", center("OFF"), 320 - 95);
                    if (menuSelect % menuOptions == 1) {
                        drawString(g, "->", center("->   low"), 320 - 55);
                    }
                    drawString(g, "Low", center("Low"), 320 - 55);
                    if (menuSelect % menuOptions == 2) {
                        drawString(g, "->", center("->   medium"), 320 - 15);
                    }
                    drawString(g, "Medium", center("Medium"), 320 - 15);
                    if (menuSelect % menuOptions == 3) {
                        drawString(g, "->", center("->   high"), 320 + 25);
                    }
                    drawString(g, "High", center("high"), 320 + 25);

                } else if (settingSelect == 2) {
                    drawString(g, "Set SFX Volume to:",
                            center("Set SFX Volume to:"), 320 - 95 - 40);
                    if (menuSelect % menuOptions == 0) {
                        drawString(g, "->", center("->   off"), 320 - 95);
                    }
                    drawString(g, "OFF", center("OFF"), 320 - 95);
                    if (menuSelect % menuOptions == 1) {
                        drawString(g, "->", center("->   low"), 320 - 55);
                    }
                    drawString(g, "Low", center("Low"), 320 - 55);
                    if (menuSelect % menuOptions == 2) {
                        drawString(g, "->", center("->   medium"), 320 - 15);
                    }
                    drawString(g, "Medium", center("Medium"), 320 - 15);
                    if (menuSelect % menuOptions == 3) {
                        drawString(g, "->", center("->   high"), 320 + 25);
                    }
                    drawString(g, "High", center("high"), 320 + 25);
                } else if (settingSelect == 9) {
                    if (controlColor == null) {
                        controlColor = lineColor;
                    }
                    Color outputColor = lineColor;
                    lineColor = Color.PINK;
                    g.setColor(lineColor);
                    drawString(g, "Original Color:",
                            center("Original Color:"),
                            10);
                    lineColor = controlColor;
                    g.setColor(lineColor);
                    drawString(g, getCurrentColor(),
                            center(getCurrentColor()),
                            70);
                    lineColor = Color.PINK;
                    g.setColor(lineColor);
                    drawString(g, "Set Color to:",
                            center("Set Color to:"), 130);

                    lineColor = Color.WHITE;
                    g.setColor(lineColor);
                    drawString(g, "White", center("White"), 170);
                    if (menuSelect % menuOptions == 0) {
                        drawString(g, "->", center("->   white"), 170);
                        outputColor = Color.WHITE;
                    }

                    lineColor = Color.RED;
                    g.setColor(lineColor);
                    drawString(g, "Red", center("OFF"), 210);
                    if (menuSelect % menuOptions == 1) {
                        drawString(g, "->", center("->   off"), 210);
                        outputColor = Color.RED;
                    }

                    lineColor = Color.ORANGE;
                    g.setColor(lineColor);
                    drawString(g, "Orange", center("Orange"), 250);
                    if (menuSelect % menuOptions == 2) {
                        drawString(g, "->", center("->   orange"), 250);
                        outputColor = Color.ORANGE;
                    }

                    lineColor = Color.YELLOW;
                    g.setColor(lineColor);
                    drawString(g, "Yellow", center("yellow"), 290);
                    if (menuSelect % menuOptions == 3) {
                        drawString(g, "->", center("->   yellow"), 290);
                        outputColor = Color.YELLOW;
                    }

                    lineColor = Color.GREEN;
                    g.setColor(lineColor);
                    drawString(g, "Green", center("green"), 330);
                    if (menuSelect % menuOptions == 4) {
                        drawString(g, "->", center("->   green"), 330);
                        outputColor = Color.GREEN;
                    }

                    lineColor = Color.BLUE;
                    g.setColor(lineColor);
                    drawString(g, "Blue", center("blue"), 370);
                    if (menuSelect % menuOptions == 5) {
                        drawString(g, "->", center("->   blue"), 370);
                        outputColor = Color.BLUE;
                    }

                    lineColor = Color.MAGENTA;
                    g.setColor(lineColor);
                    drawString(g, "Purple", center("purple"), 410);
                    if (menuSelect % menuOptions == 6) {
                        drawString(g, "->", center("->   purple"), 410);
                        outputColor = Color.MAGENTA;
                    }

                    lineColor = Color.PINK;
                    g.setColor(lineColor);
                    drawString(g, "Multi Colored", center("Multi Colored"), 450);
                    if (menuSelect % menuOptions == 7) {
                        drawString(g, "->", center("->   Multi Colored"), 450);
                        outputColor = Color.PINK;
                    }

                    lineColor = Color.BLACK;
                    g.setColor(lineColor);
                    drawString(g, "Inverse", center("inverse"), 490);
                    if (menuSelect % menuOptions == 8) {
                        drawString(g, "->", center("->   inverse"), 490);
                        outputColor = Color.BLACK;
                    }

                    lineColor = Color.PINK;
                    g.setColor(lineColor);
                    drawString(g, "Cancel", center("Cancel"), 490 + 70);
                    if (menuSelect % menuOptions == 9) {
                        drawString(g, "->", center("->   Cancel"), 490 + 70);
                        outputColor = controlColor;
                    }
                    lineColor = outputColor;
                    g.setColor(lineColor);
                } else {

                }
            }
        }
    }

    private void resetHighScores() {
        while (highScores == null) {
            try {
                FileInputStream fileIn = new FileInputStream(new File(
                        "resources/scores.ser"));
                ObjectInputStream in = new ObjectInputStream(fileIn);
                highScores = (HighScoreList) in.readObject();
            } catch (IOException i) {
                print("High Scores Class Unreadable", true, true);
                highScores = new HighScoreList();
                return;
            } catch (ClassNotFoundException c) {
                print("High Scores Class not found", true, true);
                highScores = new HighScoreList();
                return;
            }
        }
    }

    private void resetSettings() {
        while (settings == null) {
            try {
                FileInputStream fileIn = new FileInputStream(new File(
                        "resources/settings.ser"));
                ObjectInputStream in = new ObjectInputStream(fileIn);
                settings = (Settings) in.readObject();
                if (settings == null) {
                    settings = new Settings();
                    save();
                }
            } catch (IOException | ClassNotFoundException i) {
                print("Settings Class not found", true, true);
                settings = new Settings();
                return;
            }
        }
    }

    private String getCurrentColor() {
        if (lineColor.getRGB() == Color.WHITE.getRGB()) {
            lineColor = Color.WHITE;
            return "White";
        } else if (lineColor.getRGB() == Color.RED.getRGB()) {
            lineColor = Color.RED;
            return "Red";
        } else if (lineColor.getRGB() == Color.ORANGE.getRGB()) {
            lineColor = Color.ORANGE;
            return "Orange";
        } else if (lineColor.getRGB() == Color.YELLOW.getRGB()) {
            lineColor = Color.YELLOW;
            return "Yellow";
        } else if (lineColor.getRGB() == Color.GREEN.getRGB()) {
            lineColor = Color.GREEN;
            return "Green";
        } else if (lineColor.getRGB() == Color.BLUE.getRGB()) {
            lineColor = Color.BLUE;
            return "Blue";
        } else if (lineColor.getRGB() == Color.MAGENTA.getRGB()) {
            lineColor = Color.MAGENTA;
            return "Purple";
        } else if (lineColor.getRGB() == Color.BLACK.getRGB()) {
            lineColor = Color.BLACK;
            return "Inverse";
        } else if (lineColor.getRGB() == Color.PINK.getRGB()) {
            lineColor = Color.PINK;
            return "Multi-Colored";
        } else {
            return "Error";
        }
    }

    private void drawLevel(Graphics g) {
        if (displayNextLevel) {
            drawString(g, "Level " + level + " Completed!",
                    center("Level " + level + " Completed!"), 240 - 20);
            drawString(g, "Level " + (level + 1) + " Ready!",
                    center("Level " + (level + 1) + " Ready!"), 240 + 20);
            if (lineColor.getRGB() == Color.PINK.getRGB()) {
                lineColor = Color.getHSBColor((float) (Math.random() * 255), 255, 255);
                drawString(g, "$   <<", center(">>  <<"), 305);
                lineColor = Color.getHSBColor((float) (Math.random() * 255), 255, 255);
                drawString(g, ">>", center(">>  <<"), 305);
                lineColor = Color.PINK;
            } else {
                drawString(g, ">>  <<", center(">>  <<"), 305);
            }
            if ((firstGame && level == 1)
                    || levelDelay < cycleCount - (60 * 5)) {
                drawString(g, "Return to Center",
                        center("Return to Center"), 280 + 70);
            }
            if ((firstGame && level == 1)
                    || levelDelay < cycleCount - (60 * 7)) {
                if (!wentThereButLeft && !giveUpWaiting) {
                    drawString(g, "To Continue",
                            center("To Continue"), 280 + 110);
                } else if (wentThereButLeft && !giveUpWaiting) {
                    if (onceRaged) {
                        drawString(g, "Before its too late",
                                center("Before its too late"), 280 + 110);
                    } else {
                        drawString(g, "Please",
                                center("Please"), 280 + 110);
                    }
                } else {
                    if (rageIncreaseLevelDifficulty) {
                        drawString(g, "TO get this over with",
                                center("TO get this over with"), 280 + 110);
                    } else {
                        drawString(g, "NOW!!!",
                                center("NOW!!!"), 280 + 110);
                    }
                }
            }
            if (gauntletOn) {
                drawString(g, "Well, you survived",
                        center("Well, you survived"), 240 - 100);
                if (gauntletOn) {
                    if (gauntletLivesLost == 1) {
                        drawString(g, "And only 1 life Lost",
                                center("And only 1 life Lost"), 240 - 60);
                    } else if (gauntletLivesLost == 0) {
                        drawString(g, "Not a single life Lost",
                                center("not a single life Lost"), 240 - 60);
                    } else {
                        drawString(g, "And only " + gauntletLivesLost + " lives Lost",
                                center("And only " + gauntletLivesLost
                                        + " lives Lost"), 240 - 60);
                    }
                }
            }

            if (levelDelay < cycleCount - (60 * 9)
                    && !youStillThereQuestioning
                    && level != 1) {
                if (wentThereButLeft) {
                    giveUpWaiting = true;
                }
                if (youStillThereQuestioning) {
                    wentThereButLeft = true;
                }
                youStillThereQuestioning = true;
            }

            if (onceRaged) {
                if (doneWithRageText) {
                    drawString(g, "...",
                            center("..."), 280 + 150);
                } else {
                    if (rageIncreaseLevelDifficulty) {
                        drawString(g, "...",
                                center("..."), 280 + 150);
                    } else {
                        if (((levelDelay < cycleCount - (60 * 9)
                                && level != 1
                                && youStillThereQuestioning)
                                || (levelDelay < cycleCount - (60 * 9)
                                && level != 1
                                && wentThereButLeft)
                                || (levelDelay < cycleCount - (60 * 9)
                                && level != 1
                                && giveUpWaiting)) && !doneWithRageText) {
                            if (!wentThereButLeft && !giveUpWaiting) {
                                drawString(g, "Bro, hold up",
                                        center("Bro, hold up"), 280 + 150);
                            } else if (wentThereButLeft && !giveUpWaiting) {
                                drawString(g, "Ok, Here's the countdown!",
                                        center("Ok, Here's the countdown!"), 280 + 150);
                            } else {
                                drawString(g, "Ok, Here's the countdown!",
                                        center("Ok, Here's the countdown!"), 280 + 150);
                            }
                        }
                        if (((levelDelay < cycleCount - (60 * 12)
                                && level != 1
                                && youStillThereQuestioning)
                                || (levelDelay < cycleCount - (60 * 13)
                                && level != 1
                                && wentThereButLeft)
                                || (levelDelay < cycleCount - (60 * 9)
                                && level != 1
                                && giveUpWaiting)) && !doneWithRageText) {
                            if (!wentThereButLeft && !giveUpWaiting) {
                                drawString(g, "If you don't think",
                                        center("If you don't think"), 280 + 190);
                            } else if (wentThereButLeft && !giveUpWaiting) {
                                drawString(g, "3",
                                        center("3"), 280 + 190);
                            } else {
                                drawString(g, "3",
                                        center("3"), 280 + 190);
                            }
                        }
                        if (((levelDelay < cycleCount - (60 * 15)
                                && level != 1
                                && youStillThereQuestioning)
                                || (levelDelay < cycleCount - (60 * 15)
                                && level != 1
                                && wentThereButLeft)
                                || (levelDelay < cycleCount - (60 * 9)
                                && level != 1
                                && giveUpWaiting)) && !doneWithRageText) {
                            if (!wentThereButLeft && !giveUpWaiting) {
                                drawString(g, "I won't rage again",
                                        center("I won't rage again"), 280 + 230);
                            } else if (wentThereButLeft && !giveUpWaiting) {
                                drawString(g, "2",
                                        center("2"), 280 + 230);
                            } else {
                                drawString(g, "2",
                                        center("2"), 280 + 230);
                            }
                        }
                        if (((levelDelay < cycleCount - (60 * 18)
                                && level != 1
                                && youStillThereQuestioning)
                                || (levelDelay < cycleCount - (60 * 17)
                                && level != 1
                                && wentThereButLeft)
                                || (levelDelay < cycleCount - (60 * 9)
                                && level != 1
                                && giveUpWaiting)) && !doneWithRageText) {
                            if (!wentThereButLeft && !giveUpWaiting) {
                                drawString(g, "You're mistaken",
                                        center("You're mistaken"), 280 + 270);
                            } else if (wentThereButLeft && !giveUpWaiting) {
                                drawString(g, "1",
                                        center("1"), 280 + 270);
                            } else {
                                drawString(g, "1",
                                        center("1"), 280 + 270);
                            }
                        }
                        if (((levelDelay < cycleCount - (60 * 21)
                                && level != 1
                                && youStillThereQuestioning)
                                || (levelDelay < cycleCount - (60 * 19)
                                && level != 1
                                && wentThereButLeft)
                                || (levelDelay < cycleCount - (60 * 9)
                                && level != 1
                                && giveUpWaiting)) && !doneWithRageText) {
                            if (!wentThereButLeft && !giveUpWaiting) {
                                drawString(g, "try me",
                                        center("try me"), 280 + 310);
                            } else if (wentThereButLeft && !giveUpWaiting) {
                                drawString(g, "Done. Have Fun!",
                                        center("Done. Have Fun!"), 280 + 310);
                                rageIncreaseLevelDifficulty = true;
                                giveUpWaiting = true;
                            } else {
                                drawString(g, "Done. Have Fun!",
                                        center("Done. Have Fun!"), 280 + 310);
                            }
                        }
                    }
                }
            } else {
                if ((levelDelay > cycleCount - (60 * 9)
                        && level != 1
                        && youStillThereQuestioning)
                        || (levelDelay > cycleCount - (60 * 9)
                        && level != 1
                        && wentThereButLeft)
                        || (levelDelay > cycleCount - (60 * 9)
                        && level != 1
                        && giveUpWaiting)) {
                    if (!wentThereButLeft && !giveUpWaiting) {
                        drawString(g, "You're Back!!!",
                                center("You're Back!!!"), 280 + 150);
                    } else if (wentThereButLeft && !giveUpWaiting) {
                        drawString(g, "You came back!?",
                                center("You came back!?"), 280 + 150);
                    } else {
                        if (rageIncreaseLevelDifficulty) {
                            drawString(g, "...",
                                    center("..."), 280 + 150);
                            doneWithRageText = true;
                        } else {
                            drawString(g, "Are you Ready Now?!?",
                                    center("Are you Ready Now???"), 280 + 150);
                        }
                    }
                }
                if (doneWithRageText) {
                    drawString(g, "...",
                            center("..."), 280 + 150);
                    doneWithRageText = true;
                } else {
                    if (((levelDelay < cycleCount - (60 * 12)
                            && youStillThereQuestioning
                            && level != 1)
                            || (levelDelay < cycleCount - (60 * 9)
                            && wentThereButLeft
                            && level != 1)
                            || (levelDelay < cycleCount - (60 * 9)
                            && giveUpWaiting
                            && level != 1)) && !doneWithRageText) {
                        if (!wentThereButLeft && !giveUpWaiting) {
                            drawString(g, "Hello?",
                                    center("Hello?"), 280 + 150);
                        } else if (wentThereButLeft && !giveUpWaiting) {
                            drawString(g, "Oh, mocking me?",
                                    center("Oh, mocking me?"), 280 + 150);
                        } else {
                            drawString(g, "I guess not!",
                                    center("I guess not!"), 280 + 150);
                        }
                    }
                    if (((levelDelay < cycleCount - (60 * 15)
                            && youStillThereQuestioning
                            && level != 1)
                            || (levelDelay < cycleCount - (60 * 12)
                            && wentThereButLeft
                            && level != 1)
                            || (levelDelay < cycleCount - (60 * 11)
                            && giveUpWaiting
                            && level != 1)) && !doneWithRageText) {
                        if (!wentThereButLeft && !giveUpWaiting) {
                            drawString(g, "You still Playing?",
                                    center("You still Playing?"), 280 + 190);
                        } else if (wentThereButLeft && !giveUpWaiting) {
                            drawString(g, "Fine. I get it.",
                                    center("Fine. I get it."), 280 + 190);
                        } else {
                            drawString(g, "That's it!!!",
                                    center("That's it!!!"), 280 + 190);
                        }
                    }
                    if (((levelDelay < cycleCount - (60 * 19)
                            && youStillThereQuestioning
                            && level != 1)
                            || (levelDelay < cycleCount - (60 * 15)
                            && wentThereButLeft
                            && level != 1)
                            || (levelDelay < cycleCount - (60 * 13)
                            && giveUpWaiting
                            && level != 1)) && !doneWithRageText) {
                        if (!wentThereButLeft && !giveUpWaiting) {
                            drawString(g, "Please come back",
                                    center("Please come back"), 280 + 230);
                        } else if (wentThereButLeft && !giveUpWaiting) {
                            drawString(g, "Come back whenever.",
                                    center("Come back whenever."), 280 + 230);
                        } else {
                            drawString(g, "game difficulty increased",
                                    center("game difficulty increased"), 280 + 230);
                            rageIncreaseLevelDifficulty = true;
                        }
                    }
                    if (((levelDelay < cycleCount - (60 * 24)
                            && youStillThereQuestioning
                            && level != 1)
                            || (levelDelay < cycleCount - (60 * 18)
                            && wentThereButLeft
                            && level != 1)
                            || (levelDelay < cycleCount - (60 * 15)
                            && giveUpWaiting
                            && level != 1)) && !doneWithRageText) {
                        if (!wentThereButLeft && !giveUpWaiting) {
                            drawString(g, "I miss you",
                                    center("I miss you"), 280 + 270);
                        } else if (wentThereButLeft && !giveUpWaiting) {
                            drawString(g, "I can wait.",
                                    center("I can wait."), 280 + 270);
                        } else {
                            drawString(g, "Wait on that, jerk!",
                                    center("Wait on that, jerk!"), 280 + 270);
                        }
                    }
                    if (((levelDelay < cycleCount - (60 * 29)
                            && youStillThereQuestioning
                            && level != 1)
                            || (levelDelay < cycleCount - (60 * 21)
                            && wentThereButLeft
                            && level != 1)
                            || (levelDelay < cycleCount - (60 * 18)
                            && giveUpWaiting
                            && level != 1)) && !doneWithRageText) {
                        if (!wentThereButLeft && !giveUpWaiting) {
                            drawString(g, "I'm Sorry",
                                    center("I'm Sorry"), 280 + 310);
                        } else if (wentThereButLeft && !giveUpWaiting) {
                            drawString(g, ">:(",
                                    center(">:("), 280 + 310);
                        } else {
                            drawString(g, "Heck You!!",
                                    center("Heck You!!"), 280 + 310);

                        }
                    }
                }
            }
        }
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            if (!isStarted) {
                return;
            }
            int keycode = e.getKeyCode();
            switch (keycode) {
                case KeyEvent.VK_RIGHT:
                    playerRotate = 0;
                    break;

                case KeyEvent.VK_D:
                    playerRotate = 0;
                    break;

                case KeyEvent.VK_LEFT:
                    playerRotate = 0;
                    break;

                case KeyEvent.VK_A:
                    playerRotate = 0;
                    break;

                case KeyEvent.VK_UP:
                    thrust = false;
                    break;

                case KeyEvent.VK_W:
                    thrust = false;
                    break;

                case KeyEvent.VK_SPACE:
                    fire = false;
                    break;

                case KeyEvent.VK_ENTER:
                    fire = false;
                    break;
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyChar() == '$') {
                showHitBoxes = !showHitBoxes;
                return;
            }
            if (e.getKeyCode() == KeyEvent.VK_UP
                    && (konamiProgress == 0 || konamiProgress == 1)) {
                konamiProgress++;
            } else {
                if (e.getKeyCode() == KeyEvent.VK_DOWN
                        && (konamiProgress == 2 || konamiProgress == 3)) {
                    konamiProgress++;
                } else {
                    if (e.getKeyCode() == KeyEvent.VK_LEFT
                            && konamiProgress == 4) {
                        konamiProgress++;
                    } else {
                        if (e.getKeyCode() == KeyEvent.VK_RIGHT
                                && konamiProgress == 5) {
                            konamiProgress++;
                        } else {
                            if (e.getKeyCode() == KeyEvent.VK_LEFT
                                    && konamiProgress == 6) {
                                konamiProgress++;
                            } else {
                                if (e.getKeyCode() == KeyEvent.VK_RIGHT
                                        && konamiProgress == 7) {
                                    konamiProgress++;
                                } else {
                                    if (e.getKeyCode() == KeyEvent.VK_B
                                            && konamiProgress == 8) {
                                        konamiProgress++;
                                    } else {
                                        if (e.getKeyCode()
                                                == KeyEvent.VK_A
                                                && konamiProgress == 9) {
                                            konamiProgress++;
                                        } else {
                                            if (((e.getKeyCode()
                                                    == KeyEvent.VK_ENTER
                                                    || e.getKeyCode()
                                                    == KeyEvent.VK_SPACE)
                                                    && !konami)
                                                    && konamiProgress >= 9) {
                                                konamiProgress = -1;
                                                konami = true;
                                                return;
                                            } else {
                                                konamiProgress = 0;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (!programStarted) {
                if (Arrays.asList(alphabet).contains(
                        ("" + e.getKeyChar()).toUpperCase())
                        || Arrays.asList(numberLine).contains(
                                "" + e.getKeyChar())
                        || e.getKeyChar() == ' '
                        || e.getKeyCode() == KeyEvent.VK_ENTER) {
                    programStarted = true;
                    beginProgram = 0;
                    initBoard();
                    preview();
                    String i = getCurrentColor();
                    settings.setLineColor(settings.getLineColor());
                    if (SoundEffect.INTRO.isRunning()) {
                        SoundEffect.INTRO.stop();
                    }
                    showMenu();
                }
            } else {
                if (highScoreInput || numberInput) {
                    if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                        if (initialInput < name.length - 1) {
                            initialInput++;
                            initialLetter = name[initialInput];
                        }
                        SoundEffect.TYPE.play();
                    }
                    if (e.getKeyCode() == KeyEvent.VK_D) {
                        if (initialInput < name.length - 1) {
                            initialInput++;
                            initialLetter = name[initialInput];
                        }
                        SoundEffect.TYPE.play();
                    }
                    if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                        if (initialInput > 0) {
                            initialInput--;
                            initialLetter = name[initialInput];
                        }
                        SoundEffect.TYPE.play();
                    }
                    if (e.getKeyCode() == KeyEvent.VK_A) {
                        if (initialInput > 0) {
                            initialInput--;
                            initialLetter = name[initialInput];
                        }
                        SoundEffect.TYPE.play();
                    }
                    if (e.getKeyCode() == KeyEvent.VK_UP) {
                        initialLetter++;
                        SoundEffect.TYPE.play();
                    }
                    if (e.getKeyCode() == KeyEvent.VK_W) {
                        initialLetter++;
                        SoundEffect.TYPE.play();
                    }
                    if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                        if (initialLetter != 0) {
                            initialLetter--;
                        } else {
                            if (highScoreInput && !numberInput) {
                                initialLetter = 26;
                            } else {
                                initialLetter = 9;
                            }
                        }
                        SoundEffect.TYPE.play();
                    }
                    if (e.getKeyCode() == KeyEvent.VK_S) {
                        if (initialLetter != 0) {
                            initialLetter--;
                        } else {
                            if (highScoreInput && !numberInput) {
                                initialLetter = 26;
                            } else {
                                initialLetter = 9;
                            }
                        }
                        SoundEffect.TYPE.play();
                    }
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        if (addNewHighScoreScoreInput) {
                            numberInput = false;
                            String n = "";
                            for (int i = 0; i < name.length; i++) {
                                n += numberLine[name[i] % numberLine.length];
                            }
                            score = Long.parseLong(n);
                            select();
                        } else if (highScoreInput && !numberInput) {
                            saveScore();
                            showHighScore();
                        } else {
                            saveSettings();
                            showSettings();
                        }
                        SoundEffect.TYPE.play();
                    }
                    if (e.getKeyCode() == KeyEvent.VK_ESCAPE
                            && highScoreInput) {
                        highScoreInput = false;
                        showHighScore();
                    }
                    if (e.getKeyCode() == KeyEvent.VK_ESCAPE
                            && numberInput) {
                        numberInput = false;
                        addNewHighScoreScoreInput = false;
                        showHighScore();
                    }
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        if (addNewHighScoreScoreInput) {
                            numberInput = false;
                            String n = "";
                            for (int i = 0; i < name.length; i++) {
                                n += numberLine[name[i] % numberLine.length];
                            }
                            score = Long.parseLong(n);
                            select();
                        } else if (highScoreInput && !numberInput) {
                            saveScore();
                            showHighScore();
                        } else {
                            saveSettings();
                            showSettings();
                        }
                        SoundEffect.TYPE.play();
                    }
                    return;
                }
                if (!isStarted || gameOver) {
                    if (e.getKeyCode() == KeyEvent.VK_UP) {
                        menuSelect--;
                        while (menuSelect < 0) {
                            menuSelect += menuOptions;
                        }
                        SoundEffect.TYPE.play();
                    }
                    if (e.getKeyCode() == KeyEvent.VK_W) {
                        menuSelect--;
                        while (menuSelect < 0) {
                            menuSelect += menuOptions;
                        }
                        SoundEffect.TYPE.play();
                    }
                    if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                        menuSelect++;
                        SoundEffect.TYPE.play();
                    }
                    if (e.getKeyCode() == KeyEvent.VK_S) {
                        menuSelect++;
                        SoundEffect.TYPE.play();
                    }
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        select();
                        SoundEffect.TYPE.play();
                    }
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        select();
                        SoundEffect.TYPE.play();
                    }
                    return;
                }
                int keycode = e.getKeyCode();
                if (keycode == KeyEvent.VK_P) {
                    pause();
                    SoundEffect.TYPE.play();
                    return;
                }
                if (isPaused) {
                    if (e.getKeyCode() == KeyEvent.VK_C) {
                        showControls = !showControls;
                        SoundEffect.TYPE.play();
                    }

                    if (e.getKeyCode() == KeyEvent.VK_Q) {
                        initBoard();
                        preview();
                        showMenu();
                        isPaused = false;
                        SoundEffect.TYPE.play();
                    }
                    return;
                }
                switch (keycode) {
                    case KeyEvent.VK_RIGHT:
                        playerRotate = 1;
                        break;

                    case KeyEvent.VK_D:
                        playerRotate = 1;
                        break;

                    case KeyEvent.VK_LEFT:
                        playerRotate = -1;
                        break;

                    case KeyEvent.VK_A:
                        playerRotate = -1;
                        break;

                    case KeyEvent.VK_UP:
                        thrust = true;
                        break;

                    case KeyEvent.VK_W:
                        thrust = true;
                        break;

                    case KeyEvent.VK_SPACE:
                        if (!fire) {
                            fire();
                            fire = !fire;
                        }
                        break;

                    case KeyEvent.VK_ENTER:
                        if (!fire) {
                            fire();
                            fire = !fire;
                        }
                        break;

                    case KeyEvent.VK_H:
                        if (hyperSpaceTimer >= hyperSpaceCoolDown && player.getState() <= 2) {
                            hyperSpaceOn = true;
                            player.setState(0);
                            player.setTimeOfDeath(cycleCount);
                        }
                        break;
                }
            }
        }
    }

    private class ScheduleTask extends TimerTask {

        @Override
        public void run() {
            if (programStarted) {
                if (!doingCycle) {
                    doGameCycle();
                }
            } else {
                proceedInitialProgram();
            }
        }
    }
}
