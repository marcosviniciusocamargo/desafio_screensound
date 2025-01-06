package br.com.alura.screensound.service;

import java.util.Scanner;

public class GetUserInput {
    private final Scanner scanner = new Scanner(System.in);
    public String get(String query){
        System.out.println(query);
        return scanner.nextLine();
    }
}
