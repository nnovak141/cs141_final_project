//
// CS&141 - Final Project
// Circuit Solver
// A program meant to test solving circuits for voltage
// and current across circuit elements
// Nicholas Novak
//

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.io.*;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CircuitSolver extends Application {
	
	private Display display;
	private Calculate calculate;
    
    @Override
    public void start(Stage stage) {
    	
    	// create the class responsible for building and displaying the UI
    	this.display = new Display(stage);
    	// create the class that generates and solves the circuit
    	this.calculate = new Calculate();
    	
    	newCircuit();
    	
    	// this is a workaround implemented last minute when splitting out
    	// the display class. These buttons need to call methods currently 
    	// being controlled by the CircuitSolver class, so they are created
    	// here and passed in to the display class to display
        HBox buttonBox = new HBox(10);
        Button newBtn = new Button("New Circuit");
        newBtn.setOnAction(e->newCircuit());
        Button checkAnswerBtn = new Button("Check Answers");
        checkAnswerBtn.setOnAction(e->checkAnswers());
        Button showAnswerBtn = new Button("Show Answer Key");
        showAnswerBtn.setOnAction(e->showAnswers());
        buttonBox.getChildren().addAll(newBtn, checkAnswerBtn, showAnswerBtn);
        
        // call on display to populate the GUI
        // this can fail if the required circuit images are not found
        try {
        	this.display.populateUI(buttonBox);
        }
        catch(FileNotFoundException ex){
        	System.out.println("Failed to find required file : " + ex);
        }
	}
    
    public static void main(String[] args) {
        launch();
    }
	
    // generates a new circuit and calls on display to update the GUI
	private void newCircuit() {
		// get new values for the circuit components
		generateCircuit();
		this.display.resetAnswer();
		this.display.setUIData(this.calculate.getCircuitData());
	}
	
	// calls on calculate to build and solve the a new circuit
	private void generateCircuit() {
		this.calculate.buildCircuit();
		this.calculate.solveCircuit();
	}
	
	// calls on display to check if the answers are correct and updates
	// the GUI. Ideally this would be split with the calculate class
	private void checkAnswers() {
		this.display.checkAnswers(calculate.getCircuitData());
	}
	
	// calls on display to show the answer key
	private void showAnswers() {
		this.display.showAnswers(calculate.getCircuitData());
	}
}

// A class for displaying on the GUI
// this class takes in and keeps the stage so that it can build the GUI
class Display {

	private Stage stage; 
	private TextField res1VAnswer = new TextField();
	private TextField res2VAnswer = new TextField();
	private TextField res3VAnswer = new TextField();
	private TextField res4VAnswer = new TextField();
	private TextField res5VAnswer = new TextField();
	private TextField res1IAnswer = new TextField();
	private TextField res2IAnswer = new TextField();
	private TextField res3IAnswer = new TextField();
	private TextField res4IAnswer = new TextField();
	private TextField res5IAnswer = new TextField();
	private Label v1Lbl = new Label();
	private Label i1Lbl = new Label();
	private Label r1Lbl = new Label();
	private Label r2Lbl = new Label();
	private Label r3Lbl = new Label();
	private Label r4Lbl = new Label();
	private Label r5Lbl = new Label();
	private ImageView switchAOpenImg = new ImageView();
	private ImageView switchAClosedImg = new ImageView();
	private ImageView switchBClosedImg = new ImageView();
	private TextArea answerKeyTxtArea = new TextArea();
	
	public Display(Stage stage) {
		this.stage = stage;
	}
	
