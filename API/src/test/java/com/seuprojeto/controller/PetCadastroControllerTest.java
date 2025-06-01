package com.seuprojeto.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seuprojeto.PetCadastroController;
import com.seuprojeto.models.PetCadastro;
import com.seuprojeto.repository.PetCadastroRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PetCadastroController.class)
public class PetCadastroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PetCadastroRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveCriarUmPet() throws Exception {
        PetCadastro pet = new PetCadastro("1", "Rex", "Labrador", "123456");

        Mockito.when(repository.save(any(PetCadastro.class))).thenReturn(pet);

        mockMvc.perform(post("/api/petcadastro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pet)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomeAnimal").value("Rex"));
    }

    @Test
    void deveListarPets() throws Exception {
        Mockito.when(repository.findAll()).thenReturn(Collections.singletonList(
                new PetCadastro("1", "Totó", "Poodle", "789123")
        ));

        mockMvc.perform(get("/api/petcadastro"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nomeAnimal").value("Totó"));
    }
}
