import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
/**
 * @author Nikola Nikolov
 * @version 5 May 2021
 */
public class FillTypeQuestion{

    String que;
    ArrayList<String> answers;

    public FillTypeQuestion(){
        this("none");
    }

    public FillTypeQuestion(String que){
        this.que = que;
        answers = new  ArrayList<String>();
    }

    /**
     * This method create a new Question
     */
    public void createQuestion(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Please type the sentence, at the place of the missing/s place one star '*'");
        String response;
        que=scan.nextLine();
        System.out.println("How many missing place are in the sentence");
        Integer num = null;
        do {
            response = scan.nextLine();
            try {
                // Parse the input if it is successful, it will set a non null value to num
                num = Integer.parseInt(response);
            } catch (NumberFormatException e) {
                // The input value was not an integer so num remains null
                System.out.println("That's not a number!");
            }
        } while (num == null);
        //num = scan.nextInt();
        System.out.println("Input the missing word one by one: (answers will be NOT caps sensitive)");
        for(int i=0; i<num; i++){
            answers.add(scan.next().toUpperCase());
        }
    }

    @Override
    public String toString() {
        return que +'\n'+ getAnswers();
    }

    /**
     * This method returns true if there is a mach with the correct answer(s)
     *
     * @param ans answer to check
     * @return
     */
    public boolean isTheRightAnswer(String ans){
        for(int i=0; i<answers.size(); i++){
            if(answers.get(i).equals(ans)){ return true; }
        }
        return false;
    }

    /**
     * This method sets the value for que
     *
     * @param ques becomes the value
     */
    public void setQue(String ques) {
        this.que = ques;
    }

    /**
     * This method is adding an answer
     * @param newAnswer
     */
    public void addAnswer(String newAnswer){
        answers.add(newAnswer);
    }

    /**
     * This method prints the question
     */
    public void printQues(){
        System.out.println("Complete the sentence.");
        System.out.println(que);
    }

    /**
     * Get all answers
     * @return all answers
     */
    public String getAnswers(){
        StringBuilder ret = new StringBuilder();
        for(int i=0; i< answers.size(); i++){
            ret.append("   ");
            ret.append((char)(65+i));
            ret.append(". ");
            ret.append(answers.get(i));
            ret.append(" \n");
        }
        return ret.toString();
    }

    /**
     * Write information about the Fill-type of question in the file
     */
    public void save(PrintWriter pw){
        pw.println("FILL-TYPE");
        pw.println(que);
        pw.println(answers.size());
        for(String s:answers){
            pw.println(s);
        }
    }

    /**
     *  Reads in information about the Fill-type of question from the file
     * @param infile
     */
    public void load(Scanner infile){
        que = infile.next();
        int num = infile.nextInt();
        answers = new ArrayList<String>();

        for (int oCount = 0; oCount < num; oCount++) {
            answers.add(infile.next());
        }
    }
}
