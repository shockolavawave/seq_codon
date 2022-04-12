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

    private String seq_type;
    public String getSeq_type() {        return seq_type;    }
    public void setSeq_type(String seq_type) {        this.seq_type = seq_type;    }

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

    // tells the sequence type
    public static String getSequenceType(String sequence) {

        if (isDNA(sequence))
            return "DNA";
        else if (isRNA(sequence))
            return "RNA";
        else if (isProtein(sequence))
            return "protein";
        else
            return "none";
    }

    @SuppressWarnings("StatementWithEmptyBody")
    public static boolean isDNA(String sequence) {

        for (int i = 0; i < sequence.length(); i++) {
            if (sequence.charAt(i) == 'A' || sequence.charAt(i) == 'G' || sequence.charAt(i) == 'T' || sequence.charAt(i) == 'C'){
                // null block
            }
            else
                return false;
        }

        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    public static boolean isRNA(String sequence) {

        for (int i = 0; i < sequence.length(); i++) {
            if (sequence.charAt(i) == 'A' || sequence.charAt(i) == 'G' || sequence.charAt(i) == 'U' || sequence.charAt(i) == 'C'){
                // null block
            }
            else
                return false;
        }

        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    public static boolean isProtein(String sequence) {

        for (int i = 0; i < sequence.length(); i++) {
            if (
                    sequence.charAt(i) == 'A' || sequence.charAt(i) == 'C' || sequence.charAt(i) == 'D' || sequence.charAt(i) == 'E' ||
                            sequence.charAt(i) == 'F' || sequence.charAt(i) == 'G' || sequence.charAt(i) == 'H' || sequence.charAt(i) == 'I' ||
                            sequence.charAt(i) == 'K' || sequence.charAt(i) == 'L' || sequence.charAt(i) == 'M' || sequence.charAt(i) == 'N' ||
                            sequence.charAt(i) == 'O' || sequence.charAt(i) == 'P' || sequence.charAt(i) == 'Q' || sequence.charAt(i) == 'R' ||
                            sequence.charAt(i) == 'S' || sequence.charAt(i) == 'T' || sequence.charAt(i) == 'U' || sequence.charAt(i) == 'V' ||
                            sequence.charAt(i) == 'W' || sequence.charAt(i) == 'Y'
            ){
                // null block
            }
            else
                return false;
        }

        return true;
    }

    // printing codons
    public static void printSeq(oneFile m) {

        StringBuilder fin = new StringBuilder();

        m.setSeq_type(getSequenceType(m.getRawSeq()));

        switch (m.getSeq_type()) {

            case "DNA" -> {
                for (int i = 0; i < m.getLength(); i++){

                    if(i%3 == 0 && i != 0)
                        fin.append(' ');

                    if(i%60 == 0 && i != 0)
                        fin.append('\n');

                    fin.append(m.getRawSeq().charAt(i));
                }

                System.out.println("The sequence type is DNA. Printing in codon fashion.\n");
                System.out.println(m.getHeaderLine() + "\n\n" + fin +
                        "\n\n" + "length: " + m.getLength());
            }

            case "RNA" -> {
                for (int i = 0; i < m.getLength(); i++){

                    if(i%3 == 0 && i != 0)
                        fin.append(' ');

                    if(i%60 == 0 && i != 0)
                        fin.append('\n');

                    fin.append(m.getRawSeq().charAt(i));
                }

                System.out.println("The sequence type is RNA. Printing in codon fashion.\n");
                System.out.println(m.getHeaderLine() + "\n\n" + fin +
                        "\n\n" + "length: " + m.getLength());
            }

            case "protein" -> {
                for (int i = 0; i < m.getLength(); i++){

                    if(i%70 == 0 && i != 0)
                        fin.append('\n');

                    fin.append(m.getRawSeq().charAt(i));
                }

                System.out.println("The sequence type is protein.\n");
                System.out.println(m.getHeaderLine() + "\n\n" + fin +
                        "\n\n" + "length: " + m.getLength());
            }
            case "none" -> System.out.println("There something wrong with the provided sequence;\n" +
                                              "it doesn't fall under any category (DNA/RNA/protein).");
        } // end of switch statement
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

                        printSeq(m);

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

                    case "end", "exit", "bye" -> {
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
