import java.util.ArrayList;
import java.util.List;

public class Wallet {

    private String name;
    private Integer balance;
    private List<String> transactions = new ArrayList<>();

    public Wallet(String name, Integer balance) {
        this.name = name;
        this.balance = balance;
    }

    Wallet() {}

    public List<String> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<String> transactions) {
        this.transactions = transactions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public void debitBalance(Integer amount, String bnf) {
        this.balance-=amount;
        this.transactions.addLast(bnf+" Debit "+amount);
    }

    public void creditBalance(Integer amount, String sender) {
        this.balance+=amount;
        transactions.addLast(sender+" Credit "+amount);
    }

}