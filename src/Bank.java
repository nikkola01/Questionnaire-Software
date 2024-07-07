import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.*;
/**
 * @author Nikola Nikolov
 * @version 5 May 2021
 */
public class Bank {
    private ArrayList<FillTypeQuestion>[] questions;
    private ArrayList<Integer> shuffledPosition;
    private ArrayList<String> givenAnswers;
    private Map<String,Integer> studentResults;
    private String id;
    private static final int maxAnswersToGive = 10;

    Bank() {
        this("");
    }

    Bank(String id) {
        this.id = id;
        questions = new ArrayList[2];
        questions[0] = new ArrayList<FillTypeQuestion>();
        questions[1] = new ArrayList<FillTypeQuestion>();
        givenAnswers = new ArrayList<String>();
        shuffledPosition = new ArrayList<Integer>();
        studentResults = new HashMap<>();
    }

    /**
     * Get identifier
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * This method sets the value for id
     *
     * @param id becomes the value
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * This method returns all the Questions
     * @param lang  the choosed language
     * @return ret
     */
    public String printAllQuestions(int lang) {
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < questions[0].size(); i++) {
            ret.append(i + 1);
            ret.append(". ");
            ret.append(questions[lang].get(i).toString());
        }
        return ret.toString();
    }

    /**
     * This method add a question (two languages) in the question ArrayList
     *
     * @param firstLang becomes the value of the fist language
     * @param secLang   becomes the value of the second language
     */
    public void setQuestions(FillTypeQuestion firstLang, FillTypeQuestion secLang) {
        questions[0].add(firstLang);
        questions[1].add(secLang);
    }

    /**
     * This method adds a new Question in the Quiz
     */
    public void addQuestion() {
        FillTypeQuestion newQuestion;
        FillTypeQuestion newQuestionSecLang;
        String response;
        System.out.println("What kind of question do you want to create");
        System.out.println("    F - Fill-type question");
        System.out.println("    C - Choose-type question");
        System.out.println("    M - Match-type question");
        loop:
        while (true) {
            Scanner scan = new Scanner(System.in);
            response = scan.nextLine().toUpperCase();
            switch (response) {                                               //checking what type of question and initialising it
                case "F":
                    newQuestion = new FillTypeQuestion();
                    newQuestionSecLang = new FillTypeQuestion();
                    break loop;
                case "C":
                    newQuestion = new ChooseTypeQuestion();
                    newQuestionSecLang = new ChooseTypeQuestion();
                    break loop;
                case "M":
                    newQuestion = new MatchTypeQuestion();
                    newQuestionSecLang = new MatchTypeQuestion();
                    break loop;
                default:
                    System.out.println("Try Again!");
            }
        }

        newQuestion.createQuestion();                                       // calls the right method to create the question
        System.out.println("Please type the SAME question in Bulgarian");
        newQuestionSecLang.createQuestion();

        questions[0].add(newQuestion);                                      // store the English version
        questions[1].add(newQuestionSecLang);                               // store the Bulgarian version
        //System.out.println(questions[0].size() - 1);
        shuffledPosition.add(questions[0].size() - 1);

    }

    /**
     * This methid prints the collected information about the results of the quiz and returns the average result for the Quiz
     * @return  average result
     */
    public int printCollectedResults(){
        int average = 0;
        for(Map.Entry m:studentResults.entrySet()){
            System.out.println("    " + m.getKey() + " - " + m.getValue());
            average += (int)(m.getValue());
        }
        return average/studentResults.size();
    }

    /**
     * Prints the menu that is user can use throughout the quiz
     */
    private void printQuizMenu() {
        System.out.println("Before you start the quiz you have to know that you can use this commands through the quiz:");
        System.out.println("    N - next question (automatically safes the answer)");
        System.out.println("    P - previous question (automatically safes the answer)");
        System.out.println("    S - Submit & Exit)");

    }

    /**
     * By this method the user can do a quiz
     * @param language
     * @param username of the user
     */
    public void doTheQuiz(int language, String username) {
        LocalDateTime now = LocalDateTime.now();
        givenAnswers = new ArrayList<String>();                                        // we store the answers that have been given
        for (int i = 0; i < maxAnswersToGive; i++) givenAnswers.add(" ");              // making sure that we have added enough elements
        int currentQues = 1;                                                            // and using " " just so we can check if the user have got and answer
        String response;
        Scanner scan = new Scanner(System.in);
        Collections.shuffle(shuffledPosition);                                         // use shuffeledPosition so we can print them in random order without moving themselves
        int maxQtoAnswer = setQNumToDoInQuiz();                                           // maxQtoAnswer is the nummber of q that user wants to answer from the Quiz
        printQuizMenu();
        long start = System.currentTimeMillis();                                       //saving the time so we know when quiz have been started
        do {
            System.out.println(currentQues + "/" + maxQtoAnswer);
            System.out.print(currentQues + ". ");
            questions[language].get(shuffledPosition.get(currentQues-1)).printQues();
            response = scan.nextLine().toUpperCase();
            switch (response) {
                case "N":
                    if (currentQues == maxQtoAnswer) {
                        System.out.println("That is the last question. You can submit your work by typing 'S'");
                        break;
                    }
                    currentQues++;
                    break;
                case "P":
                    if (currentQues-1 == 0) {
                        System.out.println("That is the first question. There is no previous.");
                        break;
                    }
                    currentQues--;
                    break;
                case "S":

                    break;
                default:
                    givenAnswers.set(shuffledPosition.get(currentQues-1), response);
                    if (currentQues == maxQtoAnswer) {
                        System.out.println("That is the last question. You can submit your work by typing 'S'");
                        break;
                    }
                    currentQues++;
            }
        } while (!(response.equals("S")));
        long end = System.currentTimeMillis();                                  // saving that time when the quiz have been ended
        float time = (end - start) / 1000;
        int hour = (int) (time/120); time = time -hour*120;
        int min = (int) (time/60); time = time-min*60;
        System.out.println("You do the quiz for " + hour + ":"+ min + ":"+ (int)time);
        int result = calculateQuizResults(maxQtoAnswer);
        System.out.println("Your Result is " + result + "% !");
        studentResults.put(username, result);                                      //saving the Quiz results
    }

    /**
     * This method asks the user how many questions he wants to answer
     * @return num the max number of questions to answer
     */
    public int setQNumToDoInQuiz(){
        String response;
        Scanner scan = new Scanner(System.in);
        Integer num = null;
        do {
            System.out.println("How many questions from the quiz you want to do");
            response = scan.nextLine();
            try {
                // Parse the input if it is successful, it will set a non null value to i
                num = Integer.parseInt(response);
            } catch (NumberFormatException e) {
                // The input value was not an integer so i remains null
                System.out.println("That's not a number!");
            }
        } while (num == null && num >0);
        if(num > questions[0].size()){
            return questions[0].size();
        }else{
            return num;
        }

    }

    /**
     * Get the num of questions
     * @return the num of questions
     */
    public int getQuestionsNum(){
        return questions[0].size();
    }

    /**
     * This method returns the result of the quiz in %
     *
     * @param maxQ  max questions to answer from the quiz
     * @return  result - the result of the quiz in %
     */
    private int calculateQuizResults(int maxQ) {
        float result = 0;
        int notAnswered = 0;
        for (int i = 0; i < maxAnswersToGive; i++) {
            if (givenAnswers.get(i) ==" "){
                notAnswered++;
            }
            else if(questions[0].get(i).isTheRightAnswer(givenAnswers.get(i))) {
                result++;
            }
        }
        result = result / maxQ * 100;
        System.out.println( (notAnswered-(maxAnswersToGive -maxQ)) + " not answered questions");
        return Math.round(result);

    }

    /**
     * This method remove a question
     * @param index of the question
     */
    public void deleteQuestion(int index) {
        questions[0].remove(index);
        questions[1].remove(index);
        for (int i = 0; i < shuffledPosition.size(); i++) {
            if (shuffledPosition.get(i) == index) {
                shuffledPosition.remove(i);
                break;
            }
        }
    }

    /**
     * Write information about the bank in the file
     */
    public void save(PrintWriter pw) {
        pw.println(id);
        pw.println(questions[0].size());
        for (int i = 0; i < questions[0].size(); i++) {

            questions[0].get(i).save(pw);
            questions[1].get(i).save(pw);
        }
        pw.println(studentResults.size());
        for(Map.Entry m:studentResults.entrySet()){
            pw.println(m.getKey());
            pw.println(m.getValue());
        }
    }

    /**
     * Reads in information about the Bank from the file
     * @param infile
     */
    public void load(Scanner infile) {
        id = infile.next();
        int num = infile.nextInt();
        shuffledPosition = new ArrayList<Integer>();
        questions = new ArrayList[2];
        givenAnswers = new ArrayList<String>();
        questions[0] = new ArrayList<FillTypeQuestion>();
        questions[1] = new ArrayList<FillTypeQuestion>();
        studentResults = new HashMap<>();

        for (int oCount = 0; oCount < num; oCount++) {
            FillTypeQuestion q = null;
            String s = infile.next();
            if (s.equals("FILL-TYPE")) {
                q = new FillTypeQuestion();
            } else if(s.equals("CHOOSE-TYPE")){
                q = new ChooseTypeQuestion();
            } else {
                q = new MatchTypeQuestion();
            }
            q.load(infile);
            questions[0].add(q);

            s = infile.next();
            if (s.equals("FILL-TYPE")) {                          //we are doing that second time because otherwise both languages will be the same
                q = new FillTypeQuestion();
            } else if(s.equals("CHOOSE-TYPE")){
                q = new ChooseTypeQuestion();
            } else {
                q = new MatchTypeQuestion();
            }
            q.load(infile);
            questions[1].add(q);

            shuffledPosition.add(questions[0].size() - 1);
        }
        num = infile.nextInt();
        for (int i=0; i<num; i++){
            studentResults.put(infile.next(), infile.nextInt());
        }
    }

}