	// builds up the intial display 
	public void populateUI(HBox buttonBox) throws FileNotFoundException {
		int uiWidth = 1280;
		int uiHeight = 600;
		
    	// build up the UI with controls
        Label titleTextLbl = new Label("CS&141 Final Project - Circuit Solver");
        Label instructionLbl = new Label("Determine the voltage and current across each resistor; " + 
        		"answer must be within 2% to be considered correct");
        
        VBox titleBox = new VBox(10);
        titleBox.getChildren().addAll(titleTextLbl, instructionLbl);
        
        // this is the displayed circuit images and switches
        Image circuitTemplateImage = new Image(getClass().getResource("/circuit.png").toExternalForm());
        Image switchAOpenImage = new Image(getClass().getResource("/switchAopen.png").toExternalForm());
        Image switchAClosedImage = new Image(getClass().getResource("/switchAclosed.png").toExternalForm());
    	Image switchBClosedImage = new Image(getClass().getResource("/switchBclosed.png").toExternalForm());
        ImageView circuitView = new ImageView(circuitTemplateImage);
        switchAOpenImg.setImage(switchAOpenImage);
        switchAClosedImg.setImage(switchAClosedImage);
        switchBClosedImg.setImage(switchBClosedImage);
        
        // this gridpane is the 'answer box', where the user
        // types in their calculated values
		GridPane answerGrid = new GridPane();
        answerGrid.setVgap(10);
        answerGrid.setHgap(10);
        answerGrid.add(new Label("Voltage"), 1, 0);
        answerGrid.add(new Label("Current"), 3, 0);
        
        answerGrid.add(new Label("R1"), 0, 1);
        answerGrid.add(res1VAnswer, 1, 1);
        answerGrid.add(new Label("V"), 2, 1);
        answerGrid.add(res1IAnswer, 3, 1);
        answerGrid.add(new Label("A"), 4, 1);
        
        answerGrid.add(new Label("R2"), 0, 2);
        answerGrid.add(res2VAnswer, 1, 2);
        answerGrid.add(new Label("V"), 2, 2);
        answerGrid.add(res2IAnswer, 3, 2);
        answerGrid.add(new Label("A"), 4, 2);
        
        answerGrid.add(new Label("R3"), 0, 3);
        answerGrid.add(res3VAnswer, 1, 3);
        answerGrid.add(new Label("V"), 2, 3);
        answerGrid.add(res3IAnswer, 3, 3);
        answerGrid.add(new Label("A"), 4, 3);
        
        answerGrid.add(new Label("R4"), 0, 4);
        answerGrid.add(res4VAnswer, 1, 4);
        answerGrid.add(new Label("V"), 2, 4);
        answerGrid.add(res4IAnswer, 3, 4);
        answerGrid.add(new Label("A"), 4, 4);
        
        answerGrid.add(new Label("R5"), 0, 5);
        answerGrid.add(res5VAnswer, 1, 5);
        answerGrid.add(new Label("V"), 2, 5);
        answerGrid.add(res5IAnswer, 3, 5);
        answerGrid.add(new Label("A"), 4, 5);

        // these are the labels next to the circuit elements which indicate
        // each element's value (e.g. resistance value), positioned
        // carefully to show up next to each element in a readable position
        Pane circuitPane = new Pane();
        v1Lbl.setLayoutX(175);
        v1Lbl.setLayoutY(178);
        i1Lbl.setLayoutX(55);
        i1Lbl.setLayoutY(178);
        r1Lbl.setLayoutX(305);
        r1Lbl.setLayoutY(70);
        r2Lbl.setLayoutX(420);
        r2Lbl.setLayoutY(189);
        r3Lbl.setLayoutX(305);
        r3Lbl.setLayoutY(295);
        r4Lbl.setLayoutX(640);
        r4Lbl.setLayoutY(141);
        r5Lbl.setLayoutX(640);
        r5Lbl.setLayoutY(260);
        switchAOpenImg.setLayoutX(108);
        switchAOpenImg.setLayoutY(36);
        switchAClosedImg.setLayoutX(106);
        switchAClosedImg.setLayoutY(37);
        switchBClosedImg.setLayoutX(555);
        switchBClosedImg.setLayoutY(42);
        HBox qna = new HBox(10);
        qna.getChildren().addAll(circuitView, answerGrid);
        circuitPane.getChildren().addAll(qna, v1Lbl, i1Lbl, 
        		r1Lbl, r2Lbl,r3Lbl,r4Lbl,r5Lbl, switchAOpenImg, switchAClosedImg, switchBClosedImg);
        
        answerKeyTxtArea.setMaxSize(1000, 100);
        answerKeyTxtArea.setVisible(false);
        
        // this is the top left of the GUI, showing the name and intro
        VBox headerBox = new VBox(10);
        headerBox.getChildren().addAll(titleBox, buttonBox, circuitPane, answerKeyTxtArea);
        headerBox.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
        
        StackPane stackPane = new StackPane();
        stackPane.setStyle("-fx-padding:20");
        stackPane.getChildren().add(headerBox);
        Scene scene = new Scene(stackPane, uiWidth, uiHeight);
        stage.setScene(scene);
        stage.show();
	}
	
