import java.util.*;

/**
 * Implementação de uma árvore genérica para representar
 * a estrutura hierárquica de menus de uma aplicação.
 *
 * Permite inserir, remover e mover itens, além de realizar
 * consultas e percursos na árvore.
 */
public class MenuApp {

    // Representa um item do menu.
    // Cada nodo conhece seu pai e seus filhos.
    private static class Nodo {
        private String nome;
        private Nodo pai;
        private List<Nodo> filhos;

        public Nodo(String nome) {
            this.nome = nome;
            this.filhos = new ArrayList<>();
        }
    }

    private Nodo raiz;
    private Map<String, Nodo> indice;

    // Cria a árvore com a raiz "App" e inicializa o índice.
    public MenuApp() {
        raiz = new Nodo("App");
        indice = new HashMap<>();
        indice.put("App", raiz);
    }

    // Insere um novo item como filho de um item existente.
    public void inserirItem(String nomePai, String nomeFilho) {
        Nodo pai = buscar(nomePai);

        if (indice.containsKey(nomeFilho)) {
            throw new IllegalArgumentException("Ja existe um item chamado: " + nomeFilho);
        }

        Nodo filho = new Nodo(nomeFilho);
        filho.pai = pai;
        pai.filhos.add(filho);
        indice.put(nomeFilho, filho);
    }

    // Move um item e toda sua subárvore para outro pai.
    public void moverSubarvore(String nomeItem, String nomeNovoPai) {
        Nodo item = buscar(nomeItem);
        Nodo novoPai = buscar(nomeNovoPai);

        if (item == raiz) {
            throw new IllegalArgumentException("Não é permitido mover a raiz.");
        }

        if (ehDescendente(novoPai, item)) {
            throw new IllegalArgumentException(
                    "Nao eh permitido mover um item para um de seus descendentes."
            );
        }

        item.pai.filhos.remove(item);
        item.pai = novoPai;
        novoPai.filhos.add(item);
    }

    // Remove um item e todos os seus descendentes.
    public void removerSubarvore(String nomeItem) {
        Nodo item = buscar(nomeItem);

        if (item == raiz) {
            throw new IllegalArgumentException("Não eh permitido remover a raiz.");
        }

        item.pai.filhos.remove(item);
        removerDoIndice(item);
        item.pai = null;
    }

    // Retorna a altura total da árvore.
    public int altura() {
        return altura(raiz);
    }

    // Calcula recursivamente a altura de um nodo.
    private int altura(Nodo nodo) {
        if (nodo.filhos.isEmpty()) {
            return 0;
        }

        int maiorAltura = 0;

        for (Nodo filho : nodo.filhos) {
            maiorAltura = Math.max(maiorAltura, altura(filho));
        }

        return 1 + maiorAltura;
    }

    // Retorna o maior grau encontrado na árvore.
    public int grauMaximo() {
        int maiorGrau = 0;

        for (Nodo nodo : indice.values()) {
            maiorGrau = Math.max(maiorGrau, nodo.filhos.size());
        }

        return maiorGrau;
    }

    // Conta quantos nodos folha existem.
    public int qtdFolhas() {
        int folhas = 0;

        for (Nodo nodo : indice.values()) {
            if (nodo.filhos.isEmpty()) {
                folhas++;
            }
        }

        return folhas;
    }

    // Conta quantos nodos internos existem.
    public int qtdNosInternos() {
        int internos = 0;

        for (Nodo nodo : indice.values()) {
            if (!nodo.filhos.isEmpty()) {
                internos++;
            }
        }

        return internos;
    }

    // Percorre a árvore em pré-ordem.
    public List<String> preOrdem() {
        List<String> resultado = new ArrayList<>();
        preOrdem(raiz, resultado);
        return resultado;
    }

    // Visita primeiro o nodo atual e depois seus filhos.
    private void preOrdem(Nodo nodo, List<String> resultado) {
        resultado.add(nodo.nome);

        for (Nodo filho : nodo.filhos) {
            preOrdem(filho, resultado);
        }
    }

