package br.com.dio.desafio.dominio;

import java.util.*;

public class Dev {
    private String nome;
    private final Set<Conteudo> conteudosInscritos = new LinkedHashSet<>();
    private final Set<Conteudo> conteudosConcluidos = new LinkedHashSet<>();

    public void inscreverBootcamp(Bootcamp bootcamp){
        this.conteudosInscritos.addAll(bootcamp.getConteudos());
        bootcamp.addDevInscrito(this);
    }

    public void progredir() {
        this.conteudosInscritos.stream()
                .findFirst()
                .ifPresentOrElse(
                c -> {
                    this.conteudosConcluidos.add(c);
                    this.conteudosInscritos.remove(c);
                },
                () -> { throw new NoSuchElementException("Você não está matriculado em nenhum conteúdo!"); }
        );
    }

    public double calcularTotalXp() {
        return this.conteudosConcluidos
                .stream()
                .mapToDouble(Conteudo::calcularXp)
                .sum();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Conteudo> getConteudosInscritos() {
        return Collections.unmodifiableSet(new LinkedHashSet<>(conteudosInscritos));
    }

    public Set<Conteudo> getConteudosConcluidos() {
        return Collections.unmodifiableSet(new LinkedHashSet<>(conteudosConcluidos));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dev dev = (Dev) o;
        return Objects.equals(nome, dev.nome) && Objects.equals(conteudosInscritos, dev.conteudosInscritos) && Objects.equals(conteudosConcluidos, dev.conteudosConcluidos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, conteudosInscritos, conteudosConcluidos);
    }
}
