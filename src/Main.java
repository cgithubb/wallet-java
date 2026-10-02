import java.util.*;
import java.util.stream.Collectors;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static final String CREATE_CMD = "CreateWallet";
    private static final String OVERVIEW = "Overview";
    private static final String Transfer_Amount = "TransferAmount";
    private static final String Statement = "Statement";
    private static final String Offer2 = "Offer2";
    public static void main(String[] args) {

        Map<String, Wallet> wallets = new HashMap<>();

        Scanner sc = new Scanner(System.in);

        while(true) {
            var command = sc.next();

            if ("END".equalsIgnoreCase(command)) {
                break;
            }

            if (CREATE_CMD.equalsIgnoreCase(command)) {
                var accNm = sc.next();
                Integer initAmt = Integer.valueOf(sc.next());
                wallets.put(accNm, new Wallet(accNm, initAmt));
                System.out.println("Wallet created for "+ accNm);
            }

            if (OVERVIEW.equalsIgnoreCase(command)) {
                for (Map.Entry<String, Wallet> wallet: wallets.entrySet()) {
                    System.out.println(wallet.getKey() +" "+ wallet.getValue().getBalance());
                }
            }

            if (Transfer_Amount.equalsIgnoreCase(command)) {
                var sender = sc.next();
                var bnf = sc.next();
                var amt = Integer.parseInt(sc.next());
                if (!wallets.containsKey(sender)) {
                    System.out.printf("Wallet %s doesn't exist%n", sender);
                    continue;
                }
                if (!wallets.containsKey(bnf)) {
                    System.out.printf("Wallet %s doesn't exist%n", bnf);
                    continue;
                }
                var senderWallet = wallets.get(sender);
                var bnfWallet = wallets.get(bnf);
                if (senderWallet.getBalance()>=amt) {
                    senderWallet.debitBalance(amt, bnf);
                    bnfWallet.creditBalance(amt, sender);
                    checkAndApplyOffer1(senderWallet, bnfWallet);
                } else {
                    System.out.println("Not enough balance in sender wallet");
                    continue;
                }
                System.out.printf("Amount transferred from %s to %s %n", sender, bnf);
            }

            if(Statement.equalsIgnoreCase(command)) {
                var walletName = sc.next();
                if (!wallets.containsKey(walletName)) {
                    System.out.println(walletName+" doesn't exist ");
                    continue;
                }
                for (String transaction: wallets.get(walletName).getTransactions()) {
                    System.out.println(transaction);
                }
            }

            if(Offer2.equalsIgnoreCase(command)) {
                List<Wallet> topThree = wallets.values().stream()
                        .sorted(Comparator.comparingInt((Wallet wallet) ->
                                                wallet.getTransactions().size()
                                ).reversed().thenComparing(Comparator.comparingDouble(Wallet::getBalance).reversed()))
                        .limit(3)
                        .collect(Collectors.toList());

                int[] offer2Rewards = {10, 5, 2};
                for (int i=0; i<topThree.size(); i++) {
                    topThree.get(i).creditBalance(offer2Rewards[i], Offer2);
                }
            }

        }
        sc.close();
    }

    private static void checkAndApplyOffer1(Wallet senderWallet, Wallet bnfWallet) {
         if (Objects.equals(senderWallet.getBalance(), bnfWallet.getBalance())) {
             senderWallet.creditBalance(10, "Offer1");
             bnfWallet.creditBalance(10, "Offer1");
             System.out.println("===========Offer1 applied============");
         }
    }
}



