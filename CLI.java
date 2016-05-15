import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class CLI {
    int number;
    String[] history_command;
    boolean run;
    String initials="@@"; //replace by your initials
    public int getNumber() {
        return number;
    }

    CLI(){
        number=0;
        history_command=new String[10];//command history
        run=true;
    }

    public static void main(String args[]) throws IOException {
        CLI cli=new CLI();
        Scanner sc=new Scanner(new InputStreamReader(System.in));
        while (cli.run){
            System.out.print(cli.initials+"-"+(cli.getNumber()+1)+"> ");
            String comm=sc.nextLine();
            if (!comm.isEmpty()){
                cli.main(comm);
            }else {
                System.out.println();
            }
        }
    }
//main method, that choose, what command is typed
    private void main(String command) throws IOException {
        switch (command.charAt(0)){
            case '!':
                int tmp= Integer.parseInt(command.substring(1));
            if (tmp>(this.number-10)){
              int gh=this.number-tmp;
                this.main(this.history_command[9-gh]);
            } else {
                System.out.println("\t"+"Command history only available back to command "+(this.number-9));
            }

            break;
            case '#':
                String tmp2= command.substring(1);
                Process ls=null;
                BufferedReader input=null;
                String line=null;
                String[] tmp3=tmp2.split(" ");
                try {
                    ls= Runtime.getRuntime().exec(tmp3);
                    input = new BufferedReader(new InputStreamReader(ls.getInputStream()));
                } catch (IOException e1) {
                    e1.printStackTrace();
                    System.exit(1);
                }

                try {
                    while( (line=input.readLine())!=null)
                        System.out.println("\t"+line);

                } catch (IOException e1) {
                    e1.printStackTrace();
                    System.exit(0);
                }
                this.number++;
                this.addCommand(command);
                break;
            default:
                switch (command){
                    case "version":
                        System.out.println("\t"+"<Your_Name> CLI, Version 1.0");
                        this.number++;
                        this.addCommand(command);
                        break;
                    case "history":
                        if (this.number>10){

                            for (int c=0;c<10;c++){
                                int ch=this.number-9+c;
                                System.out.println(("\t"+ch+": "+this.history_command[c]));                            }

                        } else{
                            for (int c=0;c<this.number;c++){

                                int gg=c+10-this.number;
                                System.out.println(("\t"+(c+1)+": "+this.history_command[gg]));
                            }
                        }
                        this.number++;
                        this.addCommand(command);
                        break;
                    case "help":
                        this.number++;
                        System.out.println("\t"+"version         print out the CLI version number\n" +
                                "\t"+"history  \tdisplays last 10 commands entered\n" +
                                "\t"+"help       \tdisplays list of valid commands\n" +
                                "\t"+"exit       \texits CLI\n" +
                                "\t"+"date       \tprints out current date\n" +
                                "\t"+"time\t\tprints out current time\n" +
                                "\t"+"!<number>\t(re)executes command <number> from the history\n" +
                                "\t"+"#<cmd>          executes program <cmd>\n");
                        this.addCommand(command);
                        break;
                    case "exit":
                        System.out.println("\t"+this.initials+" CLI exiting.");
                        this.number++;
                        this.addCommand(command);
                        this.run=false;
                        break;
                    case "date":
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        System.out.println("\t"+"The date is  " + dateFormat.format(new Date() ) );
                        this.number++;
                        this.addCommand(command);
                        break;
                    case "time":
                        SimpleDateFormat dateFormat2 = new SimpleDateFormat("H:mm");
                        System.out.println("\t"+"The time is  " + dateFormat2.format(new Date() ) );
                        this.number++;
                        this.addCommand(command);
                        break;
                    default:
						System.out.println("\t"+"\""+command+"\" is an invalid command.");
                        break;

                }
                break;
        }

    }

    private void addCommand(String s) {
        String[] tmp=new String[10];
        tmp[9]=s;
        int ch=this.history_command.length-9;
        for (int i=0;i<9;i++){
            tmp[i]=this.history_command[i+ch];
        }
        this.history_command=tmp;
    }
}
