import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * @author Nikola Nikolov
 * @version 5 May 2021
 */
public class Application {

    private Person person;
    private ArrayList<Module> modules;
    private int language;

    Application() {
        modules = new ArrayList<Module>();
        language = 0;
        person = new Person();
    }

    /**
     * This method sets ask for the role and set it in the person variable
     */
    private void setPerson() {
        System.out.println("You have to log in.");
        System.out.println("Please enter:\n   S - student\n   T - teacher\n   E - else");
        Scanner scan = new Scanner(System.in);
        String response;
        loop:                                               // loop is type like this so it can be break from the switch
        while (true) {
            response = scan.nextLine().toUpperCase();       //toUpperCase is use to make tha input not caps sensitive
            switch (response) {
                case "S":
                    System.out.print("Username: ");
                    response = scan.nextLine();
                    person = new Person(Role.STUDENT, response);
                    break loop;
                case "T":
                    System.out.print("Username: ");
                    response = scan.nextLine();
                    person = new Person(Role.TEACHER, response);
                    break loop;
                case "E":
                    System.out.println("The program do not support that function for now!Try again!");
                    break;
                default:
                    System.out.println("Try again");
            }
        }
    }

    /**
     * printMenu() method prints the functions that can be used and the way to accses them
     */
    private void printMenu() {
        if (person.getRole() == Role.STUDENT) {
            System.out.println("1 -  Choose Language ");
            System.out.println("2 -  Take Quiz ");
            System.out.println("3 -  Print all Quizzes from a Module");
            System.out.println("q - Quit");
        } else if (person.getRole() == Role.TEACHER) {
            System.out.println("1 -  Choose Language ");
            System.out.println("2 -  Create new Module");
            System.out.println("3 -  Create new Quiz for a Module");
            System.out.println("4 -  Add new Question to a Quiz");
            System.out.println("5 -  Print all questions from a Quiz");
            System.out.println("6 -  Remove a Question from a Quiz");
            System.out.println("7 -  Remove a Quiz");
            System.out.println("8 -  Print all Quizzes from a Module");
            System.out.println("9 -  Print the collected results for a Quiz");
            System.out.println("10 -  Print the collected results for a Module");
            System.out.println("q - Quit");
        } else {
            System.out.println("You can do nothing! There is no permissions for this role.");
        }
    }

    /**
     * runMenu() method runs from the main and allows calling the methods you want
     */
    private void runMenu() {
        String response;
        if (person.getRole() == Role.STUDENT) {                                 //checks what is the role of the person that use the program
            do {                                                                //and gives an access only to the methods he can use
                printMenu();
                System.out.println("What would you like to do:");
                Scanner scan = new Scanner(System.in);
                response = scan.nextLine().toUpperCase();
                switch (response) {
                    case "1":
                        setLanguage();
                        break;
                    case "2":
                        takeQuiz();
                        break;
                    case "3":
                        printAllBanksOfModule();
                        break;
                    case "Q":
                        doSave();
                        break;
                    default:
                        System.out.println("Try again");
                }
            } while (!(response.equals("Q")));
        } else if (person.getRole() == Role.TEACHER) {
            do {
                printMenu();
                System.out.println("What would you like to do:");
                Scanner scan = new Scanner(System.in);
                response = scan.nextLine().toUpperCase();
                switch (response) {
                    case "1":
                        setLanguage();
                        break;
                    case "2":
                        addModule();
                        break;
                    case "3":
                        addQuiz();
                        break;
                    case "4":
                        addQuestionToQuiz();
                        break;
                    case "5":
                        printAllQuestionsFromQuiz();
                        break;
                    case "6":
                        removeQuestionFromQuiz();           //nin6 - bad input
                        break;
                    case "7":
                        removeQuiz();
                        break;
                    case "8":
                        printAllBanksOfModule();
                        break;
                    case "9":
                        printCollectedResultsForQuiz();
                        break;
                    case "10":
                        printCollectedResultsForModule();
                        break;
                    case "Q":
                        doSave();
                        break;
                    default:
                        System.out.println("Try again");
                }
            } while (!(response.equals("Q")));
        }
    }

