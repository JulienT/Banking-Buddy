import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public class UserController {
    private User model;
    private UserView view;

    public UserController(User model, UserView view){
        this.model = model;
        this.view = view;
    }

    public void setUserName(String name){
        model.setName(name);
    }

    public String getUserName(){
        return model.getName();
    }

    public void setUserWallet(Wallet wallet){
        model.setWallet(wallet);
    }

    public Wallet getUserWallet(){
        return model.getWallet();
    }

    public void setUserEntries(ArrayList<Entry> entries){
        model.setEntries(entries);
    }

    public ArrayList<Entry> getUserEntries(){
        return model.getEntries();
    }

    public void setUserCategories(ArrayList<Category> categories){
        model.setCategories(categories);
    }

    public ArrayList<Category> getUserCategories(){
        return model.getCategories();
    }

    public void setUserGoals(ArrayList<Goal> goals){
        model.setGoals(goals);
    }

    public ArrayList<Goal> getUserGoals(){
        return model.getGoals();
    }

    public void registerUser(){
        String username = view.promptStringInput("Enter your name: ");
        setUserName(username);
        Wallet wallet = new Wallet();
        BigDecimal balance = view.promptBalanceInput("Enter your current balance: ");
        wallet.setBalance(balance);
        model.setWallet(wallet);
    }

    public void selectOptions(){
        String[] options = {"1) Add an entry", "2) Add a category", "3) Add a goal", "4) View my entries", "5) View my information"};
        while (true){
            int selectedOption = view.promptOptionInput(options);
            switch (selectedOption){
                case 1:
                    makeEntry();
                    break;
                case 2:
                    makeCategory();
                    break;
                case 3:
                    makeGoal();
                    break;
                case 4:
                    for (Entry entry : getUserEntries()){
                        view.displayEntryDetails(entry.getType(), entry.getTransactionCategory().getCategoryName(), entry.getAmount(), entry.getTimeStamp());
                    }
                    break;
                case 5:
                    view.displayUserDetails(getUserName(), getUserWallet().getBalance());
                    break;
                default:
                    view.displayMessage("Invalid option chosen, try again.");
            }
        }
    }

    public void makeEntry(){
        Entry newEntry = new Entry();
        String type = view.promptStringInput("Enter type of entry: (Income/Expenditure)");
        newEntry.setType(type);
        if (getUserCategories().size() > 0){
            int selectedCategory = view.promptSelectCategory(getUserCategories()) - 1;
            while (selectedCategory < 0 || selectedCategory >= getUserCategories().size() + 1){
                view.displayMessage("Invalid category chosen, try again.");
                selectedCategory = view.promptSelectCategory(getUserCategories()) - 1;
            }
            if (selectedCategory == getUserCategories().size()){
                makeCategory();
                newEntry.setTransactionCategory(getUserCategories().get(getUserCategories().size() - 1));
            }else {
                newEntry.setTransactionCategory(getUserCategories().get(selectedCategory));
            }
        }else{
            makeCategory();
            newEntry.setTransactionCategory(getUserCategories().get(0));
        }
        BigDecimal amount = view.promptBalanceInput("Enter the amount spent/received: ");
        newEntry.setAmount(amount);
        Date date = new Date();
        newEntry.setTimeStamp(date);
        model.addEntry(newEntry);
    }

    public void makeCategory(){
        Category newCategory = new Category();
        String name = view.promptStringInput("Enter category name: ");
        newCategory.setCategoryName(name);
        model.addCategory(newCategory);
    }

    public void makeGoal(){
        Goal newGoal = new Goal();
        String name = view.promptStringInput("Enter goal name: ");
        newGoal.setGoalName(name);
        BigDecimal amount = view.promptBalanceInput("Enter amount: ");
        newGoal.setGoalAmount(amount);
        model.addGoal(newGoal);
    }
}
