public class MainMenu {

    static void main(String[] args) {

    MenuApp menu = new MenuApp();

    menu.inserirItem("App", "Conta");
    menu.inserirItem("App", "Pagamentos");
    menu.inserirItem("App", "Configurações");


    menu.inserirItem("Conta", "Perfil");
    menu.inserirItem("Conta", "Segurança");

    menu.inserirItem("Pagamentos", "Cartões");
    menu.inserirItem("Pagamentos", "Histórico");

    menu.inserirItem("Configurações", "Notificações");

    menu.inserirItem("Notificações", "Email");
    menu.inserirItem("Notificações", "Push");

        System.out.println("Pre-ordem inicial: " + menu.preOrdem());

        menu.moverSubarvore("Notificações", "Conta");
        System.out.println("Depois de mover Notificações para conta: ");
        System.out.println("Pre-ordem: " + menu.preOrdem());

        System.out.println("Altura: " + menu.altura());
        System.out.println("Grau máximo: " + menu.grauMaximo());
        System.out.println("Folhas: " + menu.qtdFolhas());
        System.out.println("Nos internos: " + menu.qtdNosInternos());

        System.out.println("Pos-Ordem: " + menu.posOrdem());
        System.out.println("Largura: " + menu.largura());

        System.out.println("LCA(Email, Push): " + menu.lca("Email", "Push"));
        System.out.println("LCA(Email, Segurança): " + menu.lca("Email", "Segurança"));
        System.out.println("Caminho(Email, Histórico): " + menu.caminho("Email", "Histórico"));

        menu.removerSubarvore("Pagamentos");
        System.out.println("Depois de remover Pagamentos: ");
        System.out.println("Pre-Ordem: " + menu.preOrdem());

        System.out.println("Eh consistente? " + menu.verificarConsistencia());

    }

}
