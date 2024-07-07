import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Scanner;
/**
 * @author Nikola Nikolov
 * @version 5 May 2021
 */
public class MatchTypeQuestion  extends FillTypeQuestion{

    private ArrayList<String> secondColon;
    private ArrayList<String> rightOrder;

    MatchTypeQuestion(){
        secondColon = new ArrayList<String>();
        rightOrder = new ArrayList<String>();
    }

    MatchTypeQuestion(ArrayList<String> secondColon, ArrayList<String> rightOrder){

        this.secondColon = secondColon;
        this.rightOrder = rightOrder;
    }

    /**
     * This method create a new Question
     */
    public void createQuestion(){
        Scanner scan = new Scanner(System.in);
        String response;
        Integer num = null;
        System.out.println("How many lines will have per colon");
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

        for(int i=0; i<num; i++){
            System.out.println("Input the first part of line "+ (i+1)+": ");
            response = scan.nextLine();
            answers.add(response);
            System.out.println("Input the second part part of line "+ (i+1) +": ");
            response = scan.nextLine();
            secondColon.add(response);
            rightOrder.add(response);
        }
        Collections.shuffle(secondColon);
    }

    /**
     * This method prints the question
     */
    public void printQues(){
        System.out.println("Connect the correct answer. Type just the letters without space in way to match thr first part!");
        StringBuilder strBil = new StringBuilder();
        strBil.append("\n");
        for(int i=0; i<answers.size(); i++) {
            strBil.append(i);
            strBil.append(". ");
            strBil.append(answers.get(i));
            strBil.append("     ");
            strBil.append((char) (65+i));
            strBil.append(". ");
            strBil.append(secondColon.get(i));
            strBil.append(" \n");
        }
        System.out.println(strBil.toString());
    }

    @Override
    public String toString() {
        StringBuilder strBil = new StringBuilder();
        for(int i=0; i<answers.size(); i++) {
            strBil.append("     ");
            strBil.append(i+1);
            strBil.append(". ");
            strBil.append(answers.get(i));
            strBil.append("     ");
            strBil.append((char) (65+i));
            strBil.append(". ");
            strBil.append(secondColon.get(i));
            strBil.append(" \n");
        }
        return strBil.toString();
    }

    /**
     * This method returns true if there is a mach with the correct answer(s)
     *
     * @param ans answer to check
     * @return
     */
    public boolean isTheRightAnswer(String ans){
        if(ans.length() == rightOrder.size()){
            for(int i=0; i<ans.length(); i++){
                char letter = ans.charAt(i);
                int num = (int)(letter-65);
                if( !(rightOrder.get(i).equals(secondColon.get(num))) ){
                    return false;
                }
            }
        }else{  return false; }

        return true;
    }

    /**
     * Write information about the Match-type of question in the file
     */
    public void save(PrintWriter pw){
        pw.println("MATCH-TYPE");
        pw.println(answers.size());
        for(String s:answers){
            pw.println(s);
        }
        for(String s:secondColon){
            pw.println(s);
        }
        for(String s:rightOrder){
            pw.println(s);
        }
    }

    /**
     *  Reads in information about the Match-type of question from the file
     * @param infile
     */
    public void load(Scanner infile){

        int num = infile.nextInt();
        answers = new ArrayList<String>();
        secondColon = new ArrayList<String>();
        rightOrder = new ArrayList<String>();

        for (int i = 0; i < num; i++) {
            answers.add(infile.next());
        }
        for (int i = 0; i < num; i++) {
            secondColon.add(infile.next());
        }
        for (int i = 0; i < num; i++) {
            rightOrder.add(infile.next());
        }
    }
}