    /**
     * This method prints the results of the students that have done the quiz
     */
    private void printCollectedResultsForQuiz() {
        int average = 0;
        String response;
        Scanner scan = new Scanner(System.in);
        int[] position;
        loop:
        while (true) {
            System.out.println("Please type the module and the quiz identifiers separated by colon / *****:***** /");
            response = scan.nextLine();
            position = getQuizPosition(response);
            if (position[1] == -1) {
                System.out.println("The identifiers are not correct! Please try again!");
                continue;
            }
            average = modules.get(position[0]).getBank(position[1]).printCollectedResults();  // printCollectedResults print and return the average result for the bank
            break;

        }
        System.out.println("Average sock: " + average);     //it's only one bank so we just print average
    }

    /**
     * This method prints the results of the students that have done the quiz for the hole module
     */
    private void printCollectedResultsForModule() {
        System.out.println("Type id of the Module:");
        String response;
        int average = 0;
        int counter = 0;
        Scanner scan = new Scanner(System.in);
        do {
            response = scan.nextLine();
        } while (isModuleIdFree(response));
        for (int i = 0; i < modules.size(); i++) {
            if (modules.get(i).getId().equals(response))
                for (int j = 0; j < modules.get(i).getBanksSize(); j++) {
                    average += modules.get(i).getBank(j).printCollectedResults();   // printCollectedResults print and return the average result for the bank
                    counter++;
                }
        }
        System.out.println("Average sock: " + average / counter);
    }

    /**
     * This method prints the id of every bank in a module
     */
    private void printAllBanksOfModule() {
        System.out.println("Type id of the Module:");
        String response;
        Scanner scan = new Scanner(System.in);
        do {
            response = scan.nextLine();
            if(isModuleIdFree(response)){
                System.out.println("That id do not match.Try again!");
            }else{
                break;
            }
        } while (true);
        for (int i = 0; i < modules.size(); i++) {
            if (modules.get(i).getId().equals(response))
                modules.get(i).listBankIDs();

        }
    }

    /**
     * This method add a question to a bank(Quiz)
     */
    private void addQuestionToQuiz() {
        String response;
        Scanner scan = new Scanner(System.in);
        System.out.println("To create a new question please type the module ");
        System.out.println("and the quiz identifiers separated by colon / *****:***** / ");
        int[] position;
        while (true) {
            response = scan.nextLine();
            position = getQuizPosition(response);
            if (position[0] == -1 || position[1] == -1) {
                System.out.println("The identifiers are not correct! Please try again!");
                continue;
            }
            modules.get(position[0]).getBank(position[1]).addQuestion();
            break;
        }
    }

    /**
     * This method remove a bank(Quiz) from a module
     */
    private void removeQuiz() {
        String response;
        Scanner scan = new Scanner(System.in);
        System.out.println("To delete Quiz please type the module and the quiz identifiers separated by colon / *****:***** /\nThe Quiz has to be have NO questions inside it ");
        int[] position;
        while (true) {
            response = scan.nextLine();
            position = getQuizPosition(response);
            if (position[0] == -1 || position[1] == -1) {
                System.out.println("The identifiers are not correct! Please try again!");
                continue;
            }
            if (modules.get(position[0]).getBank(position[1]).getQuestionsNum() == 0) {
                modules.get(position[0]).delBank(position[1]);
            } else {
                System.out.println("The Quiz has to be have NO questions inside it");
            }

            break;
        }
    }