	// updates the element value labels to show the current values
	// also sets the switch open or closed, depending on the boolean set
	public void setUIData(CircuitData circuitData) {
		v1Lbl.setText(circuitData.volt1.getVoltage() + "V");
		i1Lbl.setText(String.format("%.3fA", circuitData.curr1.getCurrent()));
		r1Lbl.setText(circuitData.res1.resistance + "Ω");
		r2Lbl.setText(circuitData.res2.resistance + "Ω");
		r3Lbl.setText(circuitData.res3.resistance + "Ω");
		r4Lbl.setText(circuitData.res4.resistance + "Ω");
		r5Lbl.setText(circuitData.res5.resistance + "Ω");
		this.switchAOpenImg.setVisible(circuitData.switchAOpen);
		this.switchAClosedImg.setVisible(!circuitData.switchAOpen);
		this.switchBClosedImg.setVisible(!circuitData.switchBOpen);
	}
	
	// gets the answer box ready for another attempt at answering
	// called when generating a new circuit
	public void resetAnswer() {
		res1VAnswer.setStyle("-fx-border-color: gray;");
		res1VAnswer.setText(null);
		res2VAnswer.setStyle("-fx-border-color: gray;");
		res2VAnswer.setText(null);
		res3VAnswer.setStyle("-fx-border-color: gray;");
		res3VAnswer.setText(null);
		res4VAnswer.setStyle("-fx-border-color: gray;");
		res4VAnswer.setText(null);
		res5VAnswer.setStyle("-fx-border-color: gray;");
		res5VAnswer.setText(null);
		res1IAnswer.setStyle("-fx-border-color: gray;");
		res1IAnswer.setText(null);
		res2IAnswer.setStyle("-fx-border-color: gray;");
		res2IAnswer.setText(null);
		res3IAnswer.setStyle("-fx-border-color: gray;");
		res3IAnswer.setText(null);
		res4IAnswer.setStyle("-fx-border-color: gray;");
		res4IAnswer.setText(null);
		res5IAnswer.setStyle("-fx-border-color: gray;");
		res5IAnswer.setText(null);
		answerKeyTxtArea.setText(null);
		answerKeyTxtArea.setVisible(false);
	}
	
	// this is a last-minute addition to display the correct
	// solution in an answer key
	public void showAnswers(CircuitData circuitData) {
    	String answerKeyText = "";

    	answerKeyText += "r1: " + circuitData.res1.getVoltage() + "V; " + circuitData.res1.getCurrent() + "A\n";
    	answerKeyText += "r2: " + circuitData.res2.getVoltage() + "V; " + circuitData.res2.getCurrent() + "A\n";
    	answerKeyText += "r3: " + circuitData.res3.getVoltage() + "V; " + circuitData.res3.getCurrent() + "A\n";
    	answerKeyText += "r4: " + circuitData.res4.getVoltage() + "V; " + circuitData.res4.getCurrent() + "A\n";
    	answerKeyText += "r5: " + circuitData.res5.getVoltage() + "V; " + circuitData.res5.getCurrent() + "A\n";
    	
    	answerKeyTxtArea.setVisible(true);
    	answerKeyTxtArea.setText(answerKeyText);
	}
	
