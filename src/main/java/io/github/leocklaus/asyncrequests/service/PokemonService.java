package io.github.leocklaus.asyncrequests.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class PokemonService {

    RestClient restClient = RestClient.builder()
            .baseUrl("https://pokeapi.co/api/v2/pokemon")
            .build();


    public record PokeResponse(
            @JsonProperty("base_experience")
            Integer baseExperience,
            String name
    ){}

    public String[] pokeNames(){

        return new String[]{
                "bulbasaur", "ivysaur", "venusaur", "charmander", "charmeleon",
                "charizard", "squirtle", "wartortle", "blastoise", "caterpie"
        };
    }

    public String getPokemonExpByName(String pokemonName){
        PokeResponse response = restClient.get()
                .uri("/" + pokemonName)
                .retrieve()
                .body(PokeResponse.class);

        if(response == null){
            throw new RuntimeException();
        }

        return  "Name: " +
                response.name +
                " experience: " +
                response.baseExperience;

    }

}
