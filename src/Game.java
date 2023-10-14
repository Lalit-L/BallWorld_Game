import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;

import javax.swing.event.ChangeEvent;

//import com.sun.prism.paint.Color;

import javafx.animation.AnimationTimer;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Game extends Application {

	private String curWorld;
    private BorderPane rootNode;
    private BallWorld world;
    
    private BorderPane rootNum2;
    private BallWorld lev;
    AnimationTimer worldBrick;
    
    private BorderPane root3;
    private BallWorld level3;
    AnimationTimer changeToFinal;
    
    private BorderPane finalRoot;
    AnimationTimer Finale;
    AnimationTimer finalCelebration;
    
    private final double DEFAULT_WIDTH = 500;
    private final double DEFAULT_HEIGHT = 500;
    private final double DEFAULT_BALL_X = 100;
    private final double DEFAULT_BALL_Y = 300;
    private final double DEFAULT_PADDLE_X = 100;
    private final double DEFAULT_PADDLE_Y = 300;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Ball World");
        rootNode = new BorderPane();
        world = new BallWorld();
        world.setName("world");
        world.setPrefWidth(DEFAULT_WIDTH);
        world.setPrefHeight(DEFAULT_HEIGHT);
        rootNode.setCenter(world);
        Brick[][] wB = new Brick[2][13];
        
        for (int i = 0; i < 13; i++) {
        	Brick b = new Brick(20 + 35*i, 100);
        	wB[0][i] = b;
        }
        for (int i = 0; i < 13; i++) {
        	Brick b = new Brick(20 + 35*i, 50);
        	wB[1][i] = b;
        }
        
        
        
        rootNum2 = new BorderPane();
        lev = new BallWorld();
        lev.setName("level2");
        lev.setPrefWidth(DEFAULT_WIDTH);
        lev.setPrefHeight(DEFAULT_HEIGHT);
        rootNum2.setCenter(lev);
        Brick[] lB = new Brick[13];
        StrongBrick[] lB2 = new StrongBrick[13];
        
        
        for (int i = 0; i < 13; i++) {
        	Brick b = new Brick(20 + 35*i, 100);
        	lB[i] = b;
        }
        for (int i = 0; i < 13; i++) {
        	StrongBrick b = new StrongBrick(20 + 35*i, 50);
        	lB2[i] = b;
        }
        
        root3= new BorderPane();
        level3 = new BallWorld();
        level3.setPrefWidth(DEFAULT_WIDTH);
        level3.setPrefHeight(DEFAULT_HEIGHT);
        root3.setCenter(level3);
        
        Scene s = new Scene(rootNode);
        Scene Level2 = new Scene(rootNum2);
        Scene Level3 = new Scene(root3);
        
        BorderPane joe = new BorderPane();
        VBox options = new VBox();
        options.setSpacing(20);
	    options.setMaxHeight(DEFAULT_HEIGHT);
	    options.setAlignment(Pos.CENTER);
        
    	Button start = new Button("Start Game");
    	Button story = new Button("Story");
    	Button control = new Button("Controls");
    	Button chooseSave = new Button("Start From Save");
    	
    	BackgroundImage myBI= new BackgroundImage(new Image("resources/Space.JPG",500,500,false,true),
    	BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
    	BackgroundSize.DEFAULT);
    	
    	rootNode.setBackground(new Background(myBI));
    	rootNum2.setBackground(new Background(myBI));
    	root3.setBackground(new Background(myBI));
    	
    	//start.setMinHeight(50);
    	start.setBackground(new Background(myBI));
    	start.setStyle("-fx-text-fill: white");
    	start.setFont(Font.font("Comic Sans MS", FontPosture.ITALIC, 23));
    	
    	story.setBackground(new Background(myBI));
    	story.setStyle("-fx-text-fill: white");
    	story.setFont(Font.font("Comic Sans MS", FontPosture.ITALIC, 17));
    	
    	control.setBackground(new Background(myBI));
    	control.setStyle("-fx-text-fill: white");
    	control.setFont(Font.font("Comic Sans MS", FontPosture.ITALIC, 17));
    	
    	chooseSave.setBackground(new Background(myBI));
    	chooseSave.setStyle("-fx-text-fill: white");
    	chooseSave.setFont(Font.font("Comic Sans MS", FontPosture.ITALIC, 17));
    	    	
    	options.getChildren().addAll(start, story, control, chooseSave);
    	
    	Scene sc = new Scene(joe, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    	
    	Button save = new Button("Save");
    	save.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (save.getScene() == s) {
					FileWriter fr;
					try {
						world.stop();
						File fi = new File("BallSave.txt");
						fr = new FileWriter("BallSave.txt");
						int lc = 0;
						fr.write("world\n");
						Score sco = world.getScore();
						int curScore = sco.getScore();
						String scor = curScore + "";
						fr.write(scor + "\n");
						/*for (Brick[] b: wB) {
							int br = 0;
							for (Brick bri: b) {
								String str = lc + " " + br + " " + bri.returnX() + " " + bri.returnY();
								fr.write(str + "\n");
								br++;
							}
							lc++;
						}*/
						int br = 0;
						int totalBricks = 0;
						for (Brick b : world.getBricks(Brick.class)) {
							totalBricks++;
							/*if (world.getBricks(Brick.class).size() - totalBricks > 0) {
								lc++;
							}*/
							String str = b.returnX() + " " + b.returnY();
							fr.write(str + "\n");
							br++;
							if (br == 13) {
								br = 0;
								lc++;
							}
						}
						fr.close();
						new java.util.Timer().schedule(
			            		new java.util.TimerTask() {
									@Override
									public void run() {
										world.start();
									}
			            		},
			            		1000
			            );
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				else {
					FileWriter fr;
					try {
						lev.stop();
						File fi = new File("BallSave.txt");
						fr = new FileWriter("BallSave.txt");
						fr.write("level2\n");
						Score sco = lev.getScore();
						int curScore = sco.getScore();
						String scor = curScore + "";
						fr.write(scor + "\n");
						/*int br = 0;
							for (Brick bri: lB) {
								String str = 0 + " " + br + " " + bri.returnX() + " " + bri.returnY();
								fr.write(str + "\n");
								br++;
							}*/
						for (Brick b : lev.getBricks(Brick.class)) {
							String str = b.returnX() + " " + b.returnY();
							fr.write(str + "\n");
						}
						for (StrongBrick b : lev.getStrong(StrongBrick.class)) {
							String str = b.returnX() + " " + b.returnY();
							fr.write(str + "\n");
						}
						fr.close();
						new java.util.Timer().schedule(
			            		new java.util.TimerTask() {
									@Override
									public void run() {
										lev.start();
									}
			            		},
			            		1000
			            );
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
    		
    	});
    	
    	//rootNode.setBottom(save);
    	//rootNum2.setRight(save);
    	
    	Button back = new Button("Exit");
        back.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				world.stop();
				lev.stop();
				level3.stop();
				stage.setScene(sc);
			}
        });
    	
        start.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//rootNode.setTop(back);
				//stage.setScene(s);
				if (world.getBricks(Brick.class).size() == 0) {
					if (lev.getBricks(Brick.class).size() == 0 && lev.getStrong(StrongBrick.class).size() == 0) {
						stage.setScene(Level3);
						level3.start();
						level3.requestFocus();
					}
					else {
						stage.setScene(Level2);
						lev.start();
						lev.requestFocus();
					}
				}
				else {
					stage.setScene(s);
					world.start();
					world.requestFocus();
				}
				//world.start();
				//world.requestFocus();
			}
    	});
        story.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Story st = new Story();
				st.setTop(back);
				Scene story = new Scene(st, DEFAULT_WIDTH, DEFAULT_HEIGHT);
				stage.setScene(story);
			}
    	});
        control.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ShowControls show = new ShowControls();
				show.setTop(back);
				Scene sho = new Scene(show, DEFAULT_WIDTH, DEFAULT_HEIGHT);
				stage.setScene(sho);
			}
    	});
        chooseSave.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Scanner read;
				try {
					read = new Scanner(new File("BallSave.txt"));
					String str = read.nextLine();
					String sc = read.nextLine();
					int score = Integer.parseInt(sc);
					if (!str.equals("world")) {
						for (Brick br: lev.getBricks(Brick.class)) {
							lev.remove(br);
						}
						for (StrongBrick br: lev.getStrong(StrongBrick.class)) {
							lev.remove(br);
						}
						lev.getScore().setScore(score);
					}
					else {
						for (Brick br: world.getBricks(Brick.class)) {
							world.remove(br);
						}
						world.getScore().setScore(score);
					}
					while (read.hasNextLine()) {
						String s = read.nextLine();
						String[] ar = s.split("\\s+");
						int x = 0;
						int y = 0;
						for (int i = 0; i < ar.length; i++) {
							if (i == 0) {
								x = Integer.parseInt(ar[i]);
							}
							else {
								y = Integer.parseInt(ar[i]);
							}
						}
						if (str.equals("world")) {
							Brick b = new Brick(x, y);
							b.setX(x);
							b.setY(y);
							world.add(b);
						}
						else {
							if (y == 50) {
								StrongBrick b = new StrongBrick(x, y);
								b.setX(x);
								b.setY(y);
								lev.add(b);
							}
							else {
								Brick b = new Brick(x, y);
								b.setX(x);
								b.setY(y);
								lev.add(b);
							}
						}
					}
					if (str.equals("world")) {
						HBox anotherOne = new HBox();
						anotherOne.setSpacing(410);
						anotherOne.getChildren().addAll(back, save);
						rootNode.setTop(anotherOne);
						stage.setScene(s);
						world.start();
						world.requestFocus();
					}
					else {
						HBox anotherOne = new HBox();
						anotherOne.setSpacing(400);
						anotherOne.getChildren().addAll(back, save);
						rootNum2.setTop(anotherOne);
						stage.setScene(Level2);
						lev.start();
						lev.requestFocus();
					}
				} catch (FileNotFoundException e) {
					//e.printStackTrace();
					System.out.println("There is no save file. Please play the game.");
				}
			}
    	});
    	joe.setCenter(options);
    	stage.setScene(sc);
    	stage.show();
        
    	
    	Media longMusic;
    	MediaPlayer musicPlayer;
		try {
			longMusic = new Media(getClass().getClassLoader().getResource("resources/MainMusic.mp3").toURI().toString());
			musicPlayer =  new MediaPlayer(longMusic);
    		musicPlayer.setCycleCount(1);
    		musicPlayer.play();
    		AnimationTimer mus = new AnimationTimer() {
    			@Override
    			public void handle(long now) {
    				if (stage.getScene() == s || stage.getScene() == Level2 || stage.getScene() == Level3) {
    					musicPlayer.stop();
    				}
    				else if (stage.getScene() == sc) {
    					musicPlayer.play();
    				}
    			}
    		};
    		mus.start();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		Media inGame;
    	MediaPlayer gameMusic;
		try {
			inGame = new Media(getClass().getClassLoader().getResource("resources/battleThemeA.mp3").toURI().toString());
			gameMusic =  new MediaPlayer(inGame);
			gameMusic.setCycleCount(1);
			gameMusic.play();
    		AnimationTimer mus = new AnimationTimer() {
    			@Override
    			public void handle(long now) {
    				if (stage.getScene() == s || stage.getScene() == Level2 || stage.getScene() == Level3) {
    					gameMusic.play();
    				}
    				else if (level3.lvlChange() == true) {
    					gameMusic.stop();
    				}
    				else {
    					gameMusic.stop();
    				}
    			}
    		};
    		mus.start();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
    	BackgroundImage black = new BackgroundImage(new Image("resources/black.jpg",600,600,false,true),
    	        BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
    	          BackgroundSize.DEFAULT);
    	rootNode.setBackground(new Background(black));
    	rootNum2.setBackground(new Background(black));
    	root3.setBackground(new Background(black));
    	
    	BackgroundImage mainMenu = new BackgroundImage(new Image("resources/MainMenu.jpg",500,500,false,true),
    	        BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
    	          BackgroundSize.DEFAULT);
    	joe.setBackground(new Background(mainMenu));

        Ball ball = new Ball();
        ball.setX(DEFAULT_BALL_X);
        ball.setY(DEFAULT_BALL_Y);
        //rootNode.getChildren().addAll(ball);
        world.add(ball);
        
        Paddle paddle = new Paddle(5);
        paddle.setX(DEFAULT_PADDLE_X);
        paddle.setY(DEFAULT_PADDLE_Y);
        
        
        AnimationTimer paddleTurn = new AnimationTimer() {
			private long rnnow = 0;
			@Override
			public void handle(long now) {
				long rn = now - rnnow;
				if (rn >= 2 * 1e9) {
				rnnow = now;
				double rand = Math.random()*5;
		        if (rand > 3) {
		        RotateTransition rt = new RotateTransition();
		        rt.setDuration(Duration.seconds(1));
		        rt.setByAngle(420);
		        rt.setAutoReverse(true);
		        rt.setCycleCount(2);
		        rt.setNode(paddle);
		        //rootNode.setBottom(rt);
		        rt.play();
		        }
		        
				}
			}
        	
        };
        paddleTurn.start();
        
        
        WorldRotate wr = new WorldRotate();
        wr.setX(DEFAULT_WIDTH * Math.random());
        wr.setY((300) * Math.random()+50);
        world.add(wr);
        
        WorldRotate wr2 = new WorldRotate();
        wr2.setX(DEFAULT_WIDTH * Math.random());
        wr2.setY((300) * Math.random()+50);
        lev.add(wr2);
        
        WorldRotate wr3 = new WorldRotate();
        wr3.setX(DEFAULT_WIDTH * Math.random());
        wr3.setY((300) * Math.random()+50);
        level3.add(wr3);
        
        
        PowerUp pr = new PowerUp();
        pr.setX(DEFAULT_WIDTH * Math.random());
        pr.setY((300) * Math.random()+50);
        lev.add(pr);
        
        PowerUp pr2 = new PowerUp();
        pr2.setX(DEFAULT_WIDTH * Math.random());
        pr2.setY((300) * Math.random()+50);
        level3.add(pr2);
        
        
        AnimationTimer bruh = new AnimationTimer() {
			private long rnnow = 0;
			@Override
			public void handle(long now) {
				if (ball.rotate()) {
		        RotateTransition rt = new RotateTransition();
		        rt.setDuration(Duration.seconds(10));
		        rt.setByAngle(360);
		        rt.setAutoReverse(true);
		        rt.setCycleCount(1);
		        rt.setNode(world);
		        rt.play();
		        ball.setRotate();
				}
			}
        	
        };
        bruh.start();
        
       /*AnimationTimer bruhment = new AnimationTimer() {
			private long rnnow = 0;
			@Override
			public void handle(long now) {
				if (ball2.rotate()) {
		        RotateTransition rt = new RotateTransition();
		        rt.setDuration(Duration.seconds(10));
		        rt.setByAngle(360);
		        rt.setAutoReverse(true);
		        rt.setCycleCount(1);
		        rt.setNode(lev);
		        rt.play();
		        ball2.setRotate();
				}
			}
        	
        };
        bruhment.start();*/
        
        world.add(paddle);
        HBox hw = new HBox();
        hw.setSpacing(410);
        hw.getChildren().addAll(back, save);
        rootNode.setTop(hw);
        //rootNode.setBottom(back);
        
        Button Bruhme = new Button("Bruhmaoooooo");
        //rootNode.setTop(Bruhme);
        
        Ball ball2 = new Ball(6, 6);
        ball2.setX(DEFAULT_BALL_X);
        ball2.setY(DEFAULT_BALL_Y);
        //rootNode.getChildren().addAll(ball);
        lev.add(ball2);
        
        AnimationTimer bruhment = new AnimationTimer() {
			private long rnnow = 0;
			@Override
			public void handle(long now) {
				if (ball2.rotate()) {
		        RotateTransition rt = new RotateTransition();
		        rt.setDuration(Duration.seconds(10));
		        rt.setByAngle(360);
		        rt.setAutoReverse(true);
		        rt.setCycleCount(1);
		        rt.setNode(lev);
		        rt.play();
		        ball2.setRotate();
				}
			}
        	
        };
        bruhment.start();
        
        
        Paddle paddle2 = new Paddle(5);
        paddle2.setX(DEFAULT_PADDLE_X);
        paddle2.setY(DEFAULT_PADDLE_Y);
        lev.add(paddle2);
        
        AnimationTimer paddleTurn2 = new AnimationTimer() {
			private long rnnow = 0;
			@Override
			public void handle(long now) {
				long rn = now - rnnow;
				if (rn >= 2 * 1e9) {
				rnnow = now;
				double rand = Math.random()*5;
		        if (rand > 3) {
		        RotateTransition rt = new RotateTransition();
		        rt.setDuration(Duration.seconds(1));
		        rt.setByAngle(420);
		        rt.setAutoReverse(true);
		        rt.setCycleCount(2);
		        rt.setNode(paddle2);
		        //rootNode.setBottom(rt);
		        rt.play();
		        }
		        
				}
			}
        	
        };
        paddleTurn2.start();
        
        Ball ball3 = new Ball();
        ball3.setX(DEFAULT_BALL_X);
        ball3.setY(DEFAULT_BALL_Y);
        //rootNode.getChildren().addAll(ball);
        level3.add(ball3);
        
        AnimationTimer worldTurn3 = new AnimationTimer() {
			private long rnnow = 0;
			@Override
			public void handle(long now) {
				if (ball3.rotate()) {
		        RotateTransition rt = new RotateTransition();
		        rt.setDuration(Duration.seconds(10));
		        rt.setByAngle(360);
		        rt.setAutoReverse(true);
		        rt.setCycleCount(1);
		        rt.setNode(level3);
		        rt.play();
		        ball3.setRotate();
				}
			}
        	
        };
        worldTurn3.start();
        
        Paddle paddle3 = new Paddle(5);
        paddle3.setX(DEFAULT_PADDLE_X);
        paddle3.setY(DEFAULT_PADDLE_Y);
        level3.add(paddle3);
        
        AnimationTimer paddleTurn3 = new AnimationTimer() {
			private long rnnow = 0;
			@Override
			public void handle(long now) {
				long rn = now - rnnow;
				if (rn >= 2 * 1e9) {
				rnnow = now;
				double rand = Math.random()*5;
		        if (rand > 3) {
		        RotateTransition rt = new RotateTransition();
		        rt.setDuration(Duration.seconds(1));
		        rt.setByAngle(420);
		        rt.setAutoReverse(true);
		        rt.setCycleCount(2);
		        rt.setNode(paddle3);
		        //rootNode.setBottom(rt);
		        rt.play();
		        }
		        
				}
			}
        	
        };
        paddleTurn3.start();
        
        int howMany = (int)(Math.random() * 2 + 1);
        for (int i = 0; i < howMany; i++) {
        	double x = Math.random()*DEFAULT_WIDTH;
        	double y = Math.random()*300 + 50;
        	SpeedUp sp = new SpeedUp();
            sp.setX(x);
            sp.setY(y);
            world.add(sp);
        }
        for (int i = 0; i < howMany; i++) {
        	double x = Math.random()*DEFAULT_WIDTH;
        	double y = Math.random()*300 + 50;
        	SpeedUp sp = new SpeedUp();
            sp.setX(x);
            sp.setY(y);
            lev.add(sp);
        }
        for (int i = 0; i < howMany; i++) {
        	double x = Math.random()*DEFAULT_WIDTH;
        	double y = Math.random()*300 + 50;
        	SpeedUp sp = new SpeedUp();
            sp.setX(x);
            sp.setY(y);
            level3.add(sp);
        }
        for (int i = 0; i < howMany; i++) {
        	double x = Math.random()*DEFAULT_WIDTH;
        	double y = Math.random()*300 + 50;
        	SpeedUp sp = new SpeedUp();
            sp.setX(x);
            sp.setY(y);
            level3.add(sp);
        }
        
        
        for (int i = 0; i < 13; i++) {
        	Brick b = new Brick(20 + 35*i, 100);
        	b.setX(20 + 35*i);
        	b.setY(100);
        	world.add(b);
        }
        for (int i = 0; i < 13; i++) {
        	Brick b = new Brick(20 + 35*i, 50);
        	b.setX(20 + 35*i);
        	b.setY(50);
        	world.add(b);
        }
        for (int i = 0; i < 13; i++) {
        	Brick b = new Brick(20 + 35*i, 100);
        	b.setX(20 + 35*i);
        	b.setY(150);
        	lev.add(b);
        }
        for (int i = 0; i < 13; i++) {
        	Brick b = new Brick(20 + 35*i, 100);
        	b.setX(20 + 35*i);
        	b.setY(100);
        	lev.add(b);
        }
        for (int i = 0; i < 13; i++) {
        	StrongBrick b = new StrongBrick(20 + 35*i, 50);
        	b.setX(20 + 35*i);
        	b.setY(50);
        	lev.add(b);
        }
        for (int i = 0; i < 13; i++) {
        	StrongBrick b = new StrongBrick(20 + 35*i, 150);
        	b.setX(20 + 35*i);
        	b.setY(150);
        	level3.add(b);
        }
        for (int i = 0; i < 13; i++) {
        	StrongBrick b = new StrongBrick(20 + 35*i, 100);
        	b.setX(20 + 35*i);
        	b.setY(100);
        	level3.add(b);
        }
        for (int i = 0; i < 13; i++) {
        	StrongBrick b = new StrongBrick(20 + 35*i, 50);
        	b.setX(20 + 35*i);
        	b.setY(50);
        	level3.add(b);
        }

        world.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	if(event.getX() > paddle.getX()) {
            		paddle.setDirection(1);
            	}
            	if(event.getX() < paddle.getX()) {
            		paddle.setDirection(-1);
            	}
                paddle.setX(event.getX());
            }
        });
        lev.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	if(event.getX() > paddle2.getX()) {
            		paddle2.setDirection(1);
            	}
            	if(event.getX() < paddle2.getX()) {
            		paddle2.setDirection(-1);
            	}
                paddle2.setX(event.getX());
            }
        });
        level3.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	if(event.getX() > paddle3.getX()) {
            		paddle3.setDirection(1);
            	}
            	if(event.getX() < paddle3.getX()) {
            		paddle3.setDirection(-1);
            	}
                paddle3.setX(event.getX());
            }
        });
        world.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                world.addKey(event.getCode());
            }
        });
        world.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                world.removeKey(event.getCode());
            }
        });
        lev.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                lev.addKey(event.getCode());
            }
        });
        lev.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                lev.removeKey(event.getCode());
            }
        });
        level3.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                level3.addKey(event.getCode());
            }
        });
        level3.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                level3.removeKey(event.getCode());
            }
        });
        if (world.lvlChange()) {
        	stage.setScene(sc);
        }
        
        //world.start();
        //stage.show();
        //world.requestFocus();
        worldBrick = new AnimationTimer() {

			@Override
			public void handle(long now) {
				if (world.lvlChange()) {
					world.stop();
					Score s = world.getScore();
					int curScore = s.getScore();
					stage.setScene(Level2);
					HBox anotherOne = new HBox();
					anotherOne.setSpacing(400);
					anotherOne.getChildren().addAll(back, save);
					rootNum2.setTop(anotherOne);
					//rootNum2.setTop(back);
					lev.getScore().setScore(curScore);
					lev.start();
					lev.requestFocus();
					worldBrick.stop();
				}
			}
        	
        };
        worldBrick.start();
        
        
        changeToFinal = new AnimationTimer() {

			@Override
			public void handle(long now) {
				if (lev.lvlChange()) {
					lev.stop();
					stage.setScene(Level3);
					Score sco = lev.getScore();
					int score = sco.getScore();
					HBox anotherOne = new HBox();
					anotherOne.setSpacing(400);
					anotherOne.getChildren().addAll(back, save);
					root3.setTop(anotherOne);
					//rootNum2.setTop(back);
					level3.getScore().setScore(score);
					level3.start();
					level3.requestFocus();
					changeToFinal.stop();
				}
			}
        	
        };
        changeToFinal.start();
        
        
        finalRoot = new BorderPane();
        finalRoot.setMinHeight(DEFAULT_HEIGHT);
        finalRoot.setMinWidth(DEFAULT_WIDTH);
        Text cong = new Text("CONGRATULATIONS!\nYOU'VE WON!");
        cong.setFill(Color.WHITE);
    	cong.setFont(Font.font("Comic Sans MS", FontPosture.ITALIC, 33));
        finalRoot.setCenter(cong);
        finalRoot.setBackground(new Background(myBI));
        Scene aight = new Scene(finalRoot);
        Finale = new AnimationTimer() {

			@Override
			public void handle(long now) {
				if (level3.lvlChange()) {
					level3.stop();
					Media finalMus;
			    	MediaPlayer congrat;
					try {
						finalMus = new Media(getClass().getClassLoader().getResource("resources/Victory!.mp3").toURI().toString());
						congrat =  new MediaPlayer(finalMus);
						congrat.setCycleCount(1);
						congrat.play();
					} catch (URISyntaxException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					/*new java.util.Timer().schedule(
		            		new java.util.TimerTask() {
								@Override
								public void run() {
									stage.setScene(aight);
									Finale.stop();
								}
		            		},
		            		2000
		            );*/
					//stage.setScene(sc);
					finalCelebration = new AnimationTimer() {
						private long prev = System.currentTimeMillis();
						@Override
						public void handle(long now) {
							long rn = now - prev;
							if (rn >= (3*1e9)) {
								prev = now;
								stage.setScene(aight);
								Finale.stop();
								finalCelebration.stop();
							}
						}
					};
					finalCelebration.start();
					//stage.setScene(aight);
					//Finale.stop();
				}
			}
        	
        };
        Finale.start();
	}
    
}
