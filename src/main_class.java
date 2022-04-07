import javax.swing.*;
import java.io.File;
import java.util.Scanner;

class oneFile {

    // input variables/objects
    private File myFile;
    public File getMyFile() {   return myFile;    }
    public void setMyFile(File myFile) {     this.myFile = myFile;    }

    private String headerLine;
    public String getHeaderLine() {     return headerLine;    }
    public void setHeaderLine(String headerLine) {  this.headerLine = headerLine;    }

    private String rawSeq;
    public String getRawSeq() {     return rawSeq;    }
    public void setRawSeq(String rawSeq) {    this.rawSeq = rawSeq;    }

    private int length;
    public int getLength() {   return length;    }
    public void setLength(int length) {    this.length = length;    }
}

public class main_class {

    static Scanner scObj = new Scanner(System.in);
    static Scanner scObjG = new Scanner(System.in);
    static Scanner scObjF = new Scanner(System.in);

    // for checking of file format
    public static boolean formatCheck (File fl){

        String name = fl.getName();
        StringBuilder buff = new StringBuilder();

        for (int i = (name.length() - 1); i != 0; i--) {

            if (name.charAt(i) == '.')
                break;

            buff.append(name.charAt(i));
        }

        String format = buff.reverse().toString();

        return (format.equals("fasta") || format.equals("FASTA"));
    }

    // printing codons
    public static void printCodons(oneFile m) {
        StringBuilder fin = new StringBuilder();

        for (int i = 0; i < m.getLength(); i++){

            if(i%3 == 0 && i != 0)
                fin.append(' ');

            if(i%60 == 0 && i != 0)
                fin.append('\n');

            fin.append(m.getRawSeq().charAt(i));
        }

        System.out.println(m.getHeaderLine() + "\n\n" + fin +
                            "\n\n" + "length: " + m.getLength());
    }

    public static void main(String[] args) {

        oneFile m = new oneFile();

        // main loop
        while (true) {

            try {

                System.out.print("?say > ");
                String cmd = scObj.nextLine().trim().toLowerCase();

                switch (cmd) {

                    case "start" ->{

                        // getting the file and its name
                        try {
                            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

                            JFileChooser chooser = new JFileChooser();
                            chooser.setVisible(true);
                            chooser.setDialogTitle("FASTA ONLY");

                            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

                                m.setMyFile(chooser.getSelectedFile());

                                if (!formatCheck(m.getMyFile()))
                                    throw new Exception("only FASTA files are allowed.");
                            }else
                                continue;

                        } catch (Exception e) {
                            System.out.println("Something went wrong: " + e.getMessage() + '\n' +
                                    "Try again\n\n");
                            continue;
                        }

                        StringBuilder text = new StringBuilder();

                        // storing header and raw sequence
                        try {
                            scObjF = new Scanner(m.getMyFile());
                            for (int i = 0; scObjF.hasNext();) {

                                if (i == 0) {
                                    m.setHeaderLine(scObjF.nextLine());
                                    i++;
                                } else
                                    text.append(scObjF.nextLine());
                            }

                            m.setRawSeq(text.toString().toUpperCase());

                        } catch (Exception ne) {
                            System.out.println("something went wrong" + ne.getMessage());
                        }

                        m.setLength(m.getRawSeq().length());

                        System.out.println(m.getMyFile().getName() + " loaded successfully!\n");
                        System.out.println("The codons are as follows: \n\n");

                        printCodons(m);

                        System.out.println("\n\n");
                        // *** case end


                    }

                    case "get" -> {

                        if (m.getMyFile() == null) {
                            System.out.println("File not loaded yet...\n");
                            continue;
                        }

                        try {
                            System.out.print("?residue number: ");
                            int res = scObjG.nextInt();

                            if (res < 0 || res > m.getLength()) {
                                System.out.println("residue number invalid.\nsay get again\n");
                            } else
                                System.out.println(res + " -> " + m.getRawSeq().charAt(res-1));

                        } catch (NullPointerException e) {
                            System.out.println("something went wrong: " + e.getMessage());
                        }
                    }

                    case "end" -> {
                        System.out.println("\nExiting...\n");
                        System.exit(0);
                    }

                    default -> System.out.println("Invalid input!\nEnter 'start' to continue or 'end' to exit.\n");

                } // end of switch

            } catch (Exception e) {
                System.out.println("something went wrong: " + e.getMessage());
            }

        } // end of while loop
    } // end of main method
}// end of main class
