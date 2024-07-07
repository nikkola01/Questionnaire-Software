import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
/**
 * @author Nikola Nikolov
 * @version 5 May 2021
 */
public class ChooseTypeQuestion extends FillTypeQuestion{


    private String correctAnswer;

    public ChooseTypeQuestion(){
        this("none");
    }
    
    public ChooseTypeQuestion(String que){
        this.que = que;
        this.answers = new  ArrayList<String>();
        //this.correctAnswer = correctAnswer;
    }

    /**
     * This method sets the value for correctAnswer
     *
     * @param correctAnswer becomes the value
     */
    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    /**
     * This method create a new Question
     */
    public void createQuestion(){

        Scanner scan = new Scanner(System.in);
        System.out.println("Write the question:");
        que = scan.nextLine();
        System.out.println("How many answers do you want to put(including the correct one)");
        Integer num = null;
        do {
            System.out.println("No more than 10 answers are allowed");
            String response = scan.nextLine();
            try {
                // Parse the input if it is successful, it will set a non null value to i
                num = Integer.parseInt(response);
            } catch (NumberFormatException e) {
                // The input value was not an integer so i remains null
                System.out.println("That's not a number!");
            }
        } while (num == null && num <= 10);
        System.out.println("Input the correct answer:");
        correctAnswer = scan.next().toUpperCase();
        answers.add(correctAnswer);
        //newQuestion.addAnswer(correctAnswer);
        for(int i=0; i<num-1; i++){
            System.out.println("Input next answer:");
            //response=scan.nextLine();
            addAnswer(scan.next());
        }
        Collections.shuffle(answers);

    }

    /**
     * This method prints the question
     */
    public void printQues(){
        System.out.println("Choose the correct answer. Type just the letter before the answer!");
        System.out.println(que);
        //int letter = 65; // we start with letter A
        StringBuilder strBil = new StringBuilder();
        for(int i=0; i<answers.size(); i++) {
            strBil.append((char) (65+i));
            strBil.append(" - ");
            strBil.append(answers.get(i));
            strBil.append(" \n");
        }
        System.out.println(strBil.toString());
    }

    /**
     * This method returns true if there is a mach with the correct answer(s)
     *
     * @param ans answer to check
     * @return
     */
    public boolean isTheRightAnswer(String ans){
        char first = ans.charAt(0);                     //the ans is the index of the answer stored like a letter
        int i = (int)(first-65);
        if(answers.get(i).equals(correctAnswer)){ return true; }

        return false;
    }

    /**
     * Write information about the choose-type of question in the file
     */
    public void save(PrintWriter pw){
        pw.println("CHOOSE-TYPE");
        pw.println(que);
        pw.println(answers.size());
        pw.println(correctAnswer);
        for(String s:answers){
            pw.println(s);
        }
    }

    /**
     *  Reads in information about the choose-type of question from the file
     * @param infile
     */
    public void load(Scanner infile){
        que = infile.next();
        int num = infile.nextInt();
        correctAnswer = infile.next().toUpperCase();
        answers = new ArrayList<String>();

        for (int oCount = 0; oCount < num; oCount++) {
            answers.add(infile.next());
        }
    }

}
