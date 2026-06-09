import java.util.*;

public class BinaryTreeOfString {

    private static final class Node {

        public Node father;
        public Node left;
        public Node right;
        private String element;

        public Node(String element) {
            father = null;
            left = null;
            right = null;
            this.element = element;
        }
    }

    // Atributos
    private int count; //contagem do número de nodos
    private Node root; //referência para o nodo raiz


    // Metodos
    public BinaryTreeOfString() {
        count = 0;
        root = null;
    }

    //Feito com ajuda de IA, Utilizada para retirar duvidas e aprimoramento do codigo
    public void criarCampeonato(ArrayList<String> participantes){
        Queue<Node> lista =  new LinkedList<>();
        Scanner input = new Scanner(System.in);
        Random rand = new Random();

        for(String participante : participantes){
            lista.add(new Node(participante)); //Adiciona os nodos com os nomes dos jogadores a Lista
        }


        while(lista.size() > 1){

            Node jogador1 = lista.poll();
            Node jogador2 = lista.poll();

            System.out.println("Quem venceu? digite 1 para " + jogador1.element + " e 2 para " + jogador2.element );
            //Metodo manual para a apresentação
            //int escolha = input.nextInt();
            String nomeVencedor;
            //Metodo automatico para testes (feito com auxilio de IA)
            int escolha = rand.nextInt(2) + 1;
            System.out.println("Escolha automatica da partica: " + escolha + "\n");

            if (escolha == 1) {
                nomeVencedor = jogador1.element;
            }else {
                nomeVencedor = jogador2.element;
            }
            Node partida = new Node(nomeVencedor);
            partida.left = jogador1;
            partida.right = jogador2;

            jogador1.father = partida;
            jogador2.father = partida;

            lista.add(partida);
        }
        this.root = lista.poll();

        System.out.println("\nO vencedor é: " + this.root.element + "\n");

    }

    /**
     * Remove todos os elementos da arvore.
     */
    public void clear() {
        count = 0;
        root = null;
    }

    /**
     * Verifica se a arvore esta vazia ou nao.
     *
     * @return true se arvore vazia e false caso contrario.
     */
    public boolean isEmpty() {
        return (root == null);
    }

    /**
     * Retorna o total de elementos da arvore.
     *
     * @return total de elementos
     */
    public int size() {
        return count;
    }

    /**
     * Retorna o elemento armazenado na raiz da arvore.
     *
     * @return elemento da raiz.
     * @throws EmptyTreeException se arvore vazia.
     */
    public String getRoot() {
        if (isEmpty()) {
            throw new EmptyTreeException();
        }
        return root.element;
    }

    // Metodo privado que procura por element a partir de target
// e retorna a referencia para o nodo no qual element esta
// armazenado. Retorna null se nao encontrar element.
    private Node searchNodeRef(String element, Node target) {
        if (target == null)
            return null;
        // Visita a "raiz"
        if (element.equals(target.element))
            return target; // se achou element na "raiz"

        // Visita subarvore da esquerda
        Node aux = searchNodeRef(element, target.left);

        // Se nao encontrou, visita a subarvore da direita
        if (aux == null)
            aux = searchNodeRef(element, target.right);

        return aux;
    }

    /**
     * Verifica se element esta ou nao armazenado na arvore.
     *
     * @param element
     * @return true se element estiver na arvore, false caso contrario.
     */
    public boolean contains(String element) {
        Node nAux = searchNodeRef(element, root);
        return (nAux != null);
    }

    /**
     * Retorna quem e o elemento pai do elemento passado por parametro.
     *
     * @param element
     * @return pai de element
     */
    public String getParent(String element) {
        Node n = this.searchNodeRef(element, root);
        if (n == null) {
            return null;
        } else if (n.father == null) {
            return null;
        } else {
            return n.father.element;
        }
    }

    /**
     * Altera o elemento da raiz da arvore.
     *
     * @param element a ser colocado na raiz da arvore.
     */
    public void setRoot(String element) {
        if (root != null) {
            root.element = element;
        }
    }

    /**
     * Insere o elemento como raiz da arvore, se a arvore estiver vazia.
     *
     * @param element a ser inserido.
     * @return true se for feita a insercao, e false caso a arvore nao estiver
     * vazia e a insercao não for feita.
     */
    public boolean addRoot(String element) {
        if (root != null) // se a arvore nao estiver vazia
            return false;
        root = new Node(element);
        count++;
        return true;
    }

