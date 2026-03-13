CS141 Final Project
Nicholas Novak

This JavaFX program generates and solves an electrical circuit according to a randomized template while hiding the answers from user. The user can utilize their knowledge of circuit behavior to determine the unknown values, and check to see if their answers are correct. 

The elements of this circuit include either a voltage source or a current source, and parallel paths of resistors. A couple switches in the circuit allow for some randomization, so that each circuit will be unique.

The UI shows a depiction of the circuit with labels on each component that update with new values for each newly generated circuit, and switches which open or close which alter the path. The user will fill out a table of values for the voltage and current across each resistor and then press a 'Check Answers' button that outlines incorrect answers in red and correct answers in green. There is also another button to generate a new circuit, which will randomize the switches and component values.

An answer key has been added; by pressing 'Show Answer Key' the system calculated values will be displayed in an answer key below the circuit.

The flow is as follows:
(1) User takes note of the possible current path and element values, and proceeds to calculate the voltage drop and current across each resistor
(2) User enters their calculated values
(3) User presses 'Check Answers' to see which values are correct (highlighted in green) and which are incorrect (highlighted in red)
If necessary, the user can press the 'Show Answer Key' button to see the expected result
(4) To test another configuration, the user can press 'New Circuit' and proceed back to step (1)

github link:
https://github.com/nnovak141/cs141_final_project