	// this takes in the values entered by the user on the GUI
	// and compares them with the calculated values.
	// if the answer is within some tolerance, it is accepted
	// and the box turns green, otherwise it turns red
	public void checkAnswers(CircuitData circuitData) {
		double r1Voltage = 0.0;
		double r2Voltage = 0.0;
		double r3Voltage = 0.0;
		double r4Voltage = 0.0;
		double r5Voltage = 0.0;
		double r1Current = 0.0;
		double r2Current = 0.0;
		double r3Current = 0.0;
		double r4Current = 0.0;
		double r5Current = 0.0;
		
		// the tolerance is set to 2%, so the user must match to within that precision
		double tolerance = 0.02;
		checkAnswerBox(res1VAnswer, tolerance, circuitData.res1.getVoltage());
		checkAnswerBox(res2VAnswer, tolerance, circuitData.res2.getVoltage());
		checkAnswerBox(res3VAnswer, tolerance, circuitData.res3.getVoltage());
		checkAnswerBox(res4VAnswer, tolerance, circuitData.res4.getVoltage());
		checkAnswerBox(res5VAnswer, tolerance, circuitData.res5.getVoltage());
		checkAnswerBox(res1IAnswer, tolerance, circuitData.res1.getCurrent());
		checkAnswerBox(res2IAnswer, tolerance, circuitData.res2.getCurrent());
		checkAnswerBox(res3IAnswer, tolerance, circuitData.res3.getCurrent());
		checkAnswerBox(res4IAnswer, tolerance, circuitData.res4.getCurrent());
		checkAnswerBox(res5IAnswer, tolerance, circuitData.res5.getCurrent());
	}
	
	// added as a helper method for the repetitive answer checking routine
	private void checkAnswerBox(TextField answerBox, double tolerance, double answer) {
		String answerText = answerBox.getText();
		if(answerText != null && answerText.trim() != "") {
			try {
				double value = Double.parseDouble(answerBox.getText().trim());
				// get the difference between the entered value and the solved value,
				// then divide by the solved value. the tolerance will be the 
				// relative error, and the entered value is considered correct if
				// within that tolerance of relative error
				if((value == 0 && answer == 0) || Math.abs(value - answer)/answer < tolerance) {
					answerBox.setStyle("-fx-border-color: green;-fx-border-width: 2px");
				} else {
					answerBox.setStyle("-fx-border-color: red;-fx-border-width: 2px");
				}
			}
			catch(Exception ex){
				System.out.println("Failed to parse entry : " + ex);
			}
		}
	}
}

// the class responsible for managing the circuit data
// this includes calculating new circuit element values
// and solving the circuit to determine the voltage
// and current across each element
class Calculate {

	private CircuitData circuitData;
	
	public Calculate() {
		this.circuitData = new CircuitData();
	}
	
	// accessor for other classes that need to pull the current circuit data
	public CircuitData getCircuitData() {
		return this.circuitData;
	}
	
