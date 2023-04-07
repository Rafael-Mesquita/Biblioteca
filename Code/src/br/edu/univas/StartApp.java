package br.edu.univas;

import java.util.List;
import java.util.Scanner;

public class StartApp {

    public static void main(String[] args) {
        String url = "files/biblioteca.csv";
        startBiblioteca(url);
    }
    public static void startBiblioteca(String url) {
        Scanner leitor = new Scanner(System.in);
        Biblioteca biblioteca = new Biblioteca();
        boolean start = true;
        if(!Biblioteca.ArquivoExiste(url)) {
            biblioteca.CriarArquivo(url);
        }
        do {
            System.out.println("""
                    Bem Vindo, o que deseja fazer?:
                    1 - Cadastrar Livro
                    2 - Remover Livro
                    3 - Buscar Livro
                    4 - Gerar Relatório
                    0 - Sair""");
            int escolha = leitor.nextInt();
            List<String> existentes = biblioteca.LinhaExistentes(url);
            switch (escolha) {
                default -> System.out.println("Escolha incorreta, por favor escolha outra!");
                case 0 -> {
                    System.out.println("Até mais");
                    start = false;
                }
                case 1 -> cadastrarLivro(url, leitor, biblioteca, existentes);
                case 2 -> excluirLivro(url, leitor, biblioteca, existentes);
                case 3 -> buscarLivro(leitor, existentes);
                case 4 -> gerarRelatorio(existentes);
            }
        } while(start);
        leitor.close();
    }
    public static void cadastrarLivro(String url, Scanner leitura, Biblioteca biblioteca, List<String> existentes) {
        leitura.nextLine();
        String[] conteudoLivro = new String[3];
        System.out.print("Por favor, digite o nome do livro: ");
        conteudoLivro[0] = leitura.nextLine();
        System.out.print("Por favor, digite o número de páginas: ");
        int numeroPaginas = leitura.nextInt();
        leitura.nextLine();
        System.out.print("Por favor, digite o nome do autor: ");
        conteudoLivro[1] = leitura.nextLine();
        System.out.print("Por favor, digite a área de interesse: ");
        conteudoLivro[2] = leitura.nextLine();
        if(existentes.size() == 1){
            biblioteca.CadastrarLivros(conteudoLivro[0].trim(), String.valueOf(numeroPaginas).trim(), conteudoLivro[1].trim(), conteudoLivro[2].trim(), url);
            System.out.println(conteudoLivro[0] + " - Livro Cadastrado");
        } else {
            boolean encontrado = false;
            for (int i = 1; i < existentes.size(); i++) {
                if (existentes.get(i).split(",")[0].trim().equalsIgnoreCase(conteudoLivro[0].trim()) && existentes.get(i).split(",")[2].trim().equalsIgnoreCase(conteudoLivro[1].trim()) && existentes.get(i).split(",")[3].trim().equalsIgnoreCase(conteudoLivro[2].trim())) {
                    encontrado = true;
                    break;
                }
            }
            if(encontrado)
                System.out.println("Livro duplicado! \n");
            else {
                biblioteca.CadastrarLivros(conteudoLivro[0].trim(), String.valueOf(numeroPaginas).trim(), conteudoLivro[1].trim(), conteudoLivro[2].trim(), url);
                System.out.println(conteudoLivro[0] + " - Livro Cadastrado");
            }
        }
    }

    public static void buscarLivro(Scanner scanner, List<String> existentes) {
        if (existentes.size() == 1)
            System.out.println("Cadastre pelo menos um livro! \n");
        else {
            scanner.nextLine();
            System.out.print("""
                        Qual será a forma de busca para o livro?
                        1- Nome do Livro
                        2- Nome do Autor
                        3- Área de Interesse
                        Escolha:""");
            int escolha = scanner.nextInt();
            if (escolha == 1) buscaPorNomeLivro(scanner, existentes);
            else if (escolha == 2) buscaPorNomeAutor(scanner, existentes);
            else if (escolha == 3) buscaPorAreaInteresse(scanner, existentes);
            else System.out.println("Escolha Incorreta!");
        }
    }

    public static void buscaPorNomeLivro(Scanner scanner, List<String> existentes) {
        scanner.nextLine();
        System.out.print("Qual o nome do livro? ");
        String nomeDoLivro = scanner.nextLine();
        boolean encontrado = false;
        for (int i = 1; i < existentes.size(); i++) {
            if (existentes.get(i).split(",")[0].trim().equals(nomeDoLivro.trim())) {
                System.out.println(existentes.get(i));
                encontrado = true;
            }
        }
        if(!encontrado) System.out.println("Livro não encontrado!");
    }
    public static void buscaPorNomeAutor(Scanner scanner, List<String> existentes) {
        scanner.nextLine();
        System.out.print("Qual o nome do autor? ");
        String nomeDoAutor = scanner.nextLine();
        boolean encontrado = false;
        for (int i = 1; i < existentes.size(); i++) {
            if (existentes.get(i).split(",")[2].trim().equals(nomeDoAutor.trim())) {
                System.out.println(existentes.get(i));
                encontrado = true;
            }
        }
        if(!encontrado) System.out.println("Livro com o nome do autor não encontrado!");
    }
    public static void buscaPorAreaInteresse(Scanner scanner, List<String> existentes) {
        scanner.nextLine();
        System.out.print("Qual à area de interesse? ");
        String areaDeInteresse = scanner.nextLine();
        boolean encontrado = false;
        for (int i = 1; i < existentes.size(); i++) {
            if (existentes.get(i).split(",")[3].trim().equals(areaDeInteresse.trim())) {
                System.out.println(existentes.get(i));
                encontrado = true;
            }
        }
        if(!encontrado) System.out.println("Livro com o área de interesse não encontrado!");
    }


    public static void excluirLivro(String url, Scanner scanner, Biblioteca biblioteca, List<String> existentes) {
        if (existentes.size() == 1)
            System.out.println("Cadastre pelo menos um livro! \n");
        else {
            String[] livro = new String[4];
            String[] estruturaLivro = {
                    "nome do Livro",
                    "número de Páginas",
                    "nome do Autor",
                    "área de Interesse"
            };
            scanner.nextLine();
            for (int i = 0; i < livro.length; i++) {
                System.out.print("Por favor, digite o " + estruturaLivro[i] + ": ");
                livro[i] = scanner.nextLine();
            }
            boolean encontrado = false;
            for (int i = 1; i < existentes.size(); i++) {
                if (existentes.get(i).split(",")[0].trim().equals(livro[0].trim()) && existentes.get(i).split(",")[1].trim().equals(livro[1].trim()) && existentes.get(i).split(",")[2].trim().equals(livro[2].trim()) && existentes.get(i).split(",")[3].trim().equals(livro[3].trim())) {
                    existentes.remove(existentes.get(i));
                    biblioteca.removeLivroBiblioteca(existentes, url);
                    encontrado = true;
                }
            }
            if (!encontrado) System.out.println("Livro não encontrado! \n");
        }
    }

    public static void gerarRelatorio(List<String> existentes) {
        if (existentes.size() == 1)
            System.out.println("Cadastre pelo menos um livro! \n");
        else {
            for (String livro : existentes) {
                System.out.println(livro);
            }
        }
    }
}