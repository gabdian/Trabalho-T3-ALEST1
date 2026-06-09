import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class LerParticipantes {

    private int count = 0;

    public void lerParticipantes(){

        try{
            FileReader f = new FileReader("PARTICIPANTES.TXT");
            Scanner arquivolido= new Scanner(f);
            //arquivolido.useDelimiter(" ");
            String valorlido;
            while (arquivolido.hasNextLine()){
                count++;
                valorlido=arquivolido.nextLine();
            }
        }
        catch (IOException e) {
            System.err.format("Erro de E/S: %s%n", e);
        }

    }

}
