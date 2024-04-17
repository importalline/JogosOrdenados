import java.io.*;
import java.util.*;

//definindo classes do jogo.
class Jogo {
    String jogos;
    String categoria;
    double avaliacao;

    //definindo parametros aos atributos.
    public Jogo(String jogos, String categoria, double avaliacao) {
        this.jogos = jogos;
        this.categoria = categoria;
        this.avaliacao = avaliacao;
    }
}

//armazenando os objetos do jogo.
public class OrdenadorJogos {
    static Scanner scanner = new Scanner(System.in);
    static List<Jogo> jogos = new ArrayList<>();

    //criando menu com as opções.
    public static void main(String[] args) {
        int opcao;
        do {
            mostrarMenu();
            opcao = scanner.nextInt();
            switch (opcao) {
                case 1:
                    lerArquivo("JogosDesordenados.csv");
                    break;
                case 2:
                    selectionSort(jogos);
                    salvarJogosOrdenados("JogosOrdenadosporCategoria.csv");
                    System.out.println("Sucesso! Os jogos foram ordenados por categoria e estão salvos no arquivo.");
                    break;
                case 3:
                    bubbleSort(jogos);
                    salvarJogosOrdenados("JogosOrdenadosporAvaliacao.csv");
                    System.out.println("Sucesso! Os jogos foram ordenados por avaliação e salvos no arquivo.");
                    break;
                case 4:
                    System.out.println("Encerrando programa.");
                    break;
                default:
                    System.out.println("Opção inválida. Por favor, tente novamente.");
                    break;
            }
        } while (opcao != 4);
    }

    //apresentar o menu.
    static void mostrarMenu() {
        System.out.println("[1] Ler arquivo");
        System.out.println("[2] Ordenar por categoria");
        System.out.println("[3] Ordenar por avaliação");
        System.out.println("[4] Sair");
        System.out.print("Digite uma opção: ");
    }

    //lendo o arquivo JogosDesordenados.
    static void lerArquivo(String nomeArquivo) {
        try (Scanner arquivoScanner = new Scanner(new File(nomeArquivo))) {
            while (arquivoScanner.hasNextLine()) {
                String linha = arquivoScanner.nextLine();
                String[] partes = linha.split(",");
                String nome = partes[0];
                String categoria = partes[1];
                double avaliacao = Double.parseDouble(partes[2]);
                jogos.add(new Jogo(nome, categoria, avaliacao));
            }
            System.out.println("Arquivo lido com sucesso.");
        } catch (FileNotFoundException e) {
            System.out.println("Oh não! O arquivo não foi encontrado.");
        }
    }

    //implementando algoritimo de ordenação selectionSort e bubbleSort.
    static void selectionSort(List<Jogo> jogos) {
        int n = jogos.size();

        //ordenando as CATEGORIAS em ordem alfabética.
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (jogos.get(j).categoria.compareTo(jogos.get(minIndex).categoria) < 0) {
                    minIndex = j;
                }
            }
            Collections.swap(jogos, i, minIndex);
        }
    }

    static void bubbleSort(List<Jogo> jogos) {
        int n = jogos.size();

        //ordendando as AVALIAÇÕES em ordem decrescente.
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (jogos.get(j).avaliacao < jogos.get(j + 1).avaliacao) {
                    Collections.swap(jogos, j, j + 1);
                }
            }
        }
    }

    //salvando jogos ordenados.
    static void salvarJogosOrdenados(String nomeArquivo) {
        try (PrintWriter escritor = new PrintWriter(new FileWriter(nomeArquivo))) {
            for (Jogo jogo : jogos) {
                escritor.println(jogo.jogos + "," + jogo.categoria + "," + jogo.avaliacao);
            }
        } catch (IOException e) {
            System.out.println("Oh não! Ocorreu um erro ao salvar o arquivo.");
        }
    }
}