	// generates a new circuit
	public void buildCircuit() {
		// we're using a set template for simplicity, so the elements are known
		Random random = new Random();
		
		// start with the voltage source and make it between 4 and 12 volts
		int v1Value = random.nextInt(4,13);
		this.circuitData.volt1 = new VoltageSource(0, v1Value);
		
		// there are three resistors, set each of them
		// make one small, another medium, and the last one a wide range
		int r1Value = random.nextInt(1,10) * 100;
		this.circuitData.res1 = new Resistor(1, r1Value);
		
		int r2Value = random.nextInt(10,15) * 100;
		this.circuitData.res2 = new Resistor(2, r2Value);
		
		int r3Value = random.nextInt(1,20) * 100;
		this.circuitData.res3 = new Resistor(3, r3Value);
		
		int r4Value = random.nextInt(1,20) * 100;
		this.circuitData.res4 = new Resistor(4, r4Value);
		
		int r5Value = random.nextInt(1,20) * 100;
		this.circuitData.res5 = new Resistor(5, r5Value);
		
		// try to get the current in the mA range by generating and int
		// and multiplying by 1e-3 to produce rounded numbers
		int i1 = random.nextInt(1,20);
		this.circuitData.curr1 = new CurrentSource(0, 0, i1*1e-3);
		this.circuitData.switchAOpen = random.nextInt(0,2) == 1;
		this.circuitData.switchBOpen = random.nextInt(0,2) == 1;
	}
	
	// determines the voltage and current across the resistors depending on
	// the circuit elements
	public void solveCircuit() {
		
		// this is the case where we have voltage source and no parallel path
		if(!circuitData.switchAOpen && circuitData.switchBOpen) {
			double totalResistance = circuitData.res1.resistance + 
					circuitData.res2.resistance + circuitData.res3.resistance;
			double current = circuitData.volt1.getVoltage() / totalResistance;
			circuitData.res1.setCurrent(current);
			circuitData.res1.setVoltage((circuitData.volt1.getVoltage() * (circuitData.res1.resistance/totalResistance)));
			circuitData.res2.setCurrent(current);
			circuitData.res2.setVoltage(circuitData.volt1.getVoltage() * (circuitData.res2.resistance/totalResistance));
			circuitData.res3.setCurrent(current);
			circuitData.res3.setVoltage(circuitData.volt1.getVoltage() * (circuitData.res3.resistance/totalResistance));
			circuitData.res4.setCurrent(0.0);
			circuitData.res4.setVoltage(0.0);
			circuitData.res5.setCurrent(0.0);
			circuitData.res5.setVoltage(0.0);
		}
		// this is the case where we have voltage source and the parallel path is included
		else if(!circuitData.switchAOpen && !circuitData.switchBOpen) {
			
			// equivalent resistance is r2 with the series of r4 and r5
			double r4r5Series = circuitData.res4.resistance + circuitData.res5.resistance;
			double r_eq = (circuitData.res2.resistance * r4r5Series)/(circuitData.res2.resistance + r4r5Series);
			double totalResistance = circuitData.res1.resistance + r_eq + circuitData.res3.resistance;
			double totalCurrent = circuitData.volt1.getVoltage() / totalResistance;
			
			// current over r1 and r3 will be the total current
			circuitData.res1.setCurrent(totalCurrent);
			circuitData.res3.setCurrent(totalCurrent);
			
			// voltage over the first and third resistor
			double r1Voltage = circuitData.res1.resistance * totalCurrent;
			double r3Voltage = circuitData.res3.resistance * totalCurrent;
			circuitData.res1.setVoltage(r1Voltage);
			circuitData.res3.setVoltage(r3Voltage);
			
			// voltage over the parallel paths
			double parallelVoltage = circuitData.volt1.getVoltage() - r1Voltage - r3Voltage;
			
			// current is split on r2 and r4/r5
			circuitData.res2.setCurrent(parallelVoltage / circuitData.res2.resistance);
			circuitData.res2.setVoltage(parallelVoltage);
			
			double r4r5Current = parallelVoltage/r4r5Series;
			circuitData.res4.setCurrent(r4r5Current);
			circuitData.res4.setVoltage(r4r5Current * circuitData.res4.resistance);
			circuitData.res5.setCurrent(r4r5Current);
			circuitData.res5.setVoltage(r4r5Current * circuitData.res5.resistance);
		}
		// this is the case where we have current source and no parallel path
		else if(circuitData.switchAOpen && circuitData.switchBOpen) {
			// this is the easiest type, the current is the same over the loop
			// and the voltage for each resistor is found as V=IR
			double current = circuitData.curr1.getCurrent();
			circuitData.res1.setCurrent(current);
			circuitData.res1.setVoltage(current * circuitData.res1.resistance);
			circuitData.res2.setCurrent(current);
			circuitData.res2.setVoltage(current * circuitData.res2.resistance);
			circuitData.res3.setCurrent(current);
			circuitData.res3.setVoltage(current * circuitData.res3.resistance);
			circuitData.res4.setCurrent(0.0);
			circuitData.res4.setVoltage(0.0);
			circuitData.res5.setCurrent(0.0);
			circuitData.res5.setVoltage(0.0);
		}
		// this is the case where we have voltage source and the parallel path is included
		else if(circuitData.switchAOpen && !circuitData.switchBOpen) {
			// equivalent resistance is r2 with the series of r4 and r5
			double r4r5Series = circuitData.res4.resistance + circuitData.res5.resistance;
			double r_eq = (circuitData.res2.resistance * r4r5Series)/(circuitData.res2.resistance + r4r5Series);
			double totalResistance = circuitData.res1.resistance + r_eq + circuitData.res3.resistance;
			double totalCurrent = circuitData.curr1.getCurrent();
			double totalVoltage = totalCurrent * totalResistance;
			
			// current over r1 and r3 will be the same as the current source
			circuitData.res1.setCurrent(totalCurrent);
			circuitData.res3.setCurrent(totalCurrent);
			
			// voltage over the first and third resistor
			double r1Voltage = circuitData.res1.resistance * totalCurrent;
			double r3Voltage = circuitData.res3.resistance * totalCurrent;
			circuitData.res1.setVoltage(r1Voltage);
			circuitData.res3.setVoltage(r3Voltage);
			
			// voltage drop over the parallel paths
			double parallelVoltage = totalVoltage - r1Voltage - r3Voltage;
			
			// current is split on r2 and r4/r5
			double r2Current = parallelVoltage / circuitData.res2.resistance;
			circuitData.res2.setCurrent(r2Current);
			circuitData.res2.setVoltage(parallelVoltage);
			
			double r4r5Current = parallelVoltage/r4r5Series;
			circuitData.res4.setCurrent(r4r5Current);
			circuitData.res4.setVoltage(r4r5Current * circuitData.res4.resistance);
			circuitData.res5.setCurrent(r4r5Current);
			circuitData.res5.setVoltage(r4r5Current * circuitData.res5.resistance);
		}
	}
}