    // Percorre a árvore em pós-ordem.
    public List<String> posOrdem() {
        List<String> resultado = new ArrayList<>();
        posOrdem(raiz, resultado);
        return resultado;
    }

    // Visita primeiro os filhos e depois o nodo atual.
    private void posOrdem(Nodo nodo, List<String> resultado) {
        for (Nodo filho : nodo.filhos) {
            posOrdem(filho, resultado);
        }

        resultado.add(nodo.nome);
    }

    // Percorre a árvore em largura utilizando uma fila.
    public List<String> largura() {
        List<String> resultado = new ArrayList<>();
        Queue<Nodo> fila = new ArrayDeque<>();

        fila.add(raiz);

        while (!fila.isEmpty()) {
            Nodo atual = fila.remove();
            resultado.add(atual.nome);

            for (Nodo filho : atual.filhos) {
                fila.add(filho);
            }
        }

        return resultado;
    }

    // Retorna o menor ancestral comum entre dois nodos.
    public String lca(String nomeA, String nomeB) {
        Nodo a = buscar(nomeA);
        Nodo b = buscar(nomeB);

        Set<Nodo> ancestraisDeA = new HashSet<>();

        while (a != null) {
            ancestraisDeA.add(a);
            a = a.pai;
        }

        while (b != null) {
            if (ancestraisDeA.contains(b)) {
                return b.nome;
            }
            b = b.pai;
        }

        return null;
    }

    // Retorna o caminho completo entre dois nodos.
    public List<String> caminho(String nomeOrigem, String nomeDestino) {
        Nodo origem = buscar(nomeOrigem);
        Nodo destino = buscar(nomeDestino);

        String nomeLca = lca(nomeOrigem, nomeDestino);
        Nodo ancestralComum = buscar(nomeLca);

        List<String> caminho = new ArrayList<>();

        Nodo atual = origem;
        while (atual != ancestralComum) {
            caminho.add(atual.nome);
            atual = atual.pai;
        }

        caminho.add(ancestralComum.nome);

        LinkedList<String> descida = new LinkedList<>();

        atual = destino;
        while (atual != ancestralComum) {
            descida.addFirst(atual.nome);
            atual = atual.pai;
        }

        caminho.addAll(descida);

        return caminho;
    }

    // Verifica se a estrutura da árvore está consistente.
    public boolean verificarConsistencia() {
        if (raiz == null || raiz.pai != null) {
            return false;
        }

        Set<Nodo> visitados = new HashSet<>();

        boolean semCiclos = verificarSemCiclos(raiz, visitados);

        return semCiclos && visitados.size() == indice.size();
    }

    // Verifica a existência de ciclos e a consistência dos ponteiros.
    private boolean verificarSemCiclos(Nodo nodo, Set<Nodo> visitados) {
        if (visitados.contains(nodo)) {
            return false;
        }

        visitados.add(nodo);

        for (Nodo filho : nodo.filhos) {

            if (filho.pai != nodo) {
                return false;
            }

            if (!verificarSemCiclos(filho, visitados)) {
                return false;
            }
        }

        return true;
    }

    // Busca um nodo pelo nome utilizando o índice.
    private Nodo buscar(String nome) {
        Nodo nodo = indice.get(nome);

        if (nodo == null) {
            throw new IllegalArgumentException("Item nao encontrado: " + nome);
        }

        return nodo;
    }

    // Verifica se um nodo é descendente de outro.
    private boolean ehDescendente(Nodo provavelDesc, Nodo nodo) {
        Nodo atual = provavelDesc;

        while (atual != null) {
            if (atual == nodo) {
                return true;
            }

            atual = atual.pai;
        }

        return false;
    }

    // Remove um nodo e todos os seus descendentes do índice.
    private void removerDoIndice(Nodo nodo) {
        for (Nodo filho : nodo.filhos) {
            removerDoIndice(filho);
        }

        indice.remove(nodo.nome);
    }
}