    /**
     * Insere element a esquerda de elemFather. Se nao encontrar father,
     * ou se father ja tiver um filho a esquerda, element nao e´
     * inserido.
     *
     * @param element    a ser inserido
     * @param elemFather pai do elemento a ser inserido
     * @return true se foi feita a inserção, e false caso contrario.
     */
    public boolean addLeft(String element, String elemFather) {
        // Primeiro procura por elemFather
        Node aux = searchNodeRef(elemFather, root);

        // Se nao encontrou elemFather
        if (aux == null)
            return false;
        // Se elemFather ja tem filho a esquerda
        if (aux.left != null)
            return false;

        // Senao, insere element
        Node n = new Node(element); // primeiro cria o nodo
        n.father = aux; // faz o novo nodo apontar para o pai
        aux.left = n;// faz o pai apontar para o filho
        count++; // atualiza count
        return true;
    }

    /**
     * Insere element a direita de elemFather. Se nao encontrar father,
     * ou se father ja tiver um filho a direita, element nao e´
     * inserido.
     *
     * @param element    a ser inserido
     * @param elemFather pai do elemento a ser inserido
     * @return true se foi feita a inserção, e false caso contrario.
     */
    public boolean addRight(String element, String elemFather) {
        // Primeiro procura por elemFather
        Node aux = searchNodeRef(elemFather, root);

        // Se nao encontrou elemFather
        if (aux == null)
            return false;
        // Se elemFather ja tem filho a direita
        if (aux.right != null)
            return false;

        // Senao, insere element
        Node n = new Node(element); // primeiro cria o nodo
        n.father = aux; // faz o novo nodo apontar para o pai
        aux.right = n;// faz o pai apontar para o filho
        count++; // atualiza count
        return true;
    }

    // Conta o numero de nodos a partir de "n"
    private int countNodes(Node n) {
        if (n == null)
            return 0;
        return 1 + countNodes(n.left) + countNodes(n.right);
    }

    public int countExternalNodes(){
        return countExternalNodesAux(root);
    }

    public int countExternalNodesAux(Node n) {
        if(n == null){
            return 0;
        }
        if(n.left == null && n.right == null){
            return 1;
        }
        return countExternalNodesAux(n.left) + countExternalNodesAux(n.right);
    }

    public int countInternalNodes(){
        return countInternalNodesAux(root);
    }

    public int countInternalNodesAux(Node n) {
        if(n == null){
            return 0;
        }

        if(n.left == null && n.right == null){
            return 0;
        }
        return 1 + countInternalNodesAux(n.left) + countInternalNodesAux(n.right);
    }

// ---------------------------------------------------------------------

    /**
     * Retorna uma lista com todos os elementos da arvore na ordem do
     * caminhamento pre-fixado.
     *
     * @return lista com todos os elementos da arvore.
     */
    public LinkedListOfParticipantes positionsPre() {
        LinkedListOfParticipantes lista = new LinkedListOfParticipantes();
        positionsPreAux(root, lista);
        return lista;
    }

    private void positionsPreAux(Node n, LinkedListOfParticipantes lista) {
        if (n != null) {
            lista.add(n.element); // visita raiz ... insere na lista
            positionsPreAux(n.left, lista); // percorre subarvore da esq
            positionsPreAux(n.right, lista); // percorre subarvore da dir
        }
    }

    public LinkedListOfParticipantes positionsPos() {
        LinkedListOfParticipantes lista = new LinkedListOfParticipantes();
        positionsPosAux(root, lista);
        return lista;
    }

    private void positionsPosAux(Node n, LinkedListOfParticipantes lista) {
        if (n != null) {
            positionsPosAux(n.left, lista);
            positionsPosAux(n.right, lista);
            lista.add(n.element);
        }
    }

    public LinkedListOfParticipantes positionCentral() {
        LinkedListOfParticipantes lista = new LinkedListOfParticipantes();
        positionCentralAux(root, lista);
        return lista;
    }

    public void positionCentralAux(Node n, LinkedListOfParticipantes lista) {
        if (n != null) {
            positionCentralAux(n.left, lista);
            lista.add(n.element);
            positionCentralAux(n.right, lista);
        }
    }

    public LinkedListOfParticipantes positionsWidth() {
        LinkedListOfParticipantes lista = new LinkedListOfParticipantes();
        if (root == null) {
            return lista;
        }
        Queue<Node> fila = new LinkedList<>();
        fila.add(root);
        while (!fila.isEmpty()) {
            Node aux = fila.poll();
            lista.add(aux.element);
            if (aux.left != null) {
                fila.add(aux.left);
            }
            if (aux.right != null) {
                fila.add(aux.right);
            }
        }

        return lista;
    }

    public int altura(){
        return alturaAux(root);
    }

    private int alturaAux(Node n) {
        if(n == null){
            return -1;
        }
        int alturaDireita = alturaAux(n.right);
        int alturaEsquerda = alturaAux(n.left);

        if(alturaDireita < alturaEsquerda){
            return alturaDireita + 1;
        }else {
            return alturaEsquerda + 1;
        }
    }

}