package io.github.leocklaus.asyncrequests.controller;

import io.github.leocklaus.asyncrequests.service.PokemonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Supplier;

@RestController
@RequestMapping("/pokemon")
public class PokemonController {

    private final PokemonService pokemonService;

    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @GetMapping(value = "/cf")
    public ResponseEntity<?> completableFuture(@RequestParam boolean vt){
        System.out.println("Async processing using completable future !!!");

        long startDateTime = System.currentTimeMillis();

        ExecutorService executorService;

        if (vt)
            executorService = Executors.newVirtualThreadPerTaskExecutor();
        else
            executorService = Executors.newFixedThreadPool(100);

        List<CompletableFuture<String>> cfList = new ArrayList<>();

        for(String poke : pokemonService.pokeNames()){
            cfList.add(CompletableFuture.supplyAsync(()->{
                return pokemonService.getPokemonExpByName(poke);
            }));
        }

        cfList.forEach(cf -> {
            try {
                System.out.println(cf.get());
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });

        long endDateTime = System.currentTimeMillis();
        System.out.println("Time taken in milliseconds : " + (endDateTime-startDateTime));

        executorService.close();

        return ResponseEntity.ok().build();

    }

    @GetMapping(value = "/sc")
    public ResponseEntity<?> structuredConcurrency() throws InterruptedException {
        System.out.println("Async processing using structured Concurrency !!!");

        long startDateTime = System.currentTimeMillis();

        var scope = new StructuredTaskScope.ShutdownOnFailure();
        List<Supplier<String>> supplierList = new ArrayList<>();

        for(String poke : pokemonService.pokeNames()){
            supplierList.add(scope.fork(()-> pokemonService.getPokemonExpByName(poke)));
        }

        scope.join().throwIfFailed(RuntimeException::new);

        supplierList.stream().forEach(supplier -> {
                    System.out.println(supplier.get());
                }
        );

        long endDateTime = System.currentTimeMillis();
        System.out.println("Time taken in milliseconds : " + (endDateTime-startDateTime));

        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/sync")
    public ResponseEntity<?> sync(){
        System.out.println("Sync processing");

        long startDateTime = System.currentTimeMillis();


        for(String poke : pokemonService.pokeNames()){
            System.out.println(pokemonService.getPokemonExpByName(poke));
        }

        long endDateTime = System.currentTimeMillis();
        System.out.println("Time taken in milliseconds : " + (endDateTime-startDateTime));

        return ResponseEntity.ok().build();
    }

}
