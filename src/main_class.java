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
    } // end of printSeq

    public static String translate(String sequence) {

        //
        int words;
        if ((sequence.length()%3) == 0)
            words = sequence.length()/3;
        else
            words = sequence.length()/3 + 1;

        String[] codons = new String[words];
        for (int i = 0; i < words; i++)
            codons[i] = "";

        for (int i = 0, j = 0; i < sequence.length(); i++) {

            if (i%3 == 0 && i != 0)
                j++;

            codons[j] += sequence.charAt(i);
        }

        StringBuilder fin = new StringBuilder();

        for (String codon : codons) {
            switch (codon) {

                case "AUU", "AUC", "AUA" ->                      fin.append("I");
                case "CUU", "CUC", "CUA", "CUG", "UUA", "UUG" -> fin.append("L");
                case "GUU", "GUC", "GUA", "GUG" ->               fin.append("V");
                case "UUU", "UUC" ->                             fin.append("F");
                case "AUG" ->                                    fin.append("M");
                case "UGU", "UGC" ->                             fin.append("C");
                case "GCU", "GCC", "GCA", "GCG" ->               fin.append("A");
                case "GGU", "GGC", "GGA", "GGG" ->               fin.append("G");
                case "CCU", "CCC", "CCA", "CCG" ->               fin.append("P");
                case "ACU", "ACC", "ACA", "ACG" ->               fin.append("T");
                case "UCU", "UCC", "UCA", "UCG", "AGU", "AGC" -> fin.append("S");
                case "UAU", "UAC" ->                             fin.append("Y");
                case "UGG" ->                                    fin.append("W");
                case "CAA", "CAG" ->                             fin.append("Q");
                case "AAU", "AAC" ->                             fin.append("N");
                case "CAU", "CAC" ->                             fin.append("H");
                case "GAA", "GAG" ->                             fin.append("E");
                case "GAU", "GAC" ->                             fin.append("D");
                case "AAA", "AAG" ->                             fin.append("K");
                case "CGU", "CGC", "CGA", "CGG", "AGA", "AGG" -> fin.append("R");
                case "UAA", "UAG", "UGA" ->                      fin.append("-");

                default -> {
                    return "faulty translation; sequence length not divisible by 3";
                }
            }
        }

        return fin.toString();
    }// end of translate

    public static void main(String[] args) {

        oneFile m = new oneFile();

        // main loop
        while (true) {

            try {

                System.out.print("?say > ");
                String cmd = scObj.nextLine().trim().toLowerCase();

                System.out.println();
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
                            m.setMyFile(null);
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

                    case "echo" -> {

                        if (m.getMyFile() == null) {
                            System.out.println("File not loaded yet...\n");
                            continue;
                        }

                        printSeq(m);

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

                    case "tr1" -> { // transcription

                        if (m.getMyFile() == null) {
                            System.out.println("File not loaded yet...\n");
                            continue;
                        }

                        switch (m.getSeq_type()) {

                            case "none" -> System.out.println("Faulty Sequence. Load a different file.\n");

                            case "RNA" -> System.out.println("Sequence already in RNA form.\n");

                            case "protein" -> System.out.println("Invalid command. Protein sequences cannot be transcribed.\n");

                            default -> {

                                // *** sequence changed ***
                                m.setRawSeq(m.getRawSeq().replaceAll("T", "U"));
                                m.setSeq_type("RNA");
                                StringBuilder fin = new StringBuilder();

                                for (int i = 0; i < m.getLength(); i++){

                                    if(i%3 == 0 && i != 0)
                                        fin.append(' ');

                                    if(i%60 == 0 && i != 0)
                                        fin.append('\n');

                                    fin.append(m.getRawSeq().charAt(i));
                                }

                                System.out.println("The transcribed sequence is:\n\n" + fin + "\n");
                            }
                        }
                    }

                    case "re-tr1" -> { // reverse-transcription

                        if (m.getMyFile() == null) {
                            System.out.println("File not loaded yet...\n");
                            continue;
                        }

                        switch (m.getSeq_type()) {

                            case "none" -> System.out.println("Faulty Sequence. Load a different file.\n");

                            case "DNA" -> System.out.println("Sequence already in DNA form.\n");

                            case "protein" -> System.out.println("Invalid command. Protein sequences cannot be reverse-transcribed.\n");

                            default -> {

                                // *** sequence changed ***
                                m.setRawSeq(m.getRawSeq().replaceAll("U", "T"));
                                m.setSeq_type("DNA");
                                StringBuilder fin = new StringBuilder();

                                for (int i = 0; i < m.getLength(); i++){

                                    if(i%3 == 0 && i != 0)
                                        fin.append(' ');

                                    if(i%60 == 0 && i != 0)
                                        fin.append('\n');

                                    fin.append(m.getRawSeq().charAt(i));
                                }

                                System.out.println("The reverse-transcribed sequence is:\n\n" + fin + "\n");
                            }
                        }
                    }

                    case "tr2" -> { // translation

                        if (m.getMyFile() == null) {
                            System.out.println("File not loaded yet...\n");
                            continue;
                        }

                        System.out.println("*** The original nucleotide sequence is retained. ***\n");

                        switch (m.getSeq_type()) {

                            case "none" -> System.out.println("Faulty Sequence. Load a different file.\n");


                            case "protein" -> System.out.println("Invalid command. Protein sequences cannot be translated.\n");

                            case "RNA", "DNA" -> {

                                if (m.getLength() % 3 != 0) {
                                    System.out.println("Sequence length not divisible by 3; cannot translate.\n");
                                    continue;
                                }

                                StringBuilder fin = new StringBuilder();
                                String buff;

                                if (m.getSeq_type().equals("DNA"))
                                    buff = m.getRawSeq().replaceAll("T", "U");
                                else
                                    buff = m.getRawSeq();

                                buff = translate(buff);

                                for (int i = 0; i < buff.length(); i++){

                                    if(i%70 == 0 && i != 0)
                                        fin.append('\n');

                                    fin.append(buff.charAt(i));
                                }

                                System.out.println("The translated sequence is: \n\n" + fin + '\n');
                            }
                        }
                    }

                    case "tr2/s" -> { // translation

                        if (m.getMyFile() == null) {
                            System.out.println("File not loaded yet...\n");
                            continue;
                        }

                        System.out.println("*** The original nucleotide sequence is replaced by the translated sequence. ***\n");

                        switch (m.getSeq_type()) {

                            case "none" -> System.out.println("Faulty Sequence. Load a different file.\n");


                            case "protein" -> System.out.println("Invalid command. Protein sequences cannot be translated.\n");

                            case "RNA", "DNA" -> {

                                if (m.getLength() % 3 != 0) {
                                    System.out.println("Sequence length not divisible by 3; cannot translate.\n");
                                    continue;
                                }

                                StringBuilder fin = new StringBuilder();
                                String buff;

                                if (m.getSeq_type().equals("DNA"))
                                    buff = m.getRawSeq().replaceAll("T", "U");
                                else
                                    buff = m.getRawSeq();

                                m.setRawSeq(translate(buff));

                                for (int i = 0; i < m.getLength(); i++){

                                    if(i%70 == 0 && i != 0)
                                        fin.append('\n');

                                    fin.append(m.getRawSeq().charAt(i));
                                }

                                System.out.println("The translated sequence is: \n\n" + fin + '\n');
                            }
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