    /**
     * This method starts the quiz(bank)
     */
    private void takeQuiz() {                                                // put this in different method
        String response;                                                                            // put this in different method
        Scanner scan = new Scanner(System.in);
        int[] position;
        loop:
        while (true) {
            System.out.println("Please type the module and the quiz identifiers separated by colon / *****:***** /");
            response = scan.nextLine();
            position = getQuizPosition(response);
            if (position[1] == -1 ) {
                System.out.println("The identifiers are not correct! Please try again!");
                continue;
            }
            modules.get(position[0]).getBank(position[1]).doTheQuiz(language, person.getUsername());
            break;

        }
    }

    /**
     * This method add new module
     */
    private void addModule() {
        String response;
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.println("What the module identifier will be:");
            response = scan.nextLine();
            if (isModuleIdFree(response)) {
                break;
            }
            System.out.println("The identifier is already used! Please choose another one:");
        }
        Module module = new Module(response);
        modules.add(module);
    }

    /**
     * This method add new bank to a module
     */
    private void addQuiz() {
        String response;
        Scanner scan = new Scanner(System.in);
        System.out.println("To witch module do you want to add quiz. Please type the identifier:");
        int currentIndex = -1;
        loop:
        while (true) {
            response = scan.nextLine();
            for (int i = 0; i < modules.size(); i++) {
                if (modules.get(i).getId().equals(response)) {
                    currentIndex = i;
                    break loop;
                }

            }
            System.out.println("There is no such identifier! Please try again:");
        }

        System.out.println("What the quiz identifier will be:");
        do {
            response = scan.nextLine();
            if (modules.get(currentIndex).isBankIdFree(response)) {
                break;
            }
            System.out.println("The identifier is already used! Please choose another one:");

        } while (true);

        Bank bank = new Bank(response);
        modules.get(currentIndex).addBank(bank);
    }

    /**
     * This method returns true if there isn't module with that id
     *
     * @param identifier of the module
     * @return
     */
    private boolean isModuleIdFree(String identifier) {
        for (int i = 0; i < modules.size(); i++) {
            if (modules.get(i).getId().equals(identifier)) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method remove a question from a bank(Quiz)
     */
    private void removeQuestionFromQuiz() {
        String response;
        Scanner scan = new Scanner(System.in);
        System.out.println("To remove a question please type the module and the quiz identifiers separated by colon / *****:***** / ");
        int[] position;
        int index = 0;
        while (true) {
            response = scan.nextLine();
            position = getQuizPosition(response);
            if (position[1] == -1) {                                                                        //if matching bank isn't found starts the while again
                System.out.println("The identifiers are not correct! Please try again!");
                continue;
            }
            System.out.println(modules.get(position[0]).getBank(position[1]).printAllQuestions(language));
            System.out.println("Please type the number of the question you want to remove");
            Integer num = null;
            int p = modules.get(position[0]).getBank(position[1]).getQuestionsNum();
            do {
                System.out.println("Type number between 0 and " + p);
                response = scan.nextLine();
                try {
                    // Parse the input if it is successful, it will set a non null value to num
                    num = Integer.parseInt(response);
                } catch (NumberFormatException e) {
                    // The input value was not an integer so num remains null
                    System.out.println("That's not a number!");
                }
                if(num != null){
                    if ((num <= p) &&(num>0)){
                        break;
                    }
                }

            } while ( true) ;
            modules.get(position[0]).getBank(position[1]).deleteQuestion(num - 1);
            break;
        }
    }

    /**
     * This method prints all the question from a bank(Quiz)
     */
    private void printAllQuestionsFromQuiz() {
        String response;
        Scanner scan = new Scanner(System.in);
        int[] position = {-1, -1};              //item 0 is the position of the module, 1 is the position of the bank
        loop:
        do {
            System.out.println("Please type the module and the quiz identifier separated by colon / *****:***** /");
            response = scan.next();
            position = getQuizPosition(response);
            if (position[1] == -1) {
                System.out.println("The identifiers are not correct! Please try again!");
                continue;
            }

            System.out.println(modules.get(position[0]).getBank(position[1]).printAllQuestions(language));
            break loop;

        } while (true);
    }

    /**
     *  This method gets identifiers and check if the are module and bank with this id.
     *  Then returns the position in array of int. Returns {-1, -1} if there is no match
     *
     * @param ids identifiers in one String
     * @return result - array of int - the position
     */
    private int[] getQuizPosition(String ids) {

        int[] result = {-1, -1};
        boolean stop = true;
        for(int i=0; i<ids.length(); i++){
            if(ids.charAt(i)==':'){
                stop = false;
            }
        }
        if(stop) return result;
        String[] idsSeparated = ids.split(":", 2);              //separate identifiers into two String and put them in array
        for (int i = 0; i < modules.size(); i++) {
            if (modules.get(i).getId().equals(idsSeparated[0])) {
                for (int j = 0; j < modules.get(i).getBanksSize(); j++) {
                    if (modules.get(i).getBank(j).getId().equals(idsSeparated[1])) {
                        modules.get(i).getBank(j).printAllQuestions(language);
                        result[0] = i;
                        result[1] = j;
                        return result;
                    }
                }
            }
        }
        return result;
    }

    /**
     * This method sets the value for language.
     *
     */
    private void setLanguage() {
        System.out.println("Please choose language/Molq izberete ezik:");
        System.out.println("    E - English");
        System.out.println("    B - Bulgarian(Latin)");
        String response;
        loop:
        do {                                          // loop is type like this so it can be break from the switch
            Scanner scan = new Scanner(System.in);
            response = scan.nextLine().toUpperCase();
            switch (response) {
                case "E":
                    language = 0;                   //The language is stored in int to be easier to store it.
                    break loop;
                case "B":
                    language = 1;
                    break loop;
                default:
                    System.out.println("Try Again!/Opitaite pak!");
            }
        } while (true);
    }

    /**
     * This method is trying to load the file with the data if existing
     */
    private void initialise() {
        System.out.println("Using file data.txt");

        try {
            load("data.txt");
        } catch (FileNotFoundException e) {
            System.err.println("The file: data.txt does not exist. Assuming first use and an empty file." +
                    " If this is not the first use then have you accidentally deleted the file?");
        } catch (IOException e) {
            System.err.println("An unexpected error occurred when trying to open the file data.txt");
            String message = e.getMessage();
            System.err.println(message);
        }
    }

    private void doSave() {
        try {
            save();
        } catch (IOException e) {
            System.err.println("Problem when trying to write to file: data.txt");
        }
    }

    /**
     * Reads in Application information from the file
     *
     * @param infileName The file to read from
     * @throws FileNotFoundException    if file doesn't exist
     * @throws IOException              if some other IO error occurs
     * @throws IllegalArgumentException if infileName is null or empty
     */
    public void load(String infileName) throws IOException, FileNotFoundException {
        try (FileReader fr = new FileReader(infileName);
             BufferedReader br = new BufferedReader(fr);
             Scanner infile = new Scanner(br)) {

            infile.useDelimiter("\r?\n|\r");


            int num = infile.nextInt();

            for (int oCount = 0; oCount < num; oCount++) {
                Module m = new Module();
                m.load(infile);
                modules.add(m);
            }

        }
    }

    /**
     * Saves the Application information
     *
     * @param "data.txt" The file to save to
     * @throws IOException If some IO error occurs
     */
    public void save() throws IOException {
        // Again using try-with-resource so that I don't need to close the file explicitly
        try (FileWriter fw = new FileWriter("data.txt");
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter outfile = new PrintWriter(bw);) {

            //outfile.println(name);
            outfile.println(modules.size());
            for (Module m : modules) {
                //outfile.println("MODULE");
                m.save(outfile);
            }
        }
    }

    //////////////////////////////////
    public static void main(String[] args) {
        Application application = new Application();

        System.out.println("---------Hello!-----------");
        application.initialise();
        application.setPerson();
        application.setLanguage();
        application.runMenu();
        System.out.println("---------Goodbye!-----------");
    }


}
