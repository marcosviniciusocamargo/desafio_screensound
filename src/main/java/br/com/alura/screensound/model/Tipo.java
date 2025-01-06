package br.com.alura.screensound.model;

public enum Tipo {
    SOLO("solo"),
    DUPLA("dupla"),
    BANDA("banda");

    private String tipo;

    Tipo(String tipo){
        this.tipo = tipo;
    }

    public static Tipo fromString(String texto){
        for (Tipo tipo : Tipo.values()){
            if (tipo.tipo.equalsIgnoreCase(texto)){
                return tipo;
            }
        }
        throw new IllegalArgumentException("Nenhum tipo encontrado para a string fornecida: " + texto);
    }

}
