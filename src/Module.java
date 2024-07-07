import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * @author Nikola Nikolov
 * @version 5 May 2021
 */
public class Module {
    private ArrayList<Bank> banks;
    private String id;

    Module(String id){
        this.id = id;
        this.banks= new ArrayList<Bank>();
    }

    Module(){
        this( "none");
    }

    /**
     * This method adds a new bank
     * @param bank - the new bank
     */
    public void addBank(Bank bank){
        banks.add(bank);
    }

    /**
     *  This method remove a bank from the module
     * @param index of the bank that will be remove
     */
    public void delBank(int index){
        banks.remove(index);
    }

    /**
     * This method prints every bank id of the module
     */
    public void listBankIDs(){
        banks.forEach(banks -> System.out.println(banks.getId()));
    }

    /**
     * This method sets the value for id
     *
     * @param id becomes the value
     */
    public void setId(String id){
        this.id = id;
    }

    /**
     * Get identifier
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * This method returns the bank that is stored on that index
     * @param index
     * @return bank
     */
    public Bank getBank(int index){
        return banks.get(index);
    }

    /**
     * Get the num of banks
     * @return the num of banks
     */
    public int getBanksSize(){
        return banks.size();
    }

    /**
     * This method returns true if there isn't bank with that id
     *
     * @param identifier of the module
     * @return
     */
    public boolean isBankIdFree(String identifier){
        for(int i=0; i< banks.size(); i++){
            if(banks.get(i).getId().equals(identifier)){
                return false;
            }
        }
        return true;
    }

    /**
     * Write information about the Module in the file
     */
    public void save(PrintWriter pw){
        pw.println(id);
        pw.println(banks.size());
        for(Bank b: banks){
            //pw.println("BANK");
            b.save(pw);
        }
    }

    /**
     *  Reads in information about the module from the file
     * @param infile
     */
    public void load(Scanner infile){

        id = infile.next();
        int num= infile.nextInt();
        //infile.next();
        //int num = numS;
        for (int oCount = 0; oCount < num; oCount++) {
            Bank b = new Bank();
            b.load(infile);
            addBank(b);
        }
    }
}
