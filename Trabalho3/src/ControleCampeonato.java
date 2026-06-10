import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class ControleCampeonato {

    private ArrayList<String> participantes = new ArrayList<>();
    private BinaryTreeOfString tree = new BinaryTreeOfString();

    public void comecarCampeonato() {
        try {
            // FileReader f = new FileReader("PARTICIPANTES.TXT");
            FileReader f = new FileReader("Trabalho3/PARTICIPANTES.TXT");
            Scanner arquivolido = new Scanner(f);

            while (arquivolido.hasNextLine()) {
                participantes.add(arquivolido.nextLine());
            }
            arquivolido.close();
            Collections.shuffle(participantes); // Randomiza os nomes dos participantes
            tree.criarCampeonato(participantes); // Manda a lista para o ControleCampeonato

            System.out.println("Altura da arvore (rodadas): " + tree.altura());
            System.out.println("Folhas (participantes): " + tree.countExternalNodes());
            System.out.println("Nos internos (partidas): " + tree.countInternalNodes());

            System.out.println("\nPercurso PRE-ORDEM:\n" + tree.positionsPre());
            System.out.println("Percurso POS-ORDEM:\n" + tree.positionsPos());
            System.out.println("Percurso em LARGURA:\n" + tree.positionsWidth());

            // Teste do LCA na primeira partida
            String jogA = participantes.get(0);
            String jogB = participantes.get(1);
            System.out.println("1. Onde " + jogA + " e " + jogB + " se encontram?");
            System.out.println("   Resposta do sistema: Na partida vencida por -> " + tree.lca(jogA, jogB));

            // Teste do LCA na Ultima partida
            String extremoEsquerda = participantes.get(0);
            String extremoDireita = participantes.get(31);
            System.out.println("\n2. Onde " + extremoEsquerda + " e " + extremoDireita + " se encontram?");
            System.out.println(
                    "   Resposta do sistema: Na partida vencida por -> " + tree.lca(extremoEsquerda, extremoDireita));

            tree.mostrarCaminho(participantes.get(0));
        } catch (IOException e) {
            System.err.format("Erro de E/S: %s%n", e);
        }
        System.out.println("Torneio finalizado");
    }

}
