package br.edu.univas;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Biblioteca {

    public void CadastrarLivros(String nome_do_livro, String numero_de_paginas, String nome_do_autor, String area_de_interesse, String url) {
        CriarArquivo(url);
        try {
            List<String> existentes = LinhaExistentes(url);
            String linha = nome_do_livro.trim() + "," + numero_de_paginas.trim() + "," + nome_do_autor.trim() + "," + area_de_interesse.trim();
            existentes.add(linha);
            String todasLinhas = UnicaLinha(existentes);
            FileWriter arquivo = new FileWriter(url);
            arquivo.write(todasLinhas);
            arquivo.close();

        } catch (Exception error) {
            System.out.println("Erro ao gerar o arquivo " + url);
        }
    }

    public void removeLivroBiblioteca(List<String> existentes, String url){
        try{
            String todasLinhas = UnicaLinha(existentes);
            FileWriter arquivo = new FileWriter(url);
            arquivo.write(todasLinhas);
            arquivo.close();
        } catch(Exception error){
            System.out.println("Erro ao remover Livro" + url);
        }
    }

    public String UnicaLinha(List<String> existentes) {
        StringBuilder unicaLinha = new StringBuilder();
        for (String linha : existentes) {
            unicaLinha.append(linha).append("\n");
        }
        return unicaLinha.toString();
    }
    public static boolean retornaExistenciaDoArquivo(File arquivo) {
        return arquivo.exists() && !arquivo.isDirectory();
    }

    public static boolean ArquivoExiste(String url){
        File file = new File(url);
        return retornaExistenciaDoArquivo(file);
    }
    public void CriarArquivo(String url) {
        File arquivo = new File(url);
        try {
            if (!retornaExistenciaDoArquivo(arquivo)) {
                arquivo.createNewFile();
                CadastrarLivros("nome_do_livro".trim(),"numero_de_paginas".trim(),"nome_do_autor".trim(),"area_de_interesse".trim(), url);
            }
        } catch (Exception e) {
            System.out.println("Erro ao criar arquivo!\n");
        }
    }

    public List<String> LinhaExistentes(String url) {

        List<String> result = new ArrayList<>();

        try {
            Path path = Paths.get(url);
            result = Files.readAllLines(path);
        } catch (Exception erro) {
            System.out.println("Erro ao ler o arquivo! \n");
        }

        return result;
    }
}