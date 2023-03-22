import java.io.IOException;
import java.util.Scanner;

public class CLI {
    public static void main(String[] args) throws IOException {
        System.out.println("please choose type of Dictionary:\nAVL Tree-->1\nRB Tree-->2");
        Scanner sc= new Scanner(System.in);
        Boolean st = true;
        String ch="",w ;
        while(st){
            ch = sc.nextLine().replaceAll("\\ ", "");
            if (ch.equals("1")||ch.equals("2")){
                st = false;
            }
            else{
                System.out.println("please enter a valid choice :(\n");
            }
        }
        st = true;
        English_Dictionary D = new English_Dictionary(ch);
        System.out.println("Choose what you want to do:\n" +
                "Insert-->1\n" +
                "Delete-->2\n" +
                "Search-->3\n" +
                "Batch Insert-->4\n" +
                "Batch Delete-->5\n" +
                "Size-->6\n" +
                "Tree Height-->7\n" +
                "Show Dictionary-->8\n" +
                "Show choices-->9\n" +
                "Make a new Dictionary-->10\n");
        while(st){
            ch = sc.nextLine().replaceAll("\\ ", "");
            switch (ch){
                case "1":
                    System.out.println("Enter the word you want to insert:");
                    w = sc.nextLine().replaceAll("\\ ", "");
                    if (w.contains("(")||w.contains(")")||w.contains("[")||w.contains("]")||w.contains("{")||w.contains("}")){
                        System.out.println("please enter a valid word :(\n");
                    }
                    else {
                        D.Insert(w);
                    }
                    break;
                case "2":
                    System.out.println("Enter the word you want to Delete");
                    w = sc.nextLine().replaceAll("\\ ", "");
                    if (w.contains("(")||w.contains(")")||w.contains("[")||w.contains("]")||w.contains("{")||w.contains("}")){
                        System.out.println("please enter a valid word :(\n");
                    }
                    else {
                        D.Delete(w);
                    }
                    break;
                case "3":
                    System.out.println("Enter the word you want to Search for");
                    w = sc.nextLine().replaceAll("\\ ", "");
                    if (w.contains("(")||w.contains(")")||w.contains("[")||w.contains("]")||w.contains("{")||w.contains("}")){
                        System.out.println("please enter a valid word :(\n");
                    }
                    else {
                        D.Search(w);
                    }
                    break;
                case "4":
                    System.out.println("Enter the txt path:");
                    w = sc.nextLine();
                    D.BInsert(w);
                    break;
                case "5":
                    System.out.println("Enter the txt path:");
                    w = sc.nextLine();
                    D.BDelete(w);
                    break;
                case "6":
                    D.Size();
                    break;
                case "7":
                    D.Tree_Height();
                    break;
                case "8":
                    D.Show();
                    break;
                case "9":
                    System.out.println("Choose what you want to do:\n" +
                            "Insert-->1\n" +
                            "Delete-->2\n" +
                            "Search-->3\n" +
                            "Batch Insert-->4\n" +
                            "Batch Delete-->5\n" +
                            "Size-->6\n" +
                            "Tree Height-->7\n" +
                            "Show Dictionary-->8\n" +
                            "Show choices-->9\n" +
                            "Make a new Dictionary-->10\n");
                    break;
                case "10":
                    System.out.println("please choose type of Dictionary:\nAVL Tree-->1\nRB Tree-->2");
                    boolean f = true;
                    String ty = "";
                    while(f){
                        ty = sc.nextLine().replaceAll("\\ ", "");
                        if (ty.equals("1")||ty.equals("2")){
                            f = false;
                        }
                        else{
                            System.out.println("please enter a valid choice :(\n");
                        }
                    }
                    D = new English_Dictionary(ty);
                    break;
                default:
                    System.out.println("please enter a valid choice :(\n");
            }
        }
    }
}