// class used to store and pass along the circuit data
class CircuitData {
	public Boolean switchAOpen = false;
	public Boolean switchBOpen = true;
	public VoltageSource volt1;
	public CurrentSource curr1;
	public Resistor res1;
	public Resistor res2;
	public Resistor res3;
	public Resistor res4;
	public Resistor res5;
}

// classes for the components
abstract class Component {
	private int id;
	private double voltage;
	private double current;
	
	public Component(int id, double voltage) {
		this.id = id;
		this.voltage = voltage;
	}
	
	public int getId() {
		return this.id;
	}
	
	public double getVoltage() {
		return this.voltage;
	}
	
	public double getCurrent() {
		return this.current;
	}
	
	public void setVoltage(double voltage) {
		this.voltage = voltage;
	}
	
	public void setCurrent(double current) {
		this.current = current;
	}
}

class Resistor extends Component {
	public double resistance;
	
	public Resistor (int id, double resistance) {
		super(id, 0.0);
		this.resistance = resistance;
	}
}

class VoltageSource extends Component {
	
	public VoltageSource (int id, double voltageDrop) {
		super(id, voltageDrop);
	}
}

class CurrentSource extends Component {
	
	public CurrentSource (int id, double voltageDrop, double current) {
		super(id, voltageDrop);
		super.setCurrent(current);
	}
}
