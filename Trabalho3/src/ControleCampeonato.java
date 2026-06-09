import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class ControleCampeonato {

    private ArrayList<String> participantes = new ArrayList<>();
    private BinaryTreeOfString tree = new BinaryTreeOfString();


    public void comecarCampeonato(){
        System.out.println("O Java está procurando o arquivo nesta pasta: " + new java.io.File(".").getAbsolutePath());
        try{
            //FileReader f = new FileReader("PARTICIPANTES.TXT");
            FileReader f = new FileReader("Trabalho3/PARTICIPANTES.TXT");
            Scanner arquivolido= new Scanner(f);

            while (arquivolido.hasNextLine()){
                participantes.add(arquivolido.nextLine());
            }
            arquivolido.close();
            Collections.shuffle(participantes); //Randomiza os nomes dos participantes
            tree.criarCampeonato(participantes); //Manda a lista para o ControleCampeonato
            System.out.println(tree.positionCentral());

        }
        catch (IOException e) {
            System.err.format("Erro de E/S: %s%n", e);
        }
        System.out.println("Torneio finalizado");
    }

}
